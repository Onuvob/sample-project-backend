package com.sampleproject.user.entity;

import com.sampleproject.common.entity.BaseEntity;
import com.sampleproject.common.enums.Role;
import com.sampleproject.common.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(
                        name = "idx_user_email",
                        columnList = "email"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String phone;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @PrePersist
    public void prePersist(){
        if(status == null){
            status = UserStatus.ACTIVE;
        }
        if (role == null) role = Role.OWNER;
    }
}
