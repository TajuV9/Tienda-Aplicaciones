package com.eviden.fct.tiendaaplicaciones.domain.entities;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "AppUsers", uniqueConstraints = { @UniqueConstraint(columnNames = { "userName" }),
		@UniqueConstraint(columnNames = { "email" }) })
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Length(min = 3, max = 30)
	@Column(length = 30, nullable = false, unique = true)
	private String userName;

	@NotBlank
	@Length(min = 2, max = 50)
	@Column(length = 50, nullable = false)
	private String name;

	@NotBlank
	@Length(min = 2, max = 50)
	@Column(length = 50, nullable = false)
	private String lastName;

	@NotBlank
	@Email
	@Length(max = 100)
	@Column(length = 100, nullable = false, unique = true)
	private String email;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private Role role;

	@NotBlank
	@Length(min = 8, max = 100)
	@ToString.Exclude
	@Column(length = 100, nullable = false)
	private String password;

	@NotNull
	@Column(nullable = false)
	private Date createdAt;

	@NotNull
	@Column(nullable = false)
	private Date updatedAt;

	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = String.format("ROLE_%s", this.role.toString());
		List<GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role));
		return roles;
	}

	@Override
	public String getUsername() {
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Complete this when locked functionality is implemented
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Complete this when locked functionality is implemented
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Complete this when locked functionality is implemented
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Complete this when locked functionality is implemented
		return true;
	}
	
	@Transient
	private static final long serialVersionUID = 1260634258757151271L;
	
}
