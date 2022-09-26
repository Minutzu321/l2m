<?php
function offlineCantRegister(){
    return "<p class=\"text-danger\">Server offline. Nu te poti inregistra.</p>";
}

function passwordsDontMatch(){
    return "<p class=\"text-danger\">Parolele nu se potrivesc.</p>";
}

function fieldsCantBeEmpty(){
    return "<p class=\"text-danger\">Campurile nu pot fi goale.</p>";
}

function getReg(){
    return '
    <style>
    #loader {
      position: absolute;
      left: 50%;
      top: 50%;
      z-index: 1;
      width: 150px;
      height: 150px;
      margin: -75px 0 0 -75px;
      border: 16px solid #f3f3f3;
      border-radius: 50%;
      border-top: 16px solid #3498db;
      width: 120px;
      height: 120px;
      -webkit-animation: spin 2s linear infinite;
      animation: spin 2s linear infinite;
    }
    
    @-webkit-keyframes spin {
      0% { -webkit-transform: rotate(0deg); }
      100% { -webkit-transform: rotate(360deg); }
    }
    
    @keyframes spin {
      0% { transform: rotate(0deg); }
      100% { transform: rotate(360deg); }
    }    
    </style>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script>
    window.addEventListener("load", function () {
      function sendData() {
        var XHR = new XMLHttpRequest();

        var FD = new FormData(form);
        FD.append("rbutonel","da");

        XHR.addEventListener("load", function(event) {
          if(event.target.responseText == \'home\'){
            window.location.href = "/l2m/";
          }
          document.getElementById("q1").value = "";
          document.getElementById("q2").value = "";
          document.getElementById("loader").style.display = "none";
          document.getElementById("tot").style.display = "block";
          document.getElementById("erru").innerHTML =event.target.responseText;
        });
    
        XHR.addEventListener("error", function(event) {
          alert(\'Oops! Something went wrong.\');
        });
    
        XHR.open("POST", "register");
    
        // The data sent is what the user provided in the form
        XHR.send(FD);
      }

      var form = document.getElementById("myForm");

      form.addEventListener("submit", function (event) {
        event.preventDefault();
        document.getElementById("loader").style.display = "block";
        document.getElementById("tot").style.display = "none";
        sendData();
      });
    });
    </script>
    <div id="loader" style="display:none;"></div>
<div id="tot">
    <div class="card mb-3">
      <h3 class="card-header">Register</h3>
      <div class="card-body">
      <div id="erru"></div>
      <form id="myForm">
            <div class="form-group">
              <label>Numele si prenumele</label>
              <input type="text" class="form-control" placeholder="Numele si prenumele" name="ruser" required>
            </div>
            <div class="form-group">
              <label>Email-ul</label>
              <input type="email" class="form-control" placeholder="Pune email-ul" name="remail" required>
            </div>
            <div class="form-group">
              <label>Parola</label>
              <input type="password" class="form-control" placeholder="Parola" name="rpass" id="q1" required>
              <label>Confirm password</label>
              <input type="password" class="form-control" placeholder="Confirma Parola" name="rrpass" id="q2" required>
            </div>
            <button type="submit" class="btn btn-primary btn-lg btn-block" name="rbutonel">Submit</button>
            <a href="login" class="card-link">You already have an account?</a>
          </form>
      </div>
    </div>
</div>
  ';
}