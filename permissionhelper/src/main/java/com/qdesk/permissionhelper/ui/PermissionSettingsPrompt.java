package com.qdesk.permissionhelper.ui;

// ========================================
// 5. UI/PermissionSettingsPrompt.java
// ========================================


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.qdesk.permissionhelper.R;

/**
 * Bottom sheet style prompt for directing users to app settings
 */
public class PermissionSettingsPrompt extends Dialog {

    public interface OnSettingsPromptListener {
        void onOpenSettings();
        void onCancel();
    }

    private String title;
    private String message;
    private OnSettingsPromptListener listener;

    public PermissionSettingsPrompt(Context context, String title, String message) {
        super(context);
        this.title = title;
        this.message = message;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().setGravity(Gravity.BOTTOM);
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);
        }
        setCancelable(true);
        setCanceledOnTouchOutside(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_settings_prompt);

        setupViews();
        setupAnimations();
    }

    private void setupViews() {
        TextView titleView = findViewById(R.id.tv_settings_title);
        TextView messageView = findViewById(R.id.tv_settings_message);
        Button openSettingsButton = findViewById(R.id.btn_open_settings);
        Button cancelButton = findViewById(R.id.btn_cancel);

        titleView.setText(title);
        messageView.setText(message);

        openSettingsButton.setOnClickListener(v -> {
            if (listener != null) listener.onOpenSettings();
            dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            if (listener != null) listener.onCancel();
            dismiss();
        });

        setOnCancelListener(dialog -> {
            if (listener != null) listener.onCancel();
        });
    }

    private void setupAnimations() {
        View promptContainer = findViewById(R.id.settings_prompt_container);
        promptContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.slide_up));
    }

    public void setOnSettingsPromptListener(OnSettingsPromptListener listener) {
        this.listener = listener;
    }
}
