package com.example.payme2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GroupsActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String GROUPS_KEY = "groups";

    private Set<String> groupsSet;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        // Initialize SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        groupsSet = settings.getStringSet(GROUPS_KEY, new HashSet<>());

        // Initialize the list view
        ListView listView = findViewById(R.id.listViewGroups);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>(groupsSet));
        listView.setAdapter(adapter);

        // Create Group Button
        Button btnCreateGroup = findViewById(R.id.btnCreateGroup);
        btnCreateGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateGroupDialog();
            }
        });

        // Dynamically add buttons for existing groups
        addGroupButtons();

        TextView textViewGroupName = findViewById(R.id.textViewGroupName);
        textViewGroupName.setVisibility(View.GONE);

    }

    private void addGroupButtons() {
        LinearLayout layout = findViewById(R.id.layoutGroupButtons);
        layout.removeAllViews(); // Clear existing buttons

        for (String groupName : groupsSet) {
            Button button = new Button(this);
            button.setText(groupName);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click (navigate to a new page, etc.)
                    navigateToGroupMembersActivity(groupName);
                }
            });

            // Set layout parameters
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, calculateTopMarginForNewButton(), 0, 0);
            button.setLayoutParams(params);

            // Add button to the layout
            layout.addView(button);
        }
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

    public void onCreateGroupClick(View view) {
        // This method will be called when the "Create Group" button is clicked
        Log.d("GroupsActivity", "Create Group button clicked");

        showCreateGroupDialog();
    }

    private void createGroup(String groupName) {
        try {
            // Check if the groupsSet and adapter are not null
            if (groupsSet != null && adapter != null) {
                groupsSet.add(groupName);
                Log.d("GroupsActivity", "Before saveGroupsToSharedPreferences()");
                saveGroupsToSharedPreferences();
                Log.d("GroupsActivity", "After saveGroupsToSharedPreferences()");
                adapter.add(groupName);
                adapter.notifyDataSetChanged();
                Log.d("GroupsActivity", "Before addGroupButton()");
                addGroupButton(groupName); // Add the new button
                Log.d("GroupsActivity", "After addGroupButton()");
                Toast.makeText(this, "Group created: " + groupName, Toast.LENGTH_SHORT).show();
            } else {
                Log.e("GroupsActivity", "groupsSet or adapter is null");
                Toast.makeText(this, "Error creating group", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GroupsActivity", "Exception creating group: " + e.getMessage());
            Toast.makeText(this, "Error creating group", Toast.LENGTH_SHORT).show();
        }
    }

    private void addGroupButton(String groupName) {
        LinearLayout layout = findViewById(R.id.layoutGroupButtons);
        Button button = new Button(this);
        button.setText(groupName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle button click (navigate to a new page, etc.)
                navigateToGroupMembersActivity(groupName);
            }
        });

        // Set layout parameters
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, calculateTopMarginForNewButton(), 0, 0);
        button.setLayoutParams(params);

        // Add button to the layout
        layout.addView(button);
    }

    private int calculateTopMarginForNewButton() {
        // method to calculate the top margin.
        return getResources().getDimensionPixelSize(R.dimen.margin_between_buttons);
    }


    // handles button click and navigate to GroupMembersActivity
    private void navigateToGroupMembersActivity(String groupName) {
        Intent intent = new Intent(GroupsActivity.this, GroupMembersActivity.class);
        intent.putExtra("groupName", groupName);
        startActivity(intent);
    }


    private void saveGroupsToSharedPreferences() {
        // Save the updated set of groups to SharedPreferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putStringSet(GROUPS_KEY, groupsSet);
        editor.apply();
    }
}
