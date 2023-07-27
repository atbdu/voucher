<div id="jtree" style="margin-left:15px">
	<ul class="tree treeFolder collapse clickEvent">
		<#if cartes ?? >
			<#list cartes as item>
				<#if item["LEVELS"] == 1>
					<li data-jstree='{"icon":"fa ${item['REL']}"}'>		
						<a ajax_href="${base}/?ckparam=${encryption("/Carte/prepare/?carte_no=${item['CARTE_NO']}&tag_system=${tag_system}&carte_id=${item['ID']}")}"  class="ajax_html" ajax_html="menu_content">${item["CARTE_NAME"]}</a>
					<#if (cartes[item_index + 1]) ??>
						<#if (cartes[item_index + 1]["LEVELS"] > item["LEVELS"])>
							<ul>
						<#else>
							</li>
						</#if>
					</#if>
				</#if>
				<#if item["LEVELS"] == 2>
					<li>
						<a ajax_href="${base}/?ckparam=${encryption("/Carte/prepare/?carte_no=${item['CARTE_NO']}&tag_system=${tag_system}&carte_id=${item['ID']}")}" class="ajax_html" ajax_html="menu_content">${item["CARTE_NAME"]}</a>
					<#if (cartes[item_index + 1]) ??>
						<#if (cartes[item_index + 1]["LEVELS"] > item["LEVELS"])>
							<ul>
						<#elseif (cartes[item_index + 1]["LEVELS"] < item["LEVELS"])>
							</li></ul></li>
						<#else>
							</li>
						</#if>
					</#if>
				</#if>
				<#if item["LEVELS"] == 3>
					<li>
						<a ajax_href="${base}/?ckparam=${encryption("/Carte/prepare/?carte_no=${item['CARTE_NO']}&tag_system=${tag_system}&carte_id=${item['ID']}")}" class="ajax_html" ajax_html="menu_content">${item["CARTE_NAME"]}</a>
					<#if (cartes[item_index + 1]) ??>
						<#if (cartes[item_index + 1]["LEVELS"] == 2)>
							</ul></li>
						<#elseif cartes[item_index + 1]["LEVELS"] == 1>
							</ul></li></ul></li>
						<#elseif cartes[item_index + 1]["LEVELS"] == item.LEVELS>
							</li>
						<#elseif (cartes[item_index + 1]["LEVELS"] > item.LEVELS)>
							<ul>
						</#if>
					</#if>
				</#if>
				<#if item["LEVELS"] == 4>
					<li>
						<a ajax_href="${base}/?ckparam=${encryption("/Carte/prepare/?carte_no=${item['CARTE_NO']}&tag_system=${tag_system}&carte_id=${item['ID']}")}" class="ajax_html" ajax_html="menu_content">${item["CARTE_NAME"]}</a>
					</li>
					<#if (cartes[item_index + 1]) ??>
						<#if cartes[item_index + 1]["LEVELS"] == 2>
							</ul></li></ul></li>
						<#elseif cartes[item_index + 1]["LEVELS"] == 1>
							</ul></li></ul></li></ul></li>
						<#elseif cartes[item_index + 1]["LEVELS"] == 3>
							</ul></li>
						</#if>
					</#if>
				</#if>
				<#if item_index == (cartes?size-1)>
					<#if item["LEVELS"] == 1>
						</li>
					<#elseif item["LEVELS"] == 2>
						</li>
						</ul>
						</li>
					<#elseif item["LEVELS"] == 3>
						</ul>
						</li>
						</ul>
						</li>
					<#else>
						</ul>
						</li>
						</ul>
						</li>
						</ul>
						</li>
					</#if>
				</#if>
			</#list>
		</#if>
	</ul>
</div>
<div class="text-left" style="position:fixed;bottom:15px;width:100%;left:0px">
	<div class="col-sm-3" >
				<button type="button" class="btn btn-block btn-info ajax_html" ajax_href="${base}/?ckparam=${encryption("/Carte/prepare/?carte_no=0&tag_system=${tag_system}")}" id="add_menu" ajax_html="menu_content">添加菜单</button>
	</div>
</div>