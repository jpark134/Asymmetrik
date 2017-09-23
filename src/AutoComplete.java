import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*; 

/**
 * John Park
 * 9/23/17
 * 
 * This program is designed to be trained on input sentences and then output what a list of possible auto-completions
 * of a word fragment.
 */
public class AutoComplete extends Frame implements ActionListener{
	/* A hash map is used to store words and the amount of times they pop up in training sentences.  
	 * This is because a hash map has an average case of O(1) when adding and searching for keys, which is mainly what
	 * we will be doing with it.
	 */
	Map m;
	TernaryTree t;
	Label train,out;
	Button trainB, wordB;
	TextArea trainA,outA;
	TextField wordT;
	
	//Constructor for AutoComplete that also sets up some GUI to look nice
	AutoComplete(){

		// shuts down the program if the user closes the window
		addWindowListener(new WindowAdapter(){  
			public void windowClosing(WindowEvent e) {  
				dispose();
				System.exit(0);
			}  
		});
		t = new TernaryTree();
		m = new HashMap<String, Integer>();
		trainB = new Button("Train");
		trainB.setBounds(50, 430, 100, 50);
		wordB = new Button("Get Words");
		wordB.setBounds(200, 430, 100, 50);
		wordB.addActionListener(this);
		trainB.addActionListener(this);
		train = new Label("Training Sentence:");
		train.setBounds(10, 20, 200, 30);
		out = new Label("Word Completion:");
		out.setBounds(10,300,125,30);
		wordT = new TextField();
		wordT.setBounds(135,300,200,30);
		trainA = new TextArea("",3,100,TextArea.SCROLLBARS_VERTICAL_ONLY);
		trainA.setBounds(10,60,450,220);
		outA = new TextArea();
		outA.setEditable(false);
		outA.setBounds(10,340,450,70);
		add(train);add(out);add(trainA);add(outA);add(wordT);add(trainB);add(wordB);
		setSize(500,500);
		setLayout(null);  
		setVisible(true);
	}

	public static void main(String[] args){
		new AutoComplete();
	}

	/*
	 * Depending on which button is pressed, the text is either trained into the system or the possible word
	 * completions are given
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String traintemp = trainA.getText();
		//Removes punctuation such as periods from the text
		String train = traintemp.replaceAll("\\p{P}", "").toLowerCase();
		String word = wordT.getText().toLowerCase();
		if (e.getSource() == trainB){
			String[] temp = train.split(" ");
			for (int i=0; i<temp.length; i++){
				t.add(temp[i]);
				if (m.containsKey(temp[i])){
					m.replace(temp[i], (int)m.get(temp[i])+1);
				} else {
					m.put(temp[i], 1);
				}
			}
			trainA.setText("");
		} else if (e.getSource() == wordB){
			ArrayList temp = (ArrayList) t.completion(word);
			String fin = "";
			for (int i=0; i<temp.size(); i++){
				fin = fin+temp.get(i)+"("+m.get(temp.get(i))+"), ";
			}
			outA.setText(fin);
		}

	}
}
