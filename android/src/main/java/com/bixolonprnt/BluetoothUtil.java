package com.bixolonprnt;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Set;

public class BluetoothUtil {

    public static void startBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        System.out.println("out " + bluetoothAdapter);
        if (!bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.enable();
            System.out.println("in " + bluetoothAdapter.enable());
        }
    }
}
