package no.nav.oebs.digihot.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

 import no.nav.oebs.digihot.config.common.logging.HttpLoggingFilter;
import no.nav.oebs.digihot.config.common.mdc.MdcFilter;
import no.nav.oebs.digihot.db.repository.KallLoggRepository;

@Configuration
public class HttpLoggingConfig {
	@Bean
	public FilterRegistrationBean<MdcFilter> mdcFilterRegistrationBean() {
		FilterRegistrationBean<MdcFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new MdcFilter());
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<HttpLoggingFilter> httpLoggingFilterRegistrationBean(KallLoggRepository kallLoggRepository) {
		FilterRegistrationBean<HttpLoggingFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(new HttpLoggingFilter(kallLoggRepository));
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
		return registrationBean;
	}
}
