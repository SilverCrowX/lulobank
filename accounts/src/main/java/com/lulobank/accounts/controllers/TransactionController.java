package com.lulobank.accounts.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lulobank.accounts.configuration.AsyncConfiguration;
import com.lulobank.accounts.services.TransactionService;
import com.lulobank.commons.dtos.AccountDto;
import com.lulobank.commons.dtos.TransactionDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("transactions")
public class TransactionController {

	@NonNull
	private final TransactionService service;
	
	@Async(AsyncConfiguration.ASYNC_NAME)
	@PostMapping("register")
	public @ResponseBody CompletableFuture<ResponseEntity<AccountDto>> recordTransaction(@RequestBody TransactionDto request) {
		return service.recordTransaction(request);
	}

}
