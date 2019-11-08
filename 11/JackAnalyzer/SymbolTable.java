package JackAnalyzer;

import java.util.HashMap;
import java.util.Map;

class SymbolTable {
    Map<String,String> classNameToType = new HashMap<String,String>(); 
    Map<String,String> classNameToKind = new HashMap<String,String>(); 
    Map<String,Integer> classNameToIndex = new HashMap<String,Integer>(); 
    Integer classStatics = 0;
    Integer classFields = 0;

    Map<String,String> subroutineNameToType = new HashMap<String,String>(); 
    Map<String,String> subroutineNameToKind = new HashMap<String,String>(); 
    Map<String,Integer> subroutineNameToIndex = new HashMap<String,Integer>(); 
    Integer subroutineVars = 0;
    Integer subroutineArgs = 0;

    public void startSubroutine () { 
        subroutineNameToType = new HashMap<String, String>();
        subroutineNameToKind = new HashMap<String, String>();
        subroutineNameToIndex = new HashMap<String, Integer>();
        subroutineVars = 0;
        subroutineArgs = 0;
    }
    
    public void Define(String name, String type, String kind) { 
        if (kind.equals("STATIC") || kind.equals("FIELD")) {
        } else {
            subroutineNameToKind.put(name, kind);
            subroutineNameToType.put(name, type);
            if (kind.equals("ARG")) {
                subroutineNameToIndex.put(name, subroutineArgs++);
            } else {
                subroutineNameToIndex.put(name, subroutineVars++);
            }
        }
    }

    public Integer VarCount(String kind) { 
        if (kind.equals("VAR")) {
            return subroutineVars;
        } else if (kind.equals("ARG")) {
            return subroutineArgs;
        } else if (kind.equals("STATIC")) {
            return classStatics;
        } else if (kind.equals("FIELD")) {
            return classFields;
        }
        
        return -1;
    }

    public String KindOf(String key) { 
        if (subroutineNameToIndex.containsKey(key)) {
            return subroutineNameToKind.get(key);
        } else if (classNameToIndex.containsKey(key)) {
            return classNameToKind.get(key); 
        } else { return "NONE"; }
    }

    public String TypeOf(String key) { 
        if (subroutineNameToIndex.containsKey(key)) {
            return subroutineNameToType.get(key);
        } else if (classNameToIndex.containsKey(key)) {
            return classNameToType.get(key); 
        }  else { return "NONE"; }
    }

    public Integer IndexOf(String key) { 
        if (subroutineNameToIndex.containsKey(key)) {
            return subroutineNameToIndex.get(key);
        } else if (classNameToIndex.containsKey(key)) {
            return classNameToIndex.get(key); 
        } else { return -1; }
    }
}