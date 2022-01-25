package pe.notebook;

import org.apache.commons.lang3.RandomUtils;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTutorial {

    public static void main(String[] args) {
        System.out.println("Random : " + aFloatRandom());
        System.out.println("secure : " +secureRandom());
        System.out.println("apachecommon : " + apacheCommonRandom());
        System.out.println("threadLocalRandom : " + threadLocalRandom());
    }

    private static float aFloatRandom(){
        Random random = new Random();
        return random.nextFloat() * ( 1 - 100);
    }

    private static int secureRandom() {
        int min = 10;
        int max = 100;
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());
        return secureRandom.nextInt( (max - min) +1) + min;
    }

    private static int apacheCommonRandom(){
        return RandomUtils.nextInt(10, 100);
    }

    private static int threadLocalRandom(){
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return  threadLocalRandom.nextInt(10, 100+1);
    }
}
