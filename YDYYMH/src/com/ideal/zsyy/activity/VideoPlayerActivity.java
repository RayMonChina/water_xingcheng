package com.ideal.zsyy.activity;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue.IdleHandler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.jijiang.wtapp.R;
import com.ideal.zsyy.video.SoundView;
import com.ideal.zsyy.video.SoundView.OnVolumeChangedListener;
import com.ideal.zsyy.video.VideoView;
import com.ideal.zsyy.video.VideoView.MySizeChangeLinstener;

public class VideoPlayerActivity extends Activity {

	private String video_url = "";

	private final static String TAG = "VideoPlayerActivity";
	private boolean isOnline = false;
	private boolean isChangedVideo = false;

	public static LinkedList<MovieInfo> playList = new LinkedList<MovieInfo>();

	public class MovieInfo {
		String displayName;
		String path;
	}

	private Uri videoListUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
	private static int position;
	// private static boolean backFromAD = false;
	private int playedTime;

	// private AdView adView;

	private VideoView vv = null;
	private SeekBar seekBar = null;
	private SeekBar seekbar_volume = null;

	private TextView durationTextView = null;
	private TextView playedTextView = null;
	private GestureDetector mGestureDetector = null;
	private AudioManager mAudioManager = null;

	private int maxVolume = 0;
	private int currentVolume = 0;

	private ImageButton bn1 = null;
	private ImageButton bn2 = null;
	private ImageView bn3 = null;
	private ImageButton bn4 = null;
	private ImageButton bn5 = null;

	private View controlView = null;
	private PopupWindow controler = null;

	private SoundView mSoundView = null;
	private PopupWindow mSoundWindow = null;

	// private View extralView = null;
	// private PopupWindow extralWindow = null;

	private static int screenWidth = 0;
	private static int screenHeight = 0;
	private static int controlHeight = 0;

	private final static int TIME = 6868;

