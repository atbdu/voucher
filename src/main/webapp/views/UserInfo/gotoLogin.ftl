<html lang="zh">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<head>
	<meta charset="UTF-8">
	<title>立减金-企业登录</title>
	<meta http-equiv="X-UA-Compatible" content="ie=edge">
	<link rel="shortcut icon" href="/voucher/favicon.ico" type="image/x-icon" />
	<link rel="icon" href="/voucher/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="/voucher/favicon.ico">
<#--	<link rel="stylesheet" href="${base}/public/login/css/login.css">-->
	<link rel="stylesheet" href="${base}/public/login/css/login_new.css">
    <link rel="stylesheet" href="${base}/public/login/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="${base}/public/login/poshytip/tip-yellowsimple/tip-yellowsimple.css">
</head>

<body style="overflow: hidden;">
	<section class="login_bg">

		<div class="loginPane" style="overflow: hidden;height: 100%">
			<img src="${base}/public/imgs/logo1.png" class="loginImg">
			<img src="${base}/public/imgs/logo3.png" class="loginImg" >
			<span class="line"></span>
			<div class="loginInputBox">
				<div class="login_title"><h1>企业登录</h1></div>
				<form id="myForm" method="post" autocomplete="off" onsubmit="return check()">
					<div class="mb-20 el-input el-input--prefix">
						<input type="text" placeholder="手机号" onfocus="this.placeholder=''" onblur="this.placeholder='手机号'" class="el-input__inner" id="user_code" name = "username">
						<span class="el-input__prefix" style="display:none"><i class="el-input__icon fa fa-user input_fa" ></i></span>
					</div>
					<div class="mb-20 el-input el-input--prefix el-input--suffix">
						<input type="password" placeholder="用户密码" onfocus="this.placeholder=''" onblur="this.placeholder='用户密码'" class="el-input__inner" id="user_pass" name = "password">
						<span class="el-input__prefix" style="display:none"><i class="el-input__icon fa fa-lock input_fa" ></i></span>
						<span class="el-input__suffix" style="margin-left:90%;display:none"><i class="el-input__icon fa fa-eye input_fa"></i></span>

					</div>


				<label role="checkbox" class="el-checkbox mt-20">
					<span aria-checked="mixed" class="el-checkbox__input">
						<input type="checkbox" aria-hidden="true" name="type" class="el-checkbox__original" value="记住密码"  id="remember" onfocus="this.removeAttribute('readonly')"  autocomplete="off" >
					</span>
					<span class="el-checkbox__label"  for="remember">记住密码</span>
					<span id="err" style="margin-left:15px;color:red"></span>
				</label>
				<div class="clear"></div>
				<button type="button" class="el-button loginBtn mt-20 el-button--primary" id="login-button">
					<span>立即登录</span>
				</button>
				</form>
			</div>
		</div>
	</section>





<!-- import JavaScript -->
<script type="text/javascript" src="${base}/public/login/js/jquery-2.1.1.min.js"></script>
	<script src="${base}/public/login/poshytip/jquery.poshytip.js"></script>
	<script src="${base}/public/login/js/new_login.js"></script>
	<script src="${base}/public/login/js/remember.js"></script>
</body>
</html>