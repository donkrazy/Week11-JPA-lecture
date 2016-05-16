package com.estsoft.jpabookmall;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.estsoft.jpabookmall.dto.BookDTO;

public class JPQLtest {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			testQuery( em );
			testQuery3( em );
			testQuery4( em );
			testQuery5( em );
			testQuery6( em );
		} catch( Exception ex ) {
			tx.rollback();
			ex.printStackTrace();
		}
		tx.commit();
		em.close();
		emf.close();
	}

	// DTO 사용
	public static void testQuery5( EntityManager em ) {
		String jpql = "select b.no, b.title from Book b";
		Query query = em.createQuery( jpql );
		
		List<Object[]> results = query.getResultList();
		List<BookDTO> resultDTOs = new ArrayList<BookDTO>();
		
		for( Object[] row  : results ) {
			BookDTO dto = new BookDTO();
			dto.setNo( (Long)row[ 0 ] );
			dto.setTitle( (String)row[1] ); 
			resultDTOs.add( dto );
		}	
		
		for( BookDTO dto : resultDTOs ) {
			System.out.println( dto );
		}
	}
	
	// DTO 사용 ( new 명령어 사용
	public static void testQuery6( EntityManager em ) {
		String jpql = 
"select new com.estsoft.jpabookmall.dto.BookDTO( b.no, b.title ) from Book b";

		TypedQuery<BookDTO> query = em.createQuery( jpql, BookDTO.class );
		List<BookDTO> resultDTOs = query.getResultList();
		
		for( BookDTO dto : resultDTOs ) {
			System.out.println( dto );
		}
	}
	
	public static void testQuery( EntityManager em ) {
		String jpql = "select b.no, b.title from Book b";
		Query query = em.createQuery( jpql );
		
		List<Object[]> results = query.getResultList();
		for( Object[] row  : results ) {
			System.out.println( row[0] + ":" + row[1] );
		}		
	}

	// scalar
	public static void testQuery3( EntityManager em ) {
		String jpql = "select b.title from Book b";
		Query query = em.createQuery( jpql );
		
		List<String> results = query.getResultList();
		for( String row  : results ) {
			System.out.println( row );
		}		
	}

	// scalar + entity (projection)
	public static void testQuery4( EntityManager em ) {
		String jpql = "select b.title, b.category from Book b";
		Query query = em.createQuery( jpql );
		
		List<Object[]> results = query.getResultList();
		for( Object[] row  : results ) {
			System.out.println( row[0] + ":" + row[1] );
		}		
	}
	//  SQL Injection
	public static void testQuery2( EntityManager em ) {
		//String jpql = "select b.no, b.title from Book b where b.title = :title";
		//String jpql = "select b.no, b.title from Book b where b.title = ?1";
		String title = "'baboya' OR 1 = 1";
		
		String jpql = "select b.no, b.title from Book b where b.title = " + title;
		Query query = em.createQuery( jpql );
		
		// query.setParameter( "title", title );
		// query.setParameter( 1, title );
		
		List<Object[]> results = query.getResultList();
		for( Object[] row  : results ) {
			System.out.println( row[0] + ":" + row[1] );
		}		
	}
}
