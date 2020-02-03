package jwt.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A TokenHistory.
 */
@Entity
@Table(name = "token_history")
public class TokenHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Column(name = "tr_id")
    private String trId;

    @Column(name = "version")
    private Integer version;

    @Column(name = "history_dt")
    private ZonedDateTime historyDt;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTpan() {
        return tpan;
    }

    public TokenHistory tpan(String tpan) {
        this.tpan = tpan;
        return this;
    }

    public void setTpan(String tpan) {
        this.tpan = tpan;
    }

    public String getHashKey() {
        return hashKey;
    }

    public TokenHistory hashKey(String hashKey) {
        this.hashKey = hashKey;
        return this;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getTokenExpiry() {
        return tokenExpiry;
    }

    public TokenHistory tokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
        return this;
    }

    public void setTokenExpiry(String tokenExpiry) {
        this.tokenExpiry = tokenExpiry;
    }

    public String getTokenStatus() {
        return tokenStatus;
    }

    public TokenHistory tokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
        return this;
    }

    public void setTokenStatus(String tokenStatus) {
        this.tokenStatus = tokenStatus;
    }

    public String getMaskPan() {
        return maskPan;
    }

    public TokenHistory maskPan(String maskPan) {
        this.maskPan = maskPan;
        return this;
    }

    public void setMaskPan(String maskPan) {
        this.maskPan = maskPan;
    }

    public String getEnPan() {
        return enPan;
    }

    public TokenHistory enPan(String enPan) {
        this.enPan = enPan;
        return this;
    }

    public void setEnPan(String enPan) {
        this.enPan = enPan;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public TokenHistory expiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getTrId() {
        return trId;
    }

    public TokenHistory trId(String trId) {
        this.trId = trId;
        return this;
    }

    public void setTrId(String trId) {
        this.trId = trId;
    }

    public Integer getVersion() {
        return version;
    }

    public TokenHistory version(Integer version) {
        this.version = version;
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ZonedDateTime getHistoryDt() {
        return historyDt;
    }

    public TokenHistory historyDt(ZonedDateTime historyDt) {
        this.historyDt = historyDt;
        return this;
    }

    public void setHistoryDt(ZonedDateTime historyDt) {
        this.historyDt = historyDt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TokenHistory)) {
            return false;
        }
        return id != null && id.equals(((TokenHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TokenHistory{" +
            "id=" + getId() +
            ", tpan='" + getTpan() + "'" +
            ", hashKey='" + getHashKey() + "'" +
            ", tokenExpiry='" + getTokenExpiry() + "'" +
            ", tokenStatus='" + getTokenStatus() + "'" +
            ", maskPan='" + getMaskPan() + "'" +
            ", enPan='" + getEnPan() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", trId='" + getTrId() + "'" +
            ", version=" + getVersion() +
            ", historyDt='" + getHistoryDt() + "'" +
            "}";
    }
}
