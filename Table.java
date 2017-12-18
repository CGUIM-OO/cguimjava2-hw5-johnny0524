import java.util.ArrayList;

public class Table {
	public static final int MAXPLAYER = 4;
	private Deck putcard;
	private Player[] player;
	private Dealer dealer;
	private int[] pos_betArray;
	public Table(int ndeck){
		putcard = new Deck(ndeck);
		player = new Player[MAXPLAYER];
		pos_betArray = new int[MAXPLAYER];
	}
	public void set_player(int pos, Player p){
		player[pos] = p;
	}
	public Player[] get_player(){
		return player;
	}
	public void set_dealer(Dealer d){
		dealer = d;
	}
	public Card get_face_up_card_of_dealer(){
		ArrayList<Card> dThisRoundCard = dealer.getOneRoundCard();
		Card card;
		card = dThisRoundCard.get(1);
		return card;
	}
	private void ask_each_player_about_bets(){
		int pos;
		for(pos = 0 ; pos < MAXPLAYER ; pos ++){
			Player contestant = player[pos];
			contestant.sayHello();
			pos_betArray[pos] = contestant.makeBet();
		}
	}
	private void distribute_cards_to_dealer_and_players(){
		int i = 0 , j = 0;
		for(i = 0 ; i < 2 ; i ++){
			for(j = 0 ; j < MAXPLAYER ; j ++){
				Player a = player[j];
				ArrayList<Card> cards = a.getOneRoundCard();
				Card b = putcard.getOneCard(true);				
				cards.add(b);
				a.setOneRoundCard(cards);
			}
		}
		ArrayList<Card> cards = dealer.getOneRoundCard();
		Card x = putcard.getOneCard(false);
		cards.add(x);
		Card y = putcard.getOneCard(true);
		cards.add(y);
		dealer.setOneRoundCard(cards);
		System.out.print("Dealer's face up card is ");
		y.printCard();
	}
	private void ask_each_player_about_hits(){
		int i = 0;
		for(i = 0 ; i < MAXPLAYER ; i ++){
			Player a = player[i];
			boolean hit=false;
			do{
				hit=a.hit_me(this);
				if(hit){
					a.getOneRoundCard().add(putcard.getOneCard(true));
					a.setOneRoundCard(a.getOneRoundCard());
					System.out.print("Hit! ");
					System.out.println(a.getName()+"'s Cards now:");
					for(Card c : a.getOneRoundCard()){
						c.printCard();
					}
				}
				else{
					System.out.println(a.getName()+", Pass hit!");
					System.out.println(a.getName()+", Final Card:");
					for(Card c : a.getOneRoundCard()){
						c.printCard();
					}
				}
			}while(hit);
		}
	}
	private void ask_dealer_about_hits(){
		boolean hit=false;
		ArrayList<Card> cards = new ArrayList<>();
		do{
			hit=dealer.hit_me(this); 
			if(hit){
				cards = dealer.getOneRoundCard();
				cards.add(putcard.getOneCard(true));
				dealer.setOneRoundCard(cards);
				System.out.print("Hit! ");
				System.out.println("Dealer's Cards now:");
				for(Card c : dealer.getOneRoundCard()){
					c.printCard();
				}
			}
			else{
				System.out.println("Dealer's hit is over!");
	        }
		}while(hit);
	}
	private void calculate_chips(){
		ArrayList<Card> cards  = new ArrayList<>();
		cards = dealer.getOneRoundCard();
		int x = 0;
		System.out.println("Dealer's card value is "+dealer.getTotalValue()+" ,Cards:");
		for(Card card :cards){
			card.printCard();
		}
		for(x= 0 ; x < MAXPLAYER ; x++){
			Player a = player[x];
			if(dealer.getTotalValue()>21 && a.getTotalValue()>21){
				System.out.println("Need another game");
			}else if(dealer.getTotalValue()<=21&&a.getTotalValue()>21){
				System.out.println("Dealer wins the game");
				a.increaseChips(-pos_betArray[x]);
			}else if(dealer.getTotalValue()>21&&a.getTotalValue()<=21){
				System.out.println(a.getName()+" wins the game");
				a.increaseChips(pos_betArray[x]);
			}else if(dealer.getTotalValue()>a.getTotalValue()&&dealer.getTotalValue()<=21){
				System.out.println("Dealer wins the game");
				a.increaseChips(-pos_betArray[x]);
			}else if(dealer.getTotalValue()<a.getTotalValue()&&a.getTotalValue()<=21){
				System.out.println(a.getName()+" wins the game");
				a.increaseChips(pos_betArray[x]);
			}else{
				System.out.println("Need another game");
			}
		}
	}
	public int[] get_players_bet(){
		return pos_betArray;
	}
	public void play(){
		ask_each_player_about_bets();
		distribute_cards_to_dealer_and_players();
		ask_each_player_about_hits();
		ask_dealer_about_hits();
		calculate_chips();
	}
}
