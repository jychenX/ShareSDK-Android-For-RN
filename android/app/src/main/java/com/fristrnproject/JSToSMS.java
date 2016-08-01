package com.fristrnproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jychen on 2016/6/7.
 */
public class JSToSMS extends ReactContextBaseJavaModule {

    private Context context;
    private String cb = "";

    public JSToSMS(ReactApplicationContext reactApplicationContext) {
        super(reactApplicationContext);
        context = reactApplicationContext;
        EventHandler eh=new EventHandler(){
            public void afterEvent(int event, int result, Object data) {
//				boolean smart = (Boolean)data;
//				if(smart){
//					System.out.println("用户已经验证过了");
//				}else{
//					System.out.println("用户还没有验证过");
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(Looper.getMainLooper(), (Handler.Callback) context){
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event=" + event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    Toast.makeText(context, "提交验证码成功(验证成功)+"+event, Toast.LENGTH_SHORT).show();
                    SMSSDK.unregisterAllEventHandler();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    cb = "ＪＳ验证成功";
                    Toast.makeText(context, "获取验证码成功(发送成功)+"+event, Toast.LENGTH_SHORT).show();

                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    Toast.makeText(context, "获取国家列表成功+"+event+data+result, Toast.LENGTH_SHORT).show();
                    System.out.println("全部"+event+"------\n"+data+"------\n"+result+"------\n"+SMSSDK.getGroupedCountryList());

                    GetCountryList list=new GetCountryList();
                    System.out.println(list.getCountry(SMSSDK.getGroupedCountryList()));
                    try {
                        JSONObject josn=new JSONObject(data.toString());
                        System.out.println("Json:"+josn);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
//				else if(event ==SMSSDK.EVENT_GET_VOICE_VERIFICATION_CODE){
//					Toast.makeText(getApplicationContext(), "语音验证码获取成功"+event, Toast.LENGTH_SHORT).show();
//				}
                else if(event ==SMSSDK.EVENT_GET_FRIENDS_IN_APP){
                    System.out.println("列表数据"+event+"---"+result+"----"+data);
                    Toast.makeText(context, "获取好友成功"+event, Toast.LENGTH_SHORT).show();
                }
                else if(event ==SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT){
                    System.out.println("新的朋友数据"+event+"---"+result+"----"+data);
                    Toast.makeText(context, "获取新的朋友"+event, Toast.LENGTH_SHORT).show();
                }

            } else {
                //异常信息
                ((Throwable) data).printStackTrace();
            }
        }

    };

    @Override
    public String getName() {
        return "RNSMS";
    }

    @ReactMethod
    public void sendmsg(String  code, String phone) {
        SMSSDK.getVerificationCode("86",phone);
    }

    public void callBackFuntion(Promise pro) {
        if (!cb.equals("")){
            pro.reject("0", "非空字符串"+cb.toString());
        }else{
            pro.reject("1","ＪＳ无法判断");
        }

    }
}
