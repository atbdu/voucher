<#include "/common/pageForm.ftl">
<#assign company=getCache("company")>
<#assign state=getCache("state")>
</script>
<div class="pageHeader">
	<form onsubmit="return navTabSearch(this);" action="${base}/?ckparam=${encryption("/${action}/rolelist/")}" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						姓名:&nbsp;<input type="text" name="real_name" value="${(p.params.real_name)!''}" size=15/>&nbsp;&nbsp;&nbsp;&nbsp;
						登录名:&nbsp;<input type="text" name="user_code" value="${(p.params.user_code)!''}" size=15/>&nbsp;&nbsp;&nbsp;&nbsp;
						联系电话:&nbsp;<input type="text" name="link_phone" value="${(p.params.link_phone)!''}" size=15/>&nbsp;&nbsp;&nbsp;&nbsp;
						所属公司:&nbsp;<select name="company" >
						     <option value="" >全部</option>
							<#list company?keys as key>
						    <option value="${key}" <#if p.params.company??><#if "${(p.params.company)!}"=="${key}"> selected="selected" </#if></#if> >${key}-${company[key]}</option>
						    </#list>
						    </select>&nbsp;&nbsp;&nbsp;&nbsp;
						用户是否启用 :&nbsp;<select name="status" >
						    <option value="" >全部</option>
							<#list state?keys as key>
						    <option value="${key}" <#if p.params.status??><#if "${(p.params.status)!}"=="${key}"> selected="selected" </#if></#if> >${key}-${state[key]}</option>
						    </#list>
						    </select>&nbsp;
					</td>
					<td>
						<div class="buttonActive" style="margin-right:15px;"><div class="buttonContent"><button type="submit">检索</button></div></div>
						<input type="hidden" name="carte_id" value="${(carte_id)! }"/>
						<a class="button" href="${base}/?ckparam=${encryption("/${action}/roleseniorlist")}&carte_id=${(carte_id)! }" target="dialog" mask="true" title="查询框" width="420" height="450"><span>高级检索</span></a>
					</td>
					<td></td>
				<tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar">
		<ul class="toolBar">
			<li><a class="add permission" href="${base}/?ckparam=${encryption("/${action}/add/")}&type=1" rel="UserInfo_add" target="dialog" width="390" height="350" ><span>添加业务员</span></a></li>
			<li><a class="edit permission" href="${base}/?ckparam=${encryption("/${action}/update/")}&user_code={USER_CODE}" rel="UserInfo_update" target="dialog" width="390" height="300"><span>修改业务员</span></a></li>
			<li><a class="delete permission" href="${base}/?ckparam=${encryption("/${action}/deleteUserinfo/")}&user_code={USER_CODE}" rel="UserInfo_deleteUserinfo" target="ajaxTodo" title="确定要删除{REAL_NAME}吗?"><span>删除业务员</span></a></li>
			<li><a class="delete permission" href="${base}/?ckparam=${encryption("/${action}/updateStatus/")}&user_code={USER_CODE}" rel="UserInfo_updateStatus" target="ajaxTodo" title="确定要注销{REAL_NAME}吗?"><span>注销业务员</span></a></li>
		</ul>
	</div>
	<table class="table" width="98%" layoutH="109">
		<thead>
			<tr>
				<th width="4%" orderField="real_name">真实姓名</th>
				<th width="8%" orderField="link_phone">联系电话</th>
				<th width="5%" orderField="user_code">登录名</th>
				<th width="5%" orderField="company_no">所属公司 </th>
				<th width="6%" orderField="qq">QQ</th>
				<th width="8%" orderField="email">Email</th>
				<th width="5%" orderField="add_user">添加人</th>
				<th width="11%" orderField="add_date">添加日期</th>
				<th width="8%" orderField="last_login_ip">最后登录ip</th>
				<th width="11%" orderField="last_login_date">最后一次登录时间</th>
				<th width="5%" orderField="login_num">登录次数</th>
				<th width="4%" orderField="status">状态</th>
			</tr>
		</thead>
		<tbody>
			<#list p.results as list >
				<tr target="USER_CODE" rel="${(list.USER_CODE)!}" >
					<td target="REAL_NAME" rel="${(list.REAL_NAME)!}">${(list.REAL_NAME)!'暂无'}</td>
					<td >${(list.LINK_PHONE)!'暂无'}</td>
					<td >${(list.USER_CODE)!'暂无'}----${(list.COMPANY_NO)!''}</td>
					<td><#if company?? >${(company[list.COMPANY_NO+''])!''} [${(list.COMPANY_NO)!'暂无'}]<#else>${(list.COMPANY_NO)!'暂无'}</#if></td>
					<td >${(list.QQ)!'暂无'}</TD>
					<td >${(list.EMAIL)!'暂无'}</td>
					<td >${(list.ADD_USER)!'暂无'}</td>
					<td >${(list.ADD_DATE)!'暂无'}</td>
					<td >${(list.LAST_LOGIN_IP)!'暂无'}</td>
					<td >${(list.LAST_LOGIN_DATE)!'暂无'}</td>
					<td >${(list.LOGIN_NUM)!'暂无'}</td>
					<td><#if state?? >${(state[(list.STATUS)+""])!} [${(list.STATUS)!}]<#else>${(list.STATUS)!'暂无'}</#if></td>
				</tr>
			</#list>
		</tbody>
	</table>
	<#include "/common/pages.ftl">
</div>

