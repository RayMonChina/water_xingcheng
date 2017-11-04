package com.ideal.zsyy.view;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.GestureDetector.OnGestureListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ScrollMenuLayout extends LinearLayout implements OnGestureListener{



	private Context context;
	private LinearLayout layout_right;

	private static final float baseDenity = 2.0f;
	private float dentiyRatio;
	private boolean notInterceptChild = true;

	// private Gallery gallery;
	// private View diagram;
	//
	//
	//
	// public View getDiagram() {
	// diagram = this.findViewById(R.id.LinearLayout2);
	// return diagram;
	// }
	//
	// public Gallery getGallery() {
	// gallery = (Gallery) this.findViewById(R.id.news);
	// return gallery;
	// }

	public void setLayout_right(LinearLayout layout_right) {
		this.layout_right = layout_right;
		getMAX_WIDTH();
	}

	public boolean isNotInterceptChild() {
		return notInterceptChild;
	}

	public void setNotInterceptChild(boolean notInterceptChild) {
		this.notInterceptChild = notInterceptChild;
	}

	public ScrollMenuLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
		dentiyRatio = displayMetrics.density / baseDenity;
		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
		mGestureDetector = new GestureDetector(this);
		// ���ó�������
		mGestureDetector.setIsLongpressEnabled(false);
	}

	float down_x;
	float down_y;

	// δ����
	private static final int STATE_REST = 1;
	private int TOUCH_STATE = STATE_REST;
	// ˮƽ
	private static final int STATE_H = 2;
	// ��ֱ
	private static final int STATE_V = 3;

	private int mTouchSlop;

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		boolean intercept = false;
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		if (layoutParams.leftMargin > 0) {
			Log.e("", "return---true---leftmargin>0");
			return true;
		}
		// if(null != getDiagram()){
		// if(ev.getY() >=300*dentiyRatio){
		// return false;
		// }
		// }
		// if(null != getGallery()){
		// if(ev.getY() >= 150*dentiyRatio && ev.getY() <= 300*dentiyRatio ){
		// return false;
		// }
		// }
		final int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			down_x = ev.getX();
			down_y = ev.getY();
			TOUCH_STATE = STATE_REST;
			ACTION_DOWN_X = layoutParams.leftMargin;
			break;
		case MotionEvent.ACTION_MOVE:
			if(!notInterceptChild){
				return notInterceptChild;
			}
			if ((TOUCH_STATE == STATE_H)) {
				intercept = true;
			} else if (TOUCH_STATE == STATE_V) {
				intercept = false;
			} else {
				float xDiff = Math.abs(down_x - ev.getX());
				float yDiff = Math.abs(down_y - ev.getY());
				if (xDiff >= yDiff && xDiff >= mTouchSlop) {
					TOUCH_STATE = STATE_H;
					intercept = true;
				} else if (yDiff > xDiff && yDiff >= mTouchSlop) {
					TOUCH_STATE = STATE_V;
					intercept = false;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			intercept = false;
			TOUCH_STATE = STATE_REST;
			break;
		default:
			break;
		}
		isIntercept = intercept;
		return intercept;

	}

	private boolean isIntercept;

	/** ÿ���Զ�չ��/�����ķ�Χ */
	public int MAX_WIDTH = 0;
	/** ÿ���Զ�չ��/�������ٶ� */
	private final static int SPEED = 30;

	private GestureDetector mGestureDetector;// ����
	private boolean isScrolling = false;
	private float mScrollX; // ���黬������
	private int window_width;// ��Ļ�Ŀ��
	private boolean isRun = false;
	private int moveLeftXPoint;
	private boolean hasMeasured = false;// �Ƿ�Measured.

	float ACTION_UP_X;
	float ACTION_DOWN_X;
	float move_x;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (isRun) {
			return false;
		}
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (moveLeftXPoint < MAX_WIDTH && moveLeftXPoint > 0) {
				return false;
			}
			ACTION_DOWN_X = layoutParams.leftMargin;
			break;
		case MotionEvent.ACTION_MASK:
			break;
		case MotionEvent.ACTION_MOVE:
			move_x = event.getX();
			
			break;
		case MotionEvent.ACTION_UP:
			ACTION_UP_X = layoutParams.leftMargin;
			if (isScrolling) {
				if (ACTION_UP_X - ACTION_DOWN_X > 100) {
					new AsynMove().execute(SPEED);
				} else if (ACTION_UP_X - ACTION_DOWN_X < -100) {
					new AsynMove().execute(-SPEED);
				} else {
					if (ACTION_DOWN_X == 0) {
						new AsynMove().execute(-SPEED);
					} else {
						new AsynMove().execute(SPEED);
					}
				}
			}
			break;
		default:
			break;
		}

		return mGestureDetector.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		mScrollX = 0;
		isScrolling = false;
		return true;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (isRun) {
			return false;
		}
		if (isIntercept) {
			mScrollX = 0;
			distanceX = e2.getX() - move_x;
		}
		isIntercept = false;
		isScrolling = true;

		mScrollX += distanceX;// distanceX:����Ϊ����Ϊ��
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		layoutParams.leftMargin -= mScrollX;
		layoutParams.rightMargin += mScrollX;

		if (layoutParams.leftMargin >= MAX_WIDTH) {
			isScrolling = false;// �Ϲ�ͷ�˲���Ҫ��ִ��AsynMove��
			layoutParams.leftMargin = MAX_WIDTH;
			layoutParams.rightMargin = -MAX_WIDTH;

		} else if (layoutParams.leftMargin <= 0) {
		
			isScrolling = false;
			layoutParams.leftMargin = 0;
			layoutParams.rightMargin = 0;
		}
		moveLeftXPoint = layoutParams.leftMargin;
		this.setLayoutParams(layoutParams);
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		// ���ƶ�
		if (layoutParams.leftMargin != 0) {
			new AsynMove().execute(-SPEED);
		}
		return true;
	}

	class AsynMove extends AsyncTask<Integer, Integer, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			mHandler.sendEmptyMessage(3);
			int times = 0;
			if (MAX_WIDTH % Math.abs(params[0]) == 0) {
				// ���
				times = MAX_WIDTH / Math.abs(params[0]);
			} else {
				times = MAX_WIDTH / Math.abs(params[0]) + 1;// ������
			}
			for (int i = 0; i < times; i++) {
				publishProgress(params[0]);
				try {
					Thread.sleep(Math.abs(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ScrollMenuLayout.this
					.getLayoutParams();

			if (layoutParams.leftMargin + times * params[0] <= MAX_WIDTH / 2) {
				mHandler.sendEmptyMessage(0);

			}
			if (layoutParams.leftMargin > MAX_WIDTH / 2) {
				mHandler.sendEmptyMessage(1);
			}
			return null;
		}

		/**
		 * update UI
		 */
		@Override
		protected void onProgressUpdate(Integer... values) {
			isRun = true;
			RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ScrollMenuLayout.this
					.getLayoutParams();
			if (values[0] > 0) {
				// ���ƶ�
				layoutParams.leftMargin = Math.min(layoutParams.leftMargin
						+ values[0], MAX_WIDTH);
				layoutParams.rightMargin = Math.max(layoutParams.rightMargin
						- values[0], -MAX_WIDTH);
			} else {
				// ���ƶ�
				layoutParams.leftMargin = Math.max(layoutParams.leftMargin
						+ values[0], 0);
				layoutParams.rightMargin = Math.min(layoutParams.rightMargin
						- values[0], 0);
			}
			moveLeftXPoint = layoutParams.leftMargin;
			ScrollMenuLayout.this.setLayoutParams(layoutParams);
			invalidate();
			// Log.d("layoutParams", "" + layoutParams.rightMargin);
			// Log.i("isRun", isRun+"");
			if (layoutParams.rightMargin <= 0
					&& layoutParams.rightMargin >= -MAX_WIDTH) {

				isRun = false;

			}

		}

	}

	void getMAX_WIDTH() {
		ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
		// ��ȡ�ؼ����
		viewTreeObserver.addOnPreDrawListener(new OnPreDrawListener() {
			@Override
			public boolean onPreDraw() {
				if (!hasMeasured) {
					window_width = ((Activity) context).getWindowManager()
							.getDefaultDisplay().getWidth();
					RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) ScrollMenuLayout.this
							.getLayoutParams();
					layoutParams.width = window_width;
					ScrollMenuLayout.this.setLayoutParams(layoutParams);
					MAX_WIDTH = layout_right.getWidth();
					hasMeasured = true;
				}
				return true;
			}
		});
	}

	public void show() {
		if (isRun) {
			return;
		}
		new AsynMove().execute(-SPEED);
	}

	public void hidde() {
		if (isRun) {
			return;
		}
		new AsynMove().execute(SPEED);
	}

	public void onLock() {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		if (layoutParams.leftMargin > 0 && layoutParams.leftMargin < MAX_WIDTH) {
			if (ACTION_DOWN_X != 0) {
				new AsynMove().execute(SPEED);
			} else {
				new AsynMove().execute(-SPEED);
			}
		}
	}

	public float getLeftMargin() {
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this
				.getLayoutParams();
		return layoutParams.leftMargin;
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				if (iBack != null) {

					Log.d("onShrinkFinish", "onShrinkFinish");
					iBack.onShrinkFinish();
				}

				break;
			case 1:
				if (iBack != null) {
					Log.d("onExpandFinish", "onExpandFinish");
					iBack.onExpandFinish();

				}
				break;
			case 3:
				if (iBack != null) {
					Log.d("onStartMove", "onStartMove");
					iBack.onStartMove();

				}
			default:
				break;
			}
		}

	};
	private IStatusCallBack iBack;

	public void setStatusCallBack(IStatusCallBack iBack) {
		this.iBack = iBack;
	}

	public interface IStatusCallBack {
		public void onShrinkFinish();

		public void onStartMove();

		public void onExpandFinish();
	}


	
}
