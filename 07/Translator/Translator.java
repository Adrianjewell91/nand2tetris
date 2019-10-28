package Translator;

import java.io.IOException;


class Translator {
    static Parser parser;
    static CodeWriter writer;

    public static void main(String[] args) throws IOException {
        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm");
        String fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.asm";
        translateTo(fileName);

        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.asm";
        translateTo(fileName);

        parser = new Parser("/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.asm";
        translateTo(fileName);

        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.asm";
        translateTo(fileName);

        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.asm";
        translateTo(fileName);
    }

    public static void translateTo(String fileName) throws IOException {
        writer = new CodeWriter(fileName);
        while (parser.hasMoreCommands()) {
            parser.advance();
            writer.writeLine("//" + parser.line);
            switch (parser.commandType()) {
                case "C_ARITHMETIC":
                    writer.writeArithmetic(parser.line);
                    break;
                    case "C_PUSH":
                    writer.writePushPop("C_PUSH", parser.arg1(), parser.arg2());
                    break;
                    case "C_POP":
                    writer.writePushPop("C_POP", parser.arg1(), parser.arg2());
                    break;
                default:
                    break;

            }
        }
        writer.close();
    }
}

