<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>立减金</title>

    <meta name="keywords" content="上海释名信息科技有限公司">
    <meta name="description" content="上海释名信息科技有限公司">

    <!--[if lt IE 8]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

    <link rel="shortcut icon" href="/voucher/favicon.ico" type="image/x-icon" />
    <link rel="icon" href="/voucher/favicon.ico" type="image/x-icon" />
    <link rel="shortcut icon" href="/voucher/favicon.ico">
    <link href="/voucher/public/css/bootstrap.min.css?v=3.3.5" rel="stylesheet">
    <link href="/voucher/public/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link href="/voucher/public/css/animate.min.css" rel="stylesheet">
    <link href="/voucher/public/css/style.min.css?v=4.0.0" rel="stylesheet">
    <link href="/voucher/public/css/alter.css" rel="stylesheet">
</head>


<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
    <div id="wrapper">
        <!--左侧导航开始-->
        <nav class="navbar-default navbar-static-side" role="navigation" style="box-shadow:rgba(0, 0, 0, 0.1) 0px 4px 12px;">
            <div class="nav-close"><i class="fa fa-times-circle"></i>
            </div>
            <div class="sidebar-collapse">
                <ul class="nav menu_nav" id="side-menu">
                    <li class="nav-header" style="padding-bottom: 7px;background:#fff">
                        <div class="dropdown profile-element">
                           <img alt="image" class="imgbox" src="/voucher/public/imgs/logo4.png" onerror="javascript:this.src='/voucher/public/imgs/log.png';" style="width:100%">
                            <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear" style="margin-top:10px;">
                               <span class="block m-t-xs" ><strong class="font-bold" style="color: black">${(Session.clients.client_name)!}</strong></span>
                                <span class="text-muted text-xs block">${(Session.user.real_name)!}<b class="caret"></b></span>
                                </span>
                            </a>
                            <ul class="dropdown-menu animated fadeInRight m-t-xs">
                                <li><a href="/voucher/UserInfo/exitLogin">安全退出</a>
                                </li>
                                    <li><a  href="/voucher/UserInfo/pass">修改密码</a>
                                </li>
                            </ul>
                        </div>
                        <div class="logo-element">立减金
                        </div>
                    </li>
                    <li>
                      <a class="J_menuItem boxa" href="/voucher/?ckparam=${encryption("Index/main")}" data-index="01000000">
                            <img class="J_image" src="/voucher/public/image/navimg/home.png" alt="img" style="width: 22px;height: 22px">
                            <span class="nav-label">平台首页</span>
                        </a>
                    </li>
                    <#if userCartes ?? >
                    <#list userCartes as carte>
                    <#if carte.levels==1>
                    <li>
                        <#if carte.href !="*">
                            <a class="J_menuItem boxa" href="/voucher/?ckparam=${encryption("/${carte.href}")}&carte_no=${carte.carte_no}" data-index="${carte.carte_no}">
                                <img class="J_image" src="/voucher/public/image/navimg/${carte.img_name}.png" alt="img" style="width: 22px;height: 22px">
                                <span class="nav-label">${carte.carte_name}</span>
                            </a>
                        <#else>
                            <a class="boxa" href="#">
                                <img class="J_image" src="/voucher/public/image/navimg/${carte.img_name}.png" alt="img" style="width: 22px;height: 22px">
                                <span class="nav-label">${carte.carte_name}</span>
                                <span class="fa arrow"></span>
                            </a>
                        </#if>
                        <ul class="nav nav-second-level">
                            </#if>
                            <#if carte.levels==2>
                                <li>
                                    <a class="J_menuItem" href="/voucher/?ckparam=${encryption("/${carte.href}")}&carte_no=${carte.carte_no}" data-index="0">${carte.carte_name}</a>
                                </li>
                            </#if>
                            <#if userCartes[carte_index + 1] ?? >
                            <#if (userCartes[carte_index + 1].levels==1)>
                        </ul>
                    </li>
                    </#if>
                    <#else>
                </ul>
                </li>
                </#if>
                </#list>
                </#if>
                </ul>
            </div>
        </nav>
        <!--左侧导航结束-->
        <!--右侧部分开始-->
        <div id="page-wrapper" class="gray-bg dashbard-1">          
            <div class="row content-tabs" style="border-color: #DCDCDC">
			    <div class="roll-nav roll-left navbar-minimalize" style="padding: 0;margin: 0;width: 50px"><img class="more-image" src="/voucher/?ckparam=${encryption("/public/image/more.png")}"></div>
             
                <nav class="page-tabs J_menuTabs">
                    <div class="page-tabs-content">
                        <a href="javascript:;" class="active J_menuTab theme-col" style="width: 80px;height: 42px" id="shouye" data-id="/voucher/?ckparam=${encryption("Index/main")}">平台首页</a>
                    </div>
                </nav>
				 <div class="roll-nav roll-right J_tabLeft bor-not" style="right: 455px">
                     <img class="arrow-image" src="/voucher/?ckparam=${encryption("/public/image/left-ico.png")}">
                </div>
                <div class="roll-nav roll-right J_tabRight bor-not" style="right: 405px">
                    <img class="arrow-image" src="/voucher/?ckparam=${encryption("/public/image/right-ico.png")}">
                </div>
				<a href="javascript:void(0);" class="roll-nav roll-right J_tabReply bor-not" title="返回" style="right: 275px"><img style="width: 16px" src="/voucher/?ckparam=${encryption("/public/image/point-left.png")}"> </a>
				<a href="javascript:void(0);" class="roll-nav roll-right J_tabRefresh bor-not" title="刷新" style="right: 215px"><img style="width: 16px" src="/voucher/?ckparam=${encryption("/public/image/renovate.png")}"> </a>
				<a href="javascript:void(0);" class="roll-nav roll-right J_tabFullScreen bor-not" title="全屏" style="right: 155px"><img style="width: 16px;height: 16px" src="/voucher/?ckparam=${encryption("/public/image/full-screen.png")}"> </a>
				<a href="javascript:void(0);" class="roll-nav roll-right J_notice bor-not" style="right: 100px;display:none" data-toggle="dropdown" aria-expanded="false" title="消息"><img style="width: 15px;height: 16px" src="/voucher/?ckparam=${encryption("/public/image/small.png")}"> <span class="badge badge-danger" id="msgcount">0</span></a>
				<ul class="dropdown-menu dropdown-alerts dropdown-menu-right">
          
                </ul>
				<div class="roll-nav roll-right J_tabSetting right-sidebar-toggle bor-not" style="min-width: 80px;">
                    <span>${(Session.user.real_name)!}</span>
                    <img style="width: 8px;height: 4px" src="/voucher/?ckparam=${encryption("/public/image/arrow-down.png")}">
                </div>

                <div class="btn-group roll-nav roll-right bor-not" style="right: 335px">
                    <div class="dropdown J_tabClose" data-toggle="dropdown">
                        <span style="color: #B8B8B8;font-size: 11px">关闭</span>
                        <img style="width: 8px;height: 4px" src="/voucher/?ckparam=${encryption("/public/image/arrow-down.png")}">
                    </div>
                    <ul role="menu" class="dropdown-menu dropdown-menu-right">
                        <li class="J_tabShowActive"><a>定位当前选项卡</a>
                        </li>
                        <li class="divider"></li>
                        <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                        </li>
                        <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                        </li>
                    </ul>
                </div>
             
            </div>
            <div class="row J_mainContent" id="content-main">
                <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="/voucher/?ckparam=${encryption("Index/main")}" frameborder="0" data-id="/voucher/?ckparam=${encryption("Index/main")}" seamless=""></iframe>
            </div>
            <div class="footer">
                <div class="pull-right">版权所有&nbsp;&copy;2020-2025 <a href="#" target="_blank">上海释名信息科技有限公司</a>
                </div>
            </div>
        </div>
        <!--右侧部分结束-->
    
    </div>
    <script src="/voucher/public/js/jquery.min.js?v=2.1.4"></script>
    <script src="/voucher/public/js/bootstrap.min.js?v=3.3.5"></script>
    <script src="/voucher/public/js/plugins/metisMenu/jquery.metisMenu.js"></script>
    <script src="/voucher/public/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
    <script src="/voucher/public/js/plugins/layer/layer.js"></script>
    <script src="/voucher/public/js/hplus.min.js?v=4.0.0"></script>
    <script src="/voucher/public/js/contabs.min.js"></script>
    <script src="/voucher/public/js/plugins/validate/jquery.validate.min.js"></script>
	<script src="/voucher/public/js/plugins/validate/messages_zh.min.js"></script>
    <script src="/voucher/public/js/main.js"></script>
    <script src="/voucher/public/js/index.js"></script>

    <script src="/voucher/public/js/plugins/pace/pace.min.js"></script>
</body>
<script>


</script>
</html>