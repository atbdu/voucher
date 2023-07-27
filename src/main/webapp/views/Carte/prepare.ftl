<meta charset="UTF-8" />
<div class="tabs-container">
    <ul class="nav nav-tabs">
        <li class="active"><a data-toggle="tab" href="#tab-1" aria-expanded="true"><i class="fa fa-server"></i>${(map["CARTE_NAME"])?default("添加菜单")}&nbsp;<i class="glyphicon glyphicon-cog"></i>基本信息</a>
        </li>
        <#if map?? &&map['HREF']!="*">
        <li class=""><a class="ajax_html" data-toggle="tab"  ajax_href="${base}/?ckparam=${encryption("/Permission/list/?carte_id=${(map['CARTE_NO'])!}&system_tag=${(tag_system)!}")}" ajax_html="tab-2" href="#tab-2"><i class="glyphicon glyphicon-th-large"></i>按钮/行为</a>
        </li>
			<li class=""><a class="ajax_html" data-toggle="tab" ajax_href="${base}/?ckparam=${encryption("/DataPermission/list/?carte_id=${(map['CARTE_NO'])!}&system_tag=${(tag_system)!}")}" ajax_html="tab-3" href="#tab-3" aria-expanded="false"> <i class="glyphicon glyphicon-signal"></i>数据权限</a>
        </li>
        </#if>
    </ul>
    <div class="tab-content">
        <div id="tab-1" class="tab-pane active">
            <div class="panel-body" style="min-height:calc(100vh - 90px);">
                <form method="post" action="${base}/?ckparam=${encryption("/Carte/save/")}" class="form-horizontal required-validate" onsubmit="return window.parent.validateCallback(this, 'dialogAjaxDone');">
				<!-- 平台标识 -->
				<input type="hidden" name="tag_system" id="tag_system" value="${(tag_system)!}"/>
				<input type="hidden" name="oldCarteNo" value="${(map['CARTE_NO'])!}" id="carte_no"/>
				<input name="parentNo" value="${(map['PARENT_CARTE_NO'])!'0'}" type = "hidden"/>
				<input name="operateLogo" value="${(map['OPERATELOGO'])!'0'}" type = "hidden"/>
				<input name="id" id="menu_id" value="${(map['ID'])!}" type = "hidden"/>
				<!-- END -->
				
			     <div class="hr-line-dashed"></div>
					<div class="form-group  m-t-md">
						<label class="col-sm-2 control-label">菜单名称：</label>
						<div class="col-sm-4">
						<input name="carte_name" type="text" class="form-control required" value="${(map['CARTE_NAME'])!}"/>
						</div>
						<div class="col-sm-6">
						<span class="help-block">10个字以内</span>
						</div>
					</div>
			
					<div class="form-group">
						<label class="col-sm-2 control-label">菜单URL：</label>
						<div class="col-sm-5">
							<input name="href" type="text" class="form-control required" value="${(map['HREF'])!}"/>
						</div>
						<div class="col-sm-5">
						<span class="help-block">例如：BackZbPosPriceShow/list/，父级菜单赋 *</span>
						</div>
					</div>
					
					<div class="form-group">
						<label class="col-sm-2 control-label">菜单图标：</label>
						<div class="col-sm-4">
						<input name="rel" type="text" class="form-control required" value="${(map['REL'])!}"/>
						</div>
						<div class="col-sm-6">
						<span class="help-block" style="margin-left:10px">例如：fa-database</span>
						</div>
					</div>
				
					<div class="form-group">
					    <label class="col-sm-2 control-label">打开方式：</label>
					    <div class="col-sm-4">
						<select name="target"  class="form-control">			
							<option value="navTab" >navTab-标签</option>
							<option value="dialog" >dialog-弹窗</option>
						</select>
						</div>
						<div class="col-sm-6">
						<span class="help-block" style="margin-left:10px">分两种：弹出窗（dialog）标签模式（navTab）</span>
						</div>
					</div>
				
					<div class="form-group">
						<label class="col-sm-2 control-label">上级菜单：</label>
						<div class="col-sm-4">
						<select name="organ_nb" class="form-control required"  selectvl="${(map["PARENT_CARTE_NO"])!"0"}">
						<option value="0">根菜单</option>
						<#list menuList as list>
							<option value="${list.CARTE_NO}" <#if map??><#if map["PARENT_CARTE_NO"]??&&map["PARENT_CARTE_NO"]=list.CARTE_NO>selected="true"</#if></#if>><#if list["LEVELS"] == 2>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</#if>${list.CARTE_NAME}</option>
						</#list>
						</select>
						</div>
						<div class="col-sm-6">
						<span class="help-block" style="margin-left:10px">如果是一级菜单，请选择“根菜单”</span>
						</div>
						<input type="hidden" name="organ_nb" value="${(map["PARENT_CARTE_NO"])!"0"}"><!--随选择而变-->
					</div>
					
					<div class="form-group" >
						<label class="col-sm-2 control-label">菜单介绍：</label>
					    <div class="col-sm-10">
						<textarea  name="carte_intro" class="form-control required" style="width:96%;height:120px">${(map["CARTE_INTRO"])!}</textarea>
						</div>
					</div>
					<div class="hr-line-dashed"></div>
					<div class="form-group  col-sm-8 ">
						<button type="submit" class="btn btn-block btn-info col-sm-offset-3">保存菜单基本信息</button>
					</div>
				
				</form>
            </div>
        </div>
        <#if map?? &&map['HREF']!="*" >
        <div id="tab-2" class="tab-pane"  >
            <div class="panel-body" style="height:calc(100vh - 90px);">
                等待加载...
            </div>
        </div>
           <div id="tab-3" class="tab-pane">
            <div class="panel-body"   style="height:calc(100vh - 90px);">
                <strong>该功能预留...</strong>
                <div class="form-group">等待开发..</div>
            </div>
        </div>
       </#if>
    </div>
</div>