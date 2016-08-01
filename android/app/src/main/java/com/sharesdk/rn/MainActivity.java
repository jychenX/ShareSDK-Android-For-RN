package com.sharesdk.rn;

import android.os.Bundle;

import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
public class MainActivity extends ReactActivity implements DefaultHardwareBackBtnHandler{

    private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("打印：onCreate");

    }
    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        System.out.println("打印：getMainComponentName");
        ShareSDK.initSDK(this);
        return "ShareSDKRN";
    }

    /**
     * Returns whether dev mode should be enabled.
     * This enables e.g. the dev menu.
     */
    @Override
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    /**
     * A list of packages used by the app. If the app uses additional views
     * or modules besides the default ones, add more packages here.
     */
    @Override
    protected List<ReactPackage> getPackages() {
        System.out.println("打印：getPackages");
        return Arrays.<ReactPackage>asList(
            new MainReactPackage(),
            new RNJavePackage()
        );
    }
}
