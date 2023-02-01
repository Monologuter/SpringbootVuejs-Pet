package com.evan.aiu.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "animal")
@Data
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    //将area对象的id属性作为cid进行查询
    @ManyToOne
    @JoinColumn(name = "cid")
    private Area area;

    private String status;
    private String address;
    private String picture;
    private String name;
    private String breed;
    private String date;
    private int age;
    private String adoptname;
    private String description;
}
