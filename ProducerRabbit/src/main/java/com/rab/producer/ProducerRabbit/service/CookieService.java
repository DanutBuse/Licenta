package com.rab.producer.ProducerRabbit.service;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

	public CookieService() {
		
	}
	
	public void setCookie(HttpServletResponse response, String key, String value) {
		response.addCookie(new Cookie(key,value));
		
	}

	public String getCookieValue(HttpServletRequest request, String key) {
		
		List<Cookie> cookies = Arrays.asList(request.getCookies());

		return cookies.stream()
				 .filter(x -> x.getName().equals(key) )
				 .map(x -> x.getValue())
				 .findFirst()
				 .get();
	}

}
