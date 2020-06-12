import 'dart:async';

import 'package:flutter/services.dart';

class Flutterbluetoothserial {
  static const MethodChannel _channel =
      const MethodChannel('flutterbluetoothserial');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }
}
