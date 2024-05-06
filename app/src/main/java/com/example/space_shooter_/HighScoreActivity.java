package com.example.space_shooter_;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class HighScoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mBack;
    private TextView mScore, mMeteor, mEnemy, mNullHighScore;
    private LinearLayout mHighScoreContainer;
    private RecyclerView mRecyclerView;
    private ScoreAdapter mAdapter;

    private FirebaseHandler FireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Здесь нужно получить 10 лучших результатов из базы данных Firebase и передать их в адаптер
        List<Score> topScores = getTopScores(); // Функцию getTopScores() нужно реализовать
        mAdapter = new ScoreAdapter(topScores);
        mRecyclerView.setAdapter(mAdapter);

        mBack = findViewById(R.id.back);
        mScore = findViewById(R.id.score);
        mMeteor = findViewById(R.id.meteor);
        mEnemy = findViewById(R.id.enemy);
        mNullHighScore = findViewById(R.id.null_high_score);
        mHighScoreContainer = findViewById(R.id.high_score_container);
        mBack.setOnClickListener(this);

        loadHighScore();
    }

    private List<Score> getTopScores() {
        // Здесь можно сделать запрос к базе данных Firebase, чтобы получить 10 лучших результатов
        // На данный момент вместо реального запроса возвращается фиктивный список результатов для демонстрации
        List<Score> topScores  = FireBase.fetchScores();
/*        List<Score> topScores = new ArrayList<>();
        topScores.add(new Score("Player 1", 100));
        topScores.add(new Score("Player 2", 90));
        topScores.add(new Score("Player 3", 80));*/
        // Добавьте остальные результаты по аналогии
        return topScores;
    }

    void loadHighScore(){
        SharedPreferencesManager spm = new SharedPreferencesManager(this);
        if (spm.getHighScore()!=-1){
            mNullHighScore.setVisibility(TextView.GONE);
            mHighScoreContainer.setVisibility(LinearLayout.VISIBLE);
            mScore.setText(spm.getHighScore() + "");
            mMeteor.setText(spm.getMeteorDestroyed() + "");
            mEnemy.setText(spm.getEnemyDestroyed() + "");
        }else{
            mNullHighScore.setVisibility(TextView.VISIBLE);
            mHighScoreContainer.setVisibility(LinearLayout.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
        }
    }
}