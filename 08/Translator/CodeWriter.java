package Translator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import static java.util.Map.entry;

public class CodeWriter {
    private String fileName;
    private File file;
    private FileWriter fr = null;
    private BufferedWriter br = null;
    private Integer enumerator = 0;
    private Integer returnEnumerator = 0;
    private Map<String, String> map = Map.ofEntries(entry("local", "LCL"), entry("argument", "ARG"),
            entry("this", "THIS"), entry("that", "THAT"), entry("temp", "R5"), entry("pointer", "R3"));

    CodeWriter(String f, String name) throws IOException {
        fileName = name;
        file = new File(f);
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
        enumerator = 0;
    }

    public void setFileName(String fileName) throws IOException {
        this.fileName = fileName;
    }

    public void writeBootstrapCode() {
        writeLine("@256 \nD=A \n@SP \nM=D\n");
        writeCall("Sys.init", 0);
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
        String code = "";
        if (command.equals("C_PUSH")) {
            code = _formatPUSH(segment, index);
        } else if (command.equals("C_POP")) {
            code = _formatPOP(segment, index);
        }

        writeLine(code);
    }

    public void writeLabel(String label) {
        writeLine("(" + label + ")" + "\n");
    }

    public void writeGoto(String label) {
        writeLine(String.format("@%s \n0;JMP \n", label));
    }

    public void writeIfGoto(String label) {
        writeLine(String.format("@SP \nM=M-1 \nA=M \nD=M \n@%s \nD;JNE \n", label));
    }

    public void writeFunction(String name, Integer arguments) {
        String code = String.format("(%s)\n", name);
        for (int i = 0; i < arguments; i++) {
            code += _formatPUSH("constant", 0);
        }

        writeLine(code);
    }

    public void writeReturn() {
        // The code.
        // // *ARG = pop() // pop the value into the register to which arg points.
        // @SP
        // M=M-1
        // A=M
        // D=M
        // @ARG
        // A=M
        // M=D
        // // SP = ARG+1
        // @ARG
        // D=M+1
        // @SP
        // M=D
        // FRAME = LCL 
        // // THAT = *(FRAME-1)
        // @LCL
        // M=M-1
        // A=M
        // D=M
        // @THAT
        // M=D

        // // THIS = *(FRAME-2)
        // @LCL
        // M=M-1
        // A=M
        // D=M
        // @THIS
        // M=D

        // // ARG = *(FRAME-3)
        // @LCL
        // M=M-1
        // A=M
        // D=M
        // @ARG
        // M=D
        // // LCL = *(FRAME-4)
        // @LCL
        // D=M-1
        // @R13
        // M=D
        // A=D
        // D=M
        // @LCL
        // M=D
        // // RET = *(FRAME-5)
        // @R13
        // A=M-1
        // A=M // set the next instruction as the RAM[FRAME-5]
        // 0;JMP
        // // goto RET
////    FRAME = LCL 
//@LCL 
//D=M
//@R13
//M=D // Stored Frame in R13 
////    RET = *(FRAME-5) 
//@5 
//A=D-A 
//D=M
//@R14 
//M=D // Store *(Frame-5) in R14 
////    *ARG = pop()
//@SP
//M=M-1
//A=M
//D=M
//@ARG
//A=M
//M=D
////    SP = ARG+1
//@ARG
//D=M+1
//@SP
//M=D
////    THAT = *(FRAME-1)
//@R13 
//AM=M-1 
//D=M 
//@THAT 
//M=D 
////    THIS = *(FRAME-2)
//@R13 
//AM=M-1 
//D=M 
//@THIS 
//M=D 
////    ARG = *(FRAME-3)
//@R13 
//AM=M-1 
//D=M 
//@ARG 
//M=D
////    LCL = *(FRAME-4)
//@R13 
//AM=M-1 
//D=M 
//@LCL 
//M=D
////    goto RET
//@R14 
//A=M
//0;JMP
        String code = "//    FRAME = LCL \n@LCL \nD=M\n@R13\nM=D // Stored Frame in R13 \n//    RET = *(FRAME-5) \n@5 \nA=D-A \nD=M\n@R14 \nM=D // Store *(Frame-5) in R14 \n//    *ARG = pop()\n@SP\nM=M-1\nA=M\nD=M\n@ARG\nA=M\nM=D\n//    SP = ARG+1\n@ARG\nD=M+1\n@SP\nM=D\n//    THAT = *(FRAME-1)\n@R13 \nAM=M-1 \nD=M \n@THAT \nM=D \n//    THIS = *(FRAME-2)\n@R13 \nAM=M-1 \nD=M \n@THIS \nM=D \n//    ARG = *(FRAME-3)\n@R13 \nAM=M-1 \nD=M \n@ARG \nM=D\n//    LCL = *(FRAME-4)\n@R13 \nAM=M-1 \nD=M \n@LCL \nM=D\n//    goto RET\n@R14 \nA=M\n0;JMP\n";
        // the old one: String code = "//*ARG = pop() // pop the value into the register to which arg points. \n@SP \nM=M-1  \nA=M   \nD=M  \n@ARG \nA=M \nM=D \n//    SP = ARG+1\n@ARG \nD=M+1 \n@SP \nM=D \n//    FRAME = LCL\n//    THAT = *(FRAME-1)\n@LCL \nM=M-1\nA=M\nD=M \n@THAT \nM=D\n//    THIS = *(FRAME-2)\n@LCL \nM=M-1\nA=M\nD=M \n@THIS \nM=D\n//    ARG = *(FRAME-3)\n@LCL \nM=M-1\nA=M\nD=M \n@ARG \nM=D\n//    LCL = *(FRAME-4)\n@LCL \nD=M-1 \n@R13 \nM=D \nA=D  \nD=M  \n@LCL \nM=D\n//    RET = *(FRAME-5)\n@R13 \nA=M-1 \nA=M\n0;JMP\n//    goto RET\n";
        writeLine(code);
    }

