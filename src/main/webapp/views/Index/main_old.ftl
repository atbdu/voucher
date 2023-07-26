<!DOCTYPE html>
<html>

<head>
<#include "/common/include.ftl">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    

    <title>主页</title>
    <meta name="keywords" content="灵工云服">
    <meta name="description" content="灵工云服">
<script type="text/javascript">
    window.onload=function (){
        $.ajax({
            type: 'get',
            url:$('#system').attr('href'),
            async:true,
            success:function (data){
                console.log(data)
                $.tr=$("<tr></tr>");
                for(var i=0;i<data.length;i++){
                    $.td=$("<td>"+data[i].body+"</td>");
                    $.tr.append($.td);
                }
                $('#tab').append($.tr);

                //横向滚动 需要设置div的宽度
                var speed=30
                marquePic2.innerHTML=marquePic1.innerHTML
                function Marquee(){
                    if(demo.scrollLeft>=marquePic1.scrollWidth){
                        demo.scrollLeft=0
                    }else{
                        demo.scrollLeft++
                    }
                }
                var MyMar=setInterval(Marquee,speed)
                demo.onmouseover=function() {clearInterval(MyMar)}
                demo.onmouseout=function() {MyMar=setInterval(Marquee,speed)}
            }
        })
    }
</script>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-md-12" style="background-color: white">
            <br>
            <div class="col-md-12">
                <div class="progress" style="max-width: 100%;min-width: 1764px">
                    <div class="progress-bar progress-bar-danger progress-bar-striped active" style="width:5%">系统公告:</div>
                    <div id="demo" style="overflow: hidden; width: 1400px; align: center;margin-left: 100px;">
                        <table cellspacing="0" cellpadding="0" align="center" border="0" style="font-weight: 800;color: red">
                            <tbody>
                            <tr>
                                <td id="marquePic1" valign="top">
                                    <table id="tab" width="1900px">

                                    </table>
                                </td>
                                <td id="marquePic2" valign="top">
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="col-md-12 panel panel-default" style="background-color: #b3d4fd;background-image: linear-gradient(to left top,rgba(110,100,150,0.5), #87CEEB)">
                        <div class="panel-body row">
                            <div class="col-md-4 pull-right" style="padding: 15px">
                                <img src="public/image/1.png" alt="" style="height: 60px;width: 60px">
                            </div>
                            <div class="panel-title" style="color: white"><h4>任务数<small style="color: white">(月)</small></h4></div>
                            <div class="panel-title" style="color: white">
                                <h1 class="no-margins">${(taskNumMon)!0}</h1>
                                <small>所有任务</small>
                                <span class="label label-light">${(taskNum)!0}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="col-md-12 panel panel-default" style="background-color: #48D1CC;background-image: linear-gradient(to left top,rgba(32,178,170,0.5),#98FB98)">
                        <div class="panel-body row">
                            <div class="col-md-4 pull-right" style="padding: 10px">
                                <img src="public/image/2.png" alt="" style="height: 70px;width: 70px">
                            </div>
                            <div class=" panel-title" style="color: white"><h4>发佣数</h4></div>
                            <div class="panel-title" style="color: white">
                                <h1 class="no-margins">${(oderAmt)!0}</h1>
                                <#--<small>已完成</small>-->
                                <#--<span class="label label-light">${(oderAmt)!0}</span>-->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="col-md-12 panel panel-default" style="background-color:#F08080;background-image: linear-gradient(to left top,rgba(205,175,175,0.5),#F08080)">
                        <div class="panel-body row">
                            <div class="col-md-4 pull-right" style="padding: 15px">
                                <img src="public/image/3.png" alt="" style="height: 60px;width: 60px">
                            </div>
                            <div class="panel-title" style="color: white"><h4>发佣客户数</h4></div>
                            <div class="panel-title" style="color: white">
                                <h1 class="no-margins">${allWorkerNum}</h1>
                                <small>完成数</small>
                                <span class="label label-light">${(workerNum)!0}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-md-12" style="background-color: white">
            <#list usabelMoney as item>
                <div class="col-sm-4" style="margin-bottom: 15px;margin-top: 15px;">
                    <div class="media" style="background-color: #eacd76;background-image: linear-gradient(to left top,rgba(250,185,16,0.5), #EEDD82)">
                        <div class="media-left pull-right">
                            <img src="public/image/4.png" alt="" style="height: 60px;width: 60px;margin-top: 25px;margin-bottom: 25px;">

                        </div>
                        <div class="media-body pull-left" style="margin-top: 20px;margin-right: 20px;">
                            <div class="col-md-12">
                                <h4  class="media-heading" style="color: white">${item.channel_short_name}账户余额</h4>
                                <h1 class="no-margins" style="color: white">${item.usable_money}</h1>
                                <small style="color: white">可用余额</small>
                                <#if item.recharge_logo == 1 || item.channel_status == 0>
                                    <span class="label label-light" style="font-size: 20px"><a ajax_url="/voucher/?ckparam=${encryption("/Index/recharge_method/")}&channel_no=${item.channel_no}" class="recharge_source">充值方式</a></span>
                                    <#else>
                                        <span class="label label-light" style="font-size: 20px"><a whref="/voucher/?ckparam=${encryption("/Index/to_recharge/")}&channel_no=${item.channel_no}" wwidth="1350px" wheight="80%" class="openWin">充值方式</a></span>
                                </#if>
                                <span class="label label-light" style="font-size: 20px"><a whref="/voucher/?ckparam=${encryption("/TaskOrderBatch/add/")}" wwidth="600px" wheight="500px" class="openWin">委托结算</a></span>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        </div>
    </div>
</div>
<a id="system" href="/voucher/?ckparam=${encryption("/Index/system_notification/")}" hidden></a>
</body>
<script>
    $(function (){
        $(".recharge_source").click(function(){
            console.log($(this).attr('ajax_url'));
            $.ajax({
                type:'get',
                url:$(this).attr('ajax_url'),
                async:true,
                success:function (data){
                    layer.alert(data,{icon:1,area: ['900px', '500px']});
                }
            })
        });
    });

</script>
<script src="/voucher/public/js/plugins/layer/layer.js"></script>
</html>