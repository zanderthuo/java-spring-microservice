package com.patient.patient_management_sytem.service;

import com.patient.patient_management_sytem.dto.PatientResponseDto;
import com.patient.patient_management_sytem.mapper.PatientMapper;
import com.patient.patient_management_sytem.modal.Patient;
import com.patient.patient_management_sytem.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientResponseDto> getPatients() {
        List<Patient> patients = patientRepository.findAll();

        return patients.stream().map(PatientMapper::toPatientResponseDto).toList();
    }
}
