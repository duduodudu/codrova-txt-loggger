#!/bin/bash
clear ;
if [ ! -e www ];
then
    echo 'www文件夹不存在，创建www文件夹;是为了解决Current working directory is not a Cordova-based project的报错';
    mkdir www;
fi;
# 删除原来的插件
echo '删除原来的插件'
ionic cordova plugin rm cordova-plugin-IscTxtLogger

## 添加插件
echo '添加插件'

ionic cordova plugin add ../IscTxtLogger

