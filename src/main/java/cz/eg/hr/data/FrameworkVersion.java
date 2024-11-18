package cz.eg.hr.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@Entity
@Table(name = "versions", uniqueConstraints = {@UniqueConstraint(columnNames = {"framework_id", "version_number"})})
@SequenceGenerator(name="versions_id_seq", initialValue=1)
public class FrameworkVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="versions_id_seq")
    private Long id;

    /**
     * The version number of the javascript framework
     */
    @NotBlank(message = "The version number of the javascript framework is required.")
    @Size(max = 30, message = "The version number of the javascript framework must not be longer than 30 characters.")
    @Column(name = "version_number", nullable = false, length = 30)
    private String versionNumber;

    /**
     * The date of deprecation of the framework version (the date when the version stopped being supported)
     */
    @Column(name = "deprecation_date")
    private Date deprecationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "framework_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JavascriptFramework framework;

    public FrameworkVersion() {
    }

    public FrameworkVersion(Long id, String versionNumber, Date deprecationDate) {
        this.id = id;
        this.versionNumber = versionNumber;
        this.deprecationDate = deprecationDate;
    }

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
