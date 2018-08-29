package com.iigo.ballsloadingview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.iigo.library.BallsLoadingView;

public class MainActivity extends AppCompatActivity {
    private BallsLoadingView ballsLoadingView1;
    private BallsLoadingView ballsLoadingView2;
    private BallsLoadingView ballsLoadingView3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ballsLoadingView1 = findViewById(R.id.blv_loading1);
        ballsLoadingView2 = findViewById(R.id.blv_loading2);
        ballsLoadingView3 = findViewById(R.id.blv_loading3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ballsLoadingView1.release();
        ballsLoadingView2.release();
        ballsLoadingView3.release();
    }

    public void onStart(View view) {
        ballsLoadingView1.start();
        ballsLoadingView2.start();
        ballsLoadingView3.start();
    }

    public void onStop(View view) {
        ballsLoadingView1.stop();
        ballsLoadingView2.stop();
        ballsLoadingView3.stop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        ViewGroup.LayoutParams layoutParams = ballsLoadingView1.getLayoutParams();
//        layoutParams.width = 200;
//        layoutParams.height = 200;
//        ballsLoadingView1.setLayoutParams(layoutParams);

//        ballsLoadingView1.setFirstBallColor(Color.RED);
//        ballsLoadingView1.setSecondBallColor(Color.BLACK);
//        ballsLoadingView1.setThirdBallColor(Color.GREEN);
//        ballsLoadingView1.setFourthBallColor(Color.BLUE);

//        ballsLoadingView1.setAnimType(BallsLoadingView.ANIM_TYPE_SCALE);
//        ballsLoadingView1.setBallRadius(6);
        return super.onKeyDown(keyCode, event);
    }
}
