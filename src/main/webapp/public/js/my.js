$(function(){
    $(".date").each(function(){
        var date_view=$(this).attr("date_view")||0;//日期 0/日 1/月 2/年
        var format_str="yyyy-mm-dd";
        date_view=parseInt(date_view);
        switch (date_view){
            case 0:
                format_str="yyyy-mm-dd"
                break;
            case 1:
                format_str="yyyy-mm"
                break;
            case 2:
                format_str="yyyy"
                break;
        }
        format_str=$(this).attr("date_format")||format_str;
        $(this).datepicker({
            language: "zh-CN",
            autoclose: true,
            startView:date_view,
            maxViewMode:date_view,
            minViewMode: date_view,
            format:format_str
        });
    });
	//解决table 错位问题
	$(window).resize(function () {
        $('#table').bootstrapTable('resetView');
    });

	$(document).on("click",".openWin",function(){
		window.parent.clickOpenWin($(this));
	})
     $('.i-checks').iCheck({
         checkboxClass: 'icheckbox_square-green',
         radioClass: 'iradio_square-green',
     });
    //下拉列表框选中
    $("select[selectvl]").each(function(){
        var selectvl=$(this).attr("selectvl");
        if($(this).is('[multiple]')){
        	$(this).val((selectvl.split(",")));
        }else{
        	$(this).val(selectvl);
        }
        if($(this).attr("selectvl_event")){   //如果需要触发选中事件。
            $(this).change();
        }
    });
	$(".select2").select2();

    $(document).on("click",".ajaxToDo",function(){
    	window.parent.ajaxToDo($(this));
     })
     
     $("[ptip]").each(function(){
         $this=$(this);
         var tip=$this.attr("ptip");
         var op = {
             content:tip,
             fade:true          
         };
         
         if($this.attr("type")=="text"){
             op["showon"]="focus";
         }
         var upAttrs = ["className","showOn","alignTo","alignX","alignY","offsetX","offsetY"];
         $(upAttrs).each(function(i){
             var true_op="ptip_"+upAttrs[i];//tip_showOn....都加上tip以免和别的冲突
             var op_name=upAttrs[i];
             if($this.attr(true_op)){
                 op[op_name]=$this.attr(true_op);
             }
         });
         $this.poshytip(op);
     });
})