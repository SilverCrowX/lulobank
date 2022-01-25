package com.lulobank.commons.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_parameter")
public class TbParameter {

	@Id
	@Column(name = "parameter_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long parameterId;

	@Column(name = "name")
	private String name;

	@Column(name = "value")
	private String value;

}