    public void writeCall(String functionName, Integer numArgs) {
        String code = "";
        // push return-address, the line number down below.
        code += String.format("@RETURN_ADDRESS%s \nD=A \n@SP \nA=M \nM=D \n@SP \nM=M+1 \n", returnEnumerator);
        // push LCL
        code += _writeCallHelper("LCL");
        // push ARG
        code += _writeCallHelper("ARG");
        // push THIS
        code += _writeCallHelper("THIS");
        // push THAT
        code += _writeCallHelper("THAT");
        // ARG = SP-numArgs-5
        code += String.format("@SP \nD=M \n@%s \nD=D-A \n@5 \nD=D-A\n@ARG \nM=D \n", numArgs);
        // LCL = SP
        code += "@SP \nD=M \n@LCL \nM=D \n";
        // goto functionName
        code += String.format("@%s \n0;JMP \n", functionName);
        // (return-address) - a label
        code += String.format("(RETURN_ADDRESS%s)\n", returnEnumerator);
        returnEnumerator++;

        writeLine(code);
    }

    private String _writeCallHelper(String label) {
        return String.format("@%s \nD=M \n@SP \nA=M \nM=D \n@SP \nM=M+1 \n", label);
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

    private String _formatPOP(String segment, Integer index) {
        String loadValue = "";
        if (segment.equals("temp") || segment.equals("pointer")) {
            loadValue = String.format("@%s \nD=A \n@%s \nD=D+A \n", map.get(segment), index);
        } else if (segment.equals("static")) {
            loadValue = String.format("@%s \nD=A \n", fileName + "." + index);
        } else {
            loadValue = String.format("@%s \nD=M \n@%s \nD=D+A\n", map.get(segment), index);
        }
        return String.format("%s \n@R14 \nM=D \n@SP \nM=M-1 \nA=M \nD=M \n@R14 \nA=M \n M=D \n", loadValue);
    }

    private String _formatPUSH(String segment, Integer index) {
        String loadValue = "";
        if (segment.equals("constant")) {
            loadValue = String.format("@%s \nD=A \n", index);
        } else if (segment.equals("static")) {
            loadValue = String.format("@%s \nD=M \n", fileName + "." + index);
        } else if (segment.equals("temp") || segment.equals("pointer")) {
            loadValue = String.format("@%s \nD=A \n@%s \nA=D+A \nD=M \n", map.get(segment), index);
        } else {
            loadValue = String.format("@%s \nD=M \n@%s \nA=D+A \nD=M \n", map.get(segment), index);
        }

        return String.format("%s@SP \nA=M \nM=D \n@SP \nM=M+1 \n", loadValue);
    }
}
