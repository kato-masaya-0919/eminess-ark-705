package dev.java;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 文字コードの設定
		request.setCharacterEncoding("UTF-8");

		// modeの取得
		String mode = request.getParameter("mode");

		// 実行ステータスの宣言
		String status ="成功しました";

		// JavaBeansの初期化
		ExpensesBeans expenses = new ExpensesBeans(request);

		switch (mode) {

		case "add": // 登録
			if (expenses.addData() == false) {
				status ="失敗しました";
			}
			break;

		case "delete": // 削除
			if (expenses.deleteData() == false) {
				status ="失敗しました";
			}
			break;

		case "change": // 変更
			request.setAttribute("shain", expenses);
			request.getRequestDispatcher("/change.jsp").forward(request, response);
			return;

		case "del_add": // 変更確定
			if (!(expenses.deleteData() && expenses.addData())) {
				status ="失敗しました";
			}
			break;
		}

		// statusをセットして、result.jspに転送
		request.setAttribute("status", status);		
		request.getRequestDispatcher("/result.jsp").forward(request, response);

	}

	// doGetの追加
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}