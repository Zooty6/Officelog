package officelog;
// Made by Zooty

import java.awt.image.BufferedImage;


public class Person {
        final private String Name;
        private Room Location;
        private BufferedImage Pic;
        final private int ID;

    
    public Person(String Name, int ID) {
        this.Name = Name;
        this.ID = ID;
        this.Location=null;
        //TODO: Set This.Pic to default
    }

    public Person(String Name, BufferedImage Pic, int ID) {
        this.Name = Name;
        if(Pic.getWidth()!=50 && Pic.getHeight()!=50)
            throw new IllegalArgumentException("Icon is not 50x50");
        this.Pic = Pic;
        this.ID = ID;
        this.Location=null;
        System.out.println("done");
    }

    public void setPic(BufferedImage pic) {
        this.Pic = pic;
    }
    
    public void setLocation(Room newLoc){
        this.Location = newLoc;
    }

    public BufferedImage getPic() {
        return Pic;
    }
    
    public Room getLocation() {
        return Location;
    }

    public String getName() {
        return Name;
    }

    public int getID() {
        return ID;
    }
    
    public boolean isAllowed(Room room) {
        return false;
    }
}

class WrongDimensionException extends Exception {
   public WrongDimensionException(String msg){
      super(msg);
   }
}