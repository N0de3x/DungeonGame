
public class Door
{
   private String position;
   private boolean locked;

   // Konstruktor.
   // tar in position och om dörren skall vara låst och skapar dörren.
   public Door(String position, boolean locked)
   {
      this.setPosition(position);
      this.setLocked(locked);
   }

   // Getters och setters för variabler
   public String getPosition()
   {
      return position;
   }

   public void setPosition(String position)
   {
      this.position = position;
   }

   public boolean isLocked()
   {
      return locked;
   }

   public void setLocked(boolean locked)
   {
      this.locked = locked;
   }

   // Tar in dörrar i ett rum och Låser upp en utvald dörr.
   public static Door[] unlockDoor(Door[] doors, String position)
   {
      for (int i = 0; i < doors.length; i++)
      {
         if (doors[i].getPosition().equals(position))
         {
            doors[i].setLocked(false);
         }

      }
      return doors;
   }

}
