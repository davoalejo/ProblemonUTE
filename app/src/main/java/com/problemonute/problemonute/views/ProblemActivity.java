package com.problemonute.problemonute.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.problemonute.problemonute.R;

public class ProblemActivity extends AppCompatActivity {

    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        Intent intent = getIntent();
	    message = intent.getStringExtra("KEY_MARKER");
	    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
