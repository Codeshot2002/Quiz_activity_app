package com.example.quiz_activity_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    ArrayList<ModelClass_Questions> listOfQ;
    ModelClass_Questions Model_class;

    TextView question_text,option1,option2,option3,option4;
    CardView question_c;
    Button c1,c2,c3,c4;
    Button next_button;

    private int correct_count = 0;
    private int index = 0;
    private int wrong_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing the card elements (Find view by id)
        find_elements_id();

        //Setting up the data in the model class
        listOfQ = new ArrayList<>();
        listOfQ.add(new ModelClass_Questions("What is the age category of adulthood", "10-12","12-18","18+","60+", 3));
        listOfQ.add(new ModelClass_Questions("CEO of google","Sundar pichai","Umang Sharma","Vansh chaudhary","Manish chauhan",1));
        listOfQ.add(new ModelClass_Questions("How many days are there in a week?","4","1","7","8",3));
        Collections.shuffle(listOfQ);

        //Getting the data from the model_question class and storing it in the model_class with index(0) and as the index will updated the question will get changed!
        Model_class = listOfQ.get(index);

        //Replacing the text with the questions and options fetched from the model class
        set_elements();
    }

    //This method find the id for view in the activity layout
    private void find_elements_id(){
        question_text = findViewById(R.id.question_text);

        question_c = findViewById(R.id.questions_card);
        c1 = findViewById(R.id.option1_card);
        c2 = findViewById(R.id.option2_card);
        c3 = findViewById(R.id.option3_card);
        c4 = findViewById(R.id.option4_card);

        next_button = findViewById(R.id.next_button1);
    }
    //This method replaces the text in place of the questions from the model class
    private void set_elements(){
        question_text.setText(Model_class.getQuestion());
        c1.setText(Model_class.getOption1());
        c2.setText(Model_class.getOption2());
        c3.setText(Model_class.getOption3());
        c4.setText(Model_class.getOption4());
    }

    public void resetColor(){
        c1.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        c2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        c3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        c4.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
    }
    public void setColor(Button button){
        resetColor();
        button.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
    }

    public void prev_button_colorchange(){
        int user_answer = Model_class.getUser_answer();
        switch (user_answer) {
            case 1:
                setColor(c1);
                break;
            case 2:
                setColor(c2);
                break;
            case 3:
                setColor(c3);
                break;
            case 4:
                setColor(c4);
                break;
        }
    }

    private void Quizfinished() {
        //This calculates the total score
        for(int i=0;i<listOfQ.size();i++)
        {
            if(listOfQ.get(i).getUser_answer()==listOfQ.get(i).getAns())
            {
                correct_count++;
            }
            else{
                wrong_count++;
            }
        }
        //Start intent of the result or review page activity for eg(Intent intent = new Intent(Mainactivity.this, ReviewActivity.class);
        String score = correct_count + "/" + listOfQ.size();
        Toast.makeText(MainActivity.this,"Score : " + score,Toast.LENGTH_SHORT).show();
        correct_count = 0; //this resets the value
    }

    public void next_button_onclick(View view) {
        if(index<listOfQ.size()) {
            System.out.println("Index before" + index);
            if (Model_class.getUser_answer() == Model_class.getAns()) //checks if user answer is equal to answer
            {
                System.out.println("COrrect answer");
            }
            else { //wrong answer statement
                System.out.println("wrong");
            }
            resetColor();
            if(index<listOfQ.size()-1) //stops the index from incrementing once it reaches the length
            {
                index++;
                Model_class = listOfQ.get(index);
                set_elements();
            }
            else{ //at the end of the quiz
                Quizfinished();
            }
            if(index == listOfQ.size()-1){
               next_button.setText(getResources().getText(R.string.next_to_submit));
            }
            prev_button_colorchange();
            System.out.println("the value of user answered of this index is : " + Model_class.getUser_answer());
        }
    }
    public void prev_button_onclick(View view) {
        if(index>0){
            //change the submit button to next button
            if(index==listOfQ.size()-1){
                next_button.setText(getResources().getText(R.string.submit_to_next));
            }

            index--;
            Model_class = listOfQ.get(index);
            set_elements();

            //change color of selected option
            prev_button_colorchange();
        }
        else{
            Toast.makeText(this, "You are on the first question!", Toast.LENGTH_SHORT).show();
        }
    }
    /*Here are option button functions
    1) sets the user answer
    2) set color
     */

    public void option1_click(View view) {
        Model_class.setUser_answer(1);
        setColor(c1);
        System.out.println(Model_class.getUser_answer());
    }

    public void option2_click(View view) {
        Model_class.setUser_answer(2);
        setColor(c2);
        System.out.println(Model_class.getUser_answer());
    }

    public void option3_click(View view) {
        Model_class.setUser_answer(3);
        setColor(c3);
        System.out.println(Model_class.getUser_answer());
    }
    public void option4_click(View view) {
        Model_class.setUser_answer(4);
        resetColor();
        setColor(c4);
        System.out.println(Model_class.getUser_answer());
    }

}