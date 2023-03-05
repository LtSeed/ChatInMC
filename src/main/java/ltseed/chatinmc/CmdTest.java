package ltseed.chatinmc;

import java.io.IOException;

public class CmdTest {
    public static void test() {
        try {
            // Check if wget and tar are installed
            if (!isCommandInstalled("wget") || !isCommandInstalled("tar")) {
                // Determine OS
                String osName = System.getProperty("os.name").toLowerCase();
                String downloadUrl;
                String command;

                // Download and install wget and tar based on OS
                if (osName.contains("windows")) {
                    // Windows
                    downloadUrl = "https://eternallybored.org/misc/wget/1.20.3/64/wget.exe";
                    command = "powershell -Command \"& { (New-Object Net.WebClient).DownloadFile('" + downloadUrl + "', 'wget.exe') }\"";
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar.exe";
                    command = "powershell -Command \"& { (New-Object Net.WebClient).DownloadFile('" + downloadUrl + "', 'tar.exe') }\"";
                    Runtime.getRuntime().exec(command).waitFor();
                } else if (osName.contains("mac")) {
                    // macOS
                    downloadUrl = "https://ftp.gnu.org/gnu/wget/wget-1.21.2.tar.gz";
                    command = "curl -O " + downloadUrl;
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "tar -xzvf wget-1.21.2.tar.gz";
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "cd wget-1.21.2 && ./configure && make && sudo make install";
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar_darwin_amd64";
                    command = "curl -Lo tar " + downloadUrl;
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "chmod +x tar";
                    Runtime.getRuntime().exec(command).waitFor();
                } else {
                    // Linux
                    downloadUrl = "https://ftp.gnu.org/gnu/wget/wget-1.21.2.tar.gz";
                    command = "wget " + downloadUrl;
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "tar -xzvf wget-1.21.2.tar.gz";
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "cd wget-1.21.2 && ./configure && make && sudo make install";
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar_linux_amd64";
                    command = "wget " + downloadUrl;
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "chmod +x ar_linux_amd64";
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "sudo mv ar_linux_amd64 /usr/local/bin/tar";
                    Runtime.getRuntime().exec(command).waitFor();
                }
            }

            // Print message
            System.out.println("wget and tar are installed.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isCommandInstalled(String command) {
        try {
            Process process = Runtime.getRuntime().exec("which " + command);
            process.waitFor();
            return process.exitValue()==0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
