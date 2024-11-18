package cz.eg.hr.repository;

import cz.eg.hr.data.JavascriptFramework;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JavascriptFrameworkRepository extends CrudRepository<JavascriptFramework, Long> {

    List<JavascriptFramework> findByNameContaining(String name);
}
