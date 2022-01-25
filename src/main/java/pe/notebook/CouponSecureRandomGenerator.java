package pe.notebook;

import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.LinkedHashSet;
import java.util.Set;

public class CouponSecureRandomGenerator {

    public static void main(String[] args) throws CouponGenerateError {
        Set<Coupon> couponSet = CouponGenerator.getCouponSet((byte) 10, 10);
        for( Coupon coupon :  couponSet) {
            System.out.println(CouponUtil.getVoucherString(coupon));
        }
    }
}

class CouponGenerator {
    /**
     * Represent of number of CRC chracter. If it is 1. One Character of CRC code will be generated and attached next to
     * coupon code
     */
    static int CRC_SIZE = 1;
    /**
     * CRC Generator should never be changed unless you like to invalidate all previously published coupons. It is a key
     * value to prove if the CRC value is right or not
     */
    static int CRC_GENERATOR = 31;

    public static Set<Coupon> getCouponSet(byte numberOfChar, long numberOfCoupon) throws CouponGenerateError {
        isValidNumberOfChar(numberOfChar);

        var couponSet = new LinkedHashSet();
        while (numberOfCoupon > couponSet.size()) {
            couponSet.add(generateCoupon(numberOfChar));
        }

        return couponSet;
    }

    private static void isValidNumberOfChar(byte numberOfChar) throws CouponGenerateError {
        if (numberOfChar < 3 || numberOfChar > 12) {
            throw new CouponGenerateError("Invalid numberOfChar for Coupon chracters. It must be bigger than 2 and smaller than 12");
        }
    }

    public Coupon getCoupon(byte numberOfChar) throws CouponGenerateError {
        isValidNumberOfChar(numberOfChar);
        return generateCoupon(numberOfChar);
    }

    private static Coupon generateCoupon(byte numberOfChar) throws CouponGenerateError {
        SecureRandom random = new SecureRandom();

        Coupon coupon = new Coupon();
        coupon.setNumberOfChar(numberOfChar);
        // Create 5 random bits per character. 5 bits represent Base32 Encoding
        coupon.setCode(new BigInteger((numberOfChar - CRC_SIZE) * 5, random));
        coupon.setCrc(CouponUtil.calculateCrc(coupon));

        return coupon;
    }
}

class CouponGenerateError extends Exception {
    @Serial
    private static final long serialVersionUID = -1653304954179913425L;

    public CouponGenerateError(String message) {
        super(message);
    }
}

class CouponUtil {

    /**
     * 7.  Base 32 Encoding with Extended Hex Alphabet
     *
     *    The following description of base 32 is derived from [7].  This
     *    encoding may be referred to as "base32hex".  This encoding should not
     *    be regarded as the same as the "base32" encoding and should not be
     *    referred to as only "base32".  This encoding is used by, e.g.,
     *    NextSECure3 (NSEC3) [10].
     *
     *    One property with this alphabet, which the base64 and base32
     *    alphabets lack, is that encoded data maintains its sort order when
     *    the encoded data is compared bit-wise.
     *
     *    This encoding is identical to the previous one, except for the
     *    alphabet.  The new alphabet is found in Table 4.
     *
     *                  Table 4: The "Extended Hex" Base 32 Alphabet
     *
     *          Value Encoding  Value Encoding  Value Encoding  Value Encoding
     *              0 0             9 9            18 I            27 R
     *              1 1            10 A            19 J            28 S
     *              2 2            11 B            20 K            29 T
     *              3 3            12 C            21 L            30 U
     *              4 4            13 D            22 M            31 V
     *              5 5            14 E            23 N
     *              6 6            15 F            24 O         (pad) =
     *              7 7            16 G            25 P
     *              8 8            17 H            26 Q
     */
    public static String getVoucherString(Coupon coupon) {
        byte numberOfChar = coupon.getNumberOfChar();

        String code = coupon.getCode().toString(32);

        if (!isCodeSizeRight(numberOfChar, code)) {
            code = leftPadding0(numberOfChar, code);
        }
        return code + coupon.getCrc().toString(32);
    }

    private static String leftPadding0(byte numberOfChar, String code) {
        return StringUtils.repeat("0", numberOfChar - CouponGenerator.CRC_SIZE).substring(
                code.length() % numberOfChar) + code;
    }

    private static boolean isCodeSizeRight(byte numberOfChar, String code) {
        return code.length() % (numberOfChar - CouponGenerator.CRC_SIZE) == 0;
    }

    static boolean isValidCrc(Coupon coupon) {
        return coupon.getCrc().equals(calculateCrc(coupon));
    }

    static BigInteger calculateCrc(Coupon coupon) {
        BigInteger code = coupon.getCode();
        String crcValue = String.valueOf(code.longValue() % CouponGenerator.CRC_GENERATOR);
        return new BigInteger(crcValue, 10);
    }
}

class Coupon {
    private byte numberOfChar;
    private BigInteger code;
    private BigInteger crc;
    public byte getNumberOfChar() {
        return numberOfChar;
    }
    public void setNumberOfChar(byte numberOfChar) {
        this.numberOfChar = numberOfChar;
    }
    public BigInteger getCode() {
        return code;
    }

    public void setCode(BigInteger code) {
        this.code = code;
    }

    public BigInteger getCrc() {
        return crc;
    }

    public void setCrc(BigInteger crc) {
        this.crc = crc;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coupon c)) {
            return false;
        }

        return code.equals(c.code);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (code != null ? code.hashCode() : 0);
        hash = 89 * hash + (crc != null ? crc.hashCode() : 0);
        return hash;
    }
}
