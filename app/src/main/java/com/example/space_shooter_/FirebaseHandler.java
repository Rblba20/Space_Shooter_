package com.example.space_shooter_;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHandler {

    private DatabaseReference mDatabase;

    public FirebaseHandler() {
        mDatabase = FirebaseDatabase.getInstance().getReference().child("scores");
    }

    public void saveScore(String playerName, int score) {
        String gameId = mDatabase.push().getKey();

        Map<String, Object> scoreMap = new HashMap<>();
        scoreMap.put("playerName", playerName);
        scoreMap.put("score", score);
       // scoreMap.put("timestamp", timestamp);

        mDatabase.child(gameId).setValue(scoreMap);
    }


    public void fetchScores() {
        mDatabase.orderByChild("score").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot scoreSnapshot : dataSnapshot.getChildren()) {
                    String playerName = scoreSnapshot.child("playerName").getValue(String.class);
                    int score = scoreSnapshot.child("score").getValue(Integer.class);
                 //   String timestamp = scoreSnapshot.child("timestamp").getValue(String.class);

                    // Вывести результаты или использовать их по вашему усмотрению
                    System.out.println("Player: " + playerName + ", Score: " + score);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок
            }
        });
    }
}