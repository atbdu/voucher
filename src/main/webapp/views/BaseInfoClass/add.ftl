<#include "/common/include.ftl">
	<form method="post" action="${base}/?ckparam=${encryption(" class="form-horizontal required-validate" onsubmit="return window.parent.validateCallback(this,'dialogAjaxDone');">
		<div class="ibox-content">
				<div class="form-group">
					<label class="col-xs-4 control-label small_dialog_lable">英文名称：</label>
					<div class="col-xs-7">
					<input type="text" name="class_en" class="form-control required"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label small_dialog_lable">中文名称：</label>
					<div class="col-xs-7 ml-3">
					<input type="text" name="class_cn" class="form-control required" value="${(baseInfoClass.class_cn)!}"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">启用状态：</label>
					<div class="col-xs-7">
						<div class="radio i-checks">
	                      <label style="padding-left:0px">
						<input id="one" type="radio" class="form-control" value="1" name="status" <#if ((baseInfoClass.status)!)==1>checked</#if> <#if baseInfoClass.status??>checked</#if> > 启用
						  </label>
						   <label>
						<input id="two" type="radio" class="form-control" value="2" name="status" <#if ((baseInfoClass.status)!)==2>checked</#if> > 禁用
						  </label>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</label>
					<div class="col-xs-7">
					<textarea type="text" name="ramark" class="form-control">${(baseInfoClass.ramark)!}</textarea>
					</div>
				</div>
				<div class="form-group navbar-fixed-bottom dialog_footer">				
					<div class="col-xs-6 col-xs-offset-3" >
					   <button type="submit" class="btn btn-primary btn-block">
                        	保存
                       </button>
                    </div>
				</div>
		</div>
	</form>


