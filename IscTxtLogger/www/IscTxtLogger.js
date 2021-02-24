var exec = require('cordova/exec');
var javaName = '';
// coolMethod: 插件安装后的js调用的方法名称
// arg0 传递的参数
exports.coolMethod = function (arg0, success, error) {
    // 1:成功的回调
    // 2:失败的回调
    // 3:plugin.xml中配置的feature的name属性名
    // 4:给*.java判断的action值
    exec(success, error, javaName, 'coolMethod', [arg0]);
};
exports.logInfo = function (arg0, success, error) {
    exec(success, error, javaName, 'logInfo', [arg0]);
};
exports.logNetwork = function (arg0, success, error) {
    exec(success, error, javaName, 'logNetwork', [arg0]);
};
