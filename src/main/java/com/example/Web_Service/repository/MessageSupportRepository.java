package com.example.Web_Service.repository;

import com.example.Web_Service.model.entity.MessageSupport;
import com.example.Web_Service.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageSupportRepository extends JpaRepository<MessageSupport, Integer> {
    Optional<MessageSupport> findById(int id);
    @Query("SELECT u FROM MessageSupport u")
    List<MessageSupport> findAllMessageSupport();
    List<MessageSupport> findByUser (User user);
}