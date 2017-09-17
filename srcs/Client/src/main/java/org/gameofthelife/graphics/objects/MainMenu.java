package org.gameofthelife.graphics.objects;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.gameofthelife.client.Main;

/**
 * @author jguyet
 * Menu start
 */
public class MainMenu extends JPanel implements ActionListener {

	/**
	 * VERSION
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel		lbl_host = new JLabel("Server ip address");
	private JTextField 	hostField = new JTextField("" + Main.hostname, 15);
	private JLabel		lbl_port = new JLabel("Server port");
	private JTextField 	portField = new JTextField("" + Main.port, 4);
	private JLabel		lbl_error = new JLabel("");
	private Window		win;
	
	public boolean		wait = true;
	
	public MainMenu() {
		super(new GridBagLayout());
		initialize();
	}
	
	public MainMenu(String error) {
		super(new GridBagLayout());
		initialize();
		lbl_error.setText(error);
	}
	
	private void initialize() {
		GridBagConstraints c = new GridBagConstraints();
		
		hostField.addActionListener(this);
		portField.addActionListener(this);
		lbl_error.setForeground(Color.RED);
		c.gridwidth = GridBagConstraints.REMAINDER;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lbl_host, c);
        add(hostField, c);
        add(lbl_port, c);
        add(portField, c);
        add(lbl_error);
        this.win = new Window(400, 150);
		this.win.add(this);
        this.win.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		
		try {
			InetAddress.getByName(this.hostField.getText());
		} catch (UnknownHostException e) {
			lbl_error.setText("Server host ip not resolvable.");
			return ;
		}
		int port = 0;
		
		try {
			port = Integer.parseInt(portField.getText());
		} catch (NumberFormatException e) {
			
			lbl_error.setText("Port is a number.");
			return ;
		}
		if (port < 0) {
			lbl_error.setText("Port must be > 0.");
			return ;
		}
		
		if (port > 65535) {
			lbl_error.setText("Port must be <= 65535.");
			return ;
		}
		Main.hostname = this.hostField.getText();
		Main.port = port;
		wait = false;
		
		this.win.setVisible(false);
	}
}
