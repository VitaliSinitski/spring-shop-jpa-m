package com.vitali.database.entities;

import com.vitali.database.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
    private boolean enabled;
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Cart cart;
    @ToString.Exclude
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserInformation userInformation;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Order> orders = new ArrayList<>();
}
