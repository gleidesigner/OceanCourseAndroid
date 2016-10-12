package com.gleidesilva.bookslibrary;

import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by gleides on 11/10/16.
 */

public class HttpRequest {

    public static String GET(String path){
        String result = null;
        try {
            //Dispara a conexao para o servidor
            URL url = new URL(path);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.connect();

            int resposta = conexao.getResponseCode();

            if(resposta == HttpURLConnection.HTTP_OK){
                InputStream is = conexao.getInputStream();
                result = bytesParaString(is);
                Log.i("Resultado: ", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String bytesParaString(InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, bytesRead);
        }
        return new String(byteArrayOutputStream.toByteArray(), "UTF-8");
    }
}
