function clickOpenWin($this){
	var whref=$this.attr("whref")?$this.attr("whref"):"";
	if(whref.indexOf('?') > -1){
		whref=whref+"&"+rnd(1,1000000);
	}else{
		whref=whref+"?"+rnd(1,1000000);
	}
	var wtitle=$this.attr("wtitle")?$this.attr("wtitle"):($this.text()?$this.text():"");
    var wwidth=$this.attr("wwidth")?$this.attr("wwidth"):"auto";
    var wheight=$this.attr("wheight")?$this.attr("wheight"):"auto";
    layer.open({
    	  type: 2,
    	  skin:'layui-layer-molv',
    	  title:wtitle,
    	  shadeClose: true,
    	  shade: 0.8,
    	  maxmin:true,
    	  scrollbar:false, 
    	  area: [wwidth,wheight],
    	  content:whref //iframe的url
    }); 
}
function rnd(n, m){
	   return Math.floor(Math.random()*(m-n+1)+n)
}
//后退
function rep() {
    $(".J_iframe:visible")[0].contentWindow.history.go(-1);
}

//刷新当前标签
function ref() {
    // $(".J_iframe:visible")[0].contentWindow.location.reload();
    window.frames[$(".page-tabs-content .active").index()].location.reload();
}



/**
 * 普通ajax表单提交
 * @param {Object} form
 * @param {Object} callback
 * @param {String} confirmMsg 提示确认信息
 */
function validateCallback(form, callback) {

	var $form = $(form);
	if (!$form.valid()) {
		return false;
	}
	var _submitFn = function(){
		$.ajax({
			type:$form.attr("method")|| 'POST',
			url:$form.attr("action"),
			data:$form.serializeArray(),			
			dataType:"json",
			cache: false,
			success: dialogAjaxDone ||ajaxDone,
			error:ajaxError
		});
	}
	_submitFn();
	return false;
}

function ajaxToDo($this){
	var titile=$this.attr("ajax_title")||"您确定此操作吗？";
	var url=$this.attr("ajax_url")||"";
	if(url.indexOf('?') > -1){
		url=url+"&"+rnd(1,1000000);
	}else{
		url=url+"?"+rnd(1,1000000);
	}
	var $callback = $this.attr("callback")||'tabAjaxDone';
	$callback = eval('(' + $callback + ')');
	if(url!=""){
		layer.confirm(titile, {
			  btn: ['确定','取消'], //按钮
			  skin: 'layui-layer-molv',
			  icon:3
			}, function(){
				$.ajax({
					type:'GET',
					url:url,
					dataType:"json",
					cache: false,
					success: $callback,
					error:ajaxError
				});
			}, function(){

			});
	}
}

function tabAjaxDone(json){
	ajaxDone(json);
	ref();
}

function dialogAjaxDone(json){

	var message='';
	var icon='1';
	if(json.statusCode==200){
		message=json.message||'操作成功';
		layer.close(layer.index);
	}else{
		message=json.message||'操作失败';
		icon='2';
	}
	layer.msg(message,{icon:icon});
	ref();//刷新当前tab
}



function ajaxError(json){
	layer.msg("操作出错",{icon:2});
}

function ajaxDone(json){
	var message=json.statusCode==200?json.message||'操作成功':json.message||'操作失败';
	var icon=json.statusCode==200?'1':'2';
	layer.msg(message,{icon:icon});
}

$(function(){
	$(".J_tabReply").on("click", rep);
	$(".J_tabRefresh").on("click", ref);
})
