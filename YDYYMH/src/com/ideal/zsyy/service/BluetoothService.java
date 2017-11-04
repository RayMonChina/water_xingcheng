package com.ideal.zsyy.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.Toast;

public class BluetoothService {

	private Context _context;
	private BluetoothAdapter bluetoothAdapter = BluetoothAdapter
			.getDefaultAdapter();
	private List<BluetoothDevice> bluetoothDevices = new ArrayList<BluetoothDevice>();
	private String[] deviceNames = null;
	private BluetoothDevice defaultDvice = null;
	private bondFinishListener bonListener;
	PreferencesService preService;

	public BluetoothService(Context context) {
		this._context = context;
		preService = new PreferencesService(context);
		this.initIntentFilter();
	}

	public void unRegisterBlue() {
		if (receiver != null)
			this._context.unregisterReceiver(receiver);
	}

	public boolean isOpen() {
		return this.bluetoothAdapter.isEnabled();
	}

	public void open() {
		Intent enableBtIntent = new Intent(
				BluetoothAdapter.ACTION_REQUEST_ENABLE);
		this._context.startActivity(enableBtIntent);
	}

	public void close() {
		this.bluetoothAdapter.disable();
	}

	public void searchDevice() {
		this.bluetoothDevices.clear();
		this.bluetoothAdapter.startDiscovery();
	}

	private void initIntentFilter() {
		// 设置广播信息过滤
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
		intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
		// 注册广播接收器，接收并处理搜索结果
		this._context.registerReceiver(receiver, intentFilter);

	}

	private void bondDevice(BluetoothDevice bdevice) {
		try {
			PreferencesService preService = new PreferencesService(
					this._context);
			Method createBondMethod = BluetoothDevice.class
					.getMethod("createBond");
			createBondMethod.invoke(bdevice);
			preService.SaveBlueDevice(bdevice.getName(), bdevice.getAddress());
			if (bonListener != null) {
				bonListener.doOperate();
			}
			Log.i("blue", "绑定成功");
		} catch (Exception e) {
			// TODO: handle exception
			Toast.makeText(this._context, "配对失败！", Toast.LENGTH_SHORT).show();
		}
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		ProgressDialog progressDialog = null;

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				BluetoothDevice device = intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				bluetoothDevices.add(device);
				if (device.getName() == "Qsprinter") {
					progressDialog.dismiss();
					defaultDvice = device;
					if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
						bondDevice(device);
					} else if (device.getBondState() == BluetoothDevice.BOND_BONDED) {
						preService.SaveBlueDevice(device.getName(),
								device.getAddress());
						if (bonListener != null) {
							bonListener.doOperate();
						}
					}
				}
			} else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
				progressDialog = ProgressDialog.show(context, "请稍等...",
						"搜索蓝牙设备中...", true);

			} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
					.equals(action)) {
				System.out.println("设备搜索完毕");
				progressDialog.dismiss();
				if(bluetoothDevices==null||bluetoothDevices.size()==0)
				{
					Toast.makeText(_context, "没有搜索到设备", Toast.LENGTH_SHORT).show();
					return;
				}
				if (defaultDvice == null) {
					deviceNames = new String[bluetoothDevices.size()];
					for (int j = 0; j < bluetoothDevices.size(); j++) {
						deviceNames[j] = bluetoothDevices.get(j).getName();
					}
					new AlertDialog.Builder(context)
							.setTitle("选择配对设备")
							.setItems(deviceNames,
									new DialogInterface.OnClickListener() {
										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											BluetoothDevice lDevice = bluetoothDevices
													.get(which);
											if (lDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
												bondDevice(lDevice);
											} else if (lDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
												preService.SaveBlueDevice(
														lDevice.getName(),
														lDevice.getAddress());
												if (bonListener != null) {
													bonListener.doOperate();
												}
											}
										}
									}).create().show();
				}
			}
		}

	};

	public interface bondFinishListener {
		void doOperate();
	}

	public bondFinishListener getBonListener() {
		return bonListener;
	}

	public void setBonListener(bondFinishListener bonListener) {
		this.bonListener = bonListener;
	}

}
