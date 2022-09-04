package ru.myheev.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private long getReadySeconds = 5, workoutSeconds = 5,
            restWorkout = 5, totalWorkoutSeconds = 10;
    private int sets = 1;
    private ImageView ivGetReadyMinus, ivGetReadyPlus, ivSetsMinus, ivSetsPlus,
            ivWorkoutMinus, ivWorkoutPlus, ivRestMinus, ivRestPlus;
    private TextView tvGetReadyTime, tvSetsTime, tvWorkoutTime, tvRestTime, tvTotalTime;
    private Button btnStartWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        ivGetReadyPlus.setOnClickListener(v -> {
            getReadySeconds += 5;
            countCommonTime();
            String currentTime = changeTimeFormat(getReadySeconds);
            tvGetReadyTime.setText(currentTime);
        });

        ivGetReadyMinus.setOnClickListener(v -> {
            if (getReadySeconds > 5) {
                getReadySeconds -= 5;
                countCommonTime();
                String currentTime = changeTimeFormat(getReadySeconds);
                tvGetReadyTime.setText(currentTime);
            }
        });

        ivSetsPlus.setOnClickListener(v -> {
            sets += 1;
            countCommonTime();
            tvSetsTime.setText(String.valueOf(sets));
        });

        ivSetsMinus.setOnClickListener(v -> {
            if (sets > 1) {
                sets -= 1;
                countCommonTime();
                tvSetsTime.setText(String.valueOf(sets));
            }
        });

        ivWorkoutPlus.setOnClickListener(v -> {
            workoutSeconds += 5;
            countCommonTime();
            String currentTime = changeTimeFormat(workoutSeconds);
            tvWorkoutTime.setText(currentTime);
        });

        ivWorkoutMinus.setOnClickListener(v -> {
            if (workoutSeconds > 5) {
                workoutSeconds -= 5;
                countCommonTime();
                String currentTime = changeTimeFormat(workoutSeconds);
                tvWorkoutTime.setText(currentTime);
            }
        });

        ivRestPlus.setOnClickListener(v -> {
            restWorkout += 5;
            countCommonTime();
            String currentTime = changeTimeFormat(restWorkout);
            tvRestTime.setText(currentTime);
        });

        ivRestMinus.setOnClickListener(v -> {
            if (restWorkout > 5) {
                restWorkout -= 5;
                countCommonTime();
                String currentTime = changeTimeFormat(restWorkout);
                tvRestTime.setText(currentTime);
            }
        });

        btnStartWorkout.setOnClickListener(v -> {
            startActivity(new Intent(this, WorkoutActivity.class)
                    .putExtra("TOTAL_TIME", totalWorkoutSeconds)
                    .putExtra("SETS_KEY", sets)
                    .putExtra("GET_READY_KEY", getReadySeconds)
                    .putExtra("WORKOUT_KEY", workoutSeconds)
                    .putExtra("REST_KEY", restWorkout));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void initialization() {
        tvTotalTime = findViewById(R.id.tv_total_time);

        ivGetReadyPlus = findViewById(R.id.iv_get_ready_plus);
        tvGetReadyTime = findViewById(R.id.tv_get_ready_time);
        ivGetReadyMinus = findViewById(R.id.iv_get_ready_minus);

        ivSetsPlus = findViewById(R.id.iv_sets_plus);
        tvSetsTime = findViewById(R.id.tv_sets_time);
        ivSetsMinus = findViewById(R.id.iv_sets_minus);

        ivWorkoutMinus = findViewById(R.id.iv_workout_minus);
        tvWorkoutTime = findViewById(R.id.tv_workout_time);
        ivWorkoutPlus = findViewById(R.id.iv_workout_plus);

        ivRestMinus = findViewById(R.id.iv_rest_time_minus);
        tvRestTime = findViewById(R.id.tv_rest_time);
        ivRestPlus = findViewById(R.id.iv_rest_time_plus);

        btnStartWorkout = findViewById(R.id.btn_begin_workout);

        tvGetReadyTime.setText(changeTimeFormat(getReadySeconds));
        tvSetsTime.setText(String.valueOf(sets));
        tvWorkoutTime.setText(changeTimeFormat(workoutSeconds));
        tvRestTime.setText(changeTimeFormat(restWorkout));
        tvTotalTime.setText(changeTimeFormat(totalWorkoutSeconds));
    }

    private String changeTimeFormat(long totalSeconds) {
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void countCommonTime() {
        if (sets > 1) {
            totalWorkoutSeconds = (workoutSeconds * sets) + (restWorkout * sets);
            totalWorkoutSeconds += getReadySeconds;
        } else totalWorkoutSeconds = workoutSeconds + getReadySeconds;
        String str = changeTimeFormat(totalWorkoutSeconds);
        tvTotalTime.setText(str);
    }
}