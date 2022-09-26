<?php
function getLogin(){
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
        FD.append("lbutonel","da");

        XHR.addEventListener("load", function(event) {
          if(event.target.responseText == \'home\'){
            window.location.href = "/l2m/";
          }
          document.getElementById("q1").value = "";
          document.getElementById("loader").style.display = "none";
          document.getElementById("tot").style.display = "block";
          document.getElementById("erru").innerHTML =event.target.responseText;
        });
    
        XHR.addEventListener("error", function(event) {
          alert(\'Oops! Something went wrong.\');
        });
    
        XHR.open("POST", "login");
    
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
      <h3 class="card-header">Login</h3>
      <div class="card-body">
      <div id="erru"></div>
          <form id="myForm">
            <div class="form-group">
              <label>Email address or Username</label>
              <input type="text" class="form-control" placeholder="Enter email or Username" name="luser" required>
            </div>
            <div class="form-group">
              <label>Password</label>
              <input type="password" class="form-control" placeholder="Password" name="lpass" id="q1" required>
            </div>
            <button type="submit" class="btn btn-primary btn-lg btn-block" name="lbutonel">Submit</button>
            <a href="register" class="card-link">You don\'t have an account?</a>
          </form>
      </div>
    </div>
</div>
  ';
}