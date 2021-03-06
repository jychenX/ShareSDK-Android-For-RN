package cn.sharesdk.demo;

import android.os.Handler;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.WritableMap;
import com.mob.tools.utils.Hashon;
import android.os.Bundle;

import com.mob.tools.utils.UIHandler;
import android.os.Message;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map.Entry;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.Platform.ShareParams;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

/**
 * Created by jychen on 2016/6/14.
 */
public class ShareSDKUtils extends ReactContextBaseJavaModule implements Handler.Callback {

    private ReactApplicationContext context;
    private static boolean DEBUG = true;
    private static boolean disableSSO = false;


    private static final int MSG_INITSDK = 1;
    private static final int MSG_AUTHORIZE = 2;
    private static final int MSG_SHOW_USER = 3;
    private static final int MSG_SHARE = 4;
    private static final int MSG_ONEKEY_SAHRE = 5;
    private static final int MSG_GET_FRIENDLIST = 6;
    private static final int MSG_FOLLOW_FRIEND = 7;


    public ShareSDKUtils(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        context = reactApplicationContext;

    }

    @Override
    public String getName() {
        return "RNSHARESDK";
    }


    @ReactMethod
    public void initSDK(String appKey) {
        if (DEBUG) {
            System.out.println("initSDK appkey ==>>" + appKey);
        }
        if (!TextUtils.isEmpty(appKey)) {
            ShareSDK.initSDK(context, appKey);
        } else {
            ShareSDK.initSDK(context);
        }
    }

    @ReactMethod
    public void setPlatformConfig(String configs) {
        if (DEBUG) {
            System.out.println("initSDK configs ==>>" + configs);
        }

        if (!TextUtils.isEmpty(configs)) {
            Message msg = new Message();
            msg.what = MSG_INITSDK;
            msg.obj = configs;
            UIHandler.sendMessageDelayed(msg, 1000, this);
        }
    }

