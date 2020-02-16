package controller.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import model.Category;
import model.Product;
import model.SuperMarket;
import persistence.DBManager;

public class ViewOrder extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (req.getSession().getAttribute("customer") == null) {
			resp.sendError(401);
			return;
		}

		Gson gson = new GsonBuilder().serializeNulls().create();
		String result = null;

		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
		String line = bufferedReader.readLine();
		while (line != null) {
			stringBuffer.append(line);
			line = bufferedReader.readLine();
		}
		long id = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("order").getAsLong();

		ArrayList<Product> products = DBManager.getInstance().getProductsOfOrder(id);

		if (products.isEmpty()) {
			result = "[]";
		} else {
			StringBuilder tempResult = new StringBuilder();
			tempResult.append("[");
			for (Product temp : products) {
				tempResult.append("{\"img\" : \"" + temp.getImagePath() + "\", \"name\" : \"" + temp.getName() + " "
						+ temp.getBrand() + "\", \"supermarket\" : \"" + temp.getSuperMarket().getName() + ", "
						+ temp.getSuperMarket().getCity() + "\"},");
			}
			tempResult.deleteCharAt(tempResult.length() - 1);
			tempResult.append("]");
			result = tempResult.toString();
		}

		PrintWriter writer = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		writer.println(result);
		writer.flush();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
