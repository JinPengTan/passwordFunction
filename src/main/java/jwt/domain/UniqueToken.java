package jwt.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UniqueToken.
 */
@Entity
@Table(name = "unique_token")
public class UniqueToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "tpan")
    private String tpan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWalletId() {
        return walletId;
    }

    public UniqueToken walletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getTpan() {
        return tpan;
    }

    public UniqueToken tpan(String tpan) {
        this.tpan = tpan;
        return this;
    }

    public void setTpan(String tpan) {
        this.tpan = tpan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniqueToken)) {
            return false;
        }
        return id != null && id.equals(((UniqueToken) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UniqueToken{" +
            "id=" + getId() +
            ", walletId='" + getWalletId() + "'" +
            ", tpan='" + getTpan() + "'" +
            "}";
    }
}
