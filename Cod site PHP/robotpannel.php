<?php
require_once("php/page/Header.php");
require_once("php/page/Body.php");
require_once("php/page/Footer.php");
require_once ("php/page/Page.php");
if(isset($_GET['trim'])) {
    $header = new Header();
    $body = new Body();
    $footer = new Footer();

    $pagina = new Page("Robot Pannel",3,$header,$body,$footer);

    $args = array("SET_ROBOT_DATA",$_GET['trim']);
    $r = $pagina->getJServerResponse('rqcmd',$args);
    
    print($r->mesaj);
    exit();
}else if(isset($_GET['getd'])){
    $header = new Header();
    $body = new Body();
    $footer = new Footer();

    $pagina = new Page("Robot Pannel",3,$header,$body,$footer);

    $args = array("robot_info","-1","-1");
    $r = $pagina->getJServerResponse('rqdata',$args);
    
    print($r->mesaj);
    exit();
}
$config = true;
$control = false;
$info = false;

if(isset($_GET['dir'])){
    $s = $_GET['dir'];
    if($s == 'control'){
        $control = true;
        $config = false;
    }else if($s == 'info'){
        $config = false;
        $info = true;
    }
}

?>
<html>
    <head>
        <title>Panou de control al robotului</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <style>
        .slidecontainer {
        width: 100%;
        }

        .slider {
        -webkit-appearance: none;
        width: 100%;
        height: 25px;
        background: #d3d3d3;
        outline: none;
        opacity: 0.7;
        -webkit-transition: .2s;
        transition: opacity .2s;
        }

        .slider:hover {
        opacity: 1;
        }

        .slider::-webkit-slider-thumb {
        -webkit-appearance: none;
        appearance: none;
        width: 25px;
        height: 25px;
        background: #4CAF50;
        cursor: pointer;
        }

        .slider::-moz-range-thumb {
        width: 25px;
        height: 25px;
        background: #4CAF50;
        cursor: pointer;
        }

        .mijloc{
            margin: auto;
            width: 60%;
        }

        .controale{
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .btn {
            border: none;
            color: white;
            padding: 14px 28px;
            cursor: pointer;
        }

        .verde {background-color: #4CAF50;}
        .verde:hover {background-color: #46a049;}

        .albastru {background-color: #2196F3;}
        .albastru:hover {background: #0b7dda;}

        .rosu {background-color: rgb(243, 33, 33);}
        .rosu:hover {background: #da0b0b;}

        .switch {
        position: relative;
        display: inline-block;
        width: 60px;
        height: 34px;
        }

        .switch input { 
        opacity: 0;
        width: 0;
        height: 0;
        }

        .ts {
        position: absolute;
        cursor: pointer;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: #ccc;
        -webkit-transition: .4s;
        transition: .4s;
        }

        .ts:before {
        position: absolute;
        content: "";
        height: 26px;
        width: 26px;
        left: 4px;
        bottom: 4px;
        background-color: white;
        -webkit-transition: .4s;
        transition: .4s;
        }

        input:checked + .ts {
        background-color: #2196F3;
        }

        input:focus + .ts {
        box-shadow: 0 0 1px #2196F3;
        }

        input:checked + .ts:before {
        -webkit-transform: translateX(26px);
        -ms-transform: translateX(26px);
        transform: translateX(26px);
        }

        /* Rounded sliders */
        .ts.round {
        border-radius: 34px;
        }

        .ts.round:before {
        border-radius: 50%;
        }
        </style>
    </head>
    <body>
    <p id="demo"></p>
        <div class="mijloc">
        <?php
        if($config){
            $header = new Header();
            $body = new Body();
            $footer = new Footer();

            $pagina = new Page("Robot Pannel",3,$header,$body,$footer);

            $args = array("robot_config","-1","-1");
            $r = $pagina->getJServerResponse('rqdata',$args);
            if(!$r->status){
                print('Robotul nu a comunicat cu serverul asa ca nu se stie configuratia exacta');
                exit();
            }
            $data = json_decode($r->mesaj);
        ?>
            <h1>Panou de configuratie</h1>
            <br>
            <h3>Puterea rotilor</h1>
            <input type="range" min="0" max="100" value="<?php print(doubleval($data->putereRoti)*100); ?>" class="slider" id="putereroti">
            <p id="sputereroti"></p>
            <br>
            <h3>Puterea bratului 1</h1>
            <input type="range" min="0" max="100" value="<?php print(doubleval($data->putereBrat1)*100); ?>" class="slider" id="puterebrat1">
            <p id="sputerebrat1"></p>
            <br>
            <h3>Puterea bratului 2</h1>
            <input type="range" min="0" max="100" value="<?php print(doubleval($data->putereBrat2)*100); ?>" class="slider" id="puterebrat2">
            <p id="sputerebrat2"></p>

            <h3>Telemetry</h1>
            <label class="switch">
            <input id="telem" type="checkbox" <?php if($data->telemetry) print('checked');?>>
            <span class="ts round"></span>
            </label>
            <br>
            <h3>Controllere</h1>
            <label class="switch">
            <input id="control" type="checkbox" <?php if($data->controller) print('checked');?>>
            <span class="ts round"></span>
            </label>

            <br><br><br>
            <button class="btn verde" id="trimite">Trimite valorile</button>

            <script>
            var slider = document.getElementById("putereroti");
            var output = document.getElementById("sputereroti");
            output.innerHTML = "Valoare: "+ slider.value/100;

            slider.oninput = function() {
                output.innerHTML = "Valoare: "+ this.value/100;
            }

            var slider2 = document.getElementById("puterebrat1");
            var output2 = document.getElementById("sputerebrat1");
            output2.innerHTML = "Valoare: "+ slider2.value/100;

            slider2.oninput = function() {
                output2.innerHTML = "Valoare: "+ this.value/100;
            }

            var slider3 = document.getElementById("puterebrat2");
            var output3 = document.getElementById("sputerebrat2");
            output3.innerHTML = "Valoare: "+ slider3.value/100;

            slider3.oninput = function() {
                output3.innerHTML = "Valoare: "+ this.value/100;
            }

            var trim = document.getElementById("trimite");
            trim.onclick = function() {
                var vpr = slider.value/100;
                var vbr1 = slider2.value/100;
                var vbr2 = slider3.value/100;
                var contr = document.getElementById("control").checked;
                var telem = document.getElementById("telem").checked;

                var dtls = {tip:0,putereRoti:vpr,putereBrat1:vbr1,putereBrat2:vbr2,controller:contr,telemetry:telem}
                var json = JSON.stringify(dtls);
                
                var xmlhttp = new XMLHttpRequest();
                xmlhttp.onreadystatechange = function() {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("demo").innerHTML = this.responseText;
                }
                };
                xmlhttp.open("GET", "robotpannel?trim="+json, true);
                xmlhttp.send();
            }
            </script>
            <?php
            exit();
        }
            ?>
            <?php
            if($control){
            ?>
            <h1>Panou de control</h1>
            <div class="controale">
                    <button class="btn albastru" id="binai">Inainte</button>
            </div>
            <div class="controale">
                    <button class="btn albastru" id="bstan">Stanga</button><button class="btn albastru" id="bdrea">Dreapta</button><br><br>
            </div>
            <div class="controale">
                    <button class="btn albastru" id="binap">Inapoi</button>
            </div>
            <div class="controale">
                <button class="btn rosu" id="bstop">Stop</button>
            </div>
            <h3>Control brat 1</h3>
            <div class="controale">
                <button class="btn albastru" id="bbsus1">Sus</button>
            </div>
            <div class="controale">
                <button class="btn albastru" id="bbjos1">Jos</button>
            </div>
            <br>
            <div class="controale">
                <button class="btn rosu" id="bbstop1">Stop</button>
            </div>
            <br>
            <h3>Control brat 2</h3>
            <div class="controale">
                <button class="btn albastru" id="bbsus2">Sus</button>
            </div>
            <div class="controale">
                <button class="btn albastru" id="bbjos2">Jos</button>
            </div>
            <br>
            <div class="controale">
                <button class="btn rosu" id="bbstop2">Stop</button>
            </div>
            <script>
                var inainte = document.getElementById("binai");
                inainte.onclick = function() {

                    var dtls = {tip:1,rmiscare:"inainte"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var inapoi = document.getElementById("binap");
                inapoi.onclick = function() {
                    var dtls = {tip:1,rmiscare:"inapoi"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var stanga = document.getElementById("bstan");
                stanga.onclick = function() {
                    var dtls = {tip:1,rmiscare:"rotire_stanga"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var dreapta = document.getElementById("bdrea");
                dreapta.onclick = function() {
                    var dtls = {tip:1,rmiscare:"rotire_dreapta"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }

                var stop = document.getElementById("bstop");
                stop.onclick = function() {
                    var dtls = {tip:1,rmiscare:"stop"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }

                var bstop1 = document.getElementById("bbstop1");
                bstop1.onclick = function() {
                    var dtls = {tip:3,bmiscare1:"zero"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var bstop2 = document.getElementById("bbstop2");
                bstop2.onclick = function() {
                    var dtls = {tip:3,bmiscare2:"zero"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }

                var bsus1 = document.getElementById("bbsus1");
                bsus1.onclick = function() {
                    var dtls = {tip:3,bmiscare1:"plus"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var bsus2 = document.getElementById("bbsus2");
                bsus2.onclick = function() {
                    var dtls = {tip:3,bmiscare2:"plus"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }

                var bjos1 = document.getElementById("bbjos1");
                bjos1.onclick = function() {
                    var dtls = {tip:3,bmiscare1:"minus"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }
                var bjos2 = document.getElementById("bbjos2");
                bjos2.onclick = function() {
                    var dtls = {tip:3,bmiscare2:"minus"}
                    var json = JSON.stringify(dtls);

                    var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                    if (this.readyState == 4 && this.status == 200) {
                        document.getElementById("demo").innerHTML = this.responseText;
                    }
                    };
                    xmlhttp.open("GET", "robotpannel?trim="+json, true);
                    xmlhttp.send();
                }

                //TASTE
                window.onkeydown = function(e) {
                    var key = e.keyCode ? e.keyCode : e.which;

                    if (key == 87) {
                        inainte.click();
                    }else if (key == 83) {
                        inapoi.click();
                    }else if (key == 65) {
                        stanga.click();
                    }else if (key == 68) {
                        dreapta.click();
                    }else if (key == 103) {
                        bsus1.click();
                    }else if (key == 100) {
                        bjos1.click();
                    }else if (key == 104) {
                        bsus2.click();
                    }else if (key == 101) {
                        bjos2.click();
                    }
                }
                
                window.onkeyup = function(e) {
                    stop.click();
                    bstop1.click();
                    bstop2.click();
                }
            </script>
            <?php
            exit();
            }
            ?>
            <h1>Panou de informatii</h1>
            <p id="dpinfo">Se cauta serverul...</p>
        </div>
        
    </body>

    <script>
        function sh(json) {
        if (typeof json != 'string') {
            json = JSON.stringify(json, undefined, 2);
        }
        json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function (match) {
            var cls = 'number';
            if (/^"/.test(match)) {
                if (/:$/.test(match)) {
                    cls = 'key';
                } else {
                    cls = 'string';
                }
            } else if (/true|false/.test(match)) {
                cls = 'boolean';
            } else if (/null/.test(match)) {
                cls = 'null';
            }
            return '<span class="' + cls + '">' + match + '</span>';
        });
    }

        var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("dpinfo").innerHTML = sh(this.responseText);
            }
            };
            xmlhttp.open("GET", "robotpannel?getd=1", true);
            xmlhttp.send();

        setInterval(function(){
            var xmlhttp = new XMLHttpRequest();
            xmlhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById("dpinfo").innerHTML = sh(this.responseText);
            }
            };
            xmlhttp.open("GET", "robotpannel?getd=1", true);
            xmlhttp.send();
        }, 1);
    </script>
</html>