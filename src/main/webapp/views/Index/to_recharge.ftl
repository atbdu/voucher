<#include "/common/include.ftl">
<#include "/common/include_citypicker.ftl">
<link href="/voucher/public/css/upload.css" rel="stylesheet">
<style>
    ::-webkit-scrollbar-thumb:vertical {
        display: none;
    }
</style>
<div class="col-sm-12"  style="overflow: -moz-scrollbars-none !important;">
    <div class="ibox float-e-margins">
        <div class="ibox-content" style="overflow: hidden">
            <div class="row">
                <div class="col-sm-4 b-r">
                    <br>
                    ${(rechargeInfo)!''}
                </div>
                <div class="col-sm-8">
                    <form method="post" id="fm" action="${base}/?ckparam=${encryption("/ClientsRecharge/save/")}&pay_channel=${(channel_no)!''}" class="form-horizontal required-validate"
                          enctype="multipart/form-data">
                        <div class="ibox-content" id="dialog" style="border-style: none">
                            <div class="form-group">
                                <input type="hidden" id="clientsRechargePng" name="clientsRechargePng" value="${clientsRechargePng!}">
                                <input type="hidden" id="img_is" name="img_is" value="false">
                                <label class="col-sm-2 control-label" ptip="请上传图片">电子凭证上传</label>
                                <div class="col-sm-2">
                                    <a href="javascript:;" class="file" id="url_judge" url="/voucher/public/img/upload.png">
                                        <#if clientsRechargePng ??>
                                            <input type="file" accept="image/png,image/jpg,image/jpeg" name="upload_file" id="btn_file"
                                                   src="${(clientsRechargePng.relative_path)!}" onchange="prev(this)"><img
                                                src="${(clientsRechargePng.relative_path)!}" id="prevView" style="width: 120px;height: auto;"
                                                onchange="F_Open_dialog()"/>
                                            <a href="${(clientsRechargePng.relative_path)!}" target="_blank">查看大图</a>
                                        <#else >
                                            <input type="file" accept="image/png,image/jpg,image/jpeg" name="upload_file" id="btn_file"
                                                   onchange="prev(this)"><img src="/voucher/public/img/upload.png" id="prevView"
                                                                              style="width: 120px;height: auto;" onchange="F_Open_dialog()"/>
                                        </#if>
                                    </a>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-xs-2 control-label" style="margin-top:5px">客户编号</label>
                                <div class="col-xs-4">
                                    <#if client_no  ??>
                                        <input class="required form-control" type="text" readonly="readonly" name="client_no"
                                               value="${(client_no)!}"/>
                                    <#else >
                                        <input class="required form-control" type="text" readonly="readonly" name="client_no"
                                               value="${(clientsRecharge.client_no)!}"/>
                                    </#if>
                                </div>
                                <label class="col-xs-2 control-label control-label" style="margin-top:5px">充值流水号</label>
                                <div class="col-xs-4">
                                    <input class="form-control" readonly="readonly" placeholder="自动产生，无需填写" type="text" name="recharge_no"
                                           value="${(clientsRecharge.recharge_no)!}"/>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-xs-2 control-label" style="line-height:48px">金额</label>
                                <div class="col-xs-6">
                                    <div class="input-group">
                                        <input class="required form-control input-lg number" id="amount" style="font-size:20px;font-weight:bold"
                                               type="text" name="amount" value="${(clientsRecharge.amount)!}"/><span
                                                class="input-group-addon">元 </span>
                                    </div>
                                </div>
                            </div>
                            <div class="hr-line-dashed"></div>
                            <div class="form-group">
                                <label class="col-xs-2 control-label" style="margin-top:5px">通道流水号</label>
                                <div class="col-xs-4">
                                    <input class="required form-control" type="text" readonly="readonly" placeholder="自动产生，无需填写"
                                           name="channel_recharge_no" value="${(clientsRecharge.channel_recharge_no)!}"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-xs-2 control-label" style="margin-top:5px">付款方名称</label>
                                <div class="col-xs-4">
                                    <input class="required form-control" type="text" id="remit_name" name="remit_name"
                                           value="${(clientsRecharge.remit_name)!}"/>
                                </div>
                                <label class="col-xs-2 control-label" style="margin-top:5px">付款账号</label>
                                <div class="col-xs-4">
                                    <input class="required form-control" type="text" id="remit_account" name="remit_account"
                                           value="${(clientsRecharge.remit_account)!}"/>
                                </div>

                            </div>
                            <div class="form-group">
                                <label class="col-xs-2 control-label" style="margin-top:5px">备注</label>
                                <div class="col-xs-8">
                                    <input class="form-control" type="text" name="recharge_rim" value="${(clientsRecharge.recharge_rim)!}"/>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="id" value="${(clientsRecharge.id)!}">
                        <#if (!read??)&&(!(clientsRecharge.recharge_status)??||clientsRecharge.recharge_status="0")>
                            <div class="form-group navbar-fixed-bottom dialog_footer">
                                <div class="col-xs-8 col-xs-offset-2">
                                    <button type="button" id="button_submit" onclick="btnfun()" class="btn btn-primary btn-block" id="disabled1">
                                        保存信息
                                    </button>
                                </div>
                            </div>
                        </#if>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="/voucher/public/js/uploader.js"></script>
<style>
    .b-r {
        height: 100%;
    }
</style>

