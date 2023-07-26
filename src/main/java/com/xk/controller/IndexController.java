package com.xk.controller;


import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import cn.hutool.core.util.ObjectUtil;
import com.xk.entity.*;
import com.xk.service.*;
import com.xk.util.DateUtil;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Scope("prototype")//保持单例模式
@Controller
@RequestMapping("/Index")
@SuppressWarnings("all")
public class IndexController extends BaseController {


  /**
   * ********************************************************
   *
   * @return String
   * @Title: index
   * @Description: TODO(这里用一句话描述这个类的作用)--登录
   * @date 2014-8-12 下午10:53:58
   * *******************************************************
   */
  @RequestMapping("/index")
  public String index() {

    return "Index/index";
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: main
   * @Description: TODO(这里用一句话描述这个类的作用)--登录
   * @date 2014-8-12 下午10:53:58
   * *******************************************************
   */
  @RequestMapping("/main")
  public String main() {

    return "Index/main";
  }

  @RequestMapping("/recharge_method")
  public @ResponseBody
  String recharge_method(@RequestParam("channel_no") String channel_no) {
    Map map = new HashMap();
    map.put("channel_no", channel_no);
    map.put("client_no", this.getUserInfo().getCompany_no());
    String s = null;
    return s;
  }


  @RequestMapping("system_notification")
  public @ResponseBody
  List<SystemNotification> system_notification() {
    List<SystemNotification> systemNotifications = new ArrayList<>();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    String parse = format.format(new Date());
    SystemNotification a = new SystemNotification();

    return systemNotifications;
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: main
   * @Description: TODO(无权限)--登录
   * @date 2014-8-12 下午10:53:58
   * *******************************************************
   */

  @RequestMapping("/noqx")
  public String noqx() {
    return "Index/noqx";
  }

  @RequestMapping("/err")
  public String err() {
    return "Index/err";
  }


  /**
   * ********************************************************.<br>
   * [方法] dateUtil <br>
   * [描述] 折线图统计 <br>
   * [参数] TODO(对参数的描述) <br>
   * [返回] void <br>
   * [时间] 2017-3-16 上午11:03:59 <br>
   * ********************************************************.<br>
   */
  public void hourDateUtil() {
    String dateTime = getParameter("dateTime");
    if (dateTime == null || "".equals(dateTime)) {
      dateTime = DateUtil.get4yMd(new Date());
    }
    String dataTimeArray = "";
    for (int i = 0; i < 24; i++) {
      String hour = (i + "").length() == 2 ? i + "" : "0" + i;
      dataTimeArray += "'" + dateTime + " " + hour + "',";
    }
    this.getMes().put("dateTime", dateTime);
    dataTimeArray = dataTimeArray.substring(0, dataTimeArray.length() - 1);
    this.getMes().put("dataTimeArray", dataTimeArray);
  }

  /**
   * ********************************************************.<br>
   * [方法] dayDateUtil <br>
   * [描述] TODO(这里用一句话描述这个方法的作用) <br>
   * [参数] TODO(对参数的描述) <br>
   * [返回] void <br>
   * [时间] 2017-3-20 上午10:55:57 <br>
   * ********************************************************.<br>
   */
  public void dayDateUtil() {
    String dateTime = getParameter("dateTime");
    if (dateTime == null || "".equals(dateTime)) {
      dateTime = DateUtil.getDayDateStr("yyyy-MM");
    } else {
      dateTime = dateTime.substring(0, 7);
    }
    String dataTimeArray = "";
    for (int i = 1; i <= 31; i++) {
      String hour = (i + "").length() == 2 ? i + "" : "0" + i;
      dataTimeArray += "'" + dateTime + "-" + hour + "',";
    }
    this.getMes().put("dateTime", dateTime);
    dataTimeArray = dataTimeArray.substring(0, dataTimeArray.length() - 1);
    this.getMes().put("dataTimeArray", dataTimeArray);
    setAttribute("dateTimeIndex", dateTime);
  }

  /**
   * ********************************************************.<br>
   * [方法] monthDateUtil <br>
   * [描述] 按年检索 <br>
   * [参数] TODO(对参数的描述) <br>
   * [返回] void <br>
   * [时间] 2017-3-20 上午11:01:17 <br>
   * ********************************************************.<br>
   */
  public void monthDateUtil() {
    String dateTime = getParameter("dateTime");
    if (dateTime == null || "".equals(dateTime)) {
      dateTime = DateUtil.getDayDateStr("yyyy");
    } else {
      dateTime = dateTime.substring(0, 4);
    }
    String dataTimeArray = "";
    for (int i = 1; i <= 12; i++) {
      String hour = (i + "").length() == 2 ? i + "" : "0" + i;
      dataTimeArray += "'" + dateTime + "-" + hour + "',";
    }
    this.getMes().put("dateTime", dateTime);
    dataTimeArray = dataTimeArray.substring(0, dataTimeArray.length() - 1);
    this.getMes().put("dataTimeArray", dataTimeArray);
//		List<Map<String, Object>> moneyList = bankExpenditureService.getList("getMonthMoney",this.getMes());
//		for(Map<String, Object> ml : moneyList){
//			String clearType = ml.get("CLEAR_TYPE")+"";
//			ml.remove("T0");
//			ml.remove("D1");
//			if(clearType.equals("S0")){
//				ml.remove("S0");
//				setAttribute("S0", ml);
//			}
//			if(clearType.equals("T1")){
//				ml.remove("T1");
//				setAttribute("T1", ml);
//			}
//		}
//		List<Map<String, Object>> mechanismList = bankExpenditureService.getList("getMechanismMonthMoney",this.getMes());
//		for(Map<String, Object> ml : mechanismList){
//			String agentNo = ml.get("AGENT_NO")+"";
//			ml.remove("AGENT_NO");
//			setAttribute("mer"+agentNo, ml);
//		}
    setAttribute("dateTimeIndex", dateTime);
  }

  /**
   * ********************************************************
   *
   * @Title: add
   * @Description: TODO(这里用一句话描述这个类的作用) void
   * @date 2014-9-4 下午09:45:56
   * *******************************************************
   */
  @RequestMapping("/add")
  public String add() {
    return this.display();
  }


  /**
   * ********************************************************
   *
   * @Title: clearAllCache
   * @Description: TODO(这里用一句话描述这个类的作用) void
   * @date 2014-9-4 下午09:45:56
   * *******************************************************
   */
  @RequestMapping("/clearAllCache")
  public @ResponseBody
  Map clearAllCache() {
    this.memCached.clearAllCache();
    return this.success("清除成功");
  }


}
