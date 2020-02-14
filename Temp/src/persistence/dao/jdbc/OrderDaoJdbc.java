package persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.CurrentState;
import model.Order;
import model.Product;
import persistence.DataSource;
import persistence.dao.OrderDao;

public class OrderDaoJdbc implements OrderDao {

	private DataSource dataSource;
	private final String sequenceName = "orders_sequence";
	private final String associationSequenceName = "order_contains_product_sequence";

	public OrderDaoJdbc(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void insert(Order order) {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			Long orderId = IdBroker.getId(connection, sequenceName);
			order.setId(orderId);
			String insert = "insert into orders(id, total_price, customer, delivery_address, payment_method, current_state) values (?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, order.getId());
			statement.setDouble(2, order.getTotalPrice());
			statement.setLong(3, order.getCustomer().getId());
			statement.setLong(4, order.getDeliveryAddress().getId());
			statement.setLong(5, order.getPaymentMethod().getId());
			statement.setString(6, order.getCurrentState().toString());
			System.out.println("Prima di update");
			statement.executeUpdate();
			System.out.println("Dopo update");

			for (Map.Entry<Product, Long> entry : order.getProducts().entrySet()) {
				String associationInsert = "insert into order_contains_product(id, orders, product, amount) values (?, ?, ?, ?)";
				long associationId = IdBroker.getId(connection, associationSequenceName);
				PreparedStatement associationStatement = connection.prepareStatement(associationInsert);
				associationStatement.setLong(1, associationId);
				associationStatement.setLong(2, order.getId());
				associationStatement.setLong(3, entry.getKey().getId());
				associationStatement.setLong(4, entry.getValue());
				associationStatement.executeUpdate();
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
	}

	@Override
	public ArrayList<Order> retrieveAll() {
		Connection connection = null;
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from orders";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				HashMap<Product, Long> products = new HashMap<Product, Long>();
				Order order = new Order(resultSet.getLong("id"), resultSet.getDouble("total_price"),
						new CustomerDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("customer")),
						new DeliveryAddressDaoJdbc(dataSource)
								.retrieveByPrimaryKey(resultSet.getLong("delivery_address")),
						new PaymentMethodDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("payment_method")),
						CurrentState.valueOf(resultSet.getString("current_state")), products);
				// retrieve all products
				String productsQuery = "select product, amount from order_contains_product where orders=?";
				PreparedStatement productsStatement = connection.prepareStatement(productsQuery);
				productsStatement.setLong(1, order.getId());
				ResultSet productsResultSet = productsStatement.executeQuery();
				while (productsResultSet.next()) {
					products.put(new ProductDaoJdbc(dataSource).retrieveByPrimaryKey(productsResultSet.getLong("product")),
							productsResultSet.getLong("amount"));
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
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return orders;
	}

	@Override
	public Order retrieveByPrimaryKey(Long id) {
		Connection connection = null;
		Order order = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from orders where id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				HashMap<Product, Long> products = new HashMap<Product, Long>();
				order = new Order(resultSet.getLong("id"), resultSet.getDouble("total_price"),
						new CustomerDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("customer")),
						new DeliveryAddressDaoJdbc(dataSource)
								.retrieveByPrimaryKey(resultSet.getLong("delivery_address")),
						new PaymentMethodDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("payment_method")),
						CurrentState.valueOf(resultSet.getString("current_state")), products);
				// retrieve all products
				String productsQuery = "select product, amount from order_contains_product where order=?";
				PreparedStatement productsStatement = connection.prepareStatement(productsQuery);
				productsStatement.setLong(1, order.getId());
				ResultSet productsResultSet = statement.executeQuery();
				while (productsResultSet.next()) {
					products.put(new ProductDaoJdbc(dataSource).retrieveByPrimaryKey(productsResultSet.getLong("id")),
							productsResultSet.getLong("amount"));
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
		} finally {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				throw new RuntimeException(e.getMessage());
//			}
		}
		return order;
	}

	@Override
	public void update(Order order) {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String update = "update orders set total_price=?, customer=?, delivery_address=?, payment_method=?, current_state=? where id=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setDouble(1, order.getTotalPrice());
			statement.setLong(2, order.getCustomer().getId());
			statement.setLong(3, order.getDeliveryAddress().getId());
			statement.setLong(4, order.getPaymentMethod().getId());
			statement.setString(5, order.getCurrentState().toString());
			statement.setLong(6, order.getId());
			statement.executeUpdate();
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
	}

	@Override
	public void delete(Order order) {
	}

	@Override
	public ArrayList<Order> getOrdersOfCustomer(long customerId) {
		Connection connection = null;
		ArrayList<Order> orders = new ArrayList<Order>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from orders where customer=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, customerId);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				HashMap<Product, Long> products = new HashMap<Product, Long>();
				Order order = new Order(resultSet.getLong("id"), resultSet.getDouble("total_price"),
						new CustomerDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("customer")),
						new DeliveryAddressDaoJdbc(dataSource)
								.retrieveByPrimaryKey(resultSet.getLong("delivery_address")),
						new PaymentMethodDaoJdbc(dataSource).retrieveByPrimaryKey(resultSet.getLong("payment_method")),
						CurrentState.valueOf(resultSet.getString("current_state")), products);

//				// retrieve all products
//				String productsQuery = "select * from order_contains_product where orders=?";
//				PreparedStatement productsStatement = connection.prepareStatement(productsQuery);
//				productsStatement.setLong(1, order.getId());
//				ResultSet productsResultSet = statement.executeQuery();
//				while (productsResultSet.next()) {
//					products.put(new ProductDaoJdbc(dataSource).retrieveByPrimaryKey(productsResultSet.getLong("id")),
//							productsResultSet.getLong("amount"));
//				}
				orders.add(order);
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
		return orders;
	}

}
