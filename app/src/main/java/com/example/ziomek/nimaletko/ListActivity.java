package com.example.ziomek.nimaletko;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ziomek.nimaletko.DataManager.DataManagerImpl;
import com.example.ziomek.nimaletko.Model.Student;

import java.io.Console;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        DataManagerImpl dataManager = new DataManagerImpl(getApplicationContext());
        List<Student> students = dataManager.getAllStudents();
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                students
        );

        ListView listView1 = (ListView) findViewById(R.id.studentslist);
        listView1.setAdapter(adapter);
    }
}
