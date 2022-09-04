package ru.myheev.tabatatimer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;

public class FinishActivity extends AppCompatActivity {
    private final String TOTAL_TIME = "TOTAL_TIME";
    private ImageView star1, star2, star3, star4, star5;

    // реклама
    private String AdUnitId = "R-M-1790041-1";
//    private String AdUnitId = "R-M-DEMO-interstitial";
    private InterstitialAd mInterstitialAd;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        Intent i = getIntent();
        TextView tvTimeFinish = findViewById(R.id.tv_time_finish);
        tvTimeFinish.setText(changeTime(i.getLongExtra(TOTAL_TIME, 15)));

        Button btnMain = findViewById(R.id.btn_main);
        ImageView ivBad = findViewById(R.id.iv_bad);
        ImageView ivGood = findViewById(R.id.iv_good);
        ImageView ivNormal = findViewById(R.id.iv_normal);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);
        star5 = findViewById(R.id.star5);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(AdUnitId);
        adRequest = new AdRequest.Builder().build();
        // Регистрация слушателя для отслеживания событий, происходящих в рекламе.
        mInterstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });

        // Загрузка объявления.
        mInterstitialAd.loadAd(adRequest);

        star1.setOnClickListener(v -> {
            star1.setImageResource(R.drawable.ic_star_rate);
            star2.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star3.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
        });
        star2.setOnClickListener(v -> {
            star1.setImageResource(R.drawable.ic_star_rate);
            star2.setImageResource(R.drawable.ic_star_rate);
            star3.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
        });
        star3.setOnClickListener(v -> {
            star1.setImageResource(R.drawable.ic_star_rate);
            star2.setImageResource(R.drawable.ic_star_rate);
            star3.setImageResource(R.drawable.ic_star_rate);
            star4.setImageResource(R.drawable.ic_baseline_star_outline_24);
            star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
        });
        star4.setOnClickListener(v -> {
            star1.setImageResource(R.drawable.ic_star_rate);
            star2.setImageResource(R.drawable.ic_star_rate);
            star3.setImageResource(R.drawable.ic_star_rate);
            star4.setImageResource(R.drawable.ic_star_rate);
            star5.setImageResource(R.drawable.ic_baseline_star_outline_24);
        });
        star5.setOnClickListener(v -> {
            star1.setImageResource(R.drawable.ic_star_rate);
            star2.setImageResource(R.drawable.ic_star_rate);
            star3.setImageResource(R.drawable.ic_star_rate);
            star4.setImageResource(R.drawable.ic_star_rate);
            star5.setImageResource(R.drawable.ic_star_rate);
        });

        ivBad.setOnClickListener(v -> {
            ivBad.setImageResource(R.drawable.bad_yellow);
            ivNormal.setImageResource(R.drawable.neutral_white);
            ivGood.setImageResource(R.drawable.smile_white);
        });

        ivNormal.setOnClickListener(v -> {
            ivBad.setImageResource(R.drawable.bad_white);
            ivNormal.setImageResource(R.drawable.neutral_yellow);
            ivGood.setImageResource(R.drawable.smile_white);
        });

        ivGood.setOnClickListener(v -> {
            ivBad.setImageResource(R.drawable.bad_white);
            ivNormal.setImageResource(R.drawable.neutral_white);
            ivGood.setImageResource(R.drawable.smile_yellow);
        });

        btnMain.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private String changeTime(long totalSeconds) {
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainActivity = new Intent(this, MainActivity.class);
        mainActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}