package jake.wheelview;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnFocusChangeListener, ValueAnimator.AnimatorUpdateListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] PLANETS = new String[]{"Mercury", "Venus", "Earth", "Mars", "Jupiter", "Uranus", "Neptune", "Pluto"};
    private WheelView wva;
    private WheelView wva1;
    private float _100dp;
    private float _150dp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _100dp = getResources().getDimension(R.dimen._100dp);
        _150dp = getResources().getDimension(R.dimen._150dp);

        wva = (WheelView) findViewById(R.id.main_wv);
        wva1 = (WheelView) findViewById(R.id.main_wv1);
        wva.setOffset(1);
        wva1.setOffset(2);
        wva.setItems(Arrays.asList(PLANETS));
        wva1.setItems(Arrays.asList(PLANETS));

        wva1.setSeletion(mSeletion1);
        wva.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wva1.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });
        wva.setOnKeyListener(this);
        wva1.setOnKeyListener(this);
        wva.setOnFocusChangeListener(this);
        wva1.setOnFocusChangeListener(this);

        wva.postDelayed(new Runnable() {
            @Override
            public void run() {
                wva.requestFocus();
            }
        }, 200);
    }

    private int mSeletion = 0;
    private int mSeletion1 = 5;


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            Log.e("MainActivity", "onKey:" + keyCode);
            if (v.getId() == R.id.main_wv) {
                if (!wva.hasFocus()) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    mSeletion++;
                    wva.setSeletion(mSeletion);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    mSeletion--;
                    wva.setSeletion(mSeletion);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(MainActivity.this, wva.getSeletedItem(), Toast.LENGTH_SHORT).show();
                }
            } else {
                if (!wva1.hasFocus()) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
                    mSeletion1++;
                    wva1.setSeletion(mSeletion1);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
                    mSeletion1--;
                    wva1.setSeletion(mSeletion1);
                } else if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) {
                    Toast.makeText(MainActivity.this, wva1.getSeletedItem(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.e("MainActivity", "onFocusChange:" + v.getId() + ",hasFocus:" + hasFocus);
        if (v.getId() == R.id.main_wv) {
            if (hasFocus) {

            } else {

            }
        } else {
            if (hasFocus) {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
                valueAnimator.addUpdateListener(this);
                valueAnimator.setDuration(200);
                valueAnimator.start();
            } else {
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                valueAnimator.addUpdateListener(this);
                valueAnimator.setDuration(200);
                valueAnimator.start();
            }
        }


    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float offset = (float) valueAnimator.getAnimatedValue();

        ViewGroup.LayoutParams layoutParams = wva.getLayoutParams();
        layoutParams.width = (int) (_100dp + offset * (_150dp - _100dp));
        wva.setLayoutParams(layoutParams);

    }
}
