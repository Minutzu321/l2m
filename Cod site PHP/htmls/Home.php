<?php
function getCheck($error){
    return '
<div id="tot">
    <div class="card mb-3">
    <h3 class="card-header">Email verification</h3>
    <div class="card-body">
    ' . $error . '
        <form id=\'index\' action=\'\' method=\'post\' accept-charset=\'UTF-8\'>
            <div class="form-group">
                <label>Insert the code</label>
                <input type="text" class="form-control" placeholder="Code" name="ccode" autocomplete="off">
            </div>
            <button type="submit" class="btn btn-primary btn-lg btn-block" name="cbutonel">Submit</button>
          </form>
    </div>
  </div>
</div>
  ';
}

function getPDreapta($pagina){
  return '
  <div class="parteadreapta">
  <div class="pcard">
      <h2>Despre noi</h2>
      <img src="https://ro049.com/inc/imgs/logods.png" style="width: 100%; height: 100%">
      <p>Echipa River Wolves a fost creata acum 4 ani avand ca mentori pe domul <b>Niculae Dobrescu</b> (Coordonatorul cercului de Electronica si Astronomie) si pe domnul <b>Niculae Aldea</b> (Profesor de fizica la Liceul Teoretic Grigore Moisil), de atunci a participat la fiecare sezon FIRST Tech Challange. Echipa a fost destramata deoarece cativa membri valorosi au implinit 19 ani si au plecat la facultate. Incepand cu Sezonul 4(2019-2020), vrem sa reconstruim echipa, adunand elevi ai <b>Liceului Teoretic Grigore Moisil</b></p>
    </div>
    <div class="pcard">
      <h3>Program</h3>

    <button class="accordion">9 Septembrie - Start inscrierilor</button>
    <div class="panel">
      <p>Pe 9 septembrie vom incepe sa adunam membri pentru echipa River Wolves</p>
    </div>
    </div>
    <div class="pcard">
      <h3>Voluntarii echipei</h3>
      <h4>
<ul><li>Fiecare echipa isi poate atrage o serie de voluntari pentru sustinerea anumitor activitati
tehnice sau non-tehnice, pentru a creste calitatea si performanta echipei.</li>
<li>Toti voluntarii trebuie sa–si insuseasca valorile FIRST si sa ajute la raspandirea
lor. Voluntarii nu fac parte din echipa de baza, ei au rol de suport pentru echipei
de baza.</li>
<li>Cine pot fi voluntari: parinti, profesori din liceu sau din facultate, elevi din liceu,
studenti, mediul de business local.</li></ul></h4>
    </div>
    <div class="pcard">
      <h3>Pregatirea echipei</h3>
      <h4><ul><li>Impartirea potrivita a rolurilor in echipa (tehnice si non-tehnice ) in functie de abilitatile
membrilor si coordonarea echipei – sunt cerinte obligatorii care tin de atibutiile
mentorului din cadrul liceului</li>
<li>Functionalitatea in parametrii competitionali a robotului (conform probe si sarcini
descrise in GAME MANUAL furnizat in fiecare sezon competitional) – este o cerinta care
tine de intreaga echipa, inclusiv de mentor</li>
<li>Realizarea unui caiet tehnic al echipei conform cerintelor obligatorii FIRST- Engineering
Notebook – conform GAME MANUAL & Manualul Mentorului, este o cerinta care tine
de intreaga echipa inclusiv de mentor</li>
<li>Realizarea unei identitati vizuale a echipei costand in nume, logo, slogan</li>
<li>Pregatirea de materiale de prezentare a echipei (print, video, materiale promotionale,
mascote)</li>
<li>Actualizarea permanenta a suporturilor de informare (facebook, instagram, youtube,
etc.)</li>
<li>Folosirea corecta a logo-ului de competitie, logo parteneri si organizator in materialele
de promovare / prezentare a echipei*</li></ul></h4>
      </div>
  </div>
  ';
}

function getLoginModal(){
  return '
      <div id="modal" class="modal">
  <div class="modal-content">
    <div class="modal-header">
      <span class="close">&times;</span>
      <h2 id="appentru">Logheaza-te</h2>
    </div>
    <div id="loader" style="display:none;"></div>
    <div class="modal-body" id="odyb">
    <h3>Foloseste contul Google pentru a utiliza site-ul</h3>
    <div id="googlelog"></div>
    <br>
    </div>
    <div class="modal-footer">
      <h3>Citeste <a href="/info" target="_blank" style="color:aquamarine">Termenii si Conditiile</a></h3>
    </div>
  </div>
</div>';
}

function getPCHTML($ob){

}

function getPStanga($pagina,$uuid="-1"){
  $args = array("home_posts","1",$uuid);
  $l = $pagina->getJServerResponse('rqdata',$args);
  $cont = '';
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
  
  }
  return "<div class=\"parteastanga\"><div class=\"pcard\">
      <h2>FIRST TECH CHALLANGE</h2>
      <b>1. MISIUNE:</b>
