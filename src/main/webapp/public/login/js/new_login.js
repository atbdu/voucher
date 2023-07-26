$(function () {
	document.onkeydown = function(e){
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
			$('#login-button').click();
			$('#user_code').poshytip("hide");
			$('#user_pass').poshytip("hide");
			$('#login-button').poshytip("hide");
	    }
	}

	$('#user_code').focus(function(){
		$('#user_code').poshytip("hide");
	})
	$('#user_pass').focus(function(){
		$('#user_pass').poshytip("hide");
	})

	$('#user_code').blur(function(){
		$('#user_code').poshytip("hide");
	})
	$('#user_pass').blur(function(){
		$('#user_pass').poshytip("hide");
	})


	$('#user_code').poshytip({/*自定义提示框*/
    	content: '用户名必须填写',
	    className: 'tip-yellowsimple',
		showOn: 'none',
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 5,
		offsetY: 0,
		showTimeout: 100
	});
	$('#user_pass').poshytip({/*自定义提示框*/
		content: '密码必须填写',
		className: 'tip-yellowsimple',
		showOn: 'none',
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 5,
		offsetY: 0,
		showTimeout: 100
	});
	$('#login-button').poshytip({/*自定义提示框*/
		content: '',
		className: 'tip-yellowsimple',
		showOn: 'none',
		alignTo: 'target',
		alignX: 'right',
		alignY: 'center',
		offsetX: 5,
		offsetY: 0,
		showTimeout: 100
	});


})

function proName() {
	 var pathName=window.document.location.pathname;
	 return pathName.substring(1,pathName.substr(1).indexOf('/')+1);
}

$('#login-button').click(function(event){
	var user_code=$("#user_code").val();
	var user_pass=$("#user_pass").val();
	if(user_code==""){
		$('#user_code').poshytip('show');
		return false;
	}
	if(user_pass==""){
		$('#user_pass').poshytip('show');
		return false;
	}
	$.post("/"+proName()+"/UserInfo/loginUser",{"user_code":user_code,"user_pass":user_pass},function(data){
		if(data.code=="error"||data.code=="stop"||data.code=="lock"){
			var mes=data.code=="error"?"您的账号或者密码错误，密码错误数限3次":(data.code =="stop"?"您的账号已停用，请联系管理员启用":"您的账户已被锁");
			$("#login-button").text("登录错误..");
			$('#login-button').poshytip('update',mes);
			$('#login-button').poshytip('show');
			var loginText=function loginText(){
				$("#login-button").text("登录");
			}
			setTimeout(loginText,3000);
	   	}
	   	if(data.code=="sucess"){
	   	    $("#login-button").text("登录成功，正在进入..");
	   		window.location.href = "/"+proName()+"/Index/index";
	   	}
	});
});