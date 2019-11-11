package JackAnalyzer;

import java.io.IOException;

class VMWriter {
    Writer writer;

    VMWriter(String output) throws IOException {
        writer = new Writer(output);
    }

    public void writePush(String segment, Integer index) {
        writer.writeToken(String.format("push %s %s", segment.toLowerCase(), index));;
    }
    
    public void writePop(String segment, Integer index) {
        writer.writeToken(String.format("pop %s %s", segment.toLowerCase(), index));
    }
    
    public void writeArithmetic(String operator) {
        if (operator.equals("*")) {
            writer.writeToken("call Math.multiply 2");
        } else if (operator.equals("+")) {
            writer.writeToken("add");
        } else if (operator.equals("-")) {
            writer.writeToken("sub");
        } else if (operator.equals("NEG")) {
            writer.writeToken("neg");
        } else if (operator.equals("~")) {
            writer.writeToken("not");
        } else if (operator.equals("&gt;")) {
            writer.writeToken("gt");
        } else if (operator.equals("&lt;")) {
            writer.writeToken("lt");
        } else if (operator.equals("&amp;")) {
            writer.writeToken("and");
        } else if (operator.equals("=")) {
            writer.writeToken("eq");
        } else if (operator.equals("/")) {
            writer.writeToken("call Math.divide 2");
        } else if (operator.equals("|")) {
            writer.writeToken("or");
        } 
        
    }
    
    public void writeLabel(String label) {
        writer.writeToken("label " + label);
    }
    
    public void writeIf(String label) {
        writer.writeToken("if-goto " + label);
    }
    
    public void writeGoto(String label) {
        writer.writeToken("goto " + label);
    }
    
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