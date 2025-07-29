package com.qdesk.permissionhelper.ui;

// ========================================
// 4. UI/PermissionDialog.java
// ========================================


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.qdesk.permissionhelper.R;

/**
 * Custom dialog for showing permission rationale with smooth animations
 */
public class PermissionDialog extends Dialog {

    public interface OnPermissionDialogListener {
        void onAllow();
        void onDeny();
        void onCancel();
    }

    private String title;
    private String message;
    private int iconRes;
    private OnPermissionDialogListener listener;

    public PermissionDialog(Context context, String title, String message, int iconRes) {
        super(context);
        this.title = title;
        this.message = message;
        this.iconRes = iconRes;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_permission);

        setupViews();
        setupAnimations();
    }

    private void setupViews() {
        ImageView iconView = findViewById(R.id.iv_permission_icon);
        TextView titleView = findViewById(R.id.tv_permission_title);
        TextView messageView = findViewById(R.id.tv_permission_message);
        Button allowButton = findViewById(R.id.btn_allow);
        Button denyButton = findViewById(R.id.btn_deny);

        iconView.setImageResource(iconRes);
        titleView.setText(title);
        messageView.setText(message);

        allowButton.setOnClickListener(v -> {
            if (listener != null) listener.onAllow();
            dismiss();
        });

        denyButton.setOnClickListener(v -> {
            if (listener != null) listener.onDeny();
            dismiss();
        });

        setOnCancelListener(dialog -> {
            if (listener != null) listener.onCancel();
        });
    }

    private void setupAnimations() {
        View dialogContainer = findViewById(R.id.dialog_container);
        dialogContainer.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.dialog_fade_in));
    }

    public void setOnPermissionDialogListener(OnPermissionDialogListener listener) {
        this.listener = listener;
    }
}
