package pe.notebook.validator;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class UseAssertMain {
    private static Log log = LogFactory.getLog(UseAssertMain.class);

    /**
     * JVM -ea 를 활성화 한 상태에서 다음과 같이 사용한다.
     * 해당 값을 사용하는 이유는 도달하지 말아야 되는 값을 검증하기 위한 용도로 사용되어지며 AssertionError를 발생 시킨다.
     * ex)
     * Exception in thread "main" java.lang.AssertionError: names is 3 length over
     * 	at pe.notebook.validator.UseAssertMain.main(UseAssertMain.java:8)
     * @param args
     */
    public static void main(String[] args) {
        String[] names = {"John", "Mary", "David"};


        assert names.length == 2 :  "names is 3 length over";
        System.out.println("There are "+names.length + "  names in an array");

        int value=5;
        assert value > 20 : "Underweight";
        System.out.println("value is"+value);
    }

}
