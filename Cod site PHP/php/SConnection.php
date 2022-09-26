<?php
require_once("config.php");
class JavaConnection{
    var $resp;
    function __construct($text)
    {
        $PORT = getPort();
        $HOST = getHost();
        $this->resp='{"mesaj":"errhopa","status":false,"con":true}';
        $sock = @socket_create(AF_INET, SOCK_STREAM, 0);
        @socket_connect($sock, $HOST, $PORT);
        @socket_write($sock, getPassword().''.$text . "\n", strlen(getPassword().''.$text) + 1);
        $reply = @socket_read($sock, 132120559, PHP_NORMAL_READ);
        if(!empty($reply)){
            $this->resp=$reply;
        }

    }
    public function getResponse(){
        return $this->resp;
    }
}