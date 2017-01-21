package com.problemonute.problemonute.viewmodels;

import android.databinding.ObservableField;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.problemonute.problemonute.models.Answer;
import com.problemonute.problemonute.models.Question;
import com.problemonute.problemonute.models.User;

import java.util.Random;

/**
 * Created by Hercson on 9/1/2017.
 */

public class ProblemViewModel {
    private User usr;
    private Question q;
    private Answer a1;
    private Answer a2;
    private Answer a3;
    private Answer a4;
    private static final String TAGLOG = "firebase-db";
    private static final Random rnd = new Random();
    private static int randChild = (int)(rnd.nextDouble() * 20 + 1);
    private final ObservableField<User> user = new ObservableField<>();
    private final ObservableField<Question> question = new ObservableField<>();
    private final ObservableField<Answer> answer1 = new ObservableField<>();
    private final ObservableField<Answer> answer2 = new ObservableField<>();
    private final ObservableField<Answer> answer3 = new ObservableField<>();
    private final ObservableField<Answer> answer4 = new ObservableField<>();

    public ProblemViewModel() {

    }

    public ProblemViewModel(User user){this.user.set(user);}

    public ProblemViewModel(Question question) {
        this.question.set(question);
    }

    public ProblemViewModel(Answer answer1, Answer answer2, Answer answer3, Answer answer4) {
        this.answer1.set(answer1);
        this.answer2.set(answer2);
        this.answer3.set(answer3);
        this.answer4.set(answer4);
    }

    public ObservableField<User> getUser(){
        return user;
    }
    public ObservableField<Question> getQuestion() {
        return question;
    }
    public ObservableField<Answer> getAnswer1() {
        return answer1;
    }
    public ObservableField<Answer> getAnswer2() {
        return answer2;
    }
    public ObservableField<Answer> getAnswer3() {
        return answer3;
    }
    public ObservableField<Answer> getAnswer4() {
        return answer4;
    }

    public void setUser(User user){
        this.user.set(user);
    }
    public void setQuestion(Question question){
        this.question.set(question);
    }
    public void setAnswer1(Answer answer1){
        this.answer1.set(answer1);
    }
    public void setAnswer2(Answer answer2){
        this.answer2.set(answer2);
    }
    public void setAnswer3(Answer answer3){
        this.answer3.set(answer3);
    }
    public void setAnswer4(Answer answer4){
        this.answer4.set(answer4);
    }

    public void setUserInfo(User usr){
        this.usr = usr;
    }

    public User getUserInfo(){
        return this.usr;
    }

    public void loadUser(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child("12345");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.getValue(User.class);
                setUserInfo(u);
                user.set(u);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        myRef.addListenerForSingleValueEvent(userListener);
    }

    public void uploadUser(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("users").child("12345");
        myRef.setValue(usr);
    }

    public void setAnswer1Info(Answer a1){
        this.a1 = a1;
    }

    public Answer getAnswer1Info(){
        return this.a1;
    }

    public void loadAnswer1(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q"+randChild).child("answers").child("a1");

        ValueEventListener answer1Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer a = dataSnapshot.getValue(Answer.class);
                setAnswer1Info(a);
                answer1.set(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(answer1Listener);
    }

    public void setAnswer2Info(Answer a2){
        this.a2 = a2;
    }

    public Answer getAnswer2Info(){
        return this.a2;
    }

    public void loadAnswer2(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q"+randChild).child("answers").child("a2");

        ValueEventListener answer1Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer a = dataSnapshot.getValue(Answer.class);
                setAnswer2Info(a);
                answer2.set(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(answer1Listener);
    }

    public void setAnswer3Info(Answer a3){
        this.a3 = a3;
    }

    public Answer getAnswer3Info(){
        return this.a3;
    }

    public void loadAnswer3(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q"+randChild).child("answers").child("a3");

        ValueEventListener answer1Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer a = dataSnapshot.getValue(Answer.class);
                setAnswer3Info(a);
                answer3.set(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(answer1Listener);
    }

    public void setAnswer4Info(Answer a4) {
        this.a4 = a4;
    }

    public Answer getAnswer4Info(){
        return this.a4;
    }

    public void loadAnswer4(){
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q"+randChild).child("answers").child("a4");

        ValueEventListener answer1Listener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Answer a = dataSnapshot.getValue(Answer.class);
                setAnswer4Info(a);
                answer4.set(a);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(answer1Listener);
    }

    public void setQuestionInfo(Question q){
        this.q = q;
    }
    public Question getQuestionInfo(){
        return this.q;
    }

    public void loadQuestion()
    {

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q"+randChild);

        ValueEventListener questionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question q = dataSnapshot.getValue(Question.class);
                setQuestionInfo(q);
                question.set(q);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addListenerForSingleValueEvent(questionListener);
    }
}
