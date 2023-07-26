<#assign company=getCache("company")>
<div class="dialogContent layoutBox unitBox" style="height:500px;">
<div class="pageContent" style="width:300px;" >
	<form method="post" action="${base}/?ckparam=${encryption("/${action}/rolelist/")}" class="pageForm" onsubmit="return navTabSearch(this);">
		<input type="hidden" name="role_no" value="0" >
		<div class="pageFormContent" layouth="50" style="height:203px; overflow: auto;">
			<div class="unit"><input type="hidden" name="carte_id" value="${(carte_id)! }"/>
				<label>登&nbsp;&nbsp;录&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;&nbsp;：</label>
				<input type="text" size="25" name="user_code" class="textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>真&nbsp;实&nbsp;姓&nbsp;名&nbsp;：</label>
				<input type="text" size="25" name="real_name" class="lettersonly textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>联&nbsp;系&nbsp;电&nbsp;话&nbsp;：</label>
				<input type="text" size="25" name="link_phone" class="alphanumeric textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>Q&nbsp;&nbsp;Q&nbsp;&nbsp;号&nbsp;&nbsp;码：</label>
				<input type="text" size="25" name="qq" class="alphanumeric textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>电&nbsp;子&nbsp;邮&nbsp;箱&nbsp;：</label>
				<input type="text" size="25" name="email" class="textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			
			<div class="unit">
				<label>所&nbsp;属&nbsp;公&nbsp;司&nbsp;：</label>
				<select name="company" >
			    <option value="" selected="selected">全部</option>
				<#list company?keys as key>
			    <option value="${key}" >${key}-${company[key]}</option>
			    </#list>
			    </select>
			</div>
			<div class="unit">
				<label>添&nbsp;&nbsp;&nbsp;加&nbsp;&nbsp;&nbsp;人&nbsp;&nbsp;：</label>
				<input type="text" size="25" name="add_user" class="textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>添&nbsp;加&nbsp;日&nbsp;期&nbsp;：</label>
				<input type="text" size="25" name="add_date" class="date textInput" maxlength="25">
				
			</div>
			<div class="unit">
				<label>最后登录ip&nbsp;：</label>
				<input type="text" size="25" name="last_login_ip" class="textInput" maxlength="25">
				<span class="inputInfo">模糊匹配</span>
			</div>
			<div class="unit">
				<label>最&nbsp;后&nbsp;登&nbsp;录&nbsp;：</label>
				<input type="text" size="25" name="last_login_date" class="date textInput" maxlength="25">
			</div>
			<div class="unit">
				<label>登录总次数：</label>
				<input type="text" size="25" name="login_num" class="textInput" maxlength="25">
				<span class="inputInfo">必须正确</span>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">检索</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="reset">重置</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
</div>
	<div class="dialogFooter"><div class="dialogFooter_r"><div class="dialogFooter_c"></div></div></div>
	<div class="resizable_h_l" tar="nw"></div>
	<div class="resizable_h_r" tar="ne"></div>
	<div class="resizable_h_c" tar="n"></div>
	<div class="resizable_c_l" tar="w" style="height:300px;"></div>
	<div class="resizable_c_r" tar="e" style="height:300px;"></div>
	<div class="resizable_f_l" tar="sw"></div>
	<div class="resizable_f_r" tar="se"></div>
	<div class="resizable_f_c" tar="s"></div>
</div>