package com.example.authapi.models;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;
	
	@NonNull
	private String name;
	
	@NonNull
	private String email;
	
	private String password;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Phone> phones;

	@Column(name = "created_at", updatable = false)
	private LocalDateTime created;

	@Column(name = "last_login")
	private LocalDateTime lastLogin;

	@Column(name = "is_active")
	private boolean isActive;
}