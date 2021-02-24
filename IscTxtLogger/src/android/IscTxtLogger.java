package cordova.plugin.intasect;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.widget.Toast;
import cn.com.intasect.logapp.IscTxtLoggerHelper;

/**
 * This class echoes a string called from JavaScript.
 */
public class IscTxtLogger extends CordovaPlugin {

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        // your init code here
        IscTxtLoggerHelper.getInstance().init(getApplicationContext());
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.toast(action);
        if (action.equals("init")) {
            return true;
        }
        if (action.equals("coolMethod")) {
            String message = args.getString(0);
            this.coolMethod(message, callbackContext);
            return true;
        }
        return false;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        if (message != null && message.length() > 0) {
            Toast.makeText(cordova.getContext(),message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    private void showToast(String message, CallbackContext callbackContext){
        if(message != null && message.length() > 0){
            Toast toast = Toast.makeText(cordova.getContext(),message, Toast.LENGTH_SHORT).show();
            callbackContext.success(message);
        } else {
            callbackContext.error("调用失败");
        }
    }
    /**
     * toast
     */
    private void toast(String message){
        Toast toast = Toast.makeText(cordova.getContext(),message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 记录信息
     * 记录在yyyyMMdd_info.txt文件中
     */
    public void logInfo(String message){
        IscTxtLoggerHelper.getInstance().logInfo(message);
    }
    /**
     * 记录网络信息
     *  记录在yyyyMMdd_net.txt文件中
     */
    public void logNetwork(String message){
        IscTxtLoggerHelper.getInstance().logNetwork(message);
    }
}
