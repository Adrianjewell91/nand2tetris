package JackAnalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Tokenizer {
    private Scanner scanner;
    private String line;
    private String token;
    private String tokenType;
    // Put all the patterns and maps into their own classes for readability:
    // A Mapper Class, perhaps
    private final Pattern keyword = Pattern.compile("^class|^constructor|^function|^method|^field|^static|^var|^int|^char|^boolean|^void|^true|^false|^null|^this|^let|^do|^if|^else|^while|^return");
    private final List<String> symbols = Arrays.asList("{","}","(",")","[","]",".",",",";","+","-","*","/","&","|","<",">","=","~");
    private final Map<String, String> xmlFormatter = new HashMap<String, String> () {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        {
            put("<", "&lt;");
            put(">", "&gt;");
            put("&","&amp;");
            put("\"", "&quot;");
        }
    };
    private Map<String, String> tokenTypeFormatter = new HashMap<String, String> () {
        /**
         *
         */
        private static final long serialVersionUID = 1L;
        {
            //MAP the constants to a map the same reason we did it with REDUX actions.
            put("KEYWORD", "keyword");
            put("INT_CONST", "integerConstant");
            put("STRING_CONST","stringConstant");
            put("SYMBOL", "symbol");
            put("IDENTIFIER", "identifier");
        }
    };

    Tokenizer(String p) throws FileNotFoundException {
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

    public String toXML() {
        if (Arrays.asList("<",">","&","\"").contains(token)) {
            return String.format("<symbol> %s </symbol>", xmlFormatter.get(token));
        } else {
            return String.format("<%1s> %2$s </%1$s>", tokenTypeFormatter.get(tokenType), token);
        }
    }
    
    private String _nextToken() {
        line = line.trim();
        switch (_analyzeCharacter()) {
            // Map the integers for readability:
            case 0:
            Matcher keywordMatcher = keyword.matcher(line);
            keywordMatcher.find();
            //Could refactor these into individual methods:
            token = line.substring(0, keywordMatcher.end());
            tokenType = "KEYWORD";
            line = line.substring(keywordMatcher.end());
            break;
            case 1:
            token = line.substring(0, 1);
            tokenType = "SYMBOL";
            line = line.substring(1);
            break;
            case 2:
            int endQuote = line.indexOf("\"", 1);
            token = line.substring(1, endQuote);
            tokenType = "STRING_CONST";
            line = line.substring(endQuote + 1);
            break;
            case 3:
            Integer i = _parseIntConstant();
            token = line.substring(0, i);
            tokenType = "INT_CONST";
            line = line.substring(i);
            break;
            case -1:
            token = "";
            tokenType = "IDENTIFIER";
            while (_analyzeCharacter() != 1) {
                if (line.substring(0, 1).equals(" ")) {
                    line = line.substring(1);
                    return token;
                } else {
                    token += line.substring(0, 1);
                    line = line.substring(1);
                }
            }
            break;
        }
        return token;
    }

    private void _nextLine() {
        line = scanner.nextLine();
        line = line.split("//")[0];
        line = line.trim();
        if (_shouldGetNextLine())
            this._nextLine();
        //This too:
        else if (Arrays.asList("/**", "*", "*/").contains(this.line.split(" ")[0]))
            this._nextLine();
    }

    private Integer _analyzeCharacter() {
        Matcher keywordMatcher = keyword.matcher(line);
        if (keywordMatcher.find()) {
            // Map these for readability to a map file.
            return 0; // KEYWORD
        } else if (symbols.contains(line.substring(0,1))) {
            return 1; // SYMBOL
        } else if (line.substring(0,1).equals("\"")) {
            return 2; // STRING CONSTANT
        } else if (_parseIntConstant() > 0) {
            return 3; // INT CONSTANT
        } else {
            return - 1; // NONE OF THE ABOVE;
        }
    }

    private Boolean _shouldGetNextLine() {
        return line.length() == 0 && scanner.hasNextLine();
    }

    private Integer _parseIntConstant() {
        for (int i = 5; i > 0; i--) {
            try {
                String s = line.substring(0,i);
                // Set 32768 to a MAX_INT variable.
                if (Integer.parseInt(s) < 32768) return i;
            } catch(Exception e) {

            } finally {}
        }

        return 0;
    }

    // Put line.substring(0,1) into it's own function nextChar()
}