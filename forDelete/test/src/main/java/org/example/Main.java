package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Map<String, String> card = new LinkedHashMap<>();

        System.out.println("Input the number of cards:");
        int size = Integer.parseInt(reader.readLine());

        createCards(reader, card, size);
        checkUserAnswer(reader, card);
    }

    public static void checkUserAnswer(BufferedReader reader, Map<String, String> card) throws IOException {

        String tempKey = "";
        for (var entry : card.entrySet()) {

            System.out.println("Print the definition of " + "\"" + entry.getKey() + "\"" + ":");
            String userAnswer = reader.readLine();

            if (userAnswer.equalsIgnoreCase(card.get(entry.getKey()))) {
                System.out.println("Correct!");

            } else if (card.containsValue(userAnswer)) {
                tempKey = entry.getKey();  // сюда хочу записать ключ, значение которого не является правильным ответом, но является ответом на другой вопрос. Этот вопрос(ключ) мне и нужен!
                System.out.println("Wrong. The right answer is " + "\"" + card.get(entry.getKey()) + "\"" + // надо что-то сделать тут
                        " but your definition is correct for " + "\"" + tempKey + "\"" + ".");
            } else {
                System.out.println("Wrong. The right answer is " + "\"" + card.get(entry.getKey()) + "\"");
            }
        }
    }

    public static String checkQuestionForContainsInMap(BufferedReader reader, Map<String, String> card) throws IOException {
        boolean checkQuestion = false;
        String question = "";

        while (!checkQuestion) {
            question = reader.readLine();
            if (card.containsKey(question)) {
                System.out.println("The term " + "\"" + question + "\"" + " already exists. Try again:");
            } else {
                checkQuestion = true;
            }
        }
        return question;
    }

    public static String checkRightAnswerForContainsInMap(BufferedReader reader, Map<String, String> card) throws IOException {
        boolean checkAnswer = false;
        String answer = "";

        while (!checkAnswer) {
            answer = reader.readLine();
            if (card.containsValue(answer)) {
                System.out.println("The term " + "\"" + answer + "\"" + " already exists. Try again:");
            } else {
                checkAnswer = true;
            }
        }
        return answer;
    }

    public static void createCards(BufferedReader reader, Map<String, String> card, int size) throws IOException {

        for (int i = 0; i < size; i++) {

            System.out.println("Card #" + (i + 1));
            String question = checkQuestionForContainsInMap(reader, card);

            System.out.println("The definition for card #" + (i + 1));
            String answer = checkRightAnswerForContainsInMap(reader, card);

            card.put(question, answer);
        }
        card.forEach((K, V) -> System.out.println(K + " - " + V));
    }
}