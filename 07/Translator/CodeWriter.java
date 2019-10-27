package Translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {
    private File file;
    private FileWriter fr = null;
    private BufferedWriter br = null;

    CodeWriter(String fileName) throws IOException {
        file = new File(fileName);
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
    }

    public void setFileName(String fileName) throws IOException {
        file = new File(fileName);
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
    }

    public void writeArithmetic(String command) {
        String code = "";
        switch (command) {
            case "add":
                code = "@SP \nD=M \nD=D-1 \nA=D \nD=M  \nA=A-1 \nD=D+M \nM=D \nD=A+1 \n@SP \nM=D \n";
                break;
        }
        writeLine(code);
    }
    
    public void writePushPop(String command, String segment, Integer index) {
        String code = "";
        String value = "";
        if (segment.equals("constant")) value = String.format("@%s \nD=A \n", index);
        
        if (command.equals("C_PUSH")) {
            code = String.format("%s@SP \nA=M \nM=D \nA=A+1 \nD=A \n@SP \nM=D \n", value);
        } else if (command.equals("C_POP")) {
            
        }
        // PUSH - put result of arg1 and index at the place of SP, then
        // increment SP1. 

        // POP - takes the value at SP puts it at segment[index], (constants won't be a segment here).
        // decrement SP1. 

        writeLine(code);
    }

    public void writeLine(String line) {
        String dataWithNewLine = line + System.getProperty("line.separator");
        try {
            br.write(dataWithNewLine);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void close() {
        try {
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
}
