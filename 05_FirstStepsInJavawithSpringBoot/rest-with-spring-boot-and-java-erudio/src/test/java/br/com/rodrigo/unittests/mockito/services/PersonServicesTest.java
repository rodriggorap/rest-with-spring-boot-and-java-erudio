package br.com.rodrigo.unittests.mockito.services;

import static org.junit.jupiter.api.Assertions.*;
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

import br.com.rodrigo.data.vo.v1.PersonVO;
import br.com.rodrigo.exceptions.RequiredObjectIsNullExceptionException;
import br.com.rodrigo.model.Person;
import br.com.rodrigo.repositories.PersonRepository;
import br.com.rodrigo.services.PersonServices;
import br.com.rodrigo.unittests.mapper.mocks.MockPerson;


@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class PersonServicesTest {
	
	MockPerson input;
	
	@InjectMocks
	private PersonServices service;
	
	@Mock
	PersonRepository repository;

	@BeforeEach
	void setUpMocks() throws Exception { 
		input = new MockPerson();
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	void testFindById() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		var result = service.findById(1L);
		System.out.println(result.getFirstName());
		assertNotNull(result.getId());
		assertNotNull(result.getFirstName());
		assertNotNull(result.getLastName());
		assertEquals("First Name Test1",result.getFirstName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
		
	}

	@Test
	void testFindAll() {
		List<Person> list = input.mockEntityList();
		
		when(repository.findAll()).thenReturn(list);
		
		var people = service.findAll();
		
		assertNotNull(people);
		assertEquals(14, people.size());
		
		var personOne = people.get(1);
		
		System.out.println(personOne.getFirstName());
		assertNotNull(personOne.getId());
		assertNotNull(personOne.getFirstName());
		assertNotNull(personOne.getLastName());
		assertEquals("First Name Test1",personOne.getFirstName());
		assertNotNull(personOne.getGender());
		assertNotNull(personOne.getAddress());
	}

	@Test
	void testCreate() {
		Person entity = input.mockEntity(1);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);
		
		
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.create(vo);
		System.out.println(result.getFirstName());
		assertNotNull(result.getId());
		assertEquals("First Name Test1",result.getFirstName());
		assertNotNull(result.getLastName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
	}
	
	@Test
	void testCreateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullExceptionException.class, () -> {
			service.create(null);
		});
		
		String expectdMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectdMessage));
	}

	@Test
	void testUpdate() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		Person persisted = entity;
		persisted.setId(1L);
		
		PersonVO vo = input.mockVO(1);
		vo.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		when(repository.save(entity)).thenReturn(persisted);
		
		var result = service.update(vo);
		System.out.println(result.getFirstName());
		assertNotNull(result.getId());
		assertEquals("First Name Test1",result.getFirstName());
		assertNotNull(result.getLastName());
		assertNotNull(result.getGender());
		assertNotNull(result.getAddress());
	}
	
	@Test
	void testUpdateWithNullPerson() {
		Exception exception = assertThrows(RequiredObjectIsNullExceptionException.class, () -> {
			service.update(null);
		});
		
		String expectdMessage = "Is is not allowed to persist a null object!";
		String actualMessage = exception.getMessage();
		
		assertTrue(actualMessage.contains(expectdMessage));
	}

	@Test
	void testDelete() {
		Person entity = input.mockEntity(1);
		entity.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(entity));
		
		service.delete(1L);
	}

}
