#!/bin/bash
# clear;
# 定义方法:
# 文件名称 修改行数的数组
# cahnge_2 filename "${arr[*]}"
# change_2 './a.log' '22 37' 
function change_2(){
    file=$1
    lines=$2
    index=0
    for line in ${lines[@]}; 
    do
        echo '遍历的元素' $index $line;
        startIndex=$[index * 3 + line]
        index=`expr $index + 1`
        echo '修改gradle文件脚本:即将修改' $file $startIndex $[startIndex + 1] $[startIndex + 2]
        gsed -i  "${startIndex}a  maven { url 'http://maven.aliyun.com/nexus/content/repositories/google' } // add@duweiquan" $file
        gsed -i  "$[startIndex + 1]a  maven {  url 'http://maven.aliyun.com/nexus/content/groups/public/' } // add@duweiquan" $file
        gsed -i  "$[startIndex + 2]a  maven  { url 'http://maven.aliyun.com/nexus/content/repositories/jcenter' } // add@duweiquan" $file
} 
# 执行修改
change_2 './platforms/android/build.gradle' '22 37'
change_2 './platforms/android/app/build.gradle' '23 38'
change_2 './platforms/android/CordovaLib/build.gradle' '22'
