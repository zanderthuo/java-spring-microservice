package com.patient.controller;

import com.patient.dto.PatientRequestDto;
import com.patient.dto.PatientResponseDto;
import com.patient.dto.validators.CreatePatientValidationGroup;
import com.patient.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(name = "Patient", description = "API endpoints managing Patients")
public class PatientController {

    private final PatientService patientService;

    /**
     * ✅ Get all patients
     */
    @GetMapping
    @Operation(summary = "Get all Patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    /**
     * ✅ Create patient
     */
    @PostMapping
    @Operation(summary = "Create a new Patient")
    public ResponseEntity<PatientResponseDto> createPatient(
            @Validated({Builder.Default.class, CreatePatientValidationGroup.class}) @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto response = patientService.createPatient(patientRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * ✅ Update patient
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a Patient")
    public ResponseEntity<PatientResponseDto> updatePatient(
            @PathVariable String id,
            @Valid @RequestBody PatientRequestDto patientRequestDto) {

        PatientResponseDto response = patientService.updatePatient(id, patientRequestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * ✅ DELETE patient
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Patient")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build(); // 204
    }

    /**
     * (Optional) Get patient by ID
     * Uncomment if you add it in the service
     */
    /*
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDto> getPatientById(@PathVariable String id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }
    */
}
