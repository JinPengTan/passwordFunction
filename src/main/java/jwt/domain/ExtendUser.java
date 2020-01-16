package jwt.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(name = "extend_user_profile",
               joinColumns = @JoinColumn(name = "extend_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"))
    private Set<Profile> profiles = new HashSet<>();

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

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public ExtendUser profiles(Set<Profile> profiles) {
        this.profiles = profiles;
        return this;
    }

    public ExtendUser addProfile(Profile profile) {
        this.profiles.add(profile);
        profile.getExtendUsers().add(this);
        return this;
    }

    public ExtendUser removeProfile(Profile profile) {
        this.profiles.remove(profile);
        profile.getExtendUsers().remove(this);
        return this;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
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
