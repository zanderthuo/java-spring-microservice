package com.patient.patient_management_sytem.service.impl;

import com.patient.patient_management_sytem.dto.PatientRequestDto;
import com.patient.patient_management_sytem.dto.PatientResponseDto;
import com.patient.patient_management_sytem.exception.EmailAlreadyExistsException;
import com.patient.patient_management_sytem.mapper.PatientMapper;
import com.patient.patient_management_sytem.modal.Patient;
import com.patient.patient_management_sytem.repository.PatientRepository;
import com.patient.patient_management_sytem.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Transactional
    @Override
    public List<PatientResponseDto> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map(PatientMapper::toResponseDto)
                .toList();

    }

    @Override
    @Transactional
    public PatientResponseDto createPatient(PatientRequestDto patientRequestDto) {
        if(patientRepository.existsByEmail(patientRequestDto.getEmail())) {
            throw new EmailAlreadyExistsException("A Patient with this email " + " already Exists" + patientRequestDto.getEmail());
        }
        Patient newPatient = patientRepository.save(PatientMapper.toEntity(patientRequestDto));
        return PatientMapper.toResponseDto(newPatient);
    }
}
