package com.calendartest;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddEventActivity extends AppCompatActivity {
    //UI Component
    TextView txtDateFrom, txtDateTo, txtTimeFrom, txtTimeTo;
    EditText edtEventName, edtEventLocation, edtEventDescription;
    Spinner repeatPicker, privacyPicker, remindPicker;
    ImageButton btnColorPicker;

    //all components text
    String name, location, dateFrom, dateTo, timeFrom, timeTo,
            repeatFrequency, privacy, remind, description, color;
    String[] colorArray=new String[12];
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


        //get main data
        getData = getIntent();

        colorArray = getResources().getStringArray(R.array.colorArray);

        txtDateFrom.setText(getData.getStringExtra("Date"));
        txtDateTo.setText(getData.getStringExtra("Date"));
        txtTimeFrom.setText("12點");
        txtTimeTo.setText("12點");

        btnColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(AddEventActivity.this);
                builder.setTitle("活動色彩");
                builder.setSingleChoiceItems(R.array.colorArray, temp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                btnColorPicker.setImageResource(R.drawable.palette_dimgray);
                                dialog.dismiss();
                                break;
                            case 1:
                                btnColorPicker.setImageResource(R.drawable.palette_red);
                                dialog.dismiss();
                                break;
                            case 2:
                                btnColorPicker.setImageResource(R.drawable.palette_orange_red);
                                dialog.dismiss();
                                break;
                            case 3:
                                btnColorPicker.setImageResource(R.drawable.palette_gold);
                                dialog.dismiss();
                                break;
                            case 4:
                                btnColorPicker.setImageResource(R.drawable.palette_lawn_green);
                                dialog.dismiss();
                                break;
                            case 5:
                                btnColorPicker.setImageResource(R.drawable.palette_forest_green);
                                dialog.dismiss();
                                break;
                            case 6:
                                btnColorPicker.setImageResource(R.drawable.palette_light_sea_green);
                                dialog.dismiss();
                                break;
                            case 7:
                                btnColorPicker.setImageResource(R.drawable.palette_royal_blue);
                                dialog.dismiss();
                                break;
                            case 8:
                                btnColorPicker.setImageResource(R.drawable.palette_blue_violet);
                                dialog.dismiss();
                                break;
                        }
                        temp = which;
                        color = colorArray[which];
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
                setResult(RESULT_OK, toMain);
                Toast.makeText(AddEventActivity.this,"success",Toast.LENGTH_SHORT).show();
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
        data.setColor(color);
    }

}
