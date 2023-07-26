  <script>
function chooseType(type){
	if(type==0){
		$("#pwdone").val("123456");
		$("#pwdtwo").val("123456");
	}else{
		$("#pwdone").val("");
		$("#pwdtwo").val("");
	}
}
</script>
<div class="pageContent" style="overflow:hidden;">
	<form method="post" action="${base}/?ckparam=${encryption("/${action}/updatepass/")}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="40" style="background-color:#ecf2f4;overflow:hidden;padding-bottom:0px;">
			<fieldset>
				<p>
					<label style="width:60px;">登&nbsp;&nbsp;录&nbsp;&nbsp;名：</label>
					<input type="text" name="user_code" value="${(userInfo.user_code)!''}" readonly/>
				</p>
				<p>
					<label style="width:60px;">真实姓名：</label>
					<input type="text" name="real_name" value="${(userInfo.real_name)!}" readonly/>
				</p>
				<p>
					<label style="width:60px;">重置方式：</label>
					<input type="radio" name="updatetype" id="default" value="0" onclick="chooseType(0)" checked/>默认
					<input type="radio" name="updatetype" id="manual" value="1" onclick="chooseType(1)" />手动输入
				</p>
				<p>
					<label style="width:60px;">新&nbsp;&nbsp;密&nbsp;&nbsp;码：</label>
					<input type="password" name="user_pass" id="pwdone" value="123456" onblur="checkpass()" class="required alphanumeric" minlength="6" maxlength="20"/>
					<span style="color:#C1C1C1;">字母、数字、下划线 6-20位</span>
				</p>
				<p>
					<label style="width:60px;">确认密码：</label>
					<input type="password" id="pwdtwo" value="123456" onblur="checkpass()" class="required alphanumeric" equalTo="#pwdone" maxlength="20"/>
					<span style="color:#C1C1C1;">字母、数字、下划线 6-20位</span>
				</p>
			</fieldset>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

