import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            FileWriter writer = new FileWriter("output.txt");


            int numTestCases = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numTestCases; i++) {
                String[] input = scanner.nextLine().split(" ");
                char operator = input[0].charAt(0);
                String polynomial1 = input[1];
                String polynomial2 = input[2];

                System.out.println("Operator: " + operator);
                System.out.println("Polynomial 1: " + polynomial1);
                System.out.println("Polynomial 2: " + polynomial2);

                LinkedList polyList1 = parsePolynomial(polynomial1);
                LinkedList polyList2 = parsePolynomial(polynomial2);

                System.out.println("Parsed Polynomial 1: " + polyList1);
                System.out.println("Parsed Polynomial 2: " + polyList2);

                LinkedList result = null;
                if (operator == '+') {
                    result = performAddition(polyList1, polyList2);
                } else if (operator == '-') {
                    result = performSubtraction(polyList1, polyList2);
                } else if (operator == '*') {
                    result = performMultiplication(polyList1, polyList2);
                }

                System.out.println("Result: " + result);

                String resultString = polynomialToString(result);

                writer.write(resultString + "\n");
            }
            scanner.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LinkedList parsePolynomial(String polynomial) {
        LinkedList polyList = new LinkedList();
        String[] terms = polynomial.split("(?=[+-])");
        for (String term : terms) {
            int coefficient = 1;
            String termWithoutCoeff = term;
            if (term.charAt(0) == '-') {
                coefficient = -1;
                termWithoutCoeff = term.substring(1);
            } else if (term.charAt(0) == '+') {
                termWithoutCoeff = term.substring(1);
            }
            Term newTerm = new Term(coefficient, 0, 0, 0);

            String[] variables = termWithoutCoeff.split("(?=[xyz])");
            for (String var : variables) {
                int exponent = 1;
                if (var.contains("^")) {
                    String[] parts = var.split("\\^");
                    var = parts[0];
                    exponent = Integer.parseInt(parts[1]);
                }
                if (var.equals("x")) {
                    newTerm.setExponentX(exponent);
                } else if (var.equals("y")) {
                    newTerm.setExponentY(exponent);
                } else if (var.equals("z")) {
                    newTerm.setExponentZ(exponent);
                }
            }
            Node newNode = new Node(newTerm);
            polyList.insertLast(newNode);
        }
        return polyList;
    }

    private static LinkedList performAddition(LinkedList polyList1, LinkedList polyList2) {
        LinkedList result = new LinkedList();
        Node current1 = polyList1.getHead();
        Node current2 = polyList2.getHead();

        while (current1 != null && current2 != null) {
            int coef1 = current1.getData();
            int exp1X = current1.getExponentX();
            int exp1Y = current1.getExponentY();
            int exp1Z = current1.getExponentZ();

            int coef2 = current2.getData();
            int exp2X = current2.getExponentX();
            int exp2Y = current2.getExponentY();
            int exp2Z = current2.getExponentZ();

            // Compare the exponents of the terms
            int comparisonResult = compareExponents(exp1X, exp1Y, exp1Z, exp2X, exp2Y, exp2Z);

            if (comparisonResult == 0) {
                // If exponents are equal, add the coefficients
                int sumCoefficients = coef1 + coef2;
                if (sumCoefficients != 0) {
                    result.insertLast(new Node(new Term(sumCoefficients, exp1X, exp1Y, exp1Z)));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparisonResult < 0) {
                // If exponent of the first term is smaller, add it to the result
                result.insertLast(new Node(new Term(coef1, exp1X, exp1Y, exp1Z)));
                current1 = current1.getNext();
            } else {
                // If exponent of the second term is smaller, add it to the result
                result.insertLast(new Node(new Term(coef2, exp2X, exp2Y, exp2Z)));
                current2 = current2.getNext();
            }
        }

        // Add remaining terms from polyList1
        while (current1 != null) {
            int coef1 = current1.getData();
            int exp1X = current1.getExponentX();
            int exp1Y = current1.getExponentY();
            int exp1Z = current1.getExponentZ();
            result.insertLast(new Node(new Term(coef1, exp1X, exp1Y, exp1Z)));
            current1 = current1.getNext();
        }

        // Add remaining terms from polyList2
        while (current2 != null) {
            int coef2 = current2.getData();
            int exp2X = current2.getExponentX();
            int exp2Y = current2.getExponentY();
            int exp2Z = current2.getExponentZ();
            result.insertLast(new Node(new Term(coef2, exp2X, exp2Y, exp2Z)));
            current2 = current2.getNext();
        }

        return result;
    }

    private static LinkedList performSubtraction(LinkedList polyList1, LinkedList polyList2) {
        LinkedList result = new LinkedList();
        Node current1 = polyList1.getHead();
        Node current2 = polyList2.getHead();

        while (current1 != null && current2 != null) {
            int coef1 = current1.getData();
            int exp1X = current1.getExponentX();
            int exp1Y = current1.getExponentY();
            int exp1Z = current1.getExponentZ();

            int coef2 = current2.getData();
            int exp2X = current2.getExponentX();
            int exp2Y = current2.getExponentY();
            int exp2Z = current2.getExponentZ();

            // Compare the exponents of the terms
            int comparisonResult = compareExponents(exp1X, exp1Y, exp1Z, exp2X, exp2Y, exp2Z);

            if (comparisonResult == 0) {
                // If exponents are equal, subtract the coefficients
                int diffCoefficients = coef1 - coef2;
                if (diffCoefficients != 0) {
                    result.insertLast(new Node(new Term(diffCoefficients, exp1X, exp1Y, exp1Z)));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparisonResult < 0) {
                // If exponent of the first term is smaller, add it to the result
                result.insertLast(new Node(new Term(coef1, exp1X, exp1Y, exp1Z)));
                current1 = current1.getNext();
            } else {
                // If exponent of the second term is smaller, add its negation to the result
                result.insertLast(new Node(new Term(-coef2, exp2X, exp2Y, exp2Z)));
                current2 = current2.getNext();
            }
        }

        // Add remaining terms from polyList1
        while (current1 != null) {
            int coef1 = current1.getData();
            int exp1X = current1.getExponentX();
            int exp1Y = current1.getExponentY();
            int exp1Z = current1.getExponentZ();
            result.insertLast(new Node(new Term(coef1, exp1X, exp1Y, exp1Z)));
            current1 = current1.getNext();
        }

        // Add remaining terms from polyList2
        while (current2 != null) {
            int coef2 = current2.getData();
            int exp2X = current2.getExponentX();
            int exp2Y = current2.getExponentY();
            int exp2Z = current2.getExponentZ();
            result.insertLast(new Node(new Term(-coef2, exp2X, exp2Y, exp2Z)));
            current2 = current2.getNext();
        }

        return result;
    }

    private static int compareExponents(int exp1X, int exp1Y, int exp1Z, int exp2X, int exp2Y, int exp2Z) {
        if (exp1X != exp2X) {
            return Integer.compare(exp1X, exp2X);
        } else if (exp1Y != exp2Y) {
            return Integer.compare(exp1Y, exp2Y);
        } else {
            return Integer.compare(exp1Z, exp2Z);
        }
    }

    private static LinkedList performMultiplication(LinkedList polyList1, LinkedList polyList2) {
        LinkedList result = new LinkedList();
        Node current1 = polyList1.getHead();
        while (current1 != null) {
            Node current2 = polyList2.getHead();
            while (current2 != null) {
                int coef1 = current1.getData();
                int coef2 = current2.getData();
                int productCoefficients = coef1 * coef2;
                int expX = current1.getExponentX() + current2.getExponentX();
                int expY = current1.getExponentY() + current2.getExponentY();
                int expZ = current1.getExponentZ() + current2.getExponentZ();
                Term newTerm = new Term(productCoefficients, expX, expY, expZ);
                newTerm.setExponentX(expX);
                newTerm.setExponentY(expY);
                newTerm.setExponentZ(expZ);
                result.insertLast(new Node(newTerm));
                current2 = current2.getNext();
            }
            current1 = current1.getNext();
        }
        return result;
    }

    private static String polynomialToString(LinkedList polyList) {
        StringBuilder builder = new StringBuilder();
        Node current = polyList.getHead();
        while (current != null) {
            if (current.getData() > 0 && builder.length() > 0) {
                builder.append("+");
            }
            builder.append(current.getData());
            if (current.getExponentX() > 0) {
                builder.append("x");
                if (current.getExponentX() > 1) {
                    builder.append("^").append(current.getExponentX());
                }
            }
            if (current.getExponentY() > 0) {
                builder.append("y");
                if (current.getExponentY() > 1) {
                    builder.append("^").append(current.getExponentY());
                }
            }
            if (current.getExponentZ() > 0) {
                builder.append("z");
                if (current.getExponentZ() > 1) {
                    builder.append("^").append(current.getExponentZ());
                }
            }
            current = current.getNext();
        }
        return builder.toString();
    }
}