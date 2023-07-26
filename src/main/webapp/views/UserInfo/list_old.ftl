<#include "/common/include.ftl">
<body class="gray-bg">
<div class="search_panel">
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2">
                    <!-- 自定义搜索框 -->
	                <div class="input-group m-b"><span class="input-group-addon">姓名</span>
	                       <input type="text" name="real_name"  class="form-control search_field input-sm"  onkeydown="javascript:if(event.keyCode==13)   initTable();" />
	                 </div>
                </div>
                <div class="col-sm-2">
	                  <div class="input-group m-b"><span class="input-group-addon">用户名</span>
	                       <input type="text" name="user_code"  class="form-control search_field input-sm"  onkeydown="javascript:if(event.keyCode==13)   initTable();" />
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
                 
            </div>
        </form>  
</div>
<div class="ibox-content table_panel">
    <table id="table" total="0" data-sort-name="user_code" data-sort-order="desc" data-url="/voucher/?ckparam=${encryption("/UserInfo/list_json/")}">
       	<thead>
    		<tr>
    		<th  data-field="user_code" data-sortable="true">用户名</th>
    		<th  data-field="status" data-sortable="true">状态</th>
    		<th  data-field="real_name" data-sortable="true">姓名</th>
    		<th  data-field="last_login_date" data-sortable="true">最后登陆</th>
    		<th  data-field="last_login_ip">登陆IP</th>
    		</tr>
    	</thead>
    	<tbody>
    	   	<td><i class="fa fa-thumbs-up"></i>{user_code}</td>
    		<td>{status}</td>
    		<td>{real_name}</td>
    		<td>{last_login_date}</td>
    		<td>{last_login_ip}</td>
    		</tr>
    	</tbody>
    </table>
</div>
</body>
<script src="/voucher/public/js/plugins/bootstrap-table/init-table-css.js"></script>

