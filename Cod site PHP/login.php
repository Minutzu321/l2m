<?php
//PARTEA PHP
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
//PARTEA HTML-PHP
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once ("php/page/Page.php");
require_once ("htmls/Login.php");

//HEADER
$header = new Header();

//BODY
$body = new Body();

//FOOTER
$footer = new Footer();

$pagina = new Page("Login",1,$header,$body,$footer);

if(isset($_POST['lbutonel'])) {
    $user = $_POST['luser'];
    $pas = $_POST['lpass'];
    if (!empty($user) && !empty($pas)) {
        $args = array($user,$pas,$ip);
        $r = $pagina->getJServerResponse('log',$args);
        if ($r->status) {
            require_once("php/CCipher.php");
            $uuid = $r->uuid;
            $sec = intval($r->session);
            $ruser = $r->nume;
            $remail = $r->email;
            setcookie(getUserCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$ruser), time() + $sec);
            setcookie(getUUIDCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$uuid), time() + $sec);
            setcookie(getPassCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$pas), time() + $sec);
            setcookie(getEmailCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$remail), time() + $sec);
            echo "home";
            exit();
        } else {
            // $_SESSION['err'] = "<p class=\"text-danger\">" . $r->mesaj . "</p>";
            // header("location:/" . getLoc() . "/login");
            echo "<p class=\"text-danger\">" . $r->mesaj . "</p>";
            exit();
        }
    } else {
        // $_SESSION['err'] = "<p class=\"text-danger\">Fields can't be empty</p>";
        // header("location:/" . getLoc() . "/login");
        echo "<p class=\"text-danger\">Fields can't be empty</p>";
        exit();
    }
}
$error = "";
if(isset($_SESSION['err'])){
    $error = $_SESSION['err'];
    unset($_SESSION['err']);
    if(strpos($error,'errhopa')){
        $error = "<p class=\"text-danger\">Server offline. You can't log in</p>";
    }
}
$body->addContent(getLogin($error));

$pagina->printPage();