Asociatia Natie Prin Educatie, partenerul FIRST pentru Romania, este o forta activa in domeniul educatiei STEM (Stiinta, Tehnlogie, Inginerie, Matematica), care are ca scop sa creeze o legatura puternica intre liceele si universitatile cu profil STEM, si mediul de afaceri din Romania.<br><br>
<b>2. VIZIUNE:</b>
Sustinem un nou mod de a invata stiintele teoretice: A invata prin metoda descoperirii, prin activitati practice si prin lucrul in grup. Acest nou mod de a invata genereaza un mai mare interes al elevilor pentru scoala si curricula teoretica.
Investim in formarea elevilor de azi, adica cei care vor fi specialistii de maine, ajutam la pregatirea elevilor pentru meseriile celei de-a patra revolutii industriale - ERA DIGITALA.<br><br>
<b>3. VALORILE FIRST</b>
Conceptul proiectului FIRST Tech Challenge presupune mai mult decat construirea unui robot. El include munca în echipă, lucrul pe proiect, spiritul si abilitatile antreprenoriale, initiativa si rezolvarea de probleme, voluntariatul. Se propun activitati pentru dezvoltarea atat a competentelor tehnice cat si a celor non-tehnice (soft skills).
Valorile FIRST stau la baza tuturor activitatilor proiectului. Însușindu-si aceste valori, elevii învață importanța comunicării și a cooperării intre echipe, aceste abilități fiindu-le folositoare și în viața de adult, indiferent de domeniul în care vor lucra.</div>
<div class=\"pcard\">
      <h2>Participanti</h2>
      <ul>
      <li><b>Varsta:</b> In program sunt acceptati elevi de liceu clasele IX- XII, si/sau elevi cu varsta implinita, intre 12 si 18 ani</li>
      <li><b>Numar membri:</b></li>
      	<ul>
      		<li><b>Echipa de baza:</b> intre 3 si 6 elevi
Din experienta de pana acum, am constatat ca in echipele de robotica exista un nucleu de elevi care deruleaza majoritatea sarcinilor tehnice si non-tehnice, iar acesti elevi sunt coordonati de 1 mentor.
Daca nu se poate forma o echipa extinsa, se poate participa in program cu o echipa de baza.</li>
			<li><b>Echipa extinsa:</b> 15 elevi
In situatiile in care mai multi elevi isi doresc sa se inscrie in proiect se are in vedere ca numarul acestora sa fie maxim 15, si pot fi coordonati de maxim 3 mentori.</li>
			<li><b>Voluntari:</b>
O serie de elevi, care nu pot fi inclusi in echipa extinsa de maxim 15 membri, pot face parte din echipa de voluntari a echipei de robotica. Voluntarii elevi pot ajuta la indeplinirea anumitor sarcini – stabilite in grupul de lucru al echipei (elevi & mentori coordonatori).</li>
      	</ul>
      <li><b>Sarcini de acoperit de membrii echipei:</b>
      		<ul>
      		<li><b>Sarcini TEHNICE</b> (programare, proiectare 3D, constructie robot, driving robot pe teren, strategie de joc)</li>
      		<li><b>Sarcini NON TEHNICE</b> ( fluidizarea lucrului in echipa - project management si
micro-management; alocarea membrilor pe micro-grupe de lucru, stabilirea de
obiective si timp pe fiecare etapa de lucru; <b>business plan si strategie; marketing
si comunicare- materialele de comunicare ale echipei; activitati de promovare
(outreach)</b> – inscopul strangerii de fonduri si in scopul raspandirii valorilor FIRST
in comunitate)
</li>
			<li><b>Elaborarea Engineering Notebook</b> - o echipa formata din membrii tehnici si nontehnici
</li>
      		</ul>
      </li>
      <li>Activitatile elevilor trebuie sa urmareasca respectarea normelor de siguranta (Safety
FIRST), a respectului reciproc intre participanti.</li>
      </ul>
      </div>
</div>";
//   return '
// <div class="parteastanga">
// '.$cont.'
// </div>';
}

function getSite($pagina, $uuid, $logged, $membru=false){
$hear='<div class="header">
			    <h1>River Wolves</h1>
			  </div>
			  <div class="row">
			  <div class="parteastanga">
			  <div class="pcard">
		      <h1 style="text-align: center;">FIRST Tech Challange #Sezonul 4</h1>
		      <img src="/inc/imgs/doarUnAfis.png" style="width: 100%; height: 100%">
<h2>E mult mai mult decat construirea unui robot</h2>
<p>*Descriere pe scurt*</p>
<h4>Conceptul proiectului FIRST Tech Challenge include munca în echipă, lucrul pe proiect, spiritul si abilitatile antreprenoriale, initiativa si rezolvarea de probleme, etc. Se propun activitati pentru dezvoltarea atat a competentelor tehnice cat si a celor non-tehnice (soft skills), in acest fel, doar lenea sau alte activitati va mai impiedica sa participati!
	<ul>
		<li>Sarcini TEHNICE (programare, proiectare 3D, constructie robot, driving robot pe teren, strategie de joc)</li>
		<li>Sarcini NON TEHNICE ( fluidizarea lucrului in echipa - project management si micro-management; alocarea membrilor pe micro-grupe de lucru, stabilirea de obiective si timp pe fiecare etapa de lucru; business plan si strategie; marketing si comunicare- materialele de comunicare ale echipei; activitati de promovare (outreach) – in scopul strangerii de fonduri si in scopul raspandirii valorilor FIRST in comunitate)</li>
		<li>Elaborarea Engineering Notebook - Echipa trebuie sa realizeze un caiet cu toate detaliile robotului</li>
	</ul>
</h4>
<button class="butonel" onclick="window.location.replace(\'/aplica\')">Vezi rolurile membrilor</button>
	      </div></div>';
	      return $pagina->getNavbar($membru,$logged).$hear.getPDreapta($pagina).getPStanga($pagina,$uuid).'</div>';
}

function getMess(){
return '<div id="tot">
    <div class="card mb-3">
        <h3 class="card-header">Server offline</h3>
            <div class="card-body">
                <form id=\'index\' action=\'\' method=\'post\' accept-charset=\'UTF-8\'>
                    <button type="submit" class="btn btn-primary btn-lg btn-block">Retry</button>
                </form>
            </div>
    </div>
</div>';
}
