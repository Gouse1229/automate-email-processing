package com.sgm.emailprocessing.repository;

import com.sgm.emailprocessing.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long>{
    Email findByMessageId(String messageId);
}
