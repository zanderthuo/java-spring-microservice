package com.patient.modal;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull
    @Column(nullable = false)
    private String address;

    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredDate;

    /**
     * ✅ Automatically set registeredDate before insert
     */
    @PrePersist
    protected void onCreate() {
        if (registeredDate == null) {
            registeredDate = LocalDateTime.now();
        }
    }
}
