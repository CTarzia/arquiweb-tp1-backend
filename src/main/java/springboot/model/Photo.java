package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "photos")
public class Photo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long photoId;

	private Long restaurantId;

	private String name;

	private String type;

	@Lob
	private byte[] data;

	public Photo() {
	}

	public Photo(Long restaurantId, String name, String type, byte[] data) {
		this.restaurantId = restaurantId;
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public Long getPhotoId() {
		return photoId;
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
