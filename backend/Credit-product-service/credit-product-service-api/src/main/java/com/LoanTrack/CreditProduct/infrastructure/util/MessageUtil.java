package com.LoanTrack.Backend.infrastructure.util;

import com.LoanTrack.Backend.domain.service.MessageUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtil implements MessageUtilService {

	@Autowired
	private MessageSource messageSource;
	
	public String getMessage(String key) {
		return messageSource.getMessage(key, new Object[] {}, LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, Locale local) {
		return messageSource.getMessage(key, new Object[] {}, local);
	}

	public String getMessage(String key, Object[] params) {
		return messageSource.getMessage(key, params, LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, Object[] params, Locale local) {
		return messageSource.getMessage(key, params, local);
	}

}
