package jwt.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ApiKey.
 */
@Entity
@Table(name = "api_key")
public class ApiKey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "requestor_type")
    private String requestorType;

    @Column(name = "requestor_id")
    private String requestorId;

    @Column(name = "api_key")
    private String apiKey;

    @Column(name = "api_status")
    private String apiStatus;

    @Column(name = "modified_date")
    private ZonedDateTime modifiedDate;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestorType() {
        return requestorType;
    }

    public ApiKey requestorType(String requestorType) {
        this.requestorType = requestorType;
        return this;
    }

    public void setRequestorType(String requestorType) {
        this.requestorType = requestorType;
    }

    public String getRequestorId() {
        return requestorId;
    }

    public ApiKey requestorId(String requestorId) {
        this.requestorId = requestorId;
        return this;
    }

    public void setRequestorId(String requestorId) {
        this.requestorId = requestorId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public ApiKey apiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiStatus() {
        return apiStatus;
    }

    public ApiKey apiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
        return this;
    }

    public void setApiStatus(String apiStatus) {
        this.apiStatus = apiStatus;
    }

    public ZonedDateTime getModifiedDate() {
        return modifiedDate;
    }

    public ApiKey modifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }

    public void setModifiedDate(ZonedDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ApiKey createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApiKey)) {
            return false;
        }
        return id != null && id.equals(((ApiKey) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApiKey{" +
            "id=" + getId() +
            ", requestorType='" + getRequestorType() + "'" +
            ", requestorId='" + getRequestorId() + "'" +
            ", apiKey='" + getApiKey() + "'" +
            ", apiStatus='" + getApiStatus() + "'" +
            ", modifiedDate='" + getModifiedDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
