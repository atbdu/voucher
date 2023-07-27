<#include "/common/include.ftl">
<script src="/partwork/public/js/plugins/jsTree/jstree.min.js?v=2.1.4"></script>
<link href="/partwork/public/css/plugins/jsTree/style.min.css" rel="stylesheet">
<body class="gray-bg">
<div class="m-t-xs m-l-xs">
	   <div class="row">
   			
		    <div class="col-sm-3" style="padding-right:0px">
		            
		            <div class="ibox-content" style="min-height:calc(100vh - 10px)">
		            <div class="text-center">
					 <div class="btn-group">
					    <a class="btn <#if tag_system=='0'>btn-info<#else>btn-white</#if>" href="<#if tag_system=='0'>#<#else>${base}/?ckparam=${encryption("/Carte/list/?tag_system=0")}</#if>" type="button"><i class="fa fa-heart"></i>&nbsp;&nbsp;核心平台&nbsp;&nbsp;</a>
					    <a class="btn <#if tag_system=='1'>btn-info<#else>btn-white</#if>" href="<#if tag_system=='1'>#<#else>${base}/?ckparam=${encryption("/Carte/list/?tag_system=1")}</#if>" type="button"><i class="fa fa-home"></i>&nbsp;&nbsp;商户平台&nbsp;&nbsp;</a>
					 </div>
					</div>
					<div class="hr-line-dashed"></div>
		                <#include "menus.ftl">
		            </div>
		     </div>
		      <div class="col-sm-9">
		            <div class="ibox-content" style="min-height:calc(100vh - 10px);" id="menu_content">
		            	
		            </div>
		     </div>
	   </div>
 </div>   
 <script>
     $(function(){
     	$('#jtree').jstree();
     	$(document).on("click",".ajax_html",function(){
     		var ajax_href=$(this).attr("ajax_href");
     		var ajax_html=$(this).attr("ajax_html");
     		 $.get(ajax_href, function(result){
			      $("#"+ajax_html).html(result);
			  });
     	})
     	$("#add_menu").click();
     })
 </script>
             