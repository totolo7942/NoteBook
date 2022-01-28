package pe.notebook.ifStatementsReplace;

import org.junit.jupiter.api.Test;
import org.junit.runners.Suite;

import static org.junit.jupiter.api.Assertions.*;

//checked : https://www.baeldung.com/java-search-enum-values
// class to innerclass call
@Suite.SuiteClasses({ Expression.class, RuleEngine.class})
class iforReplaceMainTest {

    @Test
    void testRuleBaseSuccessTest(){
        Expression expression = new Expression(5, 5 , Operator.ADD);
        assertEquals( expression.getX(), 5);
        RuleEngine ruleEngine = new RuleEngine();

        //예외를 발생하지 않는지 확인, assertAll(), assertThrows()
        assertDoesNotThrow(() -> ruleEngine.process(expression ));
        assertEquals(2, ruleEngine.getRules().size());

        int value = ruleEngine.process(expression);
        assertTrue( value > 0 );

    }
}