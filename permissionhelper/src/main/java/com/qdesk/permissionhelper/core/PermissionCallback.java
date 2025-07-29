package com.qdesk.permissionhelper.core;
// ========================================
// 1. Core/PermissionCallback.java
// ========================================

/**
 * Callback interface for handling permission request results
 */
public interface PermissionCallback {
    /**
     * Called when permission is granted
     * @param permission The granted permission
     */
    void onPermissionGranted(String permission);

    /**
     * Called when permission is denied
     * @param permission The denied permission
     * @param isPermanentlyDenied Whether the permission is permanently denied
     */
    void onPermissionDenied(String permission, boolean isPermanentlyDenied);

    /**
     * Called when user cancels the permission request
     * @param permission The permission that was cancelled
     */
    void onPermissionCancelled(String permission);
}
