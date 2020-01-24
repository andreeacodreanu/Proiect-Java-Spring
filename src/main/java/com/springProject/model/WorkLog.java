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
@Table(name = "worklog")
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "log_id")
    private int id;
    @Column(name = "hours")
    @NotEmpty()
    private int hours;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    @Column(name = "date")
    @NotEmpty()
    private Date date;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
}
