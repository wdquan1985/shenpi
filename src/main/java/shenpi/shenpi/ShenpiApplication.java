package shenpi.shenpi;

import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.MemoryConstrainedCacheManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication(exclude=org.activiti.spring.boot.SecurityAutoConfiguration.class)
@EnableAspectJAutoProxy //Enable Aop
public class ShenpiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShenpiApplication.class, args);
	}

}
