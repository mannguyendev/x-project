package com.mnguyendev.xproject.entity;

import com.mnguyendev.xproject.common.GenerateUUID;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @GenericGenerator(name="seq_id", type = GenerateUUID.class, parameters = {
//            @Parameter(name = "type", value = "USER")
//    } )
//    @GeneratedValue(generator="seq_id")
    private int id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "deleted_at")
    private Date deletedAt;

    @Column(name = "is_invalid")
    private boolean isInvalid;

    @Column(name = "modified_at")
    private Date modified_at;
}
