<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<body>
<%
	request.setCharacterEncoding("EUC-KR");
	int num = Integer.parseInt(request.getParameter("num"));
	
	// ���� ȭ�鿡�� �Ѿ�� �н���
	String pass = request.getParameter("password");
	//String num = request.getParameter("num");

//�����ͺ��̽� ��ü ����
	BoardDAO bdao = new BoardDAO();

	// �ش� �Խñ��� �н����尪�� ����
	String password = bdao.getPass(num);
	
	// ���� �н����尪�� update�� �Էµ� pass���� ������ ��
	if(pass.equals(password)) {
		// ������ �����ϴ� �޼ҵ� ȣ��
		bdao.deleteBoard(num);
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