package persistence;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

import exceptions.DBOperationException;
import model.Administrator;
import model.Category;
import model.Customer;
import model.DeliveryAddress;
import model.Order;
import model.PaymentMethod;
import model.Product;
import model.SuperMarket;
import persistence.dao.AdministratorDao;
import persistence.dao.CategoryDao;
import persistence.dao.CustomerDao;
import persistence.dao.DeliveryAddressDao;
import persistence.dao.OrderDao;
import persistence.dao.PaymentMethodDao;
import persistence.dao.ProductDao;
import persistence.dao.SuperMarketDao;
import persistence.dao.jdbc.AdministratorDaoJdbc;
import persistence.dao.jdbc.CategoryDaoJdbc;
import persistence.dao.jdbc.CustomerDaoJdbc;
import persistence.dao.jdbc.DeliveryAddressDaoJdbc;
import persistence.dao.jdbc.OrderDaoJdbc;
import persistence.dao.jdbc.PaymentMethodDaoJdbc;
import persistence.dao.jdbc.ProductDaoJdbc;
import persistence.dao.jdbc.SuperMarketDaoJdbc;

public class DBManager {

	private static DBManager istance = null;
	private static DataSource dataSource = null;

	public static DBManager getInstance() {
		if (istance == null)
			istance = new DBManager();
		return istance;
	}

