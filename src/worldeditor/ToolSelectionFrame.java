package worldeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class ToolSelectionFrame {

	WorldEditor editor;
	JComboBox toolCombo;
	String[] tools = { "Set Floor Type", "Add Game Object" };
	String[] floorTypes = { "grass", "water", "path", "sand" };
	String[] gameObjects = {"tree", "wall"};

	public ToolSelectionFrame(WorldEditor editor) {
		this.editor = editor;
		setupFloorPanel();
		
	}

	private void setupFloorPanel() {
		JFrame floorFrame = new JFrame();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 200));
		panel.add(new JLabel("Select Tool:"));
		JRadioButton setFloorButton = new JRadioButton("Set Floor Type");
		setFloorButton.setSelected(true);
		setFloorButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setTool("Set Floor Type");
			}
			
		});
		JRadioButton addObjectButton = new JRadioButton("Add Game Object");
		addObjectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setTool("Add Game Object");
				setupObjectPanel();
			}
			
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(setFloorButton);
		bg.add(addObjectButton);
		panel.add(setFloorButton);
		panel.add(addObjectButton);
		panel.add(Box.createRigidArea(new Dimension(400, 10)));
		panel.add(new JLabel("Select Floor Type:"));
		System.out.println("FLOOR TYPE");
		JComboBox floorCombo = new JComboBox(floorTypes);
		floorCombo.setPreferredSize(new Dimension(150, 24));
		floorCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setFloorType(floorTypes[floorCombo.getSelectedIndex()]);
			}
			
		});
		panel.add(floorCombo);

		panel.repaint();
		floorFrame.add(panel);
		floorFrame.pack();
		floorFrame.setVisible(true);
		floorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editor.setFloorType(floorTypes[floorCombo.getSelectedIndex()]);
		editor.setTool("Set Floor Type");
	}
	
	private void setupObjectPanel() {
		JFrame objectFrame = new JFrame();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 200));
		panel.add(new JLabel("Select Tool:"));
		JRadioButton setFloorButton = new JRadioButton("Set Floor Type");
		setFloorButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setTool("Set Floor Type");
				setupFloorPanel();
			}
			
		});
		JRadioButton addObjectButton = new JRadioButton("Add Game Object");
		addObjectButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setTool("Add Game Object");
			}
			
		});
		ButtonGroup bg = new ButtonGroup();
		bg.add(setFloorButton);
		bg.add(addObjectButton);
		panel.add(setFloorButton);
		panel.add(addObjectButton);
		panel.add(Box.createRigidArea(new Dimension(400, 10)));
		panel.add(new JLabel("Select Floor Type:"));
		System.out.println("FLOOR TYPE");
		System.out.println(gameObjects[0]);
		panel.add(new JLabel("Select Object Type:"));
		JComboBox objectCombo = new JComboBox(gameObjects);
		objectCombo.setPreferredSize(new Dimension(150, 24));
		objectCombo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setObjectType(gameObjects[objectCombo.getSelectedIndex()]);
			}
		});
		System.out.println(objectCombo.getItemAt(0));
		panel.add(objectCombo);
		System.out.println(objectCombo);

		panel.repaint();
		objectFrame.add(panel);
		objectFrame.pack();
		objectFrame.setVisible(true);
		objectFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		editor.setObjectType(gameObjects[objectCombo.getSelectedIndex()]);
	}

}
