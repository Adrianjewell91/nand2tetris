package JackAnalyzer;

import java.io.IOException;

class JackAnalyzer {
    public static void main(String[] args) throws IOException {
        //Tokenizer Tests.
        String[] files = new String[] {
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/ArrayTest/Main.jack"};
        String[] tokenOutputs = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/ArrayTestT.xml"};
        String[] outputs = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/ArrayTest.xml"};
        compile(files, tokenOutputs, outputs);

        files = new String[] {
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Main.jack",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Square.jack",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/SquareGame.jack", };
        tokenOutputs = new String[] { "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareMainT.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareT.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareGameT.xml", };
        outputs = new String[] { "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareMain.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/Square.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareGame.xml", };
        compile(files, tokenOutputs, outputs);
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
