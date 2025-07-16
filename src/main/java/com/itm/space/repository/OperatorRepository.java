package com.itm.space.repository;

import com.itm.space.domain.entity.Operator;
import com.itm.space.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, UUID> {
    Optional<Operator> findByUser(User user);
}
