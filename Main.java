import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

/* Class for the Hangman game. */
public class Main {
    /* Stores all the words to be used for game in a hashmap. <K: integer, V: word> */
    private static HashMap<Integer, String> words_hash = new HashMap<Integer, String>();

    /* Number of tries player has left. */
    private static int tries_left;

    /* Correct word as a string. */
    private static String word_str;

    /* Correct word as a char array. */
    private static char[] word;

    /* Guessed word so far, as a char array. */
    private static char[] guess;

    /* Sets up the word to be used and initializes the guess and number of tries left. */
    public static void setUpGame() throws FileNotFoundException {
        word_str = getWord();
        word = word_str.toCharArray();
        tries_left = 6;

        guess = new char[word.length];
        for (int i = 0; i < guess.length; i++) {
            guess[i] = '_';
        }
    }
    
    /* Retrieve a word to be used in game. */
    public static String getWord() throws FileNotFoundException {
        /********** NOTE: replace file path below with own path to words.txt **********/
        File word_file = new File("/Users/paulinetang/Desktop/projects/hangman/words.txt");

        Scanner word_scanner = new Scanner(word_file);
        
        int word_index = 0;
        while (word_scanner.hasNext()) {
            words_hash.put(word_index++, word_scanner.next());
        }
        word_scanner.close();

        Random random = new Random();
        int randint = random.nextInt(words_hash.size());

        String word_string = words_hash.get(randint);

        return word_string;
    }

    /* Determines if the game is won yet. */
    public static boolean isWin() {
        return Arrays.equals(guess, word);
    }

    /* Determines if the game has been lost. */
    public static boolean isLose() {
        return tries_left == 0 && !isWin();
    }

    /* Decrease the number of tries left and prints the response for incorrect inputs */
    public static void incorrectResponse() {
        tries_left--;
        if (isLose()) {
            System.out.println("You lost :(. You have ran out of tries and did not guess the word correctly.\nThe word is: " + word_str);
            drawHangman();
            System.exit(0);
        }
    }

    /* Checks if input is a valid lowercase letter or word. */
    public static boolean validateInput(String input) {
        char[] input_arr = input.toCharArray();

        for (int i = 0; i < input_arr.length; i++) {
            if (!Character.isLetter(input_arr[i]) || !Character.isLowerCase(input_arr[i])) {
                return false;
            }
        }

        return true;
    }

    /* Print the current layout of hangman characters. */
    public static void printLayout() {
        drawHangman();
        System.out.println();
        for (int i = 0; i < guess.length-1; i++) {
            System.out.print(guess[i] + " ");
        }
        System.out.print(guess[guess.length-1]);
        System.out.println();
    }

    /* Prints the current hangman based on the number of tries left.
        6: head, torso, left arm, right arm, left leg, right leg
        5: head, torso, left arm, right arm, left leg
        4: head, torso, left arm, right arm
        3: head, torso, left arm
        2: head, torso
        1: head
        0: deceased :(
    ____________
    |          |
    |          O
    |         /|\
    |        / | \
    |          |
    |         / \
    |        /   \
    |
    */
    public static void drawHangman() {
        if (tries_left == 6) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|         /|\\");
            System.out.println("|        / | \\");
            System.out.println("|          |");
            System.out.println("|         / \\");
            System.out.println("|        /   \\");
            System.out.println("|");
        } else if (tries_left == 5) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|         /|\\");
            System.out.println("|        / | \\");
            System.out.println("|          |");
            System.out.println("|         /");
            System.out.println("|        /");
            System.out.println("|");
        } else if (tries_left == 4) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|         /|\\");
            System.out.println("|        / | \\");
            System.out.println("|          |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } else if (tries_left == 3) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|         /|");
            System.out.println("|        / |");
            System.out.println("|          |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } else if (tries_left == 2) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|          |");
            System.out.println("|          |");
            System.out.println("|          |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } else if (tries_left == 1) {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|          O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        } else {
            System.out.println("____________");
            System.out.println("|          |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
        }
    }

    /* Run the program. */
    public static void main(String[] args) throws FileNotFoundException {
        // new Hangman();
        setUpGame();

        Scanner stdin = new Scanner(System.in);

        /* Play the game! */
        System.out.println("Beginning a game of Hangman...\n.\n.\n.");
        System.out.println("The number of letters in this word is: " + (word.length));
        System.out.println(".\n.\n.\nPlease enter a letter");
        printLayout();

        while (!isWin()) {
            String input = stdin.nextLine();
            while (!validateInput(input)) {
                System.out.println("Invalid input. Please enter a valid lowercase letter or word.");
                input = stdin.nextLine();
            }
            
            /* Player guessed a letter */
            if (input.length() == 1) {
                char letter = input.charAt(0);
                boolean exists = false;
        
                /* Determine if our guess is correct, and update our current guess word if applicable. */
                for (int i = 0; i < word.length; i++) {
                    if (word[i] == letter) {
                        guess[i] = letter;
                        exists = true;
                    }
                }
        
                /* Produce response corresponding to the guess. */
                if (!exists) {
                    incorrectResponse();
                    System.out.println("The letter does not exists in the word. Please try another letter. Number of guesses left: " + (tries_left));
                    System.out.println();
                    printLayout();
                } else {
                    if (isWin()) {
                        System.out.println("You won! The word is: " + word_str);
                        System.exit(0);
                    }
                    System.out.println("You guessed a letter correctly! Please enter another letter. Number of guesses left: " + (tries_left));
                    System.out.println();
                    printLayout();
                }
            } else { /* player guessed a word */
                if (input.length() != word.length) {
                    incorrectResponse();
                    System.out.println("The guessed word is incorrect. Please try another letter or guess another word. Number of guesses left: " + (tries_left));
                    System.out.println();
                    printLayout();
                } else {
                    boolean guessed_incorrectly = false;
                    for (int i = 0; i < word.length; i++) {
                        if (word[i] != input.charAt(i)) {
                            guessed_incorrectly = true;
                        }
                    }

                    if (guessed_incorrectly) {
                        incorrectResponse();
                        System.out.println("The guessed word is incorrect. Please try another letter or guess another word. Number of guesses left: " + (tries_left));
                        System.out.println();
                        printLayout();
                    } else {
                        System.out.println("You won! The word is: " + word_str);
                        System.exit(0);
                    }
                }
            }
        }
        stdin.close();
    }
}