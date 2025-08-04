# 🔐 Permission Helper Library for Android

[![JitPack](https://jitpack.io/v/qdeskdev/permissionhelper.svg)](https://jitpack.io/#qdeskdev/permissionhelper)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Android API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

A professional, user-friendly Android library that simplifies runtime permission handling with beautiful Material Design dialogs and smart UX flow. No more complex permission logic - just clean, simple code that works.

## 🎯 Why Use This Library?

- **🎨 Beautiful UI**: Material Design dialogs with smooth animations
- **🧠 Smart Logic**: Handles rationale, retry, and settings navigation automatically
- **🔄 Auto-retry**: Gives users a second chance if they accidentally deny
- **📱 Zero Setup**: Works out of the box with any Activity
- **⚡ One-line Integration**: Request any permission with just one method call
- **🎭 Fully Customizable**: Easy to theme and customize for your app

## 📦 Installation

### Step 1: Add JitPack Repository
Add this to your **project-level** `build.gradle` file:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' } // Add this line
    }
}
```

### Step 2: Add Dependency
Add this to your **app-level** `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.qdeskdev:permissionhelper:1.0.8'
    
    // Required dependencies (if not already included)
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.cardview:cardview:1.0.0'
    implementation 'com.google.android.material:material:1.9.0'
}
```

## 🚀 Quick Start (30 Seconds Setup)

### 1. Add Permissions to Manifest
Add the permissions you need to your `AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <!-- Add the permissions your app needs -->
    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    <uses-permission android:name="android.permission.RECORD_AUDIO" />
    <uses-permission android:name="android.permission.READ_CONTACTS" />
    
    <application>
        <!-- Your activities -->
    </application>
</manifest>
```

### 2. Basic Usage in Activity

```java
public class MainActivity extends AppCompatActivity {
    private PermissionManager permissionManager;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Step 1: Initialize Permission Manager
        permissionManager = new PermissionManager(this);
        
