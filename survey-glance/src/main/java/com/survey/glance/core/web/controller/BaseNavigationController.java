package com.survey.glance.core.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.survey.glance.core.web.service.AuthenticationSuccessHandlerImpl;

/**
 * @author Administrator
 *
 */
@Controller
public class BaseNavigationController {

	@Autowired
	@Qualifier("customAuthenticationSuccessHandler")
	private AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;

	/**
	 * @param request
	 * @param response
	 * @param model
	 * @throws IOException
	 */
	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public void defalutPage(HttpServletRequest request,
			HttpServletResponse response, ModelMap model) throws IOException {
		final Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		authenticationSuccessHandlerImpl.onAuthenticationSuccess(request,
				response, authentication);

	}

	/**
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap model) {
		return "login";
	}

	

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutPage(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loginFailure", method = RequestMethod.GET)
	public String loginFailure(HttpServletRequest request,
			HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?error";
	}
}
