package com.patient.patient_management_sytem.service;

import com.patient.patient_management_sytem.dto.PatientRequestDto;
import com.patient.patient_management_sytem.dto.PatientResponseDto;

import java.util.List;

public interface PatientService {

    List<PatientResponseDto> getAllPatients();

    PatientResponseDto createPatient(PatientRequestDto patientRequestDto);
}
