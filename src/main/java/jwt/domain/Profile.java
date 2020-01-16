package jwt.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Profile.
 */
@Entity
@Table(name = "profile")
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role")
    private String role;

    @ManyToMany
    @JoinTable(name = "profile_permission",
               joinColumns = @JoinColumn(name = "profile_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "profiles")
    @JsonIgnore
    private Set<ExtendUser> extendUsers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public Profile role(String role) {
        this.role = role;
        return this;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Profile permissions(Set<Permission> permissions) {
        this.permissions = permissions;
        return this;
    }

    public Profile addPermission(Permission permission) {
        this.permissions.add(permission);
        permission.getProfiles().add(this);
        return this;
    }

    public Profile removePermission(Permission permission) {
        this.permissions.remove(permission);
        permission.getProfiles().remove(this);
        return this;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<ExtendUser> getExtendUsers() {
        return extendUsers;
    }

    public Profile extendUsers(Set<ExtendUser> extendUsers) {
        this.extendUsers = extendUsers;
        return this;
    }

    public Profile addExtendUser(ExtendUser extendUser) {
        this.extendUsers.add(extendUser);
        extendUser.getProfiles().add(this);
        return this;
    }

    public Profile removeExtendUser(ExtendUser extendUser) {
        this.extendUsers.remove(extendUser);
        extendUser.getProfiles().remove(this);
        return this;
    }

    public void setExtendUsers(Set<ExtendUser> extendUsers) {
        this.extendUsers = extendUsers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        return id != null && id.equals(((Profile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Profile{" +
            "id=" + getId() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
