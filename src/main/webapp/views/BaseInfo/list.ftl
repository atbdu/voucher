<#include "/common/include.ftl">
<body class="gray-bg">

<div class="search_panel">
          <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-3">
                    <!-- 自定义搜索框 -->
	                <div class="input-group m-b"><span class="input-group-addon">所属类型</span>
	                 			<select name="class_en" class="form-control search_field">
								<option value="">- -请选择所属类型- -</option> 
								<#if keyvaluelist ??>
									<#list keyvaluelist as key>
										<option value="${key.CLASS_EN}">${key.CLASS_CN}</option>
									</#list> 
								</#if>
							</select>
	                 </div>
                </div>
                <div class="col-sm-2">
	                  <div class="input-group m-b"><span class="input-group-addon">信息值</span>
	                       <input type="text" name="info"  class="form-control search_field input-sm"  onkeydown="javascript:if(event.keyCode==13)   initTable();" />
	                 </div>
                </div>
                <div  class="col-sm-3">
                    <button type="button" class="btn btn-primary btn-small input-sm" id="queryBtn">
                        <span class="glyphicon glyphicon-search "></span>搜索
                    </button>
                     <button type="reset" class="btn btn-primary btn-small input-sm">
                        <span class="glyphicon glyphicon-search "></span>重置
                    </button>
                </div>
                 <div  class="col-sm-3">
             	  <button type="button" class="btn btn-info btn-small openWin" wwidth="400px" wheight="380px" whref="/voucher/?ckparam=${encryption("/BaseInfo/add/")}" >
                    <span class="glyphicon glyphicon-plus "></span>添加基本信息
                  </button>
             	  </div>
            </div>
        </form>
</div>
<div class="ibox-content table_panel">
    <table id="table" total="0" data-sort-name="user_code" data-sort-order="desc" data-url="/voucher/?ckparam=${encryption("/BaseInfo/list_json/")}">
       	<thead>
    		<tr>
    		<th  data-field="class_en" data-sortable="true">类别英文</th>
    		<th  data-field="class_cn" data-sortable="true">类别中文</th>
    		<th  data-field="info_en">信息标识</th>
    		<th  data-field="info">信息值</th>
    		<th  data-field="status">状态</th>
    		<th  data-field="add_date">添加日期</th>
    		<th  data-field="real_name">添加人</th>
    		</tr>
    	</thead>
    	<tbody>
    	   	<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		</tr>
    	</tbody>
    </table>
</div>
</body>
<script src="/voucher/public/js/plugins/bootstrap-table/init-table-css.js"></script>


