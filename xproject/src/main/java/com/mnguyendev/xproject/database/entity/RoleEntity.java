package com.mnguyendev.xproject.database.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.Reference;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name="roles")
public class RoleEntity extends BaseEntity {
    @Column(name = "role")
    private String role;
}
