package pe.notebook.ifStatementsReplace;

import java.util.*;

public class informReplaceMain {

    public static void main(String[] args) {
        //1. Factory class
        Operation operation = OperationFactory.getOperation("add")
                .orElseThrow(() -> new IllegalArgumentException("Invalid Operation"));

        System.out.println(operation.apply( 1, 2));

        //2. enum class
        Calculator calibrator = new Calculator();
        System.out.println(calibrator.calculator(Operator.ADD));

        //3. command pattern
        System.out.println(calibrator.calculator(new AddCommand( 1, 3)));

        //4. advance Rule
        Expression expression = new Expression(5 , 5, Operator.ADD);
        RuleEngine engine = new RuleEngine();
        int result = engine.process(expression);
        System.out.println(result);

        expression = new Expression(5 , 2, Operator.MINUS);
        engine = new RuleEngine();
        result = engine.process(expression);
        System.out.println(result);

    }

}

//------------- Rule Apply
interface Rule {
    boolean eval(Expression expression);
    int getResult();
}

class RuleEngine {
    static List<Rule> rules = new ArrayList<>();
    static {
        rules.add(new AddRule());
        rules.add(new MinusRule());
    }

    public int process(Expression expression){
        Rule rule = rules.stream().filter( r -> r.eval(expression))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("expression not matches"));
        return rule.getResult();
    }

    public List<Rule> getRules(){
        return rules;
    }

}


class AddRule implements Rule {
    int result = 0;
    @Override
    public boolean eval(Expression expression) {
        boolean evalResult = false;
        if (expression.getOperator() == Operator.ADD) {
            result = expression.getX() + expression.getB();
            evalResult = true;
        }
        return evalResult;
    }

    @Override
    public int getResult() {
        return result;
    }
}

class MinusRule implements Rule {
    int result = 0;
    @Override
    public boolean eval(Expression expression) {
        boolean evalResult = false;
        if (expression.getOperator() == Operator.MINUS) {
            result = expression.getX() - expression.getB();
            evalResult = true;
        }
        return evalResult;
    }

    @Override
    public int getResult() {
        return result;
    }
}


//-----------
class Expression {
    int x;
    int b;
    private Operator operator;

    public Expression(int i, int i1, Operator operator) {
        this.x = i;
        this.b = i1;
        this.operator = operator;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}

interface Command {
    int executor();
}


class AddCommand implements Command{
    int a;
    int b;
    public AddCommand(int a , int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public int executor() {
        return a + b;
    }


}



interface Operation {
    int apply(int a, int b);

}

class Addition implements Operation {
    @Override
    public int apply(int a, int b) {
        return a + b;
    }


}

class OperationFactory {
    static Map<String, Operation> operationMap = new HashMap<>();
    static  {
        operationMap.put("add", new Addition());
    }

    public static Optional<Operation> getOperation(final String opt) {
        return Optional.ofNullable(operationMap.get(opt));
    }
}

///------------- other way
enum Operator {
    ADD {
        @Override
        public int apply(int a, int b) {
            return a + b;
        }
    }, MINUS {
        @Override
        public int apply(int a, int b) {
            return 0;
        }
    };

    public abstract int apply(int a , int b);
}
