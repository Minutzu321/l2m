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

if (!isset($_SESSION['err'])) {
  $pagina = new Page("Informatii",2,$header,$body,$footer);
  $body->addContent('<div class="header">
  <h1>River Wolves</h1>
  <h3>Informatii</h3>
</div>
<div class="row">
  <div class="parteastanga">
    <div class="pcard">
      <h2>Termeni si conditii</h2>
      <ul>
      	<li><h3>Informatiile pe care le primim de la contul google reprezinta email-ul si numele de utilizator. (Acest sistem este folosit pentru a confirma identitatea persoanei)</h3></li>
      	<li><h3>Conturile vor fi folosite pentru a tine evidenta prezentelor, a sedintelor, rapoartelor de lucru, etc.</h3></li>
      	<li><h3>Datele adunate vor arata persoanele cele mai implicate, selectate pentru a alcatui echipa principala.</h3></li>
      	<li><h3>Informatiile contului nu vor fi distribuite. Acestea sunt criptate si folosite de un algoritm pentru a face o echipa cat mai eficienta.</h3></li>
      	<li><h3>Bani stransi din reclame, sponsorizari sau donatii vor fi folositi pentru imbunatatirea robotului, a laboratorului si a altor instrumente.</h3></li>
      	<li><h3>Va ruga sa aveti un comportament care se incadreaza in limita bunului simt fata de alti membri din echipa sau mentori.</h3></li>
      	<li><h3>Datele persoanelor inscrise in program (elevi, mentori, voluntari) sub forma de nume,
foto, video vor fi colectate si transmise in scopuri organizatorice partenerilor din zona
de structuri invatamant, structuri ONG, transport, cazare, alti parteneri ai programului. </h3></li>
	<li><h3>Pentru Sezonul 2019/2020 toti membrii echipelor acceptate in program (elevi, mentori,
voluntari implicati in evenimentele regionale/ nationale) vor semna acordul de folosire a
imaginii rezultate din materialele foto/ video suprinse cu ocazia meciurilor amicale,
competiilor oficiale si evenimentelor conexe.
</h3></li>
      </ul>
    </div>
  </div>
  <div class="parteadreapta">
  <div class="pcard">
      <h2>Valorile FIRST</h2>
      <ul>
       <li><h3>Ceea ce descoperim și învățăm este mai important decât ceea ce câștigam.</h3></li>
       <li><h3>Cultivăm spiritul competiției prieteneșți.</h3></li>
       <li><h3>Ne respectăm reciproc și celebrăm diversitatea.</h3></li>
       <li><h3>Ne comportăm întotdeauna cu bunăvoință și empatie unii fata de alții.</h3></li>
       <li><h3>Noi îi inspirăm si pe ceilalti să adopte aceste valori.</h3></li>
       <li><h3>Dăm dovadă de profesionalism și de cooperare în tot ceea ce facem.</h3></li>
      </ul>
    </div>
  </div>
  </div>
');
  $pagina->printPage();
	exit();
}
$pagina = new Page("Info",1,$header,$body,$footer);

if (isset($_SESSION['err'])) {
    $error = $_SESSION['err'];
    unset($_SESSION['err']);
    if (strpos($error, 'errhopa')) {
        $error = "<p class=\"text-danger\">Server offline. Nu poti verifica codul</p>";
    }
    $c = '<div id="tot">
    <div class="card mb-3">
      <h3 class="card-header">Oops..</h3>
      <div class="card-body">
      '.$error.'
      <a href="register" class="card-link">Inapoi la inregistrare</a>
      </div></div></div>';
    $body->addContent($c);
}

$pagina->printPage();
