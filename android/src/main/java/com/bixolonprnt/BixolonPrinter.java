package com.bixolonprnt;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.bixolon.commonlib.BXLCommonConst;
import com.bixolon.commonlib.log.LogService;

import java.nio.ByteBuffer;

import jpos.CashDrawer;
import jpos.JposConst;
import jpos.JposException;
import jpos.LocalSmartCardRW;
import jpos.MSR;
import jpos.MSRConst;
import jpos.POSPrinter;
import jpos.POSPrinterConst;
import jpos.SmartCardRW;
import jpos.SmartCardRWConst;
import jpos.config.JposEntry;
import jpos.events.DataEvent;
import jpos.events.DataListener;
import jpos.events.DirectIOEvent;
import jpos.events.DirectIOListener;
import jpos.events.ErrorEvent;
import jpos.events.ErrorListener;
import jpos.events.OutputCompleteEvent;
import jpos.events.OutputCompleteListener;
import jpos.events.StatusUpdateEvent;
import jpos.events.StatusUpdateListener;
import java.util.Arrays;
import java.util.List;
import com.bxl.config.editor.BXLConfigLoader;

import java.io.FileInputStream;
import java.io.IOException;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import org.json.JSONArray;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.util.Log;
import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

import java.lang.reflect.Method;
import java.util.Set;
// SPP-R310
public class BixolonPrinter {

    private final int LINE_CHARS = 42;
    private Context context = null;
    private static MethodChannel.Result _result = null;

    private BXLConfigLoader bxlConfigLoader = null;
    private POSPrinter posPrinter = null;
    private MSR msr = null;
    private SmartCardRW smartCardRW = null;
    private LocalSmartCardRW localSmartCardRW;
    private CashDrawer cashDrawer = null;
    private final String DEVICE_ADDRESS_START = " (";
    private final String DEVICE_ADDRESS_END = ")";

    // ------------------- Text attribute ------------------- //
    public static int ATTRIBUTE_NORMAL = 0;
    public static int ATTRIBUTE_FONT_A = 1;
    public static int ATTRIBUTE_FONT_B = 2;
    public static int ATTRIBUTE_FONT_C = 4;
    public static int ATTRIBUTE_BOLD = 8;
    public static int ATTRIBUTE_UNDERLINE = 16;
    public static int ATTRIBUTE_REVERSE = 32;
    public static int ATTRIBUTE_FONT_D = 64;
    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;

    static BixolonPrinter INSTANCE;

    public static void main(String[] args) {
        INSTANCE = new BixolonPrinter();

        List<PrintService> services = INSTANCE.getServicesByName("BIXOLON_SRP-R300");
        if(services == null) {
            throw new RuntimeException("No printer services available");
        }
        INSTANCE.printServices(services);

        try {
            INSTANCE.print(services.get(0), "Hello");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PrintService> getServicesByName(String serviceName) {
        //Find printer service by name
        AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(serviceName, null));
        return Arrays.asList(PrintServiceLookup.lookupPrintServices(null, aset));
    }

    public void print(PrintService service, String printData) throws Exception {
        if(service == null) {
            throw new Exception("Service is not valid");
        }
        if(printData == null) {
            throw new Exception("Nothing to print");
        }

        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(new Copies(1));
        pras.add(new PrinterResolution(180,180,PrinterResolution.DPI));

        DocPrintJob job = service.createPrintJob();
        DocAttributeSet das = new HashDocAttributeSet();
        das.add(new PrinterResolution(180,180,PrinterResolution.DPI));

        byte[] desc = printData.getBytes();
        Doc doc = new SimpleDoc(desc, DocFlavor.BYTE_ARRAY.AUTOSENSE, das);

        try {
            job.print(doc, pras);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printServices(List<PrintService> services) {
        System.out.println("Printer Services found:");
        for (PrintService service : services) {
            System.out.println("\t" + service);
        }
    }

}
