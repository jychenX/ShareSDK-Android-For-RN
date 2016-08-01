'use strict';
import React, { Component } from 'react';
import {
    Platform
} from 'react-native';

var PlatformID = {
    Unknown         : 0,    // 未知
    SinaWeibo       : 1,    // 新浪微博
    TencentWeibo    : 2,    // 腾讯微博
    DouBan          : 5,    // 豆瓣
    QZone           : 6,    // QQ空间
    Renren          : 7,    // 人人网
    Kaixin          : 8,    // 开心网
    Facebook        : 10,   // Facebook
    Twitter         : 11,   // Twitter
    YinXiang        : 12,   // 印象笔记
    GooglePlus      : 14,   // Google+
    Instagram       : 15,   // Instagram
    LinkedIn        : 16,   // LinkedIn
    Tumblr          : 17,   // Tumblr
    Mail            : 18,   // 邮件
    SMS             : 19,   // 短信
    Print           : 20,   // 打印
    Copy            : 21,   // 拷贝
    WechatSession   : 22,   // 微信好友
    WechatTimeline  : 23,   // 微信朋友圈
    QQFriend        : 24,   // QQ好友
    Instapaper      : 25,   // Instapaper
    Pocket          : 26,   // Pocket
    YouDaoNote      : 27,   // 有道云笔记
    Pinterest       : 30,   // Pinterest
    Flickr          : 34,   // Flickr
    Dropbox         : 35,   // Dropbox
    VKontakte       : 36,   // VKontakte
    WechatFav       : 37,   // 微信收藏
    YiXinSession    : 38,   // 易信好友
    YiXinTimeline   : 39,   // 易信好友圈
    YiXinFav        : 40,   // 易信收藏
    MingDao         : 41,   // 明道
    Line            : 42,   // 连我
    WhatsApp        : 43,   // WhatsApp
    KakaoTalk       : 44,   // KakaoTalk
    KakaoStory      : 45,   // KakaoStory
    FacebookMessenger: 46,   // FacebookMessenger
    AliPaySocial    : 50,   // 支付宝好友
    YiXin           : 994,  // 易信系列
    Kakao           : 995,  // KaKao
    Evernote        : 996,  // 印象笔记
    Wechat          : 997,  // 微信
    QQ              : 998,  // QQ
    Any             : 999   // 任意平台
}

module.exports = PlatformID;