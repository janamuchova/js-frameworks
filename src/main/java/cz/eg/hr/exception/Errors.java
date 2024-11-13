package cz.eg.hr.exception;

import java.util.List;

public record Errors(List<ValidationError> errors) {
}
