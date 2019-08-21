package me.jhen.devsettings;

import android.app.Activity;
import android.os.Handler;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.devsupport.DevInternalSettings;
import com.facebook.react.devsupport.DevSupportManagerImpl;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class DevSettingsModule extends ReactContextBaseJavaModule {
    public static final String REACT_CLASS = "DevSettings";
    private ReactApplicationContext reactContext = null;
    private ReactInstanceManager instanceManager = null;
    private ReactNativeHost rnHost = null;
    private DevSupportManagerImpl devManager = null;
    private Boolean useDeveloperSupport = false;

    public DevSettingsModule(ReactApplicationContext context) {
        super(context);

        reactContext = context;
        rnHost = ((ReactApplication) context.getApplicationContext())
          .getReactNativeHost();
        instanceManager = rnHost.getReactInstanceManager();
        useDeveloperSupport = rnHost.getUseDeveloperSupport();
        if (useDeveloperSupport) {
            devManager = ((DevSupportManagerImpl) instanceManager.getDevSupportManager());
        }
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
    public void reload() {
        if (!useDeveloperSupport) return;
        new Handler().postDelayed(
            new Runnable() {
                public void run() {
                    handleReloadJS();
                }
            },
        50);
    }

    @ReactMethod
    public void toggleElementInspector() {
        if (!useDeveloperSupport) return;
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setElementInspectorEnabled(!mDevSettings.isElementInspectorEnabled());
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
            .emit("toggleElementInspector", null);
    }

    @ReactMethod
    public void setLiveReloadEnabled(final boolean enabled) {
        if (!useDeveloperSupport) return;
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        mDevSettings.setReloadOnJSChangeEnabled(!mDevSettings.isReloadOnJSChangeEnabled());
    }

    @ReactMethod
    public void setHotLoadingEnabled(final boolean enabled) {
        if (!useDeveloperSupport) return;
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        Boolean needReload = enabled != mDevSettings.isHotModuleReplacementEnabled();
        mDevSettings.setHotModuleReplacementEnabled(enabled);
        if (needReload) { reload(); }
    }

    @ReactMethod
    public void setIsDebuggingRemotely(final boolean enabled) {
        if (!useDeveloperSupport) return;
        DevInternalSettings mDevSettings = (DevInternalSettings) devManager.getDevSettings();
        Boolean needReload = enabled != mDevSettings.isRemoteJSDebugEnabled();
        mDevSettings.setRemoteJSDebugEnabled(enabled);
        if (needReload) { reload(); }
    }

    @ReactMethod
    public void show() {
        if (!useDeveloperSupport) return;
        Activity activity = getCurrentActivity();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                devManager.showDevOptionsDialog();
            }
        });
    }

    @Override
    public boolean canOverrideExistingModule() {
      return true;
    }
}
