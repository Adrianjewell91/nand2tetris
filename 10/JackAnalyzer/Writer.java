package JackAnalyzer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Writer {
    private File file;
    private FileWriter fr = null;
    private BufferedWriter br = null;

    Writer(String f) throws IOException {
        file = new File(f);
        fr = new FileWriter(file);
        br = new BufferedWriter(fr);
    }

    public void writeToken(String line) {
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
