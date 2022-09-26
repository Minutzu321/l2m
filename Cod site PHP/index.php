<?php
//PARTEA PHP
require_once ("php/page/Page.php");
session_start();

$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
//PARTEA HTML-PHP
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once ("htmls/Home.php");

//HEADER
$header = new Header();

//BODY
$body = new Body();

//FOOTER
$footer = new Footer();

$header->addContent('<script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
<script>
  (adsbygoogle = window.adsbygoogle || []).push({
    google_ad_client: "ca-pub-1147191292192615",
    enable_page_level_ads: true
  });
</script>');

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
if(!$logged) {
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
    $pagina = new Page("Despre noi",1,$header,$body,$footer);
    $pagina->printPage();
    exit();
}

$pagina = new Page("Despre noi",2,$header,$body,$footer);
$body->addContent(getSite($pagina,"-1",$logged));
$footer->addContent('<script>
            var acc = document.getElementsByClassName("accordion");
            var i;
            for (i = 0; i < acc.length; i++) {
                acc[i].addEventListener("click", function() {
                this.classList.toggle("active");
                var panel = this.nextElementSibling;
                if (panel.style.display === "block") {
                    panel.style.display = "none";
                } else {
                    panel.style.display = "block";
                }
                });
            }
        </script>');
        $body->addContent(getLoginModal());
        $header->addContent('<meta name="google-signin-client_id" content="-------">');
        $footer->addContent("<script>function succes(googleUser) {
            var xhr = new XMLHttpRequest();
            xhr.open('POST', 'aplica');
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onload = function() {
              window.location.href = '/';
            };
            xhr.send('amtrimis=' + googleUser.getAuthResponse().id_token);
          }
          function fail(error) {
            console.log(error);
          }
          function renderButton() {
            gapi.signin2.render('googlelog', {
              'scope': 'profile email',
              'width': 240,
              'height': 50,
              'longtitle': true,
              'theme': 'dark',
              'onsuccess': succes,
              'onfailure': fail
            });
          }</script><script src=\"https://apis.google.com/js/platform.js?onload=renderButton\" async defer></script>");
        $footer->addContent('<script>
        function modal(){
        var modal = document.getElementById(\'modal\');
        var span = document.getElementsByClassName("close")[0];
        modal.style.display = "block";
    
          span.onclick = function() {
              modal.style.display = "none";
          }
        }
        </script>');
$pagina->printPage();

}else{
    require_once("php/CCipher.php");
    $uuid = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]);
    $user = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUserCookie()]);
    $lalapas = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getPassCookie()]);
    $email = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getEmailCookie()]);

    $pagina = new Page("Despre noi",1,$header,$body,$footer);

    $args = array($uuid,$user,$lalapas,$ip);
    $raspuns = $pagina->getJServerResponse('checkuser',$args);


    $connected = true;
    // if ($raspuns == 'errhopa') {
    //     $connected = false;
    // }

    if($connected) {
        // if(!$raspuns->status){
        //     $_SESSION['err'] = "<p class=\"t8 ext-danger\">" . $raspuns->mesaj . "</p>";
        //     header("location:/" . getLoc() . '/logout');
        //     exit();
        // }
        if ($raspuns->tuser) {
            if (isset($_POST['cbutonel'])) {
                $cod = $_POST['ccode'];
                $args = array($uuid, $cod, $lalapas, $ip);
                $r = $pagina->getJServerResponse('checkcod', $args);
                if ($r->status) {
                    $sec = intval($r->session);
                    setcookie(getUserCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $user), time() + $sec);
                    setcookie(getUUIDCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $uuid), time() + $sec);
                    setcookie(getPassCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $lalapas), time() + $sec);
                    setcookie(getEmailCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $email), time() + $sec);
                    header("location:/" . getLoc());
                    exit();
                } else {
                    $_SESSION['err'] = "<p class=\"text-danger\">" . $r->mesaj . "</p>";
                    $sec = intval($r->session);
                    setcookie(getUserCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $user), time() + $sec);
                    setcookie(getUUIDCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $uuid), time() + $sec);
                    setcookie(getPassCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $lalapas), time() + $sec);
                    setcookie(getEmailCookie(), PHP_AES_Cipher::encrypt(getCKey961(), getIV113(), $email), time() + $sec);
                    header("location:/" . getLoc())."/info";
                    exit();
                }
            }
            $error = "";
            if (isset($_SESSION['err'])) {
                $error = $_SESSION['err'];
                unset($_SESSION['err']);
                if (strpos($error, 'errhopa')) {
                    $error = "<p class=\"text-danger\">Server offline. Nu poti verifica codul</p>";
                }
            }
            $body->addContent(getCheck($error));
            $pagina = new Page("Despre noi",1,$header,$body,$footer);
            $pagina->printPage();
        } else {
            $pagina = new Page("Despre noi",2,$header,$body,$footer);
            if(isset($_POST['ales'])){
                $args = array("console","process chestie ".PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()])." ".$_POST['ales'],$uuid,$lalapas);
                $l = $pagina->getJServerResponse('rqcmd',$args);
                print $l->mesaj;
                exit();
            }
            $footer->addContent('<script>
            var acc = document.getElementsByClassName("accordion");
            var i;
            for (i = 0; i < acc.length; i++) {
                acc[i].addEventListener("click", function() {
                this.classList.toggle("active");
                var panel = this.nextElementSibling;
                if (panel.style.display === "block") {
                    panel.style.display = "none";
                } else {
                    panel.style.display = "block";
                }
                });
            }
            </script>');
            if($raspuns->permlvl < 4){
            	$body->addContent(getSite($pagina,PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]),$logged));
        	}else{
        		$body->addContent(getSite($pagina,PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]),$logged,true));
        	}
            if($raspuns->pending){
                $chestie = json_decode($raspuns->chestie);
                $body->addContent('<div style="display: block; content: ""; clear: both; display: table" class="modal">
                <div class="modal-content">
                <div class="modal-header">
                    <h2>Inca poti deveni un membru!</h2>
                </div>
                <div class="modal-body">
                    <h3>'.$chestie->mesaj.'</h3>
                    <button class="butonel" onclick="manguste(\'ACCEPTA\')" style="width: 100%">'.$chestie->acc.'</button>
                    <br>
                    <button class="rbutonel" onclick="manguste(\'REFUZA\')" style="width: 100%">'.$chestie->ref.'</button>
                </div>
                <div class="modal-footer">
                    <h3>Citeste <a href="/info" target="_blank" style="color:aquamarine">Termenii si Conditiile</a></h3>
                </div>
                </div>
            </div>');
            $footer->addContent("<script>function manguste(a) {
                var xhr = new XMLHttpRequest();
                xhr.open('POST', '/');
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function() {
                  window.location.href = '/';
                };
                xhr.send('ales=' + a);
              }</script>");
            }
            $pagina->printPage();
        }
    }else{
        $body->addContent(getMess());
        $pagina = new Page("Despre noi",1,$header,$body,$footer);
        $pagina->printPage();
    }
}