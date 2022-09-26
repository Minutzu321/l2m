<?php
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once("php/CCipher.php");
require_once("php/page/Page.php");

$logged = true;
if(!isset($_COOKIE[getUserCookie()])) {
    $logged=false;
} else {
    if (!isset($_COOKIE[getUUIDCookie()])) {
        $logged=false;
    }else{
        if (!isset($_COOKIE[getPassCookie()])) {
            $logged=false;
        }else{
            if (!isset($_COOKIE[getEmailCookie()])) {
                $logged=false;
            }
        }
    }
}
if(!$logged){
  exit();
}

$header = new Header();
$body = new Body();
$footer = new Footer();
$pagina = new Page("Panou",2,$header,$body,$footer);

$uuid = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]);
$user = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUserCookie()]);
$lalapas = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getPassCookie()]);
$email = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getEmailCookie()]);

$args = array($uuid,$user,$lalapas,$ip);
$raspuns = $pagina->getJServerResponse('checkuser',$args);

if(!$raspuns->status){
  exit();
}

if(isset($_GET['parola'])){
  $args = array("console","parola ".$_GET['parola'],$uuid,$lalapas);
  $l = $pagina->getJServerResponse('rqcmd',$args);
  print $l->mesaj;
  exit();
}

if($raspuns->permlvl < 4){
  print 'Nu ai permisiunea de a accesa panoul '.$raspuns->permlvl;
  exit();
}

if(isset($_POST['qid'])){
    $args = array("console","process uuid ".$_POST['qid']." ".$_POST['qras'],$uuid,$lalapas);
    $l = $pagina->getJServerResponse('rqcmd',$args);
    print $l->mesaj;
    exit();
}

if(isset($_POST['execcmd'])){
  $args = array("console",$_POST['execcmd'],$uuid,$lalapas);
  $l = $pagina->getJServerResponse('rqcmd',$args);
  print $l->mesaj;
  exit();
}

$panou = false;

if(isset($_GET['p'])){
  if($_GET['p'] === 'aplicanti'){
    if($raspuns->permlvl < 999999990){
      print "Nu ai voie sa procesezi cererile";
      exit();
    }
    $body->addContent('<div class="topnav">
    <a href="/">Acasa</a>
    <a href="/panou">Panou</a>
    <a>Aplicanti</a>
  </div>
  <div class="header">
    <h1>River Wolves</h1>
    <h3>Proceseaza cererile</h3>
  </div>');

    $args = array("aplicanti","1",$uuid);
    $l = $pagina->getJServerResponse('rqdata',$args);

    $cont = "";

    foreach($l->ap as $aplicant){
        $jq = json_decode($aplicant);
        $n = ($jq->nume === $jq->gnume) ? $jq->nume : $jq->nume.' / '.$jq->gnume;
        $e = ($jq->email === $jq->gemail) ? $jq->email : $jq->email.' / '.$jq->gemail;
        $cont=$cont.'<div class="pcard">
        <h2>'.$n.'</h2>
        <h3>A aplicat pentru rolul de '.$jq->grad.'</h3>
        <h4>Clasa a '.$jq->clasa.'-a</h4>
        <p>Email: '.$e.'</p>
        <select id="actiune'.$jq->id.'">
        <option value="null">Alege o actiune</option>
        <option value="ACCEPTA">Accepta</option>';
        if($jq->grad !=='Voluntar')$cont=$cont.'<option value="REFUZA-VOLUNTAR">Refuza si trece-l ca Voluntar</option>';
        if($jq->grad !=='Designer')$cont=$cont.'<option value="REFUZA-DESIGNER">Refuza si trece-l ca Designer</option>';
        if($jq->grad !=='Mecanic')$cont=$cont.'<option value="REFUZA-MECANIC">Refuza si trece-l ca Mecanic</option>';
        if($jq->grad !=='Programator')$cont=$cont.'<option value="REFUZA-PROGRAMATOR">Refuza si trece-l ca Programator</option>';
        if($jq->grad !=='Media')$cont=$cont.'<option value="REFUZA-MEDIA">Refuza si trece-l ca Media</option>';
            $cont=$cont.
            '<option value="REFUZA">Refuza</option>
            <option value="BAN">Baneaza</option></select>
        <button class="butonel" name="trimite" onclick="qqwe(\''.$jq->id.'\')">Trimite</button>
      </div>';
    }

    $footer->addContent('<script> function qqwe(id){
        var e = document.getElementById("actiune"+id);
        var r = e.options[e.selectedIndex].value;
        if(r=="null"){
            alert("Nu poti lasa o actiune nula");
        }else{
                  var XHR = new XMLHttpRequest();
                  var FD = new FormData();
                  FD.append("qid",id);
                  FD.append("qras",r);
                  XHR.addEventListener("load", function(event) {
                    alert(event.target.responseText);
                  });
              
                  XHR.open("POST", "panou");
                  XHR.send(FD);
        }
          
      }</script>');

    $body->addContent('<div class="row"><div class="parteadreapta">
    <div class="pcard">
        <h2>Info</h2>
        <p><b>Voluntari: '.$l->voluntari.'</b></p>
        <p><b>Membri: '.$l->membri.'</b></p><hr>
        <p><b>Mecanici: '.$l->mecanici.'</b></p>
        <p><b>Designeri: '.$l->designeri.'</b></p>
        <p><b>Programatori: '.$l->programatori.'</b></p>
        <p><b>Media: '.$l->media.'</b></p>
    </div>
    </div><div class="parteastanga">'.$cont.'</div></div>');
      }else{
        $panou = true;
      }
}else{
  $panou = true;
}

if($panou){
  if($raspuns->permlvl < 999999990){
    $body->addContent('<div class="topnav">
    <a href="/">Acasa</a>
    <a>Panou</a>
  </div>');
  }else{
    $body->addContent('<div class="topnav">
    <a href="/">Acasa</a>
    <a>Panou</a>
    <a href="/panou?p=aplicanti">Aplicanti</a>
  </div>');
  }
$body->addContent('<div class="header">
  <h1>River Wolves</h1>
  <h3>Administreaza site-ul</h3>
</div>

<div class="row">
  <div class="parteadreapta">
      <div class="pcard">
      <h3>Introdu o comanda</h3>
        <form id="cmde">
          <label for="execcmd"><b>Comanda</b></label>
          <input type="text" name="execcmd" id="consid" required  autocomplete="off">
        </form>
      </div>
  </div>
  <div class="parteastanga">
    <div class="pcard">
    </div>
  </div>
</div>
');

$footer->addContent('<script>
window.addEventListener("load", function () {
  function sendData() {
    var XHR = new XMLHttpRequest();

    var FD = new FormData(form);
    XHR.addEventListener("load", function(event) {
      alert(event.target.responseText);
      document.getElementById("consid").value = "";
    });

    XHR.open("POST", "panou");

    XHR.send(FD);
  }

  var form = document.getElementById("cmde");

  form.addEventListener("submit", function (event) {
    event.preventDefault();
    sendData();
  });
});
</script>');

}

$pagina->printPage();
?>