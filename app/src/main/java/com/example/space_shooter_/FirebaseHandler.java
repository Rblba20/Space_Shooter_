package com.example.space_shooter_;

import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHandler {
    FirebaseDatabase rootdatabase;
    private DatabaseReference mDatabase;


    public FirebaseHandler() {
      //  mDatabase = FirebaseDatabase.getInstance().getReference().child("scores");
        mDatabase = FirebaseDatabase.getInstance().getReference("scores");
    }

    public void saveScore(final String playerName, final int score) {
        Thread saveThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String gameId = mDatabase.push().getKey();

                Map<String, Object> scoreMap = new HashMap<>();
                scoreMap.put("name", playerName);
                scoreMap.put("score", score);

                mDatabase.child(gameId).setValue(scoreMap);
            }
        });
        saveThread.start();
    }

    /*public List<Score> fetchScores() {
        List<Score> topScores = new ArrayList<>();
        Thread fetchThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.orderByChild("score").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                            String playerName = scoreSnapshot.child("playerName").getValue(String.class);
                            int score = scoreSnapshot.child("score").getValue(Integer.class);

                            // Вывести результаты или использовать их по вашему усмотрению
                            Log.d("FirebaseHandler", "Player: " + playerName + ", Score: " + score);

                            topScores.add(new Score(playerName, score));
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Обработка ошибок при чтении данных
                        Log.e("MainActivity", "Failed to read value.", databaseError.toException());
                    }
                });
            }
        });
        fetchThread.start();
        return topScores;
    }*/

    public void fetchScores(final ScoreFetchCallback callback) {
        mDatabase.orderByChild("score").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Score> topScores = new ArrayList<>();
                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    String playerName = scoreSnapshot.child("name").getValue(String.class);
                    int score = scoreSnapshot.child("score").getValue(Integer.class);
                    Log.d("FirebaseHandler", "Player: " + playerName + ", Score: " + score);
                    topScores.add(new Score(playerName, score));
                }
                // Передаем полученные данные через коллбэк
                callback.onScoresFetched(topScores);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
                callback.onError(databaseError.getMessage());
            }
        });
    }

    // Интерфейс для обратного вызова
    public interface ScoreFetchCallback {
        void onScoresFetched(List<Score> topScores);
        void onError(String errorMessage);
    }
}
