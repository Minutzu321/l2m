<?php
//PARTEA PHP
session_start();
$ip = isset($_SERVER['HTTP_CLIENT_IP'])?$_SERVER['HTTP_CLIENT_IP']:isset($_SERVER['HTTP_X_FORWARDED_FOR'])?$_SERVER['HTTP_X_FORWARDED_FOR']:$_SERVER['REMOTE_ADDR'];
//PARTEA HTML-PHP
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once ("php/page/Page.php");
require_once ("htmls/Register.php");

//HEADER
$header = new Header();

//BODY
$body = new Body();

//FOOTER
$footer = new Footer();

$pagina = new Page("Register",1,$header,$body,$footer);

if(isset($_POST['rbutonel'])) {
    $user = $_POST['ruser'];
    $email = $_POST['remail'];
    $pas = $_POST['rpass'];
    $rpas = $_POST['rrpass'];
        if(!empty($email)&&!empty($pas)&&!empty($user)) {
            if ($rpas === $pas) {
                $args = array($user,$email,$pas,$ip);
                $r = $pagina->getJServerResponse('reg',$args);
                if ($r->status) {
                    require_once("php/CCipher.php");
                    $uuid = $r->uuid;
                    $sec = intval($r->session);
                    setcookie(getUserCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$user), time() + $sec);
                    setcookie(getUUIDCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$uuid), time() + $sec);
                    setcookie(getPassCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$pas), time() + $sec);
                    setcookie(getEmailCookie(), PHP_AES_Cipher::encrypt(getCKey961(),getIV113(),$email), time() + $sec);
                    echo "home";
                    exit();
                } else {
                    // $_SESSION['err'] = "<p class=\"text-danger\">" . $r . "</p>";
                    // header("location:/" . getLoc() . "/register");
                    echo "<p class=\"text-danger\">" . $r->mesaj . "</p>";
                    exit();
                }
            } else {
                // $_SESSION['err'] = passwordsDontMatch();
                // header("location:/" . getLoc() . "/register");
                echo passwordsDontMatch();
                exit();
            }
        }else{
            // $_SESSION['err'] = fieldsCantBeEmpty();
            echo fieldsCantBeEmpty();
            // header("location:/" . getLoc() . "/register");
            exit();
        }
}
$error = "";
if(isset($_SESSION['err'])){
    $error = $_SESSION['err'];
    unset($_SESSION['err']);
    if(strpos($error,'errhopa')){
        $error = offlineCantRegister();
    }
}

$body->addContent(getReg());

$pagina->printPage();