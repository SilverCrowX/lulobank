package com.lulobank.accounts.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lulobank.accounts.configuration.AsyncConfiguration;
import com.lulobank.accounts.services.AccountService;
import com.lulobank.commons.dtos.AccountDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("accounts")
public class AccountController {

	@NonNull
	private final AccountService service;

	@Async(AsyncConfiguration.ASYNC_NAME)
	@PostMapping("register")
	public @ResponseBody CompletableFuture<ResponseEntity<AccountDto>> recordAccount(@RequestBody AccountDto request) {
		return service.recordAccount(request);
	}

	@Async(AsyncConfiguration.ASYNC_NAME)
	@PutMapping("register/activate/{activate}")
	public @ResponseBody CompletableFuture<ResponseEntity<AccountDto>> recordAccount(@RequestParam Boolean activate,
			@RequestBody AccountDto request) {
		return service.updateAccount(request);
	}

}
