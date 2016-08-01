'use strict';
import React, { Component } from 'react';
import {
    Platform
} from 'react-native';

var ContentType = {
    Auto : 0,
    Text : 1,
    Image : 2,
    WebPage : 4,
    Music : 5,
    Video : 6,
    App : 7,
    File : 8,
    Emoji : 9
}

module.exports = ContentType;