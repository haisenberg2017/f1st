package com.haisenberg.f1st;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 extends WebMvcConfigurerAdapter{
	/**
	 * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
	 * <!--swagger资源配置--> <mvc:resources location=
	 * "classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
	 * <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping=
	 * "/webjars/**"/> 不知道为什么，这也是spring boot的一个缺点（菜鸟觉得的）
	 * 
	 * @param registry
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars*").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				//.apis(RequestHandlerSelectors.basePackage("com.haisenberg.f1st.sys.controller"))//这里采用@api包扫描的方式来确定要显示的接口
				.paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Swagger2构建RESTful APIs").description("自给自足，丰衣足食")
				.termsOfServiceUrl("https://github.com/haisenberg2017").version("1.0").build();
	}

}
