package com.apushkin.web;

import com.apushkin.model.ErrorObject;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public List<ErrorObject> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        System.out.println(e.getMessage());
        List<ErrorObject> errorObjects = parseError(e.getMessage());
        System.out.println(errorObjects);
        return errorObjects;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public List<ErrorObject> handleConstraintViolation(ConstraintViolationException exception) { //
        //you can choose to return your custom object here, which will then get transformed to json/xml etc.
        List<ErrorObject> errorObjects = parseError(exception.getMessage());
        System.out.println(errorObjects);
        return errorObjects;
    }

    public static List<ErrorObject> parseError(String error) {
        String regex = "Field error in object 'transaction' on field '(.*?)': rejected value \\[(.*?)\\];";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(error);
        List<ErrorObject> errors = new ArrayList<>();

        while (matcher.find()) {
            String field = matcher.group(1);
            String value = matcher.group(2);
            errors.add(new ErrorObject(field, value));
        }
        return errors;
    }
}
