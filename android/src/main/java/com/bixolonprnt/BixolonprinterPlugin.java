package com.bixolonprnt;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import com.bxl.config.editor.BXLConfigLoader;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import android.util.Log;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import jpos.JposConst;
import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;
import android.content.Context;
import com.bxl.config.editor.BXLConfigLoader;

/** BixolonprntPlugin */
public class BixolonprinterPlugin implements FlutterPlugin, MethodCallHandler {
    private static BixolonPrinter bxlPrinter = null;

    /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
    private POSPrinter posPrinter = null;
    private Context context = null;
    private BxlService mBxlService = null;
  @Override
  public void onAttachedToEngine(FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "bixolonprinter");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "bixolonprinter");
    channel.setMethodCallHandler(new BixolonprinterPlugin());
  }

  @Override
  public void onMethodCall( MethodCall call, Result result) {
    String printer = "PP-R310";
    String text = "PP-R310";
    String leftText = "Hello ";
    String rightText = "World";
    if (call.method.equals("printText")) {
        mBxlService = new BxlService();
    }

//      try {
//      } catch (JSONException e) {
//        result.error(e.getMessage(), null, null);
//        e.printStackTrace();
//      }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(FlutterPluginBinding binding) {
    //channel.setMethodCallHandler(null);
  }
}
