package persistence.dao;

import java.util.ArrayList;
import java.util.HashMap;

import exceptions.DBOperationException;
import model.Product;

public interface ProductDao {

	// CREATE
	public void insert(Product product) throws DBOperationException;

	// RETRIEVE
	public ArrayList<Product> retrieveAll();

	public Product retrieveByPrimaryKey(Long id);

	public ArrayList<Product> retrieveByName(String name);

	public ArrayList<Product> retrieveByCategory(long category);

	public Long retrieveAvailableQuantity(Long productId);

	public ArrayList<Product> retrieveNotDeletedProducts();

	// UPDATE
	public void update(Product product) throws DBOperationException;

	// DELETE
	public void delete(Product product);

	public void deleteProduct(long id) throws DBOperationException;

	public void decreaseQuantity(Long product, long quantity) throws DBOperationException;

	public void increaseQuantity(Long product, long l);

	public ArrayList<Product> retrieveDiscountedProducts();

	public ArrayList<Product> retrieveByCategoryAndWeight(String categoryName, boolean offBrand, long weight);

	public HashMap<String, Long> getCashForAllProducts();

	public HashMap<String, Long> getUnitsForAllProducts();

	public HashMap<String, Long> getYearlyCashForProduct(long id);

	public HashMap<String, Long> getYearlyUnitsForProduct(long id);

	public ArrayList<Product> retrieveProductsOfOrder(long id);

}
