<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="dev.java.ExpensesBeans"%>

<html>
<head>
	<title>簡易DBアプリ</title>
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0">
	<link href="styles/main.css" rel="stylesheet" type="text/css">
</head>
<body>

	<%
	ExpensesBeans expenses = (ExpensesBeans) request.getAttribute("expenses");
	%>

	<br> 以下から変更できます。
	<br>
	<br>
	
	<form action="/expenses_db/Edit" method="POST">
		<table class="changeTable" id="changeTable" >
		<tr>
			<th>日付</th>
			<td><input type="text" name="change-date" value="<%=expenses.getDate()%>"></td>
		</tr>
		<tr>
			<th>料理</th>
			<td><input type="text" name="change-dish" value="<%=expenses.getDish()%>"></td>
		</tr>
		<tr>
			<th>カテゴリ</th>
			<td>
			<SELECT NAME="change-categ">
				<OPTION VALUE="<%=expenses.getCateg()%>" selected><%=expenses.getCateg()%>"</OPTION>
				<OPTION VALUE="食品">食品</OPTION>
				<OPTION VALUE="雑貨">雑貨</OPTION>
				<OPTION VALUE="電気・ガス代">電気・ガス代</OPTION>
				<OPTION VALUE="水道代">水道代</OPTION>
				<OPTION VALUE="通信料金">通信料金</OPTION>
			</SELECT>
			</td>
		</tr>
		<tr>
			<th>商品名</th>
			<td><input type="text" name="change-prod" value="<%=expenses.getProd()%>"></td>
		</tr>
		<tr>
			<th>金額</th>
			<td><input type="text" name="change-price" value="<%=expenses.getPrice()%>"></td>
		</tr>
		<tr>
			<th>比率</th>
			<td>
			<SELECT NAME="change-rate">
				<OPTION VALUE="<%=expenses.getRate()%>" selected><%=expenses.getRate()%>"</OPTION>
				<OPTION VALUE="5:5">5:5</OPTION>
				<OPTION VALUE="3:7">3:7</OPTION>
				<OPTION VALUE="7:3">7:3</OPTION>
			</SELECT>
			</td>
		</tr>
		</table>
		<input type="hidden" name="mode" value="del_add"> 
		<input type="submit" value="変更確定">
	</form>
	<br>
	
	<a href="/index.html">トップに戻る</a>

</body>
</html>