var columnsArray= []; 

$(document).ready(function () {
    initTCss();
    initTable();
     $("#queryBtn").bind("click", initTable);
});

function initTCss(){
	var ths=$("#table").find("th");
	var tds=$("#table").find("td");
	$.each(ths, function(i, item){     
   		var field=$(item).attr("data-field");
		var title=$(item).text();
		columnsArray.push({field:field,title:title,formatter:function(value,row,index){
			var ht=$(tds[i]).html();
			if(ht==""){
				return value;
			}
			ht=ht.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
		    var vkey=$1.replace("{","").replace("}","");
				return row[vkey] ? row[vkey] :"";
			});
			if($(tds[i]).hasClass("tmpl")){
				ht= template.render(ht,row);
			}
			return ht;
		}
       });    
     });     


}
function initTable() {    //先销毁表格
    $('#table').bootstrapTable('destroy');
    $('#table').bootstrapTable({
        contentType: "application/x-www-form-urlencoded;charset=utf-8",
        method: "post",
        dataType: "json",
        dataField: 'rows',
        undefinedText: "空",//当数据为 undefined 时显示的字符
        pagination: true, //设置为 true 会在表格底部显示分页条。
        showToggle: "false",//是否显示 切换试图（table/card）按钮
        pageNumber: 1,//初始化加载第一页，默认第一页
        pageSize: 20,//每页的记录行数（*）
        pageList: [20, 50, 100],//可供选择的每页的行数（*），当记录条数大于最小可选择条数时才会出现
        paginationPreText: '上一页',
        paginationNextText: '下一页',
        height: $(window).height() - 68,
        search: false, //是否显示表格搜索,bootstrap-table服务器分页不能使用搜索功能，可以自定义搜索框，上面jsp中已经给出，操作方法也已经给出
        striped : true,//隔行变色
        showColumns: false,//是否显示 内容列下拉框
        showToggle: false, //是否显示详细视图和列表视图的切换按钮
        data_local: "zh-US",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParamsType : "undefined",//设置为 ‘limit’ 则会发送符合 RESTFul 格式的参数.
        totalRecord:3,
        queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
//            请求服务器数据时，你可以通过重写参数的方式添加一些额外的参数，例如 toolbar 中的参数 如果
//　　　　　　 queryParamsType = 'limit' ,返回参数必须包含limit, offset, search, sort, order 
//            queryParamsType = 'undefined', 返回参数必须包含: pageSize, pageNumber, searchText, sortName, sortOrder. 
//            返回false将会终止请求。
            var query_options= {                          //这里的params是table提供的
                pageNum:params.pageNumber,        //当前页数
                numPerPage:params.pageSize,         //每页多少条
                totalRecord:$("#table").attr("total"),
                orderField:params.sortName,
                orderDirection: params.sortOrder
            };
           $(".search_field").each(function(){
            	var vl=$(this).val();
            	var fname=$(this).attr("name");
            	if(vl!=null&&vl!=""){
            		query_options[fname]=vl;
            	}
            })
            return query_options;
        },
        responseHandler: function (res) {
　　　　　　//如果后台返回的json格式不是{rows:[{...},{...}],total:100},可以在这块处理成这样的格式
			if(res.total!=null){
				$("#table").attr("total",res.total);
			}
　　　　　　return res;
        },
        columns:columnsArray,
        onLoadSuccess: function () {
        },
        onLoadError: function () {
            showTips("数据加载失败！");
        }
    });
}