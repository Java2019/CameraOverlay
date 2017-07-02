package com.samples.camera.overlay;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.Toast;

public class CameraPreviewActivity extends AppCompatActivity
        implements SurfaceHolder.Callback, View.OnClickListener{
    private ImageButton bStart;
    private ImageButton bStop;

    private SurfaceView surView;
    private SurfaceHolder surHolder;
    private Camera camera;
    private boolean isCameraPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_preview);
        surView = (SurfaceView)findViewById(R.id.surView);
        surHolder = surView.getHolder();
        surHolder.addCallback(this);
        // Создаем объект LayoutInflater
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        View overlay = inflater.inflate(R.layout.overlay, null);
        LayoutParams params = new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
        // Добавляем оверлей на поверхность Activity
        addContentView(overlay, params);
        bStart = (ImageButton)overlay.findViewById(R.id.bStart);
        bStop = (ImageButton)overlay.findViewById(R.id.bStop);
        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);
        bStop.setEnabled(false);

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (isCameraPreview) {
            camera.stopPreview();
            camera.release();
            isCameraPreview = false;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bStart:
                try {
                    camera = Camera.open();
                    camera.setPreviewDisplay(surHolder);
                    camera.startPreview();
                    isCameraPreview = true;
                    bStart.setEnabled(!isCameraPreview);
                    bStop.setEnabled(isCameraPreview);
                }
                catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.bStop:
                camera.stopPreview();
                camera.release();
                isCameraPreview = false;
                bStart.setEnabled(!isCameraPreview);
                bStop.setEnabled(isCameraPreview);
                break;
        }
    }
}
