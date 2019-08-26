package com.rab.licenta.AutoClinique.web;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.rab.licenta.AutoClinique.constants.CookieConstants;
import com.rab.licenta.AutoClinique.constants.WebConstants;
import com.rab.licenta.AutoClinique.entity.User;
import com.rab.licenta.AutoClinique.service.CookieService;
import com.rab.licenta.AutoClinique.service.DbService;

@Controller
@RequestMapping("/delete")
public class DeleteController {

	@Autowired
	DbService dbService;
	
	@Autowired
	CookieService cookieService;
	
	@RequestMapping(value = "/sent/message/{id}/",method = RequestMethod.DELETE)
	public ModelAndView deleteSentMessage(HttpServletRequest request, HttpServletResponse response,
										 @PathVariable("id") String id) {
		
		String loggedIn = cookieService.getCookieValue(request,CookieConstants.LOGGED_IN_KEY);
		
		if(loggedIn.equals(CookieConstants.LOGGED_IN_VALUE_GOOD)) {
			
			dbService.deleteMessage(id);
			
			try {
				User user = dbService.getUserByName(cookieService.getCookieValue(request,CookieConstants.USERNAME_KEY));
				
				if(user.getTipUtilizator().equals("support"))
					response.sendRedirect(request.getContextPath()+"/consumeAllSupport");
				else
					response.sendRedirect(request.getContextPath()+"/consumeAllCustomer");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
			
		return new ModelAndView("login");
	}
}

