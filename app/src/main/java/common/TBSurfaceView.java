package common;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class TBSurfaceView extends SurfaceView implements Callback, Runnable {
	private SurfaceHolder sfh;
	private Thread th;
	private Canvas canvas;
	private Paint paint;

	public TBSurfaceView(Context context) {
		super(context);
		th = new Thread(this);
		sfh = this.getHolder();
		sfh.addCallback(this);
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		this.setKeepScreenOn(true);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		th.start();
	}

	private void draw() {
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawColor(Color.WHITE);
				canvas.drawText("Time: " + System.currentTimeMillis(), 100,
						100, paint);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	public void run() {
		while (true) {
			draw();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
	}
}
