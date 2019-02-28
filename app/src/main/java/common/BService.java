package common;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BService extends Service {
	private static final String TAG = "BService";

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onBind--" + arg0.getFlags());
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.i(TAG, "onCreate-b");

	}

	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		Log.i(TAG, "Service--" + startId);
	}
}
