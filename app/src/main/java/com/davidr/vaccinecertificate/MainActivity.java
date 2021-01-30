package com.davidr.vaccinecertificate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements  ZXingScannerView.ResultHandler{


    private ZXingScannerView scannerView;
    private TextView txtResullt;

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(MainActivity.this); // Register ourselves as a handler for scan results.
        scannerView.startCamera();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //init
        scannerView = (ZXingScannerView)findViewById(R.id.zxscan);
        txtResullt = (TextView) findViewById(R.id.txt_result);

        //Request permission
        Dexter.withContext(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "You must", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        scannerView.setResultHandler(MainActivity.this);
                        scannerView.startCamera();
                    }
                })
                .check();

    }

    @Override
    protected void onDestroy() {
        scannerView.stopCamera();
        super.onDestroy();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handleResult(Result rawResult) {

        String[] parts = rawResult.getText().split("#");
        if (parts.length < 3)
        {
            txtResullt.setText("תעודת מתחסן לא תקינה");
            txtResullt.setBackgroundColor(Color.RED);
            scannerView.resumeCameraPreview(this);
            return;
        }
        byte[] decodedBytes = Base64.getDecoder().decode(parts[2]);
        String decodedString = new String(decodedBytes);

        final JSONObject obj;
        try {
            obj = new JSONObject(decodedString);
           String name = obj.getString("fullName");
           String expirationDate = obj.getString("expirationDate");
            txtResullt.setText("שם: " + name+ "\n מחוסן עד לתאריך:\n"+  expirationDate+"\n מאושר!");
            txtResullt.setBackgroundColor(Color.GREEN);
        } catch (JSONException e) {
            txtResullt.setText("תעודת מתחסן לא תקינה");
            txtResullt.setBackgroundColor(Color.RED);
        }

        scannerView.resumeCameraPreview(this);
    }
}
