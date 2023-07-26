<!DOCTYPE html>
<html>

<head>
    <#include "/common/include.ftl">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>主页</title>
    <meta name="keywords" content="灵工云服">
    <meta name="description" content="灵工云服">
    <link href="/voucher/public/css/alter.css" rel="stylesheet">
    <link href="/voucher/public/css/main.css" rel="stylesheet">
    <script type="text/javascript">
        window.onload = function () {
            $.ajax({
                type: 'get',
                url: $('#system').attr('href'),
                async: true,
                success: function (data) {
                    console.log(data)
                    $.tr = $("<tr></tr>");
                    for (var i = 0; i < data.length; i++) {
                        $.td = $("<td>" + data[i].body + "</td>");
                        $.tr.append($.td);
                    }
                    $('#tab').append($.tr);

                    //横向滚动 需要设置div的宽度
                    var speed = 30
                    marquePic2.innerHTML = marquePic1.innerHTML

                    function Marquee() {
                        if (demo.scrollLeft >= marquePic1.scrollWidth) {
                            demo.scrollLeft = 0
                        } else {
                            demo.scrollLeft++
                        }
                    }

                    var MyMar = setInterval(Marquee, speed)
                    demo.onmouseover = function () {
                        clearInterval(MyMar)
                    }
                    demo.onmouseout = function () {
                        MyMar = setInterval(Marquee, speed)
                    }
                }
            })
        }
    </script>
</head>
<body class="gray-bg">
<div class="" style="background-color: #F9F9F9">
    <div class="row">
        <div class="col-md-12">
            <br>
            <div class="col-md-12" style="padding: 0 10px;">
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
                    <div class="panel panel-default box-op">
                        <div class="box-min">
                            <img class="img-pre" src="public/image/a1.png" alt="">
                            <div><span class="text-li1">任务数</span><span class="text-li2" style="margin-left: 10px;">(月)</span></div>
                            <div style="font-weight: 600;font-size: 22px;margin-bottom: 8px;">0<span class="text-li2"
                                                                                                                     style="margin-left: 8px;">个</span>
                            </div>
                            <div><span class="text-li2">所有任务：<span style="font-weight: 600;font-size: 16px">0</span>个</span></div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="panel panel-default box-op">
                        <div class="box-min">
                            <img class="img-pre" src="public/image/a2.png" alt="">
                            <div><span class="text-li1">发佣数</span></div>
                            <div style="font-weight: 600;font-size: 22px;margin-bottom: 8px;">9<span class="text-li2"
                                                                                                                  style="margin-left: 8px;">元</span>
                            </div>
                            <div><span class="text-li2">已完成：<span style="font-weight: 600;font-size: 16px">0</span>元</span></div>
                        </div>
                    </div>
                </div>

                <div class="col-md-4">
                    <div class="panel panel-default box-op">
                        <div class="box-min">
                            <img class="img-pre" src="public/image/a3.png" alt="">
                            <div><span class="text-li1">发佣客户数</span></div>
                            <div style="font-weight: 600;font-size: 22px;margin-bottom: 8px;">0<span class="text-li2"
                                                                                                                       style="margin-left: 8px;">笔</span>
                            </div>
                            <div><span class="text-li2">完成数：<span style="font-weight: 600;font-size: 16px">0</span>笔</span></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="ht-1">我的税源地</div>
    <div class="row" style="margin: 0;">
        </div>
    </div>

</div>
<a id="system" href="/voucher/?ckparam=${encryption("/Index/system_notification/")}" hidden></a>
</body>
<script>
    $(function () {
        $(".recharge_source").unbind().click(function () {
            console.log($(this).attr('ajax_url'));
            $.ajax({
                type: 'get',
                url: $(this).attr('ajax_url'),
                async: true,
                success: function (data) {
                    layer.alert(data, {icon: 1, area: ['900px', '500px']});
                }
            })
        });
        const color_old = ['green', 'orange', 'violet', 'violet', 'green', 'orange', 'orange', 'violet', 'green', 'green', 'orange', 'violet', 'violet', 'green', 'orange', 'orange', 'violet', 'green']
        let num = 0
        let color = []
        const box_li = document.querySelectorAll('.box-li')
        const box_li_name = document.querySelectorAll('.box-li-name')
        const box_li_money = document.querySelectorAll('.box-li-money')
        const box_bot = document.querySelectorAll('.box-bot')
        const box_pet = document.querySelectorAll('.box-pet')
        const bot_bg = document.querySelectorAll('.bot-bg')
        box_li.length % 9 === 0 ? num = box_li.length / 9 : num = Math.floor(box_li.length / 9) + 1
        console.log(num)
        for (let j = 1; j <= num; j++) {
            color = color.concat(color_old)
        }
        console.log(color)
        for (let i = 0; i < box_li.length; i++) {
            box_li[i].classList.add("box-li-" + color[i])
            box_li_name[i].classList.add("li-text-" + color[i])
            box_li_money[i].classList.add("li-text-" + color[i])
            box_bot[i].classList.add("box-bot-" + color[i])
            box_pet[i].classList.add("box-bot","box-bot-" + color[i])
            bot_bg[i].classList.add("bot-bg-" + color[i])
        }
    });
</script>
<script src="/voucher/public/js/plugins/layer/layer.js"></script>
</html>