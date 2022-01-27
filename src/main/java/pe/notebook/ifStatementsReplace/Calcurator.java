package pe.notebook.ifStatementsReplace;

public class Calcurator {
    public int calcuator(Operator operator) {
        return operator.apply(1 , 2);
    }

    public int calcurator(Command command) {
        return command.executor();
    }
}
