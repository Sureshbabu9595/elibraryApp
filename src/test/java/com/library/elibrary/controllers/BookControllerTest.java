package com.library.elibrary.controllers;

import static org.junit.Assert.*;

import javax.servlet.ServletContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;

import com.library.elibrary.dao.IBookDao;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	IBookDao bookDao;

	@Test
	public void testAddBookPage() {
		
	}

	@Test
	public void testIssueBookForm() {
		fail("Not yet implemented");
	}

	@Test
	public void testReturnBookForm() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testViewBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testIssueBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testViewIssuedBook() {
		fail("Not yet implemented");
	}

	@Test
	public void testReturnBook() {
		fail("Not yet implemented");
	}

}
