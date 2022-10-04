package org.example;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Test {

    static int hardestCardCounter;

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<String, String> cards = new LinkedHashMap<>();

        choiceTheActions(reader, cards);
    }

    public static void choiceTheActions(BufferedReader reader, Map<String, String> card) throws IOException {

        boolean exit = false;
        while (!exit) {

            System.out.println("Input the action (add, remove, import, export, ask, exit):");
            String userChoice = reader.readLine();

            switch (userChoice) {
                case "add" -> createCard(reader, card);
                case "remove" -> removeCard(reader, card);
                case "import" -> importCards(reader, card);
                case "export" -> exportCards(reader, card);
                case "ask" -> askHowManyCardsShow(reader, card);
                case "exit" -> {
                    System.out.println("Bye-bye!");
                    exit = true;
                }
                case "log" -> log(reader);
                case "hardest card" -> hardestCard();
                case "reset stats" -> resetStats();
                default -> System.out.println("Wrong choice");
            }
        }
    }

    public static void resetStats() {
        hardestCardCounter = 0;

    }

    public static void hardestCard() {

    }

    public static void log(BufferedReader reader) throws IOException {

        System.out.println("File name:");
        String fileName = reader.readLine();
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file, true);


        String userInput = reader.readLine();
        Scanner scanner = new Scanner(userInput);
    }

    public static void createCard(BufferedReader reader, Map<String, String> card) throws IOException {

        String question;
        String answer;

        System.out.println("The card:");
        String userQuestion = reader.readLine();

        if (!card.containsKey(userQuestion)) {
            question = userQuestion;

            System.out.println("The definition of the card:");
            String userAnswer = reader.readLine();

            if (!card.containsValue(userAnswer)) {
                answer = userAnswer;

                card.put(question, answer);
                System.out.printf("The pair (\"%s\":\"%s\") has been added.%n", question, answer);

            } else {
                System.out.println("The definition " + "\"" + userAnswer + "\"" + " already exists.");
            }
        } else {
            System.out.println("The card " + "\"" + userQuestion + "\"" + " already exists.");
        }
    }

    public static void removeCard(BufferedReader reader, Map<String, String> card) throws IOException {

        System.out.println("Which card?");
        String cardForDelete = reader.readLine();

        if (card.containsKey(cardForDelete)) {
            card.remove(cardForDelete);
            System.out.println("The card has been removed.");
        } else {
            System.out.println("Can't remove \"" + cardForDelete + "\": there is no such card.");
        }
    }

    public static void importCards(BufferedReader reader, Map<String, String> card) throws IOException {

        int counter = 0;

        System.out.println("File name:");
        String fileName = reader.readLine();

        writeValueFromFileToMap(card, fileName);

        File file = new File(fileName);

        if (file.exists()) {
            try (Scanner scan = new Scanner(file)) {
                while (scan.hasNext()) {
                    ++counter;
                    System.out.println(scan.nextLine());
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println(counter + " cards have been loaded.");
        } else {
            System.out.println("File not found.");
        }
    }

    public static void writeValueFromFileToMap(Map<String, String> card, String fileName) {
        try {
            File toRead = new File(fileName);
            FileInputStream inputStream = new FileInputStream(toRead);

            Scanner scanner = new Scanner(inputStream);

            String tempLine;
            while (scanner.hasNext()) {
                tempLine = scanner.nextLine();
                StringTokenizer tokenizer = new StringTokenizer(tempLine, " ", false);
                card.put(tokenizer.nextToken(), tokenizer.nextToken());
            }
            inputStream.close();
            scanner.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void exportCards(BufferedReader reader, Map<String, String> card) throws IOException {

        int counter = 0;
        System.out.println("File name:");
        String fileName = reader.readLine();

        try (FileWriter writer = new FileWriter(fileName)) {
            for (var entry : card.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\n");
                ++counter;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(counter + " cards have been saved.");
    }

    public static void askHowManyCardsShow(BufferedReader reader, Map<String, String> card) throws IOException {

        int counter = 1;
        Map<Integer, Map<String, String>> askMap = new LinkedHashMap<>();

        for (var entry : card.entrySet()) {

        }

        System.out.println("How many times to ask?");
        int size = Integer.parseInt(reader.readLine());

        checkUserAnswer(reader, card);
    }

    public static String checkRightAnswerForContainsInMap(BufferedReader reader, Map<String, String> card) throws IOException {
        boolean checkAnswer = false;
        String answer = "";

        System.out.println("The definition of the card:");
        while (!checkAnswer) {
            answer = reader.readLine();
            if (card.containsValue(answer)) {
                System.out.println("The term " + "\"" + answer + "\"" + " already exists. Try again:");
                checkAnswer = true;
            } else {
                checkAnswer = true;
            }
        }
        return answer;
    }

    public static String checkQuestionForContainsInMap(BufferedReader reader, Map<String, String> card) throws IOException {
        boolean checkQuestion = false;
        String question = "";

        System.out.println("The card:");
        while (!checkQuestion) {
            question = reader.readLine();
            if (card.containsKey(question)) {
                System.out.println("The card " + "\"" + question + "\"" + " already exists.");
                checkQuestion = true;
            } else {
                checkQuestion = true;
            }
        }
        return question;
    }

    public static void checkUserAnswer(BufferedReader reader, Map<String, String> card) throws IOException {

        String tempKey = "";
        for (var entry : card.entrySet()) {

            System.out.println("Print the definition of " + "\"" + entry.getKey() + "\"" + ":");
            String userAnswer = reader.readLine();

            if (userAnswer.equalsIgnoreCase(card.get(entry.getKey()))) {
                System.out.println("Correct!");

            } else if (card.containsValue(userAnswer)) {
                for (var map : card.entrySet()) {
                    if (map.getValue().equalsIgnoreCase(userAnswer)) {
                        tempKey = map.getKey();
                    }
                }
                System.out.println("Wrong. The right answer is " + "\"" + card.get(entry.getKey()) + "\"" +
                        " but your definition is correct for " + "\"" + tempKey + "\"" + ".");
                counterHardestCard(entry);
            } else {
                System.out.println("Wrong. The right answer is " + "\"" + card.get(entry.getKey()) + "\"");
                counterHardestCard(entry);
            }
        }
    }

    public static void counterHardestCard(Map.Entry<String, String> entry) {

        int counter = 0;
        Map<String, Integer> hardestCard = new LinkedHashMap<>();

        if (hardestCard.containsKey(entry.getKey())) {
            int value = hardestCard.get(entry.getKey());
            ++value;
            hardestCard.put(entry.getKey(), value);
        } else {
            hardestCard.put(entry.getKey(), counter);
        }
    }
}
