package cz.eg.hr.data;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "VERSIONS")
public class FrameworkVersion {
    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Lob
    @Column(name = "VERSIONNUMBER")
    private String versionNumber;

    @Column(name = "DEPRECATIONDATE")
    private Instant deprecationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVersioNumber() {
        return versionNumber;
    }

    public void setVersionnumber(String versionnumber) {
        this.versionNumber = versionnumber;
    }

    public Instant getDeprecationdate() {
        return deprecationDate;
    }

    public void setDeprecationdate(Instant deprecationdate) {
        this.deprecationDate = deprecationdate;
    }

}
