package com.estsoft.jpabookmall;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.estsoft.jpabookmall.domain.Book;
import com.estsoft.jpabookmall.domain.Category;

public class OneToManyMappingTest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			// 테스트 데이터 추가
			//testInsertCategories( em );
			//testInsertBooks( em );
			
			// 조회
			testFindCategory( em );
			
			// 저장
			testSave( em );
			
			//저장( 가짜매핑, 주인이 아닌 엔티티가 관계를 저장)
			//testSaveNonOwner( em );
			
		} catch( Exception ex ) {
			tx.rollback();
			ex.printStackTrace();
		}
		tx.commit();
		em.close();
		emf.close();
	}

	public static void testSaveNonOwner( EntityManager em ) {
		Book book3 = new Book();
		book3.setTitle( "책3" );
		book3.setPrice( 1000L );
		em.persist( book3 );
		
		Book book4 = new Book();
		book4.setTitle( "책4" );
		book4.setPrice( 1000L );
		em.persist( book4 );
		
		Category category = new Category();
		category.setName( "카테고리2" );
		category.getBooks().add( book3 );
		category.getBooks().add( book4 );
		em.persist( category );		
	}
	
	public static void testSaveBug( EntityManager em ) {
		Category category10 = new Category();
		category10.setName( "카테고리10" );
		em.persist( category10 );
		
		Category category20 = new Category();
		category20.setName( "카테고리20" );
		em.persist( category20 );

		Book book10 = new Book();
		book10.setTitle( "책10" );
		book10.setPrice( 1000L );
		em.persist( book10 );
		
		book10.setCategory( category10 );
		book10.setCategory( category20 );
	}
	
	public static void testSave( EntityManager em ) {
		Category category = new Category();
		category.setName( "카테고리1" );
		em.persist( category );
		
		Book book1 = new Book();
		book1.setTitle( "책1" );
		book1.setPrice( 1000L );
		book1.setCategory(category);
		em.persist( book1 );
		
		Book book2 = new Book();
		book2.setTitle( "책2" );
		book2.setPrice( 1000L );
		book2.setCategory(category);
		em.persist( book2 );
		
		List<Book> list = category.getBooks();
		for( Book book : list ) {
			System.out.println( book );
		}
	}
	
	public static void testFindCategory( EntityManager em ) {
		Category category = em.find( Category.class, 1L );
		System.out.println( category );
		
		List<Book> books = category.getBooks();
		for( Book book : books ) {
			System.out.println( book );
		}
	}
	 
	public static void testInsertBooks( EntityManager em ) {
		Category category1 = em.find( Category.class, 1L );
		Book book1 = new Book();
		book1.setTitle( "Effective Java" ); //1L
		book1.setPrice( 20000L );
		book1.setCategory( category1 );
		em.persist( book1 );
		
		Category category2 = em.find( Category.class, 2L );
		Book book2 = new Book();
		book2.setTitle( "Spring in Action" ); //2L
		book2.setPrice( 20000L );
		book2.setCategory( category2 );
		em.persist( book2 );
		
		Category category3 = em.find( Category.class, 3L );
		Book book3 = new Book();
		book3.setTitle( "The C Programming Language" ); //3L
		book3.setPrice( 20000L );
		book3.setCategory( category3 );
		em.persist( book3 );		
	}
	
	public static void testInsertCategories( EntityManager em ) {
		// 1L
		Category category1 = new Category();
		category1.setName( "Java Programming" );
		em.persist( category1 );
		
		// 2L
		Category category2 = new Category();
		category2.setName( "Spring Framework" );
		em.persist( category2 );
		
		// 3L
		Category category3 = new Category();
		category3.setName( "C Programming" );
		em.persist( category3 );
	}	
}
