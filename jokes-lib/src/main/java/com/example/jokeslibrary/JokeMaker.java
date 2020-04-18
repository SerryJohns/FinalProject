package com.example.jokeslibrary;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class JokeMaker {
    private static final Object LOCK = new Object();
    private static JokeMaker instance;
    private List<Joke> jokesList = Collections.emptyList();

    private JokeMaker() {
        setJokesList();
    }

    public static JokeMaker getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new JokeMaker();
            }
        }
        return instance;
    }

    private void setJokesList() {
        this.jokesList = Arrays.asList(
                new Joke("computer",
                        "Whoever said that the definition of insanity is doing " +
                                "the same thing over and over again and expecting different results " +
                                "has obviously never had to reboot a computer."),
                new Joke("computer",
                        "Did you hear about the monkeys who shared an Amazon account? " +
                                "They were Prime mates."),
                new Joke("coffee",
                        "Barista: How do you take your coffee? Me: Very, very seriously."),
                new Joke("coffee",
                        "Sleep is a weak substitute for coffee.")
        );
    }

    public Joke getRandomJoke() {
        int randomIndex = new Random().nextInt(jokesList.size());
        return jokesList.get(randomIndex);
    }
}
