package com.example.rumit.magicmemory;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    int level = 1;
    boolean isAnswer = true;

    Button btnSubmit, btnStart;
    GridView gridView;
    List<Integer> itemStates, itemStatesAnswers;
    TextView tvTimer;
    CountDownTimer timer;
    GridAdapter adapterAnswer, adapter;
    boolean flagAnswer = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnStart = (Button) findViewById(R.id.btn_start);
        tvTimer = (TextView) findViewById(R.id.tv_timer);

        itemStates = new ArrayList<Integer>();
        itemStatesAnswers = new ArrayList<Integer>();
        makeAnswers(1);

        adapterAnswer = new GridAdapter(MainActivity.this, itemStatesAnswers);
        adapter = new GridAdapter(MainActivity.this, itemStates);



        timer = new CountDownTimer(36000, 1000) {



            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Time remaining: " + millisUntilFinished / 1000);
                if(!flagAnswer && millisUntilFinished < 30000){
                    flagAnswer = true;
                    gridView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();
                }
            }

            public void onFinish() {
                checkAnswer();
            }


        };


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemStates.set(position, itemStates.get(position) == 1 ? 0 : 1);
                adapter.notifyDataSetChanged();
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStart.setEnabled(false);
                gridView.setAdapter(adapterAnswer);
                timer.start();

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                checkAnswer();
            }
        });

    }

    private void makeAnswers(int level){
        gridView.setNumColumns(level);
        itemStatesAnswers.clear();
        itemStates.clear();
        if(level == 1){
            this.itemStatesAnswers.add(1);
            this.itemStates.add(0);
            return;
        }
        for(int i = 0; i < level * level; i++){
            itemStatesAnswers.add(0);
            itemStates.add(0);
        }
        Random rand = new Random();
        int quantityCheck = level * level / 2;
        int state;
        while(quantityCheck != 0) {
            for (int i = 0; i < level * level; i++) {
                if(itemStatesAnswers.get(i) == 1){
                    continue;
                }
                state = rand.nextInt(2);
                if (state == 1) {
                    quantityCheck--;
                    itemStatesAnswers.set(i, state);
                    if(quantityCheck == 0) return;
                }
            }
        }
        adapter.notifyDataSetChanged();
        adapterAnswer.notifyDataSetChanged();
    }

    private void checkAnswer(){
        flagAnswer = false;
        boolean check = true;
        for(int i = 0; i < level * level; i++){
            if(itemStates.get(i) != itemStatesAnswers.get(i)){
                check = false;
                break;
            }
        }

        tvTimer.setText("");
        if(check){
            makeAnswers(++level);
            Toast.makeText(MainActivity.this, "Next level:" + level, Toast.LENGTH_SHORT).show();
            gridView.setAdapter(adapterAnswer);
            timer.start();
        }else{
            level = 1;
            makeAnswers(level);
            Toast.makeText(MainActivity.this, "Failed, try again!!", Toast.LENGTH_SHORT).show();
            btnStart.setEnabled(true);
        }
        gridView.setAdapter(adapterAnswer);
    }

}
