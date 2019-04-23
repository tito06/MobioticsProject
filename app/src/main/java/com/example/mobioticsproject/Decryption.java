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
import javax.crypto.spec.SecretKeySpec;

public class Decryption extends AppCompatActivity {

    EditText editText, et;
    TextView textView;
    Button dcrypt_button;
    String outputString;
    String AES = "AES";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decryption);

        editText = findViewById(R.id.edit_dcrypt);
        et = findViewById(R.id.et);
        dcrypt_button= findViewById(R.id.btn_submit);
        textView= findViewById(R.id.tv);

        dcrypt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    outputString = decrypt(outputString, et.getText().toString() );
                }catch (Exception e){
                    e.printStackTrace();
                }textView.setText(outputString);
            }
        });
    }

    private String decrypt(String outputString, String password) throws Exception{

        SecretKeySpec keySpec = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decodeValue = android.util.Base64.decode(outputString, Base64.DEFAULT);
        byte[] decValue = c.doFinal(decodeValue);
        String decryptedValue = new String(decValue);
        return decryptedValue;
    }

    private SecretKeySpec generateKey(String passowrd) throws Exception{

        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = passowrd.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }


}

