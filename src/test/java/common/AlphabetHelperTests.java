package common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AlphabetHelperTests {

    @Test
    public void test(){
        assertEquals(true,AlphabetHelper.isLiteral('a'));
        assertEquals(true,AlphabetHelper.isLiteral('_'));
        assertEquals(true,AlphabetHelper.isLiteral('9'));
        assertEquals(false,AlphabetHelper.isLiteral('*'));
        assertEquals(true,AlphabetHelper.isLetter('a'));
        assertEquals(false,AlphabetHelper.isLetter('*'));
        assertEquals(true,AlphabetHelper.isNumber('1'));
        assertEquals(true,AlphabetHelper.isNumber('9'));
        assertEquals(false,AlphabetHelper.isNumber('a'));
        assertEquals(false,AlphabetHelper.isOperator('9'));
        assertEquals(false,AlphabetHelper.isOperator('a'));
        assertEquals(true,AlphabetHelper.isOperator('+'));
        assertEquals(true,AlphabetHelper.isOperator('-'));
        assertEquals(true,AlphabetHelper.isOperator('*'));
        assertEquals(true,AlphabetHelper.isOperator('/'));
        assertEquals(true,AlphabetHelper.isOperator('&'));
        assertEquals(true,AlphabetHelper.isOperator('>'));
        assertEquals(true,AlphabetHelper.isOperator('<'));
        assertEquals(true,AlphabetHelper.isOperator('!'));
        assertEquals(true,AlphabetHelper.isOperator('^'));
        assertEquals(true,AlphabetHelper.isOperator('|'));
        assertEquals(true,AlphabetHelper.isOperator('='));
        assertEquals(true,AlphabetHelper.isOperator('%'));
    }
}
