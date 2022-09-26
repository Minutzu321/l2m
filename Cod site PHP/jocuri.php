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

$pagina = new Page("Jocuri",2,$header,$body,$footer);

if(isset($_POST['joc'])){
	$args = array("joc",$_POST['joc'],$uuid);
	$j = $pagina->getJServerResponse('rqdata',$args);
	$arr = json_decode($j->mesaj);
}else{
	$args = array("joc","random",$uuid);
	$j = $pagina->getJServerResponse('rqdata',$args);
	$arr = json_decode($j->mesaj);
}


$cont = "";



// print_r(json_decode($arr[0])->nume);

foreach($arr as $ob){
  $cob = json_decode($ob);
  $cont=$cont.'<div class="pcard">
	          <h2>'.$cob->nume.'</h2>
	          <p>'.$cob->desfasurarea_jocului.'</p>
          </div>';
}

$body->addContent('<div class="row"><div class="parteadreapta">      <div class="pcard">
      <h3>Introdu o comanda</h3>
        <form action="jocuri" method="post">
          <label for="joc">Cauta un joc dupa numarul sau(Ex: 4. va da ca rezultat jocul cu numarul 4), materiale si alte cuvinte cheie</label>
          <input type="text" name="joc" required autocomplete="off">
        </form>
      </div></div><div class="parteastanga">'.$cont."</div></div>");

$pagina->printPage();
