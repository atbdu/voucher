<link rel="stylesheet" type="text/css" href="${base}/public/css/zTreeStyle/zTreeStyle.css"/>
<script type="text/javascript" language="javascript" src="${base}/public/treejs/jquery.ztree.core-3.1.js"></script>
<script type="text/javascript" language="javascript" src="${base}/public/treejs/jquery.ztree.excheck-3.1.js"></script>
<script type="text/javascript" language="javascript">
var zTreeObj;
var setting = {
	check:{
		enable:true
	},
	data:{
		simpleData:{
			enable: true,
			idKey: "carteNo",
			pIdKey: "parentNo",
			rootPId: "0"
	    }
    }
};
var zNodes = eval(${json});
$(document).ready(function(){
	zTreeObj = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});
function commitTreeForm(form){
	var nodes = zTreeObj.getCheckedNodes(true);
	var retObj = "";
	for (var i = 0; i < nodes.length; i++){
		if ( i == nodes.length -1){
			retObj += nodes[i].carteId;		
		} else {
			retObj += nodes[i].carteId + ",";
		}
	}
	$("#stringCarte").val(retObj);
	return validateCallback(form, dialogAjaxDone);
}
</script>
<div id="resultBox"></div>

<div style=" float:left; display:block; overflow:auto; width:410px; height:570px; overflow:auto; border:solid 1px #CCC; line-height:21px; background:#FFF;" id="cc" class="pageContent">
	<form method="post" id="treeForm" action="${base}/?ckparam=${encryption("/Userallot/save/")}" class="pageForm required-validate" onsubmit="return commitTreeForm(this)">
		<div style="overflow: auto;" layoutH="40">
		<input type="hidden" name="user_code" value="${user_code}"/>
		<input type="hidden" name="user_logo" value="${user_logo}"/>
		<input type="hidden" id="stringCarte" name="stringCartes" value="ss"/>
			<p>
				<ul id="treeDemo" class="ztree"></ul>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent"><button type="submit">提交</button></div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent"><button type="button" class="close">取消</button></div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>