package cz.eg.hr.repository;

import cz.eg.hr.data.FrameworkVersion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FrameworkVersionRepository extends CrudRepository<FrameworkVersion, Long> {

    List<FrameworkVersion> findByFrameworkId(Long id);

    @Transactional
    void deleteByFrameworkId(Long id);

}
