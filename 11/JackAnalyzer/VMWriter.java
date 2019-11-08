package JackAnalyzer;

import java.io.IOException;

class VMWriter {
    Writer writer;

    VMWriter(String output) throws IOException {
        writer = new Writer(output);
    }

    public void writePush(String segment, Integer index) {
        if (segment.equals("CONST")) {
            writer.writeToken("push constant " + index);
        }
    }
    
    public void writePop(String segment, Integer index) {
        if (segment.equals("TEMP")) {
            writer.writeToken("pop temp " + index);
        }
    }
    
    public void writeArithmetic(String operator) {
        if (operator.equals("*")) {
            writer.writeToken("call Math.multiply 2");
        } else if (operator.equals("+")) {
            writer.writeToken("add");
        } else if (operator.equals("-")) {
            writer.writeToken("neg");
        }
    }
    
    public void writeLabel() {}
    
    public void writeGoto() {}
    
    public void writeIf() {}
    
    public void writeCall(String name, Integer args) {
        writer.writeToken("call " + name + " " + args);
    }
    
    public void writeFunction(String name, Integer args) {
        writer.writeToken("function " + name + " " + args);
    }
    
    public void writeReturn() {
        writer.writeToken("return");
    }
    
    public void close() {
        writer.close();
    }
    
}