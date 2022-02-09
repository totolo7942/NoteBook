package pe.notebook.ifStatementsReplace;

public class Calculator {
    public int calculator(Operator operator) {
        return operator.apply(1 , 2);
    }

    public int calculator(Command command) {
        return command.executor();
    }
}
