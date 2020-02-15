package model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3268360976993689518L;
	private long id;
	private long barcode;
	private String name;
	private String brand;
	private double weight;
	private SuperMarket superMarket;
	private Category category;
	private boolean offBrand; // sottomarca
	private double price;
	private long quantity;
	private double discount;
	private String imageId;
	private String imagePath;
	private boolean deleted;
	private BigDecimal roundedDiscountedPrice;

	public Product(long id, long barcode, String name, String brand, double weight, SuperMarket superMarket,
			Category category, boolean offBrand, double price, long quantity, double discount, String imageId,
			boolean deleted) {
		super();
		this.id = id;
		this.barcode = barcode;
		this.name = name;
		this.brand = brand;
		this.weight = weight;
		this.superMarket = superMarket;
		this.category = category;
		this.offBrand = offBrand;
		this.price = price;
		this.quantity = quantity;
		this.discount = discount;
		this.imageId = imageId;
		this.imagePath = "https://drive.google.com/uc?export=view&id=" + imageId;
		this.deleted = deleted;
		this.roundedDiscountedPrice = new BigDecimal(price - discount).setScale(2, RoundingMode.HALF_UP);
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imageId = imagePath;
	}

	public BigDecimal getRoundedDiscountedPrice() {
		return roundedDiscountedPrice;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public long getBarcode() {
		return barcode;
	}

	public void setBarcode(long barcode) {
		this.barcode = barcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getRoundedPrice() {
		return new BigDecimal(price).setScale(2, RoundingMode.HALF_UP);
	}

	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public SuperMarket getSuperMarket() {
		return superMarket;
	}

	public void setSuperMarket(SuperMarket superMarket) {
		this.superMarket = superMarket;
	}

	public boolean isOffBrand() {
		return offBrand;
	}

	public void setOffBrand(boolean offBrand) {
		this.offBrand = offBrand;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return barcode + ", " + name + ", " + brand + ", " + superMarket + ", " + category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
