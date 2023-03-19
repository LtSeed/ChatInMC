package ltseed.chatinmc.Utils;

import ltseed.chatinmc.ChatInMC;

import java.io.IOException;

/**
 * The {@code CmdTest} class provides a method for testing if the {@code wget} and {@code tar} commands are installed
 * on the system and, if not, installs them. The methods are static and no instances of this class should be created.
 *
 * <p>Usage:
 * <pre>{@code
 * CmdTest.test();
 * }</pre>
 *  @author ltseed
 *  @version 1.0
 */
public class CmdTest {
    /**
     * Tests if the {@code wget} and {@code tar} commands are installed and installs them if not. The method detects the
     * operating system and installs the appropriate version of the commands.
     *
     * <p>Note: this method does not return a value, but logs messages to the console using the {@link ChatInMC#debug}
     * class.
     */
    public static void test() {
        try {
            // Check if wget and tar are installed
            if (isCommandInstalled("wget") || isCommandInstalled("tar")) {
                // Determine OS
                String osName = System.getProperty("os.name").toLowerCase();
                String downloadUrl;
                String[] command;

                // Download and install wget and tar based on OS
                if (osName.contains("windows")) {
                    // Windows
                    downloadUrl = "https://eternallybored.org/misc/wget/1.20.3/64/wget.exe";
                    command = new String[] {"powershell", "-Command", "&", "{", "(New-Object", "Net.WebClient).DownloadFile('" + downloadUrl + "', 'wget.exe')", "}"};
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar.exe";
                    command = new String[] {"powershell", "-Command", "&", "{", "(New-Object", "Net.WebClient).DownloadFile('" + downloadUrl + "', 'tar.exe')", "}"};
                    Runtime.getRuntime().exec(command).waitFor();
                } else if (osName.contains("mac")) {
                    // macOS
                    downloadUrl = "https://ftp.gnu.org/gnu/wget/wget-1.21.2.tar.gz";
                    command = new String[] {"curl", "-O", downloadUrl};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = new String[] {"tar", "-xzvf", "wget-1.21.2.tar.gz"};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = new String[] {"cd", "wget-1.21.2", "&&", "./configure", "&&", "make", "&&", "sudo", "make", "install"};
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar_darwin_amd64";
                    command = new String[] {"curl", "-Lo", "tar", downloadUrl};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = new String[] {"chmod", "+x", "tar"};
                    Runtime.getRuntime().exec(command).waitFor();
                } else {
                    // Linux
                    downloadUrl = "https://ftp.gnu.org/gnu/wget/wget-1.21.2.tar.gz";
                    command = new String[]{"wget", downloadUrl};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = new String[]{"tar", "-xzvf", "wget-1.21.2.tar.gz"};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "cd wget-1.21.2 && ./configure && make && sudo make install".split(" ");
                    Runtime.getRuntime().exec(command).waitFor();

                    downloadUrl = "https://github.com/mholt/archiver/releases/download/v3.5.1/ar_linux_amd64";
                    command = new String[]{"wget", downloadUrl};
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "chmod +x ar_linux_amd64".split(" ");
                    Runtime.getRuntime().exec(command).waitFor();

                    command = "sudo mv ar_linux_amd64 /usr/local/bin/tar".split(" ");
                    Runtime.getRuntime().exec(command).waitFor();
                }
            }

            // Print message
            ChatInMC.debug.info("wget and tar are installed.");
        } catch (IOException | InterruptedException e) {
            ChatInMC.debug.err("wget and tar are not installed!");
        }
    }

    /**
     Checks if a given command is installed on the system.
     @param command the name of the command to check
     @return true if the command is installed, false otherwise
     */
    private static boolean isCommandInstalled(String command) {
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"which", command});
            process.waitFor();
            return process.exitValue() != 0;
        } catch (IOException | InterruptedException e) {
            return true;
        }
    }

}
