package jwt.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ExtendUser.
 */
@Entity
@Table(name = "extend_user")
public class ExtendUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne

    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("extendUsers")
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public ExtendUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Profile getProfile() {
        return profile;
    }

    public ExtendUser profile(Profile profile) {
        this.profile = profile;
        return this;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtendUser)) {
            return false;
        }
        return id != null && id.equals(((ExtendUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExtendUser{" +
            "id=" + getId() +
            "}";
    }
}
