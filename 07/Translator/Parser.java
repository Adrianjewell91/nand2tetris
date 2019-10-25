package Translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    Scanner scanner;
    String line;

    Parser(String p) throws FileNotFoundException {
        scanner = new Scanner(new File(p));
    }

    public Boolean hasMoreCommands() {
        return scanner.hasNextLine();
    }

    public void advance() {
        line = scanner.nextLine();
        line = line.split("//")[0];
        line = line.trim();
    }

    public String commandType() {
        return "";
    }

    public String arg1() {
        return "";
    }

    public int arg2() {
        return 0;
    }
}
