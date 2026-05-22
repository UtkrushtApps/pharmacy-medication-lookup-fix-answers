package com.example.task.service;

import com.example.task.domain.Medication;
import com.example.task.exception.MedicationNotFoundException;
import com.example.task.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MedicationService {

    private static final Logger log = LoggerFactory.getLogger(MedicationService.class);

    private final MedicationRepository medicationRepository;

    public MedicationService(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Transactional(readOnly = true)
    public Medication getByCode(String code) {
        log.info("Looking up medication with code: {}", code);

        return medicationRepository.findByCode(code)
                .map(medication -> {
                    log.info("Medication found with code: {}", code);
                    return medication;
                })
                .orElseThrow(() -> {
                    log.warn("Medication not found with code: {}", code);
                    return new MedicationNotFoundException(code);
                });
    }
}
