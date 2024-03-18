package com.otusone.Backend.repository;

import com.otusone.Backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface ChatRepo extends JpaRepository<Chat,Integer> {
}
