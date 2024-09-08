package br.com.rodrigo.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.rodrigo.data.vo.v1.BooksVO;
import br.com.rodrigo.model.Books;

public class MockBooks {

	public Books mockEntity() {
		return mockEntity(0);
	}

	public BooksVO mockVO() {
		return mockVO(0);
	}

	public List<Books> mockEntityList() {
		List<Books> books = new ArrayList<Books>();
		for (int i = 0; i < 14; i++) {
			books.add(mockEntity(i));
		}
		return books;
	}

	public List<BooksVO> mockVOList() {
		List<BooksVO> books = new ArrayList<>();
		for (int i = 0; i < 14; i++) {
			books.add(mockVO(i));
		}
		return books;
	}

	public Books mockEntity(Integer number) {
		Books book = new Books();
		book.setAuthor("Author Test" + number);
		book.setLaunchDate(new Date());
		book.setPrice(105.00);
		book.setId(number.longValue());
		book.setTitle("Title Test" + number);
		return book;
	}

	public BooksVO mockVO(Integer number) {
		BooksVO bookVO = new BooksVO();
		bookVO.setAuthor("Author Test" + number);
		bookVO.setLaunchDate(new Date());
		bookVO.setPrice(105.00);
		bookVO.setId(number.longValue());
		bookVO.setTitle("Title Test" + number);
		return bookVO;
	}

}
