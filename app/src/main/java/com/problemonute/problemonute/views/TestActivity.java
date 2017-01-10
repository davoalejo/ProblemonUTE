package com.problemonute.problemonute.views;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.problemonute.problemonute.R;
import com.problemonute.problemonute.databinding.ActivityTestBinding;
import com.problemonute.problemonute.viewmodels.TestViewModel;

public class TestActivity extends AppCompatActivity {

    private TestViewModel tvm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        if (tvm == null) tvm = new TestViewModel();
        tvm.loadQuestion();
        binding.setTestVM(tvm);

    }
}

