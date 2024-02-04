package com.example.todolist;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText taskEditText;
    private ListView taskListView;
    private ArrayList<String> taskList;
    private ArrayAdapter<String> taskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskEditText = findViewById(R.id.taskEditText);
        taskListView = findViewById(R.id.taskListView);

        taskList = new ArrayList<>();
        taskAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskList);
        taskListView.setAdapter(taskAdapter);

        // Handle item clicks for editing and long clicks for deletion
        taskListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                editTask(position);
            }
        });

        taskListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                deleteTask(position);
                return true; // Consume the long click event
            }
        });

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });
    }

    public void addTask(View view) {
        String task = taskEditText.getText().toString().trim();

        if (!task.isEmpty()) {
            taskList.add(task);
            taskAdapter.notifyDataSetChanged();
            taskEditText.setText("");
        } else {
            Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show();
        }
    }

    private void editTask(final int position) {
        final EditText editTaskEditText = new EditText(this);
        editTaskEditText.setText(taskList.get(position));

        // Show an alert dialog with the edit text for task editing
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Edit Task")
                .setView(editTaskEditText)
                .setPositiveButton("Save", (dialog, which) -> {
                    String editedTask = editTaskEditText.getText().toString().trim();
                    if (!editedTask.isEmpty()) {
                        taskList.set(position, editedTask);
                        taskAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MainActivity.this, "Task cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteTask(final int position) {
        // Show an alert dialog for task deletion confirmation
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    taskList.remove(position);
                    taskAdapter.notifyDataSetChanged();
                })
                .setNegativeButton("No", null)
                .show();
    }
}
