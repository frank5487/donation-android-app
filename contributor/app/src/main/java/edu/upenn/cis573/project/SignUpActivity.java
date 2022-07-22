package edu.upenn.cis573.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private DataManager dataManager = new DataManager(new WebClient("10.0.2.2", 3001));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }


    public void onSubmitClickButton(View view) {
        EditText loginField = findViewById(R.id.login_field);
        String login = loginField.getText().toString();

        EditText passwordField = findViewById(R.id.password_field);
        String password = passwordField.getText().toString();

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        EditText emailField = findViewById(R.id.email_field);
        String email = emailField.getText().toString();

        EditText creditCardNumberField = findViewById(R.id.creditCardNumber_field);
        String creditCardNumber = creditCardNumberField.getText().toString();

        EditText creditCardCVVField = findViewById(R.id.creditCardCVV_field);
        String creditCardCVV = creditCardCVVField.getText().toString();

        EditText creditCardExpiryMonthField = findViewById(R.id.creditCardExpiryMonth_field);
        String creditCardExpiryMonth = creditCardExpiryMonthField.getText().toString();

        EditText creditCardExpiryYearField = findViewById(R.id.creditCardExpiryYear_field);
        String creditCardExpiryYear = creditCardExpiryYearField.getText().toString();

        EditText creditCardPostCodeField = findViewById(R.id.creditCardPostCode_field);
        String creditCardPostCode = creditCardPostCodeField.getText().toString();

        Map<String, Object> map = new HashMap<>();
        map.put("login", login);
        map.put("password", password);
        map.put("name", name);
        map.put("email", email);
        map.put("creditCardNumber", creditCardNumber);
        map.put("creditCardCVV", creditCardCVV);
        map.put("creditCardExpiryMonth", creditCardExpiryMonth);
        map.put("creditCardExpiryYear", creditCardExpiryYear);
        map.put("creditCardPostCode", creditCardPostCode);

        boolean result = false;
        try {
            result = dataManager.createContributor(map);
        } catch (IllegalStateException | IllegalArgumentException e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            return;
        }

        if (result) {
            Toast.makeText(this, "Registered Successfully!!!", Toast.LENGTH_LONG).show();

            Intent i = new Intent(this, MenuActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, "Unknown errors...", Toast.LENGTH_LONG).show();
        }
    }

}
