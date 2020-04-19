package com.alena.jewelryproject.jpa_repositories;

import com.alena.jewelryproject.model.EmailMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailMessagesRepository extends JpaRepository<EmailMessage, Long> {
}
