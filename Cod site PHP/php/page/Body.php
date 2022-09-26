<?php

class Body
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

    public function printBody(){
        foreach ($this->contents as $nume => $cod){
            echo $cod;
        }
    }
}