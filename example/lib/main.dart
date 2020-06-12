import 'package:flutterpluginbixolon_example/testprint.dart';
import 'package:flutter/material.dart';
import 'dart:async';
import 'package:flutterpluginbixolon/flutterpluginbixolon.dart';
import 'package:flutter/services.dart';

void main() => runApp(new MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => new _MyAppState();
}

class _MyAppState extends State<MyApp> {
  FlutterpluginBixolon bluetooth = FlutterpluginBixolon.instance;
  String _printersArrayString = null;
  List<BluetoothDevice> _devices = [];
  BluetoothDevice _device;
  bool _connected = false;
  // String pathImage;
  TestPrint testPrint;

  @override
  void initState() {
    super.initState();
    initPlatformState();
    testPrint = TestPrint();
  }

  Future<void> initPlatformState() async {
    bool isConnected = await bluetooth.isConnected;
    print("connected ");
    print(bluetooth.isConnected);
    List<BluetoothDevice> devices = [];
    try {
      devices = await bluetooth.getBondedDevices();
      print(devices);
    } on PlatformException {
      print("no devices");
    }

    bluetooth.onStateChanged().listen((state) {
      switch (state) {
        case FlutterpluginBixolon.CONNECTED:
          setState(() {
            _connected = true;
            _printersArrayString = "Connected";
          });
          break;
        case FlutterpluginBixolon.DISCONNECTED:
          setState(() {
            _connected = false;
            _printersArrayString = "not Connected";
          });
          break;
        default:
          print(state);
          break;
      }
    });

    if (!mounted) return;
    setState(() {
      _devices = devices;
      _printersArrayString = "not Connected";
    });

    if (isConnected) {
      setState(() {
        _connected = true;
        _printersArrayString = "Connected";
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: Text('Printer'),
        ),
        body: Container(
          child: Padding(
            padding: const EdgeInsets.all(8.0),
            child: ListView(
              children: <Widget>[
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.start,
                  children: <Widget>[
                    SizedBox(
                      width: 10,
                    ),
                    Text(
                      'Device:',
                      style: TextStyle(
                        fontWeight: FontWeight.bold,
                      ),
                    ),
                    SizedBox(
                      width: 30,
                    ),
                    Expanded(
                      child: DropdownButton(
                        items: _getDeviceItems(),
                        onChanged: (value) => setState(() => _device = value),
                        value: _device,
                      ),
                    ),
                  ],
                ),
                Row(
                  children: <Widget>[
                    Text('devise is: $_printersArrayString\n')
                  ],
                ),
                SizedBox(
                  height: 10,
                ),
                Row(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  mainAxisAlignment: MainAxisAlignment.end,
                  children: <Widget>[
                    RaisedButton(
                      color: Colors.blue,
                      onPressed: () {
                        initPlatformState();
                      },
                      child: Text(
                        'Refresh',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                    SizedBox(
                      width: 20,
                    ),
                    RaisedButton(
                      color: Colors.blue,
                      onPressed: _connect,
                      child: Text(
                        _connected ? 'Disconnect' : 'Connect',
                        style: TextStyle(color: Colors.white),
                      ),
                    ),
                  ],
                ),
                Padding(
                  padding:
                      const EdgeInsets.only(left: 10.0, right: 10.0, top: 50),
                  child: RaisedButton(
                    color: Colors.red,
                    onPressed: () {
                      testPrint.sample('hello world');
                    },
                    child: Text('print', style: TextStyle(color: Colors.white)),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  List<DropdownMenuItem<BluetoothDevice>> _getDeviceItems() {
    List<DropdownMenuItem<BluetoothDevice>> items = [];
    if (_devices.isEmpty) {
      items.add(DropdownMenuItem(
        child: Text('No devices'),
      ));
    } else {
      _devices.forEach((device) {
        items.add(DropdownMenuItem(
          child: Text(device.name),
          value: device,
        ));
      });
    }
    return items;
  }

  void _connect() {
    if (_device == null) {
      show('No device selected.');
    } else {
      bluetooth.isConnected.then((isConnected) {
        if (!isConnected) {
          bluetooth.connect(_device).catchError((error) {
            setState(() {
              _connected = false;
              _printersArrayString = "not Connected";
            });
          });
          setState(() {
            _connected = true;
            _printersArrayString = "Connected";
          });
        }
      });
    }
  }

  Future show(
    String message, {
    Duration duration: const Duration(seconds: 3),
  }) async {
    await new Future.delayed(new Duration(milliseconds: 100));
    Scaffold.of(context).showSnackBar(
      new SnackBar(
        content: new Text(
          message,
          style: new TextStyle(
            color: Colors.white,
          ),
        ),
        duration: duration,
      ),
    );
  }
}
