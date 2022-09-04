package ru.myheev.tabatatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class WorkoutActivity extends AppCompatActivity {
    private String GET_READY_KEY = "GET_READY_KEY", SETS_KEY = "SETS_KEY", WORKOUT_KEY = "WORKOUT_KEY",
            REST_KEY = "REST_KEY", TOTAL_TIME = "TOTAL_TIME";
    private long totalSecondsGetReady = 5, totalSecondsWorkout = 5,
            totalSecondsRest = 5, totalCommonTime = 15;
    private int totalSets = 1, currentSet = 1;
    private TextView tvGetReady, tvSets, tvTime, tvTimeRest, tvRepsText;
    private Button btnStartWorkout, btnStopRest;
    private CountDownTimer countDownTimer;
    private ConstraintLayout clRest, clWorkout;
    private Long timeForTimer;
    private boolean isRunning = false;
    private MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        initialization();

        btnStartWorkout.setOnClickListener(v -> {
            btnStartWorkout.setVisibility(View.GONE);
            startCountDownGetReady();
        });

        btnStopRest.setOnClickListener(v -> countDownTimer.onFinish());

    }

    private void startCountDownGetReady() {
        countDownTimer = new CountDownTimer(totalSecondsGetReady * 1000 + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                totalSecondsGetReady = millisUntilFinished;
                updateTimer(totalSecondsGetReady);
            }

            @Override
            public void onFinish() {
                playStartWorkout();
                isRunning = false;
                tvGetReady.setVisibility(View.GONE);
                tvRepsText.setVisibility(View.VISIBLE);
                tvSets.setVisibility(View.VISIBLE);
                startCountDownWorkout();
            }
        }.start();
    }

    private void startCountDownWorkout() {
        timeForTimer = totalSecondsWorkout * 1000;
        countDownTimer = new CountDownTimer(timeForTimer + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                timeForTimer = millisUntilFinished;
                updateTimer(timeForTimer);
            }

            @Override
            public void onFinish() {
                playFinishWorkout();
                if (currentSet < totalSets) {
                    isRunning = false;
                    clWorkout.setVisibility(View.GONE);
                    clRest.setVisibility(View.VISIBLE);
                    startCountDownRest();
                } else {
                    startActivity(new Intent(getBaseContext(), FinishActivity.class)
                            .putExtra(TOTAL_TIME, totalCommonTime));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        }.start();
    }

    private void startCountDownRest() {
        timeForTimer = totalSecondsRest * 1000;
        countDownTimer = new CountDownTimer(timeForTimer + 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isRunning = true;
                timeForTimer = millisUntilFinished;
                updateTimerRest(timeForTimer);
            }

            @Override
            public void onFinish() {
                playStartWorkout();
                isRunning = false;
                clRest.setVisibility(View.GONE);
                clWorkout.setVisibility(View.VISIBLE);
                currentSet++;
                tvSets.setText(currentSet + "/" + totalSets);
                startCountDownWorkout();
            }
        }.start();
    }

    // обновление таймера
    private void updateTimerRest(long time) {
        int minutes = (int) time / 60000;
        int seconds = (int) time % 60000 / 1000;

        String timeCountText;
        timeCountText = "" + minutes;
        timeCountText += ":";
        if (seconds < 10) timeCountText += "0";
        timeCountText += seconds;

        tvTimeRest.setText(timeCountText);
    }

    // обновление таймера
    private void updateTimer(long time) {
        int minutes = (int) time / 60000;
        int seconds = (int) time % 60000 / 1000;

        String timeCountText;
        timeCountText = "" + minutes;
        timeCountText += ":";
        if (seconds < 10) timeCountText += "0";
        timeCountText += seconds;

        tvTime.setText(timeCountText);
    }

    private void playStartWorkout() {
        player = MediaPlayer.create(this, R.raw.one_sign);
        player.start();
    }

    private void playFinishWorkout() {
        player = MediaPlayer.create(this, R.raw.three_sign);
        player.start();
    }

    private void initialization() {
        tvGetReady = findViewById(R.id.tv_get_ready);
        tvRepsText = findViewById(R.id.tv_reps_text);
        tvSets = findViewById(R.id.tv_sets);
        tvTime = findViewById(R.id.tv_time);
        tvTimeRest = findViewById(R.id.tv_time_rest);

        clRest = findViewById(R.id.cl_rest);
        clWorkout = findViewById(R.id.cl_workout);

        btnStartWorkout = findViewById(R.id.btn_start_workout);
        btnStopRest = findViewById(R.id.btn_stop_rest);

        getDateFromPreviousActivity();

        getStartedValues();
    }

    private void getDateFromPreviousActivity() {
        Intent i = getIntent();
        totalSecondsGetReady = i.getLongExtra(GET_READY_KEY, 5);
        totalSecondsWorkout = i.getLongExtra(WORKOUT_KEY, 5);
        totalSecondsRest = i.getLongExtra(REST_KEY, 5);
        totalCommonTime = i.getLongExtra(TOTAL_TIME, 15);
        totalSets = i.getIntExtra(SETS_KEY, 1);
    }

    private void getStartedValues() {
        tvTime.setText(changeTimeFormat(totalSecondsGetReady));
        tvSets.setText("1/" + totalSets);
    }

    private String changeTimeFormat(long totalSeconds) {
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%01d:%02d", minutes, seconds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isRunning) countDownTimer.cancel();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}