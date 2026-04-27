# Inserts para Postgres
-Puede dar error por los ids solo hay que cambiarlos por los existentes
-Primero deben insertarse los datos de app_users
```sql
//Inserts para Content

INSERT INTO content (
    category, created_at, description, file_name, image, content_name,
    price, rating, developer_id, updated_at, state, version
) VALUES
(2, '2025-05-01', 'Editor de fotos con filtros y efectos avanzados.', 'photo_editor_v1.apk', true, 'Photo Editor Pro', 4.99, 4, 1, '2025-05-20', 1, 1),
(0, '2025-04-15', 'Juego de estrategia en tiempo real.', 'warzone_v2.apk', true, 'WarZone RTS', 0.00, 5, 2, '2025-05-10', 1, 1),
(1, '2025-03-10', 'Reproductor de música con ecualizador.', 'music_player.apk', false, 'BeatBox Music', 1.99, 3, 3, '2025-05-05', 1, 1),
(3, '2025-02-20', 'Lector de libros electrónicos con modo oscuro.', 'ebook_reader.apk', true, 'ReadEasy', 2.49, 4, 4, '2025-05-01', 1, 1),
(2, '2025-01-30', 'Editor de video con transiciones y efectos.', 'video_editor.apk', false, 'ClipMaster', 3.99, 5, 5, '2025-05-15', 1, 1),
(0, '2025-04-01', 'Juego de carreras con gráficos realistas.', 'speed_racer.apk', false, 'Speed Racer X', 0.00, 4, 6, '2025-05-18', 1, 1),
(1, '2025-03-25', 'Aplicación para crear listas de reproducción.', 'playlist_maker.apk', true, 'Playlist Maker', 0.99, 3, 7, '2025-05-12', 1, 1),
(3, '2025-02-10', 'Biblioteca de libros clásicos gratuitos.', 'classic_books.apk', true, 'Classic Reads', 0.00, 5, 8, '2025-05-08', 1, 1),
(2, '2025-01-15', 'App para grabar y editar videos cortos.', 'shorts_creator.apk', true, 'Shorts Creator', 2.99, 4, 9, '2025-05-03', 1, 1),
(0, '2025-04-05', 'Juego de puzzles con niveles desafiantes.', 'puzzle_master.apk', false, 'Puzzle Master', 1.49, 4, 10, '2025-05-22', 1, 1);



//Inserts para User

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('alice01', 'Alice', 'Johnson', 'alice.johnson@example.com', 'USER', 'password123', '2025-01-10', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('bob_admin', 'Bob', 'Smith', 'bob.smith@example.com', 'ADMIN', 'adminpass456', '2025-02-15', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('charlie_guest', 'Charlie', 'Brown', 'charlie.brown@example.com', 'GUEST', 'guestpass789', '2025-03-01', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('diana_dev', 'Diana', 'Prince', 'diana.prince@example.com', 'USER', 'wonderwoman321', '2025-01-25', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('edward_user', 'Edward', 'Norton', 'edward.norton@example.com', 'USER', 'fightclub654', '2025-04-10', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('fiona_admin', 'Fiona', 'Gallagher', 'fiona.g@example.com', 'ADMIN', 'shameless987', '2025-03-20', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('george_guest', 'George', 'Miller', 'george.miller@example.com', 'GUEST', 'madmax111', '2025-02-05', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('hannah_user', 'Hannah', 'Baker', 'hannah.b@example.com', 'USER', '13reasons222', '2025-01-30', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('ian_dev', 'Ian', 'Curtis', 'ian.curtis@example.com', 'USER', 'joydivision333', '2025-04-01', '2025-05-20');

INSERT INTO app_users (user_name, name, last_name, email, role, password, created_at, updated_at)
VALUES ('julia_guest', 'Julia', 'Roberts', 'julia.roberts@example.com', 'GUEST', 'prettywoman444', '2025-03-10', '2025-05-20');


//Inserts para Review

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (2, 2, '2025-05-10', 'Muy buena app para editar fotos, fácil de usar y con muchas funciones.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (3, 3, '2025-05-11', 'Me ayudó a organizar mis tareas diarias. Muy recomendable.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (5, 4, '2025-05-12', 'Buena para hacer seguimiento de mis entrenamientos, aunque podría mejorar.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (6, 5, '2025-05-13', 'Excelente para aprender idiomas, muy interactiva.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (7, 6, '2025-05-14', 'Interfaz atractiva pero se cierra a veces. Necesita mejoras.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (8, 7, '2025-05-15', 'Ideal para llevar un control de mis gastos. Muy útil.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (9, 8, '2025-05-16', 'Recetas variadas y fáciles de seguir. Me encanta.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (10, 9, '2025-05-17', 'Perfecta para meditar y relajarse. Muy recomendable.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (11, 10, '2025-05-18', 'Buena guía de viaje, aunque faltan algunas ciudades.');

INSERT INTO review (content_id, user_id, created_at, review)
VALUES (1, 1, '2025-05-19', 'Me ayuda a organizar mis estudios. Muy práctica.');

//Inserts para Download

INSERT INTO download (user_id, content_id, created_at)
VALUES (1, 3, '2025-05-10');

INSERT INTO download (user_id, content_id, created_at)
VALUES (2, 4, '2025-05-11');

INSERT INTO download (user_id, content_id, created_at)
VALUES (3, 5, '2025-05-12');

INSERT INTO download (user_id, content_id, created_at)
VALUES (4, 6, '2025-05-13');

INSERT INTO download (user_id, content_id, created_at)
VALUES (5, 7, '2025-05-14');

INSERT INTO download (user_id, content_id, created_at)
VALUES (6, 8, '2025-05-15');

INSERT INTO download (user_id, content_id, created_at)
VALUES (7, 9, '2025-05-16');

INSERT INTO download (user_id, content_id, created_at)
VALUES (8, 10, '2025-05-17');

INSERT INTO download (user_id, content_id, created_at)
VALUES (9, 11, '2025-05-18');

INSERT INTO download (user_id, content_id, created_at)
VALUES (10, 1, '2025-05-19');

//Inserts para Report
INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (1, 1, 1, 'Description for report 1', '2022-01-01', '2022-02-01');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (2, 2, 2, 'Description for report 2', '2022-01-02', '2022-02-02');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (3, 3, 3, 'Description for report 3', '2022-01-03', '2022-02-03');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (4, 4, 4, 'Description for report 4', '2022-01-04', '2022-02-04');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (5, 5, 5, 'Description for report 5', '2022-01-05', '2022-02-05');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (6, 6, 6, 'Description for report 6', '2022-01-06', '2022-02-06');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (7, 7, 7, 'Description for report 7', '2022-01-07', '2022-02-07');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (8, 8, 8, 'Description for report 8', '2022-01-08', '2022-02-08');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (0, 9, 9, 'Description for report 9', '2022-01-09', '2022-02-09');

INSERT INTO report (report_type, content, user_id, description, created_at, updated_at) 
VALUES (1, 10, 10, 'Description for report 10', '2022-01-10', '2022-02-10');

```
