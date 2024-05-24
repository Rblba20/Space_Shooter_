package com.example.space_shooter_;

import static com.example.space_shooter_.Player.CONTROL_TYPE_KEY;
import static com.example.space_shooter_.Player.PREFS_NAME;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
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

    private RadioButton touchRadioButton;
    private RadioButton accelerometerRadioButton;

    private SharedPreferences preferences;

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


/*
        preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        touchRadioButton = findViewById(R.id.radio_button_touch);
        accelerometerRadioButton = findViewById(R.id.radio_button_accelerometer);

        // Установите сохраненное значение выбранного радиобаттона
        boolean isTouchSelected = preferences.getBoolean("touch_selected", true);
        setRadioButtonStates(isTouchSelected);

        // Установите слушатель для радиобаттонов
        setRadioButtonListeners();
*/


        touchRadioButton = findViewById(R.id.radio_button_touch);
        accelerometerRadioButton = findViewById(R.id.radio_button_accelerometer);

        // Инициализация всех views
       // musicVolumeSeekBar = findViewById(R.id.musicVolumeSeekBar);
      //  soundVolumeSeekBar = findViewById(R.id.soundVolumeSeekBar);
        controlRadioGroup = findViewById(R.id.controlRadioGroup);
        nameEditText = findViewById(R.id.nameEditText);
        saveButton = findViewById(R.id.saveButton);

        // Установите сохраненные значения громкости
       // float musicVolume = preferences.getFloat("music_volume", 0.5f);
       // float soundVolume = preferences.getFloat("sound_volume", 0.5f);

// Установите начальные значения громкости
      //  musicVolumeSeekBar.setProgress((int) (musicVolume * 100));
      //  soundVolumeSeekBar.setProgress((int) (soundVolume * 100));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePlayerName();
                saveControlType();
            //    saveMusicVolume();
                Toast.makeText(getApplicationContext(), "Данные успешно сохранены", Toast.LENGTH_SHORT).show();

            }
        });


     //   String userInput = nameEditText.getText().toString();

        // Обработка события нажатия на кнопку сохранения изменений
     //   saveButton.setOnClickListener(view -> saveSettings(userInput));

    }

  //  private void saveMusicVolume() {
  //      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
   //     SharedPreferences.Editor editor = preferences.edit();

        // Сохранение громкости музыки
   //     editor.putFloat("music_volume", musicVolumeSeekBar.getProgress() / 100f);

        // Сохранение громкости звуков
    //    editor.putFloat("sound_volume", soundVolumeSeekBar.getProgress() / 100f);

    //    // Применение изменений
    //    editor.apply();
  //  }

    private void saveControlType() {
        SharedPreferences prefs = getSharedPreferences("game_settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (touchRadioButton.isChecked()) {
            editor.putInt("control_type", 1);
        } else if (accelerometerRadioButton.isChecked()) {
            editor.putInt("control_type", 2);
        }

        editor.apply();
          }

    /*
    private void setRadioButtonStates(boolean isTouchSelected) {
        touchRadioButton.setChecked(isTouchSelected);
        accelerometerRadioButton.setChecked(!isTouchSelected);
    }

    private void setRadioButtonListeners() {
        touchRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveControlPreference(true);
                }
            }
        });

        accelerometerRadioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveControlPreference(false);
                }
            }
        });
    }

    private void saveControlPreference(boolean isTouchSelected) {
        preferences.edit().putBoolean("touch_selected", isTouchSelected).apply();
    }*/




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
