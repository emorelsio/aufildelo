<?php
$json = file_get_contents('mission2.json', true);

$parsed_json = json_decode($json, true);

$jsonFinal = [];

for($i=0; $i<count($parsed_json); $i++){
    $jsonFinal[$i]["commune"] = $parsed_json[$i]["fields"]["commune"];
    $jsonFinal[$i]["identifian"] = $parsed_json[$i]["fields"]["identifian"];
    $jsonFinal[$i]["elem_patri"] = $parsed_json[$i]["fields"]["elem_patri"];
    if(empty($parsed_json[$i]["fields"]["elem_princ"])){
        $jsonFinal[$i]["elem_princ"] = "Pas d'element principale";
    }else{
        $jsonFinal[$i]["elem_princ"] = $parsed_json[$i]["fields"]["elem_princ"];
    }
    

}

$encode_json = json_encode($jsonFinal);

var_dump($encode_json);   

?>
