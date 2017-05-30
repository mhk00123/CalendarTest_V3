package com.calendartest;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity {
    //UI Component
    TextView txtDateFrom, txtDateTo, txtTimeFrom, txtTimeTo;
    EditText edtEventName, edtEventLocation, edtEventDescription;
    Spinner repeatPicker, privacyPicker, remindPicker;
    ImageButton btnColorPicker;

    //all components text
    String name, location, dateFrom, dateTo, timeFrom, timeTo,
            repeatFrequency, privacy, remind, description;

    int colorImage; //顏色圖片
    int temp = 0;

    // Intent instance
    Intent getData;
    Intent toMain;

    Item data;
    itemDAO datadao;

    public DBHelper dbHelper;
    public SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        //database control
        dbHelper = new DBHelper(this.getApplicationContext(), null, null, 1);
        db = dbHelper.getWritableDatabase();

        //UI component
        edtEventName = (EditText) findViewById(R.id.edtEventName);
        btnColorPicker = (ImageButton) findViewById(R.id.btnColorPick);
        edtEventLocation = (EditText) findViewById(R.id.edtEventLocation);
        txtDateFrom = (TextView) findViewById(R.id.txtDateFrom);
        txtDateTo = (TextView) findViewById(R.id.txtDateTo);
        txtTimeFrom = (TextView) findViewById(R.id.txtTimeFrom);
        txtTimeTo = (TextView) findViewById(R.id.txtTimeTo);
        repeatPicker = (Spinner) findViewById(R.id.repeatPicker);
        privacyPicker = (Spinner) findViewById(R.id.privacyPicker);
        remindPicker = (Spinner) findViewById(R.id.remindPicker);
        edtEventDescription = (EditText) findViewById(R.id.edtEventDescription);

        edtEventDescription.setHint("活動說明");
        edtEventDescription.setTextColor(Color.BLACK);

        getResources().getDrawable(R.drawable.circle_icon_red, null);

        //get main data
        getData = getIntent();

        txtDateFrom.setText(getData.getStringExtra("Date"));
        txtDateTo.setText(getData.getStringExtra("Date"));
        txtTimeFrom.setText(getData.getStringExtra("Time"));
        txtTimeTo.setText(getData.getStringExtra("Time"));

        txtDateFrom.setOnClickListener(new View.OnClickListener() { //日期選擇事件
            @Override
            public void onClick(View v) {
                showDataPickerDialogFrom();
            }
        });

        txtDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDataPickerDialogTo();
            }
        });

        txtTimeFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialogFrom();
            }
        });

        txtTimeTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialogTo();
            }
        });

        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorImage = R.drawable.circle_icon_dim_gray;
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
                builder.setTitle("活動色彩");
                builder.setSingleChoiceItems(R.array.colorArray, temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                btnColorPicker.setImageResource(R.drawable.palette_dimgray);
                                colorImage = R.drawable.circle_icon_dim_gray;
                                dialog.dismiss();
                                break;
                            case 1:
                                btnColorPicker.setImageResource(R.drawable.palette_red);
                                colorImage = R.drawable.circle_icon_red;
                                dialog.dismiss();
                                break;
                            case 2:
                                btnColorPicker.setImageResource(R.drawable.palette_orange_red);
                                colorImage = R.drawable.circle_icon_orange_red;
                                dialog.dismiss();
                                break;
                            case 3:
                                btnColorPicker.setImageResource(R.drawable.palette_gold);
                                colorImage = R.drawable.circle_icon_gold;
                                dialog.dismiss();
                                break;
                            case 4:
                                btnColorPicker.setImageResource(R.drawable.palette_lawn_green);
                                colorImage = R.drawable.circle_icon_lawn_green;
                                dialog.dismiss();
                                break;
                            case 5:
                                btnColorPicker.setImageResource(R.drawable.palette_forest_green);
                                colorImage = R.drawable.circle_icon_forest_green;
                                dialog.dismiss();
                                break;
                            case 6:
                                btnColorPicker.setImageResource(R.drawable.palette_light_sea_green);
                                colorImage = R.drawable.circle_icon_light_sea_green;
                                dialog.dismiss();
                                break;
                            case 7:
                                btnColorPicker.setImageResource(R.drawable.palette_royal_blue);
                                colorImage = R.drawable.c_ircle_icon_royablue;
                                dialog.dismiss();
                                break;
                            case 8:
                                btnColorPicker.setImageResource(R.drawable.palette_blue_violet);
                                colorImage = R.drawable.circle_icon_blue_violet;
                                dialog.dismiss();
                                break;
                        }
                        temp = which;   //上一次的選擇
                    }
                });

                builder.create().show();
            }
        });

        data = new Item();
        datadao = new itemDAO(this.getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addSave:
                getString();    //get all component text
                setData();  //input to item class
                datadao.insert(data);   //input to DB
                toMain = new Intent();
                toMain.putExtra("Image", colorImage);
                setResult(RESULT_OK, toMain);
                Toast.makeText(AddEventActivity.this, "success", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getString() {
        name = edtEventName.getText().toString();
        location = edtEventLocation.getText().toString();
        dateFrom = txtDateFrom.getText().toString();
        dateTo = txtDateTo.getText().toString();
        timeFrom = txtTimeFrom.getText().toString();
        timeTo = txtTimeTo.getText().toString();
        repeatFrequency = repeatPicker.getSelectedItem().toString();
        privacy = privacyPicker.getSelectedItem().toString();
        remind = remindPicker.getSelectedItem().toString();
        description = edtEventLocation.getText().toString();
    }

    public void setData() {
        data.setName(name);
        data.setLocation(location);
        data.setDate_from(dateFrom);
        data.setDate_to(dateTo);
        data.setTime_from(timeFrom);
        data.setTime_to(timeTo);
        data.setRepeatFrequency(repeatFrequency);
        data.setPrivacy(privacy);
        data.setRemind(remind);
        data.setDescription(description);
        data.setColor(colorImage);
    }

    public void showDataPickerDialogFrom() {
        final Calendar date = Calendar.getInstance();
        int mYear = date.get(Calendar.YEAR);
        int mMonth = date.get(Calendar.MONTH);
        int mDay = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDateFrom.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showDataPickerDialogTo() {
        final Calendar date = Calendar.getInstance();
        int mYear = date.get(Calendar.YEAR);
        int mMonth = date.get(Calendar.MONTH);
        int mDay = date.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDateTo.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public void showTimePickerDialogFrom() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtTimeFrom.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    public void showTimePickerDialogTo() {
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                txtTimeFrom.setText(hourOfDay + ":" + minute);
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }
}
