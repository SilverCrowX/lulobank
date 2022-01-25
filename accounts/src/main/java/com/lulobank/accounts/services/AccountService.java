package com.lulobank.accounts.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.lulobank.commons.dtos.AccountDto;
import com.lulobank.commons.repository.TbAccountRepository;
import com.lulobank.commons.utilities.ErrorResponse;
import com.lulobank.commons.entities.TbAccount;
import com.lulobank.commons.maps.AccountMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

	@NonNull
	private final TbAccountRepository accountRepository;

	public CompletableFuture<ResponseEntity<AccountDto>> recordAccount(AccountDto request) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				if (request.getId() != null) {
					Optional<TbAccount> entity = accountRepository.findById(request.getId());
					if (!entity.isPresent()) {
						TbAccount account = accountRepository.save(AccountMapper.INSTANCE.toEntity(request));
						return new ResponseEntity<>(AccountMapper.INSTANCE.toDto(account), HttpStatus.NOT_ACCEPTABLE);
					} else {
						if (Boolean.TRUE.equals(entity.get().getActivated())) {
							List<String> violations = new ArrayList<>();
							violations.add(ErrorResponse.ACCOUNT_INITIALIZED.getMessage());
							request.setViolations(violations);
						}
						return new ResponseEntity<>(request, HttpStatus.ALREADY_REPORTED);
					}
				} else {
					return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});
	}

	public CompletableFuture<ResponseEntity<AccountDto>> updateAccount(AccountDto request) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				if (request.getId() != null) {
					Optional<TbAccount> entity = accountRepository.findById(request.getId());
					if (entity.isPresent()) {
						if (Boolean.TRUE.equals(entity.get().getActivated())) {
							List<String> violations = new ArrayList<>();
							violations.add(ErrorResponse.ACCOUNT_INITIALIZED.getMessage());
							request.setViolations(violations);
							return new ResponseEntity<>(request, HttpStatus.ALREADY_REPORTED);
						}
						TbAccount account = accountRepository.save(AccountMapper.INSTANCE.toEntity(request));
						return new ResponseEntity<>(AccountMapper.INSTANCE.toDto(account), HttpStatus.NOT_ACCEPTABLE);

					} else {
						return new ResponseEntity<>(request, HttpStatus.NO_CONTENT);
					}
				} else {
					return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		});
	}

}
