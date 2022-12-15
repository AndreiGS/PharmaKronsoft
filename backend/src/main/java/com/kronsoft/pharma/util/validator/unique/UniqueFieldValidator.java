package com.kronsoft.pharma.util.validator.unique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class UniqueFieldValidator implements ConstraintValidator<Unique, Object> {
    private final ApplicationContext applicationContext;
    private JpaRepository<?, ?> repository;
    private String field;

    @Autowired
    public UniqueFieldValidator(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.repository = this.applicationContext.getBean(constraintAnnotation.repository());
        this.field = constraintAnnotation.field();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Method method;
        try {
            field = field.substring(0, 1).toUpperCase() + field.substring(1);
            method = repository.getClass().getMethod("findBy" + field);
            Optional<?> output = (Optional<?>) method.invoke(value);
            return output.isEmpty();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}