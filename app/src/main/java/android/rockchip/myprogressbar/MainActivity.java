package android.rockchip.myprogressbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private Timer timer;

    //得到屏幕的总宽度
    private int width;
    private float scrollDistance;


    /**
     * 当前位置
     */
    private float currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1);
        mTextView = findViewById(R.id.progress_value);
        mProgressBar = findViewById(R.id.progress_horizontal);

        // 得到progressBar控件的宽度
        ViewTreeObserver vto2 = mProgressBar.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mProgressBar.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                width = mProgressBar.getWidth();
            }
        });

        findViewById(R.id.btn).setOnClickListener(v -> initAchieve());

    }
    private void initAchieve(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mProgressBar.getProgress() < 100){
                            setPosWay();
                        }

                    }
                });
            }
        }, 1000, 100);



    }
    private void setPosWay() {
        //每一段要移动的距离
        scrollDistance = (float) ((1.0 / mProgressBar.getMax()) * width);
        mTextView.post(new Runnable() {
            @Override
            public void run() {
                setPos();
            }
        });
    }
    /**
     * 设置进度显示在对应的位置
     */
    public void setPos() {
        int index =  mProgressBar.getProgress() + 1;
        mProgressBar.setProgress(index);
        mTextView.setText(index+"%");
        mProgressBar.setProgress(index);
        // 得到字体的宽度
        currentPosition += scrollDistance;
        //做一个平移动画的效果
        mTextView.setTranslationX(currentPosition);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }

    }
}
