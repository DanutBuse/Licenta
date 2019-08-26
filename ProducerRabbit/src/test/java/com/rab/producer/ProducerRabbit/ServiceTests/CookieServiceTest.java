package com.rab.producer.ProducerRabbit.ServiceTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.when;

import com.rab.licenta.AutoClinique.service.CookieService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes={CookieService.class})
public class CookieServiceTest {

	@Mock
	HttpServletResponse response;
	
	@Mock
	HttpServletRequest request;
	
	@InjectMocks
	CookieService cookieService;
	
	@Before
	public void setUp() throws Exception {
		 MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSetCookie() {
		cookieService.setCookie(response, "asd", "asd");
		verify(response, times(1)).addCookie(any(Cookie.class));
	}
	
}
