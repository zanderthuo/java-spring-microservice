package com.patient.mapper;

import com.patient.dto.PatientRequestDto;
import com.patient.dto.PatientResponseDto;
import com.patient.modal.Patient;

import java.time.LocalDate;

public final class PatientMapper {

    private PatientMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static PatientResponseDto toResponseDto(Patient patient) {
        if (patient == null) return null;

        return PatientResponseDto.builder()
                .id(patient.getId() != null ? patient.getId().toString() : null)
                .name(patient.getName())
                .address(patient.getAddress())
                .email(patient.getEmail())
                .dateOfBirth(patient.getDateOfBirth() != null ? patient.getDateOfBirth().toString() : null)
                .build();
    }

    public static Patient toEntity(PatientRequestDto dto) {
        if (dto == null) return null;

        return Patient.builder()
                .name(dto.getName())
                .address(dto.getAddress())
                .email(dto.getEmail())
                .dateOfBirth(parseDate(dto.getDateOfBirth()))
                .build();
    }

    /**
     * Map updates from PatientRequestDto → existing Patient (UPDATE)
     */
    public static void updateEntity(Patient patient, PatientRequestDto dto) {
        if (patient == null || dto == null) return;

        patient.setName(dto.getName());
        patient.setAddress(dto.getAddress());
        patient.setEmail(dto.getEmail());
        patient.setDateOfBirth(parseDate(dto.getDateOfBirth()));
    }

    private static LocalDate parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        return LocalDate.parse(value); // expects ISO format: yyyy-MM-dd
    }
}
