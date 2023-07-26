package com.xk.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import javax.imageio.ImageIO;

import javax.servlet.ServletOutputStream;

import cn.hutool.core.util.ObjectUtil;
import com.xk.entity.UserLoginAllVo;
import com.xk.util.*;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xk.entity.Page;
import com.xk.entity.UserInfo;
import com.xk.service.LoginLogService;
import com.xk.service.UserInfoService;

/**
 * ********************************************************
 *
 * @author 自动生成
 * @ClassName: UserInfoController
 * @Description: 用户信息
 * @date 2016-01-12 下午 04:14:24
 * ******************************************************
 */
@SuppressWarnings("all")
@Scope("prototype")
@Controller
@RequestMapping("/UserInfo")
public class UserInfoController extends BaseController {

  @Resource
  private UserInfoService userInfoService;

  @Resource
  private LoginLogService loginLogService;



  public static final String SM2PubHardKeyHead = "3059301306072A8648CE3D020106082A811CCF5501822D034000000";
  public static String getPostResponse(String url, Map<String, String> parmMap) throws IOException {

    String result = "";
    PostMethod post = new PostMethod(url);
    HttpClient client = new HttpClient();
    Iterator it = parmMap.entrySet().iterator();
    NameValuePair[] param = new NameValuePair[parmMap.size()];
    int i = 0;
    while (it.hasNext()) {
      Map.Entry parmEntry = (Map.Entry) it.next();
      param[i++] = new NameValuePair((String) parmEntry.getKey(), (String) parmEntry.getValue());
    }

    post.setRequestBody(param);
    try {
      int statusCode = client.executeMethod(post);

      if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
        Header locationHeader = post.getResponseHeader("location");
        String location = "";
        if (locationHeader != null) {
          location = locationHeader.getValue();
          result = getPostResponse(location, parmMap);//用跳转后的页面重新请求
        }
      } else if (statusCode == HttpStatus.SC_OK) {
        result = post.getResponseBodyAsString();
      }
    } catch (
        IOException ex) {
    } finally {
      post.releaseConnection();
    }
    return result;
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: login
   * @Description:用户登录
   * @date 2016-01-12 下午 04:14:24
   * *******************************************************
   */
  @RequestMapping(value = "loginUser")
  public @ResponseBody
  Map login(@RequestParam(required = false) Map<String, String> map) throws IOException {
    //返回的数据
    Map<String, String> dataMap = new HashMap<String, String>();
    if (map.containsKey("token")) {
      System.out.println(map.get("token"));
      ResourceBundle resour = ResourceBundle.getBundle("commondata");//读取配置文件
      String sm4Key = resour.getString("sm4Key");//获取默认上传地址
      try {
        String s2 = TtSmUtil.SM4DecForCBC(sm4Key, String.valueOf(map.get("token")));
        System.out.println(s2);
        Map map1 = JsonMap.getValue(s2);
        try {
          dataMap.put("code", userInfoService.loginUser(map1, this.getRequest()));
        } catch (Exception e) {
          e.printStackTrace();
          dataMap.put("code", "error");
        } finally {
          //添加登陆记录
          saveLog(map1, dataMap.get("code") == null ? "" : dataMap.get("code"));
          //添加行为记录
          Map<String, Object> useraction = new HashMap<String, Object>();
          useraction.put("user_code", map1.get("user_code") == null ? "" : map1.get("user_code"));
          useraction.put("remark", dataMap.get("code").equals("sucess") ? "登陆成功，登陆状态为：" + dataMap.get("code") : "登陆失败，登陆状态为：" + dataMap.get("code"));
          List<Map<String, Object>> userCarteList = tokenComponent.getLoginUser(map1.get("user_code").toString()).getUserCarteList();
          //System.out.println(userCarteList);
          getSession().setAttribute("userCartes", userCarteList);
          //传入行为记录参数
          this.setAttribute("useraction", useraction);
          String b = getRequest().getScheme() + "://" + getRequest().getServerName() + getRequest().getContextPath();
          response.sendRedirect(b + "/Index/index");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return dataMap;
    }
    try {
      dataMap.put("code", userInfoService.loginUser(map, this.getRequest()));
    } catch (Exception e) {
      e.printStackTrace();
      dataMap.put("code", "error");
    } finally {
      //添加登陆记录
      saveLog(map, dataMap.get("code") == null ? "" : dataMap.get("code"));
      //添加行为记录
      Map<String, Object> useraction = new HashMap<String, Object>();
      useraction.put("user_code", map.get("user_code") == null ? "" : map.get("user_code"));
      useraction.put("remark", dataMap.get("code").equals("sucess") ? "登陆成功，登陆状态为：" + dataMap.get("code") : "登陆失败，登陆状态为：" + dataMap.get("code"));

      UserLoginAllVo loginUser = tokenComponent.getLoginUser(map.get("user_code"));
      if (ObjectUtil.isNotEmpty(loginUser)) {
        List<Map<String, Object>> userCarteList = loginUser.getUserCarteList();
        //System.out.println(userCarteList);
        getSession().setAttribute("userCartes", userCarteList);
      }
      //传入行为记录参数
      this.setAttribute("useraction", useraction);
    }
    return dataMap;
  }

  /**
   * ********************************************************
   *
   * @return WeixinWeixinuserController
   * @throws IOException
   * @Title: getRandomImg
   * @Description: 获取验证码
   * @date 2015-6-30
   * *******************************************************
   */
  @RequestMapping(value = "/getRandomImg")
  public String getRandomImg() throws IOException {
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setContentType("image/jpeg");
    // 获取验证码的缓存图片
    BufferedImage buffImg = RandomCodeUtil.instance().initImag(request);
    // 输出servlet流
    ServletOutputStream servletStream = response.getOutputStream();
    ImageIO.write(buffImg, "jpeg", servletStream);
    servletStream.close();
    return null;
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: gotoLogin
   * @Description: 跳转到登陆页面
   * @date 2016-1-13
   * *******************************************************
   */
  @RequestMapping("gotoLogin")
  public String gotoLogin() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: pass
   * @Description: 修改密码
   * @date 2020-1-13
   * *******************************************************
   */
  @RequestMapping("pass")
  public String pass() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: list
   * @Description:
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/list")
  public String list() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: list_json
   * @Description:
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/list_json")
  public @ResponseBody
  String list_json() {
    userInfoService.getPageList(this.getPage());
    return this.getPage().getJsonPage();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: liststa
   * @Description: 注销业务员
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/liststa")
  public String liststa() {
    Page page = this.getPage();
    page.getParams().put("role_no", "2");//业务员
    page.getParams().put("status", "2");//注销的
    userInfoService.getPageList(page);
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title:senior
   * @Description: 高级检索
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/seniorlist")
  public String seniorlist(@RequestParam("carte_id") String carte_id) {
    setAttribute("carte_id", carte_id);
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title:senior
   * @Description: 业务员 高级检索
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/roleseniorlist")
  public String roleseniorlist() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title:senior
   * @Description: 注销业务员 高级检索
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/seniorliststa")
  public String seniorliststa() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: add
   * @Description: 跳转添加页面
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/add")
  public String add() {
    setAttribute("type", this.getPage().getParams().get("type"));
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: update
   * @Description: 跳转修改页面
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/update")
  public String update(@RequestParam(value = "user_code") String user_code) {
    if (user_code != null && !user_code.equals("")) {
      UserInfo userInfo = userInfoService.getOne(user_code);
      this.setAttribute("userInfo", userInfo);
    }
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return boolean
   * @Title: check
   * @Description: 检查用户名
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping(value = "/check")
  public @ResponseBody
  String check(String user_code) {
    String msg = "true";
    try {
      Integer res = userInfoService.getNumber("checkcode", user_code);
      if (null != res && res > 0) {
        msg = "false";
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return msg;
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: saveUserinfo
   * @Description: 添加
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/saveUserinfo")
  public @ResponseBody
  Map saveUserinfo(UserInfo userInfo) {
    Map<String, String> useraction = new HashMap<String, String>();
    boolean res = false;
    try {
      ResourceBundle resour = ResourceBundle.getBundle("commondata");//读取配置文件
      String pwd = resour.getString("defaultpass");//获取默认密码
      userInfo.setUser_pass(pwd);//将密码传入实体类
      res = userInfoService.saveUserinfo(this.getUserCode(), userInfo);
      if (res) {
        useraction.put("remark", "[" + this.getUserCode() + "]添加[" + userInfo.getUser_code() + "]，操作成功");
        return success("保存[ " + userInfo.getUser_code() + " ]成功", "");
      } else {
        useraction.put("remark", "[" + this.getUserCode() + "]添加[" + userInfo.getUser_code() + "]，操作失败");
        return message("保存[ " + userInfo.getUser_code() + " ]失败", false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      useraction.put("remark", "[" + this.getUserCode() + "]添加[" + userInfo.getUser_code() + "]，出现错误");
      return message("出现错误！", false);
    } finally {
      this.setAttribute("useraction", useraction);
    }
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: updateUserinfo
   * @Description: 更新 修改
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/updateUserinfo")
  public @ResponseBody
  Map updateUserinfo(UserInfo userInfo) {
    Map<String, String> useraction = new HashMap<String, String>();
    boolean res = false;
    try {
      res = userInfoService.updateUserinfo(userInfo);
      if (res) {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + userInfo.getUser_code() + "]进行了修改操作，操作成功");
        return success("保存[ " + userInfo.getUser_code() + " ]成功", "");
      } else {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + userInfo.getUser_code() + "]进行了修改操作，操作失败");
        return message("保存[ " + userInfo.getUser_code() + " ]失败", false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      useraction.put("remark", "[" + this.getUserCode() + "]对[" + userInfo.getUser_code() + "]进行了修改操作，出现错误");
      return message("出现错误！", false);
    } finally {
      this.setAttribute("useraction", useraction);
    }
  }

  /**
   * ********************************************************
   *
   * @return Map
   * @Title: updateStatus
   * @Description: 修改状态 0禁用  1启用  2注销
   * @date 2016-1-14
   * *******************************************************
   */
  @RequestMapping("/updateStatus")
  public @ResponseBody
  Map updateStatus(String user_code) {
    Map<String, String> useraction = new HashMap<String, String>();
    boolean res = false;
    try {
      res = userInfoService.updateStatus(this.getUserCode(), user_code);
      if (res) {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了修改状态操作，操作成功");
        return success("修改成功", "");
      } else {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了修改状态操作，操作失败");
        return message("修改失败", false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了修改状态操作，出现错误");
      return message("出现错误", false);
    } finally {
      this.setAttribute("useraction", useraction);
    }
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: deleteUserinfo
   * @Description: 删除
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/deleteUserinfo")
  public @ResponseBody
  Map deleteUserinfo(String user_code) {
    Map<String, String> useraction = new HashMap<String, String>();
    int result = 0;
    try {
      result = userInfoService.deleteUser_info(this.getUserCode(), user_code);
      if (result > 0) {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了删除操作，操作成功");
        return success("删除成功", "");
      } else {
        useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了删除操作，操作失败");
        return message("删除失败", false);
      }
    } catch (Exception e) {
      e.printStackTrace();
      useraction.put("remark", "[" + this.getUserCode() + "]对[" + user_code + "]进行了删除操作，出现错误");
      return message("出现错误", false);
    } finally {
      this.setAttribute("userAction", useraction);
    }

  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: exitLogin
   * @Description: 退出登陆
   * @date 2016-1-14
   * *******************************************************
   */
  @RequestMapping("exitLogin")
  public String exitLogin() {
    tokenComponent.delLoginUser(this.getUserCode());

    //清空session
//		request.getSession().removeAttribute("user");
//		request.getSession().removeAttribute("clients");
    Enumeration em = request.getSession().getAttributeNames();
    while (em.hasMoreElements()) {
      request.getSession().removeAttribute(em.nextElement().toString());
    }
    return "redirect:/UserInfo/gotoLogin";
  }

  /**
   * ********************************************************
   *
   * @return void
   * @Title: saveLog
   * @Description: 保存日志信息
   * @date 2016-1-13
   * *******************************************************
   */
  public void saveLog(Map<String, String> data, String code) {
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("user_code", data.get("user_code"));
    //表示登陆成功
    if ("sucess".equals(code)) {
      //状态为1  表示登陆成功
      map.put("status", 1);
      map.put("remark", data.get("user_code") + "登陆成功");
    } else {
      //状态为0   表示登陆失败
      map.put("status", 0);
      map.put("remark", data.get("user_code") + "登陆失败");
    }
    map.put("add_date", DateUtil.getDateFormate(new Date(), "yyyy-MM-dd"));
    map.put("add_time", DateUtil.getDateFormate(new Date(), "HH:mm:ss"));
    map.put("login_ip", Utils.getIp(request));
    map.put("system_tag", "1");

    loginLogService.insert("inserloginlog", map);
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: list
   * @Description: 业务员管理
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/rolelist")
  public String rolelist() {
    Page page = this.getPage();
    page.getParams().put("role_no", "2");
    userInfoService.getPageList(page);
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return Map
   * @Title: updatepwd
   * @Description:
   * @date 2016-1-14
   * *******************************************************
   */
  @RequestMapping("/updatepwd")
  public String updatepwd(@RequestParam(value = "user_code") String user_code) {
    UserInfo userInfo = userInfoService.getOne(user_code);
    setAttribute("userInfo", userInfo);
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return Map
   * @Title: updatepass
   * @Description: 重置密码
   * @date 2016-1-14
   * *******************************************************
   */
  @RequestMapping("/updatepass")
  public String updatepass(@RequestParam(value = "password") String password) throws Exception {
    String user_code = this.getUserCode();
    int result = userInfoService.updatepass(user_code, password);
    if (result > 0) {
      tokenComponent.delLoginUser(user_code);
      request.getSession().removeAttribute("user");
      request.getSession().removeAttribute("clients");
      return "redirect:/UserInfo/gotoLogin";
    } else {
      return null;
    }
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: publicGoLogin
   * @Description:
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/publicGoLogin")
  public String publicGoLogin() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: publicLoginTime
   * @Description:
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/publicLoginTime")
  public String publicLoginTime() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: jump
   * @Description:
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  @RequestMapping("/jump")
  public String jump() {
    return this.display();
  }

  /**
   * ********************************************************
   *
   * @param user_pass
   * @return String
   * @Title: ajaxUser_pass
   * @Description: 验证密码
   * @date 2014-11-1 下午10:43:47
   * *******************************************************
   */
  @RequestMapping("/ajaxUser_pass")
  public @ResponseBody
  String ajaxUser_pass(@RequestParam String user_pass) {
    String user_code = this.getUserCode();
    UserInfo userInfo = userInfoService.getOne(user_code);
    String result = "false";
    System.out.println(Encryption.MD5(user_pass));
    if (Encryption.MD5(user_pass).equals(userInfo.getUser_pass())) {
      result = "true";
    }
    return result;
  }
}
