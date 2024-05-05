package com.example.space_shooter_;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private GameView mGameView;
    private float mXTemp;

/*    private int mTotalScore = 0; // Общий счетчик очков

    private TextView mTotalScoreTextView; // TextView для отображения общего счетчика очков
    private static final int GAME_ACTIVITY_REQUEST_CODE = 1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        setContentView(R.layout.activity_main);

        mTotalScoreTextView = findViewById(R.id.total_score_text_view);

        // Восстановить общий счетчик очков из SharedPreferences
        mTotalScore = loadTotalScore();

        // Обновить TextView для отображения общего счетчика очков
        mTotalScoreTextView.setText(String.valueOf(mTotalScore));*/

        //Membuat tampilan menjadi full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Membuat tampilan selalu menyala jika activity aktif
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //Mendapatkan ukuran layar
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        Log.d("X and Y size", "X = " + point.x + ", Y = " + point.y);

        mGameView = new GameView(this, point.x, point.y);
        setContentView(mGameView);

        //Sensor Accelerometer digunakan untuk menggerakan player ke kanan dan ke kiri
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }
/*
    public void startGameActivity(View view) {
        Intent intent = new Intent(this, GameView.class);
        startActivityForResult(intent, GAME_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GAME_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            int scoreFromGame = data.getIntExtra("score", 0);
            mTotalScore += scoreFromGame;

            // Сохранить общий счетчик очков в SharedPreferences
            saveTotalScore(mTotalScore);

            // Обновить TextView для отображения общего счетчика очков
            mTotalScoreTextView.setText(String.valueOf(mTotalScore));
        }
    }

    private void saveTotalScore(int totalScore) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("totalScore", totalScore);
        editor.apply();
    }

    private int loadTotalScore() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("totalScore", 0);
    }*/

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mXTemp = event.values[0];

        if (event.values[0] > 1){
            mGameView.steerLeft(event.values[0]);
        }
        else if (event.values[0] < -1){
            mGameView.steerRight(event.values[0]);
        }else{
            mGameView.stay();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}