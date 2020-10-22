package bullscows;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static int bull = 0;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int codeLength = 0;
        int unique = 0;

        System.out.println("Input the length of the secret code:");

        try {
            codeLength = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: isn't a valid number.\n");
            return;
        }

        System.out.println("Input the number of possible symbols in the code:");

        try {
            unique = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: isn't a valid number.\n");
            return;
        }

        if (codeLength < 1) {
            System.out.println("Error: code length should be greater then 0.\n");
            return;
        }

        if (codeLength > unique) {
            System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.\n", codeLength, unique);
            return;
        }

        if (unique > 36) {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", unique);
            return;
        }

        StringBuilder res = new StringBuilder();
        int size = 0;
        Set<Character> hash_Set = new HashSet<>();
        String available = "0123456789abcdefghijklmnopqrstuvwxyz";

        while (size < codeLength) {
            int index = ThreadLocalRandom.current().nextInt(0, unique);
            char next = available.charAt(index);

            if (hash_Set.contains(next)) {
                continue;
            }

            hash_Set.add(next);
            size++;
            res.append(next);
        }

        String stars = "*".repeat(Math.max(0, codeLength));

        if (unique > 9) {
            System.out.printf("The secret is prepared: %s (0-9, a-%c).\n", stars, available.charAt(unique - 1));
        }
        else {
            System.out.printf("The secret is prepared: %s (0-%c).\n", stars, available.charAt(unique - 1));
        }

        System.out.println("Okay, let's start a game!");
        System.out.println(res.toString());

        int turn = 1;
        char[] code = res.toString().toCharArray();

        while (bull != codeLength) {
            bull = 0;
            int cow = 0;

            System.out.printf("Turn %d:\n", turn);
            char[] guess = scanner.nextLine().toCharArray();

            for (int i = 0; i < codeLength; i++) {
                for (int j = 0; j < codeLength; j++) {
                    if (guess[i] == code[j] && i == j) {
                        bull++;
                    } else if (guess[i] == code[j] && i != j) {
                        cow++;
                    }
                }
            }

            turn++;
            String resultString;

            if (bull + cow == 0) {
                resultString = "None.";
            } else if (bull != 0) {
                resultString = bull + " bull(s).";
            } else if (cow != 0) {
                resultString = cow + " cow(s).";
            } else {
                resultString = bull + " bull(s) and " + cow + " cow(s).";
            }

            System.out.printf("Grade: %s\n", resultString);
        }

        System.out.println("Congratulations! You guessed the secret code.");
    }
}
