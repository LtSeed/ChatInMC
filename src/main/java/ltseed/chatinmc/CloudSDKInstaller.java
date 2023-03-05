package ltseed.chatinmc;

import java.io.*;
import java.net.*;

public class CloudSDKInstaller {

    public static boolean isCloudSdkInstalled() {
        try {
            Process process = Runtime.getRuntime().exec("gcloud version".split(" "));
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void install() {
        String osName = System.getProperty("os.name").toLowerCase();
        String cloudSDKUrl = "";

        if (osName.contains("win")) {
            cloudSDKUrl = "https://dl.google.com/dl/cloudsdk/channels/rapid/GoogleCloudSDKInstaller.exe";
        } else if (osName.contains("mac")) {
            cloudSDKUrl = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-355.0.0-darwin-x86_64.tar.gz";
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            cloudSDKUrl = "https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-355.0.0-linux-x86_64.tar.gz";
        }

        if (!cloudSDKUrl.isEmpty()) {
            try {
                URL url = new URL(cloudSDKUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    File tempFile = File.createTempFile("google-cloud-sdk", null);
                    FileOutputStream outputStream = new FileOutputStream(tempFile);
                    byte[] buffer = new byte[4096];
                    int bytesRead = -1;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();

                    Process process;
                    String[] commands;
                    if (osName.contains("win")) {
                        commands = new String[4];
                        commands[0] = "cmd";
                        commands[1] = "/c";
                        commands[2] = "start";
                        commands[3] = tempFile.getAbsolutePath();
                    } else {
                        commands = new String[5];
                        commands[0] = "tar";
                        commands[1] = "-xzf";
                        commands[2] = tempFile.getAbsolutePath();
                        commands[3] = "-C";
                        commands[4] = "~";
                    }
                    process = Runtime.getRuntime().exec(commands,null,ChatInMC.tp.getDataFolder());
                    process.waitFor();
                } else {
                    throw new Exception("Failed to download Google Cloud SDK");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
