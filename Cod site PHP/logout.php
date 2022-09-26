<?php
require_once("php/config.php");
require_once("php/SConnection.php");
require_once("php/CCipher.php");

function getJServerResponse($cmd,$args){
    $s = new \stdClass();
    $s->cmd = $cmd;
    $s->args = $args;
    $r = new JavaConnection(json_encode($s));
    return json_decode($r->getResponse());
}

$sent = false;

if(isset($_COOKIE[getUUIDCookie()])) {
    if (!$sent) {
        $uuid = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUUIDCookie()]);
        $args = array(0, $uuid);
        getJServerResponse('lout', $args);
        $sent=true;
    }
    unset($_COOKIE[getUUIDCookie()]);
    setcookie(getUUIDCookie(), null, time() - 3600);
}
if(isset($_COOKIE[getUserCookie()])) {
    if (!$sent) {
        $user = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getUserCookie()]);
        $args = array(1, $user);
        getJServerResponse('lout', $args);
        $sent = true;
    }
    unset($_COOKIE[getUserCookie()]);
    setcookie(getUserCookie(), null, time() - 3600);
}
if(isset($_COOKIE[getEmailCookie()])) {
    if (!$sent) {
        $email = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getEmailCookie()]);
        $args = array(2, $email);
        getJServerResponse('lout', $args);
        $sent = true;
    }
    unset($_COOKIE[getEmailCookie()]);
    setcookie(getEmailCookie(), null, time() - 3600);
}
if(isset($_COOKIE[getPassCookie()])) {
    if (!$sent) {
        $pass = PHP_AES_Cipher::decrypt(getCKey961(), $_COOKIE[getPassCookie()]);
        $args = array(2, $pass);
        getJServerResponse('lout', $args);
    }
    unset($_COOKIE[getPassCookie()]);
    setcookie(getPassCookie(), null, time() - 3600);
}

if(isset($_GET["red"])){
    print '<script>
    function signOut() {
      var auth2 = gapi.auth2.getAuthInstance();
      auth2.signOut().then(function () {
        window.location.href = "/'.$_GET["red"].'";
      });
    }
  </script>';
}else{
    print '<head>
    <meta name="google-signin-client_id" content="--------------------">
            </head>
            <script src="https://apis.google.com/js/platform.js"></script>
            <body>
            <script>
    function signOut() {
      var au = gapi.auth2.getAuthInstance();
      au.signOut().then(function () {
        window.location.href = "/";
      });
    }
    signOut();
  </script></body>';
}
exit();
