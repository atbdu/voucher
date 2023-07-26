<!--登录超时页面-->
<script>
$("#tableLogin").bind('keyup',function(event) { 
	if(event.keyCode==13){
		publicLogining();
	}
});

$(document).ready(function(){
	if (document.cookie.length>0){ 
		var c_name="username";
	     var c_start=document.cookie.indexOf(c_name + "=")
		 if (c_start!=-1){ 
			 c_start=c_start + c_name.length+1 
			 var c_end=document.cookie.indexOf(";",c_start)
			 if (c_end==-1) c_end=document.cookie.length
			 var username= unescape(document.cookie.substring(c_start,c_end))
			if (username!=null && username!=""){
				var user=username.split("|");
				$("input[name='userName']").val(user[0]);
				$("input[name='userPass']").val(user[1]);
			}
		 } 
	 }
	
	$.pdialog.resizeDialog({style: {width:650,height: 350}}, $.pdialog.getCurrent(), "");
	
});
function publicLogining(){
	var userName = $("input[name='userName']").val();
	var userPass = $("input[name='userPass']").val();
	if(userName!="" && userPass!=""){
		$.post("${base}/UserInfo/loginUser?user_code="+userName+"&user_pass="+userPass+"&code=yes",function(data){
			if(data.code == "sucess"){
				$.pdialog.closeCurrent(); //关闭当前页
				alertMsg.correct("登录成功，请继续您的操作！");
			}else if(data.code == "stop"){
				alertMsg.correct("用户已禁用...");
			}else if(data.code == "error"){
				alertMsg.correct("用户或者密码错误...");
			}else if(data.code == "error"){
				alertMsg.correct("系统错误...");
			}
		});
	}else{
		alertMsg.error("用户名和密码不能为空！"); //提示消息
	}
}
</script>
<div class="pageContent"  style="width: 100%;height: 100%;">
	<table id="tableLogin"  style="margin-left: 5%;margin-top: 5%;">
		<tr>
			<td rowspan="3"><img src="${base}/public/images/ku.gif"></td>
			<td colspan="3" style="height:50px;">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="3"><span style="font-size:16px;font-weight:bold;">登录超时，请重新登录：</span></td>
		</tr>
		<tr>
			<td>用户名：<input type="text" name="userName" size="12" value="${(user_code)!}"/>&nbsp;&nbsp;&nbsp;</td>
			<td>用户密码：<input type="password" name="userPass"  size="12" value="${(user_pass)!}"/>&nbsp;&nbsp;&nbsp;</td>
			<td><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="publicLogining()">保存</button></div></div></td>
		</tr>
	</table>
</div>

