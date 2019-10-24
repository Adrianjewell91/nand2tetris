<?php

class SymbolTree {
    private $map;
    private $counter = 16;

    function __construct()
    {
        $this->map = array(
            "SP" => "0",
            "LCL" => "1",
            "ARG" => "2",
            "THIS" => "3",
            "THAT" => "4",
            "R0" => "0",
            "R1" => "1",
            "R2" => "2",
            "R3" => "3",
            "R4" => "4",
            "R5" => "5",
            "R6" => "6",
            "R7" => "7",
            "R8" => "8",
            "R9" => "9",
            "R10" => "10",
            "R11" => "11",
            "R12" => "12",
            "R13" => "13",
            "R14" => "14",
            "R15" => "15",
            "SCREEN" => "16384" ,
            "KBD" => "24576",
        );
    }

    public function addJump($symbol, $location) {
        $symbol = trim($symbol);
        $this->map[$symbol] = $location;
    }

    public function add($symbol) {
        $symbol = trim($symbol);
        if (is_numeric($symbol) || array_key_exists($symbol, $this->map)) {
            return 0;
        } else {
            $this->map[$symbol] = $this->counter;
            $this->counter +=1;
            return $this->counter - 1;
        }
    }

    public function get($symbol) {
        $symbol = trim($symbol);
        if (array_key_exists($symbol, $this->map)) {
            return $this->map[$symbol];
        } else { return $symbol; }
    }
}
?>