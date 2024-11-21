package org.enascimento.api.exception.handler;

import org.enascimento.api.exception.BusinessException;
import org.enascimento.api.service.DateTimeService;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import static org.springframework.http.ProblemDetail.forStatusAndDetail;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private DateTimeService dateTimeService;

    @ExceptionHandler(BusinessException.class)
    ProblemDetail handleBusinessException(BusinessException e) {
        LOG.warn("{}", e.getMessage(), e);
        var problemDetail = forStatusAndDetail(HttpStatus.CONFLICT, e.getMessage());
        problemDetail.setProperty("timestamp", dateTimeService.nowLocalDateTime());
        return problemDetail;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    ProblemDetail handleNoResourceFoundExceptionException(NoResourceFoundException e) {
        LOG.warn("{}", e.getMessage(), e);
        var problemDetail = forStatusAndDetail(HttpStatus.BAD_REQUEST, "URI_INVALID");
        problemDetail.setProperty("timestamp", dateTimeService.nowLocalDateTime());
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    ProblemDetail handleException(Exception e) {
        LOG.error("{}", e.getMessage(), e);
        var problemDetail = forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "GENERIC_ERROR");
        problemDetail.setProperty("timestamp", dateTimeService.nowLocalDateTime());
        return problemDetail;
    }
}