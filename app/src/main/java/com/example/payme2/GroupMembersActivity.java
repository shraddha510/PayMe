// GroupMembersActivity.java
package com.example.payme2;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import android.content.Intent;

public class GroupMembersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_members);

        ListView listView = findViewById(R.id.listViewGroupMembers);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("groupName")) {
            String groupName = intent.getStringExtra("groupName");
            setTitle(groupName + " Members");

            // list of members
            List<String> members = new ArrayList<>();
            members.add("Vlad");
            members.add("Nyha");
            members.add("David");
            members.add("Yang");
            members.add("Shraddha");

            // Generate random amounts for demonstration
            List<Integer> owedAmounts = generateRandomAmounts(members.size());

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

            listView.setAdapter(adapter);
        }


    }

    // Generate random owed amounts for demonstration
    private List<Integer> generateRandomAmounts(int size) {
        List<Integer> amounts = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            // Generate random amount between -100 and 100
            int amount = random.nextInt(201) - 100;
            amounts.add(amount);
        }

        return amounts;
    }
}
