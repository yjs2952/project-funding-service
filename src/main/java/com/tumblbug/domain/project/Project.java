package com.tumblbug.domain.project;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    // @Id
    // @GeneratedValue
    private UUID id;

    @Column(length = 50)
    private String title;

    private String description;

    @Column(length = 20)
    private String userName;

    private String email;

    private String phoneNumber;

    
    private LocalDateTime startDate;

    private LocalDateTime endDate;

}
