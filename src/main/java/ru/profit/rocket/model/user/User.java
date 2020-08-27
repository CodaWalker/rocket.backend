package ru.profit.rocket.model.user;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import ru.profit.rocket.model.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "usr")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    /** имя пользователя (логин) */
    @Column(name = "login",unique = true,nullable = false)
    private String login;

    /** Пароль */
    @Column(name = "password",unique = false,nullable = false)
    private String password;

    /** Имя */
    private String firstName;

    /** Фамилия */
    private String lastName;

    /** Отчество */
    private String middleName;

    /** Тип */
    private String typeUser;

    /** Подтвержденный пользователь электронной почтой */
    private boolean accepted;

    /** Блокировка пользователя */
    private boolean locked;

    /** Email */
    @Column(name = "email",unique = false,length = 40,nullable = false)
    private String email;

    /** Activation_code */
    private String activationCode;

    /** Роль */
    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @PrePersist
    private void setInitialValues() {
        activationCode = RandomStringUtils.randomAlphanumeric(25);
        locked = false;
        accepted = false;
    }
}
