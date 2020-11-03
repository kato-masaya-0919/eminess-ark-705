package dev.java;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 初期化処理
	public void init() throws ServletException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// DB関連の初期設定
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		// 文字コードの設定
		request.setCharacterEncoding("UTF-8");

		String id = request.getParameter("id");	// index.htmlで入力したidの取得
		String datefrom = request.getParameter("datefrom");	// index.htmlで入力したdatefromの取得
		String dateto = request.getParameter("dateto");	// index.htmlで入力したdatetoの取得
		String yobi = request.getParameter("yobi");	// index.htmlで入力したyobiの取得
		String use = request.getParameter("use");	// index.htmlで入力したuseの取得
		String categ = request.getParameter("categ");	// index.htmlで入力したcategの取得
		String name = request.getParameter("name");	// index.htmlで入力したnameの取得
		String price = request.getParameter("price");	// index.htmlで入力したpriceの取得
		String rate = request.getParameter("rate");	// index.htmlで入力したrateの取得
		String paystatus = request.getParameter("paystatus");	// index.htmlで入力したpaystatusの取得
		String remarks = request.getParameter("remarks");	// index.htmlで入力したremarksの取得

		try {
			// JDBC Driver の登録
			Class.forName("org.postgresql.Driver");
			 
			//
			URI dbUri = new URI(System.getenv("DATABASE_URL"));

			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath();
			
			conn = DriverManager.getConnection(dbUrl, username, password);
			
			//宣言
			StringBuffer sql;
			Map<String, String> jsonMap;
			StringBuffer jsonListArrayStr;
			StringBuffer jsonSumArrayStr;
	        String jsonStr;
	        ObjectMapper mapper;	//マッパ(JSON <-> Map, List)
			
			//一覧取得
			
			//初期化
			pstmt = null;
			rset = null;
			sql = new StringBuffer();
	        jsonMap = new LinkedHashMap<String, String>();
	        jsonListArrayStr = new StringBuffer();
	        jsonStr ="";
	        mapper = new ObjectMapper();
			
			// sql文作成
			sql.append("select "
						+ "id As id, "
						+ "date As date, "
						+ "(array['日','月','火','水','木','金','土'])[EXTRACT(DOW FROM CAST(date AS DATE)) + 1] AS yobi, "
						+ "use As use, "
						+ "categ As categ, "
						+ "name As name, "
						+ "to_char(price, 'fm999,999,999,999円') As price_yen, "
						+ "rate As rate, "
						+ "to_char(price*TO_NUMBER(substring(rate,1,strpos(rate,':')-1),'99')*0.1,'fm999,999,999,999円') As price_yen_kato, "
						+ "to_char(price*TO_NUMBER(substring(rate,strpos(rate,':')+1,length(rate)-strpos(rate,':')),'99')*0.1,'fm999,999,999,999円') As price_yen_tabinuki, "
						+ "paystatus As paystatus, "
						+ "remarks As remarks "
						+ "from expenses");
			
			sql.append(generateWhere(id,datefrom,dateto,yobi,use,categ,name,price,rate,paystatus,remarks));
			
			//sql実行
			System.out.println(sql);		// sql文を表示
			pstmt = conn.prepareStatement(new String(sql));		// sql文実行準備
			pstmt.execute();		// sql文実行
			rset = pstmt.executeQuery();		// 実行結果を、ResultSetクラスに代入
	        
	        //一覧テーブル用JSON作成
			while (rset.next()) {
				jsonMap.put("ID",rset.getString("id"));
				jsonMap.put("日付",rset.getString("date"));
				jsonMap.put("曜日",rset.getString("yobi"));
				jsonMap.put("使途",rset.getString("use"));
				jsonMap.put("カテゴリ",rset.getString("categ"));
				jsonMap.put("名称",rset.getString("name"));
				jsonMap.put("金額",rset.getString("price_yen"));
				jsonMap.put("比率",rset.getString("rate"));
				jsonMap.put("金額(加藤)",rset.getString("price_yen_kato"));
				jsonMap.put("金額(足袋抜)",rset.getString("price_yen_tabinuki"));
				jsonMap.put("精算",rset.getString("paystatus"));
				jsonMap.put("備考",rset.getString("remarks"));
		        mapper = new ObjectMapper();	//マッパ(JSON <-> Map, List)
		        jsonStr = mapper.writeValueAsString(jsonMap);  //json文字列
		        jsonListArrayStr.append(jsonStr+",");
			}
			jsonListArrayStr.setLength(jsonListArrayStr.length()-1);	//	最後のカンマをとる
			
			//合計取得
			
			//初期化
			pstmt = null;
			rset = null;
			sql = new StringBuffer();
	        jsonMap = new LinkedHashMap<String, String>();
	        jsonSumArrayStr = new StringBuffer();
	        jsonStr ="";
	        mapper = new ObjectMapper();
			
			// sql文作成
			sql = new StringBuffer();
	
			sql.append("select "
					+ "to_char(sum(price*TO_NUMBER(substring(rate,1,strpos(rate,':')-1),'99')*0.1),'fm999,999,999,999円') As price_sum_kato, "
					+ "to_char(sum(price*TO_NUMBER(substring(rate,strpos(rate,':')+1,length(rate)-strpos(rate,':')),'99')*0.1),'fm999,999,999,999円') As price_sum_tabinuki "
					+ "from expenses");
			
			sql.append(generateWhere(id,datefrom,dateto,yobi,use,categ,name,price,rate,paystatus,remarks));
			
			//sql実行
			System.out.println(sql);		// sql文を表示
			pstmt = conn.prepareStatement(new String(sql));		// sql文実行準備
			pstmt.execute();		// sql文実行
			rset = pstmt.executeQuery();		// 実行結果を、ResultSetクラスに代入
	        
	        //合計テーブル用JSON作成
			while (rset.next()) {
				jsonMap.put("合計金額(加藤)",rset.getString("price_sum_kato"));
				jsonMap.put("合計金額(足袋抜)",rset.getString("price_sum_tabinuki"));
		        jsonStr = mapper.writeValueAsString(jsonMap);  //json文字列
		        jsonSumArrayStr.append(jsonStr+",");
			}
			jsonSumArrayStr.setLength(jsonSumArrayStr.length()-1);	//	最後のカンマをとる			
			
			// 送信用JSON作成
			StringBuffer jsonAllArrayStr = new StringBuffer();
			jsonAllArrayStr.append("[");
			jsonAllArrayStr.append(jsonSumArrayStr);
			jsonAllArrayStr.append(",");
			jsonAllArrayStr.append(jsonListArrayStr);
			jsonAllArrayStr.append("]");
			
	        //ヘッダ設定
			response.setContentType("application/json;charset=UTF-8");   //JSON形式, UTF-8

	        //pwオブジェクト
	        PrintWriter pw = response.getWriter();
	        
	        //出力
	        pw.print(jsonAllArrayStr);
	        //クローズ
	        pw.close();

			// 使用したオブジェクトを終了させる
			rset.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();

			String status = "検索に失敗しました。管理者に連絡してください。";
			request.setAttribute("status", status);
			request.getRequestDispatcher("/result.jsp").forward(request, response);

		} finally {
			try {
				// 念のため、finallyでDBとの接続を切断しておく
				conn.close();
			} catch (Exception e) {
			}
		}

	}
	
	private static String generateWhere(String id,
										String datefrom,
										String dateto,
										String yobi,
										String use,
										String categ,
										String name,
										String price,
										String rate,
										String paystatus,
										String remarks) {
		Integer wherecnt=0;
		StringBuffer wsql =new StringBuffer();;
		
		wsql.append(" where ");
		
		// idが選択されている場合は、追加する
		if (id != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("id ='" + id + "'");
			wherecnt ++;
		}
		// datefromが選択されている場合は、追加する
		if (datefrom != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("date >='" + datefrom + "'");
			wherecnt ++;
		}
		// datetoが選択されている場合は、追加する
		if (dateto != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("date <='" + dateto + "'");
			wherecnt ++;
		}
		// yobiが選択されている場合は、追加する
		if (yobi != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("(array['日','月','火','水','木','金','土'])[EXTRACT(DOW FROM CAST(date AS DATE)) + 1] ='" + yobi + "'");
			wherecnt ++;
		}
		// useが選択されている場合は、追加する
		if (use != "") {
			if(wherecnt!=0) {wsql.append(" and");};
			wsql.append("use ='" + use + "'");
			wherecnt ++;
		}
		// categが選択されている場合は、追加する
		if (categ != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("categ ='" + categ + "'");
			wherecnt ++;
		}
		// nameが選択されている場合は、追加する
		if (name != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("name ='" + name + "'");
			wherecnt ++;
		}
		// priceが選択されている場合は、追加する
		if (price != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("price ='" + price + "'");
			wherecnt ++;
		}
		// rateが選択されている場合は、追加する
		if (rate != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("rate ='" + rate + "'");
			wherecnt ++;
		}
		// paystatusが選択されている場合は、追加する
		if (paystatus != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("paystatus ='" + paystatus + "'");
			wherecnt ++;
		}
		// remarksが選択されている場合は、追加する
		if (remarks != "") {
			if(wherecnt!=0) {wsql.append(" and ");};
			wsql.append("remarks LIKE '%" + remarks + "%'");
			wherecnt ++;
		}
		// where句を追加
		if (wherecnt != 0) {
			return new String(wsql);
			}
		else {
			return "";
		}
	}
}

