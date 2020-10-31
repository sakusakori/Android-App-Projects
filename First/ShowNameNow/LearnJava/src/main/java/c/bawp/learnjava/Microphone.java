package c.bawp.learnjava;

public class Microphone {
    //instance variables/ properties/ fields
    private String name;
    private String color;
    private int model;


    public Microphone(){}
    public Microphone(String name, String color, int model) {
        this.name = name;
        this.color = color;
        this.model = model;
    }

    public Microphone(String name, String color) {
          this.name = name;
          this.color = color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    //Actions/behaviors methods
    public void turnOff() {
        System.out.println(this.name + " Turn off");
    }
    public void turnOn() {
        System.out.println(this.name + " Turn on");
    }
    public void setVolume() {
        System.out.println( this.name + " Setting volume");
    }

    public String showDescription() {
        return "Microphone name " + this.name + " with color: " + this.color
                    + " and its model " + this.model;
    }

}
