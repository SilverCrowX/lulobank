package com.lulobank.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

	private Long id;
	private String merchant;
	private String time;
	private AccountDto account;
	private Long amount;

}
