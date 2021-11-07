package springboot.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("TableOrder")
public class TableOrder extends Order {
    @Column(name = "tableNumber")
    private Long tableNumber;

    public TableOrder() {

    }

    public TableOrder(Long tableNumber) {
        super();
        this.tableNumber = tableNumber;
    }

    public Long getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Long tableNumber) {
        this.tableNumber = tableNumber;
    }
}
