import java.util.ArrayList;

public class Player extends Person {
		
		private String name;
		private int chips;
		private int bet;
		public Player(String name, int chips) {
			this.name=name;
			this.chips=chips;
		}
		public String getName() {
			return name;
		}
		public int makeBet(){ 
			if (chips >0){
				bet=1;
				return bet;
		}
			else return 0 ;
		}
		public boolean hit_me(Table table) {
			if (getTotalValue()>17){
				return false;
			}
			else return true;
		}
		public int getCurrentChips() {
			return chips;
		}
		public void increaseChips (int diff) {
			  chips += diff;
		}
		public void sayHello() 
		{	
		System.out.println("Hello, I am " + name + ".");
		System.out.println("I have " + chips + " chips.");
		}
}
