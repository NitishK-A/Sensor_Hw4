package com.example.nitishatal.sensors;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
 //   DbHelper mDatabaseHelper;
    DbHelper1 DatabaseAccelerometer;
    DbHelper2 DatabaseGyroscope;
    DbHelper3 DatabaseOrientation;
    DbHelper4 DatabaseGPS;
    DbHelper5 DatabaseProximity;
    private int temp = 0;
    public static final String TAG = "MainActivity";
    private SensorManager mSensorManager;
    Sensor accelerometer;
    Sensor Gyroscope;
    Sensor Orientatation;
    Sensor Proximity;
    TextView h1, h2, h3, h4;
    TextView a1, a2, a3, g1, g2, g3, o1, o2, o3, p, gps,latt,longi;

    private LocationManager locationManager;
    private LocationListener listener;

    private long lastTime = 0;
    private float lastX, lastY, lastZ;
    private static final int THRESHOLD = 4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a1 = (TextView) findViewById(R.id.a1);
        a2 = (TextView) findViewById(R.id.a2);
        a3 = (TextView) findViewById(R.id.a3);
        h1 = (TextView) findViewById(R.id.h1);

        g1 = (TextView) findViewById(R.id.g1);
        g2 = (TextView) findViewById(R.id.g2);
        g3 = (TextView) findViewById(R.id.g3);
        h2 = (TextView) findViewById(R.id.h2);

        o1 = (TextView) findViewById(R.id.o1);
        o2 = (TextView) findViewById(R.id.o2);
        o3 = (TextView) findViewById(R.id.o3);
        h3 = (TextView) findViewById(R.id.h3);

        h4 = (TextView) findViewById(R.id.h4);
        p = (TextView) findViewById(R.id.p);

        gps = (TextView) findViewById(R.id.h5);
        longi = (TextView) findViewById(R.id.longi);
        latt = (TextView) findViewById(R.id.latt);
       // altitude = (TextView) findViewById(R.id.alt);


         //Button database=(Button) findViewById(R.id.Data);
        //Button b2=(Button) findViewById(R.id.b2);

      //  mDatabaseHelper = new DbHelper(this);
        DatabaseAccelerometer= new DbHelper1(this);
        DatabaseGyroscope= new DbHelper2(this);
        DatabaseOrientation= new DbHelper3(this);
        DatabaseGPS= new DbHelper4(this);
        DatabaseProximity= new DbHelper5(this);

        //LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "ds" + location.getLatitude());
                longi.setText("Longitude: "+location.getLongitude());
                latt.setText("Latitude"+location.getLatitude());
                DatabaseGPS.addData(getDateTime(),"Longitude:"+location.getLongitude(),"Latitude:"+location.getLatitude());
              //  mDatabaseHelper.addSensorGPS("Longitude:"+location.getLongitude()+",Lattitude:"+location.getLatitude());
               // altitude.setText("Altitude"+location.getAltitude());


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);

            }

        };
        //configure_button();


        /*b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp=1;
                touch();

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp=0;

            }
        });*/


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            Log.d(TAG, "Success! There's a accelerometer");

        } else {
            Log.d(TAG, " Failure! No accelerometer");
        }

        Gyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        if (Gyroscope != null) {
            Log.d(TAG, "Success! There's a G");
        } else {
            Log.d(TAG, " Failure! No G");
        }

        Orientatation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if (Orientatation != null) {
            Log.d(TAG, "Success! There's a O");
           // Log.d(TAG, getDateTime());

        } else {
            Log.d(TAG, getDateTime());
        }

        Proximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (Gyroscope != null) {
            Log.d(TAG, "Success! There's a P");
        } else {
            Log.d(TAG, " Failure! No P");
        }
        


        /*database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Filename="Database.csv";
                File file = new File(getApplicationContext().getFilesDir(), Filename);
                try {
                    FileOutputStream out=openFileOutput(Filename, Context.MODE_PRIVATE);
                    DatabaseAccelerometer.export(out);
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });*/
    }




    @Override
    protected void onStart() {
        super.onStart();
        if (accelerometer != null) {
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (Orientatation != null) {
            mSensorManager.registerListener(this, Orientatation, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (Gyroscope != null) {
            mSensorManager.registerListener(this, Gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (Proximity != null) {
            mSensorManager.registerListener(this, Proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won't execute IF permissions are not allowed, because in the line above there is return statement.
        locationManager.requestLocationUpdates("gps", 0, 0, listener);

       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, listener);
        
    }
    @Override
    protected void onStop() {
        super.onStop();
        mSensorManager.unregisterListener(this);
        locationManager.removeUpdates(listener);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }


   /* public  void touch() {
        // Do something here if sensor accuracy changes.
        if (accelerometer != null) {
            mSensorManager.registerListener(this, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (Orientatation != null) {
            mSensorManager.registerListener(this, Orientatation,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (Gyroscope != null) {
            mSensorManager.registerListener(this, Gyroscope,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (Proximity != null) {
            mSensorManager.registerListener(this, Proximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }*/


    @Override
    public final void onSensorChanged(SensorEvent event) {
        int ig=1;
        //String col1,col2,col3,col4;

        Sensor s=event.sensor;
        if(s.getType()==Sensor.TYPE_ACCELEROMETER){
            String col2=event.values[0]+","+event.values[1]+","+event.values[2];
            //Log.d(TAG,"X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);
            a1.setText("X: "+event.values[0]);
            a2.setText("Y: "+event.values[1]);
            a3.setText("Z: "+event.values[2]);
            DatabaseAccelerometer.addData(getDateTime(),"X:"+event.values[0],"Y:"+event.values[1],"Z:"+event.values[2]);
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long currentTime = System.currentTimeMillis();
            if ((currentTime - lastTime) > 100) {
                long diffTime = (currentTime - lastTime);
                lastTime = currentTime;
                float speed = Math.abs(x + y + z - lastX - lastY - lastZ)/ diffTime * 10000;
                if (speed > THRESHOLD) {
                    Log.d("sensor", "Shake Detected ,Speed: " + speed);
                    Toast.makeText(this, "Shake Detected,Speed: " + speed, Toast.LENGTH_SHORT).show();
                }
                lastX = x;
                lastY = y;
                lastZ = z;
            }



        }
        else if(s.getType()==Sensor.TYPE_GYROSCOPE){
            String col3=event.values[0]+","+event.values[1]+","+event.values[2];
            //Log.d(TAG,"X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);
            g1.setText("X: "+event.values[0]);
            g2.setText("Y: "+event.values[1]);
            g3.setText("Z: "+event.values[2]);
            //mDatabaseHelper.Update(ig,col3);
            DatabaseGyroscope.addData(getDateTime(),"X:"+event.values[0],"Y:"+event.values[1],"Z:"+event.values[2]);
            ig++;


        }
        else if(s.getType()==Sensor.TYPE_ORIENTATION){
            String col4=event.values[0]+","+event.values[1]+","+event.values[2];
            //Log.d(TAG,"X: "+event.values[0]+" Y: "+event.values[1]+" Z: "+event.values[2]);
            o1.setText("X: "+event.values[0]);
            o2.setText("Y: "+event.values[1]);
            o3.setText("Z: "+event.values[2]);
           // mDatabaseHelper.addSensorO(col4);
            DatabaseOrientation.addData(getDateTime(),"X:"+event.values[0],"Y:"+event.values[1],"Z:"+event.values[2]);

        }
        else if(s.getType()==Sensor.TYPE_PROXIMITY){
            String col5="Value:"+event.values[0];
            p.setText("Value: "+ event.values[0]);
            //mDatabaseHelper.addSensorP(col5);
            DatabaseProximity.addData(getDateTime(),col5);

        }
        //mDatabaseHelper.addData(col)


    }
}
