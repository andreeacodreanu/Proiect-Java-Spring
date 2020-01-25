package com.springProject.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
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
    private int hours;
    @Column(name = "date")
    private String date;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
