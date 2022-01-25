package com.lulobank.accounts.services;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lulobank.accounts.validation.TransactionFilter;
import com.lulobank.commons.dtos.AccountDto;
import com.lulobank.commons.dtos.TransactionDto;
import com.lulobank.commons.entities.TbAccount;
import com.lulobank.commons.entities.TbTransaction;
import com.lulobank.commons.maps.AccountMapper;
import com.lulobank.commons.maps.TransactionMapper;
import com.lulobank.commons.repository.TbAccountRepository;
import com.lulobank.commons.repository.TbTransactionRepository;
import com.lulobank.commons.utilities.CompareState;
import com.lulobank.commons.utilities.ErrorResponse;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionService {

	@NonNull
	private final TbTransactionRepository transactionRepository;

	@NonNull
	private final TbAccountRepository accountRepository;

	public CompletableFuture<ResponseEntity<AccountDto>> recordTransaction(TransactionDto request) {

		return CompletableFuture.supplyAsync(() -> {
			try {
				CompareState state = TransactionFilter.getInstance().acceptTransaction(request);
				AccountDto response = null;
				if (state == CompareState.OK) {
					response = transaction(request);
				} else if (state == CompareState.WAITING) {
					while (state == CompareState.WAITING) {
						state = TransactionFilter.getInstance().acceptTransaction(request);
						Thread.sleep(1000);
					}
				} else if (state == CompareState.ERROR) {
					response = addViolations(request.getAccount(), ErrorResponse.DUPLICATE_TRANSACTION.getMessage());
				}
				return new ResponseEntity<>(response, HttpStatus.OK);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			} finally {
				TransactionFilter.getInstance().removeTransaction(request.getAccount().getId());
			}
		});
	}

	private AccountDto addViolations(AccountDto account, String violation) {
		if (account != null) {
			if (account.getViolations() != null) {
				account.getViolations().add(violation);
			} else {
				List<String> violations = new ArrayList<>();
				violations.add(violation);
				account.setViolations(violations);
			}
			return account;
		}
		return null;
	}

	private AccountDto transaction(TransactionDto dto) {
		Optional<TbAccount> entityAccount = accountRepository.findById(dto.getAccount().getId());
		if (entityAccount.isPresent()) {
			if (Boolean.TRUE.equals(entityAccount.get().getActivated())) {
				if (Boolean.TRUE.equals(timeTransaction(dto))) {
					if (entityAccount.get().getBalance() >= dto.getAmount()) {
						entityAccount.get().setBalance(entityAccount.get().getBalance() - dto.getAmount());
						transactionRepository.save(TransactionMapper.INSTANCE.toEntity(dto));
						return AccountMapper.INSTANCE.toDto(accountRepository.save(entityAccount.get()));
					} else {
						return addViolations(AccountMapper.INSTANCE.toDto(entityAccount.get()),
								ErrorResponse.WITHOUT_FUNDS.getMessage());
					}
				} else {
					return addViolations(AccountMapper.INSTANCE.toDto(entityAccount.get()),
							ErrorResponse.TRANSACTION_LIMIT_EXCEEDED.getMessage());
				}
			} else {
				return addViolations(AccountMapper.INSTANCE.toDto(entityAccount.get()),
						ErrorResponse.ACCOUNT_NOT_INITIALIZED.getMessage());
			}
		} else {
			return addViolations(dto.getAccount(), ErrorResponse.ACCOUNT_NOT_EXIST.getMessage());
		}
	}

	private Boolean timeTransaction(TransactionDto dto) {
		Timestamp timeTransaction = Timestamp.from(Instant.parse(dto.getTime()));
		Timestamp time1 = new Timestamp(timeTransaction.getTime() - TimeUnit.MINUTES.toMillis(2));
		Timestamp time2 = new Timestamp(timeTransaction.getTime() + TimeUnit.MINUTES.toMillis(2));
		List<TbTransaction> listTrnsaction = transactionRepository
				.findByTbAccountAccountIdAndTransactionMerchantAndTransactionDatetimeBetween(dto.getAccount().getId(),
						dto.getMerchant(), time1, time2);
		if (listTrnsaction != null && !listTrnsaction.isEmpty()) {
			boolean validate = true;

			for (TbTransaction e : listTrnsaction) {

				long diference = e.getTransactionDatetime().getTime() - timeTransaction.getTime();
				long minutos = TimeUnit.MILLISECONDS.toMinutes(diference);

				if ((minutos >= -2 && minutos <= 2) && (e.getTransactionMerchant().equals(dto.getMerchant()))) {
					validate = false;
				}
			}

			return validate;
		} else {
			return true;
		}
	}

}
