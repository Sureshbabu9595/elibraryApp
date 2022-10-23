package com.library.elibrary.dao;

import java.util.List;

import com.library.beans.BookBean;
import com.library.beans.IssueBookBean;

public interface IBookDao {

	int save(BookBean bean);

	List<BookBean> view();

	int delete(String callno);

	int getIssued(String callno);

	boolean checkIssue(String callno);

	int issueBook(IssueBookBean bean);

	int returnBook(String callno, int studentid);

	List<IssueBookBean> viewIssuedBooks();
}