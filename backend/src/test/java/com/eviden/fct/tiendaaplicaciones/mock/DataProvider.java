//package com.eviden.fct.tiendaaplicaciones.mock;
//
//import java.math.BigDecimal;
//import java.sql.Date;
//import java.time.LocalDate;
//import java.util.List;
//
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Category;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Content;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Download;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Review;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.Role;
//import com.eviden.fct.tiendaaplicaciones.domain.entities.User;
//
//public class DataProvider {
//	
//	public static List<Category> categoryListMock(){
//		
//		System.out.println("--> Getting List of Categories");
//		
//		return  List.of(
//	            new Category(1L, "Photography", "Apps focused on editing, organizing, and sharing photos.", new Content()),
//	            new Category(2L, "Health", "Applications that help users track fitness, diet, and wellness goals.", new Content()),
//	            new Category(3L, "Education", "Educational tools and platforms for learning new languages or skills.", new Content()),
//	            new Category(4L, "Finance", "Apps designed to manage personal budgets, expenses, and savings.", new Content()),
//	            new Category(5L, "Games", "Interactive and entertaining games for all age groups.", new Content()),
//	            new Category(6L, "Food & Drink", "Applications offering recipes, cooking tips, and meal planning.", new Content())
//	        );
//		
//	}
//	
//	public static List<User> userListMock(){
//		
//		return  List.of(
//				new User(1L, "jdoe", "John", "Doe", "jdoe@example.com", Role .USER, "password123", 
//				         Date.valueOf(LocalDate.now().minusDays(10)), Date.valueOf(LocalDate.now())
//				         ),
//
//				new User(2L, "asmith", "Alice", "Smith", "asmith@example.com", Role.ADMIN, "securePass456", 
//				         Date.valueOf(LocalDate.now().minusDays(20)), Date.valueOf(LocalDate.now())
//				         ),
//
//				new User(3L, "bwayne", "Bruce", "Wayne", "bwayne@waynecorp.com", Role.USER, "batmanPass789", 
//				         Date.valueOf(LocalDate.now().minusDays(30)), Date.valueOf(LocalDate.now())
//				         ),
//
//				new User(4L, "ckent", "Clark", "Kent", "ckent@dailyplanet.com", Role.GUEST, "superman321", 
//				         Date.valueOf(LocalDate.now().minusDays(40)), Date.valueOf(LocalDate.now())
//				         ),
//
//				new User(5L, "dprince", "Diana", "Prince", "dprince@themiscira.com", Role.USER, "wonderWoman!23", 
//				         Date.valueOf(LocalDate.now().minusDays(15)), Date.valueOf(LocalDate.now())
//				         ),
//
//				new User(6L, "pparker", "Peter", "Parker", "pparker@dailybugle.com", Role.ADMIN, "sp1derm@n!", 
//				         Date.valueOf(LocalDate.now().minusDays(25)), Date.valueOf(LocalDate.now())
//				         )
//	        );
//		
//	}
//	
//	public static List<Content> contentListMock(){
//		return List.of(
//			    new Content(
//			        1L,
//			        "Photo Editor Pro",
//			        "https://example.com/images/photo_editor.png",
//			        new User(),
//			        List.of(new Category()),
//			        new BigDecimal("4.99"),
//			        List.of(new Download()),
//			        Date.valueOf("2025-01-10"),
//			        Date.valueOf("2025-05-10"),
//			        4,
//			        "photo_editor_pro.apk",
//			        "Una potente herramienta de edición de fotos con filtros avanzados y efectos.",
//			        new BigDecimal("3.1")
//			    ),
//			    new Content(
//			        2L,
//			        "Fitness Tracker",
//			        "https://example.com/images/fitness_tracker.png",
//			        new User(),
//			        List.of(new Category()),
//			        new BigDecimal("2.99"),
//			        List.of(new Download()),
//			        Date.valueOf("2024-12-01"),
//			        Date.valueOf("2025-04-20"),
//			        5,
//			        "fitness_tracker.apk",
//			        "Aplicación para registrar tus entrenamientos, pasos y calorías diarias.",
//			        new BigDecimal("2.5")
//			    ),
//			    new Content(
//			        3L,
//			        "Language Learner",
//			        "https://example.com/images/language_learner.png",
//			        new User(),
//			        List.of(new Category()),
//			        new BigDecimal("0.00"),
//			        List.of(new Download()),
//			        Date.valueOf("2025-02-15"),
//			        Date.valueOf("2025-05-01"),
//			        3,
//			        "language_learner.apk",
//			        "Aprende nuevos idiomas con lecciones interactivas y juegos educativos.",
//			        new BigDecimal("1.8")
//			    )
//			);
//
//	}
//	
//	public static List<Review> reviewListMock() {
//		
//	    Content mockContent = new Content(); 
//	    User mockUser = new User(); 
//	    return List.of(
//	        new Review(
//	            1L,
//	            mockContent,
//	            mockUser,
//	            Date.valueOf("2025-05-01"),
//	            "Excelente aplicación, muy útil para el día a día."
//	        ),
//	        new Review(
//	            2L,
//	            mockContent,
//	            mockUser,
//	            Date.valueOf("2025-05-10"),
//	            "Buena experiencia, aunque podría mejorar el diseño."
//	        ),
//	        new Review(
//	            3L,
//	            mockContent,
//	            mockUser,
//	            Date.valueOf("2025-05-15"),
//	            "No me funcionó correctamente en mi dispositivo."
//	        )
//	    );
//	    
//	}
//
//
//}
