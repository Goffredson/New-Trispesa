package persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.DBOperationException;
import model.SuperMarket;
import persistence.DataSource;
import persistence.dao.SuperMarketDao;

public class SuperMarketDaoJdbc implements SuperMarketDao {

	private DataSource dataSource;
	private final String sequenceName = "supermarket_sequence";

	public SuperMarketDaoJdbc(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(SuperMarket supermarket) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			String query = "select * from supermarket where name=? and country=? and city=? and address=?";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setString(1, supermarket.getName());
			selectStatement.setString(2, supermarket.getCountry());
			selectStatement.setString(3, supermarket.getCity());
			selectStatement.setString(4, supermarket.getAddress());
			ResultSet resultSet = selectStatement.executeQuery();
			if (resultSet.next())
				throw new DBOperationException("Il supermercato è già presente", supermarket.toString());

			Long id = IdBroker.getId(connection, sequenceName);
			supermarket.setId(id);
			String insert = "insert into supermarket(id, name, country, city, address, latitude, longitude, affiliate) values (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(insert);
			statement.setLong(1, supermarket.getId());
			statement.setString(2, supermarket.getName());
			statement.setString(3, supermarket.getCountry());
			statement.setString(4, supermarket.getCity());
			statement.setString(5, supermarket.getAddress());
			statement.setDouble(6, supermarket.getLatitude());
			statement.setDouble(7, supermarket.getLongitude());
			statement.setBoolean(8, supermarket.isAffiliate());
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
	public ArrayList<SuperMarket> retrieveAll() {
		Connection connection = null;
		ArrayList<SuperMarket> supermarkets = new ArrayList<SuperMarket>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from supermarket";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				supermarkets.add(new SuperMarket(resultSet.getLong("id"), resultSet.getString("name"),
						resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("address"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
						resultSet.getBoolean("affiliate")));
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
		return supermarkets;
	}

	@Override
	public SuperMarket retrieveByPrimaryKey(Long id) {
		Connection connection = null;
		SuperMarket supermarket = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from supermarket where id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				supermarket = new SuperMarket(resultSet.getLong("id"), resultSet.getString("name"),
						resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("address"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
						resultSet.getBoolean("affiliate"));
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
		return supermarket;
	}

	@Override
	public void update(SuperMarket supermarket) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			String query = "select * from supermarket where id=?";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setLong(1, supermarket.getId());
			ResultSet resultSet = selectStatement.executeQuery();
			if (!(resultSet.next()))
				throw new DBOperationException("Il supermercato non è stato trovato", supermarket.getId() + "");

			String update = "update supermarket set name=?, country=?, city=?, address=?, latitude=?, longitude=?, affiliate=? where id=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setString(1, supermarket.getName());
			statement.setString(2, supermarket.getCountry());
			statement.setString(3, supermarket.getCity());
			statement.setString(4, supermarket.getAddress());
			statement.setDouble(5, supermarket.getLatitude());
			statement.setDouble(6, supermarket.getLongitude());
			statement.setBoolean(7, supermarket.isAffiliate());
			statement.setLong(8, supermarket.getId());
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
	public void delete(SuperMarket supermarket) {
		// TODO In teoria non serve a nulla, in quanto non modifichiamo niente da
		// codice!
	}

	@Override
	public void setAffiliate(long id, boolean affiliation) throws DBOperationException {
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			connection.setAutoCommit(false);

			String query = "select * from supermarket where id=?";
			PreparedStatement selectStatement = connection.prepareStatement(query);
			selectStatement.setLong(1, id);
			ResultSet resultSet = selectStatement.executeQuery();
			if (!(resultSet.next()))
				throw new DBOperationException("Il supermercato non è stato trovato", id + "");

			String update = "update supermarket set affiliate=? where id=?";
			PreparedStatement statement = connection.prepareStatement(update);
			statement.setBoolean(1, affiliation);
			statement.setLong(2, id);
			statement.executeUpdate();

			if (affiliation == false) {
				String updateProduct = "update product set deleted=true where supermarket=?";
				PreparedStatement updateStatement = connection.prepareStatement(updateProduct);
				updateStatement.setLong(1, id);
				System.out.println(updateStatement.executeUpdate());
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
	public ArrayList<SuperMarket> retrieveAffiliate() {
		Connection connection = null;
		ArrayList<SuperMarket> supermarkets = new ArrayList<SuperMarket>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from supermarket where affiliate=true";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				supermarkets.add(new SuperMarket(resultSet.getLong("id"), resultSet.getString("name"),
						resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("address"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
						resultSet.getBoolean("affiliate")));
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
		return supermarkets;
	}

	@Override
	public ArrayList<SuperMarket> retrieveAffiliateDontSellProduct(long barcode) {
		Connection connection = null;
		ArrayList<SuperMarket> supermarkets = new ArrayList<SuperMarket>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from supermarket as s where affiliate=true and not exists(select * from product where barcode=? and supermarket=s.id and deleted=false)";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, barcode);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				supermarkets.add(new SuperMarket(resultSet.getLong("id"), resultSet.getString("name"),
						resultSet.getString("country"), resultSet.getString("city"), resultSet.getString("address"),
						resultSet.getDouble("latitude"), resultSet.getDouble("longitude"),
						resultSet.getBoolean("affiliate")));
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
		return supermarkets;
	}

	@Override
	public HashMap<String, Long> getCashForAllSupermarkets() {
		HashMap<String, Long> supermarkets = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.super_id as id, sum(sub.cash) as tot_cash from (select s.id as super_id, o.amount*p.price as cash from supermarket as s, product as p, order_contains_product as o where o.product=p.id and p.supermarket=s.id) as sub group by sub.super_id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String query2 = "select concat(name, ', ', city) as string from supermarket where id=?";
				PreparedStatement statement2 = connection.prepareStatement(query2);
				statement2.setLong(1, resultSet.getLong("id"));
				ResultSet resultSet2 = statement2.executeQuery();
				if (resultSet2.next()) {
					supermarkets.put(resultSet2.getString("string"), resultSet.getLong("tot_cash"));
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
		return supermarkets;
	}

	@Override
	public HashMap<String, Long> getUnitsForAllSupermarkets() {
		HashMap<String, Long> supermarkets = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.super_id as id, sum(sub.units) as tot_units from (select s.id as super_id, o.amount as units from supermarket as s, product as p, order_contains_product as o where o.product=p.id and p.supermarket=s.id) as sub group by sub.super_id";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String query2 = "select concat(name, ', ', city) as string from supermarket where id=?";
				PreparedStatement statement2 = connection.prepareStatement(query2);
				statement2.setLong(1, resultSet.getLong("id"));
				ResultSet resultSet2 = statement2.executeQuery();
				if (resultSet2.next()) {
					supermarkets.put(resultSet2.getString("string"), resultSet.getLong("tot_units"));
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
		return supermarkets;
	}

	@Override
	public HashMap<String, Long> getYearlyCashForSupermarket(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.cash) as tot_cash from (select extract(year from o.order_date) as year, p.price*op.amount as cash from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.supermarket=?) as sub group by sub.year";
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
	public HashMap<String, Long> getYearlyUnitsForSupermarket(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.units) as tot_units from (select extract(year from o.order_date) as year, op.amount as units from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.supermarket=?) as sub group by sub.year";
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

}
