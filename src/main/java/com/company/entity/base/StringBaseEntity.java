package com.company.entity.base;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
public class StringBaseEntity {
    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "visibility")
    private Boolean visibility = Boolean.TRUE;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
