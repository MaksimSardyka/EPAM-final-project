package com.epam.auction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class AppConfig {
	/**
	 * Support for HTTP methods send from front, other than GET/POST.
	 */
	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		HiddenHttpMethodFilter hiddenHttpMethodFilter = new HiddenHttpMethodFilter();
		hiddenHttpMethodFilter.setBeanName("HiddenHttpMethodFilter");
		hiddenHttpMethodFilter.setMethodParam("_method");
		return hiddenHttpMethodFilter;
	}
}
