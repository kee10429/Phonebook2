<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import= "java.util.List"  %>
<%@ page import = "com.javaex.vo.PersonVO" %>


<%
	List<PersonVO> personList = (List<PersonVO>)request.getAttribute("pList");
	System.out.println("여기는 jsp");
	System.out.println(personList);

%>




<!DOCTYPE html>
<html>
   <head>
      <meta charset="UTF-8">
      <title>Insert title here</title>
   </head>
   <body>
      <h1>주소록</h1>
      
      <h2>전화번호 리스트</h2>
      <p>전화번호 리스트 입니다.</p>
      
      <%
      	for(int i=0; i<personList.size(); i++) {
      %>		
      
      <table border="1">
         <tbody>
            <tr>
               <td>이름(name)</td>
               <td><%= personList.get(i).getName() %></td>
            <tr>
            <tr>
               <td>핸드폰(hp)</td>
               <td><%= personList.get(i).getHp() %></td>
            <tr>
            <tr>
               <td>회사(company)</td>
               <td><%= personList.get(i).getCompany() %></td>
            <tr>      
         </tbody>
      </table>
      <br>
      <% 
      }
      %>
      
      
      
   
   </body>
</html>