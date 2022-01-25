package com.lulobank.commons.maps;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lulobank.commons.dtos.AccountDto;
import com.lulobank.commons.entities.TbAccount;

@Mapper
public interface AccountMapper {

	AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

	@Mapping(target = "id", source = "accountId")
	@Mapping(target = "activecard", source = "activated")
	@Mapping(target = "availableLimit", source = "balance")
	AccountDto toDto(TbAccount entity);

	@InheritInverseConfiguration
	TbAccount toEntity(AccountDto dto);

}
