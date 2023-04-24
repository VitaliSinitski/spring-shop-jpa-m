package com.vitali.database.entities;

import com.vitali.database.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    @Column(unique = true, nullable = false)
    private String username;
//    @Column(unique = true, nullable = false)
    private String email;
//    @Column(nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
//    @Column(nullable = false)
    private boolean enabled;
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInformation userInformation;
}
