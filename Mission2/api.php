<?php

header('Content-Type: application/json');
include("db_connect.php");

$departement = "";
$commune = "";
$point_interet = "";

if ($_GET){
    if ($_GET['dep']){
        $departement = $_GET['dep'];
    }
    if (isset($_GET['commune'])){
        $commune = $_GET['commune'];
    }
    if (isset($_GET['interet'])){
        $point_interet = $_GET['interet'];
    }
       
}

function requestCommune($departement)
{
    $resultat = [];
    $bdd = connect();

    $sql = "SELECT DISTINCT commune FROM recherche WHERE identifiant LIKE '$departement%' ORDER BY commune";
    //var_dump($sql);
    $state = $bdd->query($sql);
    $i = 0;
    while($row = $state->fetch()) {
        $resultat[$i] = (array)$row;
        $i++;
    }
print_r(json_encode($resultat));
header('Content-Type: application/json');
return json_encode($resultat, JSON_PRETTY_PRINT);
}



function requestInteret($departement, $commune, $type)
{
    $resultat = [];
    $bdd = connect();

    if($type!="Autre"){
    $sql = "SELECT DISTINCT identifiant, commune, elem_patri, elem_princ FROM recherche WHERE identifiant LIKE '$departement%'  and commune LIKE '$commune' and elem_patri LIKE '$type%' ORDER BY elem_patri";
    }else {
    $sql = "SELECT DISTINCT  identifiant, commune, elem_patri, elem_princ FROM recherche WHERE identifiant LIKE '$departement%'  and commune LIKE '$commune' and elem_patri NOT LIKE 'Pont%' and elem_patri NOT LIKE 'Viaduc%' and elem_patri NOT LIKE 'Passerelle%' ORDER BY elem_patri";
    }
    //var_dump($sql);
    $state = $bdd->query($sql);
    $i = 0;
    while($row = $state->fetch()) {
        $resultat[$i] = (array)$row;
        $i++;
    }
    print_r(json_encode($resultat));
header('Content-Type: application/json');
return json_encode($resultat, JSON_PRETTY_PRINT);
}

if ($departement!="" && $commune=="" && $point_interet==""){
    $communes = requestCommune($departement);
} else{
    $interets = requestInteret($departement, $commune, $point_interet);
}

//$communes = requestCommune(77);

//$interets = requestInteret(75,"Paris","Pont");

