package edu.upenn.cis573.project;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewDonationsActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_donations);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView messageField = findViewById(R.id.donationsListHeaderField);

        Contributor contributor = MainActivity.contributor;

        Log.v("contributor", "number of donations when listing " +contributor.getDonations().size());


        messageField.setText("Here are " + contributor.getName() + "'s donations:");

        String[] donations = new String[contributor.getDonations().size()+1];

        // to save the totalAmount of donations
        int totalAmount = 0;
        int index = 0;

        for (Donation d : contributor.getDonations()) {

            //Log.v("donation", d.toString());
            donations[index++] = d.toString();
            totalAmount += d.getAmount();
        }
        donations[index] = "Dear " + contributor.getName() + ", Your total donations is: $" + String.valueOf(totalAmount);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.listview, donations);

        ListView listView = (ListView) findViewById(R.id.donationsList);
        listView.setAdapter(adapter);
    }


}