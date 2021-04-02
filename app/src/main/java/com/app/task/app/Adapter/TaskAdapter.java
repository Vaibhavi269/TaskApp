package com.app.task.app.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.task.app.Activity.MainActivity;
import com.app.task.app.Model.Task;
import com.app.task.app.R;
import com.app.task.app.Utils.DBHelper;
import com.app.task.app.databinding.ListItemTaskBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private List<Task> list = new ArrayList<>();
    public Context context;
    DBHelper myDatabase;

    public TaskAdapter(Context context) {
        this.context = context;
        myDatabase = new DBHelper(context);
    }

    public void addAll(List<Task> files) {

        try {
            this.list.clear();
            this.list.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void appendAll(List<Task> files) {

        try {
            this.list.addAll(files);

        } catch (Exception e) {
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public void clearAll() {
        this.list.clear();
        notifyDataSetChanged();
    }

    public void updateList(List<Task> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ListItemTaskBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.list_item_task, viewGroup, false);
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {

        final Task task = list.get(position);

        myViewHolder.binding.textTitle.setText("Title : " + task.getTitle());
        myViewHolder.binding.textPriority.setText("Priority : " + task.getPriority() + "");

        myViewHolder.binding.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure?")
                        .setPositiveButton("Accomplished", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                myDatabase.deleteTask(task.getTitle());
                                ((MainActivity) context).updateList();
                                Toast.makeText(context,  task.getTitle() + " is marked as accomplished",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });

                AlertDialog d = builder.create();
                d.setTitle("Alert");
                d.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ListItemTaskBinding binding;

        public MyViewHolder(ListItemTaskBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
