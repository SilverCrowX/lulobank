package com.lulobank.commons.maps;

import java.sql.Timestamp;
import java.time.Instant;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.lulobank.commons.dtos.TransactionDto;
import com.lulobank.commons.entities.TbTransaction;

@Mapper(uses = AccountMapper.class)
public interface TransactionMapper {

	TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

	@Mapping(target = "id", source = "transactionId")
	@Mapping(target = "merchant", source = "transactionMerchant")
	@Mapping(target = "time", source = "transactionDatetime")
	@Mapping(target = "account", source = "tbAccount")
	@Mapping(target = "amount", source = "transactionAmount")
	TransactionDto toDto(TbTransaction entity);

	@InheritInverseConfiguration
	TbTransaction toEntity(TransactionDto dto);

	default Timestamp map(Instant instant) {
		return instant == null ? null : Timestamp.from(instant);
	}

}
