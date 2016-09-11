import java.util.*;

public class Main {
	
	public static void main(String[] args) {
		ResistanceGame newGame = new ResistanceGame();
		newGame.startGame(5);
	}
	
}

class ResistanceGame {
	
	public static ArrayList<Boolean> allQuestVotes = new ArrayList<Boolean>();
	public static ArrayList<CharacterCard> allCharacterCircle = new ArrayList<CharacterCard>();
	private ArrayList<CharacterCard> allCharacterCircleModified = new ArrayList<CharacterCard>();
	private int[][] identitySplit = {{5,3,2},{6,4,2},{7,4,3},{8,5,3},{9,6,3},{10,6,4}};
	public static int[][] questMemberSplit = {{2,3,2,3,3},{2,3,4,3,4},{2,3,3,4,4},{3,4,4,5,5},{3,4,4,5,5},{3,4,4,5,5}};
	public static ArrayList<CharacterCard> questMembers = new ArrayList<CharacterCard>();
	public static int numberOfCharacters;
	public static int numberOfGoodCharacters;
	public static int numberOfEvilCharacters;
	private boolean questFate;
	
	public static int getNumberOfCharacters() {
		return numberOfCharacters;
	}
	
	public void startGame(int numberOfCharactersValue) {
		Player player1 = new Player();
		player1.setCharacterType();
		createCharacters(numberOfCharactersValue);
		for (CharacterCard character : allCharacterCircle) {
			character.setQuestVote();
			System.out.println(character.getCharacterType() + " : " + character.getQuestVote());
		}
		setQuestFate();
		System.out.println(getQuestFate());
		player1.chooseQuestMembers(3);
	}
	
	public void setQuestFate() {
		questFate = true;
		for (boolean eachVote : allQuestVotes) {
			if (eachVote == false) {
				questFate = false;
				break;
			}
		}
		allQuestVotes.clear();
		questMembers.clear();
	}
	
	public boolean getQuestFate() {
		return questFate;
	}
	
	public static void characterSplit() {
		numberOfGoodCharacters = identitySplit[numberOfCharacters - 5][1];
		numberOfEvilCharacters = identitySplit[numberOfCharacters - 5][2];
	}
	
	private void createCharacters(int numberOfCharacters) {
		this.numberOfCharacters = numberOfCharacters;
		int index = 0;
		int playerCount = 1;
		while (allCharacterCircle.size() < numberOfCharacters) {
			int characterIndexValue = (int) (Math.random() * 2);
			if ((characterIndexValue == 1) && (numberOfGoodCharacters != 0)) {
				GoodCharacter newGoodCharacter = new GoodCharacter();
				newGoodCharacter.setCharacterType();
				allCharacterCircle.add(newGoodCharacter);
				numberOfGoodCharacters -= 1;
			} else if ((characterIndexValue == 2) && (numberOfEvilCharacters != 0)) {
				EvilCharacter newEvilCharacter = new EvilCharacter();
				newEvilCharacter.setCharacterType();
				allCharacterCircle.add(newEvilCharacter);
				numberOfEvilCharacters -= 1;
			} else if (playerCount != 0) {
				Player newPlayer = new Player();
				newPlayer.setCharacterType();
				allCharacterCircle.add(newPlayer);
				playerCount -= 1;
			}
		}
	}
	
	public ArrayList<CharacterCard> getQuestMembers() {
		return questMembers;
	}
	
}

class CharacterCard {
	
	boolean questVote;
	private int characterIndex;
	String characterType;
	
	public boolean getQuestVote() {
		return questVote;
	}
	
	public int getCharacterIndex() {
		return characterIndex;
	}
	
	public void setCharacterIndex(int characterIndex) {
		this.characterIndex = characterIndex;
	}
	
	public void setQuestVote() {
		System.out.println("placeholder");
	}
	
	public void setCharacterType() {
		System.out.println("placeholder");
	}
	
	public String getCharacterType() {
		return this.characterType;
	}
	
	public void chooseQuestMembers() {
		System.out.println("placeholder");
	}
	
}

class Player extends CharacterCard {
	
	String[] possibleCharacterTypes = {"Loyal Servant of Arthur","Minion of Mordred"};
	
	public void setCharacterType() {
		ResistanceGame.characterSplit();
		characterType = possibleCharacterTypes[(int) (Math.random() * possibleCharacterTypes.length)];
		if (characterType == "Loyal Servant of Arthur") {
			ResistanceGame.numberOfGoodCharacters -= 1;
		} else if (characterType == "Minion of Mordred") {
			ResistanceGame.numberOfEvilCharacters -= 1;
		}
	}
	
	public void chooseQuestMembers(int questNumber) {
		Scanner scan = new Scanner(System.in);
		for (int count = 0; count < ResistanceGame.questMemberSplit[ResistanceGame.numberOfCharacters - 5][questNumber - 1]; count++) {
			System.out.println("Name a character you would like to nominate for the quest: ");
			int userInput = scan.nextInt();
			ResistanceGame.questMembers.add(ResistanceGame.allCharacterCircle.get(userInput - 1));
		}
	}
	
}

class GoodCharacter extends CharacterCard {
	
	public void setQuestVote() {
		questVote = true;
		ResistanceGame.allQuestVotes.add(questVote);
	}
	
	public void setCharacterType() {
		characterType = "Loyal Servant of Arthur";
	}
	
	public void chooseQuestMembers(int questNumber) {
		for (int count = 0; count < ResistanceGame.questMemberSplit[ResistanceGame.numberOfCharacters - 5][questNumber - 1]; count++) {
			ResistanceGame.questMembers.add(ResistanceGame.allCharacterCircle.get((int) (Math.random() * (ResistanceGame.allCharacterCircle.size()))));
		}
	}
	
}

class EvilCharacter extends CharacterCard {
	
	private boolean[] questVoteChoices = {true,false};
	
	public void setQuestVote() {
		questVote = questVoteChoices[(int) (Math.random() * 2)];
		ResistanceGame.allQuestVotes.add(questVote);
	}
	
	public void setCharacterType() {
		characterType = "Minion of Mordred";
	}
	
	public void chooseQuestMembers(int questNumber) {
		for (int count = 0; count < ResistanceGame.questMemberSplit[ResistanceGame.numberOfCharacters - 5][questNumber - 1]; count++) {
			ResistanceGame.questMembers.add(ResistanceGame.allCharacterCircle.get((int) (Math.random() * (ResistanceGame.allCharacterCircle.size()))));
		}
	}
