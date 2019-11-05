package JackAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

// Refactoring the code would be good idea I think.
class Parser {
    Scanner scanner;
    String line;
    Writer writer;
    private final Pattern valuePattern = Pattern.compile("\\s.*\\s");
    private final Pattern typePattern = Pattern.compile("^<.*>\\s");

    Parser(String p, String output) throws IOException {
        scanner = new Scanner(new File(p));
        writer = new Writer(output);
        _compileClass();
    }

    public Boolean hasMoreLines() {
        return scanner.hasNextLine();
    }

    public String advance() {
        System.out.println(line);
        line = scanner.nextLine();
        line = line.trim();
        return line;
    }

    private void _compileClass() {
        advance();
        writer.writeToken("<class>");
        for (int i = 0; i < 3; i++) {
            writer.writeToken(advance());
        }
        advance();

        _compileClassVarDec();
        _compileSubroutine();
        writer.writeToken("<symbol> } </symbol>");

        writer.writeToken("</class>");
        writer.close();
    }

    private void _compileClassVarDec() {
        while (true) {
            if (!Arrays.asList("static", "field").contains(_getValue()))
                return;
            writer.writeToken("<classVarDec>");

            while (true) {
                writer.writeToken(line);
                if (_getValue().equals(";"))
                    break;
                advance();
            }

            writer.writeToken("</classVarDec>");
            advance();
        }
    }

    private void _compileSubroutine() {
        while (true) {
            if (!Arrays.asList("function", "constructor", "method").contains(_getValue()))
                return;
            writer.writeToken("<subroutineDec>");

            writer.writeToken(line);
            writer.writeToken(advance());
            writer.writeToken(advance());
            writer.writeToken(advance());
            advance();
            _compileParameterList();
            writer.writeToken("<subroutineBody>");
            writer.writeToken(line);
            advance();
            _compileVarDec();
            _compileStatements();
            writer.writeToken(line);
            writer.writeToken("</subroutineBody>");
            writer.writeToken("</subroutineDec>");
            advance();
        }

    }

    private void _compileParameterList() {
        writer.writeToken("<parameterList>");
        while (!_getValue().equals(")")) {
            writer.writeToken(line);
            advance();
        }
        writer.writeToken("</parameterList>");
        writer.writeToken(line);
        advance();
    }

    private void _compileVarDec() {
        while (true) {
            if (!Arrays.asList("var").contains(_getValue()))
                return;
            writer.writeToken("<varDec>");

            while (true) {
                writer.writeToken(line);
                if (_getValue().equals(";"))
                    break;
                advance();
            }

            writer.writeToken("</varDec>");
            advance();
        }
    }

    private void _compileStatements() {
        writer.writeToken("<statements>");
        while (true) {
            if (!Arrays.asList("while", "return", "if", "let", "do").contains(_getValue()))
                break;
            switch (_getValue()) {
            case "while":
                _compileWhile();
                advance();
                break;
            case "return":
                _compileReturn();
                advance();
                break;
            case "if":
                _compileIf();
                break;
            case "let":
                _compileLet();
                advance();
                break;
            case "do":
                _compileDo();
                advance();
                break;
            }

        }
        writer.writeToken("</statements>");
    }

    private void _compileDo() {
        writer.writeToken("<doStatement>");
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        if (_getValue().equals(".")) {
            writer.writeToken(line);
            writer.writeToken(advance());
            advance();
        }
        writer.writeToken(line);
        advance();
        _compileExpressionList();
        writer.writeToken(line);
        writer.writeToken(advance());
        writer.writeToken("</doStatement>");
    }

    private void _compileLet() {
        writer.writeToken("<letStatement>");

        writer.writeToken(line);
        writer.writeToken(advance());
        advance();

        if (_getValue().equals("[")) {
            writer.writeToken(line);
            advance();
            _compileExpression();

            writer.writeToken(line);
            advance();
        }

        writer.writeToken(line);
        advance();

        _compileExpression();

        writer.writeToken(line);
        writer.writeToken("</letStatement>");
    }

    private void _compileWhile() {
        writer.writeToken("<whileStatement>");
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        _compileExpression();
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        _compileStatements();
        writer.writeToken(line);

        writer.writeToken("</whileStatement>");
    }

    private void _compileReturn() {
        writer.writeToken("<returnStatement>");
        writer.writeToken(line);
        advance();
        if (!_getValue().equals(";")) {
            _compileExpression();
        }
        writer.writeToken(line);
        writer.writeToken("</returnStatement>");
    }

    private void _compileIf() {
        writer.writeToken("<ifStatement>");
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        _compileExpression();
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        _compileStatements();

        writer.writeToken(line);
        advance();
        if (_getValue().equals("else")) {
            writer.writeToken(line);
            writer.writeToken(advance());
            advance();
            _compileStatements();
            writer.writeToken(line);
            advance();
        }
        writer.writeToken("</ifStatement>");
    }

    private void _compileExpression() {
        writer.writeToken("<expression>");
        _compileTerm();
        while (Arrays.asList("+", "-", "*", "/", "&amp;", "|", "&lt;", "&gt;", "=").contains(_getValue())) {
            writer.writeToken(line);
            advance();
            _compileTerm();
        }
        writer.writeToken("</expression>");
    }

    private void _compileTerm() {
        writer.writeToken("<term>");

        writer.writeToken(line);
        if (Arrays.asList("-", "~").contains(_getValue())) {
            advance();
            _compileTerm();

        } else if (_getValue().equals("(")) {
            advance();
            _compileExpression();

            writer.writeToken(line);
            advance();
        } else if (!Arrays.asList("keyword", "stringConstant", "integerConstant").contains(_getType())) {

            advance();

            if (_getValue().equals("[")) {
                writer.writeToken(line);
                advance();
                _compileExpression();
                writer.writeToken(line);

                advance();
            }

            else if (_getValue().equals("(")) {
                writer.writeToken(line);
                advance();
                _compileExpressionList();
                writer.writeToken(line);
                advance();
            } else if (_getValue().equals(".")) {

                writer.writeToken(line);
                writer.writeToken(advance());
                writer.writeToken(advance());
                advance();
                _compileExpressionList();
                writer.writeToken(line);
                advance();
            }

        } else {
            advance();
        }
        writer.writeToken("</term>");
    }

    private void _compileExpressionList() {
        writer.writeToken("<expressionList>");
        if (!_getValue().equals(")")) {
            _compileExpression();
            while (_getValue().equals(",")) {
                writer.writeToken(line);
                advance();
                _compileExpression();
            }
        }
        writer.writeToken("</expressionList>");
    }

    private String _getValue() {
        java.util.regex.Matcher m = valuePattern.matcher(line);
        if (m.find()) {
            return line.substring(m.start(), m.end()).trim();
        }
        return "";
    }

    private String _getType() {
        java.util.regex.Matcher m = typePattern.matcher(line);
        if (m.find()) {
            return line.substring(m.start() + 1, m.end() - 2).trim();
        }
        return "";
    }
}