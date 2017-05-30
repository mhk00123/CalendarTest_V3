package com.calendartest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
    ArrayAdapter<String> myAd;  //清單內容排版物件，可自訂清單內容與排版

    itemDAO get_data;
    Item result;

    public DBHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Calendar");

        dbHelper = new DBHelper(this.getApplicationContext(), null, null, 1);
        db = DBHelper.getDatabase(this.getApplicationContext());
        get_data = new itemDAO(this);
        result = new Item();

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

            result = get_data.get(mcv.getSelectedDate().getCalendar());
            if (result.getDate_from().equals("")) {
                txtDate.setText(convertCalendar(mcv.getSelectedDate().getCalendar()) + "No Event");
            } else {

            }
//            Cursor result = db.rawQuery("SELECT _id, Name, Color FROM EventLog", null);
//            if (result.getCount() == 0) {
//
//            } else {
//                while (result.moveToNext()) {
//                    ArrayList<Map<String, Object>> itemList = null;
//                    for (int i = 0; i < result.getCount(); i++) {
//                        itemList = new ArrayList<>();
//                        HashMap<String, Object> eventLog = new HashMap<>();
//                        eventLog.put("Image", result.getInt(2));
//                        eventLog.put("Name", result.getString(1));
//                        itemList.add(eventLog);
//                    }
//
//                    SimpleAdapter eventAdapter = new SimpleAdapter(
//                            MainActivity.this,
//                            itemList,
//                            R.layout.row_veiw,
//                            new String[]{"Name", "Image"},
//                            new int[]{R.id.txtName, R.id.imgColor}
//                    );
//
//                    event_list.setAdapter(eventAdapter);
//                }
//            }

        }
    }

    public String convertCalendar(Calendar cal) {
        String date = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
        return date;
    }
}
