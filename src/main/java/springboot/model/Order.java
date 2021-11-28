package springboot.model;

import javax.persistence.*;

@Entity
@Inheritance
//@DiscriminatorColumn(name = "Order_Type")
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name = "content")
    private String content;

    @Column(name = "status")
    private Status  status;

    @Column(name = "restoId")
    private Long restoId;

    @Column(name = "appId")
    private int appId;

    public Order() {

    }

    public Order(String content, Status status, Long restoId) {
        super();
        this.content = content;
        this.status = status;
        this.restoId = restoId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) { this.status = status; }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public Long getRestoId() {
        return restoId;
    }

    public void setRestoId(Long restoId) {
        this.restoId = restoId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }
}

