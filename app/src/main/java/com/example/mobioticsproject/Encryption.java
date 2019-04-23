package com.example.mobioticsproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Encryption extends AppCompatActivity {

    EditText editText, et;
    TextView textView;
    Button encrypt_button;
    String outputString;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);

        editText = findViewById(R.id.edit_encrypt);
        et = findViewById(R.id.et);
        textView = findViewById(R.id.tv);
        encrypt_button = findViewById(R.id.btn_submit);

        encrypt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString = encrypt(et.getText().toString(), editText.getText().toString());
                    textView.setText(outputString);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


    }

    private String encrypt(String Data, String password) throws Exception{
        SecretKeySpec keySpec = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encVal = c.doFinal(Data.getBytes());
        String encryptedValue = android.util.Base64.encodeToString(encVal, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key,"AES");
        return secretKeySpec;
    }
}


