package shenpi.shenpi.config;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import shenpi.shenpi.interceptor.LoginHandlerInterceptor;

/**
 * Created by bruce on 2017/10/30.
 * 
 * Import info: when angular Server Deployment, should delete this configuration file, 
 * use the default Web MVC configuration.
 * 
 * @author bruce
 * @date 2017/10/30
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.springboot.mybatis.controller" })
@EnableConfigurationProperties({ ResourceProperties.class })
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor());
    }
//	@Inject
//	private ResourceProperties resourceProperties = new ResourceProperties();
    
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {	
//    	registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
//    }
// 
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
////        configurer.enable();
//        configurer.enable();
//    }
    
    //允许所有请求跨域访问(CORS)
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("*")
//                .allowedMethods("*")
//                .allowedOrigins("*");
//    }
	@Bean
	CharacterEncodingFilter characterEncodingFilter() {
	    CharacterEncodingFilter filter = new CharacterEncodingFilter();
	    filter.setEncoding("UTF-8");
	    filter.setForceEncoding(true);
	    return filter;
	}
	
}