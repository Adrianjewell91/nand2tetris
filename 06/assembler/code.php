<?php

class Code
{
    private $destinations;
    private $comparisons;
    private $jumps;

    function __construct()
    {
        $this->destinations = array(
            "" => "000",
            "A"  => "100",
            "D" => "010",
            "M" => "001",
            "AD" => "110",
            "DA" => "110",
            "AM" => "101",
            "MA" => "101",
            "DM" => "011",
            "MD" => "011",
            "AMD" => "111",
            "ADM" => "111",
            "MDA" => "111",
            "MAD" => "111",
            "DAM" => "111",
            "DMA" => "111"
        );

        $this->comparisons = array(
            "" => "0000000",
            "0" => "0101010",
            "1" => "0111111",
            "-1" => "0111010",
            "D" => "0001100",
            "A" => "0110000",
            "M" => "1110000",
            "!D" => "0001101",
            "!A" => "0110001",
            "!M"  => "1110001",
            "-D" => "0001111",
            "-A"  => "0110011",
            "-M" => "1110011",
            "D+1" => "0011111",
            "A+1" => "0110111",
            "M+1" => "1110111",
            "D-1" => "0001110",
            "A-1" => "0110010",
            "M-1" => "1110010",
            "D+A" => "0000010",
            "D+M" => "1000010",
            "D-A" => "0010011",
            "D-M" => "1010011",
            "A-D" => "0000111",
            "M-D" => "1000111",
            "D&A" => "0000000",
            "D&M" => "1000000",
            "D|A" => "0010101",
            "D|M" => "1010101",
        );

        $this->jumps = array(
            "" => "000",
            "JGT" => "001",
            "JEQ" => "010",
            "JGE" => "011",
            "JLT" => "100",
            "JNE" => "101",
            "JLE" => "110",
            "JMP" => "111",
        );
    }

    public function dest($d)
    {
        return $this->destinations[$d];
        //return binary destination mapping.
    }

    public function comp($c)
    {
        return $this->comparisons[$c];
        //return binary comparison mapping.
    }

    public function jump($j)
    {
        return $this->jumps[$j];
        //return binary jump mapping
    }
}
