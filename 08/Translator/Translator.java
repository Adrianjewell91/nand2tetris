package Translator;

import java.io.IOException;


class Translator {
    static Parser parser;
    static CodeWriter writer;

    public static void main(String[] args) throws IOException {
        String fileName;
        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.asm";
        translateTo(fileName,"BasicLoop");

        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.asm";
        translateTo(fileName,"FibonacciSeries");

        parser = new Parser(
                "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.vm");
        fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.asm";
        translateTo(fileName,"SimpleFunction");

        // These will require the parsing of multiple files into a simple asm file:
        // Write the logic for that. 
        // parser = new Parser(
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/FibonacciElement/FibonacciElement.vm");
        // fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/FibonacciElement/FibonacciElement.asm";
        // translateTo(fileName,"FibonacciElement");
        // parser = new Parser(
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/NestedCall/NestedCall.vm");
        // fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/NestedCall/NestedCall.asm";
        // translateTo(fileName,"NestedCall");
        // parser = new Parser(
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/StaticsTest/StaticsTest.vm");
        // fileName = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/StaticsTest/StaticsTest.asm";
        // translateTo(fileName,"StaticsTest");
    }

    public static void translateTo(String fileName, String name) throws IOException {
        writer = new CodeWriter(fileName, name);
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
                    case "C_LABEL":
                    writer.writeLabel(parser.arg1());
                    break;
                    case "C_GOTO":
                    writer.writeGoto(parser.arg1());
                    break;
                    case "C_IF-GOTO":
                    writer.writeIfGoto(parser.arg1());
                    break;
                    case "C_FUNCTION":
                    writer.writeFunction(parser.arg1(), parser.arg2());
                    break;
                    case "C_RETURN":
                    writer.writeReturn();
                    break;
                default:
                    break;

            }
        }
        writer.close();
    }
}

