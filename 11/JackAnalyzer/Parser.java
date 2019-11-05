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

    static void parse(String p, String output) throws IOException {
        Parser parser = new Parser(p, output);
        parser.compileClass();
    }

    Parser(String p, String output) throws IOException {
        scanner = new Scanner(new File(p));
        writer = new Writer(output);
        advance(); 
        advance(); // skip <tokens> and move to first token.
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

    public void compileClass() {
        _writeAndAdvance("<class>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        _compileClassVarDec();
        _compileSubroutine();
        _writeAndAdvance(line, true);

        _writeAndAdvance("</class>", false);
        writer.close();
    }

    private void _compileClassVarDec() {
        while (Arrays.asList("static", "field").contains(_getValue())) {
            _writeAndAdvance("<classVarDec>", false);

            while (true) {
                _writeAndAdvance(line, false);
                if (_getValue().equals(";"))
                    break;
                advance();
            }
            _writeAndAdvance("</classVarDec>", true);
        }
    }

    private void _compileSubroutine() {
        while (Arrays.asList("function", "constructor", "method").contains(_getValue())) {
            _writeAndAdvance("<subroutineDec>", false);
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _compileParameterList();
            _writeAndAdvance("<subroutineBody>", false);
            _writeAndAdvance(line, true);
            _compileVarDec();
            _compileStatements();
            _writeAndAdvance(line, true);
            _writeAndAdvance("</subroutineBody>", false);
            _writeAndAdvance("</subroutineDec>", false);
        }
    }

    private void _compileParameterList() {
        _writeAndAdvance("<parameterList>", false);
        while (!_getValue().equals(")")) {
            _writeAndAdvance(line, true);
        }
        _writeAndAdvance("</parameterList>", false);
        _writeAndAdvance(line, true);
    }

    private void _compileVarDec() {
        while (Arrays.asList("var").contains(_getValue())) {
            _writeAndAdvance("<varDec>", false);
            while (true) {
                _writeAndAdvance(line, false);
                if (_getValue().equals(";"))
                    break;
                advance();
            }
            _writeAndAdvance("</varDec>", true);
        }
    }

    private void _compileStatements() {
        _writeAndAdvance("<statements>", false);
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
        _writeAndAdvance("</statements>", false);
    }

    private void _compileDo() {
        _writeAndAdvance("<doStatement>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        if (_getValue().equals(".")) {
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
        }
        _writeAndAdvance(line, true);
        _compileExpressionList();
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _writeAndAdvance("</doStatement>", false);
    }

    private void _compileLet() {
        _writeAndAdvance("<letStatement>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        if (_getValue().equals("[")) {
            _writeAndAdvance(line, true);
            _compileExpression();
            _writeAndAdvance(line, true);
        }

        _writeAndAdvance(line, true);
        _compileExpression();
        _writeAndAdvance(line, true);
        _writeAndAdvance("</letStatement>", false);
    }

    private void _compileWhile() {
        _writeAndAdvance("<whileStatement>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        _compileExpression();
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _compileStatements();
        _writeAndAdvance(line, true);
        _writeAndAdvance("</whileStatement>", false);
    }

    private void _compileReturn() {
        _writeAndAdvance("<returnStatement>", false);
        _writeAndAdvance(line, true);

        if (!_getValue().equals(";")) {
            _compileExpression();
        }
        _writeAndAdvance(line, true);
        _writeAndAdvance("</returnStatement>", false);
    }

    private void _compileIf() {
        _writeAndAdvance("<ifStatement>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        _compileExpression();
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _compileStatements();

        _writeAndAdvance(line, true);
        if (_getValue().equals("else")) {
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _compileStatements();
            _writeAndAdvance(line, true);
        }
        _writeAndAdvance("</ifStatement>", false);
    }

    private void _compileExpression() {
        _writeAndAdvance("<expression>", false);
        _compileTerm();
        while (Arrays.asList("+", "-", "*", "/", "&amp;", "|", "&lt;", "&gt;", "=").contains(_getValue())) {
            _writeAndAdvance(line, true);
            _compileTerm();
        }
        _writeAndAdvance("</expression>", false);
    }

    private void _compileTerm() {
        _writeAndAdvance("<term>", false);

        if (Arrays.asList("-", "~").contains(_getValue())) {
            _writeAndAdvance(line, true);
            _compileTerm();
        } else if (_getValue().equals("(")) {
            _writeAndAdvance(line, true);
            _compileExpression();
            _writeAndAdvance(line, true);
        } else if (!Arrays.asList("keyword", "stringConstant", "integerConstant").contains(_getType())) {
            _writeAndAdvance(line, true);
            if (_getValue().equals("[")) {
                _writeAndAdvance(line, true);
                _compileExpression();
                _writeAndAdvance(line, true);
            } else if (_getValue().equals("(")) {
                _writeAndAdvance(line, true);
                _compileExpressionList();
                _writeAndAdvance(line, true);
            } else if (_getValue().equals(".")) {
                _writeAndAdvance(line, true);
                _writeAndAdvance(line, true);
                _writeAndAdvance(line, true);
                _compileExpressionList();
                _writeAndAdvance(line, true);
            }
        } else {
            _writeAndAdvance(line, true);
        }
        _writeAndAdvance("</term>", false);
    }

    private void _compileExpressionList() {
        _writeAndAdvance("<expressionList>", false);
        if (!_getValue().equals(")")) {
            _compileExpression();
            while (_getValue().equals(",")) {
                _writeAndAdvance(line, true);
                _compileExpression();
            }
        }
        _writeAndAdvance("</expressionList>", false);
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

    private void _writeAndAdvance(String token, Boolean shouldAdvance) {
        writer.writeToken(token);
        if (shouldAdvance)
            advance();
    }
}