<%@page import="model.BoardBean"%>
<%@page import="model.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<body>
<%
	// �ش� �Խñ۹�ȣ�� ���Ͽ� �Խñ��� ����
	int num = Integer.parseInt(request.getParameter("num").trim());
	
	// �ϳ��� �Խñۿ����� ������ ����
	BoardDAO bdao = new BoardDAO();
	
	BoardBean bean = bdao.getOneUpdateBoard(num);
		
%>
<center>
<h2>�Խñۻ���</h2>
<form action="BoardDeleteProc.jsp" method="post">
<table width="600" border="1" bgcolor="skyblue">
	<tr height="40">
		<td width="120" align="center">�ۼ���</td>
		<td width="180" align="center"><%=bean.getWriter() %></td>
		<td width="120" align="center">�ۼ���</td>
		<td width="180" align="center"><%=bean.getReg_date() %></td>
	</tr>	
	<tr>
		<td width="120" align="center">����</td>
		<td align="center" colspan="3"><%=bean.getSubject() %></td>
	</tr>
	<tr height="40">
		<td width="120" align="center">��й�ȣ </td>
		<td colspan="3">
			<input type="password" name="password" size="60">
		</td>
	</tr>
	
	<tr height="40">
		<td colspan="4" align="center">
			<input type="hidden" name="num" value="<%=bean.getNum()%>">			
			<input type="submit" value="�ۻ���">&nbsp;&nbsp;
			<input type="button" onclick="location.href='BoardList.jsp'" value="��ü�ۺ���">
		</td>
	</tr>		
</table>
</form>
</center>
</body>
</html>