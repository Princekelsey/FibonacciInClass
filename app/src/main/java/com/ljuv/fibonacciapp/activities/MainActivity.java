package com.ljuv.fibonacciapp.activities;

import com.ljuv.fibonacciapp.utils.ArrayListConverter;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import java.util.ArrayList;

import com.ljuv.fibonacciapp.R;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> fibList;
    public int inputNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Context context = this;
        loadData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button okButton = findViewById(R.id.button_ok);
        final EditText fibonacciSeq = findViewById(R.id.tv_fibonacci_sequence);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!fibonacciSeq.getText().toString().isEmpty()) {
                    int num = Integer.parseInt(fibonacciSeq.getText().toString());
                    if (num == inputNum) {
                        Intent intent = new Intent(context, FizzBuzzActivity.class).putExtra("nums",
                                Integer.parseInt(fibonacciSeq.getText().toString()));
                        startActivity(intent);
                    } else {
                        saveDataAndCalulate(num);
                        Intent intent = new Intent(context, FizzBuzzActivity.class).putExtra("nums",
                                Integer.parseInt(fibonacciSeq.getText().toString()));
                        startActivity(intent);
                    }

                }
            }
        });

    }

    public void saveDataAndCalulate(int num) {
        SharedPreferences sharedPreferences = getSharedPreferences("fibonaccPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        ArrayList<Long> list;
        list = FibonacciNumbersCalculator.getNums(num);
        fibList = ArrayListConverter.longToString(list);
        String json = gson.toJson(fibList);
        editor.putString("fibList", json);
        editor.putInt("inputValue", num);
        editor.apply();
    }

    public void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("fibonaccPref", MODE_PRIVATE);
        inputNum = sharedPreferences.getInt("inputValue", 1);
    }

}
