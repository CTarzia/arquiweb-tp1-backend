package springboot.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("PickupOrder")
public class PickupOrder extends Order {
    @Column(name = "clientId")
    private Long clientId;

    public PickupOrder() {

    }

    public PickupOrder(Long clientId) {
        super();
        this.clientId = clientId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
