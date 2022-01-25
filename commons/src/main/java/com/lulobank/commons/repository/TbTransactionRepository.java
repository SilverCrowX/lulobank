package com.lulobank.commons.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lulobank.commons.entities.TbTransaction;

public interface TbTransactionRepository extends JpaRepository<TbTransaction, Long> {

	List<TbTransaction> findByTbAccountAccountIdAndTransactionMerchantAndTransactionDatetimeBetween(Long accountId,
			String transactionMerchant, Timestamp transactionDatetimeStart, Timestamp transactionDatetimeEnd);

}
