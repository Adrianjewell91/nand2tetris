package JackAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

class JackTokenizer {
    private Scanner scanner;
    private String line;
    private String token;

    JackTokenizer(String p) throws FileNotFoundException {
        scanner = new Scanner(new File(p));
        if (scanner.hasNextLine())
            _nextLine();
    }

    public Boolean hasMoreTokens() {
        return (line != null && line.length() > 0);
    }

    public String advance() { 
        token = _nextToken();
        if (_shouldGetNextLine()) _nextLine();
        return token;
    }

    private String _nextToken() {
        // This logic is to get the next token, now the fun begins.
        // KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST

        // Check each of these, maybe use regex, 
        // ie. For keyword, check if the first n characters match keyword, if so 
        // substring those n characters and assign token. 
            //             'class' | 'constructor' | 'function' | 'method' | 'field' | 'static' | 'var' |
            // 'int' | 'char' | 'boolean' | 'void' | 'true' | 'false' | 'null' | 'this' | 'let' | 'do' | 'if' | 'else' | 'while' | 'return'
        // For symbol same idea,

        // For int constant: check if first n characters upto 5 comprise digits less than 32768, and the 
        // number is less a than 32768, but I probably don't have to check it for the sake of the project. 

        // For string constant, check if first character == \double_quote and then iterate until second quote.

        // Else= it's an identifier, as long at it doesn't start with a digit, which I probably don't have to check. 

        // 
        token = line.substring(0, 1);
        line = line.substring(1);
        return token;
    }

    private void _nextLine() {
        line = scanner.nextLine();
        line = line.split("//")[0];
        line = line.trim();
        if (_shouldGetNextLine())
            this._nextLine();
        else if (Arrays.asList("/**", "*", "*/").contains(this.line.split(" ")[0]))
            this._nextLine();
    }

    private Boolean _shouldGetNextLine() {
        return line.length() == 0 && scanner.hasNextLine();
    }
}