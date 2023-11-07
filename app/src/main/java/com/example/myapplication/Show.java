package com.example.myapplication;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import java.util.ArrayList;

public class Show extends AppCompatActivity {
    TableLayout tableLayout;
    ArrayList<String> arr = new ArrayList<>();
    Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        cancelButton = findViewById(R.id.cancelButton);
        DataBase dataBase = new DataBase(this);
        SQLiteDatabase sqLiteDatabase = dataBase.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Person", null, null, null, null, null, "Age");
        if (cursor.moveToFirst()) {
            do {
                arr.add( cursor.getString(cursor.getColumnIndex("Name")) );
                arr.add( String.valueOf(cursor.getInt(cursor.getColumnIndex("Weight"))) );
                arr.add( String.valueOf(cursor.getInt(cursor.getColumnIndex("Growth"))));
                arr.add( String.valueOf(cursor.getInt(cursor.getColumnIndex("Age"))) );
            } while ( cursor.moveToNext() );
        }
        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableDB);
        //Настроим некоторые парамеры одной строки:

            for (int i = 0; i < arr.size(); i += 4) {

                if (i == 0) {
                    TableRow r1 = new TableRow(this);
                    r1.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    for (int j = 0; j < 4; j++) {
                        TextView txt = new TextView(this);
                        if (j == 0) {
                            txt.setText("Имя     ");
                        } else if (j == 1) {
                            txt.setText("Вес     ");
                        } else if (j == 2) {
                            txt.setText("Рост     ");
                        } else {
                            txt.setText("Возраст     ");
                        }
                        txt.setTextSize(20);
                        r1.addView(txt);
                    }
                    r1.setGravity(Gravity.CENTER_HORIZONTAL);
                    tableLayout.addView(r1); // добавили
                }
                TableRow r = new TableRow(this);
                r.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                for (int j = 0; j < 4; j++) {

                    TextView txt = new TextView(this);
                    txt.setText(arr.get(i + j) + "     ");
                    txt.setTextSize(20);
                    r.addView(txt);
                }
                r.setGravity(Gravity.CENTER_HORIZONTAL);
                tableLayout.addView(r); // добавили строку в итоговую таблицу
            }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Show.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }

}
