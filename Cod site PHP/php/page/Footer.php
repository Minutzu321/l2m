<?php

class Footer
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

    public function printFooter(){
        foreach ($this->contents as $nume => $cod){
            echo $cod;
        }
        switch ($this->page->tip){
            case 1:
                $this->printFooter1();
                break;
            case 2:
                $this->printFooter2();
                break;
            default:
                break;
        }
    }

    private function printFooter1(){
?>
        <script src="<?php echo $this->page->autoversion('inc/js/pooper.min.js'); ?>"></script>
        <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
        <script src="<?php echo $this->page->autoversion('inc/js/boostrap.min.js'); ?>"></script>
        <script src="<?php echo $this->page->autoversion('inc/js/js.js'); ?>"></script>
    </body>
</html>
<?php
    }

    private function printFooter2(){
    ?>
            <div class="footer">
                <h5>Sponsori locali</h5>
                <a href="http://jtgrup.ro/" target="_blank"><img src="/inc/imgs/sponsori/jtgroup.png" width="85" height="85" alt="JT Grup" title="JT Grup"></a>
                <h4>Copyright 2019 by River Wolves.</h4>
            </div>
    </body>
</html>
    <?php
    }
}