package com.estsoft.jpabookmall;

import static com.estsoft.jpabookmall.domain.QBook.book;
import static com.estsoft.jpabookmall.domain.QCategory.category;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.estsoft.jpabookmall.domain.Book;
import com.estsoft.jpabookmall.domain.Category;
import com.estsoft.jpabookmall.domain.QBook;
import com.estsoft.jpabookmall.dto.BookDTO;
import com.mysema.query.SearchResults;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.Projections;

public class QueryDSLTest {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabookmall");
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try {
			// insertCategories( em );
			// insertBooks( em );

			// testQueryDSL( em );
			// testQueryDSL2( em );

			// testSearchCond( em );
			// testSearchCond2( em );
			// testQueryDSL3( em );
			// testQueryDSL4( em );
			// testQueryDSL5( em );

			// testMaxBookPrice( em );

			// testProjectionOne( em );
			// testProjectionTuple( em );
			// testProjectionBeanByProperty(em);
			// testProjectionBeanByFields( em );
			// testProjectionBeanByConstructor( em );
			
			// testPaging( em );
			// testPaging2( em );
			
			// testJoin( em );
			// testJoin2( em );
			testJoin3( em );

		} catch (Exception ex) {
			tx.rollback();
			ex.printStackTrace();
		}
		
		tx.commit();
		em.close();
		emf.close();
	}

	public static void testJoin( EntityManager em ) {
		JPAQuery query = new JPAQuery( em );
		
		List<Book> list = 
		query.
		from( book ).
		join( book.category, category ).
		list( book );
		
		for( Book book : list ) {
			System.out.println( book );	
		}
	}

	public static void testJoin3( EntityManager em ) {
		JPAQuery query = new JPAQuery( em );
		
		List<Book> list = 
		query.
		from( book, category ).
		where( book.category.eq(category)).
		list( book );
		
		for( Book book : list ) {
			System.out.println( book );
		}
	}
	
	public static void testJoin2( EntityManager em ) {
		JPAQuery query = new JPAQuery( em );
		
		List<Book> list = 
		query.
		from( book ).
		join( book.category, category ).
		on( category.name.like( "%J%" ) ).
		list( book );
		
		for( Book book : list ) {
			System.out.println( book );	
		}
	}
	
	public static void testPaging2( EntityManager em ) {
		int page = 2;

		JPAQuery query = new JPAQuery( em );
	
		SearchResults<Book> results = 
		query.
		from( book ).
		orderBy( book.title.asc(), book.price.desc() ).
		offset( (page-1)*3 ).
		limit( 3 ).
		listResults( book );
		
		long totalCount = results.getTotal();
		long offset = results.getOffset();
		long limit = results.getLimit();
		System.out.println( totalCount + ":" + offset + ":" + limit );
		for( Book book : results.getResults() ) {
			System.out.println( book );
		}
	}
	
	public static void testPaging( EntityManager em ) {
		int page = 2;

		JPAQuery query = new JPAQuery( em );
	
		List<Book> list = 
		query.
		from( book ).
		offset( (page-1)*3 ).
		limit( 3 ).
		list( book );
		
		for( Book book : list ) {
			System.out.println( book );
		}
		
	}
	
	public static void testProjectionBeanByConstructor(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<BookDTO> list = query.from(book).list(Projections.constructor(BookDTO.class, book.no.as("no"), book.title));

		for (BookDTO dto : list) {
			System.out.println(dto);
		}
	}
	
	public static void testProjectionBeanByFields(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<BookDTO> list = query.from(book).list(Projections.fields(BookDTO.class, book.no.as("no"), book.title));

		for (BookDTO dto : list) {
			System.out.println(dto);
		}
	}

	public static void testProjectionBeanByProperty(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<BookDTO> list = query.from(book).list(Projections.bean(BookDTO.class, book.no.as("no"), book.title));

		for (BookDTO dto : list) {
			System.out.println(dto);
		}
	}

	public static void testProjectionOne(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<String> list = query.from(book).list(book.title);
		for (String title : list) {
			System.out.println(title);
		}
	}

	public static void testProjectionTuple(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Tuple> list = query.from(book).list(book.title, book.price);
		for (Tuple t : list) {
			System.out.println(t.get(book.title) + ":" + t.get(book.price));
		}
	}

	public static void testMaxBookPrice(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		Long maxPrice = query.from(book).uniqueResult(book.price.max());

		System.out.println(maxPrice);
	}

	public static void testSearchCond2(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		// and는 ,대체
		List<Book> list = query.from(book).where(book.title.eq("Java Thread"), book.price.lt(40000L)).list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testSearchCond(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Book> list = query.from(book).where(book.title.eq("Java Thread").and(book.price.lt(40000L))).list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testQueryDSL(EntityManager em) {
		JPAQuery query = new JPAQuery(em);
		QBook qBook = new QBook("book");

		List<Book> list = query.from(qBook).where(qBook.price.gt(20000L)).orderBy(qBook.price.desc()).list(qBook);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testQueryDSL2(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Book> list = query.from(book).where(book.price.gt(20000L)).orderBy(book.price.desc()).list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testQueryDSL3(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Book> list = query.from(book).where(book.price.between(10000L, 20000L)).orderBy(book.price.desc())
				.list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testQueryDSL4(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Book> list = query.from(book).where(book.title.contains("Java")).orderBy(book.title.asc()).list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void testQueryDSL5(EntityManager em) {
		JPAQuery query = new JPAQuery(em);

		List<Book> list = query.from(book).where(book.title.startsWith("Java")).orderBy(book.title.asc()).list(book);

		for (Book book : list) {
			System.out.println(book);
		}
	}

	public static void insertCategories(EntityManager em) {
		// no = 1L
		Category category1 = new Category();
		category1.setName("Java Programming");
		em.persist(category1);

		// no = 2L
		Category category2 = new Category();
		category2.setName("Spring Framework");
		em.persist(category2);

		// no = 3L
		Category category3 = new Category();
		category3.setName("C Programming");
		em.persist(category3);
	}

	public static void insertBooks(EntityManager em) {

		Category category1 = em.find(Category.class, 1L);
		Book book1 = new Book();
		book1.setTitle("Effective Java");
		book1.setPrice(10000L);
		book1.setCategory(category1);
		em.persist(book1);

		Book book2 = new Book();
		book2.setTitle("Java in a Nutshell");
		book2.setPrice(25000L);
		book2.setCategory(category1);
		em.persist(book2);

		Book book3 = new Book();
		book3.setTitle("Java Thread");
		book3.setPrice(31000L);
		book3.setCategory(category1);
		em.persist(book3);

		Category category2 = em.find(Category.class, 2L);
		Book book4 = new Book();
		book4.setTitle("Spring in Action");
		book4.setPrice(30000L);
		book4.setCategory(category2);
		em.persist(book4);

		Book book5 = new Book();
		book5.setTitle("Spring3 Recipes");
		book5.setPrice(27000L);
		book5.setCategory(category2);
		em.persist(book5);

		Category category3 = em.find(Category.class, 3L);
		Book book6 = new Book();
		book6.setTitle("The C Programming Language");
		book6.setPrice(30000L);
		book6.setCategory(category3);
		em.persist(book6);

		Book book7 = new Book();
		book7.setTitle("Structure in C Language");
		book7.setPrice(30000L);
		book7.setCategory(category3);
		em.persist(book7);

	}

}
