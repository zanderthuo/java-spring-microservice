package com.patient.patient_management_sytem.service.impl;

import com.patient.patient_management_sytem.dto.PatientRequestDto;
import com.patient.patient_management_sytem.dto.PatientResponseDto;
import com.patient.patient_management_sytem.exception.EmailAlreadyExistsException;
import com.patient.patient_management_sytem.exception.PatientNotFoundException;
import com.patient.patient_management_sytem.mapper.PatientMapper;
import com.patient.patient_management_sytem.modal.Patient;
import com.patient.patient_management_sytem.repository.PatientRepository;
import com.patient.patient_management_sytem.service.PatientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    @Override
    public PatientResponseDto updatePatient(String id, PatientRequestDto request) {
        UUID patientId = parseUuid(id);

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + id));

        // Optional: prevent email duplicates when changing email
        String newEmail = request.getEmail();
        if (newEmail != null && !newEmail.equalsIgnoreCase(patient.getEmail())
                && patientRepository.existsByEmailAndIdNot(newEmail, patientId)) {
            throw new EmailAlreadyExistsException(newEmail);
        }

        PatientMapper.updateEntity(patient, request);

        Patient updated = patientRepository.save(patient);
        return PatientMapper.toResponseDto(updated);
    }

    @Override
    public void deletePatient(String id) {
        UUID patientId = parseUuid(id);

        if (!patientRepository.existsById(patientId)) {
            throw new PatientNotFoundException("Patient not found with id: " + id);
        }

        patientRepository.deleteById(patientId);
    }

    private UUID parseUuid(String id) {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid patient id format: " + id);
        }
    }
}
