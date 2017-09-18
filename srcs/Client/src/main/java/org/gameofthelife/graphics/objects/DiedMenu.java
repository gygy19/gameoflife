package org.gameofthelife.graphics.objects;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class DiedMenu extends JMenuBar implements MouseListener {

	/**
	 * VERSION
	 */
	private static final long serialVersionUID = 1L;
	
	JMenu		title				= new JMenu("Died");
	JMenu		button_zero			= new JMenu("0");
	JMenu		button_one			= new JMenu("1");
	JMenu		button_two			= new JMenu("2");
	JMenu		button_three		= new JMenu("3");
	JMenu		button_four			= new JMenu("4");
	JMenu		button_five			= new JMenu("5");
	JMenu		button_six			= new JMenu("6");
	JMenu		button_seven		= new JMenu("7");
	JMenu		button_height		= new JMenu("8");
	
	public DiedMenu() {
		super();
		this.add(title);
		this.add(button_zero);
		this.add(button_one);
		this.add(button_two);
		this.add(button_three);
		this.add(button_four);
		this.add(button_five);
		this.add(button_six);
		this.add(button_seven);
		this.add(button_height);
		button_three.setSelected(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
