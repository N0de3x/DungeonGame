import java.util.Scanner;

public class DungeonMaster
{
   //dungeon attribut
   private final static int DUNGEON_SIZE = 3;

   //player attribut
   private static String playerName;
   private static int playerHp = 100;
   private static int playerDmg = 10;
   private static Item[] inventory = new Item[4];

   private static Scanner userInput = new Scanner(System.in);

   public static void main(String[] args)
   {
      //låter användaren bestämma namn på spelaren
      AsciiArt.startScreen();
      playerName = userInput.nextLine();
      
      startGame();
      Dungeon.playGame();
   }
   //Skapar en dungeon och spelare med bestämda attribut
   public static void startGame()
   { 
      new Dungeon(DUNGEON_SIZE, new Player(playerName, playerHp, playerDmg, inventory));
   }

   //avslutar spelet och stänger ner programmet
   public static void endGame(Boolean win)
   {
      if (win)
      {
         AsciiArt.displayTreasure();
         System.out.println("You leave the dungeon with your riches. Congratulations, you won!");
      } else
      {
         System.out.println("Better luck next time!");
      }
      System.out.println("Press [Enter] to end the game...");
      userInput.nextLine();
      System.exit(0);
   }

}
