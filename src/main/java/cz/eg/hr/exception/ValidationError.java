package cz.eg.hr.exception;

public record ValidationError(String field, String message) {
}
