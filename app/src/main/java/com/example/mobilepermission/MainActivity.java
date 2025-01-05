package com.example.mobilepermission;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_AUDIO = 1;
    private static final int REQUEST_CODE_STORAGE = 2;
    private static final int REQUEST_CODE_CAMERA = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestPermissionButton = findViewById(R.id.request_permission_button);
        requestPermissionButton.setOnClickListener(v -> requestAudioPermission());
    }

    private void requestAudioPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_AUDIO);
        } else {
            Toast.makeText(this, "Audio permission already granted", Toast.LENGTH_SHORT).show();
            requestCameraPermission(); // Proceed to camera permission
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE);
        } else {
            Toast.makeText(this, "Storage permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(this, "Camera permission already granted", Toast.LENGTH_SHORT).show();
            requestStoragePermission(); // Proceed to storage permission
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_CODE_AUDIO:
                    Toast.makeText(this, "Audio permission granted", Toast.LENGTH_SHORT).show();
                    requestCameraPermission(); // Move to the next permission
                    break;

                case REQUEST_CODE_STORAGE:
                    Toast.makeText(this, "Storage permission granted", Toast.LENGTH_SHORT).show();
                    break;

                case REQUEST_CODE_CAMERA:
                    Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show();
                    requestStoragePermission(); // Move to storage permission
                    break;
            }
        } else {
            switch (requestCode) {
                case REQUEST_CODE_AUDIO:
                    Toast.makeText(this, "Audio permission denied", Toast.LENGTH_SHORT).show();
                    requestCameraPermission(); // Proceed to camera permission even if audio is denied
                    break;

                case REQUEST_CODE_STORAGE:
                    Toast.makeText(this, "Storage permission denied", Toast.LENGTH_SHORT).show();
                    break;

                case REQUEST_CODE_CAMERA:
                    Toast.makeText(this, "Camera permission denied", Toast.LENGTH_SHORT).show();
                    requestStoragePermission(); // Move to storage permission even if camera is denied
                    break;
            }
        }
    }
}
