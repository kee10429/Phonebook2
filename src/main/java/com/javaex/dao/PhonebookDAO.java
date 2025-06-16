package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.PersonVO;

public class PhonebookDAO {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/phonebook_db";
	private String id = "phonebook";
	private String pw = "phonebook";

	// 생성자
	public PhonebookDAO(){}

	// 메소드gs
	
	//메소드일반
	// DB연결 메소드-공통
	private void connect() { // 메인에서는 사용하지 못함

		try {
			// 1. JDBC 드라이버 (MySQL) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			this.conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}
	
	// 자원정리 메소드-공통
	private void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	// 전체리스트 가져오기
	public List<PersonVO> personSelect() {
		
		//리스트준비
		List<PersonVO> personList = new ArrayList<PersonVO>();
		
		this.connect();
		System.out.println("personSelect()");
		try {
			//3. SQL문준비 / 바인딩 / 실행
			// SQL문준비
			String query= "";
			query +=" select  person_id, ";
			query +="		  name, ";
			query +="         hp, ";
			query +="         company ";
			query +=" from person ";
			query +=" order by person_id desc ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);
			
			// 실행
			rs = pstmt.executeQuery();
			
			//4. 결과처리
			while(rs.next()) {//반복한다
				//ResultSet에서 각각의 값을 꺼내서 자바 변수에 담는다
				int personId = rs.getInt("person_id");
				String name = rs.getString("name");
				String hp = rs.getString("hp");
				String company = rs.getString("company");
				
				//VO로 묶어준다
				PersonVO personVO = new PersonVO(personId, name, hp, company);
				
				//묶여진VO를 리스트에 추가한다
				personList.add(personVO);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		this.close();
		
		return personList;
		
	}
	
}