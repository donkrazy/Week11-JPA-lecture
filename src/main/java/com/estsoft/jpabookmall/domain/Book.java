package com.estsoft.jpabookmall.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table( name = "book" )
public class Book {
	
	@Id
	@Column( name = "no" )
	@GeneratedValue( strategy = GenerationType.IDENTITY  )
	private Long no;
	
	@Column( name = "title" )
	private String title;
	
	@Column( name = "price" )
	private Long price;

	@Column( name = "description", nullable = false )
	private String description;
	
	@Transient
	private String test;
	
	
	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Book [no=" + no + ", title=" + title + ", price=" + price + "]";
	}
}
