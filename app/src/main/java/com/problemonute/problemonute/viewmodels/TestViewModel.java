package com.problemonute.problemonute.viewmodels;


import android.databinding.ObservableField;
import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.problemonute.problemonute.models.Question;


/**
 * Created by Hercson on 6/1/2017.
 */

public class TestViewModel {

    private static final String TAGLOG = "firebase-db";

    private final ObservableField<Question> question = new ObservableField<>();

    public TestViewModel() {

    }

    public TestViewModel(Question question) {
        this.question.set(question);
    }


    public ObservableField<Question> getQuestion() {
        return question;
    }

    public void setQuestion(Question question){
        this.question.set(question);
    }


    public void loadQuestion()
    {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference()
                .child("questions").child("q1");

        ValueEventListener questionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question q = dataSnapshot.getValue(Question.class);
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
