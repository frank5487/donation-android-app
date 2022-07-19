package edu.upenn.cis573.project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AggregateDonationsActivity extends AppCompatActivity {

    private Contributor contributor = MainActivity.contributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aggregate_donations);
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView messageField = findViewById(R.id.donationsListHeaderField);

        messageField.setText("Here are " + contributor.getName() + "'s aggregate donations record");

        Map<String, Long[]> m = new HashMap<>();
        if (contributor.getCache() == null ||  contributor.getCache().size() == 0) {
            for (Donation donation : contributor.getDonations()) {
                String fundName = donation.getFundName();
                long amount = donation.getAmount();
                if (!m.containsKey(fundName)) {
                    m.put(fundName, new Long[] {(long) 1, amount});
                } else {
                    Long[] l = m.get(fundName);
                    l[0]++;
                    l[1] += amount;
                    m.put(fundName, l);
                }
            }
            contributor.setCache(m);
        } else {
            m = contributor.getCache();
        }

        List<Map.Entry<String, Long[]>> list = new ArrayList<>(m.entrySet());
        Collections.sort(list, (a,b)->Long.compare(b.getValue()[1],a.getValue()[1]));

        String[] aggregateDonations = new String[m.size()];
        int count = 0;
        for (Map.Entry<String, Long[]> entry : list) {
            String key = entry.getKey();
            Long[] l = entry.getValue();
            String output = key + ", " + l[0] + " donations, $" + l[1] + " total";
            aggregateDonations[count] = output;
            count++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.listview, aggregateDonations);
        ListView listView = findViewById(R.id.donationsList);
        listView.setAdapter(adapter);
    }
}
