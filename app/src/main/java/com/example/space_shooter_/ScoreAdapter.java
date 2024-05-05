package com.example.space_shooter_;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private List<Score> mScores;

    public ScoreAdapter(List<Score> scores) {
        mScores = scores;
    }

    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_item, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        Score score = mScores.get(position);
        holder.playerNameTextView.setText(score.getPlayerName());
        holder.scoreTextView.setText(String.valueOf(score.getScore()));
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    static class ScoreViewHolder extends RecyclerView.ViewHolder {
        TextView playerNameTextView;
        TextView scoreTextView;

        ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.playerNameTextView);
            scoreTextView = itemView.findViewById(R.id.scoreTextView);
        }
    }
}
