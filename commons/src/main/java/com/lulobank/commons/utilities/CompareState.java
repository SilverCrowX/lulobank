package com.lulobank.commons.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CompareState {

	OK(1, "OK"), VALIDATE(2, "VE"), ERROR(3, "ER"), WAITING(4, "WG");

	private Integer code;
	private String state;

}
