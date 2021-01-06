
public class Player
{
    // Nödvändiga variabler
    // Namnen är ganska tydliga på vad de används till
    private static String name;
    private static int healthPoints;
    private static int damage;
    private Item[] inventory;

   public Player(String name, int healthPoints, int damage, Item[]inventory)
   {
      // Skapar spelare med värden som kommer att användas senare
      this.setName(name);
      Player.setHealthPoints(healthPoints);
      this.setDamage(damage);
      this.inventory = inventory;
   }

   // Lägger till föremål till spelaren
   public Item[] addItem(Item item)
   {
      // Om föremålet är ett svärd så ökas skadan som görs
      if(item.getName() == "Sword")
      {
         setDamage(getDamage() + Weapon.getIncreaseDamage());
      }
      
      for(int i =0; i < inventory.length; i++)
      {
         if(inventory[i] == null)
         {
            inventory[i] = item;
            break;
         }
      }
      
      return inventory;
      
   }
   
    // Tar bort föremål från spelaren
    // Exempelvis om man dricker potion
   public Item[] removeItem(String itemName)
   {
      for(int i =0; i < inventory.length; i++)
      {
         
         if(inventory[i] != null)
         {
            if(inventory[i].getName() == itemName)
            {
               inventory[i] = null;
            }
         }

      }
      return inventory;
      
   }
   
    // Visar vad spelaren har för föremål
   public void displayInventory(Item[] inventory)
   {
      Item item;
      System.out.println("Items: ");
      for(int i =0; i < inventory.length; i++)
      {
         if(inventory[i] != null)
         {
            item = inventory[i];
            System.out.print(item.getName() + "  "); 
         }
      }
      System.out.println();
   }
   
    
   public void setInventory(Item[] inventory)
   {
      this.inventory = inventory;
   }
   
   public Item[] getInventory()
   {
      return inventory;
   }

   public static int getDamage()
   {
      return damage;
   }

   public void setDamage(int damage)
   {
      Player.damage = damage;
   }

   public static int getHealthPoints()
   {
      return healthPoints;
   }

   public static void setHealthPoints(int healthPoints)
   {
      Player.healthPoints = healthPoints;
   }

   public static String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      Player.name = name;
   }
   
   
}