    @ReactMethod
    public void authorize(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.authorize");
        }
        Message msg = new Message();
        msg.what = MSG_AUTHORIZE;
        msg.arg1 = platform;
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    public void removeAccount(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.removeAccount");
        }
        String name = ShareSDK.platformIdToName(platform);
        Platform plat = ShareSDK.getPlatform(name);
        plat.removeAccount(true);
    }

    @ReactMethod
    public void isAuthValid(int platform, Callback isAuth) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.isAuthValid");
        }
        String name = ShareSDK.platformIdToName(platform);
        Platform plat = ShareSDK.getPlatform(name);
        Toast.makeText(context, String.valueOf(plat.isAuthValid()), Toast.LENGTH_LONG).show();
        if(plat.isAuthValid()){
            isAuth.invoke(true);
        }else{
            isAuth.invoke(false);
        }
    }

    @ReactMethod
    @JavascriptInterface
    public void isClientValid(int platform, Callback isClient) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.isClientValid");
        }
        String name = ShareSDK.platformIdToName(platform);
        Platform plat = ShareSDK.getPlatform(name);
        Toast.makeText(context, String.valueOf(plat.isClientValid()), Toast.LENGTH_LONG).show();
        if(plat.isClientValid()){
            isClient.invoke(true);
        }else{
            isClient.invoke(false);
        }
    }


    @ReactMethod
    public void showUser(int platform) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.showUser");
        }
        Message msg = new Message();
        msg.what = MSG_SHOW_USER;
        msg.arg1 = platform;
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    public void shareContent(int platform, String content) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.share");
        }
        Message msg = new Message();
        msg.what = MSG_SHARE;
        msg.arg1 = platform;
        msg.obj = content;
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    public void onekeyShare(int platform, String content) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.OnekeyShare");
        }
        Message msg = new Message();
        msg.what = MSG_ONEKEY_SAHRE;
        msg.arg1 = platform;
        msg.obj = content;
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    public void getFriendList(int platform, int count, int page) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.getFriendList");
        }
        Message msg = new Message();
        msg.what = MSG_GET_FRIENDLIST;
        msg.arg1 = platform;
        Bundle data = new Bundle();
        data.putInt("page", page);
        data.putInt("count", count);
        msg.setData(data);
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    public void followFriend(int platform, String account) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.followFriend");
        }

        Message msg = new Message();
        msg.what = MSG_FOLLOW_FRIEND;
        msg.arg1 = platform;
        msg.obj = account;
        UIHandler.sendMessage(msg, this);
    }

    @ReactMethod
    @JavascriptInterface
    public void getAuthInfo(int platform, Promise authInfo) {
        if (DEBUG) {
            System.out.println("ShareSDKUtils.getAuthInfo");
        }

        String name = ShareSDK.platformIdToName(platform);
        Platform plat = ShareSDK.getPlatform(name);
        Hashon hashon = new Hashon();
        WritableMap map = Arguments.createMap();
        if(plat.isAuthValid()){
            map.putDouble("expiresIn", plat.getDb().getExpiresIn());
            map.putDouble("expiresTime", plat.getDb().getExpiresTime());
            map.putString("token", plat.getDb().getToken());
            map.putString("tokenSecret", plat.getDb().getTokenSecret());
            map.putString("userGender", plat.getDb().getUserGender());
            map.putString("userID", plat.getDb().getUserId());
            map.putString("openID", plat.getDb().get("openid"));
            map.putString("userName", plat.getDb().getUserName());
            map.putString("userIcon", plat.getDb().getUserIcon());
        }
        System.out.println(map.toString());
        authInfo.resolve(map);
    }

    @ReactMethod
    public void disableSSOWhenAuthorize(boolean open){
        disableSSO = open;
    }

    @SuppressWarnings("unchecked")
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_INITSDK: {
                if (DEBUG) {
                    System.out.println("ShareSDKUtils.setPlatformConfig");
                }
                String configs = (String) msg.obj;
                Hashon hashon = new Hashon();
                HashMap<String, Object> devInfo = hashon.fromJson(configs);
                for(Entry<String, Object> entry: devInfo.entrySet()){
                    String p = ShareSDK.platformIdToName(Integer.parseInt(entry.getKey()));
                    if (p != null) {
                        if (DEBUG) {
                            System.out.println(p + " ==>>" + new Hashon().fromHashMap((HashMap<String, Object>)entry.getValue()));
                        }
                        ShareSDK.setPlatformDevInfo(p, (HashMap<String, Object>)entry.getValue());
                    }
                }
            }
            break;
            case MSG_AUTHORIZE: {
                int platform = msg.arg1;
                String name = ShareSDK.platformIdToName(platform);
                Platform plat = ShareSDK.getPlatform(name);
                plat.setPlatformActionListener(new RNPlatformListener(context));
                plat.SSOSetting(disableSSO);
                plat.authorize();
            }
            break;
            case MSG_SHOW_USER: {
                int platform = msg.arg1;
                System.out.println("平台名字"+platform);
                String name = ShareSDK.platformIdToName(platform);
                System.out.println("平台名字"+name);
                Platform plat = ShareSDK.getPlatform(name);
                plat.setPlatformActionListener(new RNPlatformListener(context));
                plat.SSOSetting(disableSSO);
                plat.showUser(null);
            }
            break;
            case MSG_SHARE: {
                int platformID = msg.arg1;
                String content = (String) msg.obj;
                String pName = ShareSDK.platformIdToName(platformID);
                Platform plat = ShareSDK.getPlatform(context, pName);
                plat.setPlatformActionListener(new RNPlatformListener(context));
                plat.SSOSetting(disableSSO);
                try {
                    Hashon hashon = new Hashon();
                    if (DEBUG) {
                        System.out.println("share content ==>>" + content);
                    }
                    HashMap<String, Object> data = hashon.fromJson(content);
                    ShareParams sp = new ShareParams(data);
                    //不同平台，分享不同内容
                    if (data.containsKey("customizeShareParams")) {
                        final HashMap<String, String> customizeSP = (HashMap<String, String>) data.get("customizeShareParams");
                        if (customizeSP.size() > 0) {
                            String pID = String.valueOf(platformID);
                            if (customizeSP.containsKey(pID)) {
                                String cSP = customizeSP.get(pID);
                                if (DEBUG) {
                                    System.out.println("share content ==>>" + cSP);
                                }
                                data = hashon.fromJson(cSP);
                                for (String key : data.keySet()) {
                                    sp.set(key, data.get(key));
                                }
                            }
                        }
                    }
                    plat.share(sp);
                } catch (Throwable t) {
                    new RNPlatformListener(context).onError(plat, Platform.ACTION_SHARE, t);
                }
            }
            break;
            case MSG_ONEKEY_SAHRE: {
                int platform = msg.arg1;
                String content = (String) msg.obj;
                Hashon hashon = new Hashon();
                if (DEBUG) {
                    System.out.println("onekeyshare  ==>>" + content);
                }
                HashMap<String, Object> map = hashon.fromJson(content);
                OnekeyShare oks = new OnekeyShare();
                if (platform > 0) {
                    String name = ShareSDK.platformIdToName(platform);
                    if (DEBUG) {
                        System.out.println("ShareSDKUtils Onekeyshare shareView platform name ==>> " + name);
                    }
                    if(!TextUtils.isEmpty(name)){
                        oks.setPlatform(name);
                        oks.setSilent(false);
                    }
                }
                if (map.containsKey("text")) {
                    oks.setText((String)map.get("text"));
                }
                if (map.containsKey("imagePath")) {
                    oks.setImagePath((String)map.get("imagePath"));
                }
                if (map.containsKey("imageUrl")) {
                    oks.setImageUrl((String)map.get("imageUrl"));
                }
                if (map.containsKey("title")) {
                    oks.setTitle((String)map.get("title"));
                }
                if (map.containsKey("comment")) {
                    oks.setComment((String)map.get("comment"));
                }
                if (map.containsKey("url")) {
                    oks.setUrl((String)map.get("url"));
                    oks.setTitleUrl((String)map.get("url"));
                }
                if (map.containsKey("site")) {
                    oks.setSite((String)map.get("site"));
                }
                if (map.containsKey("siteUrl")) {
                    oks.setSiteUrl((String)map.get("siteUrl"));
                }
                if (map.containsKey("musicUrl")) {
                    oks.setSiteUrl((String)map.get("musicUrl"));
                }
                if (map.containsKey("shareType")) {
                    if ("6".equals(String.valueOf(map.get("shareType")))) {
                        if (map.containsKey("url")) {
                            oks.setVideoUrl((String)map.get("url"));
                        }
                    }
                }
                //不同平台，分享不同内容
                if (map.containsKey("customizeShareParams")) {
                    final HashMap<String, String> customizeSP = (HashMap<String, String>) map.get("customizeShareParams");
                    if (customizeSP.size() > 0) {
                        oks.setShareContentCustomizeCallback(new ShareContentCustomizeCallback() {
                            public void onShare(Platform platform, ShareParams paramsToShare) {
                                String platformID = String.valueOf(ShareSDK.platformNameToId(platform.getName()));
                                if (customizeSP.containsKey(platformID)) {
                                    Hashon hashon = new Hashon();
                                    String content = customizeSP.get(platformID);
                                    if (DEBUG) {
                                        System.out.println("share content ==>>" + content);
                                    }
                                    HashMap<String, Object> data = hashon.fromJson(content);
                                    for (String key : data.keySet()) {
                                        paramsToShare.set(key, data.get(key));
                                    }
                                }
                            }
                        });
                    }
                }

                if(disableSSO){
                    oks.disableSSOWhenAuthorize();
                }
                oks.setCallback(new RNPlatformListener(context));
                oks.show(context);
            }
            break;
            case MSG_GET_FRIENDLIST:{
                int platform = msg.arg1;
                int page = msg.getData().getInt("page");
                int count = msg.getData().getInt("count");
                String name = ShareSDK.platformIdToName(platform);
                Platform plat = ShareSDK.getPlatform(name);
                plat.setPlatformActionListener(new RNPlatformListener(context));
                plat.SSOSetting(disableSSO);
                plat.listFriend(count, page, null);
            }
            break;
            case MSG_FOLLOW_FRIEND:{
                int platform = msg.arg1;
                String account = (String) msg.obj;
                String name = ShareSDK.platformIdToName(platform);
                Platform plat = ShareSDK.getPlatform(name);
                plat.setPlatformActionListener(new RNPlatformListener(context));
                plat.SSOSetting(disableSSO);
                plat.followFriend(account);
            }
            break;
        }
        return false;
    }

}
