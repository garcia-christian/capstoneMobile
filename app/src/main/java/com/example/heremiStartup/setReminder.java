package com.example.heremiStartup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class setReminder extends AppCompatActivity {

    LinearLayout timelayout;
    ImageView back;
    Spinner medsSpinner;
    ArrayAdapter<modelMedicineRes> allmeds;
    CheckBox mon, tue, wed, thu, fri, sat, sun;
    SwitchMaterial daysw, activesw;
    ArrayList<modelTime> timeArray = new ArrayList<>();
    EditText dose, notes;
    Button submit;
    modelReminder reminderModel = null;
    int hur, min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_reminder);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), true);
        back = findViewById(R.id.back_btn);
        medsSpinner = findViewById(R.id.spinner1);
        mon = (CheckBox) findViewById(R.id.ck_mon);
        tue = (CheckBox) findViewById(R.id.ck_tue);
        wed = (CheckBox) findViewById(R.id.ck_wed);
        thu = (CheckBox) findViewById(R.id.ck_thu);
        fri = (CheckBox) findViewById(R.id.ck_fri);
        sat = (CheckBox) findViewById(R.id.ck_sat);
        sun = (CheckBox) findViewById(R.id.ck_sun);
        submit = findViewById(R.id.submit_rem);
        daysw = findViewById(R.id.daysw);
        dose = findViewById(R.id.dose);
        notes = findViewById(R.id.rem_notes);

        activesw = findViewById(R.id.activesw);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });


        daysw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (daysw.isChecked()) {
                    mon.setChecked(true);
                    tue.setChecked(true);
                    wed.setChecked(true);
                    thu.setChecked(true);
                    fri.setChecked(true);
                    sat.setChecked(true);
                    sun.setChecked(true);

                } else {
                    mon.setChecked(false);
                    tue.setChecked(false);
                    wed.setChecked(false);
                    thu.setChecked(false);
                    fri.setChecked(false);
                    sat.setChecked(false);
                    sun.setChecked(false);

                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        addTime();
        loadMeds();

    }

    private void onSubmit() {

        try {
            reminderModel = new modelReminder();
            modelMedicineRes medId = (modelMedicineRes) medsSpinner.getSelectedItem();
            reminderModel.setMed_id(medId.getC_med_id());
            reminderModel.setDose(Integer.parseInt(dose.getText().toString()));
            reminderModel.setMon(mon.isChecked());
            reminderModel.setTue(tue.isChecked());
            reminderModel.setWed(wed.isChecked());
            reminderModel.setThu(thu.isChecked());
            reminderModel.setFri(fri.isChecked());
            reminderModel.setSat(sat.isChecked());
            reminderModel.setSun(sun.isChecked());
            reminderModel.setNotes(notes.getText().toString());
            reminderModel.setActive(activesw.isChecked());
            reminderModel.setCustomer(MainActivity.UserID);
            Call<List<modelReminderRes>> modelReminderCall = apiClient.getDeclaration().saveRemidner(reminderModel);

            modelReminderCall.enqueue(new Callback<List<modelReminderRes>>() {
                @Override
                public void onResponse(Call<List<modelReminderRes>> call, Response<List<modelReminderRes>> response) {
                    if (response.isSuccessful()) {
                        if (timeRead(response.body().get(0))) {
                            for (int cn = 0; cn < timeArray.size(); cn++) {


                                    String[] spilit = timeArray.get(cn).getTime().split(":");
                                    if(Integer.parseInt(spilit[0]) > 12){
                                        setRem(Integer.parseInt(spilit[0])+12, Integer.parseInt(spilit[1]),0);
                                    }else{
                                        setRem(Integer.parseInt(spilit[0]), Integer.parseInt(spilit[1]),0);
                                    }



                                Call<List<modelTimeRes>> timecall = apiClient.getDeclaration().saveTime(timeArray.get(cn));
                                timecall.enqueue(new Callback<List<modelTimeRes>>() {
                                    @Override
                                    public void onResponse(Call<List<modelTimeRes>> call, Response<List<modelTimeRes>> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<List<modelTimeRes>> call, Throwable t) {

                                    }
                                });
                            }
                            Toast.makeText(setReminder.this, "Reminder Created Successfully", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    } else {
                        Toast.makeText(setReminder.this, "Error " + response.code(), Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<modelReminderRes>> call, Throwable t) {
                    Toast.makeText(setReminder.this, "Saving Failed" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            });


        } catch (NumberFormatException e) {
            Toast.makeText(this, "Fill up all fields", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean timeRead(modelReminderRes rem) {
        timeArray.clear();
        Boolean res = true;


        for (int i = 0; i < timelayout.getChildCount(); i++) {

            View addtime2 = timelayout.getChildAt(i);
            TextView timetext = addtime2.findViewById(R.id.timetext_txt);

            modelTime timeList = new modelTime();
            if (!timetext.getText().toString().equals("Add Time")) {
                timeList.setRem_id(rem.getRem_id());
                timeList.setTime(timetext.getText().toString());

            } else {
                res = false;
                break;
            }
            timeArray.add(timeList);

        }
        if (timeArray.size() == 0) {
            res = false;
            Toast.makeText(this, "Add Time first", Toast.LENGTH_SHORT).show();

        } else if (!res) {
            Toast.makeText(this, "Enter details properly", Toast.LENGTH_SHORT).show();

        }

        return res;

    }


    private void loadMeds() {
        Call<List<modelMedicineRes>> modelMedicineCall = apiClient.getDeclaration().getMedicine(MainActivity.UserID);

        modelMedicineCall.enqueue(new Callback<List<modelMedicineRes>>() {
            @Override
            public void onResponse(Call<List<modelMedicineRes>> call, Response<List<modelMedicineRes>> response) {
                if (response.isSuccessful()) {
                    List<modelMedicineRes> res = response.body();

                    allmeds = new ArrayAdapter<modelMedicineRes>(setReminder.this, android.R.layout.simple_spinner_item, res);

                    medsSpinner.setAdapter(allmeds);

                } else {
                    Toast.makeText(setReminder.this, "Loading Failed ", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<modelMedicineRes>> call, Throwable t) {
                Toast.makeText(setReminder.this, "Error connection to server" + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void addTime() {
        final View addtime = getLayoutInflater().inflate(R.layout.layout_timelist, null, false);
        timelayout = findViewById(R.id.timeLayout);
        timelayout.addView(addtime);

        Button addtimeinstance = addtime.findViewById(R.id.addtime_btn);
        TextView timetxt = addtime.findViewById(R.id.timetext_txt);

        addtimeinstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addtimeinstance.getText().toString().equals("+")) {
                    addtimeinstance.setText("-");
                    addtimeinstance.setBackground(getDrawable(R.drawable.ellipse_2));
                    addTime();
                } else {
                    timelayout.removeView(addtime);
                }
            }
        });
        timetxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(addtime.getContext(), android.R.style.Theme_Holo_Light_Dialog_MinWidth, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hur = hourOfDay;
                        min = minute;
                        String time = hur + ":" + min;
                        SimpleDateFormat military = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = military.parse(time);
                            SimpleDateFormat twelveH = new SimpleDateFormat("hh:mm:aa");
                            timetxt.setText(twelveH.format(date));

                        } catch (ParseException e) {
                            Toast.makeText(setReminder.this, "Failed to ret Time", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, 12, 0, false);
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.updateTime(hur, min);
                timePickerDialog.show();

            }
        });


    }
    private void setRem(int hr, int min, int sec) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        Intent intent = new Intent(setReminder.this, MemoBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
        }

    }
    // Replace with KK:mma if you want 0-11 interval
    private static final DateFormat TWELVE_TF = new SimpleDateFormat("hh:mma");
    // Replace with kk:mm if you want 1-24 interval
    private static final DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("HH:mm");

    public static String convertTo24HoursFormat(String twelveHourTime)
            throws ParseException {
        return TWENTY_FOUR_TF.format(
                TWELVE_TF.parse(twelveHourTime));
    }

}