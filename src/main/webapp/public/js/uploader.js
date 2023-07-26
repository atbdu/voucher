function F_Open_dialog() {
    $("#btn_file").click();
}

function prev(event) {
    //获取展示图片的区域
    var img = document.getElementById("prevView");
    if (typeof (event.files[0]) == "undefined") {
        window.parent.layer.msg('未选择文件【鼠标悬浮查看是否选择了文件】', {icon: 7});
        img.setAttribute("src", $("#url_judge").attr("url"));
        $("#img_is").val("false");
        return false;
    } else if (!/\.(jpg|jpeg|png|JPG|PNG)$/.test(event.value)) {
        window.parent.layer.msg('文件类型不支持,请上传[jpg,png,jpeg]格式的文件!', {icon: 7});
        $("#btn_file").val("");
        $("#img_is").val("false");
        return false;
    }
    var fileSize = event.files[0].size / (1024 * 1024);
    if (fileSize > 2) {
        window.parent.layer.msg("上传单个文件大小不能超过2MB!", {icon: 7});
        $("#btn_file").val("");
        $("#img_is").val("false");
        return false;
    }
    //获取文件阅读器
    let reader = new FileReader();
    reader.readAsDataURL(event.files[0]);
    reader.onload = function () {
        $("#img_is").val("true");
        //给img的src设置图片url
        img.setAttribute("src", this.result);
    }
}

//图片上传
$("#button_submit").click(function () {
    var clientsRechargePng = $("#clientsRechargePng").val();
    if ($("#btn_file").val() == "" && clientsRechargePng == "") {
        window.parent.layer.msg('未选择文件【鼠标悬浮查看是否选择了文件】', {icon: 7});
        return false;
    }
    var amount=$("#amount").val();

    var pay_channel=$("#pay_channel").val();

    var remit_name=$("#remit_name").val();

    var remit_account=$("#remit_account").val();
    if(amount==''){
        window.parent.layer.msg("请填写金额", {icon: 3});
        return false;
    }
    if(pay_channel==''){
        window.parent.layer.msg("请选择通道", {icon: 3});
        return false;
    }
    if(remit_name==''){
        window.parent.layer.msg("请填写付款方名", {icon: 3});
        return false;
    }
    if(remit_account==''){
        window.parent.layer.msg("请填写付款账号", {icon: 3});
        return false;
    }
    var href = $("#fm").attr('action');
    var form = new FormData(document.getElementById("fm"));
    $.ajax({
        url: href,
        timeout: 300000,
        dataType: "json",
        type: "post",
        data: form,
        processData: false,
        contentType: false,
        success: function (json) {
            var message = '';
            var icon = '1';
            if (json.statusCode == 200) {
                message = json.message || '操作成功';
                window.parent.layer.close(window.parent.layer.index);
            } else {
                message = json.message || '操作失败';
                icon = '2';
            }
            window.parent.layer.msg(message, {icon: icon});
            window.parent.ref();//刷新tab
        },
        error: function () {
            var message = '欧吼，上传失败';
            window.parent.layer.msg(message, {icon: 2});
        }
    });
});