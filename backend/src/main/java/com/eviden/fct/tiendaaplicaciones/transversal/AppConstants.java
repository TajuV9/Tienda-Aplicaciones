package com.eviden.fct.tiendaaplicaciones.transversal;

public interface AppConstants {

	// Error messages
	String ERRORS_GENERIC_BAD_REQUEST = "Los datos introducidos no son válidos.";
	String ERRORS_GENERIC_UNAUTHORIZED = "Debes iniciar sesión para acceder.";
	String ERRORS_GENERIC_FORBIDDEN = "No dispones de los permisos requeridos para acceder.";
	String ERRORS_GENERIC_NOT_FOUND = "No se ha encontrado el elemento solicitado.";
	String ERRORS_GENERIC_METHOD_NOT_ALLOWED = "El método utilizado no está permitido.";
	String ERRORS_GENERIC_CONFLICT = "Se ha producido un error inesperado. Vuelva a intentarlo.";
	String ERRORS_GENERIC_LOCKED = "El elemento solicitado no se encuentra disponible.";
	String ERRORS_GENERIC_UNAVAILABLE_LEGAL_REASONS = "El elemento solicitado no se encuentra disponible por motivos legales.";
	String ERRORS_GENERIC_INTERNAL_SERVER_ERROR = "Se ha producido un error inesperado. Vuelva a intentarlo.";
	
	String ERRORS_VALIDATION_FIELD_ERROR = "El campo '%s' %s.";
	
	String ERRORS_AUTH_SESSION_NOT_FOUND = "Se ha producido un error al recuperar sus datos. Por favor, vuelva a iniciar sesión.";
	String ERRORS_AUTH_EXPIRED_SESSION = "Su sesión ha expirado.";
	String ERRORS_AUTH_EXPIRED_ACCOUNT = "Su cuenta ya no es válida.";
	String ERRORS_AUTH_EXPIRED_CREDENTIALS = "Sus credenciales han expirado.";
	String ERRORS_AUTH_LOCKED_ACCOUNT = "Su cuenta se encuentra bloqueada.";
	String ERRORS_AUTH_DISABLED_ACCOUNT = "Su cuenta no se encuentra activa.";
	String ERRORS_AUTH_BAD_CREDENTIALS = "El nombre de usuario o contraseña no son válidos.";
	
	String ERRORS_PROFILE_USERNAME_DUPLICATED = "El nombre de usuario se encuentra en uso.";
	String ERRORS_PROFILE_EMAIL_DUPLICATED = "El correo electrónico se encuentra en uso.";
	String ERRORS_PROFILE_ALREADY_CREATOR = "Ya eres un creador.";
	String ERRORS_PROFILE_CHANGE_PASSWORD_WRONG_PASSWORD = "La contraseña actual no es correcta.";
	String ERRORS_PROFILE_CHANGE_PASSWORD_PASSWORD_MATCH = "La nueva contraseña no puede ser igual a la anterior.";
	
	String ERRORS_CONTENT_NAME_DUPLICATED = "Ya existe un contenido con ese nombre.";
	
	String ERRORS_REVIEW_ALREADY_REVIEWED = "Ya has publicado una reseña de este contenido.";
	
	String ERRORS_FILE_NOT_IMAGE = "El archivo proporcionado no es una imagen válida.";
	String ERRORS_FILE_NOT_VIDEO = "El archivo proporcionado no es un vídeo válido. Solo se admiten vídeos en formato MP4.";
	String ERRORS_FILE_NOT_VALID = "El archivo proporcionado no tiene un formato válido.";
	
	// Success messages
	String SUCCESS_PROFILE_CREATED = "Se ha creado su cuenta con éxito. Ya puede iniciar sesión usando sus credenciales.";
	String SUCCESS_PROFILE_PASSWORD_CHANGED = "Se ha actualizado su contraseña con éxito.";
	String SUCCESS_PROFILE_BECAME_CREATOR = "Ahora es un creador. Ya puede comenzar a subir contenido.";
	
	String SUCCESS_CONTENT_UPDATED = "Se ha actualizado el contenido con éxito.";
	String SUCCESS_CONTENT_DELETED = "Se ha eliminado el contenido con éxito.";
	String SUCCESS_CONTENT_MEDIA_DELETED = "Se ha eliminado el archivo multimedia con éxito.";
	
	String SUCCESS_REVIEW_DELETED = "Se ha eliminado la reseña con éxito.";
	
	// Paths
	String PATH_CONTENT_ICON = "%s/v1/content/%s/icon";
	String PATH_CONTENT_FILE = "%s/v1/content/%s/file";
	String PATH_CONTENT_MEDIA = "%s/v1/content-media/%s";
}
