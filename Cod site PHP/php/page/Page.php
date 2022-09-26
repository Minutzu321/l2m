<?php
require_once("php/SConnection.php");
class Page
{
    public function __construct($nume, $tip = 1,Header $header, Body $body, Footer $footer)
    {
        $this->nume = $nume;
        $this->tip = $tip;

        $this->header = $header;
        $this->body = $body;
        $this->footer = $footer;

        $header->setPage($this);
        $body->setPage($this);
        $footer->setPage($this);
    }

    public function getNavbar($membru, $logged){
    	$script = '<script>function ddwn() {
					  var x = document.getElementsByClassName("topnav")[0];
					  if (x.className === "topnav") {
					    x.className += " responsive";
					  } else {
					    x.className = "topnav";
					  }
					}
				   </script>';
		if($logged){
			if($membru){
			    return '
				  <div class="topnav">
				  	<a href="/">Acasa</a>
				    <a href="/aplica">Aplica</a>
				    <a href="/panou">Panou</a>
				    <a href="/profil">Profil</a>
				    <a href="javascript:void(0);" class="icon" onclick="ddwn()">
	    				<i class="fa fa-bars"></i>
	  				</a>
				  </div> '.$script;
			}else{	
			    return '
				  <div class="topnav">
				  	<a href="/">Acasa</a>
				    <a href="/aplica">Aplica</a>
				    <a href="/logout">Logout</a>
				    <a href="javascript:void(0);" class="icon" onclick="ddwn()">
	    				<i class="fa fa-bars"></i>
	  				</a>
	      </div> '.$script;
			}
		}else{
	    return '
		  <div class="topnav">
		  	<a href="/">Acasa</a>
		    <a href="/aplica">Aplica</a>
		    <a onclick="modal()">Logheaza-te</a>
		    <a href="javascript:void(0);" class="icon" onclick="ddwn()">
	    				<i class="fa fa-bars"></i>
			</a>
		  </div> '.$script;
		}
    }

    public function getNume()
    {
        return $this->nume;
    }
    public function getHeader()
    {
        return $this->header;
    }
    public function getBody()
    {
        return $this->body;
    }
    public function getFooter()
    {
        return $this->footer;
    }

    public function getJServerResponse($cmd,$args){
        $s = new \stdClass();
        $s->cmd = $cmd;
        $s->args = $args;
        $r = new JavaConnection(json_encode($s));
        return json_decode($r->getResponse());
    }

    public function printPage(){
        $this->header->addContent("<!-- Google Tag Manager -->
<script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-WP2SB4B');</script>
<!-- End Google Tag Manager -->");
        $this->header->printHeader();
        $this->body->printBody();
        $this->footer->printFooter();
    }

    public function autoversion($file) {
        return "$file?" . filemtime($file);
    }
}