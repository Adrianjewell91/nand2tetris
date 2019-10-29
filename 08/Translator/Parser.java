package Translator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Parser {
    Scanner scanner;
    String line;
    Set<String> arithmetics = new HashSet<String>(
            Arrays.asList(new String[] { "add", "sub", "neg", "eq", "gt", "lt", "and", "or", "not" }));
    Set<String> stacks = new HashSet<String>(
            Arrays.asList(new String[] { "push", "pop", "function" }));
    Set<String> controls = new HashSet<String>(
            Arrays.asList(new String[] { "if-goto", "goto", "call", "label" }));

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
        String[] words = line.split(" ");
        if (arithmetics.contains(words[0])) {
            return "C_ARITHMETIC";
        } else if (words.length == 3 && stacks.contains(words[0])) {
            return String.format("C_%s", words[0].toUpperCase());
        } else if (words.length == 2 && controls.contains(words[0])) {
            return String.format("C_%s", words[0].toUpperCase());
        } else if (words[0].equals("return")) { 
            return "C_RETURN";
        }
        return "";
    }

    public String arg1() {
        return line.split(" ")[1];
    }

    public Integer arg2() {
        return Integer.parseInt(line.split(" ")[2]);
    }
}
