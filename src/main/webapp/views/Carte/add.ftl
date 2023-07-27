
<div class="pageContent" style="overflow:hidden;">
	<form method="post" action="${base}/?spm=${action}/save" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="50" style="background-color:#ecf2f4;overflow:hidden;padding-bottom:0">
			<fieldset>
				<p>
					<label style="width:60px;">父级编号</label>
					<input type="text" name="parent_carte_no" value="${(carte.parent_carte_no)!}"/>
				</p>
				<p>
					<label style="width:60px;">所属平台 </label>
					<input type="text" name="system_tag" value="${(carte.system_tag)!}"/>
				</p>
				<p>
					<label style="width:60px;">菜单介绍</label>
					<input type="text" name="carte_intro" value="${(carte.carte_intro)!}"/>
				</p>
				<p>
					<label style="width:60px;">菜单等级</label>
					<input type="text" name="levels" value="${(carte.levels)!}"/>
				</p>
				<p>
					<label style="width:60px;">菜单名称</label>
					<input type="text" name="carte_name" value="${(carte.carte_name)!}"/>
				</p>
				<p>
					<label style="width:60px;">打开方式</label>
					<input type="text" name="target" value="${(carte.target)!}"/>
				</p>
				<p>
					<label style="width:60px;">添加日期yyyy-MM-dd HH:MM:ss</label>
					<input type="text" name="add_date" value="${(carte.add_date)!}"/>
				</p>
				<p>
					<label style="width:60px;">REl属性</label>
					<input type="text" name="rel" value="${(carte.rel)!}"/>
				</p>
				<p>
					<label style="width:60px;">添加人</label>
					<input type="text" name="add_user" value="${(carte.add_user)!}"/>
				</p>
				<p>
					<label style="width:60px;">菜单编号（主键）</label>
					<input type="text" name="carte_no" value="${(carte.carte_no)!}"/>
				</p>
				<p>
					<label style="width:60px;">请求地址</label>
					<input type="text" name="href" value="${(carte.href)!}"/>
				</p>
				<input type="hidden" name="id" value="${(carte.id)!}">
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

