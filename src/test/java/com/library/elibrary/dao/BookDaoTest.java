package com.library.elibrary.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.library.beans.BookBean;
import com.library.beans.IssueBookBean;

@ExtendWith(MockitoExtension.class)
class BookDaoTest {

	@InjectMocks
	BookDao bookDao;

	@Mock
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Test
	void testSave() {
		BookBean bookBean = new BookBean("101", "Gone Girl", "Martin", "VJ Publications", 50);
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", bookBean.getCallno());
		paramMap.put("name", bookBean.getName());
		paramMap.put("author", bookBean.getAuthor());
		paramMap.put("publisher", bookBean.getPublisher());
		paramMap.put("quantity", String.valueOf(bookBean.getQuantity()));
		paramMap.put("issued", String.valueOf(bookBean.getIssued()));
		when(namedParameterJdbcTemplate.update(
				"insert into E_BOOK(CALLNO, NAME, AUTHOR, PUBLISHER, QUANTITY, ISSUED) values(:callno,:name,:author,:publisher,:quantity,:issued)",
				paramMap)).thenReturn(1);
		int result = bookDao.save(bookBean);
		assertEquals(1, result);
		when(namedParameterJdbcTemplate.update(
				"insert into E_BOOK(CALLNO, NAME, AUTHOR, PUBLISHER, QUANTITY, ISSUED) values(:callno,:name,:author,:publisher,:quantity,:issued)",
				paramMap)).thenReturn(0);
		result = bookDao.save(bookBean);
		assertEquals(0, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testView() {
		List<BookBean> books = new ArrayList<>();
		BookBean bookFromDB = new BookBean("101", "Gone Girl", "Martin", "VJ Publications", 50);
		books.add(bookFromDB);
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(books);
		List<BookBean> booksFromDB = bookDao.view();
		assertNotNull(booksFromDB);
		assertFalse(booksFromDB.isEmpty());
	}

	@Test
	void testDelete() {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", "101");
		when(namedParameterJdbcTemplate.update("delete from E_BOOK where callno=:callno", paramMap)).thenReturn(1);
		int result = bookDao.delete("101");
		assertEquals(1, result);

		when(namedParameterJdbcTemplate.update("delete from E_BOOK where callno=:callno", paramMap)).thenReturn(0);
		result = bookDao.delete("101");
		assertEquals(0, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testGetIssued() {
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(1);
		int result = bookDao.getIssued("101");
		assertEquals(1, result);

		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(0);
		result = bookDao.getIssued("101");
		assertEquals(0, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testCheckIssue() {
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(1);
		boolean result = bookDao.checkIssue("101");
		assertTrue(result);

		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(0);
		result = bookDao.checkIssue("101");
		assertFalse(result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testIssueBook() {
		Map<String, Object> paramMap = new HashMap<>();
		IssueBookBean issueBookFromDB = new IssueBookBean("101", "65647", "Ganga", 834567);
		paramMap.put("callno", issueBookFromDB.getCallno());
		paramMap.put("studentid", issueBookFromDB.getStudentid());
		paramMap.put("studentname", issueBookFromDB.getStudentname());
		paramMap.put("studentmobile", String.valueOf(issueBookFromDB.getStudentmobile()));
		java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
		paramMap.put("issuedate", currentDate);
		paramMap.put("returnstatus", "no");
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(1);
		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
		int result = bookDao.issueBook(issueBookFromDB);
		assertEquals(1, result);
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(0);
		result = bookDao.issueBook(issueBookFromDB);
		assertEquals(0, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testReturnBook() {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("callno", "101");
		paramMap.put("studentid", 23456);
		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(1);
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(1);
		int result = bookDao.returnBook("101", 23456);
		assertEquals(1, result);

		when(namedParameterJdbcTemplate.update(Mockito.anyString(), Mockito.anyMap())).thenReturn(0);
		result = bookDao.returnBook("101", 23456);
		assertEquals(0, result);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testViewIssuedBooks() {
		List<IssueBookBean> books = new ArrayList<>();
		IssueBookBean issueBookFromDB = new IssueBookBean("101", "6564756", "Ganga", 834567649);
		books.add(issueBookFromDB);
		when(namedParameterJdbcTemplate.query(Mockito.anyString(), Mockito.anyMap(),
				Mockito.any(ResultSetExtractor.class))).thenReturn(books);
		List<IssueBookBean> issueBookBeansFromDB = bookDao.viewIssuedBooks();
		assertNotNull(issueBookBeansFromDB);
		assertFalse(issueBookBeansFromDB.isEmpty());
	}
}
