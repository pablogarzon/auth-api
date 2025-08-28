package com.example.authapi.mappers;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public static List<PhoneDTO> toDTO(List<Phone> phones) {
		return phones.stream().map(PhoneMapper::toDTO).collect(Collectors.toList());
	}

	public static List<Phone> toEntity(List<PhoneDTO> phonesDTO) {
		return phonesDTO.stream().map(PhoneMapper::toEntity).collect(Collectors.toList());
	}
}
