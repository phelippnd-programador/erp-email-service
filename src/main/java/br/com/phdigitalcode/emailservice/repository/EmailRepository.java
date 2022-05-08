package br.com.phdigitalcode.emailservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.phdigitalcode.emailservice.entity.EmailEntity;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity,Long> {

}
