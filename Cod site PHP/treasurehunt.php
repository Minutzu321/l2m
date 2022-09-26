<?php
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once("php/page/Page.php");

$header = new Header();
$body = new Body();
$footer = new Footer();

$pagina = new Page("Treasure Hunt",2,$header,$body,$footer);

if(isset($_POST['log'])){
	$args = array("wzalog",$_POST['log'],'-1');
	$j = $pagina->getJServerResponse('rqdata',$args);
	if($j->status){
		setcookie('wza_ver2', $_POST['log'], time() + 14400);
		if($j->end){
			$ins = "";
		}else{
			$ins = '<div class="pcard">
	      <h3>Introdu raspunsul</h3>
	        <form action="treasurehunt" method="post">
	          <label for="thunt">Introdu raspunsul(fara diacritice)</label>
	          <input type="text" name="thunt" required autocomplete="off">
	        </form>
	    </div>';
		}
		$body->addContent('
	<div class="row">
	  <div class="pcard">
	  <h1>Puncte: '.$j->puncte.'</h1>
	      <h2>Ghicitoare</h3>
	      '.$j->mesaj.'
	  </div>
	    '.$ins.'
	</div>');
	}else{
		$body->addContent('
		<div class="row">
		    <div class="pcard">
		      <h3>Bine ai venit la a Treasurehunt</h3>
		      <h2 style="color: red">Codul nu este valid</h2>
		        <form action="treasurehunt" method="post">
		          <label for="log">Pentru a incepe, introdu codul</label>
		          <input type="text" name="log" required autocomplete="off">
		        </form>
		    </div>
		</div>');
	}
	$pagina->printPage();
	exit();
}

if(isset($_POST['thunt'])){
	$args = array("wza",$_POST['thunt'],'-1',$_COOKIE['wza_ver2']);
	$j = $pagina->getJServerResponse('rqdata',$args);
	if($j->status){
		if(isset($j->eroare)){
			$err = '<h4 style="color: red">'.$j->eroare.'</h4>';
		}else{
			$err = "";
		}
		if($j->end){
			$ins = "";
		}else{
			$ins = '<div class="pcard">
	      <h3>Introdu raspunsul</h3>
	      	'.$err.'
	        <form action="treasurehunt" method="post">
	          <label for="thunt">Introdu raspunsul(fara diacritice)</label>
	          <input type="text" name="thunt" required autocomplete="off">
	        </form>
	    </div>';
		}
		$body->addContent('
	<div class="row">
	  <div class="pcard">
	  <h1>Puncte: '.$j->puncte.'</h1>
	      <h2>Ghicitoare</h3>
	      '.$j->mesaj.'
	  </div>
	    '.$ins.'
	</div>');
	}else{
		$body->addContent('
		<div class="row">
		    <div class="pcard">
		      <h3>Bine ai venit la a Treasurehunt</h3>
		      <h2 style="color: red">A aparut o eroare!</h2>
		    </div>
		</div>');
	}
	$pagina->printPage();
	exit();
}

$cont = "";

if(!isset($_COOKIE['wza_ver2'])){
	$body->addContent('
		<div class="row">
		    <div class="pcard">
		      <h3>Bine ai venit la a Treasurehunt</h3>
		        <form action="treasurehunt" method="post">
		          <label for="log">Pentru a incepe, introdu codul</label>
		          <input type="text" name="log" required autocomplete="off">
		        </form>
		    </div>
		</div>');
}else{
		$args = array("wzalog",$_COOKIE['wza_ver2'],'-1');
	$j = $pagina->getJServerResponse('rqdata',$args);
	if($j->status){
		if($j->end){
			$ins = "";
		}else{
			$ins = '<div class="pcard">
		      <h3>Introdu raspunsul</h3>
		        <form action="treasurehunt" method="post">
		          <label for="thunt">Introdu raspunsul(fara diacritice)</label>
		          <input type="text" name="thunt" required autocomplete="off">
		        </form>
		    </div>';
		}
			$body->addContent('
		<div class="row">
		  <div class="pcard">
		  <h1>Puncte: '.$j->puncte.'</h1>
		      <h2>Ghicitoare</h3>
		      '.$j->mesaj.'
		  </div>
		    '.$ins.'
		</div>');
	}else{
			$body->addContent('
		<div class="row">
		    <div class="pcard">
		      <h3>Bine ai venit la a Treasurehunt</h3>
		        <form action="treasurehunt" method="post">
		          <label for="log">Pentru a incepe, introdu codul</label>
		          <input type="text" name="log" required autocomplete="off">
		        </form>
		    </div>
		</div>');
	}
}

$pagina->printPage();


