package com.javalearnings.securitydemo.entities.auth;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;

/**
 * The persistent class for the user database table.
 */
@Entity
@Table(name = "user_info")
@Data
public class User implements UserDetails, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name="active_ind")
    private String activeInd;

    @Column(name="city", nullable = true)
    private String city;

    @Column(name="country_id", nullable = true)
    private String countryId;

    @Column(name="created_date", nullable = true)
    private LocalDate createdDate;

    @Column(name="created_user_id", nullable = true)
    private Integer createdUserId;

    @Column(name="failed_login_date", nullable = true)
    private LocalDate failedLoginDate;

    @Column(name="failed_logins", nullable = true)
    private Integer failedLogins;

    private String first;

    @Column(name="last", nullable = true)
    private String last;

    @Column(name="last_login_date")
    private LocalDate lastLoginDate;

    @Column(name="manager_id", nullable = true)
    private Integer managerId;

    @Column(name="middle", nullable = true)
    private String middle;

    private String password;

    @Column(name="phone_home", nullable = true)
    private String phoneHome;

    @Column(name="phone_mobile", nullable = true)
    private String phoneMobile;

    @Column(name="reset_url", nullable = true)
    private String resetUrl;

    @Column(name="state_id", nullable = true)
    private String stateId;

    @Column(name="updated_by", nullable = true)
    private Integer updatedBy;

    @Column(name="updated_date", nullable = true)
    private LocalDate updatedDate;

    @Column(name="user_email", nullable = true)
    private String userEmail;

    private String username;

    @Column(name="zipcode", nullable = true)
    private String zipcode;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
