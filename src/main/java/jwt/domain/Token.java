package jwt.domain;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Token.
 */
@Entity
@Table(name = "token")
public class Token implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wallet_id")
    private String walletId;

    @Column(name = "tpan")
    private String tpan;

    @Column(name = "hash_key")
    private String hashKey;

    @Column(name = "token_expiry")
    private String tokenExpiry;

    @Column(name = "token_status")
    private String tokenStatus;

    @Column(name = "mask_pan")
    private String maskPan;

    @Column(name = "en_pan")
    private String enPan;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "version")
    private Integer version;

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

    public Token walletId(String walletId) {
        this.walletId = walletId;
        return this;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getTpan() {
        return tpan;
    }

    public Token tpan(String tpan) {
        this.tpan = tpan;
        return this;
    }

    public void setTpan(String tpan) {
        this.tpan = tpan;
    }

    public String getHashKey() {
        return hashKey;
    }

    public Token hashKey(String hashKey) {
        this.hashKey = hashKey;
        return this;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public Token tokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
        return this;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public Token tokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
        return this;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public String getMaskPan() {
        return maskPan;
    }

    public Token maskPan(String maskPan) {
        this.maskPan = maskPan;
        return this;
    }

    public void setMaskPan(String maskPan) {
        this.maskPan = maskPan;
    }

    public String getEnPan() {
        return enPan;
    }

    public Token enPan(String enPan) {
        this.enPan = enPan;
        return this;
    }

    public void setEnPan(String enPan) {
        this.enPan = enPan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public Token expiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getVersion() {
        return version;
    }

    public Token version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        return id != null && id.equals(((Token) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Token{" +
            "id=" + getId() +
            ", walletId='" + getWalletId() + "'" +
            ", tpan='" + getTpan() + "'" +
            ", hashKey='" + getHashKey() + "'" +
            ", tokenExpiry='" + getTokenExpiry() + "'" +
            ", tokenStatus='" + getTokenStatus() + "'" +
            ", maskPan='" + getMaskPan() + "'" +
            ", enPan='" + getEnPan() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", version=" + getVersion() +
            "}";
    }
}
