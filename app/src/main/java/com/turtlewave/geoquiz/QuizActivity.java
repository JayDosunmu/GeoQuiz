package com.turtlewave.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";

    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private TextView questionTextView;
    private Quiz quiz;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        quiz = new Quiz();

        if (savedInstanceState != null) {
            quiz.onRestoreInstanceState(savedInstanceState);
        }

        questionTextView = (TextView) findViewById(R.id.question_text_view);

        trueButton = (Button) findViewById(R.id.true_button);
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
                checkQuizState();
                quiz.nextQuestion();
                updateQuestion();
            }
        });

        falseButton = (Button) findViewById(R.id.false_button);
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
                checkQuizState();
                quiz.nextQuestion();
                updateQuestion();
            }
        });

        prevButton = (ImageButton) findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz.prevQuestion();
                updateQuestion();
            }
        });

        nextButton = (ImageButton) findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quiz.nextQuestion();
                updateQuestion();
            }
        });

        updateQuestion();
    }

    private void updateQuestion() {
        int question = quiz.getCurrentQuestion().getTextResId();
        questionTextView.setText(question);
        Log.d(TAG, "question was answered: " + quiz.currentQuestionIsAnswered());
        if (quiz.currentQuestionIsAnswered()) {
            trueButton.setEnabled(false);
            falseButton.setEnabled(false);
        } else {
            trueButton.setEnabled(true);
            falseButton.setEnabled(true);
        }
    }

    private void checkAnswer(boolean userPressedTrue) {
        int message = 0;
        if (quiz.checkAnswer(userPressedTrue)) {
            message = R.string.correct_toast;
        } else {
            message = R.string.incorrect_toast;
        }
        Toast.makeText(QuizActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    public void checkQuizState() {
        if (quiz.isComplete()) {
            Toast.makeText(QuizActivity.this,
                    String.format(
                            "Quiz Complete! You scored %d%% (%d/%d)",
                            quiz.score(),
                            quiz.getCorrect(),
                            quiz.numberOfQuestions()),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
//        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
//        Log.i(TAG, "onInstanceState");
        quiz.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
//        Log.d(TAG, "onSop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        Log.d(TAG, "onDestroy() called");
    }
}