package com.example.task.controller;

import com.example.task.domain.Medication;
import com.example.task.service.MedicationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medications")
public class MedicationController {

    private final MedicationService medicationService;

    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping("/{code}")
    public Medication getMedication(@PathVariable String code) {
        return medicationService.getByCode(code);
    }
}
