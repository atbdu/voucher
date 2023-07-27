<#include "/common/include.ftl">
	<form method="post" action="${base}/?ckparam=${encryption(" class="form-horizontal required-validate" onsubmit="return window.parent.validateCallback(this, 'dialogAjaxDone');">
		<div class="ibox-content" >
				<input type="hidden" name="id" value="${(baseInfo.id)!}">
				<div class="form-group">
					<label class="col-xs-4 control-label small_dialog_lable">所属类型：</label>
					<div class="col-xs-7">
					<select name="class_en" id="ckclass_en" class="required form-control"  >
						<option value="">- -请选择所属类型- -</option>
						<#if keyvaluelist ??>
							<#list keyvaluelist as key>
								<option value="${key.CLASS_EN}" <#if ((baseInfo.class_en)!)==key.CLASS_EN>selected</#if>>${key.CLASS_CN}</option>
							</#list>
						</#if>
					</select>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label small_dialog_lable">信息标识：</label>
					<div class="col-xs-7">
					<input type="text" name="info_en" id="ckinfo_en"  value="${(baseInfo.info_en)!}" class="required form-control"  />
				    </div>
				</div>
				<div class="form-group">
					<label class="col-xs-4 control-label small_dialog_lable">信息文字：</label>
					<div class="col-xs-7">
					<input type="text" name="info" class="required form-control" value="${(baseInfo.info)!}"/>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-4 control-label">启用状态：</label>
					<div class="col-xs-7">
						<div class="radio i-checks">
	                      <label style="padding-left:0px">
						<input id="one" type="radio" class="form-control" value="1" name="status" <#if ((baseInfo.status)!)==1>checked</#if> <#if baseInfo.status??>checked</#if> > 启用
						  </label>
						   <label>
						<input id="two" type="radio" class="form-control" value="2" name="status" <#if ((baseInfo.status)!)==2>checked</#if> > 禁用
						  </label>
						</div>
					</div>
				</div>
				<div class="form-group navbar-fixed-bottom dialog_footer">
					<div class="col-xs-6 col-xs-offset-3" >
					   <button type="submit" class="btn btn-primary btn-block">
                        	保存信息
                       </button>
                    </div>
				</div>
		</div>	
	</form>


