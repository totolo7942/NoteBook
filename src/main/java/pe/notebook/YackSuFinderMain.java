package pe.notebook;


import org.apache.commons.lang3.StringUtils;

import java.util.function.Predicate;

public class YackSuFinderMain {

  public static void main(String[] args) {

    String decimal = "0.1";
    System.out.println(zeroRate.test(decimal));

    int num =12;

    for (int i = 1; i <= num; i++) {
      if (num % i == 0) {
        System.out.println(i);
      }
    }
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
