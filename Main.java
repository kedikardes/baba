import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));

            int numTestCases = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numTestCases; i++) {
                String[] input = scanner.nextLine().split(" ");
                char operator = input[0].charAt(0);
                String polynomial1 = input[1];
                String polynomial2 = input[2];

                LinkedList polyList1 = parsePolynomial(polynomial1);
                LinkedList polyList2 = parsePolynomial(polynomial2);

                LinkedList result = null;
                if (operator == '+') {
                    result = performAddition(polyList1, polyList2);
                } else if (operator == '-') {
                    result = performSubtraction(polyList1, polyList2);
                } else if (operator == '*') {
                    result = performMultiplication(polyList1, polyList2);
                }

                String resultString = polynomialToString(result);

                writer.write(resultString + "\n");
            }
            scanner.close();
            writer.close();
            System.out.println("Everything is done");
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
            Term newTerm = new Term(coefficient, 0, 0, 0); // Initialize with exponents as 0

            String[] variables = termWithoutCoeff.split("(?=[xyz])");
            for (String var : variables) {
                int exponent = 1;
                if (var.contains("^")) {
                    String[] parts = var.split("\\^");
                    var = parts[0];
                    exponent = Integer.parseInt(parts[1]);
                }
                if (var.equals("x")) {
                    newTerm = new Term(newTerm.getCoefficient(), exponent, newTerm.getExponentY(), newTerm.getExponentZ());
                } else if (var.equals("y")) {
                    newTerm = new Term(newTerm.getCoefficient(), newTerm.getExponentX(), exponent, newTerm.getExponentZ());
                } else if (var.equals("z")) {
                    newTerm = new Term(newTerm.getCoefficient(), newTerm.getExponentX(), newTerm.getExponentY(), exponent);
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
            Term term1 = current1.getTerm();
            Term term2 = current2.getTerm();

            // Compare the exponents of the terms
            int comparisonResult = compareTerms(term1, term2);

            if (comparisonResult == 0) {
                // If exponents are equal, add the coefficients
                int sumCoefficients = term1.getCoefficient() + term2.getCoefficient();
                if (sumCoefficients != 0) {
                    result.insertLast(new Node(new Term(sumCoefficients, term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (comparisonResult < 0) {
                // If exponent of the first term is smaller, add it to the result
                result.insertLast(new Node(new Term(term1.getCoefficient(), term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
                current1 = current1.getNext();
            } else {
                // If exponent of the second term is smaller, add it to the result
                result.insertLast(new Node(new Term(term2.getCoefficient(), term2.getExponentX(), term2.getExponentY(), term2.getExponentZ())));
                current2 = current2.getNext();
            }
        }

        // Add remaining terms from polyList1
        while (current1 != null) {
            Term term1 = current1.getTerm();
            result.insertLast(new Node(new Term(term1.getCoefficient(), term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
            current1 = current1.getNext();
        }

        // Add remaining terms from polyList2
        while (current2 != null) {
            Term term2 = current2.getTerm();
            result.insertLast(new Node(new Term(term2.getCoefficient(), term2.getExponentX(), term2.getExponentY(), term2.getExponentZ())));
            current2 = current2.getNext();
        }

        return result;
    }

    private static int compareTerms(Term term1, Term term2) {
        if (term1.getExponentX() != term2.getExponentX()) {
            return Integer.compare(term1.getExponentX(), term2.getExponentX());
        } else if (term1.getExponentY() != term2.getExponentY()) {
            return Integer.compare(term1.getExponentY(), term2.getExponentY());
        } else {
            return Integer.compare(term1.getExponentZ(), term2.getExponentZ());
        }
    }


    private static LinkedList performSubtraction(LinkedList polyList1, LinkedList polyList2) {
        LinkedList result = new LinkedList();
        Node current1 = polyList1.getHead();
        Node current2 = polyList2.getHead();

        while (current1 != null && current2 != null) {
            Term term1 = current1.getTerm();
            Term term2 = current2.getTerm();

            if (term1.getExponentX() == term2.getExponentX() &&
                    term1.getExponentY() == term2.getExponentY() &&
                    term1.getExponentZ() == term2.getExponentZ()) {
                int diffCoefficients = term1.getCoefficient() - term2.getCoefficient();
                if (diffCoefficients != 0) {
                    result.insertLast(new Node(new Term(diffCoefficients, term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
                }
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else if (term1.getExponentX() > term2.getExponentX() ||
                    (term1.getExponentX() == term2.getExponentX() && term1.getExponentY() > term2.getExponentY()) ||
                    (term1.getExponentX() == term2.getExponentX() && term1.getExponentY() == term2.getExponentY() && term1.getExponentZ() > term2.getExponentZ())) {
                result.insertLast(new Node(new Term(term1.getCoefficient(), term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
                current1 = current1.getNext();
            } else {
                result.insertLast(new Node(new Term(-term2.getCoefficient(), term2.getExponentX(), term2.getExponentY(), term2.getExponentZ())));
                current2 = current2.getNext();
            }
        }

        while (current1 != null) {
            Term term1 = current1.getTerm();
            result.insertLast(new Node(new Term(term1.getCoefficient(), term1.getExponentX(), term1.getExponentY(), term1.getExponentZ())));
            current1 = current1.getNext();
        }

        while (current2 != null) {
            Term term2 = current2.getTerm();
            result.insertLast(new Node(new Term(-term2.getCoefficient(), term2.getExponentX(), term2.getExponentY(), term2.getExponentZ())));
            current2 = current2.getNext();
        }

        return result;
    }



    private static LinkedList performMultiplication(LinkedList polyList1, LinkedList polyList2) {
        LinkedList result = new LinkedList();
        Node current1 = polyList1.getHead();
        while (current1 != null) {
            Node current2 = polyList2.getHead();
            while (current2 != null) {
                int coef1 = current1.getTerm().getCoefficient();
                int coef2 = current2.getTerm().getCoefficient();
                int productCoefficients = coef1 * coef2;
                int expX = current1.getTerm().getExponentX() + current2.getTerm().getExponentX();
                int expY = current1.getTerm().getExponentY() + current2.getTerm().getExponentY();
                int expZ = current1.getTerm().getExponentZ() + current2.getTerm().getExponentZ();

                // Look for a term with the same exponents
                Node existingTerm = result.getNodeWithKey(expX, expY, expZ);
                if (existingTerm != null) {
                    existingTerm.setCoefficient(existingTerm.getTerm().getCoefficient() + productCoefficients);
                } else {
                    result.insertLast(new Node(new Term(productCoefficients, expX, expY, expZ)));
                }

                current2 = current2.getNext();
            }
            current1 = current1.getNext();
        }
        return result;
    }

    private static String polynomialToString(LinkedList polyList) {
        StringBuilder builder = new StringBuilder();
        Node current = polyList.getHead();
        boolean isFirstTerm = true;
        while (current != null) {
            int coefficient = current.getTerm().getCoefficient();
            if (coefficient != 0) {
                if (!isFirstTerm && coefficient > 0) {
                    builder.append("+");
                }
                builder.append(coefficient);

                int exponentX = current.getTerm().getExponentX();
                if (exponentX > 0) {
                    builder.append("x");
                    if (exponentX > 1) {
                        builder.append("^").append(exponentX);
                    }
                }

                int exponentY = current.getTerm().getExponentY();
                if (exponentY > 0) {
                    builder.append("y");
                    if (exponentY > 1) {
                        builder.append("^").append(exponentY);
                    }
                }

                int exponentZ = current.getTerm().getExponentZ();
                if (exponentZ > 0) {
                    builder.append("z");
                    if (exponentZ > 1) {
                        builder.append("^").append(exponentZ);
                    }
                }
                isFirstTerm = false;
            }
            current = current.getNext();
        }
        // If all terms have been canceled out, return "0"
        if (builder.length() == 0) {
            return "0";
        }
        return builder.toString();
    }
}