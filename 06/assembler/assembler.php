<?php
include "parser.php";
include "code.php";
include "symboltree.php";

$files = array(
    "max" => "Max",
    "pong" => "Pong",
    "rect" => "Rect"
);

assemble("add", "Add");
foreach ($files as $path => $name) {
    assemble($path, $name);
    assemble($path, $name . "L");
}

// Assembles a file.
function assemble($path, $name) {
    $symbols = new SymbolTree();
    $parser = new Parser("../{$path}/$name");
    $count = 0;
    while ($parser->hasMoreCommands()) {
        $command = $parser->commandType();
        if ($command == "L") {
            $symbols->addJump($parser->symbol(), $count);
        }
        
        if ($command == "A" || $command == "C") {
            $count+=1;
        }

        $parser->advance();
    }

    $parser = new Parser("../{$path}/$name");
    while ($parser->hasMoreCommands()) {
        $command = $parser->commandType();
        if ($command == "A") {
            $symbols->add($parser->symbol());
        } 
        $parser->advance();
    }
        
    $parser = new Parser("../{$path}/$name");
    $code = new Code();
    $output = fopen("../$path/{$name}COMP.hack", "w+");
    
    while ($parser->hasMoreCommands()) {
        $translation = "";
        switch ($parser->commandType()) {
            case "A":
                $symbol = $symbols->get($parser->symbol());
                $translation = "0" . sprintf('%015b',  $symbol);
                fwrite($output, $translation . "\n");
                break;
            case "C":
                $dest = $code->dest(trim($parser->dest()));
                $comp = $code->comp(trim($parser->comp()));
                $jump = $code->jump(trim($parser->jump()));
                $translation = "111" . $comp . $dest . $jump; 
                fwrite($output, $translation . "\n");
                break;
            case "//":
                break;
            default:
                // Throw error;
        }
        
        $parser->advance();
    }
}

// Tests the files assembled with this project.
include "test.php";

?>