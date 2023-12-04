// GroupMembersActivity.java
package com.example.payme2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class GroupMembersActivity extends AppCompatActivity {

    private List<String> members;
    private List<Integer> owedAmounts;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("groupName")) {
            String groupName = intent.getStringExtra("groupName");
            setTitle(groupName + " Members");

            // List of members
            List<String> members = new ArrayList<>();
            members.add("Vlad");
            members.add("Nyha");
            members.add("David");
            members.add("Yang");
            members.add("Shraddha");
            members.add("Alan");

            // Hardcoded amounts for demonstration
            List<Integer> owedAmounts = new ArrayList<>();
            owedAmounts.add(23);   // Amount Vlad owes
            owedAmounts.add(-34);  // Amount Nyha owes
            owedAmounts.add(50);    // Amount David owes
            owedAmounts.add(-35);  // Amount Yang owes
            owedAmounts.add(38);    // Amount Shraddha owes
            owedAmounts.add(-18);   // Amount Alan owes

            // Create an array to hold the display strings
            String[] displayStrings = new String[members.size()];

            // Build the display strings with owed amounts
            for (int i = 0; i < members.size(); i++) {
                String member = members.get(i);
                int amount = owedAmounts.get(i);

                // Customize the appearance based on the amount
                String displayString = member + " - ";
                if (amount < 0) {
                    displayString += "-$" + Math.abs(amount);
                } else {
                    displayString += "+$" + amount;
                }

                displayStrings[i] = displayString;
            }

            // Use ArrayAdapter to display the list
            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayStrings
            );

            ListView listView = findViewById(R.id.listViewGroupMembers);
            listView.setAdapter(adapter);

            // Set click listener for the "Add Cost" button
            Button btnAddCost = findViewById(R.id.btnAddCost);
            btnAddCost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showAddCostDialog();
                }
            });
        }
    }


    private void updateAdapter() {
        // Create an array to hold the display strings
        String[] displayStrings = new String[members.size()];

        // Build the display strings with owed amounts
        for (int i = 0; i < members.size(); i++) {
            String member = members.get(i);
            int amount = owedAmounts.get(i);

            // Customize the appearance based on the amount
            String displayString = member + " - ";
            if (amount < 0) {
                displayString += "-$" + Math.abs(amount);
            } else {
                displayString += "+$" + amount;
            }

            displayStrings[i] = displayString;
        }

        // Initialize or update the ArrayAdapter
        if (adapter == null) {
            adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_list_item_1,
                    displayStrings
            );
        } else {
            adapter.clear();
            adapter.addAll(displayStrings);
            adapter.notifyDataSetChanged();
        }
    }

    private void showAddCostDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Cost");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle adding the cost and updating owed amounts
                String costText = input.getText().toString();
                if (!costText.isEmpty()) {
                    double cost = Double.parseDouble(costText);
                    addCostAndSplit(cost);
                } else {
                    Toast.makeText(GroupMembersActivity.this, "Please enter a valid cost", Toast.LENGTH_SHORT).show();
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

    //Add a cost, and it will be equally split up amongst all other members of group
    private void addCostAndSplit(double cost) {
        // Calculate the amount to be added to each member
        int numberOfMembers = members.size();
        double amountPerMember = cost / numberOfMembers;

        // Update owed amounts for each member
        for (int i = 0; i < members.size(); i++) {
            int currentOwedAmount = owedAmounts.get(i);
            owedAmounts.set(i, currentOwedAmount + (int) amountPerMember);
        }

        // Update the UI
        updateAdapter();
    }
}
