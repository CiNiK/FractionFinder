/*
*@author Vitaliy Karpachev <vitaliy.karpachev@gmail.com>
*
* Represents the string value of numerator/denominator, recorded in the notation with given radix.
* Maximal radix is 36. If it is a periodic fraction,the period enclosed in parentheses.
* Sample input: int numerator = 100; int denominator = 13; int radix = 14
* Sample output: 7.(99)
* , where (99) - is a period.
* Sample input: String numerator = '72'; String denominator = 'X'; int radix = 36
* Sample output: 7.P(39TGD)
* , where (39TGD) - is a period.
* */
package fractions;

class Fraction {
    private final static char[] digits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
            'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
    };

    public static String toString(int numer, int denom, int radix) {
        if (radix < 2 || radix > 36)
            throw new IllegalArgumentException("Radix must be less than 36 and bigger than 2");
        String integerPart = getIntegerPart(numer, denom, radix);
        if (numer % denom == 0) return integerPart;
        /*Reduce denominator and numerator, so they are co-prime*/
        int divider = getGreatestCommonDivisor(numer, denom);
        int denominator = denom / divider;
        int numerator = numer / divider;
        int prePeriodLength = getPrePeriodLength(denominator, radix);
        String fractionalPart = getFractionPart(numerator, denominator, radix, prePeriodLength);
        return integerPart + getFormattedFraction(fractionalPart, prePeriodLength);
    }

    public static String toString(String numer, String denom, int radix) {
        return toString(Integer.parseInt(numer, radix), Integer.parseInt(denom, radix), radix);
    }

    private static String getIntegerPart(int numerator, int denominator, int radix) {
        return Integer.toString(numerator / denominator, radix).toUpperCase();
    }

    /*Implementation of Euclidean algorithm*/
    private static int getGreatestCommonDivisor(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    /* Define preperiod length that equals max power of radix's prime divider containing in denominator.
     * (eg. Radix's prime dividers for decimal notation are 2 and 5. Preperiod length for 1/20 equals 2,
     * because 20 = 2*2*5 - max power of 2 is 2) */
    private static int getPrePeriodLength(int denominator, int radix) {
        int maxLength = 0;
        int currentDenominator = denominator;
        for (int i = 2; i <= radix; i++) {
            int currentLength = 0;
            if (radix % i != 0) continue;
            int remainder = currentDenominator % i;
            while (remainder == 0) {
                currentDenominator = currentDenominator / i;
                remainder = currentDenominator % i;
                currentLength++;
            }
            if (currentLength > maxLength) maxLength = currentLength;
        }
        return maxLength;
    }

    private static String getFractionPart(int numerator, int denominator, int radix, int length) {
        StringBuilder fraction = new StringBuilder();
        int periodStartRemainder = 0;
        int iteration = 0;
        int currentRemainder = numerator % denominator;
        while (currentRemainder != periodStartRemainder) {
            if(iteration == length) periodStartRemainder = currentRemainder;
            int digit = (currentRemainder * radix) / denominator;
            currentRemainder = (currentRemainder * radix) % denominator;
            fraction.append(digits[digit]);
            iteration++;
        }
        return new String(fraction);
    }

    private static String getFormattedFraction(String fractionalPart, int prePeriodLength) {
        if (fractionalPart == null) return "";
        StringBuilder fraction = new StringBuilder(".");
        if (prePeriodLength != 0)
            fraction.append(fractionalPart, 0, prePeriodLength);
        if (fractionalPart.length() - prePeriodLength != 0)
            fraction.append("(" + fractionalPart.substring(prePeriodLength, fractionalPart.length()) + ")");
        return fraction.toString().toUpperCase();
    }

    /*Simple unit testing*/
    public static void main(String[] args) {
        String integer = Fraction.toString(999, 9, 18);
        String period = Fraction.toString(67, 7, 2);
        String mixed = Fraction.toString(43, 15, 10);
        if (!"63".equals(integer))
            System.out.println("63 not equals " + integer);
        if (!"1001.(100)".equals(period))
            System.out.println("1001.(100) not equals " + period);
        if (!"2.8(6)".equals(mixed))
            System.out.println("2.8(6) not equals " + mixed);
    }
}
