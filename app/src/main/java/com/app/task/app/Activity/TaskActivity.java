package com.app.task.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.task.app.R;
import com.app.task.app.Utils.DBHelper;
import com.app.task.app.databinding.ActivityTaskBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

public class TaskActivity extends AppCompatActivity {


    ActivityTaskBinding binding;
    DBHelper myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_task);

        myDatabase = new DBHelper(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.seekBar.correctOffsetWhenContainerOnScrolling();

        binding.buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.editTitle.getText().toString().isEmpty()){
                    Toast.makeText(TaskActivity.this, "Please enter task title", Toast.LENGTH_SHORT).show();
                }else{
                    myDatabase.insertTask(binding.editTitle.getText().toString(),binding.seekBar.getProgress());
                    Toast.makeText(TaskActivity.this, "Task insert successfully", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}