	private DBManager() {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource = new DataSource("jdbc:postgresql://rogue.db.elephantsql.com:5432/zqnyocaq", "zqnyocaq",
					"DJ8nD9eyeT4VjZAvTnAvUDcc-ExoZTN_");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public AdministratorDao getAdministratorDao() {
		return new AdministratorDaoJdbc(dataSource);
	}

	public CategoryDao getCategoryDao() {
		return new CategoryDaoJdbc(dataSource);
	}

	public CustomerDao getCustomerDao() {
		return new CustomerDaoJdbc(dataSource);
	}

	public DeliveryAddressDao getDeliveryAddressDao() {
		return new DeliveryAddressDaoJdbc(dataSource);
	}

	public OrderDao getOrderDao() {
		return new OrderDaoJdbc(dataSource);
	}

	public PaymentMethodDao getPaymentMethodDao() {
		return new PaymentMethodDaoJdbc(dataSource);
	}

	public ProductDao getProductDao() {
		return new ProductDaoJdbc(dataSource);
	}

	public SuperMarketDao getSuperMarketDao() {
		return new SuperMarketDaoJdbc(dataSource);
	}

	public void addSupermarket(SuperMarket superMarket) throws DBOperationException {
		getSuperMarketDao().insert(superMarket);
	}

	public void addProduct(Product product) throws DBOperationException {
		getProductDao().insert(product);
	}

	public SuperMarket getSupermarketById(long id) throws DBOperationException {
		SuperMarket superMarket = getSuperMarketDao().retrieveByPrimaryKey(id);
		if (superMarket == null) {
			throw new DBOperationException("Il supermercato con id " + id + " non � stato trovato", id + "");
		}
		return superMarket;
	}

	public void removeProductById(long id) throws DBOperationException {
		getProductDao().deleteProduct(id);
	}

	public Product getProductById(long id) /* throws DBOperationException */ {
		Product product = getProductDao().retrieveByPrimaryKey(id);
//		if (product == null) {
//			throw new DBOperationException("Il prodotto con id " + id + " non � stato trovato", id + "");
//		}
		return product;
	}

	public void modifyProduct(Product product) throws DBOperationException {
		getProductDao().update(product);
	}

	public void removeAffiliateSuperMarketById(long id) throws DBOperationException {
		getSuperMarketDao().setAffiliate(id, false);
	}

	public void addAffiliateSuperMarketById(long id) throws DBOperationException {
		getSuperMarketDao().setAffiliate(id, true);
	}

	// TODO da vedere alfredo e ciccio (FUNZIONA)
	public void modifySuperMarket(SuperMarket superMarket) throws DBOperationException {
		getSuperMarketDao().update(superMarket);
	}

	// TODO nuova funzione nel dao che torna solo gli affiliati, alfredo
	public ArrayList<SuperMarket> getAffiliateSuperMarkets() {
		return getSuperMarketDao().retrieveAffiliate();
	}

	public ArrayList<Product> getProductsContainsBarcode(long barcode) {
		ArrayList<Product> products = new ArrayList<>();
		for (Product product : getNotDeletedProducts()) {
			if (Long.toString(product.getBarcode()).contains(Long.toString(barcode))) {
				products.add(product);
			}
		}
		return products;
	}

	public ArrayList<SuperMarket> getAffiliateSuperMarketsDontSellProduct(long barcode) {
		return getSuperMarketDao().retrieveAffiliateDontSellProduct(barcode);
	}

	public ArrayList<Product> getProductsByBarcode(long barcode) {
		ArrayList<Product> products = new ArrayList<>();
		for (Product product : getNotDeletedProducts()) {
			if (product.getBarcode() == barcode) {
				products.add(product);
			}
		}
		return products;
	}

	// TODO va fatto il metodo nel dao giorgio
//	public ArrayList<Product> getProductsByCategory(String category) {
//		ArrayList<Product> productsByCategory = new ArrayList<Product>();
//		for (Product i : products) {
//			if (i.getCategory().getName().equals(category)) {
//				productsByCategory.add(i);
//			}
//		}
//		return productsByCategory;
//	}

	public ArrayList<Customer> getCustomers() {
		return getCustomerDao().retrieveAll();
	}

	public ArrayList<Product> getProducts() {
		return getProductDao().retrieveAll();
	}

	public ArrayList<Product> getNotDeletedProducts() {
		return getProductDao().retrieveNotDeletedProducts();
	}

	public ArrayList<Category> getCategories() {
		return getCategoryDao().retrieveAll();
	}

	public ArrayList<Category> getMacroCategories() {
		return getCategoryDao().retrieveMacroCategories();
	}

	public Category getCategoryById(long id) {
		return getCategoryDao().retrieveByPrimaryKey(id);
	}

	public ArrayList<SuperMarket> getSuperMarkets() {
		return getSuperMarketDao().retrieveAll();
	}

	// TODO
	public ArrayList<Product> getProductsByName(String nomeProdotto) {
		ArrayList<Product> prodotti = getProductDao().retrieveByName(nomeProdotto);
		return prodotti;
	}

	public Customer checkIfCustomerExists(String username, String password) {
		Customer customer = getCustomerDao().checkIfExists(username, password);
		return customer;
	}

	public Administrator checkIfAdministratorExists(String username, String password) {
		Administrator administrator = getAdministratorDao().checkIfExists(username, password);
		return administrator;
	}

	public void escludiProdotti(String categoria, ArrayList<Product> prodotti) {
		ListIterator<Product> iter = prodotti.listIterator();
		while (iter.hasNext()) {
			// System.out.println("Il prodotto " + iter.next().getName() + "è di categoria
			// " + iter.next().getCategory().getName());
			if (!(iter.next().getCategory().getName().equals(categoria))) {
				iter.remove();
			}
		}
	}

	public ArrayList<Product> getProductsByCategory(long id) {
		return getProductDao().retrieveByCategory(id);
	}

	public Long getQuantityOfProduct(Long productId) {
		return getProductDao().retrieveAvailableQuantity(productId);
	}

	public boolean insertProductIntoCart(Product product, Customer loggedCustomer) {
		boolean giaPresente = loggedCustomer.addProductToCart(product, 1L);
		if (giaPresente) {
			// System.out.println("Gia presente");
			getCustomerDao().updateCartProductAmount(product.getId(), loggedCustomer.getId(), true);

		} else {
			// System.out.println("Non gia presente");
			getCustomerDao().insertProductIntoCart(product.getId(), loggedCustomer.getId(), 1);
		}
		return true;
	}

	public void removeProductFromCart(Product product, Customer loggedCustomer) {
		boolean ultimoPezzo = loggedCustomer.removeProductFromCart(product);
		if (ultimoPezzo)
			getCustomerDao().deleteProductFromCart(product.getId(), loggedCustomer.getId());
		else
			getCustomerDao().updateCartProductAmount(product.getId(), loggedCustomer.getId(), false);
	}

	public void fillCartFromAnonymous(Customer customer, HashMap<Product, Long> anonymousCart) {
		for (Map.Entry<Product, Long> p : anonymousCart.entrySet()) {
			if (customer.addProductToCart(p.getKey(), p.getValue())) {
				getCustomerDao().updateCartProductAmount(p.getKey().getId(), customer.getId(), true);
			} else {
				getCustomerDao().insertProductIntoCart(p.getKey().getId(), customer.getId(), p.getValue());
			}
			// getCustomerDao().fillCartFromAnonymous(customer, p.getKey().getId(),
			// p.getValue());
		}
	}

	public ArrayList<Category> getLeafCategories() {
		return getCategoryDao().retrieveLeafCategories();
	}

	public boolean checkPaymentData(Long paymentId, String expirationDate, Long securityCode) {
		return getPaymentMethodDao().checkPaymentData(paymentId, expirationDate, securityCode);
	}

	public void createOrder(Customer customer, String paymentId, String deliveryAddressId) {
		long totalPrice = customer.getCartTotalPrice();
		DeliveryAddress deliveryAddress = getDeliveryAddressDao()
				.retrieveByPrimaryKey(Long.parseLong(deliveryAddressId));
		PaymentMethod paymentMethod = getPaymentMethodDao().retrieveByPrimaryKey(Long.parseLong(paymentId));
		Order order = new Order(totalPrice, customer, deliveryAddress, paymentMethod, customer.getCart());
		getOrderDao().insert(order);
		customer.getCart().clear();
		getCustomerDao().clearCart(customer.getId());
	}

	public void sendEmail() {

	}

	public void increaseProductQuantity(Long product, Long quantity) {
		getProductDao().increaseQuantity(product, 1L);
	}

	public void decreaseProductQuantity(Long product, Long quantity) throws DBOperationException {
		getProductDao().decreaseQuantity(product, 1L);
	}

	public void emptyCustomerCart(long idCustomer) {
		getCustomerDao().clearCart(idCustomer);

	}

	public void deletePaymentMethod(Customer customer, long paymentMethodId) throws DBOperationException {
		PaymentMethod paymentMethod = getPaymentMethodDao().retrieveByPrimaryKey(paymentMethodId);
		getPaymentMethodDao().dereferCustomerPaymentMethod(customer.getId(), paymentMethodId);
		customer.getPaymentMethods().remove(paymentMethod);
	}

	public void deleteDeliveryAddress(Customer customer, long deliveryAddressId) {
		DeliveryAddress deliveryAddress = getDeliveryAddressDao().retrieveByPrimaryKey(deliveryAddressId);
		getDeliveryAddressDao().dereferCustomerDeliveryAddress(customer.getId(), deliveryAddressId);
		customer.getDeliveryAddresses().remove(deliveryAddress);
	}

	public ArrayList<Product> getDiscountedProducts() {
		return getProductDao().retrieveDiscountedProducts();
	}

	public void addPaymentMethod(Customer customer, PaymentMethod paymentMethod) throws DBOperationException {
		getPaymentMethodDao().addPaymentMethod(customer.getId(), paymentMethod);
		customer.getPaymentMethods().add(paymentMethod);
	}

	public ArrayList<Category> getLeafCategoriesForDiet() {
		return getCategoryDao().retrieveLeafCategoriesForDiet();
	}

	public void modPaymentMethod(Customer customer, PaymentMethod paymentMethod) throws DBOperationException {
		getPaymentMethodDao().modPaymentMethod(paymentMethod);
		for (PaymentMethod p : customer.getPaymentMethods()) {
			if (p.equals(paymentMethod)) {
				p.setCardNumber(paymentMethod.getCardNumber());
				p.setOwner(paymentMethod.getOwner());
				p.setExpirationDate(paymentMethod.getExpirationDate());
				p.setSecurityCode(paymentMethod.getSecurityCode());
				p.setCompany(paymentMethod.getCompany());
				return;
			}
		}
	}

	public PaymentMethod getPaymentMethodById(long id) throws DBOperationException {
		PaymentMethod paymentMethod = getPaymentMethodDao().retrieveByPrimaryKey(id);
		if (paymentMethod == null) {
			throw new DBOperationException("Il metodo di pagamento non � stato trovato", "");
		}
		return paymentMethod;
	}

	public void addDeliveryAddress(Customer customer, DeliveryAddress deliveryAddress) throws DBOperationException {
		getDeliveryAddressDao().addDeliveryAddress(customer.getId(), deliveryAddress);
		customer.getDeliveryAddresses().add(deliveryAddress);
	}

	public DeliveryAddress getdeliveryAddressById(long id) throws DBOperationException {
		DeliveryAddress deliveryAddress = getDeliveryAddressDao().retrieveByPrimaryKey(id);
		if (deliveryAddress == null) {
			throw new DBOperationException("L'indirizzo di consegna non � stato trovato", "");
		}
		return deliveryAddress;
	}

	public void modDeliveryAddress(Customer customer, DeliveryAddress deliveryAddress) throws DBOperationException {
		getDeliveryAddressDao().modDeliveryAddress(deliveryAddress);
		for (DeliveryAddress d : customer.getDeliveryAddresses()) {
			if (d.equals(deliveryAddress)) {
				d.setCountry(deliveryAddress.getCountry());
				d.setCity(deliveryAddress.getCity());
				d.setAddress(deliveryAddress.getCity());
				d.setZipcode(deliveryAddress.getZipcode());
				d.setProvince(deliveryAddress.getZipcode());
				return;
			}
		}
	}

	public ArrayList<Product> getProductsForDiet(String categoryName, boolean offBrand, long weight) {
		return getProductDao().retrieveByCategoryAndWeight(categoryName, offBrand, weight);
	}

	public void modUsername(Customer customer, String username) throws DBOperationException {
		getCustomerDao().modUsername(customer.getId(), username);
		customer.setUsername(username);
	}

	public boolean checkIfUsernameExists(String username) {
		return getCustomerDao().checkIfUsernameExists(username);
	}

	public void modPassword(Customer customer, String passwordNew) {
		getCustomerDao().modPassword(customer.getId(), passwordNew);
		customer.setPassword(passwordNew);
	}

	public void modName(Customer customer, String name) {
		getCustomerDao().modName(customer.getId(), name);
		customer.setName(name);
	}

	public void modSurname(Customer customer, String surname) {
		getCustomerDao().modSurname(customer.getId(), surname);
		customer.setSurname(surname);
	}

	public void modEmail(Customer customer, String email) {
		getCustomerDao().modEmail(customer.getId(), email);
		customer.setEmail(email);
	}

	public void modBirthDate(Customer customer, String birthDate) {
		LocalDate date = LocalDate.parse(birthDate);
		getCustomerDao().modBirthDate(customer.getId(), date);
		customer.setBirthDate(date);
	}

	public ArrayList<Order> getOrdersOfCustomer(Customer customer) {
		return getOrderDao().getOrdersOfCustomer(customer.getId());
	}

	public String getCustomerEmail(String username) {
		return getCustomerDao().retrieveEmail(username);

	}

	public void updateCustomerPassword(String username, String newPassword) {
		getCustomerDao().updatePassword(username, newPassword);

	}

	public ArrayList<SuperMarket> getSuperMarketsContainsString(String string) {
		string = string.toLowerCase();
		ArrayList<SuperMarket> superMarkets = new ArrayList<SuperMarket>();
		for (SuperMarket superMarket : getSuperMarkets()) {
			String address = superMarket.getAddress().toLowerCase();
			if (address.contains(string)) {
				superMarkets.add(superMarket);
			}
		}
		return superMarkets;
	}

	public ArrayList<Category> getCategoriesContainsString(String string) {
		string = string.toLowerCase();
		ArrayList<Category> categories = new ArrayList<Category>();
		for (Category category : getCategories()) {
			String name = category.getName().toLowerCase();
			if (name.contains(string)) {
				categories.add(category);
			}
		}
		return categories;
	}

	public ArrayList<Product> getProductsContainsString(String string) {
		string = string.toLowerCase();
		ArrayList<Product> products = new ArrayList<Product>();
		for (Product product : getProducts()) {
			String all = product.toString().toLowerCase();
			if (all.contains(string)) {
				products.add(product);
			}
		}
		return products;
	}

}