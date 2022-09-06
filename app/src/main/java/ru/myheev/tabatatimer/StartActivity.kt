package ru.myheev.tabatatimer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    private var getReadySeconds: Long = 5
    private var workoutSeconds: Long = 5
    private var restWorkout: Long = 5
    private var totalWorkoutSeconds: Long = 10
    private var sets = 1

    private lateinit var ivGetReadyMinus: ImageView
    private lateinit var ivGetReadyPlus: ImageView
    private lateinit var ivSetsMinus: ImageView
    private lateinit var ivSetsPlus: ImageView
    private lateinit var ivWorkoutMinus: ImageView
    private lateinit var ivWorkoutPlus: ImageView
    private lateinit var ivRestMinus: ImageView
    private lateinit var ivRestPlus: ImageView

    private lateinit var tvGetReadyTime: TextView
    private lateinit var tvSetsTime: TextView
    private lateinit var tvWorkoutTime: TextView
    private lateinit var tvRestTime: TextView
    private lateinit var tvTotalTime: TextView
    private lateinit var btnStartWorkout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        initialization()

        ivGetReadyPlus.setOnClickListener {
            getReadySeconds += 5
            countCommonTime()
            val currentTime = changeTimeFormat(getReadySeconds)
            tvGetReadyTime.text = currentTime
        }

        ivGetReadyMinus.setOnClickListener {
            if (getReadySeconds > 5) {
                getReadySeconds -= 5
                countCommonTime()
                val currentTime = changeTimeFormat(getReadySeconds)
                tvGetReadyTime.text = currentTime
            }
        }

        ivSetsPlus.setOnClickListener {
            sets += 1
            countCommonTime()
            tvSetsTime.text = sets.toString()
        }

        ivSetsMinus.setOnClickListener {
            if (sets > 1) {
                sets -= 1
                countCommonTime()
                tvSetsTime.text = sets.toString()
            }
        }

        ivWorkoutPlus.setOnClickListener {
            workoutSeconds += 5
            countCommonTime()
            val currentTime = changeTimeFormat(workoutSeconds)
            tvWorkoutTime.text = currentTime
        }

        ivWorkoutMinus.setOnClickListener {
            if (workoutSeconds > 5) {
                workoutSeconds -= 5
                countCommonTime()
                val currentTime = changeTimeFormat(workoutSeconds)
                tvWorkoutTime.text = currentTime
            }
        }

        ivRestPlus.setOnClickListener { v: View? ->
            restWorkout += 5
            countCommonTime()
            val currentTime = changeTimeFormat(restWorkout)
            tvRestTime.text = currentTime
        }

        ivRestMinus.setOnClickListener { v: View? ->
            if (restWorkout > 5) {
                restWorkout -= 5
                countCommonTime()
                val currentTime = changeTimeFormat(restWorkout)
                tvRestTime.text = currentTime
            }
        }

        btnStartWorkout.setOnClickListener { v: View? ->
            startActivity(
                Intent(this, WorkoutActivity::class.java)
                    .putExtra("TOTAL_TIME", totalWorkoutSeconds)
                    .putExtra("SETS_KEY", sets)
                    .putExtra("GET_READY_KEY", getReadySeconds)
                    .putExtra("WORKOUT_KEY", workoutSeconds)
                    .putExtra("REST_KEY", restWorkout)
            )
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun initialization() {
        tvTotalTime = findViewById(R.id.tv_total_time)
        ivGetReadyPlus = findViewById(R.id.iv_get_ready_plus)
        tvGetReadyTime = findViewById(R.id.tv_get_ready_time)
        ivGetReadyMinus = findViewById(R.id.iv_get_ready_minus)
        ivSetsPlus = findViewById(R.id.iv_sets_plus)
        tvSetsTime = findViewById(R.id.tv_sets_time)
        ivSetsMinus = findViewById(R.id.iv_sets_minus)
        ivWorkoutMinus = findViewById(R.id.iv_workout_minus)
        tvWorkoutTime = findViewById(R.id.tv_workout_time)
        ivWorkoutPlus = findViewById(R.id.iv_workout_plus)
        ivRestMinus = findViewById(R.id.iv_rest_time_minus)
        tvRestTime = findViewById(R.id.tv_rest_time)
        ivRestPlus = findViewById(R.id.iv_rest_time_plus)
        btnStartWorkout = findViewById(R.id.btn_begin_workout)
        tvGetReadyTime.setText(changeTimeFormat(getReadySeconds))
        tvSetsTime.text = sets.toString()
        tvWorkoutTime.setText(changeTimeFormat(workoutSeconds))
        tvRestTime.setText(changeTimeFormat(restWorkout))
        tvTotalTime.setText(changeTimeFormat(totalWorkoutSeconds))
    }

    private fun changeTimeFormat(totalSeconds: Long): String {
        val minutes = totalSeconds % 3600 / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun countCommonTime() {
        if (sets > 1) {
            totalWorkoutSeconds = workoutSeconds * sets + restWorkout * sets
            totalWorkoutSeconds += getReadySeconds
        } else totalWorkoutSeconds = workoutSeconds + getReadySeconds
        val str = changeTimeFormat(totalWorkoutSeconds)
        tvTotalTime.text = str
    }
}