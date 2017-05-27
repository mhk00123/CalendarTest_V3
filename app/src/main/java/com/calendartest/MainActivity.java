package com.calendartest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MaterialCalendarView mcv;   //月曆物件
    Calendar c; //時間物件
    ListView event_list;    //活動清單物件
    ArrayList<String> event_array;  //活動內容陣列
    ArrayAdapter<String> myAd;  //清單內容排版物件，可自訂清單內容與排版

    itemDAO get_data;
    Item result,test123;

    public DBHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new DBHelper(this.getApplicationContext(), null, null, 1);
        db = DBHelper.getDatabase(this.getApplicationContext());

        get_data = new itemDAO(this);
        result = new Item();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdd = new Intent(MainActivity.this, AddEventActivity.class);
                toAdd.putExtra("Date", event_list.getItemAtPosition(0).toString());
                startActivityForResult(toAdd, 200);
            }
        });

        setTitle("Calendar");

        mcv = (MaterialCalendarView) findViewById(R.id.calendarView); //物件實體化
        c = Calendar.getInstance(); //取得目前時間
        mcv.setSelectedDate(c); //預選目前時間

        event_list = (ListView) findViewById(R.id.eventList);   //活動清單物件實體化
        refreshList(c);

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                event_array = new ArrayList<>();
                String event_date = date.getYear() + "年" + (date.getMonth() + 1) + "月" + date.getDay() + "日";
                event_array.add(event_date);
                myAd = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, event_array);
                event_list.setAdapter(myAd);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        switch (id) {
            case R.id.mode_month:
                mcv.state().edit().setCalendarDisplayMode(CalendarMode.MONTHS).commit();
                break;
            case R.id.mode_week:
                mcv.state().edit().setCalendarDisplayMode(CalendarMode.WEEKS).commit();
                mcv.goToNext();
                break;
            case R.id.action_today:
                mcv.setCurrentDate(c);
                mcv.setSelectedDate(c);
//                mcv.goToNext();
                refreshList(c);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {
            Cursor result = db.rawQuery("SELECT * FROM EventLog", null);
            if (!result.moveToNext()) {
                event_array = new ArrayList<>();
                event_array.add("No Event!");
                myAd = new ArrayAdapter<>(MainActivity.this,
                        android.R.layout.simple_list_item_1, event_array);
                event_list.setAdapter(myAd);
            } else if (result.moveToNext()) {
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        MainActivity.this, android.R.layout.simple_list_item_1,
                        result, new String[]{"Name"},
                        new int[]{android.R.id.text1},
                        0
                );
                event_list.setAdapter(adapter);
            }
        }

    }

    void refreshList(Calendar cal) {
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        event_array = new ArrayList<>();
        event_array.add(date);
        myAd = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, event_array);
        event_list.setAdapter(myAd);
    }


}
