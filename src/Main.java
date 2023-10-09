import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static char[] signs = {' ', '+', '-', '*', '/'};

    public static void main(String[] args) {
        var input = "98765432";
        var inputDivided = divideToArray(input);
        combinations(inputDivided, 0);
    }

    private static void solute(char[] input) {
        List<String> inputList = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (var c : input) {
            if (c != ' ') {
                builder.append(c);
            }
        }

        for (var c : builder.toString().toCharArray()) {
            if(c == '+' || c == '-' || c == '*' || c == '/') {
                inputList.add(Character.toString(c));
            } else {
                try {
                    Integer.parseInt(inputList.get(inputList.size() - 1));
                    inputList.set(inputList.size() - 1, inputList.get(inputList.size() - 1) + c);
                } catch (Exception e) {
                    inputList.add(Character.toString(c));
                }
            }
        }

        var prefixForm = toPrefixForm(inputList);
        if(calc(prefixForm) == 100) {
            System.out.println(String.join("", inputList));
        }
    }

    private static double calc(List<String> input) {
        Deque<Double> stack = new LinkedList<>();
        for (var c : input) {
            try {
                stack.push(Double.parseDouble(c));
            } catch (Exception e) {
                var right = stack.pop();
                var left = stack.pop();
                switch (c) {
                    case "+":
                        stack.push(left + right);
                        break;
                    case "-":
                        stack.push(left - right);
                        break;
                    case "*":
                        stack.push(left * right);
                        break;
                    case "/":
                        if(right == 0) {
                            return 0;
                        }
                        stack.push(left / right);
                        break;
                    default:
                        break;
                }
            }
        }
        return stack.pop();
    }

    private static List<String> toPrefixForm(List<String> input) {

        List<String> res = new ArrayList<String>();
        Deque<String> stack = new LinkedList<>();

        for (String c : input) {
            try {
                Integer.parseInt(c);
                res.add(c);
            } catch(Exception e) {
                while(!stack.isEmpty() && getPriority(stack.peek()) >= getPriority(c)) {
                    res.add(stack.pop());
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            res.add(stack.pop());
        }
        return res;
    }

    private static int getPriority(String c) {
        if(c.equals("+") || c.equals("-")) {
            return 1;
        } else if(c.equals("/") || c.equals("*")) {
            return 2;
        } else {
            throw new ArithmeticException();
        }
    }

    private static void combinations(char[] input, int index) {
        if(index == 7) {
            solute(input);
            return;
        }

        var place = index * 2 + 1;
        for (var sign : signs) {
            input[place] = sign;
            combinations(input, index + 1);
        }
    }

    private static char[] divideToArray(String input) {
        char[] res = new char[input.length() * 2 - 1];
        for (int i = 0; i < input.length() - 1; i++) {
            res[i * 2] = input.charAt(i);
            res[i * 2 + 1] = ' ';
        }
        res[res.length - 1] = input.charAt(input.length() - 1);
        return res;
    }
}