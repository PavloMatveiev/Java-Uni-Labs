package gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import monster.Monster;
import monster.MonsterException;
import trainer.Trade;
import trainer.Trainer;

// Suppress an annoying and (in this case) irrelevant Eclipse compiler warning
@SuppressWarnings("serial")
public class TrainerFrame extends JFrame implements ActionListener {
	// The buttons
	private JButton tradeButton;
	private JButton add1Button, delete1Button;
	private JButton add2Button, delete2Button;
	
	// The two trainers being presented
	private Trainer trainer1, trainer2;
	
	// The two lists of monsters displayed
	private DefaultListModel<Monster> trainer1Model, trainer2Model;
	private JList<Monster> trainer1List, trainer2List;
	
	public TrainerFrame(Trainer trainer1, Trainer trainer2) {
		// Basic window features
		super("Trading");
		setLocationByPlatform(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Create the two JLists and their corresponding list models
		this.trainer1 = trainer1;
		trainer1Model = new DefaultListModel<>();
		trainer1Model.addAll(trainer1.getMonsters());
		trainer1List = new JList<>(trainer1Model);
		
		this.trainer2 = trainer2;
		trainer2Model = new DefaultListModel<>();
		trainer2Model.addAll(trainer2.getMonsters());
		trainer2List = new JList<>(trainer2Model);

		// Add titled scrolling pane to hold the lists, and add them to the layout
		JScrollPane trainer1Scroll = new JScrollPane(trainer1List);
		trainer1Scroll.setBorder(new TitledBorder(trainer1.getName() + "'s Monsters"));
		JScrollPane trainer2Scroll = new JScrollPane(trainer2List);
		trainer2Scroll.setBorder(new TitledBorder(trainer2.getName() + "'s Monsters"));

		// Add the lists to the layout
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new GridLayout(1, 2));
		listPanel.add(trainer1Scroll);
		listPanel.add(trainer2Scroll);
		
		// Create the trade button
		tradeButton = new JButton("Trade");
		tradeButton.addActionListener(this);
		
		add1Button = new JButton("Add");
		add1Button.addActionListener(this);
		add2Button = new JButton("Add");
		add2Button.addActionListener(this);
		delete1Button = new JButton("Delete");
		delete1Button.addActionListener(this);
		delete2Button = new JButton("Delete");
		delete2Button.addActionListener(this);
		
		// Lay out the buttons
		Box buttonBox = Box.createHorizontalBox();
		buttonBox.add(add1Button);
		buttonBox.add(delete1Button);
		buttonBox.add(Box.createGlue());
		buttonBox.add(tradeButton);
		buttonBox.add(Box.createGlue());
		buttonBox.add(add2Button);
		buttonBox.add(delete2Button);
		
		getContentPane().setLayout(new BorderLayout()); 
		getContentPane().add(listPanel);
		getContentPane().add(BorderLayout.SOUTH, buttonBox);
		pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == tradeButton) {
			Monster m1 = trainer1List.getSelectedValue();
			Monster m2 = trainer2List.getSelectedValue();
			if (m1 != null && m2 != null) {
				// Do the trade and update the lists
				try {
					new Trade(trainer1, m1, trainer2, m2).doTrade();
					trainer1Model.removeElement(m1);
					trainer2Model.removeElement(m2);
					trainer1Model.addElement(m2);
					trainer2Model.addElement(m1);
				} catch (MonsterException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == add1Button) {
			new AddMonsterDialog(this, trainer1, trainer1Model).setVisible(true);
			
		} else if (e.getSource() == add2Button) {
			new AddMonsterDialog(this, trainer2, trainer2Model).setVisible(true);
			
		} else if (e.getSource() == delete1Button) {
			Monster m1 = trainer1List.getSelectedValue();
			if (m1 != null) {
				trainer1.removeMonster(m1);
				trainer1Model.removeElement(m1);
			}
			
		} else if (e.getSource() == delete2Button) {
			Monster m2 = trainer2List.getSelectedValue();
			if (m2 != null) {
				trainer2.removeMonster(m2);
				trainer2Model.removeElement(m2);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		Trainer trainer1 = Trainer.loadFromFile("brendan.txt");
		Trainer trainer2 = Trainer.loadFromFile("ethan.txt");

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new TrainerFrame(trainer1, trainer2).setVisible(true);
			}
		});
	}

}