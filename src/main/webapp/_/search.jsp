<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.sql.*"%>

<html>
<head>
	<title>簡易DBアプリ</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0">
	<link href="styles/style.css" rel="stylesheet" type="text/css">
</head>
<body>
	<br>

	<%
		ResultSet rset = (ResultSet) request.getAttribute("kekka");
	%>
	
	<table border="1">
		<tr bgcolor="#cccccc">
			<td><b>ID</b></td>
			<td><b>名前</b></td>
			<td><b>性別</b></td>
			<td><b>入社年</b></td>
			<td><b>住所</b></td>
			<td><b>削除</b></td>
			<td><b>変更</b></td>
		</tr>
		<%
			while (rset.next()) {
		%>
		<tr>
			<td><%=rset.getString(1)%></td>
			<td><%=rset.getString(2)%></td>
			<td><%=rset.getString(3)%></td>
			<td><%=rset.getString(4)%></td>
			<td><%=rset.getString(5)%></td>
			<td><a href="Edit?&mode=delete&id=<%=rset.getString(1)%>">削除</a></td>
			<td>
				<form action="/expenses_db/Edit" method="POST">
					<input type="hidden" name="mode" value="change">
					<input type="hidden" name="id" value="<%=rset.getString(1)%>"> 
					<input type="hidden" name="name" value="<%=rset.getString(2)%>">
					<input type="hidden" name="sei" value="<%=rset.getString(3)%>">
					<input type="hidden" name="nen" value="<%=rset.getString(4)%>">
					<input type="hidden" name="address" value="<%=rset.getString(5)%>">
					<input type="submit" value="変更">
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>

	<br>

	<a href="/index.jsp">トップに戻る</a>

</body>
</html>
