import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
    Scanner scanner = new Scanner(System.in);
    RNG rng = new RNG();
    Buffer buffer = new Buffer();
    Multiple multiple = new Multiple();

    String choice;
    String playerName;
    int gameTotal;
    ArrayList<Buffer> multipleList = new ArrayList<Buffer>();
    int customGameTotal;
    String selectedLine;
    boolean ch = false;



    public boolean bufferOverTotal() {
        if (gameTotal >= customGameTotal) {
            return true;
        } else
            return false;
    }

    //HD
    public void chooseMultipleLine() {
        String[] lines = rng.readMultiplesFile();
        System.out.println("Choose multiples: ");
        for (int i = 0; i < lines.length; i++) {
            System.out.println(String.valueOf(i + 1) + " - " + lines[i]);
        }
        int choice;
        while (true) {
            System.out.println("Choose multiple line: ");
            choice = scanner.nextInt();
            if (choice > 0 && choice <= lines.length) {
                break;
            }
        }
        selectedLine = lines[choice - 1];
        System.out.println(selectedLine);
    }


    public void displayWelcomeMessage()
    {
        System.out.println("Hey there. Welcome to 256 with Arraylists! ");
    }


    //HD:
    public void getGameTotal() {
        try {
            int gTot;
            System.out.println("Please enter game total: ");
            gTot = scanner.nextInt();
            while (gTot < 32 || (gTot % 8 != 0)) {
                System.out.println("Game Total should be more than 32 and a multiple of 8. Please enter Game Total again: ");
                gTot = scanner.nextInt();
            }
            customGameTotal = gTot;
            System.out.println("Game Total is " + gTot);
        }catch (InputMismatchException e){
            System.out.println("Please only enter integer");
        }
    }


    private void merge(int bNo) {
        ch = false;
        switch (bNo) {
            case 1: {
                if (buffer.listB1.contains(gameTotal)) {
                    int gT = gameTotal;
                    int freq = Collections.frequency(buffer.listB1, gameTotal);
                    if (freq > 0) {
                        gameTotal = gameTotal * (freq + 1);
                    }
                    buffer.listB1.remove(new Integer(gT));
                }
                break;
            }
            case 2: {
                if (buffer.listB2.contains(gameTotal)) {
                    int gT = gameTotal;
                    int freq = Collections.frequency(buffer.listB2, gameTotal);
                    if (freq > 0) {
                        gameTotal = gameTotal * (freq + 1);
                    }
                    buffer.listB2.remove(new Integer(gT));
                }
                break;
            }
        }
        ch = true;
    }


    public void playerTurn() {

        //Buffer One  display
        if (buffer.listB1.size() > 0)
            System.out.println("BUFFER LEFT: ");
        for (int i = 0; i < buffer.listB1.size(); i++) {
            System.out.println((i + 1) + " - " + buffer.listB1.get(i));
        }

        //Buffer two display
        if (buffer.listB2.size() > 0)
            System.out.println("BUFFER RIGHT: ");
        for (int i = 0; i < buffer.listB2.size(); i++) {
            System.out.println((i + 1) + " - " + buffer.listB2.get(i));
        }

        System.out.println("Choose your action: ");
        System.out.println("1. Split Right ->");
        System.out.println("2. Merge Right <-<-");
        System.out.println("3. Split Left <-");
        System.out.println("4. Merge Left ->->");
        choice = scanner.next();

        while (!choice.equalsIgnoreCase("1") && !choice.equalsIgnoreCase("2") &&
                !choice.equalsIgnoreCase("3") && !choice.equalsIgnoreCase("4")) {
            System.out.println("PLease enter a number from 1 to 4 ");
            System.out.println("Choose your action: ");
            System.out.println("1. Split Right ->");
            System.out.println("2. Merge Right <-<-");
            System.out.println("3. Split Left <-");
            System.out.println("4. Merge Left ->->");
            choice = scanner.next();
        }
    }


    private void printOutcome() {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(
                    new File("outcome.txt"));

            writer.write(playerName + " has won the game, with the highest achieved score of " + customGameTotal);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            writer.close();;
        }
    }


    private void split(int bNo) {
        if (bNo == 1) {
            buffer.addToBufferOne(gameTotal);
        } else if (bNo == 2) {
            buffer.addToBufferTwo(gameTotal);
        }
        if (bufferOverTotal()) {
            System.out.println("Game is OVER!");
        } else {
            System.out.println(gameTotal + " has been added to buffer " + bNo);
        }
        gameTotal = 0;
        ch = false;
    }



    public void startGame() {
        selectedLine = rng.selectMultipleLine();
        while (buffer.listB1.size() < 6 && buffer.listB2.size() < 4 && gameTotal < customGameTotal) {
            if (!ch) {
                gameTotal = rng.getMultiple(selectedLine);
            }
            System.out.println("\n\nThe multiple in gameTotal is " + gameTotal);
            playerTurn();
            if (choice.equalsIgnoreCase("1")) {
                split(2);
            } else if (choice.equalsIgnoreCase("2")) {
                merge(2);
            } else if (choice.equalsIgnoreCase("3")) {
                split(1);
            } else if (choice.equalsIgnoreCase("4")) {
                merge(1);
            }

        }
        System.out.println("GAME IS OVER! GAME TOTAL: " + gameTotal);
        if (bufferOverTotal()) {
            System.out.println("YOU WIN.");
            printOutcome();
        } else {
            System.out.println("BUFFER IS FULL. YOU LOSE.");
        }
    }



    public void takePLayerName() {
        displayWelcomeMessage();
        String name;
        System.out.println("Please enter your name: ");
        name = scanner.next();

        while (!name.matches("[A-Za-z]+") || name.length() > 10 || name.length() < 3) {
            System.out.println("Your name should have 3 to 10 characters and cannot be blank. Please enter your name again: ");
            name = scanner.next();
        }
        playerName = name;

    }



    public static void main(String[] args) {
        // write your code here
        Game game = new Game();
        game.takePLayerName();
        //HD:
        game.getGameTotal();
        game.chooseMultipleLine();
        //--
        game.startGame();
    }


}
