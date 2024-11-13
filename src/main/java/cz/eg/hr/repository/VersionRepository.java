package cz.eg.hr.repository;

import cz.eg.hr.data.Version;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VersionRepository extends CrudRepository<Version, Long> {

    List<Version> findByFrameworkId(Long id);

    @Transactional
    void deleteByFrameworkId(Long id);

}
