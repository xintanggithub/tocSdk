package common;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class TestBActivity extends Activity {
    private static final String TAG = "TestBActivity";
    private Activity otherActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        boolean b = false;
        if (savedInstanceState != null) {
            b = savedInstanceState.getBoolean("KEY_START_FROM_OTHER_ACTIVITY", false);
            if (b) {
                this.otherActivity.setContentView(new TBSurfaceView(this.otherActivity));
            }
        }
        if (!b) {
            super.onCreate(savedInstanceState);
            // setContentView(R.layout.main);
            setContentView(new TBSurfaceView(this));
            Intent intent = new Intent(this, BService.class);
            intent.putExtra("b", "b");
            startService(intent);
        }
    }

    public void setActivity(Activity paramActivity) {
        Log.d(TAG, "setActivity..." + paramActivity);
        this.otherActivity = paramActivity;
    }
}