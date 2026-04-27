//package com.eviden.fct.tiendaaplicaciones.mock;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
//import com.eviden.fct.tiendaaplicaciones.persistance.repositories.JpaCategoryRepository;
//import com.eviden.fct.tiendaaplicaciones.persistance.repositories.JpaReviewRepository;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ItemNotFoundException;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//
//public class JpaReviewRepositoryTest {
//
//	@Mock
//	private EntityManager entityManager;
//	
//	@InjectMocks
//	private JpaReviewRepository jpaReviewRepository;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void readTest() {
//
//		TypedQuery<Review> mockQuery = mock(TypedQuery.class);
//		when(entityManager.createQuery(anyString(), eq(Review.class))).thenReturn(mockQuery);
//		when(mockQuery.getResultList()).thenReturn(DataProvider.reviewListMock());
//		
//		List<Review> result = jpaReviewRepository.read();
//		
//		Assertions.assertEquals(3, result.size());
//
//		Assertions.assertNotNull(result.get(0).getContent());
//		Assertions.assertNotNull(result.get(0).getUser());
//		Assertions.assertEquals(Date.valueOf("2025-05-01"), result.get(0).getCreatedAt());
//		Assertions.assertEquals("Excelente aplicación, muy útil para el día a día.",
//		result.get(0).getReview());
//		
//		Assertions.assertNotNull(result.get(1).getContent());
//		Assertions.assertNotNull(result.get(1).getUser());
//		Assertions.assertEquals(Date.valueOf("2025-05-10"), result.get(1).getCreatedAt());
//		Assertions.assertEquals("Buena experiencia, aunque podría mejorar el diseño.",
//		result.get(1).getReview());
//		
//		Assertions.assertNotNull(result.get(2).getContent());
//		Assertions.assertNotNull(result.get(2).getUser());
//		Assertions.assertEquals(Date.valueOf("2025-05-15"), result.get(2).getCreatedAt());
//		Assertions.assertEquals("No me funcionó correctamente en mi dispositivo.",
//		result.get(2).getReview());
//		
//	}
//	
//	@Test
//	public void readByIdTest() {
//		
//		Content content = new Content();
//		User user = new User();
//		
//		Review review = new Review(
//	            1L,
//	            content,
//	            user,
//	            Date.valueOf("2025-05-01"),
//	            "Excelente aplicación, muy útil para el día a día."
//	        );
//		
//		when(entityManager.find(Review.class, 1L)).thenReturn(review);
//		
//		Assertions.assertEquals(1L, review.getId());
//		Assertions.assertNotNull(review.getContent());
//		Assertions.assertNotNull(review.getUser());
//		Assertions.assertEquals(Date.valueOf("2025-05-01"), review.getCreatedAt());
//		Assertions.assertEquals("Excelente aplicación, muy útil para el día a día.",
//		review.getReview());
//		
//	}
//	
//	@Test
//	public void createTest() throws ConflictException {
//		
//		Content content = new Content();
//		User user = new User();
//		
//		Review review = new Review(
//	            null,
//	            content,
//	            user,
//	            Date.valueOf("2025-05-10"),
//	            "Buena experiencia, aunque podría mejorar el diseño."
//	        );
//		
//		doAnswer(invocation -> {Review r = invocation.getArgument(0);
//		r.setId(10L); // Simula que la BD asigna el ID
//		return null;
//		}).when(entityManager).persist(any(Review.class));
//
//		Review result = jpaReviewRepository.create(review);
//		
//		Assertions.assertNotNull(result);
//		
//		Assertions.assertEquals(10L, result.getId());
//		Assertions.assertNotNull(result.getContent());
//		Assertions.assertNotNull(result.getUser());
//		Assertions.assertEquals(Date.valueOf("2025-05-10"), result.getCreatedAt());
//		Assertions.assertEquals("Buena experiencia, aunque podría mejorar el diseño.",
//				review.getReview());
//
//		verify(entityManager).persist(review);
//		
//	}
//	
//	@Test
//	public void updateTest() throws ItemNotFoundException {
//		
//		Long id = 1L;
//		Review existingReview = new Review();
//		existingReview.setId(id);
//		Review updatedReview = new Review();
//		updatedReview.setReview("New Review");
//		
//		when(entityManager.find(Review.class,id)).thenReturn(existingReview);
//		when(entityManager.merge(any(Review.class))).thenReturn(updatedReview);
//		
//		Review result = jpaReviewRepository.update(id, updatedReview);
//		
//	}
//	
//	
//	@Test
//	public void deleteTest() throws ItemNotFoundException {
//		
//		Long id = 10L;
//		Content content = new Content();
//		User user = new User();
//		
//		Review review = new Review(
//	            id,
//	            content,
//	            user,
//	            Date.valueOf("2025-05-15"),
//	            "No me funcionó correctamente en mi dispositivo."
//	        );
//		
//		when(entityManager.find(Review.class, 10L)).thenReturn(review);
//		jpaReviewRepository.delete(id);
//		verify(entityManager).remove(review);	
//		
//	}
//	
//	@Test
//	public void existsTest() {
//		
//		Long id = 1L;
//		Content content = new Content();
//		User user = new User();
//		Review review =  new Review(
//	            1L,
//	            content,
//	            user,
//	            Date.valueOf("2025-05-01"),
//	            "Excelente aplicación, muy útil para el día a día."
//	        );
//		
//		when(entityManager.find(Review.class, id)).thenReturn(review);
//		boolean result = jpaReviewRepository.exists(id);
//		Assertions.assertTrue(result);
//		verify(entityManager).find(Review.class,id);
//		
//	}
//	
//	@Test
//	public void notExistsTest() {
//		
//		Long id = 2L;
//		
//		when(entityManager.find(Review.class, id)).thenReturn(null);
//		
//		boolean result = jpaReviewRepository.exists(id);
//		Assertions.assertFalse(result);
//		verify(entityManager).find(Review.class, id);
//		
//	}
//	
//	@Test
//	public void findByContentIdTest() {
//	    Long contentId = 5L;
//
//	    Content mockContent = new Content();
//	    mockContent.setId(contentId);
//
//	    User mockUser = new User();
//	    mockUser.setId(1L);
//
//	    List<Review> expectedReviews = List.of(
//	        new Review(
//	            1L,
//	            mockContent,
//	            mockUser,
//	            Date.valueOf("2025-05-01"),
//	            "Muy buena aplicación, la uso todos los días."
//	        ),
//	        new Review(
//	            2L,
//	            mockContent,
//	            mockUser,
//	            Date.valueOf("2025-05-02"),
//	            "Interfaz intuitiva y fácil de usar."
//	        )
//	    );
//
//	    TypedQuery<Review> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(Review.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter("contentId", contentId)).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedReviews);
//
//	    List<Review> result = jpaReviewRepository.findByContentId(contentId);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(2, result.size());
//	    Assertions.assertEquals("Muy buena aplicación, la uso todos los días.", result.get(0).getReview());
//	    Assertions.assertEquals("Interfaz intuitiva y fácil de usar.", result.get(1).getReview());
//
//	    verify(entityManager).createQuery(anyString(), eq(Review.class));
//	    verify(mockQuery).setParameter("contentId", contentId);
//	    verify(mockQuery).getResultList();
//	}
//
//	
//	@Test
//	public void findByUserIdTest() {
//		
//		Long userId = 12L;
//		
//		Content content = new Content();
//		
//		User user = new User();
//		
//		List<Review> expectedReview = List.of(
//				new Review(
//			            2L,
//			            content,
//			            user,
//			            Date.valueOf("2025-05-10"),
//			            "Buena experiencia, aunque podría mejorar el diseño."
//			        )
//		    );
//		
//		TypedQuery<Review> mockQuery = mock(TypedQuery.class);
//		
//		when(entityManager.createQuery(anyString(), eq(Review.class))).thenReturn(mockQuery);
//		when(mockQuery.setParameter("userId", userId)).thenReturn(mockQuery);
//		when(mockQuery.getResultList()).thenReturn(expectedReview);
//		
//		List<Review> result = jpaReviewRepository.findByUserId(userId);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(1, result.size());
//		verify(entityManager).createQuery(anyString(), eq(Review.class));
//	    verify(mockQuery).setParameter("userId", userId);
//	    verify(mockQuery).getResultList();
//		
//	}
//	
//}
