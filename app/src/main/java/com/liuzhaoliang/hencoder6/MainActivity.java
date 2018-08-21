package com.liuzhaoliang.hencoder6;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liuzhaoliang.dog.singleDog;

public class MainActivity extends AppCompatActivity {

    private singleDog dog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Animation db = findViewById(R.id.db);
//        final EditText et = findViewById(R.id.et);
//        Button bt = findViewById(R.id.bt);
//        bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String st = et.getText().toString();
//                Log.e("st",st);
//                db.setValue(Double.parseDouble(st));
//                db.invalidate();
//            }
//        });

        //Camera Animation
//        Animation db = findViewById(R.id.db);
//        AnimatorSet set = new AnimatorSet();
//        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(db,"cornerY",-45);
//        objectAnimator.setStartDelay(1000);
//        objectAnimator.setDuration(2000);
//
//        ObjectAnimator objectAnimator1 = ObjectAnimator.ofInt(db,"cornerZ",270);
//        objectAnimator1.setStartDelay(1000);
//        objectAnimator1.setDuration(2000);
//
//        ObjectAnimator objectAnimator2 = ObjectAnimator.ofInt(db,"cornerN",45);
//        objectAnimator2.setStartDelay(1000);
//        objectAnimator2.setDuration(2000);
//
//
//        set.play(objectAnimator1)
//                .after(objectAnimator).before(objectAnimator2);
//        set.start();
        dog = findViewById(R.id.db);

    }

    public void go(View view) {
        dog.startAnimation();
    }
}
