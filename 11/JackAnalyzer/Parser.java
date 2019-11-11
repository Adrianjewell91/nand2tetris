package JackAnalyzer;

// This thing is so massize how, can i fix this?

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

class Parser {
    Scanner scanner;
    String line;
    Writer writer;
    VMWriter codeWriter;
    private SymbolTable symbols = new SymbolTable();
    private final Pattern valuePattern = Pattern.compile("\\s.*\\s");
    // private final Pattern valuePattern = Pattern.compile(">.*<");
    private final Pattern typePattern = Pattern.compile("^<.*>\\s");

    private Integer whileEnumerator = 0;
    private Integer ifEnumerator = 0;
    private String className;
    private Integer expressionsCount;

    static void parse(String p, String output) throws IOException {
        Parser parser = new Parser(p, output);
        parser.compileClass();
    }

    Parser(String p, String output) throws IOException {
        scanner = new Scanner(new File(p));
        writer = new Writer(output);
        codeWriter = new VMWriter(output.split("\\.")[0] + ".vm");
        // skip "<tokens>"" and move to first token.
        advance();
        advance();
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
        this.className = _getValue();
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        _compileClassVarDec();
        _compileSubroutine();
        
        _writeAndAdvance(line, true);
        _writeAndAdvance("</class>", false);
        codeWriter.close();
        writer.close();
    }

    private void _compileClassVarDec() {
        while (Arrays.asList("static", "field").contains(_getValue())) {
            String kind = _getValue();
            _writeAndAdvance("<classVarDec>", false);
            _writeAndAdvance(line, true); // static or field
            String type = _getValue();
            _writeAndAdvance(line, true); // type

            while (true) {
                String name = _getValue();
                symbols.Define(name, type, kind.toUpperCase());
                _writeAndAdvance(line, true); // name;
                _writeAndAdvance(line, false); // , or ;
                if (_getValue().equals(";"))
                    break;
                advance();
            }
            _writeAndAdvance("</classVarDec>", true);
        }
    }

    private void _compileSubroutine() {
        while (Arrays.asList("function", "constructor", "method").contains(_getValue())) {
            String type = _getValue();
            whileEnumerator = 0;
            ifEnumerator = 0;
            symbols.startSubroutine(type.equals("method"));

            _writeAndAdvance("<subroutineDec>", false);
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            String name = _getValue();
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _compileParameterList();
            _writeAndAdvance("<subroutineBody>", false);
            _writeAndAdvance(line, true);
            _compileVarDec();
            codeWriter.writeFunction(this.className + "." + name, symbols.VarCount("VAR"));

            if (type.equals("constructor")) {
                int count = symbols.VarCount("FIELD");
                codeWriter.writePush("CONSTANT", count);
                codeWriter.writeCall("Memory.alloc", 1);
                codeWriter.writePop("POINTER", 0);
            } else if (type.equals("method")) {
                codeWriter.writePush("ARGUMENT", 0);
                codeWriter.writePop("POINTER", 0);
            }
            _compileStatements();
            _writeAndAdvance(line, true);
            _writeAndAdvance("</subroutineBody>", false);
            _writeAndAdvance("</subroutineDec>", false);
        }
    }

    private void _compileParameterList() {
        _writeAndAdvance("<parameterList>", false);
        while (!_getValue().equals(")")) {
            // Two passes, one for the type and one for the name.
            String type = _getValue();
            _writeAndAdvance(line, true);
            String name = _getValue();
            _writeAndAdvance(line, true);
            symbols.Define(name, type, "ARG");
            if (_getValue().equals(","))
                advance();
        }
        _writeAndAdvance("</parameterList>", false);
        _writeAndAdvance(line, true);
    }

