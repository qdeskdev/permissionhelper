package com.qdesk.permissionhelper.core;

// ========================================
// 2. Core/PermissionManager.java
// ========================================

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.qdesk.permissionhelper.ui.PermissionDialog;
import com.qdesk.permissionhelper.ui.PermissionSettingsPrompt;
import com.qdesk.permissionhelper.utils.PermissionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Main manager class for handling permission requests with smart UX flow
 */
public class PermissionManager {
    private static final int PERMISSION_REQUEST_CODE = 1001;

    private Activity activity;
    private PermissionCallback callback;
    private String currentPermission;
    private Map<String, Integer> permissionRetryCount;
    private static final int MAX_RETRY_COUNT = 1;

    public PermissionManager(@NonNull Activity activity) {
        this.activity = activity;
        this.permissionRetryCount = new HashMap<>();
    }

    /**
     * Request a single permission with smart UX flow
     * @param permission The permission to request
     * @param callback Callback to handle results
     */
    public void requestPermission(@NonNull String permission, @NonNull PermissionCallback callback) {
        this.callback = callback;
        this.currentPermission = permission;

        if (isPermissionGranted(permission)) {
            callback.onPermissionGranted(permission);
            return;
        }

        if (shouldShowRationale(permission)) {
            showRationaleDialog(permission);
        } else {
            requestPermissionDirectly(permission);
        }
    }

    /**
     * Check if permission is already granted
     */
    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Check if we should show rationale
     */
    private boolean shouldShowRationale(String permission) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
    }

    /**
     * Show rationale dialog with smooth UX
     */
    private void showRationaleDialog(String permission) {
        String title = PermissionUtils.getPermissionTitle(permission);
        String message = PermissionUtils.getPermissionRationale(permission);
        int iconRes = PermissionUtils.getPermissionIcon(permission);

        PermissionDialog dialog = new PermissionDialog(activity, title, message, iconRes);
        dialog.setOnPermissionDialogListener(new PermissionDialog.OnPermissionDialogListener() {
            @Override
            public void onAllow() {
                requestPermissionDirectly(permission);
            }

            @Override
            public void onDeny() {
                callback.onPermissionDenied(permission, false);
            }

            @Override
            public void onCancel() {
                callback.onPermissionCancelled(permission);
            }
        });
        dialog.show();
    }

    /**
     * Request permission directly from system
     */
    private void requestPermissionDirectly(String permission) {
        ActivityCompat.requestPermissions(activity, new String[]{permission}, PERMISSION_REQUEST_CODE);
    }

    /**
     * Handle permission result - call this from onRequestPermissionsResult
     */
    public void handlePermissionResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != PERMISSION_REQUEST_CODE) return;

        if (permissions.length == 0) return;

        String permission = permissions[0];
        boolean isGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

        if (isGranted) {
            callback.onPermissionGranted(permission);
        } else {
            handlePermissionDenied(permission);
        }
    }

    /**
     * Handle permission denied with retry logic
     */
    private void handlePermissionDenied(String permission) {
        int retryCount = permissionRetryCount.getOrDefault(permission, 0);
        boolean isPermanentlyDenied = !shouldShowRationale(permission) && retryCount > 0;

        if (isPermanentlyDenied) {
            showSettingsPrompt(permission);
        } else if (retryCount < MAX_RETRY_COUNT) {
            // Auto retry once
            permissionRetryCount.put(permission, retryCount + 1);
            showRationaleDialog(permission);
        } else {
            callback.onPermissionDenied(permission, false);
        }
    }

    /**
     * Show settings prompt for permanently denied permissions
     */
    private void showSettingsPrompt(String permission) {
        String title = "Permission Required";
        String message = String.format("Please enable %s permission in Settings to continue.",
                PermissionUtils.getPermissionTitle(permission));

        PermissionSettingsPrompt prompt = new PermissionSettingsPrompt(activity, title, message);
        prompt.setOnSettingsPromptListener(new PermissionSettingsPrompt.OnSettingsPromptListener() {
            @Override
            public void onOpenSettings() {
                openAppSettings();
            }

            @Override
            public void onCancel() {
                callback.onPermissionDenied(permission, true);
            }
        });
        prompt.show();
    }

    /**
     * Open app settings
     */
    private void openAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivity(intent);
    }
}

