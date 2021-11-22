<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<body>
<%
	request.setCharacterEncoding("EUC-KR");

	//int num = Integer.parseInt(request.getParameter("num").trim());
%>

<!--  데이터를 한번에 받아오는 빈클래스를 사용  -->
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*" />
</jsp:useBean>

<%
	//데이터베이스 객체 생성
	BoardDAO bdao = new BoardDAO();

	// 해당 게시글의 패스워드값을 얻어옴
	String pass = bdao.getPass(boardbean.getNum());
	
	// 기존 패스워드값과 update시 입력된 pass값이 같은지 비교
	if(pass.equals(boardbean.getPassword())) {
		// 데이터 수정하는 메소드 호출
		bdao.updateBoard(boardbean);
		// 수정이 완료되면 전체 게시글 보기
		response.sendRedirect("BoardList.jsp");
	} else {
%>
	<script type="text/javascript">	
		alert("비밀번호가 일치하지 않습니다. 다시 확인후 수정해주세요.");
		history.go(-1);
	</script>
<%		
	}

	
	
%>



</body>
</html>