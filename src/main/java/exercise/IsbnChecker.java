package exercise;

// code from https://www.knowprogram.com/java/isbn-number-in-java/

/**
 * IsbnChecker to evaluate ISBN numbers
 */
public class IsbnChecker {

    // method to check number is ISBN
    public static boolean isISBN(String number) {

        // remove all hyphens
        number = number.replace("-", "");
        // remove all spaces
        number = number.replace(" ", "");

        // find length
        int length = number.length();
        if(length==13)
            return isISBN13(number);
        else if(length==10)
            return isISBN10(number);

        return false;
    }

    // method to check ISBN-13
    private static boolean isISBN13(String number) {

        // declare variables
        int sum = 0;
        int multiple = 0;
        char ch = '\0';
        int digit = 0;

        // add digits
        for (int i = 1; i <= 13; i++) {

            if (i % 2 == 0)
                multiple = 3;
            else multiple = 1;

            // fetch digit
            ch = number.charAt(i - 1);
            // convert it to number
            digit = Character.getNumericValue(ch);

            // addition
            sum += (multiple * digit);
        }

        // check sum
        if (sum % 10 == 0)
            return true;
        return false;
    }
    // method to check ISBN-10
    private static boolean isISBN10(String number) {

        // declare variables
        int sum = 0;
        int digit = 0;
        char ch = '\0';

        // add upto 9th digit
        for(int i=1; i<=9; i++) {
            ch = number.charAt(i-1);
            digit = Character.getNumericValue(ch);
            sum += (i* digit);
        }

        // last digit
        ch = number.charAt(9);
        ch = Character.toUpperCase(ch);
        if(ch =='X')
            sum += (10*10);
        else {
            digit = Character.getNumericValue(ch);
            sum += (digit * 10);
        }

        // check sum
        if(sum % 11 == 0)
            return true;

        return false;
    }
}
