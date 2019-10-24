<?php
include "parser.php";
include "code.php";
include "symboltree.php";

class Assembler {
    public $parser;
    public $symbols;
    public $encoder;
    public $path;
    public $output;
    
    function __construct($symbolTree, $encoder, $parser, $path, $output) {
        $this->path = "../{$path}/{$output}";
        $this->symbols = new $symbolTree();
        $this->encoder = new $encoder();
        $this->parser = new $parser($this->path);
        $this->output = fopen("../$path/{$output}COMP.hack", "w+");
    }
    
    public function assemble() {
        $this->make();
        $this->map();
        $this->encode();
    }
    
    private function iterate($cb, ...$args) {
        $this->parser = new $this->parser($this->path);
        while ($this->parser->hasMoreCommands()) {
            $cb($args[0]);
            $this->parser->advance();
        }
    }
    
    private function encode() {
        $this->iterate(
            function() {
                $translation = "";
                switch ($this->parser->commandType()) {
                    case "A":
                    $symbol = $this->symbols->get($this->parser->symbol());
                    $translation = "0" . sprintf('%015b',  $symbol);
                    fwrite($this->output, $translation . "\n");
                    break;
                    case "C":
                    $dest = $this->encoder->dest(trim($this->parser->dest()));
                    $comp = $this->encoder->comp(trim($this->parser->comp()));
                    $jump = $this->encoder->jump(trim($this->parser->jump()));
                    $translation = "111" . $comp . $dest . $jump;
                    fwrite($this->output, $translation . "\n");
                    break;
                    case "//":
                    break;
                    default:
                    // Throw error;
                }
            }
        );
    }
    
    private function make() {
        $count = 0;
        $this->iterate(function(&$count){
            $command = $this->parser->commandType();
            if ($command == "L") {
                $this->symbols->addJump($this->parser->symbol(), $count);
            }
            
            if ($command == "A" || $command == "C") {
                $count += 1;
            }
        }, $count);
    }
        
    private function map() {
        $this->iterate(function() {
            $command = $this->parser->commandType();
            if ($command == "A") {
                $this->symbols->add($this->parser->symbol());
            }
        });
    }  
}

?>