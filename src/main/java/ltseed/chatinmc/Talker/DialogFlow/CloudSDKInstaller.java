package ltseed.chatinmc.Talker.DialogFlow;

import ltseed.chatinmc.ChatInMC;

import java.io.*;
import java.net.*;
/**

 This class provides methods to check if the Google Cloud SDK is installed and to install it if it is not.
 The installation process downloads the SDK installer from the Google Cloud SDK website and executes it.
 The installer URL is determined based on the operating system the application is running on.
 <p>
 To use this class, simply call the isCloudSdkInstalled() method to check if the SDK is already installed,
 and call the install() method to install it if necessary.
 </p>
 <p>
 Note that the installation process requires an internet connection and may take some time to complete.
 </p>
 @author ltseed
 @version 1.0
 */
public class CloudSDKInstaller {

    /**
     * 返回一个boolean值，指示当前系统上是否已安装Google Cloud SDK。
     * 通过执行gcloud version命令来检查是否已安装SDK，如果返回值为0则说明已安装，否则未安装。
     * 如果执行过程中出现异常（IOException或InterruptedException），则返回false。
     * */
    public static boolean isCloudSdkInstalled() {
        try {
            Process process = Runtime.getRuntime().exec("gcloud version".split(" "));
            process.waitFor();
            return process.exitValue() == 0;
        } catch (IOException | InterruptedException e) {
            return false;
        }
    }


    /**
     * 检查当前操作系统类型，根据操作系统类型选择相应的Google Cloud SDK安装文件的下载链接。
     * 如果找到了合适的下载链接，则创建一个HttpURLConnection对象，并使用GET请求获取下载链接的响应码。
     * 如果响应码为HTTP_OK，则创建一个临时文件，并使用FileOutputStream将下载内容写入该文件。
     * 接着根据操作系统类型执行相应的命令来安装Google Cloud SDK。如果操作系统是Windows，则使用cmd命令执行下载的可执行文件；否则，在Linux或macOS上，使用tar命令解压下载的文件并将其安装在用户目录下。
     * 最后，使用waitFor()方法等待进程执行完成。
     * 如果在任何步骤中出现异常，则会忽略异常并不执行任何操作。
     * */
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
                    int bytesRead;

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
                    process = Runtime.getRuntime().exec(commands,null, ChatInMC.tp.getDataFolder());
                    process.waitFor();
                } else {
                    throw new Exception("Failed to download Google Cloud SDK");
                }

            } catch (Exception ignored) {
            }
        }
    }
}
