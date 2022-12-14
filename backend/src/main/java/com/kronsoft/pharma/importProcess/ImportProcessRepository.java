package com.kronsoft.pharma.importProcess;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImportProcessRepository extends JpaRepository<ImportProcess, UUID> {
    Optional<ImportProcess> findByStatus(ProcessStatus status);
}
