package controller.administration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
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

public class ManageData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Gson gson = new GsonBuilder().serializeNulls().create();
		String result = null;

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

			ArrayList<Category> categories = DBManager.getInstance().getLeafCategoriesContainsString(string);

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
		case "generateGraph": {
			StringBuffer stringBuffer = new StringBuffer();
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(req.getInputStream(), StandardCharsets.UTF_8));
			String line = bufferedReader.readLine();
			while (line != null) {
				stringBuffer.append(line);
				line = bufferedReader.readLine();
			}

			String type = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("type").getAsString();
			boolean allSupermarkets = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("allSupermarkets")
					.getAsBoolean();
			boolean allCategories = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("allCategories")
					.getAsBoolean();
			boolean allProducts = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("allProducts")
					.getAsBoolean();
			String supermarketId = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("supermarketId")
					.getAsString();
			String categoryId = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("categoryId")
					.getAsString();
			String productId = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("productId").getAsString();
			String dataType = gson.fromJson(stringBuffer.toString(), JsonObject.class).get("dataType").getAsString();

			switch (type) {
			case "supermarket": {
				if (allSupermarkets) {
					HashMap<String, Long> supermarkets = DBManager.getInstance().retrieveAllSupermarketsData(dataType);

					if (supermarkets.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						Set<Map.Entry<String, Long>> entry = supermarkets.entrySet();
						for (Map.Entry<String, Long> temp : entry) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				} else {
					HashMap<String, Long> years = DBManager.getInstance()
							.retrieveYearlySupermarketData(Long.parseLong(supermarketId), dataType);

					if (years.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						TreeSet<Map.Entry<String, Long>> set = new TreeSet<Map.Entry<String, Long>>(
								new Comparator<Map.Entry<String, Long>>() {

									@Override
									public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
										return o1.getKey().compareTo(o2.getKey());
									}
								});
						set.addAll(years.entrySet());
						for (Map.Entry<String, Long> temp : set) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				}
			}
				break;
			case "category": {
				if (allCategories) {
					HashMap<String, Long> categories = DBManager.getInstance().retrieveAllCategoriesData(dataType);

					if (categories.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						Set<Map.Entry<String, Long>> entry = categories.entrySet();
						for (Map.Entry<String, Long> temp : entry) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				} else {
					HashMap<String, Long> years = DBManager.getInstance()
							.retrieveYearlyCategoryData(Long.parseLong(categoryId), dataType);

					if (years.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						TreeSet<Map.Entry<String, Long>> set = new TreeSet<Map.Entry<String, Long>>(
								new Comparator<Map.Entry<String, Long>>() {

									@Override
									public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
										return o1.getKey().compareTo(o2.getKey());
									}
								});
						set.addAll(years.entrySet());
						for (Map.Entry<String, Long> temp : set) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				}
			}
				break;
			case "product": {
				if (allProducts) {
					HashMap<String, Long> products = DBManager.getInstance().retrieveAllProductsData(dataType);

					if (products.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						Set<Map.Entry<String, Long>> entry = products.entrySet();
						for (Map.Entry<String, Long> temp : entry) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				} else {
					HashMap<String, Long> years = DBManager.getInstance()
							.retrieveYearlyProductData(Long.parseLong(productId), dataType);

					if (years.isEmpty()) {
						result = "[]";
					} else {
						StringBuilder tempResult = new StringBuilder();
						tempResult.append("[");
						TreeSet<Map.Entry<String, Long>> set = new TreeSet<Map.Entry<String, Long>>(
								new Comparator<Map.Entry<String, Long>>() {

									@Override
									public int compare(Entry<String, Long> o1, Entry<String, Long> o2) {
										return o1.getKey().compareTo(o2.getKey());
									}
								});
						set.addAll(years.entrySet());
						for (Map.Entry<String, Long> temp : set) {
							tempResult.append(
									"{\"label\" : \"" + temp.getKey() + "\", \"y\" : " + temp.getValue() + "},");
						}
						tempResult.deleteCharAt(tempResult.length() - 1);
						tempResult.append("]");
						result = tempResult.toString();
					}
				}
			}
				break;
			}
		}
			break;
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
