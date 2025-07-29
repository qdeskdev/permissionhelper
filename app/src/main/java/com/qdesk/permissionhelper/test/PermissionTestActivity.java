package com.qdesk.permissionhelper.test;

// ========================================
// PermissionTestActivity.java
// ========================================

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.Manifest;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.qdesk.permissionhelper.core.PermissionCallback;
import com.qdesk.permissionhelper.core.PermissionManager;

/**
 * Complete test Activity for Permission Helper Library
 * This demonstrates all features and provides a comprehensive testing interface
 */
public class PermissionTestActivity extends AppCompatActivity {

    private PermissionManager permissionManager;
    private LinearLayout logContainer;
    private ScrollView scrollView;
    private int testCounter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();

        // Initialize Permission Manager
        permissionManager = new PermissionManager(this);

        logMessage("🚀 Permission Helper Test Started", "#4CAF50");
        logMessage("📱 Tap any permission button to test the flow", "#2196F3");
    }

    /**
     * Create UI programmatically for easy testing
     */
    private void setupUI() {
        // Main ScrollView
        scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));
        scrollView.setPadding(32, 32, 32, 32);
        scrollView.setBackgroundColor(Color.parseColor("#F5F5F5"));

        // Main container
        LinearLayout mainContainer = new LinearLayout(this);
        mainContainer.setOrientation(LinearLayout.VERTICAL);
        mainContainer.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        // Title
        TextView title = createTitle("🔐 Permission Helper Test Lab");
        mainContainer.addView(title);

        // Test buttons section
        CardView permissionCard = createCard("Permission Tests");
        LinearLayout permissionContainer = new LinearLayout(this);
        permissionContainer.setOrientation(LinearLayout.VERTICAL);
        permissionContainer.setPadding(24, 24, 24, 24);

        // Add all test buttons
        permissionContainer.addView(createPermissionButton("📸 Test Camera Permission",
                Manifest.permission.CAMERA, "#FF5722"));
        permissionContainer.addView(createPermissionButton("💾 Test Storage Permission",
                Manifest.permission.WRITE_EXTERNAL_STORAGE, "#9C27B0"));
        permissionContainer.addView(createPermissionButton("📍 Test Location Permission",
                Manifest.permission.ACCESS_FINE_LOCATION, "#FF9800"));
        permissionContainer.addView(createPermissionButton("🎤 Test Microphone Permission",
                Manifest.permission.RECORD_AUDIO, "#F44336"));
        permissionContainer.addView(createPermissionButton("👥 Test Contacts Permission",
                Manifest.permission.READ_CONTACTS, "#3F51B5"));
        permissionContainer.addView(createPermissionButton("📞 Test Phone Permission",
                Manifest.permission.CALL_PHONE, "#009688"));

        // Add utility buttons
        permissionContainer.addView(createSpacing(16));
        permissionContainer.addView(createUtilityButton("📱 Open App Settings", this::openAppSettings));
        permissionContainer.addView(createUtilityButton("🧹 Clear Log", this::clearLog));

        permissionCard.addView(permissionContainer);
        mainContainer.addView(permissionCard);

        // Log section
        CardView logCard = createCard("Test Log");
        logContainer = new LinearLayout(this);
        logContainer.setOrientation(LinearLayout.VERTICAL);
        logContainer.setPadding(24, 24, 24, 24);
        logCard.addView(logContainer);
        mainContainer.addView(logCard);

        scrollView.addView(mainContainer);
        setContentView(scrollView);
    }

    /**
     * Create title TextView
     */
    private TextView createTitle(String text) {
        TextView title = new TextView(this);
        title.setText(text);
        title.setTextSize(24);
        title.setTextColor(Color.parseColor("#333333"));
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 32);
        title.setLayoutParams(params);
        return title;
    }

    /**
     * Create card container
     */
    private CardView createCard(String title) {
        CardView card = new CardView(this);
        card.setRadius(16);
        card.setCardElevation(8);
        card.setUseCompatPadding(true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 16);
        card.setLayoutParams(params);

        return card;
    }

    /**
     * Create permission test button
     */
    private Button createPermissionButton(String text, String permission, String colorHex) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(Color.WHITE);
        button.setTextSize(16);
        button.setBackgroundColor(Color.parseColor(colorHex));
        button.setAllCaps(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 12);
        button.setLayoutParams(params);

        button.setOnClickListener(v -> testPermission(permission, text));

        return button;
    }

    /**
     * Create utility button
     */
    private Button createUtilityButton(String text, Runnable action) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextColor(Color.parseColor("#666666"));
        button.setTextSize(14);
        button.setBackgroundColor(Color.parseColor("#E0E0E0"));
        button.setAllCaps(false);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 8);
        button.setLayoutParams(params);

        button.setOnClickListener(v -> action.run());

        return button;
    }

    /**
     * Create spacing view
     */
    private View createSpacing(int heightDp) {
        View spacing = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dpToPx(heightDp));
        spacing.setLayoutParams(params);
        return spacing;
    }

    /**
     * Test a specific permission
     */
    private void testPermission(String permission, String buttonText) {
        logMessage("🧪 Test #" + testCounter + ": " + buttonText, "#2196F3");
        testCounter++;

        // Show current permission status
        boolean isGranted = ContextCompat.checkSelfPermission(this, permission)
                == android.content.pm.PackageManager.PERMISSION_GRANTED;
        logMessage("📊 Current Status: " + (isGranted ? "✅ GRANTED" : "❌ NOT GRANTED"),
                isGranted ? "#4CAF50" : "#FF5722");

        permissionManager.requestPermission(permission, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String permission) {
                logMessage("✅ SUCCESS: Permission granted!", "#4CAF50");
                showToast("✅ " + getPermissionName(permission) + " permission granted!");

                // Demonstrate feature usage
                demonstrateFeature(permission);
            }

            @Override
            public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                if (isPermanentlyDenied) {
                    logMessage("🚫 PERMANENTLY DENIED: User must enable in Settings", "#F44336");
                    showToast("🚫 " + getPermissionName(permission) + " permanently denied");
                } else {
                    logMessage("❌ DENIED: Permission denied but can retry", "#FF9800");
                    showToast("❌ " + getPermissionName(permission) + " permission denied");
                }
            }

            @Override
            public void onPermissionCancelled(String permission) {
                logMessage("🚫 CANCELLED: User cancelled the request", "#9E9E9E");
                showToast("🚫 " + getPermissionName(permission) + " request cancelled");
            }
        });
    }

    /**
     * Demonstrate feature usage after permission is granted
     */
    private void demonstrateFeature(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA:
                logMessage("📸 Demo: Opening camera app...", "#4CAF50");
                try {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(cameraIntent);
                    }
                } catch (Exception e) {
                    logMessage("⚠️ Camera app not available", "#FF9800");
                }
                break;

            case Manifest.permission.ACCESS_FINE_LOCATION:
                logMessage("📍 Demo: Location feature now available", "#4CAF50");
                showToast("📍 You can now use location-based features!");
                break;

            case Manifest.permission.WRITE_EXTERNAL_STORAGE:
                logMessage("💾 Demo: Storage access granted", "#4CAF50");
                showToast("💾 You can now save files to storage!");
                break;

            case Manifest.permission.RECORD_AUDIO:
                logMessage("🎤 Demo: Microphone access granted", "#4CAF50");
                showToast("🎤 You can now record audio!");
                break;

            case Manifest.permission.READ_CONTACTS:
                logMessage("👥 Demo: Contacts access granted", "#4CAF50");
                showToast("👥 You can now access contacts!");
                break;

            case Manifest.permission.CALL_PHONE:
                logMessage("📞 Demo: Phone access granted", "#4CAF50");
                showToast("📞 You can now make phone calls!");
                break;
        }
    }

    /**
     * Get user-friendly permission name
     */
    private String getPermissionName(String permission) {
        switch (permission) {
            case Manifest.permission.CAMERA: return "Camera";
            case Manifest.permission.WRITE_EXTERNAL_STORAGE: return "Storage";
            case Manifest.permission.ACCESS_FINE_LOCATION: return "Location";
            case Manifest.permission.RECORD_AUDIO: return "Microphone";
            case Manifest.permission.READ_CONTACTS: return "Contacts";
            case Manifest.permission.CALL_PHONE: return "Phone";
            default: return "Permission";
        }
    }

    /**
     * Add log message to the UI
     */
    private void logMessage(String message, String colorHex) {
        TextView logText = new TextView(this);
        logText.setText("• " + message);
        logText.setTextColor(Color.parseColor(colorHex));
        logText.setTextSize(12);
        logText.setPadding(0, 4, 0, 4);

        logContainer.addView(logText, 0); // Add to top

        // Auto-scroll to bottom
        scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_DOWN));
    }

    /**
     * Show toast message
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Open app settings
     */
    private void openAppSettings() {
        try {
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            android.net.Uri uri = android.net.Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            logMessage("⚙️ Opening app settings...", "#2196F3");
        } catch (Exception e) {
            showToast("❌ Could not open settings");
            logMessage("❌ Error opening settings: " + e.getMessage(), "#F44336");
        }
    }

    /**
     * Clear the log
     */
    private void clearLog() {
        logContainer.removeAllViews();
        testCounter = 1;
        logMessage("🧹 Log cleared - Ready for new tests!", "#4CAF50");
    }

    /**
     * Convert dp to pixels
     */
    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // CRITICAL: Forward the result to PermissionManager
        permissionManager.handlePermissionResult(requestCode, permissions, grantResults);

        // Log the system response
        if (permissions.length > 0) {
            boolean granted = grantResults.length > 0 && grantResults[0] == android.content.pm.PackageManager.PERMISSION_GRANTED;
            logMessage("📱 System Response: " + (granted ? "GRANTED" : "DENIED"),
                    granted ? "#4CAF50" : "#FF5722");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log when returning from settings
        logMessage("📱 App resumed - Check if permissions were enabled in Settings", "#2196F3");
    }
}