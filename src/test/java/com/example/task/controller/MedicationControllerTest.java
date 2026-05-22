package com.example.task.controller;

import com.example.task.domain.Medication;
import com.example.task.exception.MedicationNotFoundException;
import com.example.task.service.MedicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MedicationController.class)
class MedicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicationService medicationService;

    @Test
    void getMedication_existingCode_returns200() throws Exception {
        Medication medication = new Medication();
        medication.setCode("AMOX500");
        medication.setName("Amoxicillin");
        medication.setDosageForm("capsule");
        medication.setStrength("500mg");

        when(medicationService.getByCode("AMOX500")).thenReturn(medication);

        mockMvc.perform(get("/medications/AMOX500"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("AMOX500"))
                .andExpect(jsonPath("$.name").value("Amoxicillin"));
    }

    @Test
    void getMedication_unknownCode_returns404() throws Exception {
        when(medicationService.getByCode("UNKNOWN"))
                .thenThrow(new MedicationNotFoundException("UNKNOWN"));

        mockMvc.perform(get("/medications/UNKNOWN"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsStringIgnoringCase("not found")));
    }
}
