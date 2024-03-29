package edu.upenn.cis573.project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MakeDonationActivity extends AppCompatActivity {

    private Organization selectedOrg;
    private Fund selectedFund;
    private DataManager dataManager = new DataManager(new WebClient("10.0.2.2", 3001));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_donation);

        List<Organization> orgs = new ArrayList<>();
        try {
            orgs = dataManager.getAllOrganizations();
        } catch (IllegalStateException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        selectedOrg = orgs.get(0);
        if (selectedOrg.getFunds().isEmpty() == false) {
            selectedFund = selectedOrg.getFunds().get(0);
        }
        else {
            selectedFund = new Fund("0", "This Organization has no Funds.", 0, 0);
        }

        final Spinner orgSpinner = findViewById(R.id.orgSpinner);
        final Spinner fundSpinner = findViewById(R.id.fundSpinner);

        List<Organization> finalOrgs = orgs;
        orgSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String selectedOrgName = (String) adapterView.getItemAtPosition(i);

                for (Organization org : finalOrgs) {

                    if (org.getName().equals(selectedOrgName)) {
                        selectedOrg = org;
                        break;
                    }

                }

                Log.v("spinner", "Selected org: " + selectedOrg.getName() + "; num funds = " + selectedOrg.getFunds().size());

                List<String> fundNames = new LinkedList<>();
                if (selectedOrg.getFunds().isEmpty() == false) {
                    for (Fund fund : selectedOrg.getFunds()) {
                        fundNames.add(fund.getName());
                    }
                }
                else {
                    fundNames.add("This Organization has no Funds.");
                }

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, fundNames);

                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                fundSpinner.setAdapter(dataAdapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fundSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedFundName = (String)adapterView.getItemAtPosition(i);

                if (selectedOrg.getFunds().isEmpty() == false) {
                    for (Fund f : selectedOrg.getFunds()) {
                        if (f.getName().equals(selectedFundName)) {
                            selectedFund = f;
                            break;
                        }
                    }
                }
                else {
                    selectedFund = new Fund("0", "This Organization has no Funds.", 0, 0);
                }

                Log.v("spinner", "Selected fund: " + selectedFundName);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        List<String> orgNames = new LinkedList<>();
        for (Organization org : orgs) {
            orgNames.add(org.getName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, orgNames);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        orgSpinner.setAdapter(dataAdapter);

    }


    public void onMakeDonationButtonClick(View view) {

        String orgId = selectedOrg.getId();
        String fundId = selectedFund.getId();

        if (fundId.equals("0")) {
            Toast.makeText(this, "Sorry, this Organization does not have any Funds.", Toast.LENGTH_LONG).show();
            return;
        }

        EditText amountField = findViewById(R.id.amountField);
        String amount = amountField.getText().toString();
        // check if amount is valid.
        try {
            int numAmount = Integer.parseInt(amount);
            if (numAmount <= 0) {
                Toast.makeText(this, "the amount of donation must be greater than zero!!!", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "amount of donation must be a positive Integer", Toast.LENGTH_LONG).show();
            return;
        }
        Contributor contributor = MainActivity.contributor;
        String contributorId = contributor.getId();

        Log.v("makeDonation", orgId + " " + fundId + " " + amount + " " + contributorId);

        boolean success = false;
        try {
            success = dataManager.makeDonation(contributorId, fundId, amount);
        } catch (IllegalStateException | IllegalArgumentException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        if (success) {
            Toast.makeText(this, "Thank you for your donation!", Toast.LENGTH_LONG).show();
            contributor.getDonations().add(new Donation(selectedFund.getName(), contributor.getName(), Long.parseLong(amount), new SimpleDateFormat("MMM dd, yyyy").format(new Date())));
            Map<String, Long[]> cache = contributor.getCache();
            if (!cache.containsKey(selectedFund.getName())) {
                cache.put(selectedFund.getName(), new Long[] {(long) 1, Long.parseLong(amount)});
            } else {
                Long[] l = cache.get(selectedFund.getName());
                l[0]++;
                l[1] += Long.parseLong(amount);
                cache.put(selectedFund.getName(), l);
            }

            new AsyncTask<String, String, String>() {

                protected String doInBackground(String... inputs) {
                    try { Thread.sleep(3000); }
                    catch (Exception e) { }
                    return null;
                }

                protected void onPostExecute(String input) {
                    finish();
                }
            }.execute();


        }
        else {
            Toast.makeText(this, "Sorry, something went wrong!", Toast.LENGTH_LONG).show();
        }
    }

}