<?php

class Header
{
    public function __construct()
    {
        $this->contents = array();
    }

    public function setPage($page){
        $this->page = $page;
    }

    public function addContent($content){
        array_push($this->contents,$content);
    }

    public function printHeader(){
        switch ($this->page->tip){
            case 1:
                $this->printHeader1();
                break;
            case 2:
                $this->printHeader2();
                break;
            default:
                break;
        }
        foreach ($this->contents as $nume => $cod){
            echo $cod;
        }
        echo "    </head>
    <body>
    ";
    }

    private function printHeader1(){
?>
<!DOCTYPE html>
<html lang="ro">
    <head>
    	<!-- Google Tag Manager -->
		<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
		new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
		j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
		'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
		})(window,document,'script','dataLayer','GTM-WP2SB4B');</script>
		<!-- End Google Tag Manager -->
        <title><?php echo $this->page->nume?></title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="author" content="Minutz">
        <link href="<?php echo $this->page->autoversion('inc/imgs/favicon.ico'); ?>" rel="shortcut icon">
        <link href="<?php echo $this->page->autoversion('inc/css/style.css'); ?>" rel="stylesheet">
<?php
    }

    private function printHeader2(){
?>
<!DOCTYPE html>
<html lang="ro">
    <head>
        <title><?php echo $this->page->nume?></title>
        <meta charset="utf-8">
        <script async src="https://pagead2.googlesyndication.com/pagead/js/adsbygoogle.js"></script>
        <meta name="author" content="Minutz">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="<?php echo $this->page->autoversion('inc/imgs/favicon.ico'); ?>" rel="shortcut icon">
        <link href="<?php echo $this->page->autoversion('inc/css/style2.css'); ?>" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
        <meta name="keywords" content="tulcea, robotica, robotics, liceul teoretic grigore moisil, echipa de robotica, ltgm, lgm, ltgm robotica tulcea, lgm robotica, ftc, first tech challange, river wolves">
        <meta name="propeller" content="4a4c7c176f9ed33650d5003734274f4c">
        <script>
     (adsbygoogle = window.adsbygoogle || []).push({
          google_ad_client: "ca-pub-1147191292192615",
          enable_page_level_ads: true
     });
</script>
<?php
    }
}
