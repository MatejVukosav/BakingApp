package com.vuki.bakingapp.ui.step;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vuki.bakingapp.R;
import com.vuki.bakingapp.databinding.ActivityStepBinding;

public class StepActivity extends AppCompatActivity {

    ActivityStepBinding binding;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = DataBindingUtil.setContentView( this, R.layout.activity_step );
    }
}
