package shenpi.shenpi.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import shenpi.shenpi.interceptor.shiro.ShiroRealm;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;

@Configuration
public class ShiroConfig {
	
	@Bean(name = "shiroFilter")
    public FilterRegistrationBean shiroFilter() throws Exception {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter((Filter) getShiroFilterFactoryBean().getObject());
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return registration;
	}
	
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/");
        shiroFilterFactoryBean.setSuccessUrl("/main/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login_toLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/login_toLogin");


        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
		filterChainDefinitionMap.put("/static/login/**", "anon");
		filterChainDefinitionMap.put("/plugins/keypad/**", "anon");
		filterChainDefinitionMap.put("/static/js/myjs/**", "authc");
		filterChainDefinitionMap.put("/static/js/**", "anon");
		filterChainDefinitionMap.put("/uploadFiles/uploadImgs/**", "anon");
		filterChainDefinitionMap.put("/code.do", "anon");
		filterChainDefinitionMap.put("/login_login", "anon");
		filterChainDefinitionMap.put("/app**/**", "anon");
		filterChainDefinitionMap.put("/weixin/**", "anon");
		filterChainDefinitionMap.put("/**", "authc");

        return shiroFilterFactoryBean;
    }
	
//	@Bean
//	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//		System.out.println("ShiroConfiguration.shirFilter()");
//		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//		shiroFilterFactoryBean.setSecurityManager(securityManager);
//		//拦截器.
//		Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();
//		// 配置不会被拦截的链接 顺序判断
//		filterChainDefinitionMap.put("/static/login/**", "anon");
//		filterChainDefinitionMap.put("/plugins/keypad/**", "anon");
//		filterChainDefinitionMap.put("/static/js/myjs/**", "authc");
//		filterChainDefinitionMap.put("/static/js/**", "anon");
//		filterChainDefinitionMap.put("/uploadFiles/uploadImgs/**", "anon");
//		filterChainDefinitionMap.put("/code.do", "anon");
//		filterChainDefinitionMap.put("/login_login", "anon");
//		filterChainDefinitionMap.put("/app**/**", "anon");
//		filterChainDefinitionMap.put("/weixin/**", "anon");
//		filterChainDefinitionMap.put("/**", "authc");
//
//		
//		//配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
////		filterChainDefinitionMap.put("/plugins/keypad/**", "logout");
//		//<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//		//<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
////		filterChainDefinitionMap.put("/**", "authc");
//		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
////		shiroFilterFactoryBean.setLoginUrl("/login");
//		// 登录成功后要跳转的链接
////		shiroFilterFactoryBean.setSuccessUrl("/index");
//
//		//未授权界面;
//		shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//		return shiroFilterFactoryBean;
//	}

	/**
	 * 凭证匹配器
	 * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
	 * ）
	 * @return
	 */
	@Bean
	public HashedCredentialsMatcher hashedCredentialsMatcher(){
		HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
		hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
		hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
		return hashedCredentialsMatcher;
	}

	@Bean
	public ShiroRealm ShiroRealm(){
		ShiroRealm myShiroRealm = new ShiroRealm();
		myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myShiroRealm;
	}


	@Bean(name = "securityManager")
	public SecurityManager securityManager(){
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(ShiroRealm());
//		ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
//		authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
//		securityManager.setAuthenticator(authenticator);
		return securityManager;
	}

	/**
	 *  开启shiro aop注解支持.
	 *  使用代理方式;所以需要开启代码支持;
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

}
