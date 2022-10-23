package com.library.elibrary.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.beans.LibrarianBean;

/**
 * 
 * @author Gangadevi
 *
 */
@Repository
public class LibrarianDao implements ILibrarianDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LibrarianDao.class);

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public LibrarianDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public int save(LibrarianBean librarianBean) {
		LOGGER.info(String.format("Entering.. Save to insert librarianBean:: [%s]",librarianBean));
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("name", librarianBean.getName());
		paramMap.put("email", librarianBean.getEmail());
		paramMap.put("password", librarianBean.getPassword());
		paramMap.put("mobile", librarianBean.getMobile());
		return namedParameterJdbcTemplate.update(
				"insert into E_LIBRARIAN(name,email,password,mobile) values(:name,:email,:password,:mobile)", paramMap);
	}

	public int update(LibrarianBean librarianBean) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("name", librarianBean.getName());
		paramMap.put("email", librarianBean.getEmail());
		paramMap.put("password", librarianBean.getPassword());
		paramMap.put("mobile", String.valueOf(librarianBean.getMobile()));
		paramMap.put("id", String.valueOf(librarianBean.getId()));
		return namedParameterJdbcTemplate.update(
				"update E_LIBRARIAN set name=:name,email=:email,password=:password,mobile=:mobile where id=:id",
				paramMap);
	}

	public List<LibrarianBean> view() {
		List<LibrarianBean> list = namedParameterJdbcTemplate
				.query("select name, email, password, mobile, id from E_LIBRARIAN", new HashMap<>(), (rs) -> {
					List<LibrarianBean> returnValue = new ArrayList<>();
					while (rs.next()) {
						LibrarianBean librarianBeanRow = new LibrarianBean();
						librarianBeanRow.setName(rs.getString("name"));
						librarianBeanRow.setEmail(rs.getString("email"));
						librarianBeanRow.setPassword(rs.getString("password"));
						librarianBeanRow.setMobile(rs.getLong("mobile"));
						librarianBeanRow.setId(rs.getInt("id"));
						returnValue.add(librarianBeanRow);
					}
					return returnValue;
				});
		return list;
	}

	public LibrarianBean viewById(int id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", String.valueOf(id));
		LibrarianBean librarianBean = namedParameterJdbcTemplate
				.query("select name, email, password, mobile, id from E_LIBRARIAN where id=:id", paramMap, rs -> {
					LibrarianBean librarianBeanRow = new LibrarianBean();
					while (rs.next()) {
						librarianBeanRow.setName(rs.getString("name"));
						librarianBeanRow.setEmail(rs.getString("email"));
						librarianBeanRow.setPassword(rs.getString("password"));
						librarianBeanRow.setMobile(rs.getLong("mobile"));
						librarianBeanRow.setId(rs.getInt("id"));
					}
					return librarianBeanRow;
				});
		return librarianBean;
	}

	@Override
	public int delete(int id) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("id", String.valueOf(id));
		return namedParameterJdbcTemplate.update("delete from E_LIBRARIAN where id=:id", paramMap);
	}

	public boolean authenticate(String email, String password) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("email", email);
		paramMap.put("password", password);

		LibrarianBean librarianBean = namedParameterJdbcTemplate.query(
				"select name, email, password, mobile, id from E_LIBRARIAN where email=:email and password=:password",
				paramMap, rs -> {
					LibrarianBean librarianBeanRow = new LibrarianBean();
					while (rs.next()) {
						librarianBeanRow.setName(rs.getString("name"));
						librarianBeanRow.setEmail(rs.getString("email"));
						librarianBeanRow.setPassword(rs.getString("password"));
						librarianBeanRow.setMobile(rs.getLong("mobile"));
						librarianBeanRow.setId(rs.getInt("id"));
					}
					return librarianBeanRow;
				});
		return librarianBean.getId() > 0 ? true : false;
	}
}
