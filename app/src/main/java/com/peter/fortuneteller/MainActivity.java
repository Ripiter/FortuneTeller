package com.peter.fortuneteller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FortuneWatchable {

    TextView fortuneText;
    ApiCaller caller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fortuneText = findViewById(R.id.fortune_text);

        caller = new ApiCaller();

        caller.addListener(this);

        Button btn = findViewById(R.id.getfortune_btn);
        btn.setOnClickListener(this::getJokePressed);

        caller.getRequest(MainActivity.this);
    }

    public void getJokePressed(View v){
        caller.getRequest(MainActivity.this);
    }

    @Override
    public void update(Fortune fortune) {
        fortuneText.setText(fortune.getFortune());
    }

    @Override
    public void errorUpdate(String errorMessage) {
        fortuneText.setText(errorMessage);
    }
}