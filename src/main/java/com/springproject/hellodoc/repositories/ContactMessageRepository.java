package com.springproject.hellodoc.repositories;

import com.springproject.hellodoc.models.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {
}
