package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import monster.Attack;
import monster.Monster;
import trainer.Trainer;

@SuppressWarnings("serial")
public class AddMonsterDialog extends JDialog implements ActionListener {
	private JComboBox<String> typeCombo = new JComboBox<>();
	private JTextField attack1Field, attack2Field;
	private JSpinner attack1Spinner, attack2Spinner, hitPointSpinner;

	private Trainer trainer;
	private DefaultListModel<Monster> model;

	// Buttons
	private JButton okButton;
	private JButton cancelButton;

	public AddMonsterDialog(JFrame frame, Trainer trainer, DefaultListModel<Monster> model) {
		super(frame, "Add Monster (" + trainer.getName() + ")", true);
		setLocationRelativeTo(frame);

		this.trainer = trainer;
		this.model = model;

		typeCombo = new JComboBox<>(new String[] { "Water", "Fire", "Electric" });
		attack1Field = new JTextField(20);
		attack2Field = new JTextField(20);

		attack1Spinner = new JSpinner(new SpinnerNumberModel(100, 0, 300, 10));
		attack2Spinner = new JSpinner(new SpinnerNumberModel(100, 0, 300, 10));
		hitPointSpinner = new JSpinner(new SpinnerNumberModel(100, 0, 300, 10));

		// Layout the fields
		Box fieldBox = Box.createVerticalBox();

		Box typeBox = Box.createHorizontalBox();
		typeBox.add(new JLabel("Type"));
		typeBox.add(typeCombo);
		fieldBox.add(typeBox);

		Box hitPointBox = Box.createHorizontalBox();
		hitPointBox.add(new JLabel("Hit points"));
		hitPointBox.add(hitPointSpinner);
		fieldBox.add(hitPointBox);

		Box attack1Box = Box.createVerticalBox();
		attack1Box.setBorder(new TitledBorder("Attack 1"));
		Box name1Box = Box.createHorizontalBox();
		name1Box.add(new JLabel("Attack name"));
		name1Box.add(attack1Field);
		attack1Box.add(name1Box);
		Box points1Box = Box.createHorizontalBox();
		points1Box.add(new JLabel("Hit points"));
		points1Box.add(attack1Spinner);
		attack1Box.add(points1Box);
		fieldBox.add(attack1Box);

		Box attack2Box = Box.createVerticalBox();
		attack2Box.setBorder(new TitledBorder("Attack 2"));
		Box name2Box = Box.createHorizontalBox();
		name2Box.add(new JLabel("Attack name"));
		name2Box.add(attack2Field);
		attack2Box.add(name2Box);
		Box points2Box = Box.createHorizontalBox();
		points2Box.add(new JLabel("Hit points"));
		points2Box.add(attack2Spinner);
		attack2Box.add(points2Box);
		fieldBox.add(attack2Box);

		// Create buttons and add the current class as an ActionListener
		okButton = new JButton("OK");
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);

		// Bottom row of GUI: buttons
		Box bottomBox = Box.createHorizontalBox();
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(okButton);
		bottomBox.add(Box.createHorizontalGlue());
		bottomBox.add(cancelButton);
		bottomBox.add(Box.createHorizontalGlue());

		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		add(fieldBox);
		getContentPane().add(Box.createVerticalStrut(10));
		getContentPane().add(bottomBox);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelButton) {
			// Just close the window
			dispose();

		} else if (e.getSource() == okButton) {
			// Get all field values
			String type = (String)typeCombo.getSelectedItem();
			int hp = (Integer)hitPointSpinner.getValue();
			Attack a1 = new Attack(attack1Field.getText(), (Integer)attack1Spinner.getValue());
			Attack a2 = new Attack(attack2Field.getText(), (Integer)attack2Spinner.getValue());
			
			Monster m = Trainer.createMonster(type, hp, new Attack[] { a1, a2 });
			trainer.addMonster(m);
			model.addElement(m);

			// Get rid of the dialog box
			dispose();
		}
	}

}
