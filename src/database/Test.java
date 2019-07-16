package database;


public class Test {
   public static void main(String[] srg) {
     Crud database =new Crud();
     Music m=new Music("123","C:\\CloudMusic\\1.mp3");
     database.insertMusic(m);
     database.delete("123");
    // System.out.println("³ö²åÈë");
     //database.update(m);
   }
}