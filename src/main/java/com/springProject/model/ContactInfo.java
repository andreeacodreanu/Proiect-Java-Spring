package com.springProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_info")
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "contact_info_id")
    private int id;
    @Column(name = "phone")
    @Length(min = 10, max = 10, message = "*Please insert a valid phone number")
    @NotEmpty(message = "*Please provide a phone number")
    private String phone;
    @Column(name = "city")
    @NotEmpty(message = "*Please provide your city")
    private String city;
    @Column(name = "street")
    @NotEmpty(message = "*Please provide your street")
    private String street;
    @Column(name = "number")
    @NotEmpty(message = "*Please provide your street number")
    private Integer number;
    @OneToOne(mappedBy = "contactInfo")
    private User user;

}
