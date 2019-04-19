package Pack;

import java.io.IOException;

public class Client {
	
	static Console con = new Console();
	static GUI swingGui = new GUI();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
//		con.test();
//		con.sendCommandToConsole("dir");
		
		swingGui.start(args);
		
	}
}

