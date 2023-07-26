<div class="pageContent" style="overflow:hidden;">
	<form method="post" action="${base}/?ckparam=${encryption("/${action}/updateUserinfo/")}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="40" style="background-color:#ecf2f4;overflow:hidden;padding-bottom:0px;">
			<fieldset style="height:205px;">
				<p>
					<label style="width:60px;">登&nbsp;&nbsp;录&nbsp;&nbsp;名：</label>
					<input type="text" name="user_code" maxlength="20" value="${(userInfo.user_code)!''}" readonly/>
				</p>
				<p>
					<label style="width:60px;">真实姓名：</label>
					<input type="text" name="real_name" value="${(userInfo.real_name)!}" 
					class="required " maxlength="10"/>
				</p>
				<p>
					<label style="width:60px;">联系电话：</label>
					<input type="text" name="link_phone" value="${(userInfo.link_phone)!}" 
					class="required checkphone" maxlength="11"/>
				</p>
				<p>
					<label style="width:60px;">QQ&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label>
					<input type="text" name="qq" value="${(userInfo.qq)!}" 
					class="digits" maxlength="10"/>
				</p>
				<p>
					<label style="width:60px;">Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label>
					<input type="text" name="email" value="${(userInfo.email)!}" 
					class="checkemail" maxlength="50"/>
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

