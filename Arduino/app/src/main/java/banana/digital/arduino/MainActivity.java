package banana.digital.arduino;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button button;
    Switch toogle;

    private boolean isConnected = false;
    OutputStream outputStream;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        toogle = findViewById(R.id.toggle);

        button.setOnClickListener(v -> connect());

        toogle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                send(b ? 2 : 1);
            }
        });
    }

    private void connect() {
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        for(BluetoothDevice bluetoothDevice : bluetoothAdapter.getBondedDevices()) {
            if(bluetoothDevice.getName().equals("HC-06")) {
                try {
                    BluetoothSocket socket = bluetoothDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                    socket.connect();
                    isConnected = true;
                    outputStream = socket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("error", "connect" + e);
                }
            }
        }
    }

    private void send(int i) {
        if(isConnected) {
            try {
                outputStream.write(i);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("error", "send" + e);
            }
        }
    }
}
