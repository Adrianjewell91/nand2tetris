package JackAnalyzer;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {
    // name ,type, kind, number (of it's kind).
    Map<String,String> classNameToType = new HashMap<String,String>(); 
    Map<String,String> classNameToKind = new HashMap<String,String>(); 
    Map<String,Integer> classNameToNumber = new HashMap<String,Integer>(); 
    Integer classStatics = 0;
    Integer classFields = 0;

    Map<String,String> subroutineNameToType = new HashMap<String,String>(); 
    Map<String,String> subroutineNameToKind = new HashMap<String,String>(); 
    Map<String,Integer> subroutineNameToNumber = new HashMap<String,Integer>(); 
    Integer subroutineVars = 0;
    Integer subroutineArgs = 0;

    public void startSubroutine () { 
        // Clear subroutine hashtables.
        subroutineNameToType = new HashMap<String, String>();
        subroutineNameToKind = new HashMap<String, String>();
        subroutineNameToNumber = new HashMap<String, Integer>();
        subroutineVars = 0;
        subroutineArgs = 0;
        // actually should try, then return 1 for true, 0 for fail.
    }
    
    public String Define(String name, String type, String kind) { 
        // insert
        if (kind.equals("STATIC")) {

        } else {

        }
        return ""; }

    // Key into hashtable accordingly.
    public String VarCount() { 
        // if the subroutine has it then get it,
        // else get it from class cope. 
        
        return ""; 
    }
    public String KindOf() { 
        // 
        return ""; 
    }
    public String TypeOf() { 
        //
        return ""; 
    }
    public String IndexOf() { 
        // 
        return ""; 
    }
}