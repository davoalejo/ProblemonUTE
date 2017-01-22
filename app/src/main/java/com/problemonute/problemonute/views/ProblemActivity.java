package com.problemonute.problemonute.views;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.icu.util.TimeUnit;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import android.content.Intent;
import com.problemonute.problemonute.R;
import com.problemonute.problemonute.databinding.ActivityProblemBinding;
import com.problemonute.problemonute.viewmodels.ProblemViewModel;
import io.github.kexanie.library.*;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")

public class ProblemActivity extends AppCompatActivity {
    TextView textViewTime;
    final CounterClass timer = new CounterClass(60000,1000);
    MathView answer1View;
    MathView answer2View;
    MathView answer3View;
    MathView answer4View;
    ProblemViewModel user;
    ProblemViewModel pvm;
    ProblemViewModel answer1;
    ProblemViewModel answer2;
    ProblemViewModel answer3;
    ProblemViewModel answer4;
    String message;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityProblemBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_problem);
        
        Intent intent = getIntent();
	      message = intent.getStringExtra("KEY_MARKER");
	      Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        textViewTime = (TextView) findViewById(R.id.textViewTime);
        answer1View = (MathView) findViewById(R.id.answer1);
        answer2View = (MathView) findViewById(R.id.answer2);
        answer3View = (MathView) findViewById(R.id.answer3);
        answer4View = (MathView) findViewById(R.id.answer4);
        if (user == null) user = new ProblemViewModel();
        user.loadUser();
        if(pvm == null) pvm = new ProblemViewModel();
        pvm.loadQuestion();
        binding.setProblemVM(pvm);
        if(answer1 == null) answer1 = new ProblemViewModel();
        answer1.loadAnswer1();
        binding.setAns1(answer1);
        if(answer2 == null) answer2 = new ProblemViewModel();
        answer2.loadAnswer2();
        binding.setAns2(answer2);
        if(answer3 == null) answer3 = new ProblemViewModel();
        answer3.loadAnswer3();
        binding.setAns3(answer3);
        if(answer4 == null) answer4 = new ProblemViewModel();
        answer4.loadAnswer4();
        binding.setAns4(answer4);
        textViewTime.setText("01:00:00");

        answer1View.setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(answer1.getAnswer1Info().isRight()){
                            Toast.makeText(ProblemActivity.this, "CORRECTO!!!!! ganas " + pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() + (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }else{
                            Toast.makeText(ProblemActivity.this, "INCORRECTO!!!!! pierdes "+ pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() - (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }
                        return false;
                    }
                }
        );
        answer2View.setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(answer2.getAnswer2Info().isRight()){
                            Toast.makeText(ProblemActivity.this, "CORRECTO!!!!! ganas " + pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() + (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }else{
                            Toast.makeText(ProblemActivity.this, "INCORRECTO!!!!! pierdes "+ pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() - (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }
                        return false;
                    }
                }
        );
        answer3View.setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(answer3.getAnswer3Info().isRight()){
                            Toast.makeText(ProblemActivity.this, "CORRECTO!!!!! ganas " + pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() + (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }else{
                            Toast.makeText(ProblemActivity.this, "INCORRECTO!!!!! pierdes "+ pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() - (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }
                        return false;
                    }
                }
        );
        answer4View.setOnTouchListener(
                new View.OnTouchListener(){
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if(answer4.getAnswer4Info().isRight()){
                            Toast.makeText(ProblemActivity.this, "CORRECTO!!!!! ganas " + pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() + (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }else{
                            Toast.makeText(ProblemActivity.this, "INCORRECTO!!!!! pierdes "+ pvm.getQuestionInfo().getPoints()+" puntos", Toast.LENGTH_SHORT).show();
                            user.getUserInfo().setScore(user.getUserInfo().getScore() - (int)pvm.getQuestionInfo().getPoints());
                            user.uploadUser();
                            finish();
                        }
                        return false;
                    }
                }
        );
        timer.start();
    }
    
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer{

        public CounterClass(long millisInFuture, long countDownInterval){
            super(millisInFuture,countDownInterval);
        }
        @SuppressLint("NewApi")
        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        @Override
        public void onTick(long millisUntilFinished){
            long millis = millisUntilFinished;
            String hms = String.format("%02d:%02d:%02d", java.util.concurrent.TimeUnit.MILLISECONDS.toHours(millis),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millis) - java.util.concurrent.TimeUnit.HOURS.toMinutes(java.util.concurrent.TimeUnit.MILLISECONDS.toHours(millis)),
                    java.util.concurrent.TimeUnit.MILLISECONDS.toSeconds(millis) - java.util.concurrent.TimeUnit.MINUTES.toSeconds(java.util.concurrent.TimeUnit.MILLISECONDS.toMinutes(millis)));
            textViewTime.setText(hms);
        }
        @Override
        public void onFinish(){
            Toast.makeText(getApplicationContext(),"SE ACABÓ EL TIEMPO!!! pierdes "+pvm.getQuestionInfo().getPoints()+" puntos",Toast.LENGTH_LONG).show();
            user.getUserInfo().setScore(user.getUserInfo().getScore() - (int)pvm.getQuestionInfo().getPoints());
            user.uploadUser();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            finish();
        }
        
       
    @Override
    public void onBackPressed() {

        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("KEY_MARKER_RETURN", message);
        setResult(RESULT_OK, intent);
        finish();

        super.onBackPressed();
    }
}