    private void _compileVarDec() {
        while (Arrays.asList("var").contains(_getValue())) {
            _writeAndAdvance("<varDec>", false);
            _writeAndAdvance(line, true); // var
            String type = _getValue();
            _writeAndAdvance(line, true); // type

            while (true) {
                String name = _getValue();
                symbols.Define(name, type, "VAR");
                _writeAndAdvance(line, true); // name;
                _writeAndAdvance(line, false); // , or ;
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
        // The situations are:
        // 1. A library function
        // 2. An object function with or without variables.
        // 3 A method on 'this', with no context.

        _writeAndAdvance("<doStatement>", false);
        _writeAndAdvance(line, true);

        String method = _getValue();
        String context = "";

        _writeAndAdvance(line, true);
        if (_getValue().equals(".")) {
            _writeAndAdvance(line, true);
            context = method;
            method = _getValue();
            _writeAndAdvance(line, true);
        }

        _writeAndAdvance(line, true);
        Integer initialExpressionsCount = 0;
        
        String objectLookUp = symbols.TypeOf(context);
        if (context.equals("")) {
            codeWriter.writePush("POINTER", 0);
            initialExpressionsCount++;
            context = this.className;
        } else if (!objectLookUp.equals("NONE")) {
            initialExpressionsCount++;
            codeWriter.writePush(_getSegment(symbols.KindOf(context)), symbols.IndexOf(context));
            context = objectLookUp;
        }

        _compileExpressionList(initialExpressionsCount);
        
        String name = context.equals("") ? method : context + "." + method;
        codeWriter.writeCall(name, expressionsCount);
        codeWriter.writePop("TEMP", 0);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _writeAndAdvance("</doStatement>", false);
    }

    private void _compileLet() {
        _writeAndAdvance("<letStatement>", false);
        _writeAndAdvance(line, true);

        int idx = symbols.IndexOf(_getValue());
        String segment = _getSegment(symbols.KindOf(_getValue()));
        String varName = _getValue();
        Boolean isArray = false;

        _writeAndAdvance(line, true);
        if (_getValue().equals("[")) {
            isArray = true;
            _writeAndAdvance(line, true);
            _compileExpression();
            codeWriter.writePush(_getSegment(symbols.KindOf(varName)), symbols.IndexOf(varName));
            codeWriter.writeArithmetic("+");
            _writeAndAdvance(line, true);
        }

        _writeAndAdvance(line, true);
        _compileExpression();

        if (isArray) {
            codeWriter.writePop("TEMP", 0);
            codeWriter.writePop("POINTER", 1);
            codeWriter.writePush("TEMP", 0);
            codeWriter.writePop("THAT", 0);
        } else {
            codeWriter.writePop(segment, idx);
        }

        _writeAndAdvance(line, true);

        _writeAndAdvance("</letStatement>", false);
    }

    private void _compileWhile() {
        _writeAndAdvance("<whileStatement>", false);
        codeWriter.writeLabel("WHILE_EXP" + whileEnumerator);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);

        _compileExpression();
        codeWriter.writeArithmetic("~");
        codeWriter.writeIf("WHILE_END" + whileEnumerator);
        Integer whileEnum = whileEnumerator;
        whileEnumerator++;
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _compileStatements();
        _writeAndAdvance(line, true);
        codeWriter.writeGoto("WHILE_EXP" + whileEnum);
        codeWriter.writeLabel("WHILE_END" + whileEnum);
        _writeAndAdvance("</whileStatement>", false);
    }

    private void _compileReturn() {
        _writeAndAdvance("<returnStatement>", false);
        _writeAndAdvance(line, true);

        if (!_getValue().equals(";")) {
            _compileExpression();
        } else {
            codeWriter.writePush("CONSTANT", 0);
        }
        _writeAndAdvance(line, true);
        codeWriter.writeReturn();
        _writeAndAdvance("</returnStatement>", false);
    }

    private void _compileIf() {
        _writeAndAdvance("<ifStatement>", false);
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _compileExpression();
        codeWriter.writeIf("IF_TRUE" + ifEnumerator);
        codeWriter.writeGoto("IF_FALSE" + ifEnumerator);
        codeWriter.writeLabel("IF_TRUE" + ifEnumerator);
        Integer ifEnum = ifEnumerator;
        ifEnumerator++;
        _writeAndAdvance(line, true);
        _writeAndAdvance(line, true);
        _compileStatements();

        _writeAndAdvance(line, true);
        if (_getValue().equals("else")) {
            codeWriter.writeGoto("IF_END" + ifEnum);
            codeWriter.writeLabel("IF_FALSE" + ifEnum);
            _writeAndAdvance(line, true);
            _writeAndAdvance(line, true);
            _compileStatements();
            _writeAndAdvance(line, true);
            codeWriter.writeLabel("IF_END" + ifEnum);
        } else {
            codeWriter.writeLabel("IF_FALSE" + ifEnum);

        }
        _writeAndAdvance("</ifStatement>", false);
    }

    private void _compileExpression() {
        _writeAndAdvance("<expression>", false);
        _compileTerm();
        while (Arrays.asList("+", "-", "*", "/", "&amp;", "|", "&lt;", "&gt;", "=").contains(_getValue())) {
            String operator = _getValue();
            _writeAndAdvance(line, true);
            _compileTerm();
            codeWriter.writeArithmetic(operator); // Math.multiply
        }
        _writeAndAdvance("</expression>", false);
    }

    private void _compileTerm() {
        _writeAndAdvance("<term>", false);

        if (Arrays.asList("-", "~").contains(_getValue())) {
            String op = _getValue();
            _writeAndAdvance(line, true);
            _compileTerm();
            codeWriter.writeArithmetic(op.equals("-") ? "NEG" : op);
        } else if (_getValue().equals("(")) {
            _writeAndAdvance(line, true);
            _compileExpression();
            _writeAndAdvance(line, true);
        } else if (!Arrays.asList("keyword", "stringConstant", "integerConstant").contains(_getType())) {
            String method = _getValue();
            _writeAndAdvance(line, true);
            String context = "";
            if (_getValue().equals("[")) {
                // right here, I'm keying into the array.
                _writeAndAdvance(line, true);
                _compileExpression();
                String variable = method;
                codeWriter.writePush(_getSegment(symbols.KindOf(variable)), symbols.IndexOf(variable));
                codeWriter.writeArithmetic("+");
                codeWriter.writePop("POINTER", 1);
                codeWriter.writePush("THAT", 0);
                _writeAndAdvance(line, true);
            } else if (_getValue().equals("(")) {
                _writeAndAdvance(line, true);
                _compileExpressionList();
                _writeAndAdvance(line, true);
            } else if (_getValue().equals(".")) {
                _writeAndAdvance(line, true);
                context = method;
                method = _getValue();
                _writeAndAdvance(line, true);
                _writeAndAdvance(line, true);
                
                Integer initialExpressionsCount = 0;

                String objectLookUp = symbols.TypeOf(context);
                if (context.equals("")) {
                    codeWriter.writePush("POINTER", 0);
                    initialExpressionsCount++;
                    context = this.className;
                } else if (!objectLookUp.equals("NONE")) {
                    initialExpressionsCount++;
                    codeWriter.writePush(_getSegment(symbols.KindOf(context)), symbols.IndexOf(context));
                    context = objectLookUp;
                }

                _compileExpressionList(initialExpressionsCount);

                String name = context.equals("") ? method : context + "." + method;
                codeWriter.writeCall(name, expressionsCount);
                _writeAndAdvance(line, true);
            } else {
                String variable = method;
                codeWriter.writePush(_getSegment(symbols.KindOf(variable)), symbols.IndexOf(variable));
            }
        } else {
            if (_getType().equals("integerConstant")) {
                codeWriter.writePush("CONSTANT", Integer.parseInt(_getValue()));
            } else if (_getType().equals("keyword")) {
                if (_getValue().equals("true")) {
                    codeWriter.writePush("CONSTANT", 0);
                    codeWriter.writeArithmetic("~");
                } else if (_getValue().equals("false")) {
                    codeWriter.writePush("CONSTANT", 0);
                } else if (_getValue().equals("this")) {
                    codeWriter.writePush("POINTER", 0);
                } else if (_getValue().equals("null")) {
                    codeWriter.writePush("CONSTANT", 0);
                }
            } else if (_getType().equals("stringConstant")) {
                // Get the string with the proper amount of spaces.
                Integer length = _getValue().length();
                codeWriter.writePush("CONSTANT", length);
                codeWriter.writeCall("String.new", 1);
                String str = _getValue();
                for (int i = 0; i < length; i++) {
                    int j = (int) str.charAt(i);
                    codeWriter.writePush("CONSTANT", j);
                    codeWriter.writeCall("String.appendChar", 2);
                }
            }
            _writeAndAdvance(line, true);
        }
        _writeAndAdvance("</term>", false);
    }

    private void _compileExpressionList() {
        _compileExpressionList(0);
    }

    private void _compileExpressionList(Integer initialExpressionsCount) {
        expressionsCount = initialExpressionsCount;
        _writeAndAdvance("<expressionList>", false);
        if (!_getValue().equals(")")) {
            expressionsCount++;
            // Each one gets VMwriten sequentially.
            _compileExpression();
            while (_getValue().equals(",")) {
                expressionsCount++;
                _writeAndAdvance(line, true);
                _compileExpression();
            }
        }
        _writeAndAdvance("</expressionList>", false);
    }

    private String _getSegment(String kind) {
        if (kind.equals("CONSTANT")) {
            return "CONSTANT";
        } else if (kind.equals("VAR")) {
            return "LOCAL";
        } else if (kind.equals("ARG")) {
            return "ARGUMENT";
        } else if (kind.equals("FIELD")) {
            return "THIS";
        } else if (kind.equals("STATIC")) {
            return "STATIC";
        } else {
            return "";
        }
    }

    private String _getValue() {
        return _parseHelper(valuePattern, 1, -1);
    }

    private String _getType() {
        return _parseHelper(typePattern, 1, -2);
    }

    private String _parseHelper(Pattern pattern, Integer offsetLeft, Integer offsetRight) {
        java.util.regex.Matcher m = pattern.matcher(line);
        if (m.find()) {
            return line.substring(m.start() + offsetLeft, m.end() + offsetRight);
        }
        return "";
    }

    private void _writeAndAdvance(String token, Boolean shouldAdvance) {
        writer.writeToken(token);
        if (shouldAdvance)
            advance();
    }

}