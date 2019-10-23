<?php
$files = array(
    "max" => "Max",
    "pong" => "Pong",
    "rect" => "Rect"
);

echo check("add", "Add") . "\n";
foreach ($files as $path => $name) {
    echo check($path, $name) . "\n";
    echo check($path, $name . "L") . "\n";
}

function check($path, $name) {
    $one = fopen("../$path/{$name}.hack", "r");
    $two = fopen("../$path/{$name}COMP.hack", "r");
    $handle1 = fgets($one);
    $handle2 = fgets($two);
    
    while ($handle1 !== false || $handle2 !== false) {
        if ($handle1 != $handle2) { return 0; }
        $handle1 = fgets($one);
        $handle2 = fgets($two);
    }

    return 1;
}

?>