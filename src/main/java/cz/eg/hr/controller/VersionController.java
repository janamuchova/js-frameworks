package cz.eg.hr.controller;

import cz.eg.hr.data.Version;
import cz.eg.hr.exception.ResourceNotFoundException;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import cz.eg.hr.repository.VersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class VersionController {

    private final JavascriptFrameworkRepository frameworkRepository;

    private final VersionRepository versionRepository;

    @Autowired
    public VersionController(JavascriptFrameworkRepository frameworkRepository, VersionRepository versionRepository) {
        this.frameworkRepository = frameworkRepository;
        this.versionRepository = versionRepository;
    }

    // otestovat všechny metody
    // GeneralControllerAdvice, Errors, ValidationError poladit
    // přesun do package service
    // junit testy
    // komentáře

    @GetMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<List<Version>> getVersionsByFrameworkId(@PathVariable(value = "frameworkId") Long frameworkId) {
        if (!frameworkRepository.existsById(frameworkId)) {
            throw new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId);
        }

        List<Version> versions = versionRepository.findByFrameworkId(frameworkId);
        return new ResponseEntity<>(versions, HttpStatus.OK);
    }

    @GetMapping("/versions/{id}")
    public ResponseEntity<Version> getVersionById(@PathVariable(value = "id") Long id) {
        Version version = versionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Version with id = " + id));

        return new ResponseEntity<>(version, HttpStatus.OK);
    }

    @PostMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<Version> createVersion(@PathVariable(value = "frameworkId") Long frameworkId,
                                                 @RequestBody Version versionRequest) {
        Version version = frameworkRepository.findById(frameworkId).map(framework -> {
            versionRequest.setFramework(framework);
            return versionRepository.save(versionRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId));

        return new ResponseEntity<>(version, HttpStatus.CREATED);
    }

    @PutMapping("/versions/{id}")
    public ResponseEntity<Version> updateVersion(@PathVariable("id") Long id, @RequestBody Version versionRequest) {
        Version version = versionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("VersionId " + id + "not found"));

        version.setVersionNumber(versionRequest.getVersionNumber());
        version.setDeprecationDate(versionRequest.getDeprecationDate());
        return new ResponseEntity<>(versionRepository.save(version), HttpStatus.OK);
    }

    @DeleteMapping("/versions/{id}")
    public ResponseEntity<HttpStatus> deleteVersion(@PathVariable("id") Long id) {
        versionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<List<Version>> deleteAllVersionsOfFramework(@PathVariable(value = "frameworkId") Long frameworkId) {
        if (!frameworkRepository.existsById(frameworkId)) {
            throw new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId);
        }

        versionRepository.deleteByFrameworkId(frameworkId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
