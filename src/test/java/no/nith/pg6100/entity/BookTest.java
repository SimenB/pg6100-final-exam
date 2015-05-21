package no.nith.pg6100.entity;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BookTest {
    ValidatorFactory validatorFactory;
    Validator validator;

    @Before
    public void setUp() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void noViolations_book() throws Exception {
        Book book = new Book();
        book.setAuthor("Douglas Adams");
        book.setTitle("Hitchhiker's Guide to the Galaxy");
        book.setIsbn("12345");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(0, violations.size());
    }

    @Test
    public void violations_forAuthor() throws Exception {
        Book book = new Book();
        book.setTitle("Hitchhiker's Guide to the Galaxy");
        book.setIsbn("12345");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
    }

    @Test
    public void violations_forTitle() throws Exception {
        Book book = new Book();
        book.setAuthor("Douglas Adams");
        book.setIsbn("12345");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
    }

    @Test
    public void violations_forTooShortIsbn() throws Exception {
        Book book = new Book();
        book.setAuthor("Douglas Adams");
        book.setTitle("Hitchhiker's Guide to the Galaxy");
        book.setIsbn("123");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
    }

    @Test
    public void violations_forTooLongIsbn() throws Exception {
        Book book = new Book();
        book.setAuthor("Douglas Adams");
        book.setTitle("Hitchhiker's Guide to the Galaxy");
        book.setIsbn("123456789");

        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertEquals(1, violations.size());
    }
}
