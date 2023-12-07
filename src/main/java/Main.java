/*
    Author: Jordan Arroyo
    Purpose: Java program that accepts input and performs spell checking against a word database.
 */
import entity.Words;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public static void main(String[] args) {

        // Added to unclutter console window
        disableLogging();
        Scanner input = new Scanner(System.in);

        boolean continueToRun = true;

        do {

            int choice;

            choice = processMenu();
            switch (choice) {

                case 1 -> {
                    System.out.print("Enter a phrase: ");
                    String phrase = input.nextLine();
                    List<String> misspelled = new ArrayList<>();

                    for (String word : convertToList(phrase)) {
                        if (wordExist(word.toLowerCase()))
                            misspelled.add(word);
                    }

                    if (misspelled.isEmpty()) {
                        System.out.println("Spell Check Complete! No errors found!");
                    } else {
                        System.out.print("Spell Check Complete!\nWord(s) not found in dictionary: ");
                        for (String incorrect : misspelled) {
                            System.out.print(incorrect + " ");
                        }
                        System.out.println();
                    }
                }

                case 2 -> {
                    System.out.println("Enter a word: ");
                    String word = input.nextLine();

                    if (wordExist(word.toLowerCase()))
                        System.out.print("Spell Check Complete!\nWord not found in dictionary: " + word);
                    else
                        System.out.println("Spell Check Complete! No errors found!");

                    System.out.println();
                }

                case 3 -> {
                    System.out.println("Enter a word: ");
                    String word = input.nextLine();
                    System.out.println(getWord(word));

                }

                case 4 -> getAll();

                case 5 -> {
                    System.out.println("You selected to EXIT!");
                    continueToRun = false;

                }
            }
        }
        while (continueToRun);
        System.out.println("Thank you for using the Spell Checker Program!");

        input.close();
    }


    // User input is used to retrieve a word and its ID from the database
    public static String getWord(String word) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT w from Words w WHERE w.word = :word";

        TypedQuery<Words> tq = em.createQuery(query, Words.class);
        tq.setParameter("word", word);
        Words words;
        try {
            words = tq.getSingleResult();
            return "[Id: " + words.getWordId() + " Word: " + words.getWord() + "]";

        } catch (Exception ex) {
           return "Word does not exist in database!";
        } finally {
            em.close();
        }

    }

    // Checks if user input exist in the database and returns result
    public static boolean wordExist(String phrase) {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT w from Words w WHERE w.word = :word";

        TypedQuery<Words> tq = em.createQuery(query, Words.class);
        tq.setParameter("word", phrase);
        Words words;
        try {
            words = tq.getSingleResult();
            if (Objects.equals(words.getWord(), phrase)) {
                return false;
            }
        } catch (Exception ex) {
            return true;
        } finally {
            em.close();
        }
        return true;
    }

    // Retrieves all data from the database
    public static void getAll() {
        EntityManager em = emf.createEntityManager();
        String query = "SELECT w FROM Words w WHERE w.wordId IS NOT NULL order by w.word asc";
        TypedQuery<Words> tq = em.createQuery(query, Words.class);
        List<Words> word;
        try {
            word = tq.getResultList();
            word.forEach(str -> System.out.println("[" + str.getWord() + "]"));

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            em.close();
        }

    }

    // Converts user String input into a list of seperated words
    public static List<String> convertToList(String phrase) {

        return Arrays.asList(phrase.split("\\s+"));

    }

    // Disabled hibernate logging to unclutter console window
    private static void disableLogging() {
        LogManager logManager = LogManager.getLogManager();
        Logger logger = logManager.getLogger("");
        logger.setLevel(Level.SEVERE);
    }

    // Menu
    public static int processMenu() {

        Scanner input = new Scanner(System.in);

        int response;
        do {
            System.out.println("\nWelcome to Spell Check!");
            System.out.println("-------------------------");
            System.out.println("Please select from the menu below.");
            System.out.println("----------------------------------");
            System.out.println("Enter 1 to Spell Check Phrase ");
            System.out.println("Enter 2 to Spell Check Word");
            System.out.println("Enter 3 to Get Word from Database");
            System.out.println("Enter 4 to Get All Words from Database");
            System.out.println("Enter 5 to EXIT");
            System.out.print(">>> ");
            response = input.nextInt();
            input.nextLine();
            if (response < 1 || response > 5) {
                System.out.println("Invalid menu selection. Please try again.\n");
            }
        } while (response < 1 || response > 5);

        return response;
    }
}
