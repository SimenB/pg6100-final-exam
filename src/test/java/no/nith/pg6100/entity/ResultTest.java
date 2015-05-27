package no.nith.pg6100.entity;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ResultTest {
    private static Validator validator;
    private Result result;

    @BeforeClass
    public static void setUpOnce() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Before
    public void setUp() throws Exception {
        result = new Result();

        result.setWinner(1);
        result.setLoser(1);
    }

    @Test
    public void tesNoViolations() throws Exception {
        assertThat(validator.validate(result), is(empty()));
    }

    @Test
    public void testInvalidNumber() throws Exception {
        result.setWinner(null);

        assertThat(validator.validate(result), hasSize(1));
    }

    @Test
    public void testNegativeNumber() throws Exception {
        result.setLoser(null);

        assertThat(validator.validate(result), hasSize(1));
    }
}
