package com.example.space_shooter_;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private SeekBar musicVolumeSeekBar;
    private SeekBar soundVolumeSeekBar;
    private RadioGroup controlRadioGroup;
    private EditText nameEditText;
    private Button saveButton;

    private String mName = "SpaceShooter";
    private Context mContext_set;


    public SettingsActivity() {
    }

    public SettingsActivity(Context context) {
        mContext_set = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Инициализация всех views
        musicVolumeSeekBar = findViewById(R.id.musicVolumeSeekBar);
        soundVolumeSeekBar = findViewById(R.id.soundVolumeSeekBar);
        controlRadioGroup = findViewById(R.id.controlRadioGroup);
        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlayerName();
                Toast.makeText(getApplicationContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show();

            }
        });

     //   String userInput = nameEditText.getText().toString();

        // Обработка события нажатия на кнопку сохранения изменений
     //   saveButton.setOnClickListener(view -> saveSettings(userInput));

    }

    private void savePlayerName() {
        EditText nameEditText = findViewById(R.id.nameEditText);
        String playerName = nameEditText.getText().toString();

        SharedPreferences sharedPreferences = getSharedPreferences("player_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("player_name", playerName);
        editor.apply();
    }
/*    private void saveSettings(String name) {
        // Получение значений настроек и SharedPreferences
        SharedPreferences sp = mContext_set.getSharedPreferences(mName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString("name", name);
        e.commit();
    }*/


    public String getName(){
        SharedPreferences sp = mContext_set.getSharedPreferences(mName, Context.MODE_PRIVATE);
        return sp.getString("name", "");
    }
}
