package com.qdesk.permissionhelper.utils;

// ========================================
// 3. Utils/PermissionUtils.java
// ========================================

import android.Manifest;
import com.qdesk.permissionhelper.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for permission-related operations
 */
public class PermissionUtils {

    private static final Map<String, String> PERMISSION_TITLES = new HashMap<>();
    private static final Map<String, String> PERMISSION_RATIONALES = new HashMap<>();
    private static final Map<String, Integer> PERMISSION_ICONS = new HashMap<>();

    static {
        // Camera
        PERMISSION_TITLES.put(Manifest.permission.CAMERA, "Camera Access");
        PERMISSION_RATIONALES.put(Manifest.permission.CAMERA,
                "This app needs camera access to take photos and videos.");
        PERMISSION_ICONS.put(Manifest.permission.CAMERA, R.drawable.ic_camera);

        // Storage
        PERMISSION_TITLES.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Storage Access");
        PERMISSION_RATIONALES.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "This app needs storage access to save and manage your files.");
        PERMISSION_ICONS.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.drawable.ic_storage);

        // Location
        PERMISSION_TITLES.put(Manifest.permission.ACCESS_FINE_LOCATION, "Location Access");
        PERMISSION_RATIONALES.put(Manifest.permission.ACCESS_FINE_LOCATION,
                "This app needs location access to provide location-based features.");
        PERMISSION_ICONS.put(Manifest.permission.ACCESS_FINE_LOCATION, R.drawable.ic_location);

        // Microphone
        PERMISSION_TITLES.put(Manifest.permission.RECORD_AUDIO, "Microphone Access");
        PERMISSION_RATIONALES.put(Manifest.permission.RECORD_AUDIO,
                "This app needs microphone access to record audio.");
        PERMISSION_ICONS.put(Manifest.permission.RECORD_AUDIO, R.drawable.ic_microphone);

        // Contacts
        PERMISSION_TITLES.put(Manifest.permission.READ_CONTACTS, "Contacts Access");
        PERMISSION_RATIONALES.put(Manifest.permission.READ_CONTACTS,
                "This app needs contacts access to help you connect with friends.");
        PERMISSION_ICONS.put(Manifest.permission.READ_CONTACTS, R.drawable.ic_contacts);
    }

    public static String getPermissionTitle(String permission) {
        return PERMISSION_TITLES.getOrDefault(permission, "Permission Required");
    }

    public static String getPermissionRationale(String permission) {
        return PERMISSION_RATIONALES.getOrDefault(permission,
                "This permission is required for the app to function properly.");
    }

    public static int getPermissionIcon(String permission) {
        return PERMISSION_ICONS.getOrDefault(permission, R.drawable.ic_permission_default);
    }

    /**
     * Get user-friendly permission name
     */
    public static String getPermissionDisplayName(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                return "Camera";
            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return "Storage";
            case Manifest.permission.ACCESS_FINE_LOCATION:
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                return "Location";
            case Manifest.permission.RECORD_AUDIO:
                return "Microphone";
            case Manifest.permission.READ_CONTACTS:
                return "Contacts";
            case Manifest.permission.CALL_PHONE:
                return "Phone";
            default:
                return "Permission";
        }
    }
}

