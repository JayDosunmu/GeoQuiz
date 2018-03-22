package com.turtlewave.geoquiz;

import android.os.Bundle;

/**
 * Created by Jay on 3/21/2018.
 */

public class Quiz {
    private static final String KEY_INDEX = "index";
    private static final String KEY_CORRECT = "correct";
    private static final String KEY_ANSWERED_QUESTIONS = "answered_questions";

    private Question[] questions;
    private boolean[] answered_questions;
    private int currentIndex = 0;
    private int correct = 0;

    public Quiz() {
        questions = new Question[] {
                new Question(R.string.question_united_states, true),
                new Question(R.string.question_oceans, true),
                new Question(R.string.question_mideast, false),
                new Question(R.string.question_africa, false),
                new Question(R.string.question_asia, true),
        };
        answered_questions = new boolean[5];
    }

    public Question getCurrentQuestion() {
        return questions[currentIndex];
    }

    public boolean currentQuestionIsAnswered() {
        return answered_questions[currentIndex];
    }

    public void updateCurrentQuestion(int skip_num) {
        currentIndex = (currentIndex + questions.length + skip_num) % questions.length;
    }

    public void prevQuestion() {
        updateCurrentQuestion(-1);
    }

    public void nextQuestion() {
        updateCurrentQuestion(1);
    }

    public boolean checkAnswer(boolean userPressedTrue) {
        answered_questions[currentIndex] = true;
        boolean answered_correctly = questions[currentIndex].isAnswerTrue() == userPressedTrue;
        if (answered_correctly) correct++;
        return answered_correctly;
    }

    public boolean isComplete() {
        for(boolean is_answered:answered_questions) {
            if (!is_answered) {
                return false;
            }
        }
        return true;
    }

    public int numberOfQuestions() {
        return questions.length;
    }

    public int score() {
        return (int)((float) correct/questions.length * 100);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(KEY_INDEX, getCurrentIndex());
        savedInstanceState.putBooleanArray(KEY_ANSWERED_QUESTIONS, getAnswered_questions());
        savedInstanceState.putInt(KEY_CORRECT, getCorrect());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        this.currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        this.answered_questions = savedInstanceState.getBooleanArray(KEY_ANSWERED_QUESTIONS);
        this.correct = savedInstanceState.getInt(KEY_CORRECT, 0);
    }

    public Question[] getQuestions() {
        return questions;
    }

    public void setQuestions(Question[] questions) {
        this.questions = questions;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public boolean[] getAnswered_questions() {
        return answered_questions;
    }

    public void setAnswered_questions(boolean[] answered_questions) {
        this.answered_questions = answered_questions;
    }
}
