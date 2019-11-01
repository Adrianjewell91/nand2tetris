package JackAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class JackTokenizer {
    private Scanner scanner; 
    public String line;
    
    JackTokenizer(String p) throws FileNotFoundException {
        // For now, I want to print every single character to a nextline, then start grouping characters.
        scanner = new Scanner(new File(p));
        if (scanner.hasNextLine()) _nextLine();
    }

    public Boolean hasMoreTokens() {
        return (line != null && line.length() > 0);
    }

    public String advance() { 
        String s = line.split("")[0];
        line = line.substring(1);
        if (line.length() == 0 && scanner.hasNextLine()) _nextLine();
        return s;
    }

    private void _nextLine() {
        line = scanner.nextLine();   
        line = line.split("//")[0];
        line = line.trim();
        if (line.equals("") && scanner.hasNextLine()) this._nextLine();
        else if (Arrays.asList("/**","*","*/").contains(this.line.split(" ")[0])) this._nextLine();
    }
}