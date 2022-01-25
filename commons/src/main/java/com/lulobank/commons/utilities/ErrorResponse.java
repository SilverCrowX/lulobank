package com.lulobank.commons.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorResponse {

	ACCOUNT_INITIALIZED(1, "account-already-initialized"), WITHOUT_FUNDS(2, "insufficient-limit"),
	DUPLICATE_TRANSACTION(3, "duplicate-transaction"), ACCOUNT_NOT_INITIALIZED(4, "account-not-initialized"),
	ACCOUNT_NOT_EXIST(5, "account-not-exist"), TRANSACTION_LIMIT_EXCEEDED(6, "transaction-limit-exceeded");

	private Integer code;
	private String message;

}
