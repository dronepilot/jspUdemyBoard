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

<!--  �����͸� �ѹ��� �޾ƿ��� ��Ŭ������ ���  -->
<jsp:useBean id="boardbean" class="model.BoardBean">
	<jsp:setProperty name="boardbean" property="*" />
</jsp:useBean>

<%
	//�����ͺ��̽� ��ü ����
	BoardDAO bdao = new BoardDAO();

	// �ش� �Խñ��� �н����尪�� ����
	String pass = bdao.getPass(boardbean.getNum());
	
	// ���� �н����尪�� update�� �Էµ� pass���� ������ ��
	if(pass.equals(boardbean.getPassword())) {
		// ������ �����ϴ� �޼ҵ� ȣ��
		bdao.updateBoard(boardbean);
		// ������ �Ϸ�Ǹ� ��ü �Խñ� ����
		response.sendRedirect("BoardList.jsp");
	} else {
%>
	<script type="text/javascript">	
		alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�. �ٽ� Ȯ���� �������ּ���.");
		history.go(-1);
	</script>
<%		
	}

	
	
%>



</body>
</html>