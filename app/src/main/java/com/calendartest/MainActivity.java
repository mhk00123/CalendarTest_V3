package com.calendartest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MaterialCalendarView mcv;   //月曆物件
    TextView txtDate;
    Calendar c; //時間物件
    ListView event_list;    //活動清單物件

    Item item;
    itemDAO item_dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Calendar");

        c = Calendar.getInstance();

        mcv = (MaterialCalendarView) findViewById(R.id.calendarView); //物件實體化
        mcv.setSelectedDate(c); //預選目前時間

        txtDate = (TextView) findViewById(R.id.txtDate);
        txtDate.setText(convertCalendar(mcv.getSelectedDate().getCalendar()));

        event_list = (ListView) findViewById(R.id.eventList);   //活動清單物件實體化

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            String time = c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);

            @Override
            public void onClick(View view) {
                Intent toAdd = new Intent(MainActivity.this, AddEventActivity.class);
                toAdd.putExtra("Date", txtDate.getText().toString());
                toAdd.putExtra("Time", time);
                startActivityForResult(toAdd, 200);
            }
        });

        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                txtDate.setText(convertCalendar(date.getCalendar()));
            }
        });

        item = new Item();
        item_dao = new itemDAO(MainActivity.this);

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
                break;
            case R.id.action_today:
                mcv.setCurrentDate(c);
                mcv.setSelectedDate(c);
                txtDate.setText(convertCalendar(mcv.getSelectedDate().getCalendar()));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 200 && resultCode == RESULT_OK) {

            ArrayList<Item> item_get = item_dao.get(mcv.getSelectedDate().getCalendar());
            ArrayList<Map<String, Object>> eventArray = new ArrayList<>();
            if (item_get == null)
                txtDate.setText(convertCalendar(mcv.getSelectedDate().getCalendar()) + "\nNo Event");
            else {
                txtDate.setText(convertCalendar(mcv.getSelectedDate().getCalendar()));
                for (int i = 0; i < item_get.size(); i++) {
                    HashMap<String, Object> item_map = new HashMap<>();
                    item_map.put("Color", item_get.get(i).getColor());
                    item_map.put("Name", item_get.get(i).getName());
                    eventArray.add(item_map);
                }

                SimpleAdapter adapter = new SimpleAdapter(
                        MainActivity.this,
                        eventArray,
                        R.layout.row_veiw,
                        new String[]{"Color", "Name"},
                        new int[]{R.id.imgColor, R.id.txtName}
                );

                event_list.setAdapter(adapter);
            }

        }
    }

    public String convertCalendar(Calendar cal) {
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        return date;
    }
}
