import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

class TrieNode {
	char letter; // to store the letter
	TrieNode[] links; // array of 26 Nodes
	boolean fullword; // check for word
	public TrieNode parent; // storing parent letter
	boolean visited;

	TrieNode(char letter, boolean fullword, TrieNode p, boolean visit) {
		this.letter = letter;
		links = new TrieNode[26];
		this.fullword = fullword;
		this.parent = p;
		this.visited = false;
	}
}

class Trie {

	ArrayList<String> al = new ArrayList<String>();

	TrieNode root = new TrieNode(' ', false, null, false);

	void construct_trie(String s) {
		TrieNode temp = root; // copy root node
		TrieNode check;
		TrieNode par = root; // initially parent is root

		for (int i = 0; i < s.length(); i++) // loop over each character in word
		{
			int t = s.charAt(i); // integer value of character

			t = t - 97; // calculate appropriate index for array

			while ((check = temp.links[t]) != null) // check if root is null..
			{
				temp = temp.links[t]; // go forward
				t = s.charAt(++i);
				t = t - 97;
				par = temp; // update parent
			}

			if (i != s.length() - 1) // if not end of word..pass false
			{
				temp.links[t] = new TrieNode((char) (t + 97), false, par, false);
				par = temp.links[t];
			} else // if end of word..pass true to full word
			{
				temp.links[t] = new TrieNode((char) (t + 97), true, par, false);
				par = temp.links[t];
			}

			temp = temp.links[t]; // update temp..

		}
	}

	public TrieNode n = null;

	void read_trie(String find) // pass the intial string
	{
		int len = find.length();
		int i = 0;

		TrieNode temp = root; // copy root

		while (i != len) // go until the string matches
		{
			int t = find.charAt(i);
			t = t - 97;

			temp = temp.links[t];

			i++;

		}

		print_all(temp, Character.toString(temp.parent.letter)); // pass the
																	// node

	}

	public String word = "";
	boolean new_word = false;
	boolean print_letter = false;
	boolean first_letter = false;

	void dfs(TrieNode t) {
		Stack<TrieNode> s = new Stack<TrieNode>();
		s.add(t);

		while (!s.isEmpty()) {
			TrieNode temp = s.peek();

			TrieNode v = getAdjacent(temp);

			if (v == null)
				s.pop();
			else {

				System.out.print(v.letter);
				if (v.fullword) {
					System.out.println();

				}
				s.add(v);
			}
		}

	}

	TrieNode getAdjacent(TrieNode temp) {
		for (int i = 0; i < 26; i++) {
			if (temp.links[i] != null) {

				if (temp.links[i].visited == false) {
					temp.links[i].visited = true;
					return temp.links[i];
				}
			}
		}
		return null;
	}

	void print_all(TrieNode t, String parent)
	// from here we have to recursively print all nodes if they are not null
	{

		if (t == null) // base condition
		{
			return;
		}

		parent += t.letter; // concat parent with child
		if (t.fullword) {
			// only print when you reach a full word
			al.add(parent.trim());
			System.out.println(parent);
		}

		for (int i = 0; i < 26; i++) {
			// Try each child, pre-pending the parent's string
			print_all(t.links[i], parent);
		}
	}
}

class Tries {
	static ArrayList<String> f = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		f.clear();
		Trie tt = new Trie();

		File fp = new File("cities.txt");
		FileReader fr = new FileReader(fp);
		BufferedReader br = new BufferedReader(fr);

		String temp = "";

		ArrayList<String> fh = new ArrayList<String>();

		while ((temp = br.readLine()) != null) {
			tt.construct_trie(temp.trim());
			fh.add(temp.trim());

		}

		tt.read_trie(args[0]);

		System.out.println(args[0]);

		if (tt.al.size() == 1) {
			tt.al.clear();

			for (int i = 0; i < fh.size(); i++) {
				if (fh.get(i).startsWith(args[0])) {
					// System.out.println(fh.get(i));
					tt.al.add(fh.get(i));
				}
			}
		}

		for (int i = 0; i < tt.al.size(); i++) {
			f.add(tt.al.get(i));
		}

	}
}