package JackAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class JackTokenizer {
    private Scanner scanner;
    private String line;
    private String token;
    private String tokenType;

    JackTokenizer(String p) throws FileNotFoundException {
        scanner = new Scanner(new File(p));
        if (scanner.hasNextLine())
            _nextLine();
    }

    public Boolean hasMoreTokens() {
        return (line != null && line.length() > 0);
    }

    public String getTokenType() {
        return tokenType;
    }
    public String advance() {
        token = _nextToken();
        if (_shouldGetNextLine())
            _nextLine();
        return token;
    }

    private String _nextToken() {
        // KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST
        Pattern keyword = Pattern.compile("^class|^constructor|^function|^method|^field|^static|^var|^int|^char|^boolean|^void|^true|^false|^null|^this|^let|^do|^if|^else|^while|^return");
        Matcher keywordMatcher = keyword.matcher(line);
        List<String> symbols = Arrays.asList("{","}","(",")","[","]",".",",",";","+","-","*","/","&","|","<",">","=","~");
    

        if (keywordMatcher.find()) {
            token = line.substring(0, keywordMatcher.end());
            tokenType = "KEYWORD";
            line = line.substring(keywordMatcher.end());
        } else if (symbols.contains(line.substring(0,1))) {
            token = line.substring(0, 1);
            tokenType = "SYMBOL";
            line = line.substring(1);
        } else if (parseIntConstant() > 0) {
            Integer i = parseIntConstant();
            token = line.substring(0, i);
            tokenType = "INT_CONST";
            line = line.substring(i);
        } else {
            token = line.substring(0, 1);
            line = line.substring(1);
        }
        // For symbol same idea,

        // For int constant: check if first n characters upto 5 comprise digits less than 32768, and the 
        // number is less a than 32768, but I probably don't have to check it for the sake of the project. 

        // For string constant, check if first character == \double_quote and then iterate until second quote.

        // Else= it's an identifier, as long at it doesn't start with a digit, which I probably don't have to check. 

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

    private Integer parseIntConstant() {
        for (int i = 5; i > 0; i--) {
            try {
                String s = line.substring(0,i);
                if (Integer.parseInt(s) < 32768) return i;
            } catch(Exception e) {

            } finally {}
        }

        return 0;
    }
}