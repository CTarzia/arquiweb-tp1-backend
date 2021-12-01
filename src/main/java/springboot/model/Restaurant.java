package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurants")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "longitude")
	private String longitude;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "working_hours")
	private String workingHours;

	@Column(name = "appId")
	private int appId;


	public Restaurant() {

	}

	public Restaurant(String name, String latitude, String longitude, String phoneNumber, String workingHours) {
		super();
		this.name = name;
		this.latitude = latitude;
		this.longitude = longitude;
		this.phoneNumber = phoneNumber;
		this.workingHours = workingHours;
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getWorkingHours() {
		return workingHours;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setWorkingHours(String workingHours) {
		this.workingHours = workingHours;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
}
