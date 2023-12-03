package com.example.payme2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupsActivity extends AppCompatActivity {

    private List<String> groups;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        groups = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, groups);

        ListView listView = findViewById(R.id.listViewGroups);
        listView.setAdapter(adapter);

        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateGroupDialog();
            }
        });

        Button btnSeeGroups = findViewById(R.id.btnSeeGroups);
        btnSeeGroups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGroupListActivity();
            }
        });
    }

    public void onCreateGroupClick(View view) {
        Log.d("GroupsActivity", "Create Group button clicked");

        showCreateGroupDialog();
    }
    public void onSeeGroupsClick(View view) {
        Log.d("GroupsActivity", "See Groups button clicked");

        // Implement the logic to navigate to the page listing the groups
        Intent intent = new Intent(this, GroupListActivity.class);
        startActivity(intent);
    }


    private void showCreateGroupDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create Group");

        // Set up the input
        final EditText input = new EditText(this);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Create Group", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = input.getText().toString();
                if (!groupName.isEmpty()) {
                    createGroup(groupName);
                } else {
                    Toast.makeText(GroupsActivity.this, "Group name cannot be empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void createGroup(String groupName) {
        groups.add(groupName);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Group created: " + groupName, Toast.LENGTH_SHORT).show();
    }

    private void navigateToGroupListActivity() {
        Intent intent = new Intent(GroupsActivity.this, GroupListActivity.class);
        intent.putStringArrayListExtra("groups", new ArrayList<>(groups));
        startActivity(intent);
    }
}
