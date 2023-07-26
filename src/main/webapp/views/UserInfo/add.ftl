<div class="pageContent" style="overflow:hidden;">
	<form method="post" action="${base}/?ckparam=${encryption("/${action}/saveUserinfo/")}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="40" style="background-color:#ecf2f4; overflow:hidden ; padding-bottom:0px;">
			<fieldset style="height:257px;">
				<p>
					<label style="width:60px;">登&nbsp;&nbsp;录&nbsp;&nbsp;名：</label>
					<input type="text" name="user_code" 
					remote="${baseClass}/check" 
					class="required alphanumeric textInput"  maxlength="20">
					<span style="color:#C1C1C1;">字母、数字、下划线</span>
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
					class="digits" maxlength="15"/>
				</p>
				<p>
					<label style="width:60px;">Email&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：</label>
					<input type="text" name="email" value="${(userInfo.email)!}" 
					class="checkemail" maxlength="50"/>
				</p>
				<p>
					<label style="width:60px;">角&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色：</label>
					<select name="role_no" >
						<#if "${(type)!}"!="1">
						<option value="1" selected="selected">用户</option>
						<option value="0">管理员</option>
						</#if>
						<option value="2">业务员</option>
				    </select>
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

