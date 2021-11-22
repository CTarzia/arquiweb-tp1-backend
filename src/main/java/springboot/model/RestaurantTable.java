package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "tableId")
    private long tableId;

    @Column(name = "table_number")
    private long tableNumber;

    @Column(name = "table_status")
    private Boolean status;

    @Column(name = "table_calling_server")
    private Boolean callingServer;

    @Column(name = "restaurantid")
    private Long restaurantId;

    @Column(name = "restaurantname")
    private String restaurantName;

    public RestaurantTable() {

    }

    public RestaurantTable(Boolean status, Boolean calling_server) {
        super();
        this.status = status;
        this.callingServer = calling_server;
    }

    public long getTableID() {
        return tableId;
    }

    public void setTableID(long id) {
        this.tableId = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCalling_server() {
        return callingServer;
    }

    public void setCalling_server(Boolean calling_server) {
        this.callingServer = calling_server;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(long tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getRestaurantName() {
        return restaurantName;

    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
