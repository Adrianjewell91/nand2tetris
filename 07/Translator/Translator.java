package Translator;

import java.io.FileNotFoundException;

class Translator {
    static Parser parser;
    public static void main(String[] args) throws FileNotFoundException {
        parser = new Parser("/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm");
        iterate();
    }

    public static void iterate() {
        while (parser.hasMoreCommands()) {
            parser.advance();
            
            System.out.println(parser.line);
        }
    }
}

