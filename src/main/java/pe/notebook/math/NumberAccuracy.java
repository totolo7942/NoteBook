package pe.notebook.math;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.function.Predicate;

public class NumberAccuracy {
        public static void main(String[] args) {
            // Double
            Double sum_1 = 0.0;
            for (int i=0 ; i<100; i++) {
                sum_1 += 0.2;
            }
            System.out.println("Dobule 0.1 100번 더함, 0.1*100 : "+sum_1 + "  ,  값:"+ (0.1f*100));

            // Float
            Float sum_2 = 0.0f;
            for (int i=0 ; i<100; i++) {
                sum_2 += 0.2f;
            }
            System.out.println("Float 0.1f 100번 더함, 0.1*100 : "+sum_2 + "  ,  값:"+ (0.1*100));

            Double sum_3 = 0.0;
            for(int b=0; b<100; b++) {
                BigDecimal value1 = new BigDecimal(sum_3.toString());
                sum_3 = value1.add(new BigDecimal("0.1")).doubleValue();
            }
            System.out.println("BigDecimal '0.1' 100더함 , 0.1*100 : "+sum_3 + "  , 값: "+ (0.1*100));


            String decimal = "0.1";
            System.out.println(zeroRate.test(decimal));
        }

    protected static Predicate<String> zeroRate = (i) -> {
        if( StringUtils.isBlank(i))
            return false;

        double v = Double.parseDouble(i);
        if( v % 1 != 0) {
            return v <= 0.0;
        }

        return v <= 0;
    };
}
