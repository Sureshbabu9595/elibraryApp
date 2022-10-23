package com.library.elibrary.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.library.beans.LibrarianBean;
import com.library.elibrary.dao.ILibrarianDao;

@Controller
public class LibrarianController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianController.class);

	@Autowired
	private ILibrarianDao librarianDao;

	@GetMapping("/DeleteLibrarian")
	public ModelAndView deleteLibrarian(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... deleteLibrarian");
		String sid=httpServletRequest.getParameter("id");
		int id=Integer.parseInt(sid);
		int i = librarianDao.delete(id);
		ModelAndView view = new ModelAndView("viewlibrarian.html");
		if(i>0) {
			view.addObject("message", "Deleted Successfully!!");	
		}
		List<LibrarianBean> librarianList= librarianDao.view();
		view.addObject("librarianList", librarianList);
		return view;
	}

	@GetMapping("/AddLibrarianForm")
	public ModelAndView addlibrarianform(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... login");
		return new ModelAndView("addlibrarianform.html");
	}
	
	@PostMapping("/AddLibrarian")
	public ModelAndView addLibrarian(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String name=httpServletRequest.getParameter("name");
		String email=httpServletRequest.getParameter("email");
		String password=httpServletRequest.getParameter("password");
		String smobile=httpServletRequest.getParameter("mobile");
		long mobile=Long.parseLong(smobile);
		LibrarianBean bean=new LibrarianBean(name, email, password, mobile);
		int i = librarianDao.save(bean);
		ModelAndView modelAndView = new ModelAndView("addlibrarianform.html");
		String message = "";
		if(i>0) {
			message = "Librarian saved successfully";
		}
		modelAndView.addObject("message", message);
		return modelAndView;
	}
	
	@GetMapping("/ViewLibrarian")
	public ModelAndView viewLibrarian(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... viewLibrarian");
		List<LibrarianBean> librarianList= librarianDao.view();
		ModelAndView view = new ModelAndView("viewlibrarian.html");
		view.addObject("librarianList", librarianList);
		return view;
	}
	
	@GetMapping("/EditLibrarianForm")
	public ModelAndView editLibrarian(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... editLibrarian");
		String sid=httpServletRequest.getParameter("id");
		int id=Integer.parseInt(sid);
		LibrarianBean librarian = librarianDao.viewById(id);
		ModelAndView view = new ModelAndView("editlibrarianform.html");
		view.addObject("librarian", librarian);
		return view;
	}
	
	@PostMapping("/UpdateLibrarian")
	public ModelAndView updateLibrarian(HttpServletRequest httpServletRequest) {
		LOGGER.info("Enteriing... updateLibrarian");
		String name=httpServletRequest.getParameter("name");
		String email=httpServletRequest.getParameter("email");
		String password=httpServletRequest.getParameter("password");
		String smobile=httpServletRequest.getParameter("mobile");
		String sid=httpServletRequest.getParameter("id");
		int id=Integer.parseInt(sid);
		long mobile=Long.parseLong(smobile);
		LibrarianBean bean=new LibrarianBean(name, email, password, mobile);
		bean.setId(id);
		int i = librarianDao.update(bean);
		ModelAndView view = new ModelAndView("viewlibrarian.html");
		if(i>0) {
			view.addObject("message", "Updated Successfully!!");	
		}
		List<LibrarianBean> librarianList= librarianDao.view();
		view.addObject("librarianList", librarianList);
		return view;
	}
}
