package springboot.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "menus")
public class Menu {
	@Id
	private Long restaurantId;

	private String name;

	private String type;

	@Lob
	private byte[] data;

	public Menu() {
	}

	public Menu(Long restaurantId, String name, String type, byte[] data) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public Long getRestaurantId() {
		return restaurantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}
