package cz.eg.hr.controller;

import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class JavascriptFrameworkController {

    private final JavascriptFrameworkRepository repository;

    @Autowired
    public JavascriptFrameworkController(JavascriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/frameworks")
    public ResponseEntity<List<JavascriptFramework>> getFrameworks(@RequestParam(required = false) String name) {
        List<JavascriptFramework> frameworks = new ArrayList<>();

        if (name == null)
            repository.findAll().forEach(frameworks::add);
        else
            frameworks.addAll(repository.findAllByNameContaining(name));

        if (frameworks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(frameworks, HttpStatus.OK);
    }

    @GetMapping("/frameworks/{id}")
    public ResponseEntity<JavascriptFramework> getFrameworkById(@PathVariable("id") Long id) {
        JavascriptFramework framework = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found JavascriptFramework with id = " + id));

        return new ResponseEntity<>(framework, HttpStatus.OK);
    }

    @PostMapping("/frameworks")
    public ResponseEntity<JavascriptFramework> createFramework(@RequestBody JavascriptFramework framework) {
        JavascriptFramework newFramework = repository
            .save(new JavascriptFramework(framework.getName(), framework.getRating()));
        return new ResponseEntity<>(newFramework, HttpStatus.CREATED);
    }

    @PutMapping("/frameworks/{id}")
    public ResponseEntity<JavascriptFramework> updateFramework(@PathVariable("id") Long id, @RequestBody JavascriptFramework framework) {
        JavascriptFramework updatedFramework = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found JavascriptFramework with id = " + id));

        updatedFramework.setName(framework.getName());
        updatedFramework.setRating(framework.getRating());
        return new ResponseEntity<>(repository.save(updatedFramework), HttpStatus.OK);
    }

    @DeleteMapping("/frameworks/{id}")
    private ResponseEntity<HttpStatus> deleteFramework(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
