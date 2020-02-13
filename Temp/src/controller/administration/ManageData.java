package controller.administration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import exceptions.DBOperationException;
import model.Category;
import model.OperationResult;
import model.Product;
import model.SuperMarket;
import model.Product;
import persistence.DBManager;

public class ManageData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String result = null;

		try {
			switch (req.getParameter("action")) {
			case "retrieveSupermarkets": {
				String string = req.getParameter("term");

				ArrayList<SuperMarket> superMarkets = DBManager.getInstance().getSuperMarketsContainsString(string);

				if (superMarkets.isEmpty()) {
					result = "[]";
				} else {
					StringBuilder tempResult = new StringBuilder();
					tempResult.append("[");
					for (SuperMarket temp : superMarkets) {
						tempResult.append("{\"id\" : \"" + temp.getId() + "\", \"label\" : \"" + temp.getAddress()
								+ "\", \"value\" : \"" + temp.getAddress() + "\"},");
					}
					tempResult.deleteCharAt(tempResult.length() - 1);
					tempResult.append("]");
					result = tempResult.toString();
				}
			}
				break;

			case "retrieveCategories": {
				String string = req.getParameter("term");

				ArrayList<Category> categories = DBManager.getInstance().getCategoriesContainsString(string);

				if (categories.isEmpty()) {
					result = "[]";
				} else {
					StringBuilder tempResult = new StringBuilder();
					tempResult.append("[");
					for (Category temp : categories) {
						tempResult.append("{\"id\" : \"" + temp.getId() + "\", \"label\" : \"" + temp.getName()
								+ "\", \"value\" : \"" + temp.getName() + "\"},");
					}
					tempResult.deleteCharAt(tempResult.length() - 1);
					tempResult.append("]");
					result = tempResult.toString();
				}
			}
				break;
			case "retrieveProducts": {
				String string = req.getParameter("term");

				ArrayList<Product> products = DBManager.getInstance().getProductsContainsString(string);

				if (products.isEmpty()) {
					result = "[]";
				} else {
					StringBuilder tempResult = new StringBuilder();
					tempResult.append("[");
					for (Product temp : products) {
						tempResult.append("{\"id\" : \"" + temp.getId() + "\", \"label\" : \"" + temp.toString()
								+ "\", \"value\" : \"" + temp.toString() + "\"},");
					}
					tempResult.deleteCharAt(tempResult.length() - 1);
					tempResult.append("]");
					result = tempResult.toString();
				}
			}
				break;
			}
		} finally {
			PrintWriter writer = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			writer.println(result);
			writer.flush();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
