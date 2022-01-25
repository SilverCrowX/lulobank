package com.lulobank.commons.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lulobank.commons.entities.TbAccount;

public interface TbAccountRepository extends JpaRepository<TbAccount, Long>{

}
