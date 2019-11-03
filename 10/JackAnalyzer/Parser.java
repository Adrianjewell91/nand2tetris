package JackAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Parser {
    Scanner scanner;
    String line;
    Writer writer;

    Parser(String p, String output) throws IOException {
        scanner = new Scanner(new File(p));
        writer = new Writer(output);
        _compileClass();
    }

    public Boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    public void advance() {
        line = scanner.nextLine();
        line = line.split("//")[0];
        line = line.trim();
    }

    private void _compileClass() {
    }

    // private void _compileClassVarDec() {
    // }

    // private void _compileSubroutine() {
    // }

    // private void _compileParameterList() {
    // }

    // private void _compileVarDec() {
    // }

    // private void _compileStatements() {
    // }

    // private void _compileDo() {
    // }

    // private void _compileLet() {
    // }

    // private void _compileWhile() {
    // }

    // private void _compileReturn() {
    // }

    // private void _compileIf() {
    // }

    // private void _compileExpression() {
    // }

    // private void _compileTerm() {
    // }

    // private void _compileExpressionList() {
    // }

}