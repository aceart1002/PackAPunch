package Pack;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class GUI {

		static JTextField textfield1, textfield2, textfield3;
		static JButton openCmd, openCmdWithCommands, moreConsole, nonCom, anotherPopUp;
		
		
		 public static void start(String[] args) {
			  		  
			    JFrame f = new JFrame("Text Field Examples");
			    f.getContentPane().setLayout(new FlowLayout());
			    
			    textfield1 = new JTextField("Text field 1",10);
			    f.getContentPane().add(textfield1);
			    
			    openCmd = new JButton("open system's cmd");
			    f.getContentPane().add(openCmd);
			    openCmdWithCommands = new JButton("open and run commands");
			    f.getContentPane().add(openCmdWithCommands);
			    moreConsole = new JButton("some other program");
			    f.getContentPane().add(moreConsole);
			    nonCom = new JButton("pop up");
			    f.getContentPane().add(nonCom);
			    anotherPopUp = new JButton("another pop up");
			    f.getContentPane().add(anotherPopUp);
			    
			    ConsoleCaller call = new SimpleConsole();
			    openCmd.addActionListener(call);
			    openCmdWithCommands.addActionListener(new ConsoleWithCommands());
			    moreConsole.addActionListener(new MoreConsole());
			    nonCom.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						 JOptionPane.showMessageDialog(f, "Massage", "TITEL",1);
						
					}
				}
			    );
			    anotherPopUp.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						 JOptionPane.showMessageDialog(f, "See the massage", "SAMPLE TEXT",3);
						
					}
				}
			    );
			    
			    f.setPreferredSize(new Dimension(300,400));
			    
			    f.pack();
			    f.setVisible(true);
	}	 
		 
}

abstract class ConsoleCaller implements ActionListener {
	static Console console = new Console();
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			callConsole();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	abstract void callConsole() throws IOException;
}

class SimpleConsole extends ConsoleCaller {
	void callConsole() throws IOException {
		console.callConsole();
	}
}

class ConsoleWithCommands extends ConsoleCaller {

	@Override
	void callConsole() throws IOException {
		console.callConsoleWithPingAndDir();
	}
	
}

class MoreConsole extends ConsoleCaller {

	@Override
	void callConsole() throws IOException {
		console.sendCommandToConsole("pause");
	}
	
}
