package cc.cloudist.acpsample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressCustom;
import cc.cloudist.acplibrary.ACProgressFlower;
import cc.cloudist.acplibrary.ACProgressPie;

public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
        findViewById(R.id.button_5).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_1: {
                ACProgressFlower dialog = new ACProgressFlower.Builder(this, R.style.NonDimACProgressDialog)
                        .direction(ACProgressConstant.DIRECT_CLOCKWISE)
                        .themeColor(Color.WHITE)
                        .fadeColor(Color.DKGRAY).build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            break;
            case R.id.button_2: {
                ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                        .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
                        .themeColor(Color.GREEN)
                        .fadeColor(Color.RED).build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            break;
            case R.id.button_3: {
                ACProgressFlower dialog = new ACProgressFlower.Builder(this)
                        .text("Text is here")
                        .build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            break;
            case R.id.button_4: {
                ACProgressPie dialog = new ACProgressPie.Builder(this).build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            break;
            case R.id.button_5: {
                ACProgressCustom dialog = new ACProgressCustom.Builder(this)
                        .useImages(R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3).build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
            }
            break;
        }
    }
}
