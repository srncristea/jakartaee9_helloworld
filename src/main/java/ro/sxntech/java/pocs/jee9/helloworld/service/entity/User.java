package ro.sxntech.java.pocs.jee9.helloworld.service.entity;

import jakarta.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "mso_ekg.User")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
    @SequenceGenerator(name = "user_generator", sequenceName = "mso_ekg.user_identifier_seq", allocationSize = 1)
    public Long getId() {
        return id;
    }

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "username")
    private String userName;
    private String password;
    private String email;
    private String phone;


}
