package com.example.payme2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class GroupListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        ListView listView = findViewById(R.id.listViewGroupList);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("groups")) {
            List<String> groups = intent.getStringArrayListExtra("groups");
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);
            listView.setAdapter(adapter);
        }
    }
}
