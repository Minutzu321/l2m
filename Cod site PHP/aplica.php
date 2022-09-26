<?php
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once("php/CCipher.php");
require_once("php/page/Page.php");

if(isset($_POST['traplica'])) {
	$header = new Header();
    $body = new Body();
    $footer = new Footer();
    $pagina = new Page("Se proceseaza...",3,$header,$body,$footer);
    $elevmoisil = false;
    if(isset($_POST['elevmoisil'])){
    	$elevmoisil = true;
    }
    $terms = false;
    if(isset($_POST['termeni'])){
    	$terms = true;
    }
	$args = array(PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]),$_POST['numele'],$_POST['contact'],$_POST['clasa'],$_POST['micsec'],$elevmoisil,$terms,$ip);
    $r = $pagina->getJServerResponse('aplica',$args);
    print_r(json_encode($r));
	exit();
}

if(isset($_POST['amtrimis'])) {
    $header = new Header();
    $body = new Body();
    $footer = new Footer();

    $pagina = new Page("Token verify",3,$header,$body,$footer);

    $args = array($_POST['amtrimis'],$ip);
    $r = $pagina->getJServerResponse('gugal',$args);
    
    if($r->status){
      $sec = intval($r->session);
      setcookie(getUserCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$r->user), time() + $sec);
      setcookie(getUUIDCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$r->uuid), time() + $sec);
      setcookie(getPassCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$r->parola), time() + $sec);
      setcookie(getEmailCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$r->email), time() + $sec);
      exit();
    }else{
      print($r->mesaj);
      exit();
    }
    
}

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

function getPDreapta2($pagina){
  return '
  <div class="parteadreapta">
    <div class="pcard">
      <h3>Participarea la activitatile incluse in program</h3>
      <h4>Echipele de robotica inscrise in cadrul BRD FIRST Tech Challenge Romania 2019/2020 isi
iau angajamentul sa fie prezente, sa fie active si sa se implice in conditii optime intr-o serie
de actiuni si activitati:
	<ul><li>Activitati de promovarea a roboticii in comunitatea lor (initiate de ei sau de alte
entitati) la care sunt invitate sa participe*</li>
		<li>Activitati cu scop educativ si de informare puse la dispozitie de catre organizatorul NPE
si anuntate prin diferite canalele de informare email, website, facebook: Sesiuni de
webinar, Sesiuni de training, Conferinte, Schimburi de experienta intre echipe nationale
si internationale, etc.</li>
		<li>Activitati cu rol de antrenament, initiate de organizatorul NPE sau initiate de alte
echipe din orasul/ regiunea geografica (meciuri demo regionale, meciuri amicale inter
liceale)</li>
		<li>Activitati cu miza de calificare (meciuri de calificare la Competitiile Regionale, meciuri
de calificare la Campionatul National)
</li>
		<li>NPE recomanda echipelor sa organizeze si sa ia parte la evenimente in comunitatea lor
– evenimente de tipul “outreach” promovate in cadrul circuitului global FIRST. </li>
		</ul>
	</h4>
    </div>
  </div>
  ';
}

function getPDreapta($pagina){
  return '
  <div class="parteadreapta">
    <div class="pcard">
      <h2>Abilitati necesare?</h2>

      <p><b>Toate abilitatile sunt binevenite</b>, chiar daca sunt tehnice sau non-tehnice. Echipa are nevoie de toate tipurile de abilitati ca sa reuseasca. Este aproape imposibil sa nu avem un job pentru tine, si cel mai probabil te vom invata cateva lucruri noi cat timp ne esti alaturi.
<br><br>
Studentii sunt incurajati sa isi arate orice tip de abilitate, precum vorbitul in public, indemanarea tehnica, creativitatea, energia si multe altele. <i>FIRST Tech Challenge primeste orice student motivat si energic cu sau fara abilitati speciale.</i></p>

    </div>
  </div>
  ';
}

function getPStanga($pagina,$uuid,$logat){
  $args = array("grade","1",$uuid);
  $l = $pagina->getJServerResponse('rqdata',$args);
  $cont = "";
  if(!$l->status){
    $cont=$cont.'<div class="pcard">
      <h2>Ups</h2>
      <p>';
      if(@$l->con){
        $cont=$cont."A aparut o eroare la conexiunea cu serverul. Incearca mai tarziu.";
      }else{
        $cont=$cont.$l->mesaj;
      }
    $cont=$cont.'</p>
    </div>';
  }else{
    if($logat){
      foreach($l->grade[0] as $ob){
        $cob = json_decode($ob);
        if($l->deja){
	        $cont=$cont.'<div class="pcard">
	          <h2>'.$cob->nume.'</h2>
	          <p>'.$cob->descriere.'</p>
	          <button class="butonel" disabled>Deja ai aplicat pentru '.$l->arol.'</button>
          </div>';
    	}else{
	        $cont=$cont.'<div class="pcard">
	          <h2>'.$cob->nume.'</h2>
	          <p>'.$cob->descriere.'</p>
	          <button class="butonel" id="'.$cob->nume.'" onclick="modal(this,\''.$cob->nume.'\',\''.PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$cob->id).'\')">Aplica pentru acest rol</button>
	        </div>';
    	}
      }
    }else{
      foreach($l->grade[0] as $ob){
        $cob = json_decode($ob);
        $cont=$cont.'<div class="pcard">
          <h2>'.$cob->nume.'</h2>
          <p>'.$cob->descriere.'</p>
          <button class="butonel" id="'.$cob->nume.'" onclick="modal(this,\''.$cob->nume.'\',\''.PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$cob->id).'\')">Aplica pentru acest rol</button>
        </div>';
      }
    }
  }
  return '
<div class="parteastanga">
'.$cont.'
</div>';
}

