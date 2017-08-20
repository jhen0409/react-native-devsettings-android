package me.jhen.devsettings;

import android.support.annotation.Nullable;
import android.app.Activity;
import android.os.Handler;

import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.devsupport.DevInternalSettings;
import com.facebook.react.devsupport.DevSupportManagerImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.HashMap;
import java.util.Map;

public class DevSettingsModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "DevSettings";
    private ReactApplicationContext reactContext = null;
    private ReactInstanceManager instanceManager = null;
    private DevSupportManagerImpl devManager = null;

    public DevSettingsModule(ReactApplicationContext context) {
        super(context);

        reactContext = context;
        instanceManager = ((ReactApplication) context.getApplicationContext())
          .getReactNativeHost()
          .getReactInstanceManager();
        devManager = ((DevSupportManagerImpl) instanceManager.getDevSupportManager());
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    private void handleReloadJS() {
        Activity activity = getCurrentActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                devManager.handleReloadJS();
            }
        });
    }

    @ReactMethod
    public void reload(Promise promise) {
        promise.resolve(null);
        new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    handleReloadJS();
                }
            },
        50);
    }

    @ReactMethod
    public void toggleElementInspector(Promise promise) {
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setElementInspectorEnabled(!mDevSettings.isElementInspectorEnabled());
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("toggleElementInspector", null);
        promise.resolve(null);
    }

    @ReactMethod
    public void setLiveReloadEnabled(final boolean enabled, Promise promise) {
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setReloadOnJSChangeEnabled(!mDevSettings.isReloadOnJSChangeEnabled());
        promise.resolve(null);
    }

    @ReactMethod
    public void setHotLoadingEnabled(final boolean enabled, Promise promise) {
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setHotModuleReplacementEnabled(!mDevSettings.isHotModuleReplacementEnabled());
        promise.resolve(null);
        handleReloadJS();
    }

    @ReactMethod
    public void setIsDebuggingRemotely(final boolean enabled, Promise promise) {
        promise.resolve(null);
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setRemoteJSDebugEnabled(enabled);
        new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    handleReloadJS();
                }
            },
        50);
    }

    @ReactMethod
    public void show(Promise promise) {
        devManager.showDevOptionsDialog();
        promise.resolve(null);
    }
}
