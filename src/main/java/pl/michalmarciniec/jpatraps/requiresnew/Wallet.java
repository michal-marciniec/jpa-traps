package pl.michalmarciniec.jpatraps.requiresnew;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Wallet {

    @Id
    @GeneratedValue
    private Long id;

    private BigDecimal amount;

    public Wallet() {
        this.amount = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
