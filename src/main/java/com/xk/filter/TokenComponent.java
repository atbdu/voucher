package com.xk.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.xk.entity.UserLoginAllVo;
import com.xk.util.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 陈驷口
 * @version 1.0
 * @date 2022/3/18 11:05
 */
@Component
public class TokenComponent {
    @Resource
    private RedisUtil redisUtil;

    private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

    private static final Long MILLIS_MINUTE = 60 * 1000L;

    // 令牌有效期（默认30分钟） todo 调试期改为5小时
    private static final int expireTime = 60;
//    private static final int expireTime = 5 * 60;

    // Redis 存储的key
    private static final String TOKEN_REDIS = "TOKEN:CLIENT:";


    /**
     * 保存企业端菜单
     *
     * @return 用户信息
     */
    public boolean set(String key,Map value) {
        return redisUtil.set(key,value);
    }

    /**
     * 获取企业端菜单
     *
     * @return 用户信息
     */
    public Map get(String key) {
        Object o = redisUtil.get(key);
        return redisUtil.get(key);
    }


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public UserLoginAllVo getLoginUser(String token) {
        // 获取请求携带的令牌
        if (StrUtil.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            return redisUtil.get(userKey);
        }
        return null;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(UserLoginAllVo loginUser) {
        if (ObjectUtil.isNotNull(loginUser) && StrUtil.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户身份信息
     */
    public void delLoginUser(String token) {
        if (StrUtil.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisUtil.delete(userKey);
        }
    }

    /**
     * 创建令牌
     *
     * @param loginUser 用户信息
     * @return 令牌
     */
    public String createToken(UserLoginAllVo loginUser) {
//        String token = UUID.randomUUID().toString().replace("-", "");
//        loginUser.setToken(token);
        refreshToken(loginUser);
        return "ok";
    }

    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     *
     * @param loginUser LoginUserVo
     */
    public void verifyToken(UserLoginAllVo loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(UserLoginAllVo loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getUser().getUser_code());
        redisUtil.set(userKey, loginUser, (long) expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String uuid) {
        return TOKEN_REDIS + uuid;
    }
}
