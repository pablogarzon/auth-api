package com.example.authapi.mappers;

import com.example.authapi.dtos.PhoneDTO;
import com.example.authapi.models.Phone;

public class PhoneMapper {

	private PhoneMapper() {
	}

	public static PhoneDTO toDTO(Phone phone) {
		return new PhoneDTO(phone.getNumber(), phone.getCitycode(), phone.getContrycode());
	}

	public static Phone toEntity(PhoneDTO phoneDTO) {
		return new Phone(phoneDTO.getNumber(), phoneDTO.getCitycode(), phoneDTO.getContrycode());
	}
}
