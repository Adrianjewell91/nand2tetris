package Translator;

import java.io.IOException;

class Translator {
    static Parser parser;
    static CodeWriter writer;

    public static void main(String[] args) throws IOException {
        String[] files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.vm"};
        String output = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/BasicLoop/BasicLoop.asm";
        String[] name = new String [] {"BasicLoop" } ;
        compile(files, output, name);
        
        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/FibonacciElement/Sys.vm", 
                              "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/FibonacciElement/Main.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/FibonacciElement/FibonacciElement.asm";;
        name = new String[] {"Sys", "Main"};
        compile(files, output, name);
        
        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/ProgramFlow/FibonacciSeries/FibonacciSeries.asm";
        name = new String[] {"FibonacciSeries"};
        compile(files, output, name);
        
        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/08/FunctionCalls/SimpleFunction/SimpleFunction.asm";
        name = new String[] {"SimpleFunction"};
        compile(files, output, name);

        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/SimpleAdd/SimpleAdd.asm";
        name = new String[] {"SimpleAdd"};
        compile(files, output, name);
        
        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/StackArithmetic/StackTest/StackTest.asm";
        name = new String[] {"StackTest"};
        compile(files, output, name);

        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/BasicTest/BasicTest.asm";
        name = new String[] {"BasicTest"};
        compile(files, output, name);
        
        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/PointerTest/PointerTest.asm";
        name = new String[] {"PointerTest"};
        compile(files, output, name);

        files = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.vm"};
        output = "/Users/adrianjewell/Desktop/nand2tetris/projects/07/MemoryAccess/StaticTest/StaticTest.asm";
        name = new String[] {"StaticTest"};
        compile(files, output, name);
    }

    // Refactor into this then test:
    public static void compile(String[] files, String output, String[] names) throws IOException {
        writer = new CodeWriter(output, names[0]);
        for (int i = 0; i < files.length; i++) {
           writer.setFileName(names[i]);
           parser = new Parser(files[i]);
           translate();
        }
        writer.close();
    }

    public static void translate() throws IOException {
        while (parser.hasMoreCommands()) {
            parser.advance();
            writer.writeLine("//" + parser.line);
            switch (parser.commandType()) {
                // Use command pattern here?
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
    }
}

