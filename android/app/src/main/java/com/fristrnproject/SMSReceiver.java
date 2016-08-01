package com.fristrnproject;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;


public class SMSReceiver extends BroadcastReceiver{

	String smsContent;
    public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	
	public void onReceive(Context context, Intent intent) {
		
	}

	@SuppressWarnings("deprecation")
	public static SmsMessage[] getMessagesFromIntent(Intent intent) {
		Bundle bundle= intent.getExtras();  
		    Object messages[]=(Object[])bundle.get("pdus");  
		    SmsMessage mSmsMessage[]= new SmsMessage[messages.length];  
		    for(int i=0;i<messages.length;i++){  
		     mSmsMessage[i]=SmsMessage.createFromPdu((byte[])messages[i]);  
		    }  
		     return mSmsMessage;
	}


}

