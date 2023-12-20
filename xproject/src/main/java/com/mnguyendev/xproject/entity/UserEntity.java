package com.mnguyendev.xproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="customer", uniqueConstraints = @UniqueConstraint(name = "uniqueUsername", columnNames = {"username", "phone_no", "email"}))
public class UserEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_no")
    private String phoneNo;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "user_img")
    private String userImg;

//    @OneToMany(mappedBy = "user",
//            fetch = FetchType.LAZY,
//            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
//                    CascadeType.DETACH, CascadeType.REFRESH})
//    private List<UserSectionEntity> userSections;

    public void map(UserEntity user){
        if (user.getUsername() != null) setUsername(user.username);
        if (user.getFirstName() != null) setFirstName(user.firstName);
        if (user.getLastName() != null) setLastName(user.lastName);
        if (user.getGender() != null) setGender(user.gender);
        if (user.getDateOfBirth() != null) setDateOfBirth(user.dateOfBirth);
        if (user.getUserImg() != null) setUserImg(user.userImg);
        if (user.getPhoneNo() != null) setPhoneNo(user.phoneNo);
        if (user.getEmail() != null) setEmail(user.email);
    }
}
