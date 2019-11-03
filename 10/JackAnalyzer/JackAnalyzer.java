package JackAnalyzer;

import java.io.IOException;

class JackAnalyzer {
    private static JackTokenizer tokenizer;
    private static Writer writer;
    
    public static void main(String[] args) throws IOException {
        //Tokenizer Tests.
        String[] files = new String[] {
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/ArrayTest/Main.jack"};
        String[] outputs = new String[] {"/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/ArrayTestT.xml"};
        compile(files, outputs);

        files = new String[] {
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Main.jack",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/Square.jack",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Square/SquareGame.jack", };
        outputs = new String[] { "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareMainT.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareT.xml",
                "/Users/adrianjewell/Desktop/nand2tetris/projects/10/Test/SquareGameT.xml", };
        compile(files, outputs);
    }

    public static void compile(String[] files, String[] outputs) throws IOException {
        for (int i = 0; i < files.length; i++) {
            writer = new Writer(outputs[i]);
            tokenizer = new JackTokenizer(files[i]);
            // Compilation engine code goes here.
            writer.writeToken("<tokens>");
            translate();
            writer.writeToken("</tokens>");
            writer.close();
        }
    }

    public static void translate() throws IOException {
        while (tokenizer.hasMoreTokens()) {
            tokenizer.advance();
            writer.writeToken(tokenizer.toXML());
        }
    }
}
