<?php
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once("php/CCipher.php");
require_once("php/page/Page.php");

if(isset($_GET['parola'])) {
	if($_GET['parola'] === '--------------'){
		$header = new Header();
	    $body = new Body();
	    $footer = new Footer();
	    $pagina = new Page("Se proceseaza...",3,$header,$body,$footer);
	}else{
		echo("Eroare, parola incorecta");
		exit();
	}
}else{
	echo("Se pare ca ai ajuns pe o pagina privata. WOW");
	exit();
}