        // Step 2: Request permission when needed
        Button cameraButton = findViewById(R.id.btn_camera);
        cameraButton.setOnClickListener(v -> requestCameraPermission());
    }
    
    private void requestCameraPermission() {
        permissionManager.requestPermission(Manifest.permission.CAMERA, new PermissionCallback() {
            @Override
            public void onPermissionGranted(String permission) {
                // ✅ Permission granted - start camera
                Toast.makeText(MainActivity.this, "Camera permission granted!", Toast.LENGTH_SHORT).show();
                openCamera();
            }
            
            @Override
            public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                // ❌ Permission denied
                if (isPermanentlyDenied) {
                    Toast.makeText(MainActivity.this, "Please enable camera permission in Settings", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Camera permission is required", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onPermissionCancelled(String permission) {
                // 🚫 User cancelled the dialog
                Toast.makeText(MainActivity.this, "Permission request cancelled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    // Step 3: Forward permission results (IMPORTANT!)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionManager.handlePermissionResult(requestCode, permissions, grantResults);
    }
    
    private void openCamera() {
        // Your camera code here
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(intent);
    }
}
```

## 📱 Supported Permissions & Use Cases

The library includes built-in support for common Android permissions with user-friendly messages:

| Permission | Constant | Use Case | Icon | Auto Message |
|------------|----------|----------|------|--------------|
| **Camera** | `Manifest.permission.CAMERA` | Take photos/videos | 📸 | "This app needs camera access to take photos and videos." |
| **Storage** | `Manifest.permission.WRITE_EXTERNAL_STORAGE` | Save files | 💾 | "This app needs storage access to save and manage your files." |
| **Location** | `Manifest.permission.ACCESS_FINE_LOCATION` | GPS features | 📍 | "This app needs location access to provide location-based features." |
| **Microphone** | `Manifest.permission.RECORD_AUDIO` | Record audio | 🎤 | "This app needs microphone access to record audio." |
| **Contacts** | `Manifest.permission.READ_CONTACTS` | Access contacts | 👥 | "This app needs contacts access to help you connect with friends." |

## 🎯 Complete Examples

### Example 1: Camera Permission
```java
private void takePicture() {
    permissionManager.requestPermission(Manifest.permission.CAMERA, new PermissionCallback() {
        @Override
        public void onPermissionGranted(String permission) {
            // Open camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
        
        @Override
        public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
            String message = isPermanentlyDenied ? 
                "Camera permission permanently denied. Please enable in Settings." :
                "Camera permission is required to take photos.";
            showSnackbar(message);
        }
        
        @Override
        public void onPermissionCancelled(String permission) {
            showSnackbar("Camera permission request was cancelled.");
        }
    });
}
```

### Example 2: Location Permission
```java
private void getCurrentLocation() {
    permissionManager.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, new PermissionCallback() {
        @Override
        public void onPermissionGranted(String permission) {
            // Get location using LocationManager or FusedLocationProviderClient
            Toast.makeText(MainActivity.this, "Getting your location...", Toast.LENGTH_SHORT).show();
            startLocationUpdates();
        }
        
        @Override
        public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
            if (isPermanentlyDenied) {
                showAlertDialog("Location Required", 
                    "This app needs location access to work properly. Please enable it in Settings.");
            } else {
                Toast.makeText(MainActivity.this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
        
        @Override
        public void onPermissionCancelled(String permission) {
            // Handle cancellation
        }
    });
}
```

### Example 3: Storage Permission
```java
private void saveFile() {
    // Note: For Android 10+ (API 29+), consider using Scoped Storage instead
    permissionManager.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
        @Override
        public void onPermissionGranted(String permission) {
            // Save file to external storage
            saveFileToStorage();
        }
        
        @Override
        public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
            if (isPermanentlyDenied) {
                showStoragePermissionDialog();
            } else {
                Toast.makeText(MainActivity.this, "Storage permission required", Toast.LENGTH_SHORT).show();
            }
        }
        
        @Override
        public void onPermissionCancelled(String permission) {
            // User cancelled
        }
    });
}
```

### Example 4: Multiple Permissions (Sequential)
```java
private void requestMultiplePermissions() {
    // Request camera first
    permissionManager.requestPermission(Manifest.permission.CAMERA, new PermissionCallback() {
        @Override
        public void onPermissionGranted(String permission) {
            // Camera granted, now request storage
            permissionManager.requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void onPermissionGranted(String permission) {
                    // Both permissions granted
                    Toast.makeText(MainActivity.this, "All permissions granted!", Toast.LENGTH_SHORT).show();
                    startCameraWithStorage();
                }
                
                @Override
                public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
                    Toast.makeText(MainActivity.this, "Storage permission denied", Toast.LENGTH_SHORT).show();
                }
                
                @Override
                public void onPermissionCancelled(String permission) {
                    // Handle cancellation
                }
            });
        }
        
        @Override
        public void onPermissionDenied(String permission, boolean isPermanentlyDenied) {
            Toast.makeText(MainActivity.this, "Camera permission denied", Toast.LENGTH_SHORT).show();
        }
        
        @Override
        public void onPermissionCancelled(String permission) {
            // Handle cancellation
        }
    });
}
```

## 🧠 How The Smart Flow Works

The library automatically handles the complex Android permission flow:

```
1. Check if permission is already granted ✅
   ↓ (if not granted)
2. Check if rationale should be shown 🤔
   ↓ (show beautiful dialog explaining why)
3. Request permission from system 📱
   ↓ (if denied)
4. Auto-retry once with explanation 🔄
   ↓ (if still denied)
5. Guide user to Settings if permanently denied ⚙️
```

### What This Means For You:
- **No complex logic needed** - Just call one method
- **Better user experience** - Users understand why permissions are needed
- **Higher grant rates** - Smart retry and rationale dialogs
- **Handles edge cases** - Permanent denial, settings navigation, etc.

## 🎨 UI Components Explained

### 1. Permission Rationale Dialog
When a permission needs explanation, users see a beautiful Material Design dialog with:
- **Custom icon** for each permission type
- **Clear title** (e.g., "Camera Access Required")
- **Helpful message** explaining why you need the permission
- **Two buttons**: "Allow" and "Not Now"
- **Smooth animations** for professional feel

### 2. Settings Navigation Prompt  
When permission is permanently denied, users see a bottom sheet with:
- **Settings icon** to indicate next step
- **Clear message** asking them to enable in Settings
- **"Open Settings" button** that takes them directly to your app settings
- **Slide-up animation** for smooth UX

## ⚙️ Customization Options

### Adding Your Own Permission Messages
Edit `PermissionUtils.java` to add custom permissions:

```java
// In PermissionUtils.java static block
PERMISSION_TITLES.put(Manifest.permission.CALL_PHONE, "Phone Access");
PERMISSION_RATIONALES.put(Manifest.permission.CALL_PHONE, 
    "This app needs phone access to make calls directly from the app.");
