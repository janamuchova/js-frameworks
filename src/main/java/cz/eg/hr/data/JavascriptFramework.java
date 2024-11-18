package cz.eg.hr.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "frameworks")
@SequenceGenerator(name="frameworks_id_seq", initialValue=1)
public class JavascriptFramework {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="frameworks_id_seq")
    private Long id;

    /**
     * The name of the javascript framework
     */
    @NotBlank(message = "The name of the javascript framework is required.")
    @Size(max = 30, message = "The name of the javascript framework must not be longer than 30 characters.")
    @Column(name = "name", nullable = false, unique = true, length = 30)
    private String name;

    /**
     * The rating of the javascript framework (1 to 5 stars)
     */
    @Min(value = 1, message = "The rating of the javascript framework must be equal or greater than 1.")
    @Max(value = 5, message = "The rating of the javascript framework must be equal or less than 5.")
    @Column(name = "rating")
    private Double rating;

    public JavascriptFramework() {
    }

    public JavascriptFramework(String name, Double rating) {
        this.name = name;
        this.rating = rating;
    }

    public JavascriptFramework(Long id, String name, Double rating) {
        this.id = id;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id + ", name=" + name + ", rating=" + rating + "]";
    }

}
