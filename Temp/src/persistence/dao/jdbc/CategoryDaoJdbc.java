package persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.Category;
import model.Product;
import persistence.DataSource;
import persistence.dao.CategoryDao;

public class CategoryDaoJdbc implements CategoryDao {

	private DataSource dataSource;
	private final String sequenceName = "category_sequence";

	public CategoryDaoJdbc(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void insert(Category category) {
		// TODO In teoria non serve a nulla, in quanto non modifichiamo niente da
		// codice!
	}

	@Override
	public ArrayList<Category> retrieveAll() {
		Connection connection = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from category";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long parentId = resultSet.getLong("parent");
				if (parentId == 0) {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"), null));
				} else {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(parentId)));
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
		return categories;
	}

	@Override
	public Category retrieveByPrimaryKey(Long id) {
		Connection connection = null;
		Category category = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from category where id=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				long parentId = resultSet.getLong("parent");
				if (parentId == 0) {
					category = new Category(resultSet.getLong("id"), resultSet.getString("name"), null);
				} else {
					category = new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(parentId));
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
		return category;
	}

	@Override
	public ArrayList<Category> retrieveMacroCategories() {
		Connection connection = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from category where parent=? or parent=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setLong(1, 1);
			statement.setLong(2, 13);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getLong("parent") == 1) {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new Category(1, "Alimentare", null)));
				} else {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new Category(13, "Non alimentare", null)));
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
		return categories;
	}

	@Override
	public void update(Category category) {
		// TODO In teoria non serve a nulla, in quanto non modifichiamo niente da
		// codice!
	}

	@Override
	public void delete(Category category) {
		// TODO In teoria non serve a nulla, in quanto non modifichiamo niente da
		// codice!
	}

	@Override
	public ArrayList<Category> retrieveLeafCategories() {
		Connection connection = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from category as C1 where not exists (select * from category as C2 where C2.parent = C1.id)";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long parentId = resultSet.getLong("parent");
				if (parentId == 0) {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"), null));
				} else {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(parentId)));
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
		return categories;

	}

	@Override
	public ArrayList<Category> retrieveLeafCategoriesForDiet() {
		Connection connection = null;
		ArrayList<Category> categories = new ArrayList<Category>();
		try {
			connection = this.dataSource.getConnection();
			String query = "select * from category as C1 where not exists (select * from category as C2 where C2.parent = C1.id) and exists(select * from category as C3,category as C2 where C2.id=C1.parent and C2.parent=C3.id and C3.name='Alimentare')";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long parentId = resultSet.getLong("parent");
				if (parentId == 0) {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"), null));
				} else {
					categories.add(new Category(resultSet.getLong("id"), resultSet.getString("name"),
							new CategoryDaoJdbc(dataSource).retrieveByPrimaryKey(parentId)));
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
		return categories;
	}

	@Override
	public HashMap<String, Long> getCashForAllCategories() {
		HashMap<String, Long> categories = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.name, sum(sub.cash) as tot_cash from (select c.name, p.price*o.amount as cash from order_contains_product as o, product as p, category as c where o.product=p.id and p.category=c.id) as sub group by sub.name";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categories.put(resultSet.getString("name"), resultSet.getLong("tot_cash"));
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
		return categories;
	}

	@Override
	public HashMap<String, Long> getUnitsForAllCategories() {
		HashMap<String, Long> categories = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.name, sum(sub.units) as tot_units from (select c.name, o.amount as units from order_contains_product as o, product as p, category as c where o.product=p.id and p.category=c.id) as sub group by sub.name";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				categories.put(resultSet.getString("name"), resultSet.getLong("tot_units"));
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
		return categories;
	}

	@Override
	public HashMap<String, Long> getYearlyCashForCategory(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.cash) as tot_cash from (select extract(year from o.order_date) as year, p.price*op.amount as cash from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.category=?) as sub group by sub.year";
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
	public HashMap<String, Long> getYearlyUnitsForCategory(long id) {
		HashMap<String, Long> years = new HashMap<String, Long>();
		Connection connection = null;
		try {
			connection = this.dataSource.getConnection();
			String query = "select sub.year, sum(sub.units) as tot_units from (select extract(year from o.order_date) as year, op.amount as units from orders as o, order_contains_product as op, product as p where op.orders=o.id and op.product=p.id and p.category=?) as sub group by sub.year";
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
