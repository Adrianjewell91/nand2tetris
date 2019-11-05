package JackAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        _writeTokenAndNewLine();

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

            _writeTokenAndNewLine();
            _writeTokenAndNewLine();
            _writeTokenAndNewLine();
            _writeTokenAndNewLine();
            _compileParameterList();
            writer.writeToken("<subroutineBody>");
            _writeTokenAndNewLine();
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
            _writeTokenAndNewLine();
        }
        writer.writeToken("</parameterList>");
        _writeTokenAndNewLine();
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
                break;
            case "return":
                _compileReturn();
                break;
            case "if":
                _compileIf();
                break;
            case "let":
                _compileLet();
                break;
            case "do":
                _compileDo();
                break;
            }

        }
        writer.writeToken("</statements>");
    }

    private void _compileDo() {
        writer.writeToken("<doStatement>");
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        if (_getValue().equals(".")) {
            _writeTokenAndNewLine();
            _writeTokenAndNewLine();
        }
        _writeTokenAndNewLine();
        _compileExpressionList();
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        writer.writeToken("</doStatement>");
    }

    private void _compileLet() {
        writer.writeToken("<letStatement>");

        _writeTokenAndNewLine();
        _writeTokenAndNewLine();

        if (_getValue().equals("[")) {
            _writeTokenAndNewLine();
            _compileExpression();

            _writeTokenAndNewLine();
        }

        _writeTokenAndNewLine();

        _compileExpression();

        _writeTokenAndNewLine();
        writer.writeToken("</letStatement>");
    }

    private void _compileWhile() {
        writer.writeToken("<whileStatement>");
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        _compileExpression();
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        _compileStatements();
        _writeTokenAndNewLine();
        writer.writeToken("</whileStatement>");
    }

    private void _compileReturn() {
        writer.writeToken("<returnStatement>");
        _writeTokenAndNewLine();
        if (!_getValue().equals(";")) {
            _compileExpression();
        }
        _writeTokenAndNewLine();
        writer.writeToken("</returnStatement>");
    }

    private void _compileIf() {
        writer.writeToken("<ifStatement>");
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        _compileExpression();
        _writeTokenAndNewLine();
        _writeTokenAndNewLine();
        _compileStatements();

        _writeTokenAndNewLine();
        if (_getValue().equals("else")) {
            _writeTokenAndNewLine();
            _writeTokenAndNewLine();
            _compileStatements();
            _writeTokenAndNewLine();
        }
        writer.writeToken("</ifStatement>");
    }

    private void _compileExpression() {
        writer.writeToken("<expression>");
        _compileTerm();
        while (Arrays.asList("+", "-", "*", "/", "&amp;", "|", "&lt;", "&gt;", "=").contains(_getValue())) {
            _writeTokenAndNewLine();
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

            _writeTokenAndNewLine();
        } else if (!Arrays.asList("keyword", "stringConstant", "integerConstant").contains(_getType())) {

            advance();

            if (_getValue().equals("[")) {
                _writeTokenAndNewLine();
                _compileExpression();
                _writeTokenAndNewLine();
            }

            else if (_getValue().equals("(")) {
                _writeTokenAndNewLine();
                _compileExpressionList();
                _writeTokenAndNewLine();
            } else if (_getValue().equals(".")) {
                _writeTokenAndNewLine();
                _writeTokenAndNewLine();
                _writeTokenAndNewLine();
                _compileExpressionList();
                _writeTokenAndNewLine();
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
                _writeTokenAndNewLine();
                _compileExpression();
            }
        }
        writer.writeToken("</expressionList>");
    }

    private String _getValue() {
        return _parseHelper(valuePattern, 0, 0);
    }

    private String _getType() {
        return _parseHelper(typePattern, 1, -2);
    }

    private String _parseHelper(Pattern pattern, Integer offsetLeft, Integer offsetRight) {
        java.util.regex.Matcher m = pattern.matcher(line);
        if (m.find()) {
            return line.substring(m.start() + offsetLeft, m.end() + offsetRight).trim();
        }
        return "";
    }

    private void _writeTokenAndNewLine() {
        // Eventually add error handling 
        writer.writeToken(line);
        advance();
    }
}