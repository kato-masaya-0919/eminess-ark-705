package dev.java;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;


public class ExpensesBeans {

	private String id;
	private String date;
	private String dish;
	private String categ;
	private String prod;
	private String price;
	private String rate;

	// DB関連の初期設定
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	// コンストラクタ
	public ExpensesBeans(HttpServletRequest request) {
		
		//setId(request.getParameter("id"));
		setDate(request.getParameter("date"));
		setDish(request.getParameter("dish"));
		setCateg(request.getParameter("categ"));
		setProd(request.getParameter("prod"));
		setPrice(request.getParameter("price"));
		setRate(request.getParameter("rate"));
	}

	// データベースへのアクション
	private void doDataBase(String sql) throws Exception {
		
		// JDBC Driver の登録
		Class.forName("org.postgresql.Driver");
		 
		//
		URI dbUri = new URI(System.getenv("DATABASE_URL"));

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
		
		conn = DriverManager.getConnection(dbUrl, username, password);
		
		// sql文を表示
		System.out.println(sql);
		pstmt = conn.prepareStatement(sql);
		// sql文実行
		pstmt.execute();

		// 使用したオブジェクトを終了させる
		pstmt.close();
		conn.close();
	}

	// getter setter
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDish() {
		return dish;
	}

	public void setDish(String dish) {
		this.dish = dish;
	}

	public String getCateg() {
		return categ;
	}

	public void setCateg(String categ) {
		this.categ = categ;
	}

	public String getProd() {
		return prod;
	}

	public void setProd(String prod) {
		this.prod = prod;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}
	// 追加
	public Boolean addData() {
		try {

			// sql文 の作成
			String sql = "insert into expenses(date, dish, categ, prod, price rate) values ('" + date + "','" + dish + "','"
					+ categ + "','" + prod + "','" + price + "','" + prod + "')";

			// データベース接続＆ｓｑｌの実行
			doDataBase(sql);

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//削除
	public Boolean deleteData() {
		try {

			// sql文 の作成
			String sql = "delete from expenses where id=" + id;

			// データベース接続＆ｓｑｌの実行
			doDataBase(sql);

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