function getModal2(){
  return getModal1("","");
}

function getModal1($nume,$email){
  return '
      <div id="modal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2 id="appentru">Aplica pentru </h2>
    </div>
    <div class="modal-body">
    	<h3>Completeaza campurile pentru a te inscrie</h3>
      <div id="loader" style="display:none;"></div>
      <div id="erru"></div>
    	<form id="fq">
    	<label for="numele"><b>Numele complet</b></label>
    	<input type="text" name="numele" required>
    	<label for="contact"><b>O modalitate de contact(instagram/facebook/email)</b></label>
    	<input type="text" name="contact" required>
    	<label for="clasa"><b>Clasa:</b></label>
    	<select name="clasa">
        	<option value="null">Alege o clasa</option>
        	<option value="VI">a VI-a</option>
    		<option value="VII">a VII-a</option>
		    <option value="VIII">a VIII-a</option>
		    <option value="IX">a IX-a</option>
		    <option value="X">a X-a</option>
		    <option value="XI">a XI-a</option>
  		</select>
      <h3>Sunt elev al Liceului Teoretic Grigore Moisil Tulcea</h3>
		 <label class="switch">
		  <input type="checkbox" name="elevmoisil" value="suntElevDeLaMoisilCeMaiVrei">
		  <span class="slider round"></span>
	   </label>
  		<h3> Am citit Termenii si Conditiile</h3>
    <label class="switch">
      <input type="checkbox" name="termeni" value="chiarDacaNuAmCititCuAdevaratCeNuStiiNuTePoateAfecta">
      <span class="slider round"></span>
     </label>
     <br><br>
    	<button type="submit" class="butonel" name="traplica">Trimite</button>
    	</form>
    	<br>
    </div>
    <div class="modal-footer">
      <h3>Citeste <a href="/info" target="_blank" style="color:aquamarine">Termenii si Conditiile</a></h3>
    </div>
  </div>
</div>';
}

function getSite($pagina,$uuid,$logat,$nume,$email,$membru=false){
	$heat = ' <div class="header">
			    <h1>River Wolves</h1>
			    <h3>Alatura-te pentru a dezvolta o echipa puternica</h3>
			  </div>';
    $mo = $logat ? getModal1($nume,$email) : getModal2();
    return $mo.$pagina->getNavbar($membru,$logat).$heat.'<div class="row">'.getPDreapta($pagina).getPStanga($pagina,$uuid,$logat).getPDreapta2($pagina).'</div>';
}

$header = new Header();
$body = new Body();
$footer = new Footer();

    $footer->addContent('<script>
    	var ms = undefined;
    function modal(btn,txt,micunealtasecreta){
    	ms=micunealtasecreta;
    document.getElementById("appentru").innerHTML = "Aplica pentru rolul de "+txt;
    var modal = document.getElementById(\'modal\');
    var span = document.getElementsByClassName("close")[0];
    modal.style.display = "block";

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
        modal.style.display = "none";
        }
    }
    }
    </script>');
    if(isset($_GET['deschide'])) {
    	$footer->addContent('<script>document.getElementById("'.$_GET['deschide'].'").click();</script>');
    }
        $footer->addContent('<script>
    window.addEventListener("load", function () {
      function sendData() {
        var XHR = new XMLHttpRequest();

        var FD = new FormData(form);
        FD.append("traplica","da");
        FD.append("micsec",ms);
        XHR.addEventListener("load", function(event) {
        	var ob = JSON.parse(event.target.responseText);
          document.getElementById("loader").style.display = "none";
          document.getElementById("erru").innerHTML =ob.mesaj;
          if(ob.out){
          	window.location.href = "/logout?red=aplica";
          }
          if(ob.continua){
            document.getElementById("fq").style.display = "block";
          }
          if(ob.status){
          document.getElementById("erru").style.color = "green";
      		}else{
      			document.getElementById("erru").style.color = "red";
      		}
        });
    
        XHR.open("POST", "aplica");
    
        XHR.send(FD);
      }

      var form = document.getElementById("fq");

      form.addEventListener("submit", function (event) {
        event.preventDefault();
        document.getElementById("loader").style.display = "block";
        document.getElementById("fq").style.display = "none";
        sendData();
      });
    });
    </script>');

if($logged){
	$uuid=PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]);
	$nume=PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUserCookie()]);
	$lalapas = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getPassCookie()]);
	$email=PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getEmailCookie()]);
	$pagina = new Page("Intra in echipa",2,$header,$body,$footer);
	$args = array($uuid,$nume,$lalapas,$ip);
	$raspuns = $pagina->getJServerResponse('checkuser',$args);
	if($raspuns->permlvl < 4){
		$body->addContent(getSite($pagina,$uuid,$logged,$nume,$email));
	}else{
		$body->addContent(getSite($pagina,$uuid,$logged,$nume,$email,true));
	}
}else{
	$uuid = "-1";
	$nume= "-1";
	$nume="-1";
	$pagina = new Page("Intra in echipa",2,$header,$body,$footer);
	$body->addContent(getSite($pagina,$uuid,$logged,$nume,$email));
}



$pagina->printPage();
?>