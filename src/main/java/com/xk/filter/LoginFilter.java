
package com.xk.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xk.Encryption.EncryptionDES;
import com.xk.entity.UserInfo;
import com.xk.util.MemCached;
import com.xk.util.Utils;
import com.xk.util.XssRequestWrapper;
@SuppressWarnings("all")
@Component
public class LoginFilter extends OncePerRequestFilter{
	MemCached memCache=MemCached.getInstance();

	private static TokenComponent tokenComponent;
	@Autowired
	private TokenComponent tokenComponent2;
	@PostConstruct
	public void init() {
		tokenComponent=tokenComponent2;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterchain)throws ServletException,IOException {
		//获取请求路径
		String uri = request.getRequestURI();
		try {
			//获取当前登录用户
			UserInfo user=(UserInfo) request.getSession().getAttribute("user");
			//获取ckparam参数
			String ckparam=request.getParameter("ckparam");
			if(null==user && !uri.endsWith("dwz.frag.xml") && !uri.endsWith(".jsp") && !uri.endsWith(".css") && !uri.endsWith("/UserInfo/link_login")
					&& !uri.endsWith(".js") && !uri.endsWith(".png") && !uri.endsWith("/UserInfo/loginUser")
					&& !uri.endsWith(".jpeg") && !uri.endsWith(".jpg")&&!uri.endsWith("/UserInfo/getRandomImg")
					&&!uri.endsWith("/publicGetRealData") && !uri.endsWith(".bmp") && !uri.endsWith(".gif")){
				if (uri.endsWith("/backsunsmile/") && ckparam!=null && !"".equals(ckparam)) {
					request.getRequestDispatcher("/UserInfo/publicGoLogin").forward(request, response);
				}else if(uri.endsWith("/UserInfo/publicLoginTime/")){
					filterchain.doFilter(request, response);
				}else{
					request.getRequestDispatcher("/UserInfo/gotoLogin").forward(request, response);
				}
			}else{//用户已登录
				//获取项目名
				String base = request.getContextPath();
				if (uri.endsWith(base) || uri.endsWith(base+"/")) {
					if (ckparam==null || "".equals(ckparam)) {
						request.getRequestDispatcher("/UserInfo/gotoLogin").forward(request, response);
					}else{
						//获取加密KEY
						//String key=(String) MemCached.getInstance().get("encryKey_"+user.getUser_code());
						String key = null;
						if (ObjectUtil.isNotNull(tokenComponent.getLoginUser(user.getUser_code()))){
							key=tokenComponent.getLoginUser(user.getUser_code()).getToken();
						}
						//设置请求编码格式
						request.setCharacterEncoding("utf-8");
						if (key==null || "".equals(key)) {
							request.getRequestDispatcher("/UserInfo/jump").forward(request, response);
						}else{
							//解密URL地址
							String url=new EncryptionDES(key).decrypt(ckparam);
//							if(user.getRole_no() != 0){
//								Map btnAll = (Map) memCache.get("userBtnAll");
								Map btnAll = (Map) tokenComponent.get("userBtnAll");
								if(btnAll.containsKey(url)){
									//Map userBtnMap = (Map) memCache.get("userBtnMap"+user.getUser_code());
									Map<String, Object> userBtnMap = tokenComponent.getLoginUser(user.getUser_code()).getUserBtnMap();
									if(userBtnMap.containsKey(url)){
									}else{
										//权限拦截
										url = "/Index/noqx";
									}
								}
//							}
							//转发
							request.getRequestDispatcher(url).forward(request, response);
						}
					}
				}else{
					filterchain.doFilter(request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			//异常，跳转到错误界面
			request.getRequestDispatcher("/Index/err").forward(request, response);

		}
	}
}

	
