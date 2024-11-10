package cz.eg.hr.data;

import jakarta.persistence.*;

@Entity
@Table(name = "FRAMEWORKS")
public class JavascriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", nullable = false, length = 30)
    private String name;

    @Column(name = "RATING")
    private Integer rating;

//    @Column
//    private Long versionId;

    public JavascriptFramework() {
    }

    public JavascriptFramework(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id + ", name=" + name + "]";
    }

}
