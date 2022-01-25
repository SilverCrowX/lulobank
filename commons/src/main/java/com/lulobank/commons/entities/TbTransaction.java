package com.lulobank.commons.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_transaction")
public class TbTransaction {

	@Id
	@Column(name = "transaction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionId;

	@Column(name = "transaction_merchant")
	private String transactionMerchant;

	@Column(name = "transaction_datetime")
	private Timestamp transactionDatetime;

	@ManyToOne
	@JoinColumn(name = "transaction_account")
	private TbAccount tbAccount;

	@Column(name = "transaction_amount")
	private Long transactionAmount;

}
