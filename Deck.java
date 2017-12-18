import java.util.ArrayList;
import java.util.Random;

public class Deck{
	
	private ArrayList<Card> cards;
	private ArrayList<Card> usedCard;
	private ArrayList<Card> openCard;
	public int nUsed=0;
	public Deck(int nDeck){
		cards=new ArrayList<Card>();
		usedCard=new ArrayList<Card>();
		openCard=new ArrayList<Card>();
		int number , kind , n ;
		for (n=1 ; n <= nDeck ; n++)
		{
			for (kind=1 ; kind <5 ; kind++)
			{
				for(number = 1 ; number<14 ; number++)
				{
					Card card = new Card (kind , number);
					cards.add(card);
				}
			}
		}
		shuffle();
	}
	public void shuffle(){
		openCard.clear();
		Random rnd = new Random();
		while (usedCard.size()>0){
			Card recard ;
			recard= usedCard.get(0);
			usedCard.remove(0);
			cards.add(recard);
		}
		for (int a=0 ; a< cards.size(); a++){
			Card putcard;
			putcard = cards.get(a);
			int j = rnd.nextInt(cards.size());
			cards.set(a,cards.get(j));
			cards.set(j, putcard);
		}
		nUsed=0;
	}
	public Card getOneCard(boolean isOpened){
		if(cards.size()!= 0){
			nUsed = nUsed + 1;
		    Card card = cards.get(0);
		    usedCard.add(card);
		    cards.remove(0);
		    if(isOpened == true) openCard.add(card);
		    return card;
	    }
		else{
			shuffle();
		    getOneCard(isOpened);
	    }
		return null;
	}

	public void printDeck(){
		int n ;
		for(n=0 ; n<cards.size(); n++)
		{
			Card pokecard = cards.get(n);
			pokecard.printCard();
		}


	}
	public ArrayList<Card> getOpenedCards(){
		return openCard ;
	}
	public ArrayList<Card> getAllCards() {
		return cards;
		
	}
}