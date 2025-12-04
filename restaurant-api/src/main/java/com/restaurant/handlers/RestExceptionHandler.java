package com.restaurant.handlers;

import com.restaurant.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Tratamento Global de Exceções. 
 * Mapeia exceções Java para Status Codes HTTP padronizados.
 */
@ControllerAdvice
public class RestExceptionHandler { 

    // Record para a resposta de erro padronizada
    public record ErrorResponse(int status, String error, String message, String timestamp) {}

    // Auxiliar para construir o corpo da resposta
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String error, String message) {
        ErrorResponse errorBody = new ErrorResponse(
            status.value(),
            error,
            message,
            LocalDateTime.now().toString()
        );
        return ResponseEntity.status(status).body(errorBody);
    }

    // --- Handler para 400 Bad Request (Erros de Validação @Valid) ---
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex) {

        // Constrói uma mensagem única com todos os erros de campo
        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation Error", message);
    }

    // --- Handler para 404 Not Found ---
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage());
    }

    // --- Handler para 400 Bad Request (Erros de lógica de negócio simples) ---
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage());
    }

    // --- Handler para 422 Unprocessable Entity (Erros de Regras de Negócio) ---
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(BusinessException ex) {
        // HttpStatus.UNPROCESSABLE_ENTITY (422) é um código de erro HTTP padrão para falhas de validação de negócios.
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity", ex.getMessage());
    }

    // --- Handler para 409 Conflict ---
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        return buildResponse(HttpStatus.CONFLICT, "Conflict", ex.getMessage());
    }

    // --- Handler para 401 Unauthorized ---
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(UnauthorizedException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.getMessage());
    }
    
    // --- Handler para 500 Internal Server Error (Erros de Persistência) ---
    @ExceptionHandler(InvalidDatabaseActionException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseAction(InvalidDatabaseActionException ex) {
        // Erros de banco de dados que não são controlados (como um bloqueio ou falha de conexão) 
        // devem ser tratados como um erro interno do servidor.
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "Ocorreu um erro interno de persistência.");
    }
}