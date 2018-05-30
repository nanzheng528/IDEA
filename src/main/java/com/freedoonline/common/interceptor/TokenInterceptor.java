package com.freedoonline.common.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.freedoonline.domain.entity.User;

import cn.cloudlink.core.common.cache.RedisCacheService;
import cn.cloudlink.core.common.dataaccess.data.BusinessResult;
import cn.cloudlink.core.common.utils.StringUtil;
import net.sf.json.JSONObject;

/**
  * 
  *<p>类描述：token拦截器。</p>
  * @author 刘建雨。
  * @version v1.0。
  * @since JDK1.8。
  *<p>创建日期：2018年4月27日 下午8:57:49。</p>
  */
public class TokenInterceptor extends HandlerInterceptorAdapter{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource(name="redisCacheService")
	private RedisCacheService localRedisCacheService;
	
	@Value("${user.token.security.ignored}")
	private String ignodedUrl;  //拦截器要忽略的路径地址，多个用逗号隔开
	private  String[] ignodedUrls; //拦截器要忽略的路径地址数组

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hander) throws Exception{
		//遍历忽略token的uri，如果存在，则直接返回
		String[] ignoredUrlArray = ignoredUrls();
		String requestURI =  request.getRequestURI();
		for (String ignoredUrl : ignoredUrlArray) {
			PathMatcher matcher = new AntPathMatcher();
			boolean matchResult = matcher.match(ignoredUrl, requestURI);
			if (matchResult) {
				return true;
			}
		}
		BusinessResult businessResult = new BusinessResult();
		//检查token值是否为空，如果为空，则返回，Code值为401
		String token = request.getParameter("token");
		if(!StringUtil.hasText(token)){
			businessResult.setSuccess(-1);
			businessResult.setCode("401");
			businessResult.setMsg("token为空！");
			logger.error("====token is empty===="+request.getRequestURI()+"====");
			setResponseResultStr(response, businessResult);
			return false;
		}
		//检查token是否有效：从redis中获取用户对象，如果结果为空，则token无效，Code值为402
		User User = (User) localRedisCacheService.getValue(token);
		if(User == null){
			businessResult.setSuccess(-1);
			businessResult.setCode("402");
			businessResult.setMsg("token无效！");
			logger.error("====User is null===="+request.getRequestURI()+"====");
			setResponseResultStr(response, businessResult);
			return false;

		}
		//将用户及token值设置到当前进程中
		ThreadLocalHolder.setUser(User);
		ThreadLocalHolder.setToken(token);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, 
			Object hander, ModelAndView modelAndView) throws Exception{
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object hander, Exception ex) throws Exception{
		
	}
	
	private void setResponseResultStr(HttpServletResponse response,
			BusinessResult businessResult) throws Exception{
		
		response.setContentType("application/x-json;charset=UTF-8");
		JSONObject json = JSONObject.fromObject(businessResult);//将java对象转换为json对象
		String str = json.toString();//将json对象转换为字符串
		response.getWriter().print(str);
	}
	 /**
	   * 
	   * <p>功能描述：忽略的URL。</p>
	   * <p> 刘建雨。</p>	
	   * @return
	   * @since JDK1.8。
	   * <p>创建日期:2018年4月27日 下午8:58:19。</p>
	   * <p>更新日期:[日期YYYY-MM-DD][更改人姓名][变更描述]。</p>
	   */
	private String[] ignoredUrls(){
		if(ignodedUrls==null){
			if(StringUtil.hasText(ignodedUrl)){
				ignodedUrls = ignodedUrl.split(",");
			}else{
				ignodedUrls = new String[]{};
			}
		}
		return ignodedUrls;
	}
}
