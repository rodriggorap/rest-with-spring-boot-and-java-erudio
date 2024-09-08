package br.com.rodrigo.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.data.vo.v1.PersonVO;
import br.com.rodrigo.exceptions.RequiredObjectIsNullExceptionException;
import br.com.rodrigo.exceptions.ResourceNotFoundException;
import br.com.rodrigo.mapper.DozerMapper;
import br.com.rodrigo.model.Person;
import br.com.rodrigo.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;

	public List<PersonVO> findAll() {

		logger.info("Finding All people!");
		
		var vo = repository.findAll();
		return DozerMapper.parseListObjects(vo, PersonVO.class);
	}

	public PersonVO findById(Long id) {

		logger.info("Finding onde person!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, PersonVO.class);
	}

	public PersonVO create(PersonVO person) {
		if (person == null) throw new RequiredObjectIsNullExceptionException();
		logger.info("Creating one person!");
		
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = repository.save(entity);
		
		return DozerMapper.parseObject(vo, PersonVO.class); 
	}

	public PersonVO update(PersonVO person) {
		if (person == null) throw new RequiredObjectIsNullExceptionException();
		logger.info("Update one person!");
		
		var entity = repository.findById(person.getId()).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		var vo = repository.save(entity);
		
		return DozerMapper.parseObject(vo, PersonVO.class); 
	}
	
	public void delete(Long id) {

		logger.info("Update one person!");
		
		var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		
		repository.delete(entity);
	}

}
