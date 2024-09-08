package br.com.rodrigo.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.rodrigo.data.vo.v1.BooksVO;
import br.com.rodrigo.exceptions.RequiredObjectIsNullExceptionException;
import br.com.rodrigo.model.Books;
import br.com.rodrigo.repositories.BooksRepository;
import br.com.rodrigo.services.BooksServices;
import br.com.rodrigo.unittests.mapper.mocks.MockBooks;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BooksServicesTest {

	MockBooks input;

	@InjectMocks
	private BooksServices service;

	@Mock
	BooksRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception {
		input = new MockBooks();
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testFindById() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		var result = service.findById(1L);
		System.out.println(result.getAuthor());
		assertNotNull(result.getId());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getTitle());
		assertEquals("Author Test1", result.getAuthor());

	}

	@Test
	void testFindAll() {
		List<Books> list = input.mockEntityList();

		when(repository.findAll()).thenReturn(list);

		var books = service.findAll();

		assertNotNull(books);
		assertEquals(14, books.size());

		var bookOne = books.get(2);

		System.out.println(bookOne.getAuthor());
		System.out.println(bookOne.getLaunchDate());
		assertNotNull(bookOne.getId());
		assertNotNull(bookOne.getLaunchDate());
		assertNotNull(bookOne.getTitle());
		assertEquals("Author Test2", bookOne.getAuthor());
	}

	@Test
	void testCreate() {
		Books entity = input.mockEntity(1);

		Books persisted = entity;
		persisted.setId(1L);

		BooksVO vo = input.mockVO(1);
		vo.setId(1L);

		when(repository.save(entity)).thenReturn(persisted);

		var result = service.create(vo);
		System.out.println(result.getAuthor());
		assertNotNull(result.getId());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
	}

	@Test
	void testCreateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullExceptionException.class, () -> {
			service.create(null);
		});

		String expectdMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectdMessage));
	}

	@Test
	void testUpdate() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);

		Books persisted = entity;
		persisted.setId(1L);

		BooksVO vo = input.mockVO(1);
		vo.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);

		var result = service.update(vo);
		System.out.println(result.getAuthor());
		assertNotNull(result.getId());
		assertNotNull(result.getLaunchDate());
		assertNotNull(result.getTitle());
		assertEquals("Author Test1", result.getAuthor());
	}

	@Test
	void testUpdateWithNullBook() {
		Exception exception = assertThrows(RequiredObjectIsNullExceptionException.class, () -> {
			service.update(null);
		});

		String expectdMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectdMessage));
	}

	@Test
	void testDelete() {
		Books entity = input.mockEntity(1);
		entity.setId(1L);

		when(repository.findById(1L)).thenReturn(Optional.of(entity));

		service.delete(1L);
	}

}