	private boolean isControllerShow = true;
	private boolean isPaused = false;
	private boolean isFullScreen = false;
	private boolean isSilent = false;
	private boolean isSoundShow = false;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.videoplayer);

		video_url = getIntent().getStringExtra("url");
		// ToastUtil.show(getApplicationContext(), "" + video_url);
		Log.v("tags", "video_url=" + video_url);

		Looper.myQueue().addIdleHandler(new IdleHandler() {

			@Override
			public boolean queueIdle() {

				// TODO Auto-generated method stub
				if (controler != null && vv.isShown()) {
					controler.showAtLocation(vv, Gravity.BOTTOM, 0, 0);
					// controler.update(screenWidth, controlHeight);
					controler.update(0, 0, screenWidth, controlHeight);
				}

				// if (extralWindow != null && vv.isShown()) {
				// extralWindow.showAtLocation(vv, Gravity.TOP, 0, 0);
				// extralWindow.update(0, 25, screenWidth, 60);
				// }

				// myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
				return false;
			}
		});

		controlView = getLayoutInflater().inflate(R.layout.controler, null);
		controler = new PopupWindow(controlView);
		durationTextView = (TextView) controlView.findViewById(R.id.duration);
		playedTextView = (TextView) controlView.findViewById(R.id.has_played);

		mSoundView = new SoundView(this);
		mSoundView.setOnVolumeChangeListener(new OnVolumeChangedListener() {

			@Override
			public void setYourVolume(int index) {

				cancelDelayHide();
				updateVolume(index);
				hideControllerDelay();
			}
		});

		mSoundWindow = new PopupWindow(mSoundView);

		// extralView = getLayoutInflater().inflate(R.layout.extral, null);
		// extralWindow = new PopupWindow(extralView);
		//
		// ImageButton backButton = (ImageButton) extralView
		// .findViewById(R.id.back);
		// ImageButton aboutButton = (ImageButton) extralView
		// .findViewById(R.id.about);

		position = -1;

		// backButton.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// VideoPlayerActivity.this.finish();
		// }
		//
		// });
		// aboutButton.setOnClickListener(new OnClickListener() {
		//
		// Dialog dialog;
		// OnClickListener mClickListener = new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// Log.d("DIALOG", "DISMISS");
		// dialog.dismiss();
		// // vv.seekTo(msec);
		// vv.start();
		// }
		// };
		//
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// /*
		// * Intent intent = new Intent();
		// * intent.setClass(VideoPlayerActivity.this,
		// * VideoChooseActivity.class);
		// * VideoPlayerActivity.this.startActivityForResult(intent, 0);
		// */
		//
		// dialog = new Dialog(VideoPlayerActivity.this,
		// R.style.transDialog);
		// dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// View view = VideoPlayerActivity.this.getLayoutInflater()
		// .inflate(R.layout.about, null);
		// dialog.setContentView(view);
		// view.findViewById(R.id.cancel).setOnClickListener(
		// mClickListener);
		// vv.pause();
		// dialog.show();
		// cancelDelayHide();
		// }
		//
		// });

		bn1 = (ImageButton) controlView.findViewById(R.id.button1);
		bn2 = (ImageButton) controlView.findViewById(R.id.button2);
		bn3 = (ImageView) controlView.findViewById(R.id.button3);
		bn4 = (ImageButton) controlView.findViewById(R.id.button4);
		bn5 = (ImageButton) controlView.findViewById(R.id.button5);

		vv = (VideoView) findViewById(R.id.vv);

		vv.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {

				vv.stopPlayback();
				isOnline = false;

				new AlertDialog.Builder(VideoPlayerActivity.this)
						.setTitle("对不起")
						.setMessage("您所播的视频格式不正确，播放已停止。")
						.setPositiveButton("知道了",
								new AlertDialog.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										vv.stopPlayback();
										VideoPlayerActivity.this.finish();

									}

								}).setCancelable(false).show();

				return false;
			}

		});

		Uri uri = getIntent().getData();
		if (uri != null) {

			vv.stopPlayback();
			vv.setVideoURI(uri);
			isOnline = true;

			bn3.setImageResource(R.drawable.video_pause);
		} else {
			bn3.setImageResource(R.drawable.video_play);
		}

		getVideoFile(playList, new File("/sdcard/"));

		if (android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED)) {

			Cursor cursor = getContentResolver()
					.query(videoListUri,
							new String[] { "_display_name", "_data" }, null,
							null, null);
			int n = cursor.getCount();
			cursor.moveToFirst();
			LinkedList<MovieInfo> playList2 = new LinkedList<MovieInfo>();
			for (int i = 0; i != n; ++i) {
				MovieInfo mInfo = new MovieInfo();
				mInfo.displayName = cursor.getString(cursor
						.getColumnIndex("_display_name"));
				mInfo.path = cursor.getString(cursor.getColumnIndex("_data"));
				playList2.add(mInfo);
				cursor.moveToNext();
			}

			if (playList2.size() > playList.size()) {
				playList = playList2;
			}
		}

		vv.setMySizeChangeLinstener(new MySizeChangeLinstener() {

			@Override
			public void doMyThings() {
				// TODO Auto-generated method stub
				setVideoScale(SCREEN_DEFAULT);
			}

		});

		// bn1.setAlpha(0xBB);
		// bn2.setAlpha(0xBB);
		// bn3.setAlpha(0xBB);
		// bn4.setAlpha(0xBB);

		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		currentVolume = mAudioManager
				.getStreamVolume(AudioManager.STREAM_MUSIC);
		bn5.setAlpha(findAlphaFromSound());

		bn1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Intent intent = new Intent();
				// intent.setClass(VideoPlayerActivity.this,
				// VideoChooseActivity.class);
				// VideoPlayerActivity.this.startActivityForResult(intent, 0);
				// cancelDelayHide();
			}

		});

		bn4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int n = playList.size();
				isOnline = false;
				if (++position < n) {
					vv.setVideoPath(playList.get(position).path);
					cancelDelayHide();
					hideControllerDelay();
				} else {
					VideoPlayerActivity.this.finish();
				}
			}

		});

		bn3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelDelayHide();
				if (isPaused) {
					vv.start();
					bn3.setImageResource(R.drawable.video_pause);
					hideControllerDelay();
				} else {
					vv.pause();
					bn3.setImageResource(R.drawable.video_play);
				}
				isPaused = !isPaused;

			}

		});

		bn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isOnline = false;
				if (--position >= 0) {
					vv.setVideoPath(playList.get(position).path);
					cancelDelayHide();
					hideControllerDelay();
				} else {
					VideoPlayerActivity.this.finish();
				}
			}

		});

		bn5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cancelDelayHide();
				if (isSoundShow) {
					mSoundWindow.dismiss();
				} else {
					if (mSoundWindow.isShowing()) {
						mSoundWindow.update(15, 0, SoundView.MY_WIDTH,
								SoundView.MY_HEIGHT);
					} else {
						mSoundWindow.showAtLocation(vv, Gravity.RIGHT
								| Gravity.CENTER_VERTICAL, 15, 0);
						mSoundWindow.update(15, 0, SoundView.MY_WIDTH,
								SoundView.MY_HEIGHT);
					}
				}
				isSoundShow = !isSoundShow;
				hideControllerDelay();
			}
		});

		bn5.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				if (isSilent) {
					bn5.setImageResource(R.drawable.soundenable);
				} else {
					bn5.setImageResource(R.drawable.sounddisable);
				}
				isSilent = !isSilent;
				updateVolume(currentVolume);
				cancelDelayHide();
				hideControllerDelay();
				return true;
			}

		});

		seekBar = (SeekBar) controlView.findViewById(R.id.seekbar);
		seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onProgressChanged(SeekBar seekbar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub

				if (fromUser) {

					// if (!isOnline)
					{
						vv.seekTo(progress);
					}

				}

			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				myHandler.removeMessages(HIDE_CONTROLER);
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
			}
		});

		seekbar_volume = (SeekBar) controlView
				.findViewById(R.id.seekbar_volume);
		seekbar_volume.setMax(maxVolume);
		seekbar_volume.setProgress(currentVolume);
		seekbar_volume
				.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

					@Override
					public void onProgressChanged(SeekBar seekbar,
							int progress, boolean fromUser) {
						// TODO Auto-generated method stub

						mAudioManager.setStreamVolume(
								AudioManager.STREAM_MUSIC, progress, 0);
						currentVolume = mAudioManager
								.getStreamVolume(AudioManager.STREAM_MUSIC); // 获取当前值
						seekbar_volume.setProgress(currentVolume);

					}

					@Override
					public void onStartTrackingTouch(SeekBar arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						// TODO Auto-generated method stub

					}
				});

		ImageView video_notfull = (ImageView) controlView
				.findViewById(R.id.video_notfull);
		video_notfull.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				finish();

			}
		});

		getScreenSize();

		mGestureDetector = new GestureDetector(getApplicationContext(),
				new SimpleOnGestureListener() {

					@Override
					public boolean onDoubleTap(MotionEvent e) {
						// TODO Auto-generated method stub
						if (isFullScreen) {
							setVideoScale(SCREEN_DEFAULT);
						} else {
							setVideoScale(SCREEN_FULL);
						}
						isFullScreen = !isFullScreen;
						Log.d(TAG, "onDoubleTap");

						if (isControllerShow) {
							showController();
						}
						// return super.onDoubleTap(e);
						return true;
					}

					@Override
					public boolean onSingleTapConfirmed(MotionEvent e) {
						// TODO Auto-generated method stub
						if (!isControllerShow) {
							showController();
							hideControllerDelay();
						} else {
							cancelDelayHide();
							hideController();
						}
						// return super.onSingleTapConfirmed(e);
						return true;
					}

					@Override
					public void onLongPress(MotionEvent e) {
						// TODO Auto-generated method stub
						if (isPaused) {
							vv.start();
							bn3.setImageResource(R.drawable.video_pause);
							cancelDelayHide();
							hideControllerDelay();
						} else {
							vv.pause();
							bn3.setImageResource(R.drawable.video_play);
							cancelDelayHide();
							showController();
						}
						isPaused = !isPaused;
						// super.onLongPress(e);
					}
				});

		vv.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer arg0) {
				// TODO Auto-generated method stub

				setVideoScale(SCREEN_DEFAULT);
				isFullScreen = false;
				if (isControllerShow) {
					showController();
				}

				int i = vv.getDuration();
				Log.d("onCompletion", "" + i);
				seekBar.setMax(i);
				i /= 1000;
				int minute = i / 60;
				int hour = minute / 60;
				int second = i % 60;
				minute %= 60;
				durationTextView.setText(String.format("%02d:%02d:%02d", hour,
						minute, second));

				/*
				 * controler.showAtLocation(vv, Gravity.BOTTOM, 0, 0);
				 * controler.update(screenWidth, controlHeight);
				 * myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
				 */

				vv.start();
				bn3.setImageResource(R.drawable.video_pause);
				hideControllerDelay();
				myHandler.sendEmptyMessage(PROGRESS_CHANGED);
			}
		});

		vv.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer arg0) {
				// TODO Auto-generated method stub
				int n = playList.size();
				isOnline = false;
				if (++position < n) {
					vv.setVideoPath(playList.get(position).path);
				} else {
					vv.stopPlayback();
					VideoPlayerActivity.this.finish();
				}
			}
		});

		if (video_url != null) {
			vv.setVideoPath(video_url);
			isOnline = true;
			isChangedVideo = true;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == 0 && resultCode == Activity.RESULT_OK) {

			vv.stopPlayback();

			int result = data.getIntExtra("CHOOSE", -1);
			Log.d("RESULT", "" + result);
			if (result != -1) {
				isOnline = false;
				isChangedVideo = true;
				vv.setVideoPath(playList.get(result).path);
				position = result;
			} else {
				String url = data.getStringExtra("CHOOSE_URL");
				if (url != null) {
					vv.setVideoPath(url);
					isOnline = true;
					isChangedVideo = true;
				}
			}

			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private final static int PROGRESS_CHANGED = 0;
	private final static int HIDE_CONTROLER = 1;

	Handler myHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub

			switch (msg.what) {

			case PROGRESS_CHANGED:

				int i = vv.getCurrentPosition();
				seekBar.setProgress(i);

				if (isOnline) {
					int j = vv.getBufferPercentage();
					seekBar.setSecondaryProgress(j * seekBar.getMax() / 100);
				} else {
					seekBar.setSecondaryProgress(0);
				}

				i /= 1000;
				int minute = i / 60;
				int hour = minute / 60;
				int second = i % 60;
				minute %= 60;
				playedTextView.setText(String.format("%02d:%02d:%02d", hour,
						minute, second));

				sendEmptyMessageDelayed(PROGRESS_CHANGED, 100);
				break;

			case HIDE_CONTROLER:
				hideController();
				break;
			}

			super.handleMessage(msg);
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		boolean result = mGestureDetector.onTouchEvent(event);

		if (!result) {
			if (event.getAction() == MotionEvent.ACTION_UP) {

				/*
				 * if(!isControllerShow){ showController();
				 * hideControllerDelay(); }else { cancelDelayHide();
				 * hideController(); }
				 */
			}
			result = super.onTouchEvent(event);
		}

		return result;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub

		getScreenSize();
		if (isControllerShow) {

			cancelDelayHide();
			hideController();
			showController();
			hideControllerDelay();
		}

		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		playedTime = vv.getCurrentPosition();
		vv.pause();
		bn3.setImageResource(R.drawable.video_play);
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (!isChangedVideo) {
			vv.seekTo(playedTime);
			vv.start();
		} else {
			isChangedVideo = false;
		}

		// if(vv.getVideoHeight()!=0){
		if (vv.isPlaying()) {
			bn3.setImageResource(R.drawable.video_pause);
			hideControllerDelay();
		}

		// Log.d("REQUEST", "NEW AD !");
		// adView.requestFreshAd();

		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}

		super.onResume();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		if (controler.isShowing()) {
			controler.dismiss();
			// extralWindow.dismiss();
		}

		if (mSoundWindow.isShowing()) {
			mSoundWindow.dismiss();
		}

		myHandler.removeMessages(PROGRESS_CHANGED);
		myHandler.removeMessages(HIDE_CONTROLER);

		if (vv.isPlaying()) {
			vv.stopPlayback();
		}

		playList.clear();

		super.onDestroy();
	}

	private void getScreenSize() {

		Display display = getWindowManager().getDefaultDisplay();
		screenHeight = display.getHeight();
		screenWidth = display.getWidth();
		controlHeight = screenHeight / 4;

		// adView = (AdView) extralView.findViewById(R.id.ad);
		// LayoutParams lp = adView.getLayoutParams();
		// lp.width = screenWidth * 3 / 5;
	}

	private void hideController() {
		if (controler.isShowing()) {
			controler.update(0, 0, 0, 0);
			// extralWindow.update(0, 0, screenWidth, 0);
			isControllerShow = false;
		}
		if (mSoundWindow.isShowing()) {
			mSoundWindow.dismiss();
			isSoundShow = false;
		}
	}

	private void hideControllerDelay() {
		myHandler.sendEmptyMessageDelayed(HIDE_CONTROLER, TIME);
	}

	private void showController() {
		controler.update(0, 0, screenWidth, controlHeight);
		// if (isFullScreen) {
		// extralWindow.update(0, 0, screenWidth, 60);
		// } else {
		// extralWindow.update(0, 25, screenWidth, 60);
		// }

		isControllerShow = true;
	}

	private void cancelDelayHide() {
		myHandler.removeMessages(HIDE_CONTROLER);
	}

	private final static int SCREEN_FULL = 0;
	private final static int SCREEN_DEFAULT = 1;

	private void setVideoScale(int flag) {

		LayoutParams lp = vv.getLayoutParams();

		switch (flag) {
		case SCREEN_FULL:

			Log.d(TAG, "screenWidth: " + screenWidth + " screenHeight: "
					+ screenHeight);
			vv.setVideoScale(screenWidth, screenHeight);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			// if (getRequestedOrientation() !=
			// ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			// }

			break;

		case SCREEN_DEFAULT:

			int videoWidth = vv.getVideoWidth();
			int videoHeight = vv.getVideoHeight();
			int mWidth = screenWidth;
			int mHeight = screenHeight - 25;

			if (videoWidth > 0 && videoHeight > 0) {
				if (videoWidth * mHeight > mWidth * videoHeight) {
					// Log.i("@@@", "image too tall, correcting");
					mHeight = mWidth * videoHeight / videoWidth;
				} else if (videoWidth * mHeight < mWidth * videoHeight) {
					// Log.i("@@@", "image too wide, correcting");
					mWidth = mHeight * videoWidth / videoHeight;
				} else {

				}
			}

			vv.setVideoScale(mWidth, mHeight);
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

			// if (getRequestedOrientation() !=
			// ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
			// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			// }

			break;
		}
	}

	private int findAlphaFromSound() {
		if (mAudioManager != null) {
			// int currentVolume =
			// mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			int alpha = currentVolume * (0xCC - 0x55) / maxVolume + 0x55;
			return alpha;
		} else {
			return 0xCC;
		}
	}

	private void updateVolume(int index) {
		if (mAudioManager != null) {
			if (isSilent) {
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
			} else {
				mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index,
						0);
			}
			currentVolume = index;
			bn5.setAlpha(findAlphaFromSound());
		}
	}

	private void getVideoFile(final LinkedList<MovieInfo> list, File file) {

		file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				String name = file.getName();
				int i = name.indexOf('.');
				if (i != -1) {
					name = name.substring(i);
					if (name.equalsIgnoreCase(".mp4")
							|| name.equalsIgnoreCase(".3gp")) {

						MovieInfo mi = new MovieInfo();
						mi.displayName = file.getName();
						mi.path = file.getAbsolutePath();
						list.add(mi);
						return true;
					}
				} else if (file.isDirectory()) {
					getVideoFile(list, file);
				}
				return false;
			}
		});
	}
}