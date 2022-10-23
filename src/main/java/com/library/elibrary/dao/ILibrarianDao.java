package com.library.elibrary.dao;

import java.util.List;

import com.library.beans.LibrarianBean;

public interface ILibrarianDao {

	int save(LibrarianBean bean);

	int update(LibrarianBean bean);

	List<LibrarianBean> view();

	LibrarianBean viewById(int id);

	boolean authenticate(String email, String password);

	int delete(int id);

}