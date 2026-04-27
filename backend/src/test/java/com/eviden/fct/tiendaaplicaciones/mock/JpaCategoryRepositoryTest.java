//package com.eviden.fct.tiendaaplicaciones.mock;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import static org.mockito.Mockito.*;
//
//
//import java.util.List;
//
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
//import com.eviden.fct.tiendaaplicaciones.persistance.repositories.JpaCategoryRepository;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ItemNotFoundException;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//
//public class JpaCategoryRepositoryTest {
//	
//	// Este test verifica que el método read() del repositorio simulado (mock) devuelve 
//	//correctamente una lista de objetos Category
//	// con los valores esperados en sus campos. También comprueba que cada categoría tiene 
//	//una instancia válida de Content asociada
//	@Mock
//	private EntityManager entityManager;
//	
//	@InjectMocks
//	private JpaCategoryRepository jpaCategoryRepository;
//
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void readTest() {
//
//		TypedQuery<Category> mockQuery = mock(TypedQuery.class);
//		when(entityManager.createQuery(anyString(), eq(Category.class))).thenReturn(mockQuery);
//		when(mockQuery.getResultList()).thenReturn(DataProvider.categoryListMock());
//		
//		List<Category> result = jpaCategoryRepository.read();
//		
//		Assertions.assertEquals(6, result.size());
//
//
//	    Assertions.assertEquals("Photography", result.get(0).getCategoryName());
//	    Assertions.assertEquals("Apps focused on editing, organizing, and sharing photos.", result.get(0).getDescription());
//
//	    Assertions.assertEquals("Health", result.get(1).getCategoryName());
//	    Assertions.assertEquals("Applications that help users track fitness, diet, and wellness goals.", result.get(1).getDescription());
//
//	    Assertions.assertEquals("Education", result.get(2).getCategoryName());
//	    Assertions.assertEquals("Educational tools and platforms for learning new languages or skills.", result.get(2).getDescription());
//
//	    Assertions.assertEquals("Finance", result.get(3).getCategoryName());
//	    Assertions.assertEquals("Apps designed to manage personal budgets, expenses, and savings.", result.get(3).getDescription());
//
//	    Assertions.assertEquals("Games", result.get(4).getCategoryName());
//	    Assertions.assertEquals("Interactive and entertaining games for all age groups.", result.get(4).getDescription());
//
//	    Assertions.assertEquals("Food & Drink", result.get(5).getCategoryName());
//	    Assertions.assertEquals("Applications offering recipes, cooking tips, and meal planning.", result.get(5).getDescription());
//
//	    // Verifica que los contenidos no sean null
//	    result.forEach(category -> Assertions.assertNotNull(category.getContent()));
//	}
//	
//
//	// Este test verifica que el método read(id) del repositorio simulado devuelve correctamente un objeto Category
//	// con los valores esperados, incluyendo su relación con un objeto Content.
//	@Test
//	public void readByIdTest() throws ItemNotFoundException {
//		
//		Category mockCategory = new Category(1L, "Photography",
//				"Apps focused on editing, organizing, and sharing photos.", 
//				new Content()
//				);
//		
//		when(entityManager.find(Category.class,1L)).thenReturn(mockCategory);
//		
//		Category result = jpaCategoryRepository.read(1L);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(1L,result.getId());
//		Assertions.assertEquals("Photography", result.getCategoryName());
//		Assertions.assertEquals("Apps focused on editing, organizing, and sharing photos.", result.getDescription());
//		Assertions.assertNotNull(result.getContent());
//	
//	}
//	
//
//	// Este test verifica que el método create(category) del repositorio real persiste correctamente
//	// una nueva categoría usando EntityManager, y que se asigna un ID simulado como lo haría la base de datos
//
//	@Test
//	public void createTest() throws ConflictException {
//		
//		
//		Content content  = new Content();
//		Category category = new Category(
//				null,
//				"Productivity",
//				"Apps that help users manage tasks, time, and work efficiently.",
//				content
//				);
//		
//		doAnswer(invocation -> {Category c = invocation.getArgument(0);
//			c.setId(10L); // Simula que la BD asigna el ID
//			return null;
//		}).when(entityManager).persist(any(Category.class));
//
//		Category result = jpaCategoryRepository.create(category);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(10L, result.getId());
//		Assertions.assertEquals("Productivity", result.getCategoryName());
//		Assertions.assertEquals("Apps that help users manage tasks, time, and work efficiently.", result.getDescription());
//		Assertions.assertEquals(content, result.getContent());
//		
//		verify(entityManager).persist(category);
//				
//	}
//	
//
//	// Este test verifica que el método update(id, updatedCategory) del repositorio actualiza correctamente
//	// una entidad Category existente. Simula la búsqueda con entityManager.find() y la actualización con entityManager.merge(),
//	// y comprueba que el nombre de la categoría se actualiza correctamente y que el ID original se conserva.
//	@Test
//	public void updateTest() throws ItemNotFoundException {
//		
//		Long id = 1L;
//		Category existingCategory = new Category();
//		existingCategory.setId(id);
//		Category updatedCategory = new Category();
//		updatedCategory.setCategoryName("New Name");
//		
//		when(entityManager.find(Category.class,id)).thenReturn(existingCategory);
//		when(entityManager.merge(any(Category.class))).thenReturn(updatedCategory);
//		
//		Category result = jpaCategoryRepository.update(id, updatedCategory);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals("New Name", result.getCategoryName());
//		Assertions.assertEquals(id, updatedCategory.getId());
//		verify(entityManager).merge(updatedCategory);
//		
//	}
//	
//	// Este test verifica que el método delete(id) del repositorio elimina correctamente una entidad Category existente.
//	// Simula la búsqueda con entityManager.find() y comprueba que entityManager.remove() se invoca con la entidad encontrada.
//	@Test
//	public void deleteTest() throws ItemNotFoundException {
//		
//		Long id = 10L;
//		Content content  = new Content();
//		Category category = new Category(
//				id,
//				"Productivity",
//				"Apps that help users manage tasks, time, and work efficiently.",
//				content
//				);
//		
//		
//		when(entityManager.find(Category.class, 10L)).thenReturn(category);
//		jpaCategoryRepository.delete(id);
//		verify(entityManager).remove(category);
//		
//		
//	}
//	
// 
//	// Estos tests verifican que el método exists(id) devuelve true si la entidad existe en la base de datos,
//	// y false si no existe, utilizando entityManager
//	@Test
//	public void existsTest() {
//		
//		Long id = 1L;
//		Content content = new Content();
//		Category category = new Category(
//				id,
//				"Productivity",
//				"Apps that help users manage tasks, time, and work efficiently.",
//				content
//				);
//		
//		when(entityManager.find(Category.class, id)).thenReturn(category);
//		boolean result = jpaCategoryRepository.exists(id);
//		Assertions.assertTrue(result);
//		verify(entityManager).find(Category.class, id);
//		
//	}
//	
//	
//	@Test
//	public void notExistsTest() {
//		
//		Long id = 2L;
//		
//		when(entityManager.find(Category.class, id)).thenReturn(null);
//		
//		boolean result = jpaCategoryRepository.exists(id);
//		Assertions.assertFalse(result);
//		verify(entityManager).find(Category.class, id);
//		
//	}
//	
//	
//	// Este test verifica que el método findByCategoryNameContaining(subString) construye correctamente
//	// la consulta JPQL con LIKE y devuelve una lista de categorías cuyo nombre contiene el texto indicado.
//	//Las mayusculas y minusculas deben coincidir
//	@Test
//	public void findByCategoryNameContainingTest() {
//		
//	    String subString = "Prod";
//
//	    // Simula el resultado esperado
//	    List<Category> expectedCategories = List.of(
//	        new Category(1L, "Productivity", "Apps for work", new Content()),
//	        new Category(2L, "Production", "Apps for media", new Content())
//	    );
//
//	    // Simula el TypedQuery
//	    TypedQuery<Category> mockQuery = mock(TypedQuery.class);
//
//	    // Configura el comportamiento del EntityManager y del TypedQuery
//	    when(entityManager.createQuery(anyString(), eq(Category.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter(eq("subString"), eq("%" + subString + "%"))).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedCategories);
//
//	    // Ejecuta el método real
//	    List<Category> result = jpaCategoryRepository.findByCategoryNameContaining(subString);
//
//	    // Verificaciones
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(2, result.size());
//	    Assertions.assertTrue(result.get(0).getCategoryName().contains(subString));
//	    verify(entityManager).createQuery(anyString(), eq(Category.class));
//	    verify(mockQuery).setParameter("subString", "%" + subString + "%");
//	    verify(mockQuery).getResultList();
//	}
//
//
//}
