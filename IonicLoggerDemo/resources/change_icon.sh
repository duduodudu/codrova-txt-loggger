#!/bin/sh
echo '开始执行命令~~~';
ionic cordova resources --icon android;
if [ $? -eq 0 ];
then
    echo 'Android图片制作成功';
else
    echo 'Android图片制作失败';
    exit;
fi;
ionic cordova resources --icon ios ;
if [ $? -eq 0 ];
then
    echo 'IOS图片制作成功'
    cp ./ios/icon/icon-29.png      ./ios/icon/icon-small.png
    cp ./ios/icon/icon-29@2x.png   ./ios/icon/icon-small@2x.png
    cp ./ios/icon/icon-29@3x.png   ./ios/icon/icon-small@3x.png
else
    echo 'IOS图片制作失败'
fi;
