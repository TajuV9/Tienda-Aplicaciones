//package com.eviden.fct.tiendaaplicaciones.mock;
//
//import org.junit.jupiter.api.Assertions;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
//import com.eviden.fct.tiendaaplicaciones.persistance.repositories.JpaUserRepository;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ConflictException;
//import com.eviden.fct.tiendaaplicaciones.transversal.exceptions.ItemNotFoundException;
//
//import static org.mockito.Mockito.*;
//
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.TypedQuery;
//
//public class JpaUserRepositoryTest {
//	
//	@Mock
//	private EntityManager entityManager;
//	
//	@InjectMocks
//	private JpaUserRepository jpaUserRepository;
//	
//	@BeforeEach
//	public void setUp() {
//		MockitoAnnotations.openMocks(this);
//	}
//	
//	@Test
//	public void readTest() {
//		
//		TypedQuery<User> mockQuery = mock(TypedQuery.class);
//		when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
//		when(mockQuery.getResultList()).thenReturn(DataProvider.userListMock());
//		
//		List<User> users = jpaUserRepository.read();
//		
//		Assertions.assertEquals(6, users.size());
//		
//		Assertions.assertEquals("jdoe", users.get(0).getUserName());
//		Assertions.assertEquals("John", users.get(0).getName());
//		Assertions.assertEquals("Doe", users.get(0).getLastName());
//		Assertions.assertEquals(Role.USER, users.get(0).getRole());
//		Assertions.assertEquals("password123", users.get(0).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(10)), users.get(0).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(0).getUpdatedAt());
//
//		Assertions.assertEquals("asmith", users.get(1).getUserName());
//		Assertions.assertEquals("Alice", users.get(1).getName());
//		Assertions.assertEquals("Smith", users.get(1).getLastName());
//		Assertions.assertEquals(Role.ADMIN, users.get(1).getRole());
//		Assertions.assertEquals("securePass456", users.get(1).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(20)), users.get(1).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(1).getUpdatedAt());
//
//		Assertions.assertEquals("bwayne", users.get(2).getUserName());
//		Assertions.assertEquals("Bruce", users.get(2).getName());
//		Assertions.assertEquals("Wayne", users.get(2).getLastName());
//		Assertions.assertEquals(Role.USER, users.get(2).getRole());
//		Assertions.assertEquals("batmanPass789", users.get(2).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(30)), users.get(2).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(2).getUpdatedAt());
//
//		Assertions.assertEquals("ckent", users.get(3).getUserName());
//		Assertions.assertEquals("Clark", users.get(3).getName());
//		Assertions.assertEquals("Kent", users.get(3).getLastName());
//		Assertions.assertEquals(Role.GUEST, users.get(3).getRole());
//		Assertions.assertEquals("superman321", users.get(3).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(40)), users.get(3).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(3).getUpdatedAt());
//
//		Assertions.assertEquals("dprince", users.get(4).getUserName());
//		Assertions.assertEquals("Diana", users.get(4).getName());
//		Assertions.assertEquals("Prince", users.get(4).getLastName());
//		Assertions.assertEquals(Role.USER, users.get(4).getRole());
//		Assertions.assertEquals("wonderWoman!23", users.get(4).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(15)), users.get(4).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(4).getUpdatedAt());
//
//		Assertions.assertEquals("pparker", users.get(5).getUserName());
//		Assertions.assertEquals("Peter", users.get(5).getName());
//		Assertions.assertEquals("Parker", users.get(5).getLastName());
//		Assertions.assertEquals(Role.ADMIN, users.get(5).getRole());
//		Assertions.assertEquals("sp1derm@n!", users.get(5).getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(25)), users.get(5).getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), users.get(5).getUpdatedAt());
//
//	}
//	
//	@Test
//	public void readByIdTest() throws ItemNotFoundException {
//		
//		User mockUser = new User(1L, "bwayne", "Bruce", "Wayne", "bwayne@waynecorp.com", Role.USER, "batmanPass789", 
//		         Date.valueOf(LocalDate.now().minusDays(30)), Date.valueOf(LocalDate.now())
//		         );
//		
//		when(entityManager.find(User.class, 1L)).thenReturn(mockUser);
//		
//		User result = jpaUserRepository.read(1L);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(1L, result.getId());
//		Assertions.assertEquals("bwayne", result.getUserName());
//		Assertions.assertEquals("Bruce", result.getName());
//		Assertions.assertEquals("Wayne", result.getLastName());
//		Assertions.assertEquals(Role.USER, result.getRole());
//		Assertions.assertEquals("batmanPass789", result.getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(30)), result.getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), result.getUpdatedAt());
//			
//	}
//	
//	@Test
//	public void createTest() throws ConflictException {
//		
//		User user = new User(null, "ckent", "Clark", "Kent", "ckent@dailyplanet.com", Role.GUEST, "superman321", 
//		         Date.valueOf(LocalDate.now().minusDays(40)), Date.valueOf(LocalDate.now())
//		         );
//		
//		doAnswer(invocation -> {User u = invocation.getArgument(0);
//			u.setId(10L);
//			return null;
//		}).when(entityManager).persist(any(User.class));
//		
//		User result = jpaUserRepository.create(user);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals(10L, result.getId());
//		Assertions.assertEquals("ckent", result.getUserName());
//		Assertions.assertEquals("Clark", result.getName());
//		Assertions.assertEquals("Kent", result.getLastName());
//		Assertions.assertEquals(Role.GUEST, result.getRole());
//		Assertions.assertEquals("superman321", result.getPassword());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now().minusDays(40)), result.getCreatedAt());
//		Assertions.assertEquals(Date.valueOf(LocalDate.now()), result.getUpdatedAt());
//		
//		verify(entityManager).persist(user);
//		
//	}
//	
//	@Test
//	public void updateTest() throws ItemNotFoundException {
//		
//		Long id = 1L;
//		User existingUser = new User();
//		existingUser.setId(id);
//		User updatedUser = new User();
//		updatedUser.setName("new Name");
//		
//		when(entityManager.find(User.class, id)).thenReturn(existingUser);
//		when(entityManager.merge(any(User.class))).thenReturn(updatedUser);
//		
//		User result = jpaUserRepository.update(id, updatedUser);
//		
//		Assertions.assertNotNull(result);
//		Assertions.assertEquals("new Name", result.getName());
//		Assertions.assertEquals(id, updatedUser.getId());
//		
//		verify(entityManager).merge(updatedUser);
//		
//	}
//	
//	@Test
//	public void deleteTest() throws ItemNotFoundException {
//		
//		Long id = 1L;
//		User user = new User(id, "jdoe", "John", "Doe", "jdoe@example.com", Role .USER, "password123", 
//		         Date.valueOf(LocalDate.now().minusDays(10)), Date.valueOf(LocalDate.now())
//		         );
//		
//		when(entityManager.find(User.class, 1L)).thenReturn(user);
//		jpaUserRepository.delete(id);
//		
//		verify(entityManager).remove(user);
//		
//	}
//	
//	@Test
//	public void existsTest() {
//		
//		Long id = 1L;
//		User user = new User(id, "asmith", "Alice", "Smith", "asmith@example.com", Role.ADMIN, "securePass456", 
//		         Date.valueOf(LocalDate.now().minusDays(20)), Date.valueOf(LocalDate.now())
//		         );
//		
//		when(entityManager.find(User.class, id)).thenReturn(user);
//		boolean result = jpaUserRepository.exists(id);
//		Assertions.assertTrue(result);
//		verify(entityManager).find(User.class, id);
//		
//	} 
//	
//	@Test
//	public void notExistsTest() {
//		
//		Long id = 2L;
//		
//		when(entityManager.find(User.class, id)).thenReturn(null);
//		
//		boolean result = jpaUserRepository.exists(id);
//		Assertions.assertFalse(result);
//		verify(entityManager).find(User.class, id);
//		
//	}
//	
//	@Test
//	public void findByUsernameTest() {
//	    String username = "johndoe";
//
//	    User mockUser = new User();
//	    mockUser.setId(1L);
//	    mockUser.setUserName(username);
//
//	    TypedQuery<User> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter("username", username)).thenReturn(mockQuery);
//	    when(mockQuery.getSingleResult()).thenReturn(mockUser);
//
//	    Optional<User> result = jpaUserRepository.findByUsernameOrEmail(username);
//
//	    Assertions.assertTrue(result.isPresent());
//	    Assertions.assertEquals(username, result.get().getUserName());
//
//	    verify(entityManager).createQuery(anyString(), eq(User.class));
//	    verify(mockQuery).setParameter("username", username);
//	    verify(mockQuery).getSingleResult();
//	}
//
//	
//	@Test
//	public void findByRolesTest() {
//	    String roleName = "ADMIN";
//
//	    User user1 = new User();
//	    user1.setId(1L);
//	    user1.setUserName("admin1");
//
//	    User user2 = new User();
//	    user2.setId(2L);
//	    user2.setUserName("admin2");
//
//	    List<User> expectedUsers = List.of(user1, user2);
//
//	    TypedQuery<User> mockQuery = mock(TypedQuery.class);
//
//	    when(entityManager.createQuery(anyString(), eq(User.class))).thenReturn(mockQuery);
//	    when(mockQuery.setParameter("roleName", roleName)).thenReturn(mockQuery);
//	    when(mockQuery.getResultList()).thenReturn(expectedUsers);
//
//	    List<User> result = jpaUserRepository.findByRoles(roleName);
//
//	    Assertions.assertNotNull(result);
//	    Assertions.assertEquals(2, result.size());
//	    Assertions.assertEquals("admin1", result.get(0).getUserName());
//	    Assertions.assertEquals("admin2", result.get(1).getUserName());
//
//	    verify(entityManager).createQuery(anyString(), eq(User.class));
//	    verify(mockQuery).setParameter("roleName", roleName);
//	    verify(mockQuery).getResultList();
//	}
//
//	
//	
//	
//}
