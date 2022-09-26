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
  print 'A aparut o eroare neasteptata :O';
  exit();
}

if($raspuns->permlvl < 4){
  print 'Doar membri pot accesa profilul '.$raspuns->permlvl;
  exit();
}

$target_dir = "inc/imgs/profile/";
$target_file = $target_dir . $uuid .'.'. pathinfo($_FILES["fileToUpload"]["name"], PATHINFO_EXTENSION);;
$uploadOk = 1;
$imageFileType = strtolower(pathinfo($target_file,PATHINFO_EXTENSION));
// Check if image file is a actual image or fake image
if(isset($_POST["supload"])) {
    $check = getimagesize($_FILES["fileToUpload"]["tmp_name"]);
    if($check !== false) {
        $uploadOk = 1;
    } else {
        echo "File is not an image.";
        $uploadOk = 0;
    }
    echo $target_file;
// Check if file already exists
if (file_exists($target_file)) {
    unlink($target_file);
}
// Check file size
if ($_FILES["fileToUpload"]["size"] > 500000) {
    echo "Sorry, your file is too large.";
    $uploadOk = 0;
}
// Allow certain file formats
if($imageFileType != "jpg" && $imageFileType != "png" && $imageFileType != "jpeg"
&& $imageFileType != "gif" ) {
    echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "Sorry, your file was not uploaded.";
// if everything is ok, try to upload file
} else {
    if (move_uploaded_file($_FILES["fileToUpload"]["tmp_name"], $target_file)) {
        echo "The file ". basename( $_FILES["fileToUpload"]["name"]). " has been uploaded.";
    } else {
        echo "Sorry, there was an error uploading your file.";
    }
}
}

$body->addContent('
<div class="topnav">
  <a href="#">Link</a>
</div>
<div class="header">
  <h1>River Wolves</h1>
  <h3>Profilul tau</h3>
  <br>
</div>
<div class="row">
  <div class="parteastanga">');
$body->addContent('
    <div class="pcard">
      <h2>Informatii</h2>
      <p>Nume: '.$user.' </p>
      <p>Email: '.$email.'</p>
    </div>
  </div>
  <div class="parteadreapta">
    <div class="pcard">
      <h2>Cod QR</h2>
      <h5>Scaneaza codul QR pentru a te loga la aplicatia de pe telefon</h5>
      <img src="https://api.qrserver.com/v1/create-qr-code/?data='.$uuid.'&amp;size=150x150">
    </div>
    <div class="pcard">
      <h2>Seteaza imaginea de profil</h2>
      <form action="profil" method="post" enctype="multipart/form-data">
        Select image to upload:
        <input type="file" name="fileToUpload" id="fileToUpload">
        <input type="submit" value="Upload Image" name="supload">
    </form>
    </div>
  </div>
</div>');

$pagina->printPage();
?>