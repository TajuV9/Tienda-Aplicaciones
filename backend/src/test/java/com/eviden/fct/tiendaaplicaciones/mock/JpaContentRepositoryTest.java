//package com.eviden.fct.tiendaaplicaciones.mock;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.*;
//
//import java.math.BigDecimal;
//import java.sql.Date;
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
//import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
//import com.eviden.fct.tiendaaplicaciones.persistance.repositories.JpaContentRepository;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ItemNotFoundException;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//
//public class JpaContentRepositoryTest {
//
//	@Mock
//	private EntityManager entityManager;
//	
//	@InjectMocks
//	private JpaContentRepository jpaContentRepository;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void readTest() {
//	    TypedQuery<Content> mockQuery = mock(TypedQuery.class);
//	    when(entityManager.createQuery(anyString(), eq(Content.class))).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(DataProvider.contentListMock());
//
//	    List<Content> result = jpaContentRepository.read();
//
//	    Assertions.assertEquals(3, result.size());
//
//	    // ---------- Content 1 ----------
//	    Content c1 = result.get(0);
//	    Assertions.assertEquals(1L, c1.getId());
//	    Assertions.assertEquals("Photo Editor Pro", c1.getName());
//	    Assertions.assertEquals("https://example.com/images/photo_editor.png", c1.getImage());
//	    Assertions.assertEquals("photo_editor_pro.apk", c1.getFileName());
//	    Assertions.assertEquals("Una potente herramienta de edición de fotos con filtros avanzados y efectos.", c1.getDescription());
//	    Assertions.assertEquals(new BigDecimal("3.1"), c1.getVersion());
//	    Assertions.assertEquals(4, c1.getRating());
//
//	    // ---------- Content 2 ----------
//	    Content c2 = result.get(1);
//	    Assertions.assertEquals(2L, c2.getId());
//	    Assertions.assertEquals("Fitness Tracker", c2.getName());
//	    Assertions.assertEquals("https://example.com/images/fitness_tracker.png", c2.getImage());
//	    Assertions.assertEquals("fitness_tracker.apk", c2.getFileName());
//	    Assertions.assertEquals("Aplicación para registrar tus entrenamientos, pasos y calorías diarias.", c2.getDescription());
//	    Assertions.assertEquals(new BigDecimal("2.5"), c2.getVersion());
//	    Assertions.assertEquals(5, c2.getRating());
//
//	    // ---------- Content 3 ----------
//	    Content c3 = result.get(2);
//	    Assertions.assertEquals(3L, c3.getId());
//	    Assertions.assertEquals("Language Learner", c3.getName());
//	    Assertions.assertEquals("https://example.com/images/language_learner.png", c3.getImage());
//	    Assertions.assertEquals("language_learner.apk", c3.getFileName());
//	    Assertions.assertEquals("Aprende nuevos idiomas con lecciones interactivas y juegos educativos.", c3.getDescription());
//	    Assertions.assertEquals(new BigDecimal("1.8"), c3.getVersion());
//	    Assertions.assertEquals(3, c3.getRating());
//	}
//
//	
//	@Test
//	public void readByIdTest() throws ItemNotFoundException {
//	    Category category = new Category();
//	    category.setCategoryName("Health");
//
//	    Content content2 = new Content(
//	        2L,
//	        "Fitness Tracker",
//	        "https://example.com/images/fitness_tracker.png",
//	        new User(),
//	        List.of(category),
//	        new BigDecimal("2.99"),
//	        List.of(new Download()),
//	        Date.valueOf("2024-12-01"),
//	        Date.valueOf("2025-04-20"),
//	        5,
//	        "fitness_tracker.apk",
//	        "Aplicación para registrar tus entrenamientos, pasos y calorías diarias.",
//	        new BigDecimal("2.5")
//	    );
//
//	    when(entityManager.find(Content.class, 2L)).thenReturn(content2);
//
//	    Content result = jpaContentRepository.read(2L);
//
//	    Assertions.assertEquals(2L, result.getId());
//	    Assertions.assertEquals("Fitness Tracker", result.getName());
//	    Assertions.assertEquals("https://example.com/images/fitness_tracker.png", result.getImage());
//	    Assertions.assertEquals("fitness_tracker.apk", result.getFileName());
//	    Assertions.assertEquals("Aplicación para registrar tus entrenamientos, pasos y calorías diarias.", result.getDescription());
//	    Assertions.assertEquals(new BigDecimal("2.5"), result.getVersion());
//	    Assertions.assertEquals(5, result.getRating());
//	}
//
//	
//	@Test
//	public void updateTest() throws ItemNotFoundException {
//		
//		Long id = 1L;
//		Content existingContent = new Content();
//		existingContent.setId(id);
//		Content updatedContent = new Content();
//		updatedContent.setName("New Name");
//		
//		when(entityManager.find(Content.class,id)).thenReturn(existingContent);
//		when(entityManager.merge(any(Content.class))).thenReturn(updatedContent);
//		
//		Content result = jpaContentRepository.update(id, updatedContent);
//		
//	}
//	
//	@Test
//	public void createTest() throws ConflictException {
//	    Category category = new Category();
//	    category.setCategoryName("Photography");
//
//	    Content content1 = new Content(
//	        null,
//	        "Photo Editor Pro",
//	        "https://example.com/images/photo_editor.png",
//	        new User(),
//	        List.of(category),
//	        new BigDecimal("4.99"),
//	        List.of(new Download()),
//	        Date.valueOf("2025-01-10"),
//	        Date.valueOf("2025-05-10"),
//	        4,
//	        "photo_editor_pro.apk",
//	        "Una potente herramienta de edición de fotos con filtros avanzados y efectos.",
//	        new BigDecimal("3.1")
//	    );
//
//	    doAnswer(invocation -> {
//	        Content c = invocation.getArgument(0);
//	        c.setId(10L); // Simula que la BD asigna el ID
//	        return null;
//	    }).when(entityManager).persist(any(Content.class));
//
//	    Content result = jpaContentRepository.create(content1);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(10L, content1.getId());
//	    Assertions.assertEquals("Photo Editor Pro", content1.getName());
//	    Assertions.assertEquals("photo_editor_pro.apk", content1.getFileName());
//	    Assertions.assertEquals("Una potente herramienta de edición de fotos con filtros avanzados y efectos.", content1.getDescription());
//	    Assertions.assertEquals(new BigDecimal("3.1"), content1.getVersion());
//	    Assertions.assertEquals(4, content1.getRating());
//
//	    verify(entityManager).persist(content1);
//	}
//
//	
//	
//	@Test
//	public void deleteTest() throws ItemNotFoundException {
//	    Long id = 10L;
//	    Category category = new Category();
//	    category.setCategoryName("Productivity");
//
//	    Content content = new Content(
//	        id,
//	        "Language Learner",
//	        "https://example.com/images/language_learner.png",
//	        new User(),
//	        List.of(category),
//	        new BigDecimal("0.00"),
//	        List.of(new Download()),
//	        Date.valueOf("2025-02-15"),
//	        Date.valueOf("2025-05-01"),
//	        3,
//	        "language_learner.apk",
//	        "App para aprender idiomas con ejercicios interactivos.",
//	        new BigDecimal("1.0")
//	    );
//
//	    when(entityManager.find(Content.class, id)).thenReturn(content);
//	    jpaContentRepository.delete(id);
//	    verify(entityManager).remove(content);
//	}
//
//	
//	@Test
//	public void existsTest() {
//	    Long id = 1L;
//
//	    Content content = new Content(
//	        3L,
//	        "Language Learner",
//	        "https://example.com/images/language_learner.png",
//	        new User(),
//	        List.of(new Category()),
//	        new BigDecimal("0.00"),
//	        List.of(new Download()),
//	        Date.valueOf("2025-02-15"),
//	        Date.valueOf("2025-05-01"),
//	        3,
//	        "language_learner.apk",
//	        "App para aprender idiomas con ejercicios interactivos.",
//	        new BigDecimal("1.0")
//	    );
//
//	    when(entityManager.find(Content.class, id)).thenReturn(content);
//	    boolean result = jpaContentRepository.exists(id);
//	    Assertions.assertTrue(result);
//	    verify(entityManager).find(Content.class, id);
//	}
//
//	
//	@Test
//	public void notExistsTest() {
//		
//		Long id = 2L;
//		
//		when(entityManager.find(Content.class, id)).thenReturn(null);
//		
//		boolean result = jpaContentRepository.exists(id);
//		Assertions.assertFalse(result);
//		verify(entityManager).find(Content.class, id);
//		
//		
//	}
//	
//	@Test
//	public void findByImageContainingTest() {
//	    String imageUrl = "language_learner.png";
//
//	    List<Content> expectedImage = List.of(
//	        new Content(
//	            3L,
//	            "Language Learner",
//	            "https://example.com/images/language_learner.png",
//	            new User(),
//	            List.of(new Category()),
//	            new BigDecimal("0.00"),
//	            List.of(new Download()),
//	            Date.valueOf("2025-02-15"),
//	            Date.valueOf("2025-05-01"),
//	            3,
//	            "language_learner.apk",
//	            "App para aprender idiomas con ejercicios interactivos.",
//	            new BigDecimal("1.0")
//	        )
//	    );
//
//	    TypedQuery<Content> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(Content.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter(eq("imageURL"), eq("%" + imageUrl + "%"))).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedImage);
//
//	    List<Content> result = jpaContentRepository.findByImageContaining(imageUrl);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(1, result.size());
//	    Assertions.assertTrue(result.get(0).getImage().contains(imageUrl));
//
//	    verify(entityManager).createQuery(anyString(), eq(Content.class));
//	    verify(mockQuery).setParameter("imageURL", "%" + imageUrl + "%");
//	    verify(mockQuery).getResultList();
//	}
//
//	
//	@Test
//	public void findByDeveloperNameTest() {
//	    String developerName = "John Dev";
//
//	    List<Content> expectedContent = List.of(
//	        new Content(
//	            1L,
//	            "App One",
//	            "https://example.com/images/app_one.png",
//	            new User(),
//	            List.of(new Category()),
//	            new BigDecimal("1.99"),
//	            List.of(new Download()),
//	            Date.valueOf("2025-01-01"),
//	            Date.valueOf("2025-05-01"),
//	            5,
//	            "app_one.apk",
//	            "Aplicación de productividad para organizar tareas.",
//	            new BigDecimal("1.2")
//	        )
//	    );
//
//	    TypedQuery<Content> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(Content.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter("developerName", developerName)).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedContent);
//
//	    List<Content> result = jpaContentRepository.findByDeveloperName(developerName);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(1, result.size());
//	    verify(entityManager).createQuery(anyString(), eq(Content.class));
//	    verify(mockQuery).setParameter("developerName", developerName);
//	    verify(mockQuery).getResultList();
//	}
//
//	@Test
//	public void findByDeveloperIdTest() {
//	    Long developerId = 42L;
//
//	    List<Content> expectedContent = List.of(
//	        new Content(
//	            2L,
//	            "App Two",
//	            "https://example.com/images/app_two.png",
//	            new User(),
//	            List.of(new Category()),
//	            new BigDecimal("2.99"),
//	            List.of(new Download()),
//	            Date.valueOf("2025-02-01"),
//	            Date.valueOf("2025-06-01"),
//	            4,
//	            "app_two.apk",
//	            "App de seguimiento de hábitos saludables.",
//	            new BigDecimal("2.0")
//	        )
//	    );
//
//	    TypedQuery<Content> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(Content.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter("developerId", developerId)).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedContent);
//
//	    List<Content> result = jpaContentRepository.findByDeveloperId(developerId);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(1, result.size());
//	    verify(entityManager).createQuery(anyString(), eq(Content.class));
//	    verify(mockQuery).setParameter("developerId", developerId);
//	    verify(mockQuery).getResultList();
//	    
//	}
//	
//}
