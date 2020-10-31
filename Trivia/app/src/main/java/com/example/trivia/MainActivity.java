package com.example.trivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trivia.data.AnswerListAsyncResponse;
import com.example.trivia.data.QuestionBank;
import com.example.trivia.model.Question;
import com.example.trivia.model.Score;
import com.example.trivia.utils.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String MESSAGE_ID = "id";
    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private int currentQuestionIndex=0;
    private List<Question> questionList;
    private TextView scoreText;
    private Button saveButton;
    private int scoreCounter=0;
    private Score score;
    private Prefs prefs;
    private TextView highestScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextview=findViewById(R.id.question_textview);
        questionCounterTextview=findViewById(R.id.counter_text);
        trueButton=findViewById(R.id.true_button);
        falseButton=findViewById(R.id.false_button);
        nextButton=findViewById(R.id.next_button);
        prevButton=findViewById(R.id.prev_button);
        scoreText=findViewById(R.id.score_text);
        saveButton=findViewById(R.id.save_button);
        highestScore=findViewById(R.id.highest_score);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        score = new Score();
        prefs = new Prefs(MainActivity.this);
        currentQuestionIndex=prefs.getState();

        scoreText.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        highestScore.setText(MessageFormat.format("Highest Score: {0}", String.valueOf(prefs.getHighScore())));
//        scoreText.setText("Score: "+ score);

//        SharedPreferences getSharedPrefs=getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
//        String value = getSharedPrefs.getString("score","Nothing YET");
//        scoreText.setText(value);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextview.setText(MessageFormat.format("{0} / {1}", currentQuestionIndex, questionArrayList.size()));
                //Log.d("Question", "processFinished: "+questionArrayList);
            }
        });
        //Log.d("Main", "onCreate: "+questionList);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.prev_button:
                if(currentQuestionIndex>=1){
                    currentQuestionIndex=(currentQuestionIndex-1)%questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next_button:
//                Log.d("HighScore", "onClick: "+ prefs.getHighScore());
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
//            case R.id.save_button:
//                String message=scoreText.getText().toString().trim();
//                SharedPreferences sharedPreferences = getSharedPreferences(MESSAGE_ID,MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("score",message);
//                editor.apply();
//                break;
        }
    }

    @Override
    protected void onPause() {
        prefs.saveHighestScore(score.getScore());
        prefs.setState(currentQuestionIndex);
        super.onPause();

    }

    private void addPoints(){
        scoreCounter+=100;
        score.setScore(scoreCounter);
        scoreText.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        Log.d("Score: ", "addPoints: "+ score.getScore());
    }

    private void deletePoints(){
        scoreCounter-=100;
        if(scoreCounter > 0){
            score.setScore(scoreCounter);
            scoreText.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        } else {
            scoreCounter=0;
            score.setScore(scoreCounter);
            scoreText.setText(MessageFormat.format("Score: {0}", String.valueOf(score.getScore())));
        }
    }

    private void updateQuestion(){
        String question=questionList.get(currentQuestionIndex).getAnswer();
        questionTextview.setText(question);
        questionCounterTextview.setText(MessageFormat.format("{0} / {1}", currentQuestionIndex + 1, questionList.size()));
    }
    private void checkAnswer(boolean userChooseCorrect){
        boolean answerIsTrue=questionList.get(currentQuestionIndex).isAnswerTrue();
        int toastMessageId=0;
        if(userChooseCorrect==answerIsTrue){
            fadeView();
            addPoints();

//            score+=100;
//            String value="Score: "+ score;
//            scoreText.setText(value);
            toastMessageId=R.string.correct_answer;
        } else {
            shakeAnimation();
            deletePoints();
//            if(score>0){
//                score-=100;
//                String value="Score: "+ score;
//                scoreText.setText(value);
//            } else {
//                Toast.makeText(MainActivity.this,"Not Enough Score",Toast.LENGTH_SHORT).show();
//            }
            toastMessageId=R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this,toastMessageId,Toast.LENGTH_SHORT).show();
    }
    private void fadeView(){
        final CardView cardView=findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);//opacity from 1 to 0
        //Visible to invisible
        alphaAnimation.setDuration(350);//ms
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);//fade in and fade out
        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    private void shakeAnimation(){
        Animation shake= AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        final CardView cardView=findViewById(R.id.cardView);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                currentQuestionIndex=(currentQuestionIndex+1)%questionList.size();
                updateQuestion();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
