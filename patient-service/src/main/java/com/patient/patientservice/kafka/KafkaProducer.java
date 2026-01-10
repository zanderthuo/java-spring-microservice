package com.patient.patientservice.kafka;

import com.patient.patientservice.modal.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service
public class KafkaProducer {
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(Patient patient) {
        PatientEvent event = PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build();

        kafkaTemplate.send("patient", event.toByteArray())
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Kafka publish failed for patientId={}", event.getPatientId(), ex);
                    } else {
                        var m = result.getRecordMetadata();
                        log.info("Kafka published topic={} partition={} offset={} patientId={}",
                                m.topic(), m.partition(), m.offset(), event.getPatientId());
                    }
                });
    }
}
