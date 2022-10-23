package com.library.elibrary.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.library.elibrary.dao.ILibrarianDao;

@Controller
public class LoginController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private ILibrarianDao librarianDao;
	
	@PostMapping("/LibrarianLogin")
	public ModelAndView librarianLogin(HttpServletRequest httpServletRequest, HttpServletResponse response) {
		LOGGER.info("Enteriing... login");
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		if (null != email && null != password && librarianDao.authenticate(email, password)) {
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute("email", email);
			return new ModelAndView("librarianloginsuccess.html");
		} else {
			return new ModelAndView("librarianLoginPage.html");
		}
	}
	
	@PostMapping("/AdminLogin")
	public ModelAndView adminLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LOGGER.info("Enteriing... adminLogin");
		String email = httpServletRequest.getParameter("email");
		String password = httpServletRequest.getParameter("password");
		if (null != email && email.equals("gangadevi@gmail.com") && null != password && password.equals("admin123")) {
			HttpSession session = httpServletRequest.getSession();
			session.setAttribute("admin", "true");
			return new ModelAndView("adminloginsuccess.html");
		} else {
			LOGGER.info("Admin Login failure");
			ModelAndView view = new ModelAndView("adminLoginPage.html");
			view.addObject("message", "User Name or Password is incorrect");
			return new ModelAndView("adminLoginPage.html");
		}
	}
	
	@GetMapping(path = { "/login", "/home" })
	public ModelAndView login(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... login");
		return new ModelAndView("index.html");
	}
	
	@GetMapping(path = { "/LibrarianLoginPage", "/LogoutLibrarian"})
	public ModelAndView librarianLoginPage(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... librarianLoginPage");
		return new ModelAndView("librarianLoginPage.html");
	}
	
	@GetMapping(path = { "/AdminLoginPage", "/LogoutAdmin" })
	public ModelAndView adminLoginPage(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... adminLoginPage");
		return new ModelAndView("adminLoginPage.html");
	}

}
