package com.cleanhub.customertakehometask.dao;

import com.cleanhub.customertakehometask.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ContractDao extends JpaRepository<Contract, String> {
}