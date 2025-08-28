package com.example.authapi.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.authapi.mappers.PhoneMapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "user_id")
	private UUID id;

	@NonNull
	private String name;

	@NonNull
	@Column(nullable = false, unique = true)
	private String email;

	private String password;

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, orphanRemoval = true, fetch = FetchType.EAGER)
	private List<Phone> phones = new ArrayList<>();
	
	public void setPhones(List<Phone> phones) {
		for (Phone phone : phones) {
			phone.setUser(this);
			this.phones.add(phone);
		}
	}
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime created;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "is_active")
	private boolean isActive;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isActive;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isActive;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isActive;
	}

	@Override
	public boolean isEnabled() {
		return isActive;
	}
}