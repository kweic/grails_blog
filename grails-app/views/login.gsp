
<!doctype html>
<html lang="en" class="no-js">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title>
        Login
    </title>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="stylesheet" href="/assets/bootstrap.css?compile=false" />
<link rel="stylesheet" href="/assets/grails.css?compile=false" />
<link rel="stylesheet" href="/assets/main.css?compile=false" />
<link rel="stylesheet" href="/assets/mobile.css?compile=false" />
<link rel="stylesheet" href="/assets/application.css?compile=false" />



	
	<style type="text/css" media="screen">
	#login {
		margin: 15px 0px;
		padding: 0px;
		text-align: center;
	}

	#login .inner {
		width: 340px;
		padding-bottom: 6px;
		margin: 60px auto;
		text-align: left;
		border: 1px solid #aab;
		background-color: #f0f0fa;
		-moz-box-shadow: 2px 2px 2px #eee;
		-webkit-box-shadow: 2px 2px 2px #eee;
		-khtml-box-shadow: 2px 2px 2px #eee;
		box-shadow: 2px 2px 2px #eee;
	}

	#login .inner .fheader {
		padding: 18px 26px 14px 26px;
		background-color: #f7f7ff;
		margin: 0px 0 14px 0;
		color: #2e3741;
		font-size: 18px;
		font-weight: bold;
	}

	#login .inner .cssform p {
		clear: left;
		margin: 0;
		padding: 4px 0 3px 0;
		padding-left: 105px;
		margin-bottom: 20px;
		height: 1%;
	}

	#login .inner .cssform input[type="text"] {
		width: 120px;
	}

	#login .inner .cssform label {
		font-weight: bold;
		float: left;
		text-align: right;
		margin-left: -105px;
		width: 110px;
		padding-top: 3px;
		padding-right: 10px;
	}

	#login #remember_me_holder {
		padding-left: 120px;
	}

	#login #submit {
		margin-left: 15px;
	}

	#login #remember_me_holder label {
		float: none;
		margin-left: 0;
		text-align: left;
		width: 200px
	}

	#login .inner .login_message {
		padding: 6px 25px 20px 25px;
		color: #c33;
	}

	#login .inner .text_ {
		width: 120px;
	}

	#login .inner .chk {
		height: 12px;
	}
	</style>

</head>

<body>

    <div class="navbar navbar-default navbar-static-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/#">
                    <i class="my-blog-logo">
                        <img src="/assets/blog_logo_invert.png" class="blog-logo"/>
                    </i>
                </a>
            </div>


            <div class="navbar-collapse collapse" aria-expanded="false" style="height: 0.8px;">
                <ul class="nav navbar-nav navbar-right">
                    
                </ul>
            </div>

        </div>
    </div>



    <div class="login-text right">
         <a href="/login/auth">Login</a>
    </div>
    




    
<div id="login">
	<div class="inner">
		<div class="fheader">Please Login</div>

		

		<form action="/login/authenticate" method="POST" id="loginForm" class="cssform" autocomplete="off">
			<p>
				<label for="username">Username:</label>
				<input type="text" class="text_" name="username" id="username"/>
			</p>

			<p>
				<label for="password">Password:</label>
				<input type="password" class="text_" name="password" id="password"/>
			</p>

			<p id="remember_me_holder">
				<input type="checkbox" class="chk" name="remember-me" id="remember_me" />
				<label for="remember_me">Remember me</label>
			</p>

			<p>
				<input type="submit" id="submit" value="Login"/>
			</p>
		</form>
	</div>
</div>
<script>
(function() {
	document.forms['loginForm'].elements['username'].focus();
})();
</script>



    <div class="footer" role="contentinfo"></div>

    <div id="spinner" class="spinner" style="display:none;">
        Loading&hellip;
    </div>

    <script type="text/javascript" src="/assets/jquery-2.2.0.min.js?compile=false" ></script>
<script type="text/javascript" src="/assets/bootstrap.js?compile=false" ></script>
<script type="text/javascript" src="/assets/application.js?compile=false" ></script>


</body>
</html>