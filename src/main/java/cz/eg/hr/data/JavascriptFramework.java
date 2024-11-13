package cz.eg.hr.data;

import jakarta.persistence.*;

@Entity
@Table(name = "frameworks")
@SequenceGenerator(name="frameworks_id_seq", initialValue=1)
public class JavascriptFramework {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="frameworks_id_seq")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "rating")
    private Integer rating;

    public JavascriptFramework() {
    }

    public JavascriptFramework(String name, Integer rating) {
        this.name = name;
        this.rating = rating;
    }

    public Long getId() {
        return id;
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
        return "JavaScriptFramework [id=" + id + ", name=" + name + ", rating=" + rating + "]";
    }

}
