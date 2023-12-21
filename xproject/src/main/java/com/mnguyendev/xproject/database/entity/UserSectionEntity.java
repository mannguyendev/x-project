package com.mnguyendev.xproject.database.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="user-section", uniqueConstraints = @UniqueConstraint(name = "uniqueUserToken", columnNames = {"token"}))
public class UserSectionEntity extends BaseEntity {
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    private UserEntity user;

    @Column(name = "token")
    private String token;
}
