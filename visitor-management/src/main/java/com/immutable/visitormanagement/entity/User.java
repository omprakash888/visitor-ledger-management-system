package com.immutable.visitormanagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isEnabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ConfirmationToken> confirmationToken;

}
