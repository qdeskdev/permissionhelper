package com.qdesk.permissionhelper.utils;

import android.Manifest;
import com.qdesk.permissionhelper.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Updated utility class for permission-related operations with proper icon mapping
 */
public class PermissionUtils {

    private static final Map<String, String> PERMISSION_TITLES = new HashMap<>();
    private static final Map<String, String> PERMISSION_RATIONALES = new HashMap<>();
    private static final Map<String, Integer> PERMISSION_ICONS = new HashMap<>();

    static {
        // Camera Permission
        PERMISSION_TITLES.put(Manifest.permission.CAMERA, "Camera Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.CAMERA,
                "This app needs camera access to take photos and videos. Please allow camera permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.CAMERA, R.drawable.ic_camera);

        // Storage Permission
        PERMISSION_TITLES.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "Storage Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                "This app needs storage access to save and manage your files. Please allow storage permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.drawable.ic_storage);

        PERMISSION_TITLES.put(Manifest.permission.READ_EXTERNAL_STORAGE, "Storage Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.READ_EXTERNAL_STORAGE,
                "This app needs storage access to read and manage your files. Please allow storage permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.READ_EXTERNAL_STORAGE, R.drawable.ic_storage);

        // Location Permission
        PERMISSION_TITLES.put(Manifest.permission.ACCESS_FINE_LOCATION, "Location Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.ACCESS_FINE_LOCATION,
                "This app needs location access to provide location-based features. Please allow location permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.ACCESS_FINE_LOCATION, R.drawable.ic_location);

        PERMISSION_TITLES.put(Manifest.permission.ACCESS_COARSE_LOCATION, "Location Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.ACCESS_COARSE_LOCATION,
                "This app needs location access to provide location-based features. Please allow location permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.ACCESS_COARSE_LOCATION, R.drawable.ic_location);

        // Microphone Permission
        PERMISSION_TITLES.put(Manifest.permission.RECORD_AUDIO, "Microphone Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.RECORD_AUDIO,
                "This app needs microphone access to record audio. Please allow microphone permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.RECORD_AUDIO, R.drawable.ic_microphone);

        // Contacts Permission
        PERMISSION_TITLES.put(Manifest.permission.READ_CONTACTS, "Contacts Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.READ_CONTACTS,
                "This app needs contacts access to help you connect with friends. Please allow contacts permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.READ_CONTACTS, R.drawable.ic_contacts);

        PERMISSION_TITLES.put(Manifest.permission.WRITE_CONTACTS, "Contacts Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.WRITE_CONTACTS,
                "This app needs contacts access to manage your contacts. Please allow contacts permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.WRITE_CONTACTS, R.drawable.ic_contacts);

        // Phone Permission
        PERMISSION_TITLES.put(Manifest.permission.CALL_PHONE, "Phone Access Required");
        PERMISSION_RATIONALES.put(Manifest.permission.CALL_PHONE,
                "This app needs phone access to make calls. Please allow phone permission to continue.");
        PERMISSION_ICONS.put(Manifest.permission.CALL_PHONE, R.drawable.ic_permission_default);
    }

    public static String getPermissionTitle(String permission) {
        return PERMISSION_TITLES.getOrDefault(permission, "Permission Required");
    }

    public static String getPermissionRationale(String permission) {
        return PERMISSION_RATIONALES.getOrDefault(permission,
                "This permission is required for the app to function properly. Please allow this permission to continue.");
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
            case Manifest.permission.WRITE_CONTACTS:
                return "Contacts";
            case Manifest.permission.CALL_PHONE:
                return "Phone";
            default:
                return "Permission";
        }
    }
}