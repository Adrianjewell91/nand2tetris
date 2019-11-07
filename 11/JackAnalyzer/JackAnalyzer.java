package JackAnalyzer;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class JackAnalyzer {
    public static void main(String[] args) throws IOException {
        // 1. Write code to parse only the .jack files. 
        compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/Average");
        // compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/ComplexArrays");
        // compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/ConvertToBin");
        // compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/Pong");
        // compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/Seven");
        // compileDirectory("/Users/adrianjewell/Documents/Repos/nand2tetris/projects/11/Square");

        // 2. Write the VM code writer. 
            // Involves the symbol tree and the VM writer. 
    }

    // public static void compile(String[] files, String[] tokenOutputs, String[] outputs) throws IOException {
    //     for (int i = 0; i < files.length; i++) {
    //         tokenize(files[i], tokenOutputs[i]);
    //         parse(tokenOutputs[i], outputs[i]);
    //     }
    // }

    public static void tokenize(String path, String output) throws IOException {
        Tokenizer.tokenize(path, output);
    }

    public static void parse(String path, String output)throws IOException {
        Parser.parse(path, output);
    }

    public static void compileDirectory(String folder_path) {
        Path folderPath = Paths.get(folder_path);
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(folderPath)) {
            for (Path path : directoryStream) {
                String p = path.toString();
                if (p.split("\\.")[1].equals("jack")) {
                    tokenize(p, p.split("\\.")[0] + "T.xml");
                    parse(p.split("\\.")[0] + "T.xml", p.split("\\.")[0] + ".xml");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error reading files");
            ex.printStackTrace();
        }
    }
}
