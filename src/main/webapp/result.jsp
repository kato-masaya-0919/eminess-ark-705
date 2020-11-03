<%@ page language="java" contentType="text/html;charset=UTF-8"%>

<html>
<head>
	<title>簡易DBアプリ</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0">
	<link href="styles/main.css" rel="stylesheet" type="text/css">
</head>
<body>
	<br>

	<%
		String status = (String)request.getAttribute("status");
	%>

	<br>
	<br>
	<%=status%>
	<br>
	<br>

	<a href="/index.html">トップページに戻る</a>


</body>
</html>
