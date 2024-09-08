package br.com.rodrigo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.rodrigo.data.vo.v1.BooksVO;
import br.com.rodrigo.exceptions.RequiredObjectIsNullExceptionException;
import br.com.rodrigo.exceptions.ResourceNotFoundException;
import br.com.rodrigo.mapper.DozerMapper;
import br.com.rodrigo.model.Books;
import br.com.rodrigo.repositories.BooksRepository;

@Service
public class BooksServices {

	@Autowired
	BooksRepository repository;

	public List<BooksVO> findAll() {
		var vo = repository.findAll();
		return DozerMapper.parseListObjects(vo, BooksVO.class);
	}

	public BooksVO findById(Long id) {
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
		return DozerMapper.parseObject(entity, BooksVO.class);
	}

	public BooksVO create(BooksVO book) {
		if (book == null)
			throw new RequiredObjectIsNullExceptionException();

		var entity = DozerMapper.parseObject(book, Books.class);
		var vo = repository.save(entity);

		return DozerMapper.parseObject(vo, BooksVO.class);
	}

	public BooksVO update(BooksVO book) {
		if (book == null)
			throw new RequiredObjectIsNullExceptionException();

		var entity = repository.findById(book.getId())
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());
		var vo = repository.save(entity);

		return DozerMapper.parseObject(vo, BooksVO.class);
	}

	public void delete(Long id) {

		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

		repository.delete(entity);
	}

}
