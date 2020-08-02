package com.paypal.dictionary;

/**
* Implementing dictionary
*
* @author  Antony Sibiya J
* @version 1.0
*/
import java.util.Scanner;

class DictionaryNode {
	static final int ALPHA_LEN = 26;
	DictionaryNode[] children = new DictionaryNode[ALPHA_LEN];
	private boolean eow;
	private String meaning = "";

	public DictionaryNode[] getChildren() {
		return children;
	}

	public void setChildren(DictionaryNode[] children) {
		this.children = children;
	}

	public boolean isEow() {
		return eow;
	}

	public void setEow(boolean eow) {
		this.eow = eow;
	}

	public String getMeaning() {
		return meaning;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	DictionaryNode() {
		setEow(false);
		setMeaning("");
		for (int i = 0; i < ALPHA_LEN; i++)
			children[i] = null;
	}
}

public class EnglishDictionary {
	DictionaryNode root;
	static final int ALPHA_LEN = 26;

	EnglishDictionary() {
		root = new DictionaryNode();
	}

	/**
	 * Method to do CREATE in CRUD
	 * 
	 * @param key
	 * @param meaning
	 */
	void insert(String key, String meaning) {
		key = key.toLowerCase();
		int index;
		DictionaryNode dictionaryNode = root;
		for (int level = 0; level < key.length(); level++) {
			index = key.charAt(level) - 'a';
			if (dictionaryNode.getChildren()[index] == null)
				dictionaryNode.getChildren()[index] = new DictionaryNode();

			dictionaryNode = dictionaryNode.getChildren()[index];
		}

		dictionaryNode.setEow(true);
		dictionaryNode.setMeaning(meaning);
	}

	boolean isEmpty(DictionaryNode dicNode) {
		for (int i = 0; i < ALPHA_LEN; i++)
			if (dicNode.children[i] != null)
				return false;
		return true;
	}

	private boolean remove(DictionaryNode dicNode, String word, int depth) {
		if (depth == word.length()) {
			if (!dicNode.isEow()) {
				return false;
			}
			dicNode.setEow(false);
			return isEmpty(dicNode);
		}
		int index = word.charAt(depth) - 'a';
		DictionaryNode node = dicNode.getChildren()[index];
		if (node == null) {
			return false;
		}
		boolean shouldDeleteCurrentNode = remove(node, word, depth + 1) && !node.isEow();

		if (shouldDeleteCurrentNode) {
			dicNode.children[index] = null;
			return isEmpty(dicNode);
		}
		return false;
	}

	/**
	 * Method to DELETE in CRUD
	 * 
	 * @param key
	 * @return
	 */
	boolean remove(String key) {
		key = key.toLowerCase();
		return remove(root, key, 0);
	}

	/**
	 * Method to READ in CRUD
	 * 
	 * @param key
	 * @return
	 */
	String search(String key) {
		key = key.toLowerCase();
		int index;
		DictionaryNode dictionaryNode = root;

		for (int level = 0; level < key.length(); level++) {
			index = key.charAt(level) - 'a';

			if (dictionaryNode.children[index] == null)
				return "Not Found in dictionary !!!!!!!!!!!!!!!!!";

			dictionaryNode = dictionaryNode.children[index];
		}

		return (dictionaryNode != null && dictionaryNode.isEow() ? dictionaryNode.getMeaning() : "");
	}

	/**
	 * Method to UPDATE in CRUD
	 * 
	 * @param key
	 * @param meaning
	 */
	String update(String key, String meaning) {
		key = key.toLowerCase();
		int index;
		DictionaryNode dictionaryNode = root;

		for (int level = 0; level < key.length(); level++) {
			index = key.charAt(level) - 'a';

			if (dictionaryNode.children[index] == null)
				return "No Data Found to Update";

			dictionaryNode = dictionaryNode.children[index];
		}

		if (dictionaryNode.isEow()) {
			dictionaryNode.setMeaning(meaning);
			return "Updated Successfully";
		}
		return "No Data Found to Update";
	}

	// Testing
	public static void main(String[] args) {
		EnglishDictionary dictionary = new EnglishDictionary();
		String[] keys = { "APPLE", "BALL", "CAT", "DOG" };
		String[] meaningArr = { "A fruit", "A Playing Object", "A Pet Animal", "A Pet Animal which guards the house" };
		for (int i = 0; i < keys.length; i++)
			dictionary.insert(keys[i], meaningArr[i]);

		Scanner in = new Scanner(System.in);
		System.out.println("Enter dictionary word and meaning to insert : ");
		String word = in.nextLine();
		String meaning = in.nextLine();
		dictionary.insert(word, meaning);

		System.out.println("Enter  word to get the meaning : ");
		word = in.nextLine();
		System.out.println("Meaning of the word " + word + " is: " + dictionary.search(word));
		System.out.print("Enter  word to update meaning: ");
		word = in.nextLine();
		meaning = in.nextLine();
		System.out.println("Updating the word " + word + " : " + dictionary.update(word, meaning));
		System.out.println("Enter  word to delete: ");
		word = in.nextLine();
		dictionary.remove(word);
		System.out.println("Deleted Now ,Meaning of the word " + word + " is: " + dictionary.search(word));
		in.close();

	}

}
