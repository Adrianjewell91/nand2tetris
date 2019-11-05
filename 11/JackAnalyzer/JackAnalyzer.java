package JackAnalyzer;

import java.io.IOException;

class JackAnalyzer {
    public static void main(String[] args) throws IOException {
        // Seven programs to test:

        // Write code to compile a directory:
            // Should produce the tokens, the parse tree, and the .vm file (albeit empty atm)

        // I think the way this is supposed to go, is that 
        // I write vm as I parse the tree, taking the token as queues for what to write. 
            // The symbol tree will help along the way for identifiers.

            // Write the parse tree -> .vm file code.
            // 1. Symbol Tree: tracks identifiers as the parse-tree is built.
            // 2. VM writer -> writes VM code to the .vm file. 

        

        // Tokenizer Tests.
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
        Tokenizer.tokenize(path, output);
    }

    public static void parse(String path, String output)throws IOException {
        Parser.parse(path, output);
    }
}
