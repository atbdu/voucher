
package com.xk.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xk.Encryption.EncryptionDES;
import com.xk.entity.UserInfo;
import com.xk.dao.CarteDao;
import com.xk.dao.UserInfoDao;
import com.xk.entity.UserLoginAllVo;
import com.xk.service.UserInfoService;

import com.xk.util.DateUtil;

import com.xk.util.CommonUtil;

import com.xk.util.Encryption;
import com.xk.util.Utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ********************************************************
 *
 * @author 自动生成
 * @ClassName: UserInfoServiceImpl
 * @Description: 用户信息
 * @date 2016-01-12 下午 04:34:32
 * ******************************************************
 */
@SuppressWarnings("all")
@Service
@Transactional
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, Integer> implements UserInfoService {

  @Autowired
  private UserInfoDao userInfoDao;
  @Autowired
  private CarteDao carteDao;//菜单

  @Autowired
  public void setBaseDao(UserInfoDao uDao) {
    super.setBaseDao(userInfoDao);
  }

  /**
   * ********************************************************
   *
   * @Title: loginUser
   * @Description: 用户登陆
   * @date 2016-1-13
   * *******************************************************
   */

  @Transactional
  public String loginUser(Map<String, String> map, HttpServletRequest request) throws Exception {
    //获取session验证码
//		String sesssionCode=(String) request.getSession().getAttribute("randomCode");
//		String code=map.get("code");
//		//判断验证码是否相等
//		if(null!=code && null!=sesssionCode && !code.equalsIgnoreCase(sesssionCode)){
//			return "codeError";
//		}
//		//清空验证码
//		request.getSession().getAttribute("");
    //调用登陆方法
    //System.out.println("user_code----------------"+map.get("user_code"));
    UserInfo user = new UserInfo();
    if (map.containsKey("corporate_logo")) {
      user = userInfoDao.getOne("loginUser", map); //根据用户查询用户信息，不传入passs(md5有转义符SQL注入漏洞),防止SQL注入
    } else {
      String user_code = map.get("user_code").toString().trim();// 去前后空格
      map.put("user_code", user_code);//去掉前后空格
      Matcher matcher = Pattern.compile("^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$").matcher(user_code);  //防止SQL注入，用户限制数字、字母、下划线
      if (!matcher.find()) {   //所有密码、用户错误都返回同样错误码，不具体提错误在哪，防止套取用户名
        return "error";
      }
      user = userInfoDao.getOne("loginUser", map); //根据用户查询用户信息，不传入passs(md5有转义符SQL注入漏洞),防止SQL注入
    }
    if (null == user) {  //用户不存在
      return "error";
    }
    if (user.getErr_num() >= 3 || user.getStatus() == 2) { //超登录错误次数限制用户锁定
      return "lock";
    }
    String inpass = map.containsKey("corporate_logo") ? map.get("user_pass") : Encryption.MD5(map.get("user_pass"));  //输入的 pass加密md5
    Integer err_num = 0; //初始化登录错误次数0，最大3次即上锁用户（状态status为2）
    if (user.getUser_pass().equals(inpass)) {   //密码对比成功，才进行用户状态判断，防止陌生套取用户状态
      if (user.getStatus() == 0) {  //停用
        return "stop";
      }
    } else { //密码对比失败，进行登录次数判断，考虑到昨天有登录错误次数，今天登录次数从0开始
      err_num = user.getErr_num();//登录错误次数，有可能昨天非0
      String last_login = user.getLast_login_date();  //最后登录日期，用于判断限制当天登录3次
      if (last_login != null && last_login != "" && last_login.substring(0, 10).equals(DateUtil.getDayDateStr("yyyy-MM-dd"))) {
        err_num = err_num + 1;
      } else {
        err_num = 1;
      }
    }
    //不管成功还是失败，记录登录数据IP、时间、错误次数（如果有错误）
    Map<String, Object> updateData = new HashMap<String, Object>();
    updateData.put("user_code", user.getUser_code());
    updateData.put("last_login_ip", Utils.getIp(request));
    updateData.put("last_login_date", DateUtil.getDayDateStr("yyyy-MM-dd HH:mm:ss"));
    updateData.put("err_num", err_num);  //登录成功err_num自动重置为0
    //修改登陆的信息

    if (err_num > 0) {  //如果密码错误
      if (err_num >= 3) {
        updateData.put("status", "2");
      }
      userInfoDao.update("updateInfo", updateData);
      return "error";
    }
    userInfoDao.update("updateInfo", updateData);
    user.setLast_login_ip(Utils.getIp(request));
    user.setLogin_num(user.getLogin_num() + 1);
    request.getSession().setAttribute("user", user);

    String client_no = user.getCompany_no();

    String key1 = CommonUtil.createRandomString(32);

    //获取该用户的菜单
    Map<String, Object> userMap = new HashMap<String, Object>();
    userMap.put("user_code", user.getUser_code());
    userMap.put("system_tag", user.getSystem_tag());
    userMap.put("role_id", user.getRole_id());

    List<Map<String, Object>> userCarteList = null;
    List<Map<String, Object>> userBtnList = null;

    //用户登录成功缓存所有按钮
    Map<String, Object> btnAll = new HashMap<String, Object>();
    Map<String, Object> userBtnMap = new HashMap<String, Object>();

    //通过部门查询所有 数据  可能为空
    Map mapbtn = new HashMap();
    if (user.getRole_id() == 0) {//管理员
      userCarteList = carteDao.getList("getAllCarteByTag", user.getSystem_tag());
      for (Map btn : userCarteList) {
        btnAll.put("/" + String.valueOf(btn.get("href")), 1);
      }
    } else {
      userCarteList = carteDao.getList("getAllCarteByTag", user.getSystem_tag());
      for (Map btn : userCarteList) {
        btnAll.put("/" + String.valueOf(btn.get("href")), 1);
      }
      userCarteList = carteDao.getList("getCartebyUserCode", userMap);
      userBtnList = carteDao.getList("getBtnbyUserCode", userMap);
      if (userBtnList != null) {
        for (Map lmap : userBtnList) {
          String burl = lmap.get("url") == null ? "" : lmap.get("url").toString();
          String[] arr = burl.split(",");
          StringBuilder sbu = new StringBuilder();
          try {
            //加密key
            //String key = (String) MemCached.getInstance().get("encryKey_"+user.getUser_code());
            String key = key1;
            if (key != null) {
              //加密url
              for (String u : arr) {
                sbu.append(",").append(new EncryptionDES(key).encrypt(u));
                userBtnMap.put(u, 1);
              }
            }
          } catch (Exception e) {
            e.printStackTrace();
          }
          mapbtn.put(lmap.get("carte_id"), sbu.length() > 0 ? sbu.substring(1) : 0);
        }
      }
      for (Map btn : userCarteList) {
        userBtnMap.put("/" + btn.get("href"), 1);
      }
    }

    List<Map<String, Object>> btnList = carteDao.getList("getBtnbyUserCodeAll", userMap);
    for (Map btn : btnList) {
      btnAll.put(String.valueOf(btn.get("href")), 1);
    }
    //memCache.add("userBtnAll", btnAll);
    tokenComponent.set("userBtnAll", btnAll);

    //把该用户的菜单放进缓存中
    //memCache.delete("userCarte"+user.getUser_code());
    //memCache.delete("userBtn"+user.getUser_code());
    //memCache.delete("userBtnMap"+user.getUser_code());
    //memCache.add("userCarte"+user.getUser_code(), userCarteList);
    //memCache.add("userBtn"+user.getUser_code(), mapbtn);
    //memCache.add("userBtnMap"+user.getUser_code(), userBtnMap);
    user.setUser_pass(null);
    UserLoginAllVo userLoginAllVo = new UserLoginAllVo(key1, user, userCarteList, mapbtn, userBtnMap);
    tokenComponent.delLoginUser(user.getUser_code());
    tokenComponent.createToken(userLoginAllVo);
    return "sucess";
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
  public boolean saveUserinfo(String loginUser_code, UserInfo userInfo) throws Exception {
    userInfo.setUser_pass(Encryption.MD5(userInfo.getUser_pass()));//密码加密
    userInfo.setSystem_tag(0);          //所属平台
    userInfo.setAdd_date(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));      //添加日期
    userInfo.setAdd_user(loginUser_code);    //添加人
    userInfo.setStatus(1);            //启用状态
    if (null == userInfo.getQq()) {
      userInfo.setQq("");            //QQ
    }
    if (null == userInfo.getEmail()) {
      userInfo.setEmail("");          //Email
    }
    if (null == userInfo.getCookie()) {
      userInfo.setCookie("");          //Cookie
    }
    if (null == userInfo.getLogin_num()) {
      userInfo.setLogin_num(0);        //登录次数
    }
    if (null == userInfo.getLast_login_ip()) {
      userInfo.setLast_login_ip("");      //登录ip
    }
    if (null == userInfo.getLast_login_date()) {
      userInfo.setLast_login_date(DateUtil.getDateFormate(new Date(), "yyyy-MM-dd HH:mm:ss"));//最后登录时间
    }
    int result = userInfoDao.insert(userInfo);
    if (result > 0) {
      //判断  是否是业务员  添加开放业务员
      if (userInfo.getRole_no() == 2) {
//				OutsideUserInfo outuser=new OutsideUserInfo();
//				outuser.setAdd_date(userInfo.getAdd_date());
//				outuser.setAdd_user(userInfo.getAdd_user());
//				outuser.setLogin_num(userInfo.getLogin_num());
//				outuser.setLast_login_date(userInfo.getLast_login_date());
//				outuser.setLast_login_ip(userInfo.getLast_login_ip());
//				outuser.setLink_phone(userInfo.getLink_phone());
//				outuser.setUser_code(userInfo.getUser_code());
//				outuser.setUser_pass(userInfo.getUser_pass());
//				outuser.setReal_name(userInfo.getReal_name());
//				outuser.setRole_no(2);//业务员
//				outuser.setIs_admin(0);//0不是管理员
//				outuser.setStatus(1);//启用
//				outuser.setSystem_tag(userInfo.getSystem_tag());
//		        ResourceBundle rb=ResourceBundle.getBundle("commondata");
//				outuser.setBelong_no(rb.getString("belong_no"));
//				outuser.setSales_no(Integer.valueOf(rb.getString("sales_no")));
        int res = 1;
//					outsideUserInfoDao.insert("insertbyrole",outuser);
        if (res == 1) {
          return true;
        } else {
          userInfoDao.delete("deleteUser", userInfo.getUser_code());
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * ********************************************************
   *
   * @return Integer
   * @Title: deleteUser_info
   * @Description: 删除
   * @date 2016-01-12 下午 04:34:32
   * *******************************************************
   */
  public Integer deleteUser_info(String loginUser_code, String user_code) throws Exception {
    if (loginUser_code.equals(user_code)) {
      return 0;
    }
    int res = delete("deleteUser", user_code);
    return res;
  }

  /**
   * ********************************************************
   *
   * @return String
   * @Title: updateUserinfo
   * @Description: 更新用户
   * @date 2016-1-14
   * *******************************************************
   */
  public boolean updateUserinfo(UserInfo userInfo) throws Exception {
    UserInfo user = this.getOne(userInfo.getUser_code());
    user.setReal_name(userInfo.getReal_name());    //真实姓名
    user.setQq(userInfo.getQq());          //QQ
    user.setEmail(userInfo.getEmail());        //邮箱
    user.setLink_phone(userInfo.getLink_phone());  //电话
    user.setCompany_no(userInfo.getCompany_no());  //公司编号
    if (null == user.getLast_login_ip()) {
      user.setLast_login_ip("");  //最后登录ip
    }
    if (null == user.getCookie()) {
      user.setCookie("");      //cookie
    }
    int result = userInfoDao.update(user);
    if (result > 0) {
//			OutsideUserInfo outuser=outsideUserInfoDao.getOne("getOne",user.getUser_code());
//			if(null!=outuser){
//				outuser.setReal_name(user.getReal_name());
//				outuser.setLink_phone(user.getLink_phone());
//				int res=outsideUserInfoDao.update("updatebyrole", outuser);
//				if(res==1){
//					return true;
//				}else{
//					return false;
//				}
//			}
      return true;
    } else {
      return false;
    }
  }

  /**
   * ********************************************************
   *
   * @return Map
   * @Title: updateStatus
   * @Description: 修改状态 0禁用  1启用
   * @date 2016-1-14
   * *******************************************************
   */
  public boolean updateStatus(String loginUser_code, String user_code) throws Exception {
    UserInfo user = getOne("selectcode", user_code);
    if (user.getUser_code().equals(loginUser_code)) {
      return false;
    }
    if (user.getStatus() == 2) {
      user.setStatus(1);//启用
    } else {
      user.setStatus(2);//注销
    }
    int res = this.update("updateStatus", user);
    if (res > 0) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * ********************************************************
   *
   * @return Map
   * @Title: updatepass
   * @Description: 重置密码
   * @date 2016-1-26
   * *******************************************************
   */
  public int updatepass(String user_code, String pwd) throws Exception {
    int result = 0;
    Map<String, String> userMap = new HashMap<String, String>();
    userMap.put("user_code", user_code);
    userMap.put("user_pass", Encryption.MD5(pwd));
    result = userInfoDao.update("updatepass", userMap);
    return result;
  }


}

