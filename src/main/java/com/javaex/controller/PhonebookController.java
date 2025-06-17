package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhonebookDAO;
import com.javaex.vo.PersonVO;


@WebServlet("/pbc")
public class PhonebookController extends HttpServlet {
	//필드
	private static final long serialVersionUID = 1L;
       
	//생성자 기본생성자 사용 그래서 삭제했음

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//작동했는지 확인용(먼저 반드시 확인부터 할것)
		System.out.println("PhonebookController");
		
		//action 파라미터의 값이 뭔지 알아야됨
		String action = request.getParameter("action");
		System.out.println(action);//업무구분
		
	
		
		if("list".equals(action)) {//리스트업무
		
			
			//db데이터가져온다  --> list
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			List<PersonVO> personList = phonebookDAO.personSelect();
			
			System.out.println(personList);
			
			//html + list
			
			//저밑에 있는 list.jsp에게 후반일 html을 만들고 응답문서 만들어보낸다
			//1)request 객체에 데이터를 넣어준다
			request.setAttribute("pList", personList);
			
			//2)list.jsp에 request 객체와 response객체를 보낸다
			//**포워드
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
		////////////////////////////////////////////////////////////
		}else if("wform".equals(action)) {//등록폼업무
			System.out.println("등록폼");
			
			//등록폼을 응답해야한다
			//1) DB관련 할일이 없다 - 안하면 된다
			
			//2)jsp에게 화면을 그리게 한다(포워드)
			//writeForm.jsp 에게 포워드한다
			RequestDispatcher rd = request.getRequestDispatcher("/writeForm.jsp");
			rd.forward(request, response);

		}else if("write".equals(action)) {//등록업무 
			System.out.println("등록");
			
			//1)파라미터 값 3개 꺼내기
			String name = request.getParameter("name");
			String hp = request.getParameter("hp");
			String company = request.getParameter("company");
			
			//데이터를 묶는다
			PersonVO personVO = new PersonVO(name, hp, company);
			  //-> 같은 모양으로 생긴 생성자를 VO에 만들어야함
			
			System.out.println(personVO);
			
			
			//2)DAO를 통해서 저장시키기
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personInsert(personVO);
			
			// 리다이렉트 list 요청해주세요
			response.sendRedirect("http://localhost:8080/phonebook2/pbc?action=list");
			
			
			
			/*
			//응답 (리스트) 하기 -----------------------------------------------
			// - dao시켜서 데이터 가져오기
			List<PersonVO> personList = phonebookDAO.personSelect();
			
			//request의 어트리뷰트 
			request.setAttribute("pList", personList);

			// jsp 포워드 
			RequestDispatcher rd = request.getRequestDispatcher("/list.jsp");
			rd.forward(request, response);
			*/
			
		}else if("delete".equals(action)) {
			System.out.println("삭제");
			
			// 1)파라미터에서 no값을 꺼내온다
			int no = Integer.parseInt(request.getParameter("no"));  
			
			// 2)dao를 통해서 삭제
			PhonebookDAO phonebookDAO = new PhonebookDAO();
			phonebookDAO.personDelete(no);
			
			// 리다이렉트 action=list
			response.sendRedirect("http://localhost:8080/phonebook2/pbc?action=list");
			
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
