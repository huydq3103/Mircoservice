package com.example.apigateway.services.implement;

import com.example.apigateway.services.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Implementations của IMessageService.
 * Service này cung cấp phương thức để lấy thông điệp dựa trên key từ MessageSource.
 */
@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private MessageSource messageSource;

    /**
     * Lấy thông điệp từ MessageSource dựa trên key và ngôn ngữ hiện tại của ứng dụng.
     *
     * @param key Key của thông điệp trong file properties.
     * @return Chuỗi thông điệp tương ứng với key và ngôn ngữ hiện tại.
     */
    @Override
    public String getMessage(String key) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }

}
