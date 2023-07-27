<#include "/common/include.ftl">
<body class="gray-bg">

<div class="search_panel">
          <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-3">
                    <!-- 自定义搜索框 -->
	                <div class="input-group m-b"><span class="input-group-addon">英文名称</span>
	                 		<input type="text" name="class_en"  class="form-control search_field input-sm"  onkeydown="javascript:if(event.keyCode==13)   initTable();" />
	                 		
	                 </div>
                </div>
                <div class="col-sm-2">
	                  <div class="input-group m-b"><span class="input-group-addon">中文名称</span>
	                       <input type="text" name="class_cn"  class="form-control search_field input-sm"  onkeydown="javascript:if(event.keyCode==13)   initTable();" />
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
             	  <button type="button" class="btn btn-info btn-small openWin" wwidth="400px" wheight="380px" whref="/voucher/?ckparam=${encryption("/BaseInfoClass/add/")}" >
                    <span class="glyphicon glyphicon-plus "></span>添加基本信息类
                  </button>
             	  </div>
            </div>
        </form>
</div>
<div class="ibox-content table_panel">
    <table id="table" total="0" data-sort-name="add_date" date-height="auto" data-sort-order="desc" data-url="/voucher/?ckparam=${encryption("/BaseInfoClass/list_json/")}">
       	<thead>
    		<tr>
    		<th  data-width="10%" data-field="class_en" data-sortable="true">类别英文</th>
    		<th  data-width="10%" data-field="class_cn" data-sortable="true">类别中文</th>
    		<th  data-field="ramark">备注</th>
    		<th data-width="50px" data-field="status" data-sortable="true">状态</th>
    		<th  data-field="add_date" data-sortable="true">添加日期</th>
    		<th  data-width="10%" data-field="real_name">添加人</th>
    		<th data-width="50px">操作</th>
    		</tr>
    	</thead>
    	<tbody>
    	   	<td></td>
    	   	<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td></td>
    		<td>
    		<div class="btn-group">
                <button data-toggle="dropdown" class="btn btn-warning btn-sm dropdown-toggle">操作 <span class="caret"></span>
                </button>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li><a href="#">修改</a>
                    </li>
                    <li><a href="buttons.html#">禁用/启用</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="#" ajax_url="/voucher/?ckparam=${encryption("/BaseInfoClass/delete/")}&class_en={class_en}" ajax_title="确定删除{class_cn}吗？" class="ajaxToDo">删除</a>
                    </li>
                </ul>
             </div>
    		</td>
    		</tr>
    	</tbody>
    </table>
</div>
</body>
<script src="/voucher/public/js/plugins/bootstrap-table/init-table-css.js"></script>



