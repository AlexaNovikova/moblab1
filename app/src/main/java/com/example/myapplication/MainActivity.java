package com.example.myapplication;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public final ArrayList<String> arrayList = new ArrayList<>();
    EditText name, weight, growth;
    Button AddBtn , ClearBtn, ReadBtn;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //инициализируем переменные
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList.clear();
        AddBtn = findViewById(R.id.addButton);
        ClearBtn = findViewById(R.id.clearButton);
        ReadBtn = findViewById(R.id.showButton);
        AddBtn.setOnClickListener((View.OnClickListener) this);
        ClearBtn.setOnClickListener((View.OnClickListener) this);
        ReadBtn.setOnClickListener((View.OnClickListener) this);
        name = findViewById(R.id.nameTextField);
        weight = findViewById(R.id.weightTextField);
        growth = findViewById(R.id.heightTextField);
        dataBase = new DataBase(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if (v.getId() == R.id.addButton) {
            contentValues.put("Name", name.getText().toString());
            int wei = Integer.valueOf(weight.getText().toString());
            contentValues.put("Weight", wei);
            int gro = Integer.valueOf(growth.getText().toString());
            contentValues.put("Growth", gro);
            Random r = new Random();
            contentValues.put("Age", r.nextInt(80));
            sqLiteDatabase.insert("Person", null, contentValues);
            Toast.makeText(getApplicationContext(), "Данные были добавлены в БД", Toast.LENGTH_LONG).show();
            dataBase.close();
            name.setText("");
            growth.setText("");
            weight.setText("");
        } else if (v.getId() == R.id.showButton) {
            dataBase.close();
            Intent intent = new Intent(MainActivity.this, Show.class);
            startActivity(intent);
        } else if (v.getId() == R.id.clearButton) { //запись
            sqLiteDatabase.delete("Person", null, null);
            Toast.makeText(getApplicationContext(), "Таблица успешно очищена", Toast.LENGTH_LONG).show();
            arrayList.clear();
            dataBase.close();
        }
    }
}
