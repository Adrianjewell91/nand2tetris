<?php
// How that assemblage works without translation, 
// How can I build symbol translation component. 

// Basically, when I find a symbol, then put it in the tree,


include "parser.php";
include "code.php";
include "symboltree.php";

$files = array(
    // "add" => "Add",
    "max" => "Max",
    "pong" => "Pong",
    "rect" => "Rect"
);

// The test suite:
// assemble("pong", "Pong");
assemble("add", "Add");
foreach ($files as $path => $name) {
    assemble($path, $name);
    assemble($path, $name . "L");
}

function assemble($path, $name) {
    $symbols = new SymbolTree();
    $parser = new Parser("../{$path}/$name");
    $count = 0;
    while ($parser->hasMoreCommands()) {
        $command = $parser->commandType();
        if ($command == "L") {
            $symbol = $parser->symbol();
            echo $symbol . "\n";
            $symbols->addJump($symbol, $count);
            echo $symbols->get($symbol) ."\n";
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
            $symbol = $parser->symbol();
            echo $symbol . "\n";
            $symbols->add($symbol);
            echo $symbols->get($symbol) ."\n";
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
            // echo $parser->line;
            $symbol = $symbols->get($parser->symbol());
            // echo $symbol ."\n";
            $translation = "0" . sprintf('%015b',  $symbol);
            fwrite($output, $translation . "\n");
            // echo $translation;
            break;
            case "L":
            break;
            case "C":
            echo $parser->line;
            $dest = $code->dest(trim($parser->dest()));
            
            $comp = $code->comp(trim($parser->comp()));
            
            $jump = $code->jump(trim($parser->jump()));
            
            $translation = "111" . $comp . $dest . $jump; 
            fwrite($output, $translation . "\n");
            // echo $translation;
            break;
            case "//":
            break;
            default:
            // Throw error;
        }
        
        $parser->advance();
    }
    
    // fclose($output);
}

?>