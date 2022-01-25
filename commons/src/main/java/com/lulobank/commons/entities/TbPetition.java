package com.lulobank.commons.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_petition")
public class TbPetition {

	@Id
	@Column(name = "petition_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long petitionId;

	@Column(name = "petition_time")
	private Timestamp petitionTime;

	@Column(name = "status")
	private String status;

}
