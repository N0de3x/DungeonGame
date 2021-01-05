import java.util.Random;

public class Monster
{
   // String som slumpmässigt hämtar ett element från en array med diverse
   // monsternamn med hjälp av en random.
   Random randMonst = new Random();
   private String[] randomMonster =
   { "Troll", "Zombie", "Vampire", "Skeleton", "Orc", "Dark Wizard", "Frost Giant", "Undead Knight" };
   private String rndMonster = randomMonster[randMonst.nextInt(randomMonster.length)];
   
  //Deklaration av privata primitiva variabler för Setters & Getters.
   private String monsterNamn;
   private int monsterSkada;
   private int monsterHp;
   
   //Konstruktor för Monster.
   public Monster(String monsterName, int monsterDamage, int monsterHealth)
   {
      this.monsterNamn = monsterName;
      this.monsterSkada = monsterDamage;
      this.monsterHp = monsterHealth;

   }

   // Getter för att hämta MonsterHp
   public int getMonsterHp()
   {
      return this.monsterHp;
   }

   //Setter för att bestämma MonsterHp
   public void setMonsterHp(int monsterHp)
   {
      this.monsterHp = monsterHp;
   }

   //Getter för att hämta MonsterNamn
   public String getMonsterName()
   {
      return this.monsterNamn;
   }

   //Setter för att bestämma MonsterNamn
   public void setMonsterName(String monsterNamnTest)
   {
      this.monsterNamn = monsterNamnTest;
   }

   //Metod för Combat, där monstret tar den skada som spelaren har definerad.
   public void monsterTakingDamage()
   {
      this.monsterHp -= Player.getDamage();

   }

   //Getter för att hämta monsterSkada
   public int getMonsterDamage()
   {
      return this.monsterSkada;
   }

   //Setter för att bestämma monsterSkada
   public void setMonsterDamage(int monsterSkada)
   {
      this.monsterSkada = monsterSkada;
   }

   // Metod som använder sig utav vår rndMonster-String och skapar ett slumpmässigt
   // namn åt monsterName i konstruktorn när man kallar efter den.
   public String monsterRandomizer()
   {
      this.monsterNamn = rndMonster;
      return this.monsterNamn;
   }
}