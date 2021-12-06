package springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "restaurant_tables")
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tableId;

    @Column(name = "table_number")
    private long tableNumber;

    @Column(name = "table_calling_server")
    private Boolean callingServer;

    @Column(name = "restaurantid")
    private Long restaurantId;

    public RestaurantTable() {

    }

    public RestaurantTable(long tableId, long tableNumber, Boolean callingServer, Long restaurantId) {
        super();
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.callingServer = callingServer;
        this.restaurantId = restaurantId;
    }

    public long getTableId() {
        return tableId;
    }

    public void setTableId(long id) {
        this.tableId = id;
    }

    public Boolean getCallingServer() {
        return callingServer;
    }

    public void setCallingServer(Boolean calling_server) {
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

}
