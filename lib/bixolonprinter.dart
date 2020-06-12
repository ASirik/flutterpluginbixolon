import 'dart:async';

import 'package:flutter/services.dart';

class BixolonPrinter {
  static const MethodChannel _channel = const MethodChannel('bixolonprinter');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

//"SPP-R310"
  static Future<String> main(String name) async {
    print('print hello');
    final String result = await _channel.invokeMethod('printText');
    return result;
  }
}
