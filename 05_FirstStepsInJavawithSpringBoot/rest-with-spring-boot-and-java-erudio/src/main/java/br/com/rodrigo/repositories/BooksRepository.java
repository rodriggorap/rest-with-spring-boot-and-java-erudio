package br.com.rodrigo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.rodrigo.model.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long> {}
