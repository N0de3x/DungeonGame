import java.util.Scanner;

public class Dungeon
{

   private static Room[][] rooms;
   private static Player player;

   // Konstruktor.
   // Tar in storlek på dungeon och skapar en 2d array med rum. Skapar även en
   // lokal instans av spelare.
   public Dungeon(int dungeonSize, Player player)
   {
      rooms = new Room[dungeonSize][dungeonSize];
      Dungeon.player = player;

      for (int i = 0; i < dungeonSize; i++)
      {
         for (int j = 0; j < dungeonSize; j++)
         {
            // i och j skickas in i Room för att användas som pekare till rum.
            rooms[i][j] = new Room(i, j);
         }
      }
   }

   public static void playGame()
   {

      // start position.
      int px = 1; // motsvarar spelarens position på x-axeln i 2d arrayen
      int py = 0; // motsvarar spelarens position på y-axeln i 2d arrayen
      Room currentRoom = rooms[px][py]; // motsvarar rummet spelaren befinner sig i.

      // skapar scanner som används för input av invändaren.
      @SuppressWarnings("resource")
      Scanner userInput = new Scanner(System.in);

      System.out.println("Welcome " + Player.getName() + " to your treasure hunt. beware of the dragon!");

      // Loopar obegränsat
      while (true)
      {

         String playerInput; // variabel som lagrar input

         // håller reda på vilka dörrar som finns i nuvarande rum och vilka som är låsta.
         boolean north = false;
         boolean south = false;
         boolean west = false;
         boolean east = false;
         boolean northLocked = false;
         boolean southLocked = false;
         boolean westLocked = false;
         boolean eastLocked = false;
         Door[] doors = currentRoom.getDoors();// lokal instans av en array med nuvarande rummets dörrar

         Item[] inventory = player.getInventory();// lokal instans av en array med spelarens items.

         // håller reda på vad spelaren har för items
         boolean playerHasPotion = false;
         boolean playerHasKey = false;
         boolean playerHasTreasure = false;

         // tittar efter dörrar i nuvarande rum
         for (int i = 0; i < doors.length; i++)
         {
            if (doors[i].getPosition() == "w")
            {
               if (doors[i].isLocked())
                  westLocked = true;
               west = true;
            } else if (doors[i].getPosition() == "e")
            {
               if (doors[i].isLocked())
                  eastLocked = true;
               east = true;
            } else if (doors[i].getPosition() == "s")
            {
               if (doors[i].isLocked())
                  southLocked = true;
               south = true;
            } else if (doors[i].getPosition() == "n")
            {
               if (doors[i].isLocked())
                  northLocked = true;
               north = true;
            }
         }

         // Tittar vad spelaren har för items i sin inventory
         for (int i = 0; i < player.getInventory().length; i++)
         {
            if (player.getInventory()[i] != null)
            {
               if (player.getInventory()[i].getName() == "Potion")
                  playerHasPotion = true;
               else if (player.getInventory()[i].getName() == "Key")
                  playerHasKey = true;
               else if (player.getInventory()[i].getName() == "Treasure")
                  playerHasTreasure = true;
            }

         }
         // om nuvarande rummet är "utomhus"-rummet så visas en bild, annars visas
         // rummet.
         if (px == 0 && py == 2)
         {
            AsciiArt.displayOutside();
            if (playerHasTreasure)
            {
               DungeonMaster.endGame(true);
            } else
            {
               System.out.println("You found the exit! Retrieve the treasure before you escape. \n"
                     + "Press [s] to go back into the cave");
            }
         } else
         {
            // visar spelarens nuvarande items och rummet.
            player.displayInventory(inventory);
            AsciiArt.displayRoom(north, south, west, east);
         }

         // tittar efter items i nuvarande rum. Om det finns ett item som är möjligt att
         // ta upp så visas en text.
         if (currentRoom.getItem() != null && !currentRoom.getItem().getName().equals("Treasure"))
         {
            System.out.println("you found an item!");
            System.out.println(currentRoom.getItem().getName());
            System.out.println(currentRoom.getItem().getDesc());
            System.out.println("Do you want to pick up item? [p]");
         }

         // tittar efter monster i nuvarande rum. Om det finns så startas startFight();
         // och spelaren för slåss mot monstret.
         // draken har en separat fight-metod. Efter spelaren har slagits mot monstret
         // sätts rummets monster till null.
         if (currentRoom.getMonster() != null)
         {
            boolean playerUsedPotion;
            if (currentRoom.getMonster().getMonsterName().equals("The Legendary Dragon!"))
            {
               AsciiArt.displayDragon();
               playerUsedPotion = Room.dragonFight(currentRoom.getMonster(), playerHasPotion);
               currentRoom.setMonster(null);
               System.out.println("You found the dragons treasure! Now you can exit the cave!");
               player.addItem(currentRoom.getItem());
               currentRoom.setItem(null);
            } else
            {

               playerUsedPotion = Room.startFight(currentRoom.getMonster(), playerHasPotion);
               currentRoom.setMonster(null)

               ;
            }
            // om spelaren använder sin potion under fighten så tas den bort
            if (playerUsedPotion)
            {
               playerHasPotion = false;
               player.removeItem("Potion");
            }

            player.displayInventory(inventory);
            AsciiArt.displayRoom(north, south, west, east);
         }

         // input -loop som fortsätter tills användaren har matat in en giltig sträng.
         boolean validInput = false;
         while (!validInput)
         {
            // visar en text om spelaren har en potion.
            if (playerHasPotion)
               System.out.println("Press [h] to use potion");
            // tar in input
            System.out.print("Choose an action: ");
            playerInput = userInput.nextLine();

            // om spelaren väljer att gå till norr eller söder och det finns en dörr dit
            // spelaren vill gå så
            // uppdateras spelarens position på y axeln.
            if (playerInput.equalsIgnoreCase("n") && north || playerInput.equalsIgnoreCase("s") && south)
            {
               // om dörren är låst så tittar man om spelaren har en nyckel och dörren låses
               // upp om det finns.
               if (playerInput.equalsIgnoreCase("n") && northLocked || playerInput.equalsIgnoreCase("s") && southLocked)
               {
                  if (playerHasKey)
                  {
                     System.out.println("Used key and unlocked door");

                     // låser upp dörren och tar bort nyckeln.
                     doors = Door.unlockDoor(doors, playerInput);
                     currentRoom.setDoors(doors);
                     player.removeItem("Key");
                     validInput = true;

                  } else
                  {

                     System.out.println("Door is locked!");
                     validInput = true;
                  }
               } else
               {
                  // uppdaterar spelarens position.
                  py = updatePosition(py, playerInput);
                  validInput = true;
               }

               // om spelaren väljer att gå till väst eller öst och det finns en dörr dit
               // spelaren vill gå så
               // uppdateras spelarens position på x axeln.
            } else if (playerInput.equalsIgnoreCase("w") && west || playerInput.equalsIgnoreCase("e") && east)
            {
               // om dörren är låst så tittar man om spelaren har en nyckel och dörren låses
               // upp om det finns.
               if (playerInput.equalsIgnoreCase("w") && westLocked || playerInput.equalsIgnoreCase("e") && eastLocked)
               {
                  if (playerHasKey)
                  {
                     System.out.println("Used key and unlocked door");
                     doors = Door.unlockDoor(doors, playerInput);
                     currentRoom.setDoors(doors);
                     player.removeItem("Key");
                     validInput = true;

                  } else
                  {

                     System.out.println("Door is locked!");
                     validInput = true;
                  }
               } else
               {
                  px = updatePosition(px, playerInput);
                  validInput = true;
               }
               // om spelaren trycker P och det finns ett item i rummet så plockas itemet upp.
            } else if (playerInput.equalsIgnoreCase("p") && currentRoom.getItem() != null)
            {
               System.out.println("Picked up Item");

               // plockar upp itemet i rummet.
               player.addItem(currentRoom.getItem());
               currentRoom.setItem(null);
               validInput = true;
               // om spelaren trycker h så används potion och spelarens livspoäng går tillbaka
               // till 100.
            } else if (playerInput.equalsIgnoreCase("h") && playerHasPotion)
            {
               // använder potion
               Player.setHealthPoints(100);
               System.out.println("Your health is fully restored!");
               player.removeItem("Potion");
               validInput = true;
            }

            else
            {
               System.out.println("Invalid input, try again!");
            }
         }

         // uppdaterar currentRoom till nästa rum om px eller py har ändrats.
         currentRoom = rooms[px][py];
         System.out.println("------------------------------");
      }

   }

   // uppdaterar position i 2d-arrayen.
   static int updatePosition(int position, String direction)
   {
      if (direction.equalsIgnoreCase("n") || direction.equalsIgnoreCase("e"))
      {
         position++;
      } else if (direction.equalsIgnoreCase("s") || direction.equalsIgnoreCase("w"))
      {
         position--;
      }
      return position;
   }

}
