package shenpi.shenpi.model;

public class ShenPi {
	private Long id;
	private String name;
	private String product;
	private String address;
	private String phoneNumber;
	private String productType;

	public ShenPi() {
		super();
	}

	public ShenPi(Long id, String name, String product, String address, String phoneNumber, String productType) {
		super();
		this.id = id;
		this.name = name;
		this.product = product;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.productType = productType;
	}

	public ShenPi(String name, String product, String address, String phoneNumber, String productType) {
		super();
		this.name = name;
		this.product = product;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.productType = productType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	@Override
	public String toString() {
		return String.format("ShenPi [id=%s, name=%s, product=%s]", id, name, product);
	}

}
