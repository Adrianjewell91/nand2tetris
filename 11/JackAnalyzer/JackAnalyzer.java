package JackAnalyzer;

import java.io.IOException;

class JackAnalyzer {
    private static Tokenizer tokenizer;
    private static Writer writer;
    
    public static void main(String[] args) throws IOException {
        // Seven programs to test:

        // Shall I write some kind of searcher for .jack files and out put each one into a .vm file in the same directory?
        // Or just hard code it - lets actually try to do the directory searcher.

        //General outline:
            // For each file: tokenize, compile, build the class symbol table during the class declarations, 
            // and build the function symboltable as you go through the function. 

            // at the same time, build the .vm file using the parse results and the symbol table.
        // Symbol Table:
            // Hash table for the class, then a hash table for each function as you parse it. 
        
        // VM Writer - when you parse the code the vm writer will also writhe appropriate code into the .vm 
            // And that will require writing code for every possible thing in the grammar, including all the clever things
            // like pushing references to objects when calling functions.

        // I have to study the history because there can't be anything in this field that is magical to me, that's not allowed.
        // For some reaosn I believe that to be true.

        // Old Tests:
        // String[] files = new String[] {
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/ArrayTest/Main.jack"};
        // String[] tokenOutputs = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/ArrayTestT.xml"};
        // String[] outputs = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/ArrayTest.xml"};
        // compile(files, tokenOutputs, outputs);

        // files = new String[] {
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Main.jack",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Square.jack",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/SquareGame.jack", };
        // tokenOutputs = new String[] { "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareMainT.xml",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareT.xml",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareGameT.xml", };
        // outputs = new String[] { "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareMain.xml",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/Square.xml",
        //         "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareGame.xml", };
        // compile(files, tokenOutputs, outputs);
    }

    public static void compile(String[] files, String[] tokenOutputs, String[] outputs) throws IOException {
        for (int i = 0; i < files.length; i++) {
            tokenize(files[i], tokenOutputs[i]);
            parse(tokenOutputs[i], outputs[i]);
        }
    }

    public static void tokenize(String path, String output) throws IOException {
        writer = new Writer(output);
        tokenizer = new Tokenizer(path);
        writer.writeToken("<tokens>");
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            writer.writeToken(tokenizer.toXML());
        }
        writer.writeToken("</tokens>");
        writer.close();
    }

    public static void parse(String path, String output)throws IOException {
        new Parser(path, output);
    }
}
