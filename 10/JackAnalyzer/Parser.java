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
        advance(); // skip "<tokens>"
        writer.writeToken("<class>");
        for (int i = 0; i < 3; i++) {
            writer.writeToken(advance());
        }
        advance();
        // System.out.println(_getType());
        // System.out.println(_getValue());
        _compileClassVarDec();
        _compileSubroutine();
        writer.writeToken("<symbol> } </symbol>");
        // writer.writeToken(advance()); // the final symbol "}", by this point the write should be on the last line.
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
            // start parsing:
            // 4X advance() function returnType name (
            writer.writeToken(line);
            writer.writeToken(advance());
            writer.writeToken(advance());
            writer.writeToken(advance());
            advance();
            _compileParameterList(); // parameters
            writer.writeToken("<subroutineBody>");
            writer.writeToken(line); // {
            advance();
            _compileVarDec();
            _compileStatements();    
            writer.writeToken(line); // }
            writer.writeToken("</subroutineBody>");
            writer.writeToken("</subroutineDec>");
            advance(); // to next line
        }

    }

    private void _compileParameterList() {
        writer.writeToken("<parameterList>");
        while(!_getValue().equals(")")) {
            writer.writeToken(line);
            advance();
        }
        writer.writeToken("</parameterList>");
        writer.writeToken(line); // )
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
            if (!Arrays.asList("while", "return", "if", "let", "do").contains(_getValue())) break;
            switch(_getValue()) {
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

            advance(); // advance beyond the ";" to the possible next statement.
        }
        writer.writeToken("</statements>");
    }

    private void _compileDo() {
        writer.writeToken("<doStatement>");
        writer.writeToken("</doStatement>");
    }

    private void _compileLet() {
        writer.writeToken("<letStatement>");
        // current line will be "let";
        // two advances for let and varName:
        writer.writeToken(line);
        writer.writeToken(advance());
        advance();
        // check the optional [expression] attachment  
        if (_getValue().equals("[")) {
            writer.writeToken(line); // for [
            _compileExpression();
            // advance for "]"
            writer.writeToken(line);
            advance();
        }
        // advance for "="
        writer.writeToken(line);
        advance(); // to first line of expression

        _compileExpression(); // this is the necessary expression 
        
        writer.writeToken(line); // writeToken(line) for the ";"
        writer.writeToken("</letStatement>");
    }

    private void _compileWhile() {
        writer.writeToken("<whileStatement>");
        writer.writeToken("</whileStatement>");
    }

    private void _compileReturn() {
        writer.writeToken("<returnStatement>");
        writer.writeToken("</returnStatement>");
    }

    private void _compileIf() {
        writer.writeToken("<ifStatement>");
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
        //Eight options here: current line is first line of the term:
        //In all cases writeToken(line), then 
        writer.writeToken(line);
        if (Arrays.asList("-", "~").contains(_getValue())) {
            advance();
            _compileTerm();
        // if "(" ; 
        } else if (_getValue().equals("(")) {
            advance();
            _compileExpression();
            // close the ) 
            writer.writeToken(line);
            advance();
        } else if (!Arrays.asList("keyword", "stringConstant", "integerConstant").contains(_getType())) {
            // if it integer keyword or char constant, you're done, so...
            // else
            advance(); //to the next line
            // if it's '['
            if (_getValue().equals("[")) {
                writer.writeToken(line);
                advance();
                _compileExpression();
                writer.writeToken(line);
                // for the ]
                advance();
            }
            // else if it's (, were in a subroutine call now.
            else if (_getValue().equals("(")) {
                writer.writeToken(line); 
                advance();
                _compileExpressionList();
                writer.writeToken(line); // for )
                advance();
            } else if (_getValue().equals(".")) { // also subroutine call.
                // else if it '.':
                writer.writeToken(line);// write the "."
                writer.writeToken(advance());// write the subroutineName
                writer.writeToken(advance()); // write the ( 
                advance(); // to the first line of the expressionList 
                _compileExpressionList();
                writer.writeToken(line);// write the ")"
                advance();
            }
            //else
            // it just the varName and the term is done. do nothing (you're already on the next line)
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
        return line.substring(m.start()+1, m.end()-2).trim();
        }
        return "";
    }
}