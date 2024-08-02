package com.example.drinkservice.until.configs;

import com.example.drinkservice.until.constants.MessageSourceConstant;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * LocaleConfig chịu trách nhiệm thiết lập và quản lý các cài đặt liên quan đến ngôn ngữ và bản địa hóa (locale)
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /**
     * Cấu hình LocaleResolver sử dụng SessionLocaleResolver để lưu trữ thông tin về ngôn ngữ trong session.
     * Đặt ngôn ngữ mặc định là tiếng Anh.
     *
     * @return LocaleResolver đã được cấu hình.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.ENGLISH);
        return slr;
    }

    /**
     * Cấu hình LocaleChangeInterceptor để cho phép thay đổi ngôn ngữ thông qua tham số 'lang' trên URL.
     *
     * @return LocaleChangeInterceptor đã được cấu hình.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName(MessageSourceConstant.PARAM_NAME_LANG);
        return lci;
    }

    /**
     * Thêm LocaleChangeInterceptor vào danh sách các interceptor của ứng dụng.
     *
     * @param registry InterceptorRegistry để thêm các interceptor.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Cấu hình MessageSource sử dụng ResourceBundleMessageSource để đọc các tệp tin properties chứa thông báo.
     * Đặt mã nguồn thông báo và mã hóa mặc định từ các hằng số đã định nghĩa trong MessageSourceConstant.
     *
     * @return MessageSource đã được cấu hình.
     */
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(MessageSourceConstant.BASE_NAMES);
        messageSource.setDefaultEncoding(MessageSourceConstant.DEFAULT_UTF8);
        return messageSource;
    }
}
