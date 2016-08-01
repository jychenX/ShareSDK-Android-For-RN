package com.fristrnproject;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.JSBundleLoader;
import com.facebook.react.bridge.JSCJavaScriptExecutor;
import com.facebook.react.bridge.JavaScriptExecutor;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class MainActivity extends ReactActivity implements DefaultHardwareBackBtnHandler{

    //广播监听
    String smsContent;
    SmsMessage[] messages;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

    private ReactInstanceManager mReactInstanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("打印：onCreate");

    }

    BroadcastReceiver receiver=new BroadcastReceiver(){
        public void onReceive(Context context, Intent intent) {
            char [] code = new char[4];
            if (intent.getAction().equals(SMS_RECEIVED_ACTION)){
                messages = SMSReceiver.getMessagesFromIntent(intent);
//		           SmsMessage message;
                for (SmsMessage message : messages){
//		           SmsMessage message=messages;
                    smsContent=message.getDisplayMessageBody().toString();
                }
                char [] smsArr=new char[smsContent.length()];
                smsArr=smsContent.toCharArray();
                for(int i=smsArr.length-1,j=3;i>smsArr.length-5;i--,j--){
                    code[j]=smsArr[i];
                }
                String yzm=String.valueOf(code).toString();
                intent.putExtra("code", yzm);
                Toast.makeText(context, "jaa层获取到验证码：" + yzm, Toast.LENGTH_LONG).show();
            }
        }
    };

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     */
    @Override
    protected String getMainComponentName() {
        System.out.println("打印：getMainComponentName");
        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "d6adcf0d7936", "c115c79d33d029b0565198b6dc518a54",false);//MyKEY2.0
        return "FristRNProject";
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
