package com.estsoft.jpabookmall;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.estsoft.jpabookmall.domain.Book;
import com.estsoft.jpabookmall.domain.Category;

public class ManyToOneMappingTest {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		tx.begin();
		try {
			// 0. 저장 테스트
			// testSave( em );
			
			// 1. 카테고리 저장
			testInsertCategories( em );
			
			// 2. 책 저장
			testInsertBooks( em );
			
			// 3. 조회1
			testFindBook( em );
			
			//4. 조회2 (join psql)
			testFindBook2( em );
			
			//5. 수정1
			testUpdateBook( em );
			
			//6. 수정2
			testUpdateBook2( em );
			
			//7. 관계 제거
			testRemoveRelation( em );
			
			//8. 카테고리 제거
			testRemoveCategory( em );
			
		} catch( Exception ex ) {
			tx.rollback();
			ex.printStackTrace();
		}
		tx.commit();

		em.close();
		emf.close();
	}

	public static void testRemoveCategory( EntityManager em ) {
		Book book2 = em.find( Book.class, 2L );
		em.remove( book2 );
		Book book3 = em.find( Book.class, 3L );
		em.remove( book3 );
		
		Category category = em.find( Category.class, 4L );
		em.remove( category );
	}
	
	public static void testRemoveRelation( EntityManager em ) {
		Book book = em.find( Book.class, 1L );
		book.setCategory( null );
	}
	
	public static void testUpdateBook( EntityManager em ) {
		Category category = em.find( Category.class, 2L );
		Book book = em.find( Book.class, 1L );
		
		book.setCategory( category );
	}

	public static void testUpdateBook2( EntityManager em ) {
		Category category = new Category();
		category.setName( "IT" );
		em.persist( category );

		TypedQuery<Book> query 
			= em.createQuery( "select b from Book b", Book.class );
		
		List<Book> list = query.getResultList();
		for( Book book : list ) {
			book.setCategory( category );
		}
	}
	
	public static void testFindBook2( EntityManager em ) {
		// PSQL join
		String psql = "select b from Book b join b.category c where b.title = ?1";
		TypedQuery<Book> query = em.createQuery( psql, Book.class );
		query.setParameter( 1, "Effective Java" );
		
		List<Book> list = query.getResultList();
		for( Book book : list ) {
			System.out.println( book );
		}
	}

	public static void testFindBook( EntityManager em ) {
		Book book = em.find( Book.class, 1L );
		System.out.println( book );
		
		Category category = book.getCategory();
		System.out.println( category );
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
	
	public static void testSave( EntityManager em ) {
		Category category = new Category();
		category.setName( "프로그래밍" );
		em.persist( category );
		
		Book book = new Book();
		book.setTitle( "자바의 신" );
		book.setPrice( 20000L );
		book.setCategory(category);
		em.persist( book );
	}
}
