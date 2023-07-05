package com.immutable.visitormanagement.entity;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true)
    private long employeeId;

    private String employeeName;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
    private List<Visitor> visitor;
}
