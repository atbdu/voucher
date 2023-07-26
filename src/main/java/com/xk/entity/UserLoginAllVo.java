package com.xk.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 陈驷口
 * @version 1.0
 * @date 2022/3/18 10:20
 */
@SuppressWarnings("all")
public class UserLoginAllVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;
    /**
     * 登陆时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;
    /**
     * 登录用户对象
     */
    private UserInfo user;
    /**
     *用户菜单
     */
    private List<Map<String, Object>> userCarteList;
    /**
     * 通过部门查询所有数据（可能为空）
     */
    private Map userBtn;

    /**
     * 所有按钮
     */
    private Map<String,Object> userBtnMap;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }

    public List<Map<String, Object>> getUserCarteList() {
        return userCarteList;
    }

    public void setUserCarteList(List<Map<String, Object>> userCarteList) {
        this.userCarteList = userCarteList;
    }

    public Map getUserBtn() {
        return userBtn;
    }

    public void setUserBtn(Map userBtn) {
        this.userBtn = userBtn;
    }

    public Map<String, Object> getUserBtnMap() {
        return userBtnMap;
    }

    public void setUserBtnMap(Map<String, Object> userBtnMap) {
        this.userBtnMap = userBtnMap;
    }

    public UserLoginAllVo() {
    }

    public UserLoginAllVo(String token, UserInfo user, List<Map<String, Object>> userCarteList, Map userBtn, Map<String, Object> userBtnMap) {
        this.token = token;
        this.user = user;
        this.userCarteList = userCarteList;
        this.userBtn = userBtn;
        this.userBtnMap = userBtnMap;
    }
}
