<div class="pageContent" style="overflow:hidden;">
	<form method="post" action="${base}/?ckparam=${encryption("/${action}/saveUserinfo/")}" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="50" style="background-color:#ecf2f4; overflow:hidden ; padding-bottom:0px;">
			<fieldset style="height:230px;">
				<p>
					<label style="width:60px;">登录名</label>
					<input type="text" name="user_code" 
					remote="${baseClass}/check" 
					class="required textInput valid" maxlength="20">
				</p>
				<p>
					<label style="width:60px;">密码</label>
					<input id="w_validation_pwd" type="password" name="user_pass" 
					class="required alphanumeric textInput"  value="${(userInfo.user_pass)!}"
					minlength="6" maxlength="32" alt="字母、数字、下划线 6-20位">
				</p>
				<p>
					<label style="width:60px;">真实姓名</label>
					<input type="text" name="real_name" value="${(userInfo.real_name)!}" 
					class="required " maxlength="20"/>
				</p>
				<p>
					<label style="width:60px;">联系电话</label>
					<input type="text" name="link_phone" value="${(userInfo.link_phone)!}" 
					class="required checkphone" maxlength="25"/>
				</p>
				<p>
					<label style="width:60px;">QQ</label>
					<input type="text" name="qq" value="${(userInfo.qq)!}" 
					class="digits" maxlength="20"/>
				</p>
				<p>
					<label style="width:60px;">Email</label>
					<input type="text" name="email" value="${(userInfo.email)!}" 
					class="checkemail" maxlength="50"/>
				</p>
				<p>
					<label style="width:60px;">所属公司 </label>
					<select name="company_no" class="required checknumber">
				    <option value="0" <#if userInfo??><#if userInfo.company_no==0> selected="selected" </#if></#if> >0-北京</option>
				    <option value="1" <#if userInfo??><#if userInfo.company_no==1> selected="selected" </#if></#if> >1-天津</option>
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

