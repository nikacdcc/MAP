package com.nikola.todoapplication;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.nikola.todoapplication.custom.ItemAddDialog;
import com.nikola.todoapplication.custom.ListAdapter;
import com.nikola.todoapplication.custom.SettingsDialog;
import com.nikola.todoapplication.model.TodoItem;
import com.nikola.todoapplication.util.TodoUtils;

import java.io.FileNotFoundException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TodoItem> todos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try {
            updateListView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ListView listView =  findViewById(R.id.list_view);
        listView.setOnItemClickListener((parent, view, position, id) -> delete(position));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> ItemAddDialog.create(MainActivity.this).show());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            SettingsDialog.create(MainActivity.this).show();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateListView() throws FileNotFoundException {
        todos = TodoUtils.fromFileList(fileList(), this);
        String[] nameArray = todos.stream().map(TodoItem::getName).toArray(String[]::new);
        String[] dateArray = todos.stream().map(t -> t.getDay() + ". " + t.getMonth() + ". " + t.getYear() + ".").toArray(String[]::new);
        ListAdapter adapter = new ListAdapter(this, nameArray, dateArray);
        ListView listView =  findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private void delete(int position) {
        deleteFile(todos.get(position).getId() + ".todo");
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean("notifications", true)) {
            Toast.makeText(this, "Item has been successfully deleted", Toast.LENGTH_SHORT).show();
        }
        try {
            updateListView();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
