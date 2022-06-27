import java.util.HashMap;
import java.util.Scanner;

public class Calc {
    public static void main(String[] args) throws Exception {
        start();
    }

    private static void start() throws Exception {
        while (true) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Введите операцию:");
            String inputStr = reader.nextLine();
            if (inputStr.isEmpty()) {
                System.out.println("0");
            } else {
                String[] words = inputStr.split("\\s+"); // Делим строку на составные части
                if (words.length < 3) throw new Exception("строка не является математической операцией");
                if (words.length > 3) throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                if (inputStr.equals("quit")) break; // Выход из программы
                String num1 = words[0];
                String num2 = words[2];
                String operator = words[1];
                System.out.println(prepareToCalculation(operator, num1, num2));
            }
        }
    }

    private static String prepareToCalculation(String operator, String num1, String num2) throws Exception {
        String res = "";
        if (num1.contains(".") || num2.contains(".")) {
            System.out.println("калькулятор может работать только с целыми числами.");
            return "";
        }

        if (!isNumeric(num1) && isNumeric(num2) || isNumeric(num1) && !isNumeric(num2)) {
            throw new Exception("используются одновременно разные системы счисления");
        }

        if (isNumeric(num1) && isNumeric(num2)) {
            // Арабские символы
            res = calculation(operator, "arab", num1, num2);
        }
        else if (!isNumeric(num1) && !isNumeric(num2)) {
            // Римские символы
            String res1 = calculation(operator, "rome", checkRomeNumbers(num1), checkRomeNumbers(num2));
            res = checkRomeNumbers(res1);
        }
        return String.valueOf(res);
    }

    private static String calculation(String operator, String type, String num1, String num2) throws Exception {
        int result = 0;
        int n1 = Integer.parseInt(num1);
        int n2 = Integer.parseInt(num2);
        if ((n1 > 0 && n1 <= 10) && (n2 > 0 && n2 <= 10)) {
            switch (operator) {
                case "+" -> result = n1 + n2;
                case "-" -> result = n1 - n2;
                case "*" -> result = n1 * n2;
                case "/" -> result = n1 / n2;
                default -> System.out.println("Оператор не существует.");
            }
        } else {
            System.out.println("Используйте только числа от 1 до 10.");
            return String.valueOf(result);
        }

        if (type.equals("rome") && result < 1) {
            throw new Exception("в римской системе нет отрицательных чисел");
        }
          return String.valueOf(result);
    }

    private static String checkRomeNumbers(String num) {
        HashMap<Integer, String> numbers = new HashMap<>();
        HashMap<String, Integer> romeToNumbers = new HashMap<>();
        String[] romeNumbers  = new String[] {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String dozen = "";
        int counter = 0;
        for (int i = 0; i <= 10; i++) {
            if(i == 1) { counter++; dozen = "X"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 2) { counter++; dozen = "XX"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 3) { counter++; dozen = "XXX"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 4) { counter++; dozen = "XL"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 5) { counter++; dozen = "L"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 6) { counter++; dozen = "LX"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 7) { counter++; dozen = "LXX"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 8) { counter++; dozen = "LXXX"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 9) { counter++; dozen = "XC"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            if(i == 10) { counter ++; dozen = "C"; numbers.put(counter, dozen); romeToNumbers.put(dozen, counter); }
            for (int y = 0; y <= 8; y++) {
                if (counter == 100) break;
                counter ++;
                numbers.put(counter, dozen + romeNumbers[y]);
                romeToNumbers.put(dozen + romeNumbers[y], counter);
            }
        }
        if (isNumeric(num)){
            return numbers.get(Integer.parseInt(num));
        } else {
            return String.valueOf(romeToNumbers.get(num));
        }
    }

    // Проверка, число или нет
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
}
