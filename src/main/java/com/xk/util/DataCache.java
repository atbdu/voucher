package com.xk.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.xk.dao.impl.BaseDaoImpl;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.mysql.jdbc.Util;

import javax.annotation.Resource;

/**
 * ********************************************************
 *
 * @author NoNo
 * @ClassName: DataCache
 * @Description: TODO(缓存操作)
 * @date 2014-11-4 下午03:28:13
 * ******************************************************
 */
@SuppressWarnings("all")
@Component
public class DataCache extends BaseDaoImpl {

    @Resource
    private RedisUtil redisUtil;
    private static MemCached cache = MemCached.getInstance();
    private static final String sqlSpaceName = "Cache";

    /**
     * ********************************************************
     *
     * @return MemCached
     * @Title: getCacheObj
     * @Description: TODO(获取cache整体对像)
     * @date 2014-11-1 下午09:57:37
     * *******************************************************
     */
    public static MemCached getCacheObj() {
        return cache;
    }

    /**
     * ********************************************************
     *
     * @param cacheNames 缓存名
     * @param methodName 方法名
     * @param o
     * @param SearchKey  要搜索的键名
     * @param SearchVl   要搜索的值
     * @return Object
     * @Title: getCache
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @date 2014-11-4 下午05:35:17
     * *******************************************************
     */
    public Object getCache(String cacheNames, String methodName, Object o) {
        //Object cache_result = cache.get(cacheNames);    //cacheName的名称为AA.BB.CC时，cache_result相当于get("AA");
        Object cache_result = redisUtil.get(cacheNames);    //cacheName的名称为AA.BB.CC时，cache_result相当于get("AA");
        if (cache_result == null) {
            try {
                Method[] ms = this.getClass().getMethods();
                for (Method m : ms) {
                    if (m.getName().equals("getCache" + methodName)) {
                        cache_result = m.getParameterAnnotations().length == 0 ? m.invoke(this) : m.invoke(this, o);
                        break;
                    }
                }
                //cache.add(cacheNames, cache_result);
                redisUtil.set("CACHE:REPALCE:" + cacheNames, cache_result, (long) expireTime, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (cacheNames.indexOf(".") != -1) {
            String[] names = cacheNames.split("\\.");
            try {
                Map<String, Object> current = (Map<String, Object>) cache_result;
                for (int i = 1; i < names.length - 1; i++) {   //AA.BB.CC 从BB开始，因为cache_result就是AA的结果。
                    if (current.get(names[i]) != null) {
                        current = (Map<String, Object>) current.get(names[i]);
                    } else {
                        return new HashMap<String, Object>();
                    }
                }
                cache_result = current.get(names[names.length - 1]);
            } catch (Exception e) {
                e.printStackTrace();
                return new HashMap<String, Object>();
            }
        }
        return cache_result != null ? cache_result : null;
    }


    /**
     * ********************************************************
     *
     * @param key 缓存名 getCache+key是方法名
     * @return Object
     * @Title: getCache
     * @Description: TODO(根据缓存名 ， 参数 ， 搜索键名 ， 搜索值获取缓存)
     * @date 2014-11-1 下午09:58:25
     * *******************************************************
     */
    public Object getCache(String key, String args, String searchKey, String searchVl) {
        String methodName = key.split("\\.")[0];
        if (key.startsWith("b_")) {  //如果是b_ 开头的，那么就是读取baseinfo的信息
            args = methodName.replaceFirst("b_", "");
            methodName = "baseinfo";
        }
        methodName = methodName.substring(0, 1).toUpperCase() + methodName.replaceFirst("\\w", "");
        return this.getCache(key, methodName, (Object) args);
    }


    public Object getCache(String key, String searchKey, String searchVl) {
        return this.getCache(key, null, searchKey, searchVl);
    }

    /**
     * ********************************************************
     *
     * @param key
     * @param args
     * @return Object
     * @Title: getCarteButtonCache
     * @Description: TODO(获取当前菜单下的按钮)
     * @date 2014-11-5 上午10:25:40
     * *******************************************************
     */
    public Object getCarteButtonCache(String key, String args) {
        return this.getCache(key, args, null, null);
    }

    /**
     * ********************************************************
     *
     * @param key
     * @return Object
     * @Title: getCache
     * @Description: TODO(这里用一句话描述这个类的作用)
     * @date 2014-11-4 下午06:11:18
     * *******************************************************
     */

    public Object getCache(String key) {
        return this.getCache(key, null, null, null);
    }

    /**
     * 获取基本信息缓存
     *
     * @param key
     * @return
     */
    public Object getDataCache(String key) {
        //Object obj = cache.get(key);
        Object obj = redisUtil.get("CACHE:BASEINFO:" + key);
        //没获取到数据则查询数据库
        try {
            if (obj == null) {
                List<Map<String, Object>> valList = getSqlSession().selectList("getDataByKeyen");
                if (valList != null) {
                    //临时保存基本数据，归类后便于保存进缓存
                    Map<String, Map<String, String>> tmp = new HashMap<String, Map<String, String>>();

                    for (int i = 0; i < valList.size(); i++) {
                        Map<String, Object> tmpMap = valList.get(i);
                        Map vltmp = tmp.get(tmpMap.get("class_en") + "");
                        if (vltmp == null) {
                            vltmp = new HashMap();
                        }
                        vltmp.put(tmpMap.get("info_en") + "", tmpMap.get("info"));
                        tmp.put(tmpMap.get("class_en") + "", vltmp);
                    }

                    //放进缓存
                    for (String keytmp : tmp.keySet()) {
                        //cache.add(keytmp, tmp.get(keytmp));
                        redisUtil.set("CACHE:BASEINFO:" + keytmp, tmp.get(keytmp), (long) expireTime, TimeUnit.MINUTES);

                    }
                    //obj = cache.get(key);
                    obj = redisUtil.get("CACHE:BASEINFO:" + key);

                }
            }
//			obj=sortMapByKey((Map) obj);//按Key进行排序
            obj = (Map) obj;//按Key进行排序
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return obj;
    }

    /**
     * ********************************************************
     *
     * @return Map<String, String>
     * @Title: sortMapByKey
     * @Description: 跟据Key值排序
     * @date 2016-3-22
     * *******************************************************
     */
    public static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }

        Map<String, String> sortMap = new TreeMap<String, String>(
                new MapKeyComparator());

        sortMap.putAll(map);

        return sortMap;
    }

    /**
     * ********************************************************
     *
     * @param class_en
     * @return Map<String, Map < String, Object>>
     * @Title: getCacheBaseinfo
     * @Description: TODO(根据类CLASS_EN获取信息)
     * @date 2014-11-4 下午03:50:05
     * *******************************************************
     */
    public Map<String, Map<String, Object>> getCacheBaseinfo(String class_en) { //获取基础信息
        List<Map<String, Object>> l = getSqlSession().selectList(getSqlName("getDataByKeyen"), class_en);
        return Utils.listToMap(l, "info_en");
    }

    /**
     * ********************************************************
     *
     * @return Map<String, String>
     * @Title: getCacheBaseinfoClass
     * @Description: TODO(获取所有类英文 、 中文信息)
     * @date 2014-11-4 下午03:51:12
     * *******************************************************
     */
    public Map<String, String> getCacheBaseinfoClass() {  //获取基础信息,<类英文，类中文>
        List<Map<String, Object>> l = getSqlSession().selectList(getSqlName("baseinfoClass"));
        return Utils.listToMap(l, "class_cn", "class_cn");
    }

    /**
     * ********************************************************.<br>
     * [方法] getCacheCarte <br>
     * [描述] 获取菜单map <br>
     * [参数] TODO(对参数的描述) <br>
     * [返回] Map<String,Map<String,Object>> <br>
     * [时间] 2015-4-7 下午03:45:53 <br>
     * ********************************************************.<br>
     */
    public List<Map<String, Object>> getCacheCarte() {
        List<Map<String, Object>> carteList = getSqlSession().selectList(getSqlName("getAllList"));
        return carteList;
    }

    public Map<String, Map<String, Object>> getCacheBackCarte() {
        List<Map<String, Object>> carteList = getSqlSession().selectList(getSqlName("getAllList"));
        Map<String, Map<String, Object>> IdListMap = CommonUtil.listToMap(carteList, "ID");
        return IdListMap;
    }

    /**
     * 获取用户缓存
     *
     * @return
     */
    public Map<String, Map<String, Object>> getCacheUserInfo() {
        List<Map<String, Object>> list = getSqlSession().selectList(getSqlName("UserInfo"));
        return Utils.listToMap(list, "user_code");

    }

    /**
     * 获取代理缓存
     */
    public Map<String, Map<String, Object>> getCacheAgentInfo() {
        List<Map<String, Object>> list = getSqlSession().selectList(getSqlName("AgentInfo"));
        return Utils.listToMap(list, "agent_no");
    }

    /**
     * 获取通道缓存
     */
    public Map<String, Map<String, Object>> getCachePay_channel() {
        List<Map<String, Object>> list = getSqlSession().selectList(getSqlName("pay_channel"));
        return Utils.listToMap(list, "channel_no");
    }

    /**
     * ********************************************************
     *
     * @param sqlId
     * @return String
     * @Title: getSqlName
     * @Description: TODO(获取执行SQL所需要的格式 ： 命令空间.SQLID)
     * @date 2014-11-4 下午03:37:14
     * *******************************************************
     */
    public String getSqlName(String sqlId) {
        return sqlSpaceName + "." + sqlId;
    }


}
