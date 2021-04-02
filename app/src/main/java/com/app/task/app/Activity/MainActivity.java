package com.app.task.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.task.app.Adapter.TaskAdapter;
import com.app.task.app.Model.Task;
import com.app.task.app.R;
import com.app.task.app.Utils.DBHelper;
import com.app.task.app.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TaskAdapter task_Adapter;
    DBHelper myDatabase;
    List<Task> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        myDatabase = new DBHelper(this);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        binding.recyclerView.setNestedScrollingEnabled(false);
        task_Adapter = new TaskAdapter(MainActivity.this);
        binding.recyclerView.setAdapter(task_Adapter);
        list = myDatabase.getTask();
        setPrioritySorting(list);
        task_Adapter.addAll(list);

        if(list.size() > 0){
            binding.textEmpty.setVisibility(View.GONE);
        }else {
            binding.textEmpty.setVisibility(View.VISIBLE);
        }

        binding.floatingAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,TaskActivity.class);
                startActivityForResult(intent,100);
            }
        });
    }

    public void setPrioritySorting(List<Task> taskList){
        Collections.sort(taskList, new Comparator<Task>() {
            @Override
            public int compare(Task task, Task t1) {
                return task.getTitle().compareToIgnoreCase(t1.getTitle());
            }
        });
        Collections.sort(taskList, new Comparator<Task>(){
            @Override
            public int compare(Task task, Task t1) {
                return task.getPriority() - t1.getPriority();
            }
        });
    }

    public void updateList(){
        list = myDatabase.getTask();
        setPrioritySorting(list);
        task_Adapter.addAll(list);

        if(list.size() > 0){
            binding.textEmpty.setVisibility(View.GONE);
        }else {
            binding.textEmpty.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                list = myDatabase.getTask();
                setPrioritySorting(list);
                task_Adapter.addAll(list);

                if(list.size() > 0){
                    binding.textEmpty.setVisibility(View.GONE);
                }else {
                    binding.textEmpty.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}