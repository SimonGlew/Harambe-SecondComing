package worldeditor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ToolSelectionFrame extends JFrame {
	
	WorldEditor editor;
	JPanel panel = new JPanel();
	JComboBox floorTypeSelect;
	String[] floorTypes = {"grass", "water", "path", "sand"};
	
	public ToolSelectionFrame(WorldEditor editor){
		this.editor = editor;
		setupPanel();
		add(panel);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void setupPanel() {
		panel.setPreferredSize(new Dimension(400, 200));
		panel.add(new JLabel("Floor Type"));
		floorTypeSelect = new JComboBox(floorTypes);
		floorTypeSelect.setPreferredSize(new Dimension(150, 24));
		floorTypeSelect.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				editor.setFloorType(floorTypes[floorTypeSelect.getSelectedIndex()]);
			}
			
		});
		panel.add(floorTypeSelect);
	}
}
