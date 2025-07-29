package com.qdesk.permissionhelper.sample;

// ========================================
// 6. Sample/SampleActivity.java
// ========================================

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.qdesk.permissionhelper.R;
import android.Manifest;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.qdesk.permissionhelper.core.PermissionCallback;
import com.qdesk.permissionhelper.core.PermissionManager;

/**
 * Sample activity demonstrating the Permission Helper usage
 */
public class SampleActivity extends AppCompatActivity {

    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        // Initialize Permission Manager
        permissionManager = new PermissionManager(this);

        setupButtons();
    }

    private void setupButtons() {
        Button cameraButton = findViewById(R.id.btn_camera_permission);
        Button storageButton = findViewById(R.id.btn_storage_permission);
        Button locationButton = findViewById(R.id.btn_location_permission);

        cameraButton.setOnClickListener(v -> requestCameraPermission());
        storageButton.setOnClickListener(v -> requestStoragePermission());
        locationButton.setOnClickListener(v -> requestLocationPermission());
    }

    private void requestCameraPermission() {
        permissionManager.requestPermission(Manifest.permission.CAMERA, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String permission) {
                showToast("Camera permission granted! 📸");
                // Proceed with camera functionality
            }

            @Override
            public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                if (isPermanentlyDenied) {
                    showToast("Camera permission permanently denied. Please enable in Settings.");
                } else {
                    showToast("Camera permission denied.");
                }
            }

            @Override
            public void onPermissionCancelled(String permission) {
                showToast("Camera permission request cancelled.");
            }
        });
    }

    private void requestStoragePermission() {
        permissionManager.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String permission) {
                showToast("Storage permission granted! 💾");
            }

            @Override
            public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                showToast(isPermanentlyDenied ?
                        "Storage permission permanently denied." :
                        "Storage permission denied.");
            }

            @Override
            public void onPermissionCancelled(String permission) {
                showToast("Storage permission request cancelled.");
            }
        });
    }

    private void requestLocationPermission() {
        permissionManager.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String permission) {
                showToast("Location permission granted! 📍");
            }

            @Override
            public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                showToast(isPermanentlyDenied ?
                        "Location permission permanently denied." :
                        "Location permission denied.");
            }

            @Override
            public void onPermissionCancelled(String permission) {
                showToast("Location permission request cancelled.");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Important: Forward the result to PermissionManager
        permissionManager.handlePermissionResult(requestCode, permissions, grantResults);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}