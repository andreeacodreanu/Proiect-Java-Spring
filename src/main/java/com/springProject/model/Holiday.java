package com.springProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "holiday")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "holiday_id")
    private int id;
    @Column(name = "type")
    @NotEmpty(message = "*Please provide a type")
    private String type;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "start_date")
    @NotEmpty()
    private Date startDate;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "end_date")
    @NotEmpty()
    private Date endDate;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
