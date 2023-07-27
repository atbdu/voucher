<#include "/common/include.ftl">
<body class="gray-bg">
<div class="search_panel">
 
        <form class="form-horizontal">
            <div class="form-group">
                <div class="col-sm-2">
                    <!-- 自定义搜索框 -->
	                <div class="input-group m-b"><span class="input-group-addon">用户名</span>
	                       <input type="text" name="user_code"  class="form-control search_field input-sm"  />
	                 </div>
                </div>
                 <div class="col-sm-2">
					 <div class='input-group date ' id='datetimepicker2' data-date-format="yyyy-mm-dd">
						<input type='text' name="min_date" class="form-control input-sm search_field" />
						<span class="input-group-addon">
						    <span class="glyphicon glyphicon-calendar"></span>
						</span>
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
<div class="ibox-content table-panel">
    <table id="table" total="0" data-sort-name="user_code" data-sort-order="desc" data-url="/voucher/?ckparam=${encryption("/LoginLog/list_json/")}">
       	<thead>
    		<tr>
    		<th  data-field="user_code">登录名</th>
    		<th  data-field="status">登录状态</th>
    		<th  data-field="login_ip">登录IP</th>
    		<th  data-field="add_date" data-sortable="true">日期</th>
    		<th  data-field="add_time" data-sortable="true">时间</th>
    		</tr>
    	</thead>
    	<tbody>
    	   	<td>{user_code}</td>
    		<td>{status}</td>
    		<td>{login_ip}</td>
    		<td>{add_date}</td>
    		<td>{add_time}</td>
    		</tr>
    	</tbody>
    </table>
</div>
</body>
<script src="/voucher/public/js/plugins/bootstrap-table/init-table-css.js"></script>





