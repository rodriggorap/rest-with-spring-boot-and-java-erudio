package br.com.rodrigo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.model.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{}
