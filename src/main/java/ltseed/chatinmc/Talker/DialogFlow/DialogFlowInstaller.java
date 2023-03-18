package ltseed.chatinmc.Talker.DialogFlow;

import ltseed.chatinmc.ChatInMC;

import java.io.*;
import java.net.*;

import static ltseed.chatinmc.ChatInMC.*;

public class DialogFlowInstaller {

    public static boolean isDialogFlowInstalled() {
        try {
            Class.forName("com.google.cloud.dialogflow.v2beta1.SessionsClient");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static void install() throws Exception {

        String os = System.getProperty("os.name").toLowerCase();
        String url;

        if (os.contains("win")) {
            url = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-350.0.0-windows-x86_64.tar.gz";
        } else if (os.contains("mac")) {
            url = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-350.0.0-darwin-x86_64.tar.gz";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")) {
            url = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-350.0.0-linux-x86_64.tar.gz";
        } else {
            ChatInMC.debug.err("Unsupported operating system: " + os);
            return;
        }

        debug.debugB("Downloading Google DialogFlow CLI from " + url);

        URL dlUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) dlUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.connect();

        InputStream inputStream = conn.getInputStream();
        FileOutputStream outputStream = new FileOutputStream("google-cloud-sdk.tar.gz");

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.close();
        inputStream.close();

        debug.debugB("Download completed");

        debug.debugB("Extracting archive");

        //command = "tar -zxvf google-cloud-sdk.tar.gz";
        String[] cs = new String[3];
        cs[0] = "tar";
        cs[1] = "-zxvf";
        cs[2] = "google-cloud-sdk.tar.gz";
        //cs = "tar -zxvf google-cloud-sdk.tar.gz".split(" ");
        Process process = Runtime.getRuntime().exec(cs,null,tp.getDataFolder());
        process.waitFor();

        debug.info("Installation completed");
    }
}