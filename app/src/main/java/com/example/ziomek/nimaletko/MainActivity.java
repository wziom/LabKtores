package com.example.ziomek.nimaletko;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ziomek.nimaletko.R;

public class MainActivity extends AppCompatActivity {

    Button button;
    MainActivity mActivity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.buttonList);
        button.setOnClickListener(handler);
        OpenHelper openHelper = new OpenHelper(getBaseContext());
    }

    public void buttonOnClick(View v) {
        Intent intent = new Intent(mActivity, ListActivity.class);
        startActivity(intent);
    }

    View.OnClickListener handler = new View.OnClickListener() {
        public void onClick(View v) {
            Intent intent = new Intent(mActivity, ListActivity.class);
            startActivity(intent);
        }
    };
}
