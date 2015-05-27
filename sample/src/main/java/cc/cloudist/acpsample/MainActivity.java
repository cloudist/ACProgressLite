package cc.cloudist.acpsample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import cc.cloudist.acplibrary.ACProgressCustom;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.flower_cancelable).setOnClickListener(this);
        findViewById(R.id.flower_fixed).setOnClickListener(this);
        findViewById(R.id.pie).setOnClickListener(this);
        findViewById(R.id.custom).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flower_cancelable: {
                ACProgressFlower.Builder builder = new ACProgressFlower.Builder(this);
                ACProgressFlower flower = builder.build();
                flower.setCancelable(true);
                flower.show();
            }
            break;
            case R.id.flower_fixed: {
                ACProgressFlower.Builder builder = new ACProgressFlower.Builder(this);
                ACProgressFlower flower = builder.build();
                flower.setCancelable(false);
                flower.show();
            }
            break;
            case R.id.pie: {
                ACProgressPie.Builder builder = new ACProgressPie.Builder(this);
                ACProgressPie pie = builder.build();
                pie.setCancelable(true);
                pie.show();
            }
            break;
            case R.id.custom: {
                ACProgressCustom.Builder builder = new ACProgressCustom.Builder(this);
                builder.useImages(R.drawable.refresh_0, R.drawable.refresh_1, R.drawable.refresh_2, R.drawable.refresh_3, R.drawable.refresh_4, R.drawable.refresh_5);
                builder.sizeRatio(0.15f);
                ACProgressCustom custom = builder.build();
                custom.setCancelable(true);
                custom.show();
            }
            break;
        }
    }
}
