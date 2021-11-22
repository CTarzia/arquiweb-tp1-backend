package springboot.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PickupOrder")
public class PickupOrder extends Order {
    @Column(name = "clientName")
    private String clientName;

    public PickupOrder() {

    }

    public PickupOrder(String clientName) {
        super();
        this.clientName = clientName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
