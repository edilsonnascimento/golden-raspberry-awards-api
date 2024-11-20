package org.enascimento.api.exception.handler;

import org.enascimento.api.exception.*;
import org.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    ProblemDetail handleBusinessException(BusinessException e) {
        LOG.warn("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    ProblemDetail handleNotFoundException(NotFoundException e) {
        LOG.warn("{}",  e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ProblemDetail handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        LOG.warn("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.BAD_REQUEST, "INVALID_REQUEST_ARGUMENTS");
    }

    @ExceptionHandler(NullPointerException.class)
    ProblemDetail handleNullPointerException(NullPointerException e) {
        LOG.error("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "APPLICATION_INTERNAL_ERROR");
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        LOG.error("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC_ERROR");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    ProblemDetail handleNoHandlerFoundException(NoHandlerFoundException e) {
        LOG.error("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    ProblemDetail handleMethodArgumentNotValidException(MissingServletRequestParameterException e) {
        LOG.error("{}", e.getMessage(), e);
        return forStatusAndDetail(HttpStatus.BAD_REQUEST, "REQUIRED_FIELD_NOT_FOUND");
    }
}