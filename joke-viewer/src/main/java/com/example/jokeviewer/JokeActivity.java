package com.example.jokeviewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jokeviewer.util.Constants;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Jokes");
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        if (intent != null) {
            String jokeDescription = intent.getStringExtra(Constants.EXTRA_JOKE_DESCRIPTION);
            String jokeCategory = intent.getStringExtra(Constants.EXTRA_JOKE_CATEGORY);
            showJoke(jokeCategory, jokeDescription);
        } else {
            Toast.makeText(this, R.string.msg_no_joke_found, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void showJoke(String jokeCategory, String jokeDescription) {
        ((TextView) findViewById(R.id.text_joke_category)).setText(getString(R.string.joke_category, jokeCategory));
        ((TextView) findViewById(R.id.text_joke)).setText(jokeDescription);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
