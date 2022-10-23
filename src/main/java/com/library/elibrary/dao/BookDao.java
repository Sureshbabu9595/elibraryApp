package com.library.elibrary.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.library.beans.BookBean;
import com.library.beans.IssueBookBean;

@Repository
public class BookDao implements IBookDao {

	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	public BookDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		super();
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public int save(BookBean bookBean) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", bookBean.getCallno());
		paramMap.put("name", bookBean.getName());
		paramMap.put("author", bookBean.getAuthor());
		paramMap.put("publisher", bookBean.getPublisher());
		paramMap.put("quantity", String.valueOf(bookBean.getQuantity()));
		paramMap.put("issued", String.valueOf(bookBean.getIssued()));
		return namedParameterJdbcTemplate.update(
				"insert into E_BOOK(CALLNO, NAME, AUTHOR, PUBLISHER, QUANTITY, ISSUED) values(:callno,:name,:author,:publisher,:quantity,:issued)",
				paramMap);
	}

	public List<BookBean> view() {
		List<BookBean> list = namedParameterJdbcTemplate
				.query("select CALLNO, NAME, AUTHOR, PUBLISHER, QUANTITY, ISSUED from E_BOOK", new HashMap<>(),rs -> {
					List<BookBean> returnValue = new ArrayList<>();
					while (rs.next()) {
						BookBean bookBean = new BookBean();
						bookBean.setCallno(rs.getString("CALLNO"));
						bookBean.setName(rs.getString("NAME"));
						bookBean.setAuthor(rs.getString("AUTHOR"));
						bookBean.setPublisher(rs.getString("PUBLISHER"));
						bookBean.setQuantity(rs.getInt("QUANTITY"));
						bookBean.setIssued(rs.getInt("ISSUED"));
						returnValue.add(bookBean);
					}
					return returnValue;
				});
		return list;
	}

	public int delete(String callno) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", callno);
		return namedParameterJdbcTemplate.update("delete from E_BOOK where callno=:callno", paramMap);
	}

	public int getIssued(String callno) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", callno);
		int returnValue = namedParameterJdbcTemplate.query("select ISSUED from E_BOOK where callno=:callno", paramMap, rs -> {
			int issued = 0;
			while (rs.next()) {
				issued = rs.getInt("ISSUED");
			}
			return issued;
		});
		return returnValue;
	}

	public boolean checkIssue(String callno) {
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("callno", callno);
		int returnValue = namedParameterJdbcTemplate
				.query("select count(*) as count from E_BOOK where callno = :callno and quantity > issued",paramMap, rs -> {
					int issued = 0;
					while (rs.next()) {
						issued = rs.getInt("count");
					}
					return issued;
				});
		return returnValue > 0;
	}

	public int issueBook(IssueBookBean bean) {
		String callno = bean.getCallno();
		boolean checkstatus = checkIssue(callno);
		System.out.println("Check status: " + checkstatus);
		if (checkstatus) {
			int status = 0;
			Map<String, Object> paramMap = new HashMap<>();
			paramMap.put("callno", bean.getCallno());
			paramMap.put("studentid", bean.getStudentid());
			paramMap.put("studentname", bean.getStudentname());
			paramMap.put("studentmobile", String.valueOf(bean.getStudentmobile()));
			java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
			paramMap.put("issuedate", currentDate);
			paramMap.put("returnstatus", "no");
			status = namedParameterJdbcTemplate.update(
					"insert into E_ISSUEBOOK(CALLNO, STUDENTID, STUDENTNAME, STUDENTMOBILE, ISSUEDDATE, RETURNSTATUS) values(:callno,:studentid,:studentname,:studentmobile,:issuedate,:returnstatus)",
					paramMap);
			if (status > 0) {
				paramMap = new HashMap<>();
				paramMap.put("callno", bean.getCallno());
				paramMap.put("issued", getIssued(callno) + 1);
				status = namedParameterJdbcTemplate.update("update E_BOOK set issued=:issued, quantity = (quantity-1) where callno=:callno",
						paramMap);
			}

			return status;
		} else {
			return 0;
		}
	}

	public int returnBook(String callno, int studentid) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("callno", callno);
		paramMap.put("studentid", studentid);

		int status = namedParameterJdbcTemplate.update(
				"update E_ISSUEBOOK set returnstatus='yes' where callno=:callno and studentid=:studentid", paramMap);
		if (status > 0) {
			paramMap = new HashMap<>();
			paramMap.put("callno", callno);
			paramMap.put("issued", getIssued(callno) - 1);
			status = namedParameterJdbcTemplate.update("update E_BOOK set issued=:issued, quantity = (quantity+1) where callno=:callno",
					paramMap);
		}
		return status;
	}

	public List<IssueBookBean> viewIssuedBooks() {
		List<IssueBookBean> list = namedParameterJdbcTemplate.query(
				"select callno, studentid, studentname, studentmobile, issueddate, returnstatus from E_ISSUEBOOK order by issueddate desc",new HashMap<>(),
				rs -> {
					List<IssueBookBean> returnValue = new ArrayList<>();
					while (rs.next()) {
						IssueBookBean issueBookBean = new IssueBookBean();
						issueBookBean.setCallno(rs.getString("callno"));
						issueBookBean.setStudentid(rs.getString("studentid"));
						issueBookBean.setStudentname(rs.getString("studentname"));
						issueBookBean.setStudentmobile(rs.getLong("studentmobile"));
						issueBookBean.setIssueddate(rs.getDate("issueddate"));
						issueBookBean.setReturnstatus(rs.getString("returnstatus"));
						returnValue.add(issueBookBean);
					}
					return returnValue;
				});
		return list;
	}
}
