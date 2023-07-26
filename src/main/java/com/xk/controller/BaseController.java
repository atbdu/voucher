package com.xk.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.hutool.core.util.ObjectUtil;
import com.xk.entity.UserLoginAllVo;
import com.xk.filter.TokenComponent;
import com.xk.util.RedisUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.xk.Encryption.EncryptionDES;
import com.xk.entity.Page;
import com.xk.entity.UserInfo;

import com.xk.service.BaseInfoService;
import com.xk.service.UserInfoService;
import com.xk.util.DataCache;
import com.xk.util.GetCacheMethod;
import com.xk.util.MemCached;

import static com.xk.dao.impl.BaseDaoImpl.expireTime;

@Scope("prototype")
@SuppressWarnings("all")
public class BaseController {
    private Page page;
    protected Map mes = new HashMap<String, String>();//消息
    protected String message = "";//记录操作结果信息
    protected int resultFlag = 0;//默认成功,前台返回成功或者失败
    protected String statusCode = "";//成功(200)或是失败(300)
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    //protected DataCache cache = new DataCache();
    @Autowired
    protected DataCache dc;
    protected MemCached memCached = MemCached.getInstance();

    Logger logger = LoggerFactory.getLogger(BaseController.class);

    private BaseInfoService baseInfoService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    protected TokenComponent tokenComponent;
    @Resource
    protected RedisUtil redisUtil;

