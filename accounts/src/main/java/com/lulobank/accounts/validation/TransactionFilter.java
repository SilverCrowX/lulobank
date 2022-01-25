package com.lulobank.accounts.validation;

import java.util.Hashtable;

import com.lulobank.commons.dtos.TransactionDto;
import com.lulobank.commons.utilities.CompareState;

public class TransactionFilter {

	private static TransactionFilter INSTANCE;
	private Hashtable<Long, TransactionDto> transactionsAccount;

	public static synchronized TransactionFilter getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TransactionFilter();
		}
		return INSTANCE;
	}

	private TransactionFilter() {
		transactionsAccount = new Hashtable<>();
	}

	public synchronized CompareState acceptTransaction(TransactionDto dto) {
		if (!transactionsAccount.containsKey(dto.getAccount().getId())) {
			transactionsAccount.put(dto.getAccount().getId(), dto);
			return CompareState.OK;
		} else {
			if (transactionsAccount.get(dto.getAccount().getId()).equals(dto)) {
				return CompareState.ERROR;
			}
			return CompareState.WAITING;
		}
	}

	public void removeTransaction(Long id) {
		transactionsAccount.remove(id);
	}

}
