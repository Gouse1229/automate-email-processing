package com.evoke.emailprocessing.repository;

import com.evoke.emailprocessing.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, String>{
}
