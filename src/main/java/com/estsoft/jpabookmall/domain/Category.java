package com.estsoft.jpabookmall.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "category" )
public class Category {
	
	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	private Long no;
	
	@Column( name = "name", nullable = false, length = 200 )
	private String name;

	@OneToMany( mappedBy = "category" )
	private List<Book> books = new ArrayList<Book>();
	
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

	@Override
	public String toString() {
		return "Category [no=" + no + ", name=" + name + "]";
	}
}
