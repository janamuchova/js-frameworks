package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity
@Table(name = "versions")
@SequenceGenerator(name="versions_id_seq", initialValue=1)
public class Version {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="versions_id_seq")
    private Long id;

    @Lob
    @Column(name = "version_number")
    private String versionNumber;

    @Column(name = "deprecation_date")
    private Date deprecationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "framework_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JavascriptFramework framework;

    public Long getId() {
        return id;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public Date getDeprecationDate() {
        return deprecationDate;
    }

    public void setDeprecationDate(Date deprecationDate) {
        this.deprecationDate = deprecationDate;
    }

    public JavascriptFramework getFramework() {
        return framework;
    }

    public void setFramework(JavascriptFramework framework) {
        this.framework = framework;
    }

}
