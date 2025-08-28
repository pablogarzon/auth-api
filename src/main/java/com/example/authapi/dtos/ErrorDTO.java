package com.example.authapi.dtos;

import java.time.Instant;

import lombok.Data;

@Data
public class ErrorDTO {
	private Instant timestamp = Instant.now();
	private final int codigo;
	private final String detail;
}
