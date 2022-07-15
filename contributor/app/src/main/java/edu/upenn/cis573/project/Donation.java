package edu.upenn.cis573.project;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Donation implements Serializable {

    private String fundName;
    private String contributorName;
    private long amount;
    private String date;

    public Donation(String fundName, String contributorName, long amount, String date) {
        this.fundName = fundName;
        this.contributorName = contributorName;
        this.amount = amount;
        this.date = date;
    }

    public String getFundName() {
        return fundName;
    }

    public String getContributorName() {
        return contributorName;
    }

    public long getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String toString() {
        String newDate = changeDateFormat(date);
        return fundName + ": $" + amount + " on " + newDate;
    }

    private String changeDateFormat(String date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date parseDate = null;
        try {
            parseDate = sdf1.parse(date.substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
        SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        date = sdf2.format(parseDate);
        return date;
    }


}