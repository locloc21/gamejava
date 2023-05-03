package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		//tao khung
		JFrame obj = new JFrame();
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(50,50,700,600); //vi tri Frame
		obj.setTitle("Breakout Ball");
		obj.setResizable(false); //thay doi kich thuoc hay ko
		obj.setVisible(true); //hien thi
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gamePlay);
	}

}
