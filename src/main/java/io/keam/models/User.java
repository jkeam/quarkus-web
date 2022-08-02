package io.keam.models;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.util.Locale;
import java.util.Optional;

@Entity
@Table(name = "users", indexes = { @Index(name = "usernameIndex", columnList = "username", unique = true) })
@UserDefinition
public class User extends PanacheEntity {
    @Username
    @Column(length = 128, nullable = false)
    public String username;

    @Password
    @Column(length = 128, nullable = false)
    public String password;

    @Roles
    @Column(length = 16, nullable = false)
    public String role;

    /**
     * Find user by username
     * @param username The username of the user
     * @return user
     */
    public static Optional<User> findByUsername(String username) {
        if (username == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(find("lower(username)", username.toLowerCase(Locale.US)).firstResult());
    }

    public static void add(String username, String password, String role) {
        User user = new User(username, password, role);
        user.persist();
    }

    public User() {
    }

    public User(String username, String password, String role) {
        this.username = username;
        this.password = BcryptUtil.bcryptHash(password);
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        if (this.username != null) {
            this.username = this.username.toLowerCase(Locale.US);
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return new EqualsBuilder().append(this.id, that.getId()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(id).toHashCode();
    }
}
