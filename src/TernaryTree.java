import java.util.ArrayList;
import java.util.List;
/**
 * In this program we will be implementing a Ternary Tree that will allow us to incrementally search through strings
 * @author John Park
 * 9/23/17
 */


/* The node that we will use to store into the tree*/
class TernNode{
	char val;
	boolean reachEnd;
	TernNode left, middle, right;
	public TernNode(char d) {
		this.val = d;
		this.reachEnd = false;
		this.left = null;
		this.middle = null;
		this.right = null;
	}
}

/* This is the class that will store the words we put in them as characters so we can incrementally search them */
class TernaryTree {
	private TernNode root;
	private ArrayList<String> storedArray;

	/* Ternary Tree constructor */
	public TernaryTree(){
		root = null;
	}
	
	/* Adds the given word to the ternary tree */
	public void add(String word){
		root = addToTree(root, word.toCharArray(), 0);
	}

	/* Adds a character to the tree and marks reachEnd if the character is the end of the inputted string */
	public TernNode addToTree(TernNode r, char[] word, int place){
		if (r == null)
			r = new TernNode(word[place]);
		if (word[place] < r.val)
			r.left = addToTree(r.left, word, place);
		else if (word[place] > r.val)
			r.right = addToTree(r.right, word, place);
		else
		{
			if (place + 1 < word.length)
				r.middle = addToTree(r.middle, word, place + 1);
			else
				r.reachEnd = true;
		}
		return r;
	}
	
	/* Returns the array of the possible autocompletions */
	public ArrayList getAl(){
		return storedArray;
	}
	
	/* Find where the prefix ends in the tree and move on from there */
	public List completion(String s){
		char [] match2=s.toCharArray();
		storedArray = new ArrayList<String>();
		int pos = 0;
		TernNode node = root;
		while (node != null){
			if (match2[pos] != node.val){
				if (match2[pos] < node.val){
					node = node.left;
				}
				else{
					node = node.right;
				}
			}
			else{
				if (++pos == s.length()){
					if (node.reachEnd == true){
						storedArray.add(s);
					}
					newPossibles(s, node.middle);
					return (storedArray);
				}
				node = node.middle;
			}
		}
		return storedArray;
	}
	
	/* Now that prefix is found, we search for more words that have this prefix */
	private void newPossibles(String p, TernNode c){
		if (c != null){
			if (c.reachEnd == true){
				storedArray.add(p + c.val);
			}

			newPossibles(p, c.left);
			newPossibles(p + c.val, c.middle);
			newPossibles(p, c.right);	
		}
	}


}