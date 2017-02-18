package com.three38inc.xactitude;

import java.util.HashMap;
import com.pushbots.push.Pushbots;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;
 
public class pushHandler extends BroadcastReceiver
{
    private static final String TAG = "customPushReceiver";
    @Override
    public void onReceive(Context context, Intent intent)
    {
        String action = intent.getAction();
        Log.d(TAG, "action=" + action);
        Vibrator v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000, 300, 200, 100, 500, 200, 100};
        v.vibrate(pattern, -1);
        
        // Handle Push Message when opened
        if (action.equals(Pushbots.MSG_OPENED)) {
            HashMap<?, ?> PushdataOpen = (HashMap<?, ?>) intent.getExtras().get(Pushbots.MSG_OPEN);
            Log.w(TAG, "User clicked notification with Message: " + PushdataOpen.get("message"));
            //Toast.makeText(context, (CharSequence) PushdataOpen.get("msg"), Toast.LENGTH_LONG).show();
            // Start activity if not active
            // set the value of local variable "active" in onStart()/onStop() in MainActivity
            // to check for MainActivity status
            if(!FeedActivity.isActive()){
                Intent launch = new Intent(Intent.ACTION_MAIN);
                launch.setClass(Pushbots.getInstance().appContext, MainActivity.class);
                launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Pushbots.getInstance().appContext.startActivity(launch);
            }
        // Handle Push Message when received
        }else if(action.equals(Pushbots.MSG_RECEIVE)){
            HashMap<?, ?> Pushdata = (HashMap<?, ?>)  intent.getExtras().get(Pushbots.MSG_RECEIVE);
            Log.w(TAG, "User Received notification with Message: " + Pushdata.get("message"));
        }
    }
}