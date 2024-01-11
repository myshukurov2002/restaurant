package com.company.entity;

import com.company.entity.base.StringBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "check_item")
public class CheckEntity extends StringBaseEntity {

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_phone")
    private String phone;//TODO for cashback

    @Column(name = "price")
    private Double price;

    @Column(name = "order_date")
    private Date date = new Date(); // default now()

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private ProfileEntity profileEntity;

    @Column(name = "owner_id")
    private String ownerId;
}
