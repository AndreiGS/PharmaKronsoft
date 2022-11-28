package com.kronsoft.pharma.auth;

import com.kronsoft.pharma.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "auth_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthToken {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(nullable = false)
    @NotNull
    private String jwtToken;

    @Column(nullable = false)
    @NotNull
    private String refreshToken;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @NotNull
    private AppUser user;

    public AuthToken(String jwtToken, String refreshToken, AppUser user) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        AuthToken token = (AuthToken) obj;
        return Objects.equals(id, token.id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode() + jwtToken.hashCode() + refreshToken.hashCode();
    }
}