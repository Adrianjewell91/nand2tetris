<?php

class Parser
{
    private $path;
    private $file;
    public $line;
    
    function __construct($path)
    {
        $this->_loadFile($path);
    }
    
    public function hasMoreCommands()
    {
        return $this->line;
    }
    
    public function advance()
    {
        $this->line = fgets($this->file);
        return $this->hasMoreCommands();
    }
    
    public function commandType()
    {
        $this->__trim();
        if (substr($this->line, 0, 2) == "//") {
            return "//";
        }
        
        switch ($this->line[0]) {
            case "@":
                return "A";
            case "M":
                return "C";
            case "D":
                return "C";
            case "A":
                return "C";
            case "(":
                return "L";
        }
        
        if (strpos($this->line, "=") != false || strpos($this->line, ";") != false) {
            return "C";
        }

        return "Empty"; // Or throw Error
    }
    
    public function symbol()
    {
        if ($this->line[0] == "(") {
            return substr($this->line, 1, -2);
        } else {
            return substr($this->line, 1);
        }
    }
    
    public function dest()
    {
        if (strpos($this->line, "=") != false) {
            return preg_split("/=/", $this->line)[0];
        } else {
            return "";
        }
    }
    
    public function comp()
    {
        if (strpos($this->line, "=") != false && strpos($this->line, ";") != false) {
            preg_match('/=-(.*?)-;/', $this->line, $match);
            return $match[1];
        } else if (strpos($this->line, "=") != false) {
            return preg_split("/=/", $this->line)[1];
        } else if (strpos($this->line, ";") != false) {
            return preg_split("/;/", $this->line)[0];
        } else {
            return "";
        }
    }
    
    public function jump()
    {
        if (strpos($this->line, ";") != false) {
            return preg_split("/;/", $this->line)[1];
        } else {
            return "";
        }
    }
    
    private function _loadFile($path)
    {
        $this->path = $path;
        $this->file = fopen("{$this->path}.asm", "r");
        $this->line = fgets($this->file);
    }

    private function __trim() {
        // Could be different 
        $this->line = preg_split("/\s/", ltrim($this->line))[0] . "\n";
    }
}
