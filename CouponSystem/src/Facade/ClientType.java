package Facade;

public enum ClientType {

	ADMIN("admin"), COMPANY("company"), CUSTOMER("customer");

	private final String name;

	ClientType(String name) {
		this.name = name();
	}

	public String getName() {
		return name;
	}

}
