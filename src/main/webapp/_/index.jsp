<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@page import="java.sql.*"%>

<html>
<head>
	<title>簡易DBアプリ</title>
	<meta name="viewport" content="width=device-width initial-scale=1.0 minimum-scale=1.0 maximum-scale=1.0 user-scalable=no">
	<link href="styles/style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="js/javascript.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
	<br>
	検索条件を入力してください。（何も入力しないと全件抽出します）<br>
	<br>
	日付: 
	<input type="text" id="search-date"> 
	曜日: 
	<input type="text" id="search-yobi">
	料理: 
	<input type="text" id="search-dish">
	カテゴリ： 
	<SELECT id="search-categ">
		<OPTION VALUE="" selected></OPTION>
		<OPTION VALUE="食品">食品</OPTION>
		<OPTION VALUE="雑貨">雑貨</OPTION>
		<OPTION VALUE="電気・ガス代">電気・ガス代</OPTION>
		<OPTION VALUE="水道代">水道代</OPTION>
		<OPTION VALUE="通信料金">通信料金</OPTION>
	</SELECT>
	商品名: 
	<input type="text" id="search-prod">
	金額: 
	<input type="text" id="search-price">
	比率： 
	<SELECT id="search-rate">
		<OPTION VALUE="" selected></OPTION>
		<OPTION VALUE="5:5">5:5</OPTION>
		<OPTION VALUE="3:7">3:7</OPTION>
		<OPTION VALUE="7:3">7:3</OPTION>
	</SELECT> 
	<br>
	<br>
	<input id = "search-btn" type="button" value="検索">
	
	<!-- -------------- -->
	
	<br> 以下から登録できます。
	<br>
	<br>
	<form action="/expenses_db/Edit" method="POST">
		日付: 
		<input type="text" name="reg-date"> 
		曜日: 
		<input type="text" name="reg-yobi">
		料理: 
		<input type="text" name="reg-dish">
		カテゴリ： 
		<SELECT NAME="reg-categ">
			<OPTION VALUE="" selected></OPTION>
			<OPTION VALUE="食品">食品</OPTION>
			<OPTION VALUE="雑貨">雑貨</OPTION>
			<OPTION VALUE="電気・ガス代">電気・ガス代</OPTION>
			<OPTION VALUE="水道代">水道代</OPTION>
			<OPTION VALUE="通信料金">通信料金</OPTION>
		</SELECT>
		商品名: 
		<input type="text" name="reg-prod">
		金額: 
		<input type="text" name="reg-price">
		比率： 
		<SELECT NAME="reg-rate">
			<OPTION VALUE="" selected></OPTION>
			<OPTION VALUE="5:5">5:5</OPTION>
			<OPTION VALUE="3:7">3:7</OPTION>
			<OPTION VALUE="7:3">7:3</OPTION>
		</SELECT> 
		<br>
		<br>
		<input type="hidden" name="mode" value="add"> 
		<input name = "btn" type="submit" value="登録">
	</form>
	<br>
	<div id="table_wrap" class="table-responsive">
		<table id="expenses"></table>
	</div>
</body>
</html>