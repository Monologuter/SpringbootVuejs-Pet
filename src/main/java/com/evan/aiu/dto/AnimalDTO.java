package com.evan.aiu.dto;

import com.evan.aiu.entity.Area;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author kanghb
 * @data 2022/5/4
 */
@Data
public class AnimalDTO {

    int id;

    //将area对象的id属性作为cid进行查询
    @ManyToOne
    @JoinColumn(name = "cid")
    private Area area;

    private String status;
    private String statusName;
    private String address;
    private String picture;
    private String name;
    private String breed;
    private String date;
    private int age;
    private String adoptname;
    private String description;
}
