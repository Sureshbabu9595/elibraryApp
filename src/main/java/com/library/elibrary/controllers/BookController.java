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

import com.library.beans.BookBean;
import com.library.beans.IssueBookBean;
import com.library.elibrary.dao.IBookDao;

@Controller
public class BookController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	private IBookDao bookDao;

	@GetMapping("/AddBookForm")
	public ModelAndView addBookPage(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... addBookPage");
		return new ModelAndView("addbookPage.html");
	}
	
	@GetMapping("/IssueBookForm")
	public ModelAndView issueBookForm(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... issueBookForm");
		return new ModelAndView("issueBookPage.html");
	}
	
	@GetMapping("/ReturnBookForm")
	public ModelAndView returnBookForm(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... returnBookForm");
		return new ModelAndView("returnBookPage.html");
	}

	@PostMapping("/AddBook")
	public ModelAndView addBook(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		LOGGER.info("Entering... addBookPage");
		String callno = httpServletRequest.getParameter("callno");
		String name = httpServletRequest.getParameter("name");
		String author = httpServletRequest.getParameter("author");
		String publisher = httpServletRequest.getParameter("publisher");
		String squantity = httpServletRequest.getParameter("quantity");
		int quantity = Integer.parseInt(squantity);
		BookBean bean = new BookBean(callno, name, author, publisher, quantity);
		ModelAndView modelAndView = new ModelAndView("addbookPage.html");
		modelAndView.setViewName("addbookPage.html");
		int i = bookDao.save(bean);
		if(i>0) {
			modelAndView.addObject("message", "Book saved successfully");
		}
		return modelAndView;
	}
	
	@GetMapping("/ViewBook")
	public ModelAndView viewBook(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... addBookPage");
		ModelAndView modelAndView = new ModelAndView("viewbook.html");
		List<BookBean> bookList =bookDao.view();
		modelAndView.addObject("bookList", bookList);
		return modelAndView;
	}
	
	@GetMapping("/DeleteBook")
	public ModelAndView deleteBook(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... deleteBook");
		String callno=httpServletRequest.getParameter("callno");
		int i = bookDao.delete(callno);
		String message ="";
		if(i>0) {
			message = "Book Deleted successfully!!";
		}
		ModelAndView modelAndView = new ModelAndView("viewbook.html");
		List<BookBean> bookList =bookDao.view();
		modelAndView.addObject("bookList", bookList);
		modelAndView.addObject("message",message);
		return modelAndView;
	}
	
	@PostMapping("/IssueBook")
	public ModelAndView issueBook(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... issueBook");
		String callno=httpServletRequest.getParameter("callno");
		String studentid=httpServletRequest.getParameter("studentid");
		String studentname=httpServletRequest.getParameter("studentname");
		String sstudentmobile=httpServletRequest.getParameter("studentmobile");
		long studentmobile=Long.parseLong(sstudentmobile);
		
		IssueBookBean bean=new IssueBookBean(callno,studentid,studentname,studentmobile);
		int i=bookDao.issueBook(bean);
		
		String message ="";
		if(i>0) {
			message = "Book Issued successfully!!";
		}else {
			message = "Sorry, unable to issue book. We may have sortage of books. Kindly visit later.";
		}
		ModelAndView modelAndView = new ModelAndView("librarianloginsuccess.html");
		modelAndView.addObject("message",message);
		return modelAndView;
	}
	
	@GetMapping("/ViewIssuedBook")
	public ModelAndView viewIssuedBook(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... viewIssuedBook");
		ModelAndView modelAndView = new ModelAndView("viewIssueBook.html");
		List<IssueBookBean> issueBookList =bookDao.viewIssuedBooks();
		modelAndView.addObject("issueBookList", issueBookList);
		return modelAndView;
	}
	
	
	@PostMapping("/ReturnBook")
	public ModelAndView returnBook(HttpServletRequest httpServletRequest) {
		LOGGER.info("Entering... returnBook");
		String callno=httpServletRequest.getParameter("callno");
		String sstudentid=httpServletRequest.getParameter("studentid");
		int studentid=Integer.parseInt(sstudentid);
		
		int i=bookDao.returnBook(callno,studentid);
		String message ="";
		if(i>0) {
			message = "Book returned successfully!!";
		}else {
			message = "Sorry, unable to return book. Please Kindly visit later.";
		}
		ModelAndView modelAndView = new ModelAndView("librarianloginsuccess.html");
		modelAndView.addObject("message",message);
		return modelAndView;
	}

}