PERMISSION_ICONS.put(Manifest.permission.CALL_PHONE, R.drawable.ic_phone);
```

### Custom Colors & Themes
Override these in your app's `colors.xml`:

```xml
<resources>
    <color name="colorAccent">#2196F3</color>
    <color name="permission_button_primary">#2196F3</color>
    <color name="permission_button_outline">#757575</color>
</resources>
```

### Custom Button Styles
Create your own button backgrounds:
- `res/drawable/button_primary.xml` - Primary action button
- `res/drawable/button_outline.xml` - Secondary action button

## 🔧 Advanced Usage

### Check Permission Status
```java
// Check if permission is granted without requesting
if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
    // Permission already granted
    openCamera();
} else {
    // Request permission
    permissionManager.requestPermission(Manifest.permission.CAMERA, callback);
}
```

### Handle Activity Lifecycle
```java
@Override
protected void onResume() {
    super.onResume();
    // Check if user granted permission in Settings and came back
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
        // Permission granted, update UI
        updateCameraButton(true);
    }
}
```

## 🐛 Troubleshooting

### Common Issues & Solutions

**Issue**: Permission dialog not showing
```java
// ❌ Wrong - Missing onRequestPermissionsResult
public class MainActivity extends AppCompatActivity {
    // Missing the required method below
}

// ✅ Correct - Always include this method
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    permissionManager.handlePermissionResult(requestCode, permissions, grantResults);
}
```

**Issue**: Callback not triggered
```java
// ❌ Wrong - Not storing PermissionManager reference
new PermissionManager(this).requestPermission(...); // Gets garbage collected

// ✅ Correct - Store as instance variable
private PermissionManager permissionManager;
permissionManager = new PermissionManager(this);
```

**Issue**: App crashes on permission request
```java
// ❌ Wrong - Activity is null
permissionManager = new PermissionManager(null);

// ✅ Correct - Pass valid Activity context
permissionManager = new PermissionManager(this);
```

## 📋 Testing Your Implementation

### Test Checklist
- [ ] **First request**: Permission granted immediately
- [ ] **Show rationale**: User sees explanation dialog
- [ ] **User denies**: App handles gracefully
- [ ] **Permanent denial**: User guided to Settings
- [ ] **Settings return**: App works when user comes back
- [ ] **Multiple requests**: Sequential permissions work
- [ ] **Rotation**: Works after screen rotation

### Test Code Examples
```java
// Test different scenarios
private void testPermissionFlow() {
    // Scenario 1: First time request
    permissionManager.requestPermission(Manifest.permission.CAMERA, testCallback);
    
    // Scenario 2: After user denied once
    // (Library will show rationale dialog)
    
    // Scenario 3: After permanent denial
    // (Library will show settings prompt)
}
```

## 💡 Best Practices

### ✅ Do's
- **Request contextually** - Ask when user tries to use the feature
- **Explain benefits** - Use clear, benefit-focused messages  
- **Handle all outcomes** - Implement all callback methods
- **Test thoroughly** - Test grant, deny, and permanent denial
- **Provide alternatives** - App should work without optional permissions

### ❌ Don'ts  
- **Don't request on app start** - Wait for user to trigger feature
- **Don't ignore permanent denial** - Always handle isPermanentlyDenied
- **Don't request unnecessary permissions** - Only ask what you actually need
- **Don't forget onRequestPermissionsResult** - Always include this method
- **Don't assume permissions persist** - Always check before using features

## 🤝 Contributing

We welcome contributions! Please:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests if applicable
5. Commit your changes (`git commit -m 'Add amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)  
7. Open a Pull Request

## 📄 License

```
MIT License

Copyright (c) 2024 Permission Helper Library

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

## 📞 Support

- **GitHub Issues**: [Report bugs or request features](https://github.com/qdeskdev/permissionhelper/issues)
- **Documentation**: Check this README and sample code
---

**Made with ❤️ for Android developers who care about user experience**

### Quick Links
- 📦 [Installation](#-installation)
- 🚀 [Quick Start](#-quick-start-30-seconds-setup)  
- 📱 [Supported Permissions](#-supported-permissions--use-cases)
- 🎯 [Complete Examples](#-complete-examples)
- 🔧 [Advanced Usage](#-advanced-usage)
- 🐛 [Troubleshooting](#-troubleshooting)

---

If really help this, like the library? Give it a ⭐ on  [GitHub](https://github.com/qdeskdev/permissionhelper/)

<div align="center">
  <h3>Happy Coding 🚀</h3>
</div>

