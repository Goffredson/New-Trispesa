package persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.DBOperationException;
import model.Product;
import persistence.DataSource;
import persistence.dao.ProductDao;

public class ProductDaoJdbc implements ProductDao {

	private DataSource dataSource;
	private final String sequenceName = "product_sequence";

	public ProductDaoJdbc(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Product product) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			// Cerca se esistono prodotti UGUALI E ATTIVI
			String query = "select * from product where barcode=? and supermarket=? and deleted=false";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setLong(1, product.getBarcode());
			selectStatement.setLong(2, product.getSuperMarket().getId());
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next())
				throw new DBOperationException("Il prodotto � gi� venduto dal supermercato", product.toString());

			// se il prodotto esiste ma � eliminato allora lo mette non eliminato
			String query2 = "select * from product where barcode=? and supermarket=? and deleted=true";
			PreparedStatement selectStatement2 = connection.prepareStatement(query2);
			selectStatement2.setLong(1, product.getBarcode());
			selectStatement2.setLong(2, product.getSuperMarket().getId());
			ResultSet resultSet2 = selectStatement2.executeQuery();
			if (resultSet2.next()) {
				String update = "update product set offbrand=?, price=?, quantity=?, discount=?, image_path=?, deleted=false where id=?";
				PreparedStatement statement = connection.prepareStatement(update);
				statement.setBoolean(1, product.isOffBrand());
				statement.setDouble(2, product.getPrice());
				statement.setLong(3, product.getQuantity());
				statement.setDouble(4, product.getDiscount());
				statement.setString(5, product.getImageId());
				statement.setLong(6, resultSet2.getLong("id"));
				statement.executeUpdate();
			} else {
				Long id = IdBroker.getId(connection, sequenceName);
				product.setId(id);
				String insert = "insert into product(id, barcode, name, brand, weight, supermarket, category, offbrand, price, quantity, discount, image_path, deleted) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = connection.prepareStatement(insert);
				statement.setLong(1, product.getId());
				statement.setLong(2, product.getBarcode());
				statement.setString(3, product.getName());
				statement.setString(4, product.getBrand());
				statement.setDouble(5, product.getWeight());
				statement.setLong(6, product.getSuperMarket().getId());
				statement.setLong(7, product.getCategory().getId());
				statement.setBoolean(8, product.isOffBrand());
				statement.setDouble(9, product.getPrice());
				statement.setLong(10, product.getQuantity());
				statement.setDouble(11, product.getDiscount());
				statement.setString(12, product.getImageId());
				statement.setBoolean(13, product.isDeleted());
				statement.executeUpdate();
			}

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public ArrayList<Product> retrieveAll() {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return products;
	}

	@Override
	public Product retrieveByPrimaryKey(Long id) {
		Connection connection = null;
		Product product = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product where id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				product = new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted"));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return product;
	}

	@Override
	public ArrayList<Product> retrieveByName(String name) {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product where name~*?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, name);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return products;
	}

	@Override
	public void update(Product product) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			String query = "select * from product where id=?";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setLong(1, product.getId());
			ResultSet resultSet = selectStatement.executeQuery();
			if (!(resultSet.next()))
				throw new DBOperationException("Il prodotto non � stato trovato", product.getId() + "");

			String update = "update product set barcode=?, name=?, brand=?, weight=?, supermarket=?, category=?, offbrand=?, price=?, quantity=?, discount=?, image_path=?, deleted=? where id=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setLong(1, product.getBarcode());
			statement.setString(2, product.getName());
			statement.setString(3, product.getBrand());
			statement.setDouble(4, product.getWeight());
			statement.setLong(5, product.getSuperMarket().getId());
			statement.setLong(6, product.getCategory().getId());
			statement.setBoolean(7, product.isOffBrand());
			statement.setDouble(8, product.getPrice());
			statement.setLong(9, product.getQuantity());
			statement.setDouble(10, product.getDiscount());
			statement.setString(11, product.getImageId());
			statement.setBoolean(12, product.isDeleted());
			statement.setLong(13, product.getId());
			statement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public void delete(Product product) {
		// TODO In teoria non serve a nulla, in quanto non modifichiamo niente da
		// codice!
	}

	@Override
	public ArrayList<Product> retrieveByCategory(long categoryId) {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product as prod where prod.category=? or exists (select * from category where parent=? and prod.category=id)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, categoryId);
			statement.setLong(2, categoryId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return products;
	}

	@Override
	public Long retrieveAvailableQuantity(Long productId) {
		Connection connection = null;
		long availableQuantity = 0;
		try {
			connection = this.dataSource.getConnection();
			String query = "select quantity from product where id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, productId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				availableQuantity = resultSet.getLong("quantity");
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return availableQuantity;
	}

	@Override
	public ArrayList<Product> retrieveNotDeletedProducts() {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product where deleted = false";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return products;
	}

	@Override
	public void deleteProduct(long id) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			String query = "select * from product where id=?";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setLong(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!(resultSet.next()))
				throw new DBOperationException("Il prodotto non � stato trovato", id + "");

			String update = "update product set deleted=true where id=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setLong(1, id);
			statement.executeUpdate();

			connection.commit();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
	}

	@Override
	public void decreaseQuantity(Long product, long quantity) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			PreparedStatement pstmt = connection.prepareStatement("call decrease_product_quantity(?,?)");
			pstmt.setLong(1, product);
			pstmt.setLong(2, quantity);
			pstmt.execute();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new DBOperationException("Il seguente prodotto è terminato: ",
							retrieveByPrimaryKey(product).getName());
				}
			}
		}
	}

	@Override
	public void increaseQuantity(Long product, long l) {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String update = "update product set quantity=quantity+? where id=?";
			PreparedStatement pstmt = connection.prepareStatement(update);
			pstmt.setLong(1, l);
			pstmt.setLong(2, product);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}

	}

	@Override
	public ArrayList<Product> retrieveDiscountedProducts() {
		Connection connection = null;
		ArrayList<Product> prodotti = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product where discount>0";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				prodotti.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return prodotti;
	}

	@Override
	public ArrayList<Product> retrieveByCategoryAndWeight(String categoryName, boolean offBrand, long weight) {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * " + "from product as prod, category as c "
					+ "where prod.category=c.id and c.name=? and prod.quantity > 0 and prod.weight>=? and prod.offbrand=? or exists "
					+ "(select * from category as cat where cat.name=? and cat.parent=prod.category and prod.weight>=? and prod.offbrand=? and prod.quantity > 0)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, categoryName);
			statement.setLong(2, weight);
			statement.setBoolean(3, offBrand);
			statement.setString(4, categoryName);
			statement.setLong(5, weight);
			statement.setBoolean(6, offBrand);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return products;
	}

	@Override
	public HashMap<String, Long> getCashForAllProducts() {
		HashMap<String, Long> products = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.id, sum(sub.cash) as tot_cash from (select p.id, p.price*o.amount as cash from order_contains_product as o, product as p where o.product=p.id) as sub group by sub.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String query2 = "select concat(name, ' ', brand) as string from product where id=?";
				PreparedStatement statement2 = connection.prepareStatement(query2);
				statement2.setLong(1, resultSet.getLong("id"));
				ResultSet resultSet2 = statement2.executeQuery();
				if (resultSet2.next()) {
					products.put(resultSet2.getString("string"), resultSet.getLong("tot_cash"));
				}
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return products;
	}

	@Override
	public HashMap<String, Long> getUnitsForAllProducts() {
		HashMap<String, Long> products = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.id, sum(sub.units) as tot_units from (select p.id, o.amount as units from order_contains_product as o, product as p where o.product=p.id) as sub group by sub.id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String query2 = "select concat(name, ' ', brand) as string from product where id=?";
				PreparedStatement statement2 = connection.prepareStatement(query2);
				statement2.setLong(1, resultSet.getLong("id"));
				ResultSet resultSet2 = statement2.executeQuery();
				if (resultSet2.next()) {
					products.put(resultSet2.getString("string"), resultSet.getLong("tot_units"));
				}
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return products;
	}

	@Override
	public HashMap<String, Long> getYearlyCashForProduct(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.cash) as tot_cash from (select extract(year from o.order_date) as year, p.price*op.amount as cash from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.id=?) as sub group by sub.year";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				years.put(resultSet.getString("year"), resultSet.getLong("tot_cash"));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return years;
	}

	@Override
	public HashMap<String, Long> getYearlyUnitsForProduct(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.units) as tot_units from (select extract(year from o.order_date) as year, op.amount as units from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.id=?) as sub group by sub.year";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				years.put(resultSet.getString("year"), resultSet.getLong("tot_units"));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return years;
	}

	@Override
	public ArrayList<Product> retrieveProductsOfOrder(long id) {
		Connection connection = null;
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from product as p, order_contains_product as o where p.id=o.product and o.orders=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				products.add(new Product(resultSet.getLong("id"), resultSet.getLong("barcode"),
						resultSet.getString("name"), resultSet.getString("brand"), resultSet.getDouble("weight"),
						new SuperMarketDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("supermarket")),
						new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("category")),
						resultSet.getBoolean("offbrand"), resultSet.getDouble("price"), resultSet.getLong("quantity"),
						resultSet.getDouble("discount"), resultSet.getString("image_path"),
						resultSet.getBoolean("deleted")));
			}
		} catch (SQLException e) {
			if (connection != null) {
				try {
					connection.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return products;
	}

}
