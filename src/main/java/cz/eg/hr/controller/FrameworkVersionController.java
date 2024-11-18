package cz.eg.hr.controller;

import cz.eg.hr.data.FrameworkVersion;
import cz.eg.hr.exception.ResourceNotFoundException;
import cz.eg.hr.repository.FrameworkVersionRepository;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class FrameworkVersionController {

    private final JavascriptFrameworkRepository frameworkRepository;

    private final FrameworkVersionRepository versionRepository;

    @Autowired
    public FrameworkVersionController(JavascriptFrameworkRepository frameworkRepository, FrameworkVersionRepository versionRepository) {
        this.frameworkRepository = frameworkRepository;
        this.versionRepository = versionRepository;
    }

    @GetMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<List<FrameworkVersion>> getVersionsByFrameworkId(@PathVariable("frameworkId") Long frameworkId) {
        if (!frameworkRepository.existsById(frameworkId)) {
            throw new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId);
        }

        List<FrameworkVersion> versions = versionRepository.findByFrameworkId(frameworkId);
        return new ResponseEntity<>(versions, HttpStatus.OK);
    }

    @GetMapping("/versions/{id}")
    public ResponseEntity<FrameworkVersion> getVersionById(@PathVariable("id") Long id) {
        FrameworkVersion version = versionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found FrameworkVersion with id = " + id));

        return new ResponseEntity<>(version, HttpStatus.OK);
    }

    @PostMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<FrameworkVersion> createVersionByFrameworkId(@PathVariable("frameworkId") Long frameworkId,
                                                 @Valid @RequestBody FrameworkVersion versionRequest) {
        FrameworkVersion version = frameworkRepository.findById(frameworkId).map(framework -> {
            versionRequest.setFramework(framework);
            return versionRepository.save(versionRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId));

        return new ResponseEntity<>(version, HttpStatus.CREATED);
    }

    @PutMapping("/versions/{id}")
    public ResponseEntity<FrameworkVersion> updateVersionById(@PathVariable("id") Long id, @Valid @RequestBody FrameworkVersion versionRequest) {
        FrameworkVersion version = versionRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found FrameworkVersion with id = " + id));

        version.setVersionNumber(versionRequest.getVersionNumber());
        version.setDeprecationDate(versionRequest.getDeprecationDate());
        return new ResponseEntity<>(versionRepository.save(version), HttpStatus.OK);
    }

    @DeleteMapping("/versions/{id}")
    public ResponseEntity<HttpStatus> deleteVersionById(@PathVariable("id") Long id) {
        if (!versionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Not found FrameworkVersion with id = " + id);
        }

        versionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/frameworks/{frameworkId}/versions")
    public ResponseEntity<List<FrameworkVersion>> deleteVersionsByFrameworkId(@PathVariable("frameworkId") Long frameworkId) {
        if (!frameworkRepository.existsById(frameworkId)) {
            throw new ResourceNotFoundException("Not found JavascriptFramework with id = " + frameworkId);
        }

        versionRepository.deleteByFrameworkId(frameworkId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
