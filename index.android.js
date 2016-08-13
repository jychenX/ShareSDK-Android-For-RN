/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, { Component } from 'react';
import{
    AppRegistry,
    StyleSheet,
    Text,
    Image,
    View,
    ToastAndroid,
    TouchableHighlight,
    DeviceEventEmitter,
    NativeModules,
    TextInput
} from 'react-native';

var ContentType = require('./ShareSDK/ContentType')
var PlatformID = require('./ShareSDK/PlatformID')

var javaSendSMS = NativeModules.RNSMS;
var javaShare = NativeModules.RNSHARESDK;

// storeModule.addUser("jjz","",(msg)=>{alert(msg);},(errorMsg)=>{alert(errorMsg)});
// storeModule.login('aa','123').then((map)=>{alert(map['user_id']);},(code,message)=>{alert(code);})


class FristRNProject extends React.Component {
    render() {
        return (
            <View style={{backgroundColor:'#000000',flex:1}}>

                <TouchableHighlight onPress={() =>javaShare.authorize(PlatformID.QQFriend)}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            授权
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>javaShare.showUser(PlatformID.QQFriend)}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            登陆
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>javaShare.removeAccount(PlatformID.QQFriend)}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            移除授权
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>this.isAuthValid()}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            是否已经授权
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>this.isClientValid()}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            是否安装客户端
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>this.shareContent()}>
                    <View style={styles.style_view_button} >
                        <Text style={{color:'#fff'}}>
                            单独平台分享
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>this.onekeyShare()}>
                    <View style={styles.style_view_button}>
                        <Text style={{color:'#fff'}}>
                            一键分享
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>javaShare.getFriendList(PlatformID.SinaWeibo, 3, 2)}>
                    <View style={styles.style_view_button}>
                        <Text style={{color:'#fff'}}>
                            获取好友列表
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>javaShare.followfriend(PlatformID.SinaWeibo, "央视新闻")}>
                    <View style={styles.style_view_button}>
                        <Text style={{color:'#fff'}}>
                            关注好友
                        </Text>
                    </View>
                </TouchableHighlight>

                <TouchableHighlight onPress={() =>this.getAuthInfo()}>
                    <View style={styles.style_view_button}>
                        <Text style={{color:'#fff'}}>
                            获取用户登录信息
                        </Text>
                    </View>
                </TouchableHighlight>
            </View>
        );
    }
    componentDidMount(){
        // Log.d("tag","tag");//调用java层log方法，之后就会回调对应的事件（前提是注册了事件）
        //直接使用DeviceEventEmitter进行事件注册
        var listent = DeviceEventEmitter.addListener('OnComplete',(e)=>{
            console.log(e);
            listent.remove();
        });

        var listent = DeviceEventEmitter.addListener('OnError',(e)=>{
            console.log(e);
            listent.remove()
        });

        var listent = DeviceEventEmitter.addListener('OnCancel',(e)=>{
            console.log(e);
            //listent.remove()
        })
    }

    isAuthValid(){
		javaShare.isAuthValid(PlatformID.QQFriend, 
							(msg)=>{
								console.log("是否已经授权"+msg);
							});
    }
    isClientValid(){
        javaShare.isClientValid(PlatformID.QQFriend,
								(msg)=>{
									console.log("是否安装客户端"+msg);
								});
    }

    shareContent(){
        var shareContent = {
            text: '分享内容',
            imageUrl: 'http://mbiadmin.lianzhong.com/Images/jjerdy/captureScreen.png',
            url: 'http://www.mob.com',
            title: '分享标题',
            titleurl: 'http;//www.baidu.com',
			"type": ContentType.WebPage
        };
        var params = JSON.stringify(shareContent);
        javaShare.shareContent(PlatformID.QQFriend, params);
    }

    onekeyShare(){
        var shareContent = {
            text: '分享内容',
            imageUrl: 'http://mbiadmin.lianzhong.com/Images/jjerdy/captureScreen.png',
            url: 'http://www.mob.com',
            title: '分享标题',
            titleurl: 'http;//www.baidu.com'
        };
        var params = JSON.stringify(shareContent);
        javaShare.onekeyShare(0, params);
    }

    getAuthInfo(){
        javaShare.getAuthInfo(PlatformID.QQFriend)
							.then((map)=>{
								console.log("用户信息：" + 
									"\n expiresIn:"+map['expiresIn']+
									"\n expiresTime:"+map['expiresTime']+
									"\n token:"+map['token']+
									"\n tokenSecret:"+map['tokenSecret']+
									"\n userGender:"+map['userGender']+
									"\n userID:"+map['userID']+
									"\n openID:"+map['openID']+
									"\n userName:"+map['userName']+
									"\n userIcon:"+map['userIcon']);
							},(code,message)=>{
								console.log("错误信息：" + code + message);
							});
    }

    test(){
        console.log("测试值：" + PlatformID.QQFriend);
    }

}

const styles = StyleSheet.create({
    style_user_input:{
        backgroundColor:'#fff',
        marginTop:10,
        height:45,
    },
    style_pwd_input:{
        backgroundColor:'#fff',
        height:45,
    },
    style_view_button:{
        marginTop:10,
        marginLeft:10,
        marginRight:10,
        backgroundColor:'#63B8FF',
        borderColor:'#5bc0de',
        height:45,
        borderRadius:5,
        justifyContent: 'center',
        alignItems: 'center',
    },
    style_textsize:{
        fontSize:20,
    },
});

AppRegistry.registerComponent('ShareSDKRN', () => FristRNProject);