    @ModelAttribute
    public void setReqAndResp(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    //图片保存地址
//	  protected String loadpath = "//app//upjas-2.0.4//welcome-content//backyy//UploadImg//";
//	  protected String fullPath = "/backyy/UploadImg";
//	  
//	  protected String del_loadpath = "//app//upjas-2.0.4//welcome-content//";

    protected String loadpath = "//192.168.1.52/images";
    protected String fullPath = "/backsunsmile/UploadImg";

    protected String del_loadpath = "http://192.168.1.52:8080/images/";

    /********************************************************
     * @Title: getRequest
     * @Description:返回当前调用方法的类名与方法名，做为VIEW的地址
     * @return current class/method
     * @throws Exception
     * @date 2014-8-12 上午12:33:26
     *********************************************************/
    public String display() {
        //String action = new Exception().getStackTrace()[1].getFileName().toString().replace("Controller.java", "");	//action名称
        String[] class_str = (this.getClass().getName()).split("\\.");

        String action = class_str[class_str.length - 1].replace("Controller", "");

        String method = new Exception().getStackTrace()[1].getMethodName().toString();    //方法名称

        String base = getRequest().getContextPath();

        String fullPath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + base;
//	    String fullPath = getRequest().getScheme()+"://"+getRequest().getServerName()+":8188"+base;

        //zb
        setAttribute("action", "/" + action);   // /action
        setAttribute("method", method);

        String urlEncrypt = "";
        try {
            //加密key
            //String key = (String) MemCached.getInstance().get("encryKey_" + getUserInfo().getUser_code());
            String key = null;
            if (ObjectUtil.isNotNull(tokenComponent.getLoginUser(getUserInfo().getUser_code()))) {
                key = tokenComponent.getLoginUser(getUserInfo().getUser_code()).getToken();
            }
            if (key != null) {
                //加密url
                urlEncrypt = new EncryptionDES(key).encrypt("/" + action + "/" + method + "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAttribute("carte_id", getParameter("carte_id"));
        setAttribute("base", fullPath);   //http://localhost/medicine
        setAttribute("baseClass", fullPath + "/" + action);   //http://localhost/finance/action
        setAttribute("url", "/voucher/?ckparam=" + urlEncrypt);     // http://localhost:8088/finance/Index/countryPage
        this.setAttribute("getCache", new GetCacheMethod(response)); //注册freemaker 自定义函数
        if (this.page != null) {
            this.setAttribute("p", page);
        }
        setAttribute("role_no", getUserInfo().getRole_no());
        setAttribute("carte_no", getParameter("carte_no"));
        if (null != getUserInfo().getRole_no()) {
            //Map btnmap = (Map) memCached.get("userBtn" + getUserCode());
            Map btnmap = getLoginUser().getUserBtn();
            if (ObjectUtil.isNotNull(btnmap)) {
                setAttribute("bids", btnmap.get(getParameter("carte_no")));
            }
        }
        return action + "/" + method;
    }


    public JSONObject getCache(String cache_name) {
        //DataCache dc = new DataCache();
        Object cache = null;
        if (cache_name.startsWith("b_")) {   //当baseInfo时，缓存KEY名为   b_ 开头 ，得去掉
            cache_name = cache_name.replace("b_", "");
            cache = dc.getDataCache(cache_name);
        } else {
            cache = dc.getCache(cache_name);
        }
        JSONObject json = (JSONObject) JSONObject.fromObject(cache);
        return json;
    }

    public void repalceCache(Page p, String cache_name, String key) {
        //cache_name 存在两种格式 如：UserInfo.real_name 或者b_status
        String repalceKey = "";
        if (cache_name.indexOf(".") != -1) {
            repalceKey = cache_name.split("\\.")[1];  //UserInfo.real_name 找到缓存中的real_name,替换数据中的key
            cache_name = cache_name.split("\\.")[0];
        }
        JSONObject json = this.getCache(cache_name);
        List<Map<String, Object>> rs = p.getResults();
        for (int i = 0; i < rs.size(); i++) {
            Map<String, Object> mapList = rs.get(i);
            if (mapList.containsKey(key)) {
                if (cache_name.startsWith("b_")) {
                    mapList.put(key, json.get(mapList.get(key).toString()));
                } else {
                    JSONObject cache_info = (JSONObject) json.get(mapList.get(key).toString());
                    if (cache_info != null) {
                        mapList.put(key, cache_info.get(repalceKey) == null ? "无" : cache_info.get(repalceKey));
                    }
                }
                rs.set(i, mapList);
            }
        }
        this.getPage().setResults(rs);
    }

    /**
     * ********************************************************
     *
     * @return void
     * @Title: setAttribute
     * @Description: 获取setAttribute
     * @date 2014-8-12 上午12:39:55
     ********************************************************/
    public void setAttribute(String name, Object value) {
        this.getRequest().setAttribute(name, value);
    }

    /**
     * ********************************************************
     *
     * @param argName
     * @return String
     * @Title: getParameter
     * @Description: TODO(根据参数名获取值)
     * @date 2014-9-2 上午11:19:15
     * *******************************************************
     */
    public String getParameter(String argName) {
        return request.getParameter(argName);
    }

    /**
     * ********************************************************
     *
     * @return HttpServletRequest
     * @Title: getRequest
     * @Description: 获取request
     * @date 2014-8-12 上午12:59:09
     * *******************************************************
     */
    public HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    /**
     * ********************************************************
     *
     * @return Session
     * @Title: getSession
     * @Description: 获取Session
     * @date 2014-8-12 上午12:59:09
     * *******************************************************
     */

    public HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * ********************************************************
     *
     * @return HttpServletResponse
     * @Title: getResponse
     * @Description: TODO(获取response)
     * @date 2014-11-1 下午10:43:12
     * *******************************************************
     */
    public HttpServletResponse getResponse() {
        ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
        return servletContainer.getResponse();
    }

    /**
     * ********************************************************
     *
     * @param message
     * @return Map<String, String>
     * @Title: Sucess
     * @Description: TODO(显示成功信息)
     * @date 2014-9-2 上午10:38:17
     * *******************************************************
     */
    public Map<String, String> success(String message, String navTabId) {
        if (message.equals("")) {
            message = "操作成功";
        }
        mes.put("message", message);
        mes.put("statusCode", "200");
        mes.put("navTabId", navTabId);
        return mes;
    }

    public Map<String, String> success(String message) {
        if (message.equals("")) {
            message = "操作成功";
        }
        mes.put("message", message);
        mes.put("statusCode", "200");
        return mes;
    }

    public Map<String, String> error(String message) {
        if (message.equals("")) {
            message = "操作失败";
        }
        mes.put("message", message);
        mes.put("statusCode", "300");
        return mes;
    }

    /**
     * ********************************************************
     *
     * @param message
     * @return Map<String, String>
     * @Title: alertInfo
     * @Description: TODO(显示信息)
     * @date 2014-9-2 上午10:38:17
     * *******************************************************
     */
    public Map<String, String> alertInfo(String message, String navTabId, String statusCode) {
        if (message.equals("")) {
            message = "操作成功";
        }
        mes.put("message", message);
        mes.put("statusCode", statusCode);
        mes.put("navTabId", navTabId);
        return mes;
    }

    /**
     * ********************************************************
     *
     * @param message
     * @param result
     * @return Map<String, String>
     * @Title: message
     * @Description: TODO(返回消息到前端)
     * @date 2014-11-1 下午06:49:18
     * *******************************************************
     */
    public Map<String, String> message(String message, boolean result) {
        mes.put("statusCode", result ? "200" : "300");
        mes.put("message", message);
        return mes;
    }


    /**
     * ********************************************************
     *
     * @param message
     * @param result
     * @return Map<String, String>
     * @Title: message
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @date 2014-11-1 下午06:53:02
     * *******************************************************
     */
    public Map<String, String> message(String message, int result) {
        mes.put("statusCode", result == 0 ? "200" : "300");
        mes.put("message", message);
        return mes;
    }

    /**
     * ********************************************************.<br>
     * [方法] getCurrentTime <br>
     * [描述] TODO(这里用一句话描述这个方法的作用) <br>
     * [参数] type 0 yyyy-MM-dd HH:mm:ss 1 yyyy-MM-dd hh:mm:ss 2 yyyy-MM-dd <br>
     * [返回] String <br>
     * [时间] 2015-4-16 上午09:57:38 <br>
     * ********************************************************.<br>
     */
    public String getCurrentTime(int type) {
        String dateType = "";
        switch (type) {
            case 0:
                dateType = "yyyy-MM-dd HH:mm:ss";
                break;
            case 1:
                dateType = "yyyy-MM-dd hh:mm:ss";
                break;
            case 2:
                dateType = "yyyy-MM-dd";
                break;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateType);
        String dateTime = sdf.format(new Date());
        return dateTime;
    }

    //删除文件
    public static void delFile(String filePath) {
        try {
            File myDelFile = new File(filePath.toString());
            boolean delete = myDelFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置默认搜索条件
     *
     * @param key
     * @param value
     */
    public void setDefaltSearch(String key, String value) {
        if (this.getPage().getParams().get("url") != null) {  //直接菜单打开的，未点查询有URL名参数
            page.getParams().put(key, value);
        }
    }

    //---------------------------------------
    public Page getPage() {
        if (page == null) {
            page = new Page();
        }
        return page;
    }

    public Page getTruePage() {
        return this.page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Map getMes() {
        return mes;
    }

    public void setMes(Map mes) {
        this.mes = mes;
    }

    public UserInfo getUserInfo() {
        UserInfo user = (UserInfo) getSession().getAttribute("user");
        if (user == null) {
            user = new UserInfo();
        }
        return user;
    }

    /**
     * ********************************************************.<br>
     * [方法] getUserCode <br>
     * [描述] 获取当前用户登录名 <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] String <br>
     * [时间] 2015-4-16 上午09:45:52 <br>
     * ********************************************************.<br>
     */
    public static String getUserCode() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        UserInfo user = (UserInfo) request.getSession().getAttribute("user");
        String userCode = "";
        if (user != null) {
            userCode = user.getUser_code();
        }
        return userCode;
    }
    /**
     *
     * ********************************************************
     *
     * @Title: getAllClientName
     * @Description: 查询全部商户名称
     * @return List<String>
     * @date 2014-3-6
     * @author zb
     ********************************************************
     */
//	public List<String> getAllClientName(){
//		List<String> listClientName =(List<String>) memCached.get("listClientName");
//		if(listClientName==null){
//			listClientName = (List<String>) baseService.getObjectList("getClientName");
//			memCached.add("listClientName", listClientName);
//		}
//		JSONArray json = listByJSon(listClientName);
//		setAttribute("jsonObjct", json.toString());
//		return listClientName;
//	}

    /**
     * ********************************************************
     *
     * @return List<String>
     * @Title: getAllSaleName
     * @Description: 查询全部销售人员名称
     * @date 2014-3-6
     * @author zb
     * *******************************************************
     */
    public List<String> getAllSaleName() {
        //List<String> listsalename = (List<String>) memCached.get("listsalename");
        List<String> listsalename=redisUtil.get("listsalename");
        if (listsalename == null) {
            listsalename = (List<String>) userInfoService.getObjectList("getSaleName");
            //memCached.add("listsalename", listsalename);
            redisUtil.set("listsalename", listsalename,(long) expireTime, TimeUnit.MINUTES);
        }
        JSONArray json = listByJSon(listsalename);
        setAttribute("saleJson", json.toString());
        return listsalename;
    }

    /**
     * ********************************************************
     *
     * @return List<String>
     * @Title: getAllMcc
     * @Description: 查询全部MCC
     * @date 2014-3-6
     * @author zb
     * *******************************************************
     */
    public List<Map<String, Object>> getAllMcc() {
        //List<Map<String, Object>> listMcc = (List<Map<String, Object>>) memCached.get("listMcc");
        List<Map<String, Object>> listMcc =redisUtil.get("listMcc");

//		if(listMcc==null){
//			listMcc =  hfMccService.getObjectList("getAllMcc");
//			memCached.add("listMcc", listMcc);
//		}
        List<String> list = new ArrayList<String>();
        for (Map<String, Object> map : listMcc) {
            String mcc_id = map.get("MCC_ID").toString();
            String bussiness = map.get("BUSSINESS").toString();
            list.add(mcc_id + "-" + bussiness);
        }
        JSONArray json = listByJSon(list);
        setAttribute("mccJson", json.toString());
        return listMcc;
    }

    //将数组转化成json对象
    public static JSONArray listByJSon(List<String> list) {
        String[] valueArr = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            valueArr[i] = "" + list.get(i) + "";
        }
        JSONArray json = JSONArray.fromObject(valueArr);
        return json;
    }


    /********************************************************
     * @Title: getRequest
     * @Description:自定义返回当前调用方法的类名与方法名，做为VIEW的地址
     * @return current class/method
     * @throws Exception
     * @date 2014-8-12 上午12:33:26
     *********************************************************/
    public String display(String action, String method) {
        //String action = new Exception().getStackTrace()[1].getFileName().toString().replace("Controller.java", "");	//action名称
        String[] class_str = (this.getClass().getName()).split("\\.");

        String base = getRequest().getContextPath();

        String fullPath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + base;

        //zb
        setAttribute("action", "/" + action);   // /action
        setAttribute("method", method);

        String urlEncrypt = "";
        try {
            //加密key
            String key = (String) MemCached.getInstance().get("encryKey_" + getUserInfo().getUser_code());
            if (key != null) {
                //加密url
                urlEncrypt = new EncryptionDES(key).encrypt("/" + action + "/" + method + "/");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setAttribute("carte_id", getParameter("carte_id"));
        setAttribute("base", fullPath);   //http://localhost/medicine
        setAttribute("baseClass", fullPath + "/" + action);   //http://localhost/finance/action
        setAttribute("url", "/backsunsmile/?ckparam=" + urlEncrypt);     // http://localhost:8088/finance/Index/countryPage
        this.setAttribute("getCache", new GetCacheMethod(response)); //注册freemaker 自定义函数
        if (this.page != null) {
            this.setAttribute("p", page);
        }
        return action + "/" + method;
    }

    //设置当前的url路径
    public String setBaseUrl() {
        return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + getRequest().getContextPath();
    }

    /**
     * ********************************************************
     *
     * @param html
     * @return String
     * @Title: ajaxHtml
     * @Description: AJAX输出HTML，返回null
     * @date 2013-1-12 上午12:44:19
     * *******************************************************
     */
    public String ajaxHtml(String html) {
        return showMes(html, "text/html");
    }

    public String showMes(String content, String type) {
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserLoginAllVo getLoginUser() {
        UserLoginAllVo loginUser = tokenComponent.getLoginUser(getUserCode());
        if (ObjectUtil.isNull(loginUser)) {
            loginUser = new UserLoginAllVo();
        }
        return loginUser;
    }
}
