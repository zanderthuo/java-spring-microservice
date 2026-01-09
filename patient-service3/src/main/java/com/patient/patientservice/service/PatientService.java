package com.patient.service;

import com.patient.dto.PatientRequestDto;
import com.patient.dto.PatientResponseDto;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<PatientResponseDto> getAllPatients();

    PatientResponseDto createPatient(PatientRequestDto patientRequestDto);
    PatientResponseDto updatePatient(String id, PatientRequestDto patientRequestDto);
    void deletePatient(String id);
}
