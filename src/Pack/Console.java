package Pack;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import jdk.nashorn.tools.Shell;

public class Console {
	
	void callConsole() throws IOException {
		 Runtime.getRuntime().exec(new String[] {"cmd", "/K", "Start"}); 
		 
	}
	
	void callConsoleWithPingAndDir() throws IOException {
		   Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"dir && ping localhost\""); 
	}
	
	void test() throws IOException, InterruptedException {
		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		
		ProcessBuilder builder = new ProcessBuilder();
		if (isWindows) {
		    builder.command("cmd.exe", "/c", "dir");
		} else {
		    builder.command("sh", "-c", "ls");
		}
		builder.directory(new File(System.getProperty("user.home")));
		Process process = builder.start();
		StreamGobbler streamGobbler = 
		  new StreamGobbler(process.getInputStream(), System.out::println);
		Executors.newSingleThreadExecutor().submit(streamGobbler);
		int exitCode = process.waitFor();
		assert exitCode == 0;
	}
	
	private String sendShellCommand(String[] cmd){
		   System.out.println("\n###executing: "+ cmd[0]+ "###");
		   String AllText="Command Not Executed";   
		   try {
		      String line;
		      Process process = new ProcessBuilder(cmd).start();
		      BufferedReader STDOUT = new BufferedReader( new InputStreamReader(process.getInputStream()));
		      BufferedReader STDERR = new BufferedReader( new InputStreamReader(process.getErrorStream()));
		      while ((line = STDOUT.readLine()) != null) {
		          AllText=AllText+"\n"+line;
		          while ((line = STDERR.readLine()) != null) {
		              AllText=AllText+"\n"+line;
		          }   
		      }
		      System.out.println("Response from command \""+cmd[0]+"\":"+AllText);
		      return AllText;
		  } catch (IOException ex) {
		      Logger.getLogger(Shell.class.getName()).log(Level.SEVERE, null, ex);
		  }
		  return AllText;
		}
	
	void sendCommandToConsole(String command) {
		String Arr[] = {"cmd", "/c", "start", command};
		sendShellCommand(Arr);
	}
}

class StreamGobbler implements Runnable {
    private InputStream inputStream;
    private Consumer<String> consumer;
 
    public StreamGobbler(InputStream inputStream, Consumer<String> consumer) {
        this.inputStream = inputStream;
        this.consumer = consumer;
    }
 
    @Override
    public void run() {
        new BufferedReader(new InputStreamReader(inputStream)).lines()
          .forEach(consumer);
    }
}
