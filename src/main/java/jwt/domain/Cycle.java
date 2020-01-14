package jwt.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;

/**
 * A Cycle.
 */
@Entity
@Table(name = "cycle")
public class Cycle implements Serializable, Comparator<Cycle> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cycle_count")
    private Integer cycleCount;

    @Column(name = "cycle_datetime")
    private LocalDate cycleDatetime;

    @Column(name = "cycle_password")
    private String cyclePassword;

    @ManyToOne
    @JsonIgnoreProperties("cycles")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCycleCount() {
        return cycleCount;
    }

    public Cycle cycleCount(Integer cycleCount) {
        this.cycleCount = cycleCount;
        return this;
    }

    public void setCycleCount(Integer cycleCount) {
        this.cycleCount = cycleCount;
    }

    public LocalDate getCycleDatetime() {
        return cycleDatetime;
    }

    public Cycle cycleDatetime(LocalDate cycleDatetime) {
        this.cycleDatetime = cycleDatetime;
        return this;
    }

    public void setCycleDatetime(LocalDate cycleDatetime) {
        this.cycleDatetime = cycleDatetime;
    }

    public String getCyclePassword() {
        return cyclePassword;
    }

    public Cycle cyclePassword(String cyclePassword) {
        this.cyclePassword = cyclePassword;
        return this;
    }

    public void setCyclePassword(String cyclePassword) {
        this.cyclePassword = cyclePassword;
    }

    public User getUser() {
        return user;
    }

    public Cycle user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cycle)) {
            return false;
        }
        return id != null && id.equals(((Cycle) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cycle{" +
            "id=" + getId() +
            ", cycleCount=" + getCycleCount() +
            ", cycleDatetime='" + getCycleDatetime() + "'" +
            ", cyclePassword='" + getCyclePassword() + "'" +
            "}";
    }

    @Override
    public int compare(Cycle cycle, Cycle t1) {
        return cycle.getCycleCount() - t1.getCycleCount();
    }
}
