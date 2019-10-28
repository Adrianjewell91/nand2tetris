package Translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeWriter {
    private String fileName;
    private File file;
    private FileWriter fr = null;
    private BufferedWriter br = null;
    private Integer enumerator = 0;

    CodeWriter(String f, String name) throws IOException {
        fileName = name;
        file = new File(f);
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
        enumerator = 0;
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
            code = "@SP \nA=M-1 \nD=M  \nA=A-1 \nD=D+M \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "sub":
            code = "@SP \nA=M-1 \nD=M  \nA=A-1 \nD=M-D \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "neg":
            code = "@SP \nA=M-1 \nD=-M \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "and":
            code = "@SP \nA=M-1 \nD=M  \nA=A-1 \nD=D&M \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "or":
            code = "@SP \nA=M-1 \nD=M  \nA=A-1 \nD=D|M \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "not":
            code = "@SP \nA=M-1 \nD=!M \nM=D \nD=A+1 \n@SP \nM=D \n";
            break;
        case "gt":
            code = String.format(
                    "@SP \nA=M-1 \nD=M \nA=A-1 \nD=M-D \n@TRUE%1$s \nD;JGT \n@SP \nA=M-1 \nA=A-1 \nM=0 \n@END%1$s \n0;JMP \n(TRUE%1$s) \n@SP \nA=M-1 \nA=A-1 \nM=-1 \n(END%1$s)\n@SP \nD=M-1\nM=D\n",
                    enumerator);
            enumerator++;
            break;
        case "lt":
            code = String.format(
                    "@SP \nA=M-1 \nD=M \nA=A-1 \nD=M-D \n@TRUE%1$s \nD;JLT \n@SP \nA=M-1 \nA=A-1 \nM=0 \n@END%1$s \n0;JMP \n(TRUE%1$s) \n@SP \nA=M-1 \nA=A-1 \nM=-1 \n(END%1$s)\n@SP \nD=M-1\nM=D\n",
                    enumerator);
            enumerator++;
            break;
        case "eq":
            code = String.format(
                    "@SP \nA=M-1 \nD=M \nA=A-1 \nD=M-D \n@TRUE%1$s \nD;JEQ \n@SP \nA=M-1 \nA=A-1 \nM=0 \n@END%1$s \n0;JMP \n(TRUE%1$s) \n@SP \nA=M-1 \nA=A-1 \nM=-1 \n(END%1$s)\n@SP \nD=M-1\nM=D\n",
                    enumerator);
            enumerator++;
            break;
        }
        writeLine(code);

    }

    public void writePushPop(String command, String segment, Integer index) {
        String loadValue = "";
        String code = "";
        Map<String, String> map = new HashMap<String, String>();
        map.put("local", "LCL");
        map.put("argument", "ARG");
        map.put("this", "THIS");
        map.put("that", "THAT");
        map.put("temp", "R5");
        map.put("pointer", "R3");

        if (command.equals("C_PUSH")) {

            if (segment.equals("constant")) {
                loadValue = String.format("@%s \nD=A \n", index);
            } else if (segment.equals("static")) {
                loadValue = String.format("@%s \nD=M \n", fileName + "." + index);
            } else if (segment.equals("temp") || segment.equals("pointer")) {
                loadValue = String.format("@%s \nD=A \n@%s \nA=D+A \nD=M \n", map.get(segment), index);
            } else {
                loadValue = String.format("@%s \nD=M \n@%s \nA=D+A \nD=M \n", map.get(segment), index);
            }

            code = String.format("%s@SP \nA=M \nM=D \n@SP \nM=M+1 \n", loadValue);
        } else if (command.equals("C_POP")) {

            if (segment.equals("temp") || segment.equals("pointer")) {
                code = String.format(
                        "@%s \nD=A \n@%s \nD=D+A \n@SP \nA=M\nM=D\nA=A-1 \nD=M\nA=A+1\nA=M\nM=D \n@SP\nM=M-1 \n",
                        map.get(segment), index);

            } else if (segment.equals("static")) {
                code = String.format(
                        "@%s \nD=A \n@SP \nA=M\nM=D\nA=A-1 \nD=M\nA=A+1\nA=M\nM=D \n@SP\nM=M-1 \n",
                        fileName + "." + index);
            } else {
                code = String.format(
                        "@%s \nD=M \n@%s \nD=D+A\n@SP \nA=M\nM=D\nA=A-1 \nD=M\nA=A+1\nA=M\nM=D \n@SP\nM=M-1 \n",
                        map.get(segment), index);
            }

            // code = "";
        }

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
