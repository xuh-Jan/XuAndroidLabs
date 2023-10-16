package algonquin.cst2355.xu.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import algonquin.cst2355.xu.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Switch sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.flagview);
        sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener((btn, isChecked) -> {
            if (isChecked) {
                setAppLocale("zh");
                startRotationAnimation();
            } else {
                setAppLocale("en");
                stopRotationAnimation();
            }
        });
    }

    private void setAppLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    private void startRotationAnimation() {
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        rotate.setRepeatCount(Animation.INFINITE);
        rotate.setInterpolator(new LinearInterpolator());
        imageView.startAnimation(rotate);
    }

    private void stopRotationAnimation() {
        imageView.clearAnimation();
    }
}
