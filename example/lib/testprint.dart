import 'package:flutterpluginbixolon/flutterpluginbixolon.dart';

class TestPrint {
  FlutterpluginBixolon bluetooth = FlutterpluginBixolon.instance;

  sample(String pathImage) async {
    bluetooth.isConnected.then((isConnected) {
      if (isConnected) {
        bluetooth.printCustom("Thank You", 2, 1);
      }
    });
  }
}
