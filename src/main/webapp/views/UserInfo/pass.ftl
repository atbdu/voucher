<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<head>
			<meta charset="UTF-8">
			<title>修改密码</title>
			<meta http-equiv="X-UA-Compatible" content="ie=edge">
			<link rel="stylesheet" href="${base}/public/login/css/login.css">
			<link rel="stylesheet" href="${base}/public/login/css/font-awesome.min.css">
		</head>
		<script type="text/javascript">
			var flag = false;
			var flag1 = false;
			var flag2 = false;
			function checkoldpwd(){
				$("#pwdError1").hide();
				var oldPwd = $("#oldPwd").val();
				if(oldPwd!=""){
					$.post("${baseClass}/ajaxUser_pass",{"user_pass":oldPwd},function(data){
						if(data=="false"){
							$("#pwdError2").hide();
							$("#pwdError").show();
						}else{
							$("#pwdError2").hide();
							$("#pwdError").hide();
							flag = true;
						}
					})
				}else{
					$("#pwdError").hide();
					$("#pwdError2").show();
				}
				return flag;
			}
			function checknewpwd(){
				$("#pwdError3").hide();
				var newPwd = $("#password").val();
				var reg = /^[0-9a-zA-Z_]{1,}$/;
				if(newPwd!=""){
					if(!reg.test(newPwd)){
						$("#pwdError5").hide();
						$("#pwdError4").show();
					}else{
						$("#pwdError4").hide();
						$("#pwdError5").hide();
						flag1 = true
					}
				}else{
					$("#pwdError4").hide();
					$("#pwdError5").show();
				}
				return flag1;
			}
			function checknewpwd2(){
				$("#pwdError6").hide();
				var newPwd = $("#password").val();
				var newPwd2 = $("#password2").val();
				if(newPwd2!=""){
					if(newPwd2!=newPwd){
						$("#pwdError8").hide();
						$("#pwdError7").show();
					}else{
						$("#pwdError7").hide();
						$("#pwdError8").hide();
						flag2 = true
					}
				}else{
					$("#pwdError7").hide();
					$("#pwdError8").show();
				}
				return flag2;
			}
			function check(){
				if(checkoldpwd()&&checknewpwd()&&checknewpwd2()){
					return true;
				}
				return false;
			}
		</script>


	<body style="background-color: #fff">
	<section >
		<div class="loginPane" style="top: 18%">
			<div style="margin-top: 20px"><h1 style="font-family: 黑体;text-align: center">修改密码</h1></div>
			<form method="post" action="${base}/?ckparam=${encryption("/${action}/updatepass/")}"  onsubmit="return check()">
				<div class="loginInputBox" style="margin-top: 20px">
					<div class="mb-20 el-input el-input--prefix">
						<input type="password" id="oldPwd" autocomplete="off" placeholder="旧密码" class="el-input__inner" onblur="checkoldpwd();" />
						<label role="checkbox" class="el-checkbox" style="margin-top: 5px">
							<span  style="display:none;" id="pwdError">
								<span class="middle" style="margin-left:15px;color:red">原密码输入错误</span>
							</span>
							<span style="display:none;" id="pwdError2">
								<span class="middle" style="margin-left:15px;color:red">原密码不能为空</span>
							</span>
						</label>
					</div>
				</div>
				<div class="loginInputBox">
					<div class="mb-20 el-input el-input--prefix el-input--suffix">
						<input type="password" id="password"  autocomplete="off" placeholder="新密码" name="password" class="el-input__inner" onblur="checknewpwd();"/>
						<label role="checkbox" class="el-checkbox" style="margin-top: 5px">
							<span  style="display:none;" id="pwdError4">
								<span class="middle" style="margin-left:15px;color:red">格式错误(字母、数字、下划线)</span>
							</span>
							<span  style="display:none;" id="pwdError5">
								<span class="middle" style="margin-left:15px;color:red">密码不能为空</span>
							</span>
						</label>
					</div>
				</div>
				<div class="loginInputBox">
					<div class="mb-20 el-input el-input--prefix el-input--suffix">
						<input type="password" id="password2" autocomplete="off" placeholder="再次输入新密码" class="el-input__inner" onblur="checknewpwd2();"/>
						<label role="checkbox" class="el-checkbox" style="margin-top: 5px">
							<span  style="display:none;" id="pwdError7">
								<span class="middle" style="margin-left:15px;color:red">两次输入密码不一致</span>
							</span>
							<span  style="display:none;" id="pwdError8">
								<span class="middle" style="margin-left:15px;color:red">密码不能为空</span>
							</span>
						</label>
					</div>
				</div>
				<div class="clear"></div>
				<button type="submit" class="el-button loginBtn mt-20 el-button--primary" style="width: 20%;margin-left: 20%">
					<span>提交</span>
				</button>
				<button type="button" class="el-button loginBtn mt-20 el-button--primary" style="width: 20%;margin-left: 5%"  onclick="window.location.reload()">
					<span>取消</span>
				</button>
			</div>
		</form>
		</div>
	</section>

	<!-- import JavaScript -->
	<script type="text/javascript" src="${base}/public/login/js/jquery-2.1.1.min.js"></script>
	<script src="${base}/public/login/js/new_login.js"></script>
	</body>
	</html>
