
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FractionFinder {
    public static void main(String[] args) throws IOException {
        printFractionsFromStandardInput();
    }

    private static void printFractionsFromStandardInput() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;
        System.out.println("Print numerator, denominator and radix through whitespace. Type 'exit' to exit");
        while (!(input = reader.readLine()).equals("exit")) {
            try {
                String[] data = input.split(" ");
                int numerator = Integer.parseInt(data[0]);
                int denominator = Integer.parseInt(data[1]);
                int radix = Integer.parseInt(data[2]);
                System.out.println(Fraction.toString(numerator,denominator,radix));
            } catch (ArrayIndexOutOfBoundsException ex){
                System.out.println("Invalid input. Print three integers");
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Numerator, denominator and radix must be integers");
            }catch (IllegalArgumentException ex) {
                System.out.println("Invalid input. "+ex.getMessage());
            }
        }
        reader.close();
    }
}
