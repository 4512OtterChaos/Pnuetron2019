package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.*;

public class Limelight{
    private NetworkTable table;
    public String name;
    private double tx;
    private double ty;
    private double ta;
    private double tv;

    /**
     * Limelight object, holding contour fields. Defaults to standard limelight url.
     * @param displayName Name of limelight to be displayed on smartdashboard.
     */
    public Limelight(String displayName){
        this.table = NetworkTableInstance.getDefault().getTable("limelight");
        this.name = displayName;
        this.tx = table.getEntry("tx").getDouble(0.0);
        this.ty = table.getEntry("ty").getDouble(0.0);
        this.ta = table.getEntry("ta").getDouble(0.0);
        this.tv = table.getEntry("tv").getDouble(0.0);
    }

    /**
     * Limelight object, holding contour fields.
     * @param displayName Name of limelight to be displayed on smartdashboard.
     * @param key String representation of limelight url. Default is "limelight", as in 'limelight'.local:5800
     */
    public Limelight(String displayName, String key){
        this.table = NetworkTableInstance.getDefault().getTable(key);
        this.name = displayName;
        this.tx = table.getEntry("tx").getDouble(0.0);
        this.ty = table.getEntry("ty").getDouble(0.0);
        this.ta = table.getEntry("ta").getDouble(0.0);
        this.tv = table.getEntry("tv").getDouble(0.0);
    }

    public void displayStats(){
        SmartDashboard.putNumber(name+"Tx", getTx());
        SmartDashboard.putNumber(name+"Ty", getTy());
        SmartDashboard.putNumber(name+"Ta", getTa());
    }

    public void toggleLight(){
        NetworkTableEntry lightMode = table.getEntry("ledMode");
        lightMode.setNumber((lightMode.getDouble(0)==3)? 1:3);
    }

    public void shiftPipe(){
        NetworkTableEntry pipe = table.getEntry("pipeline");
        pipe.setNumber(pipe.getDouble(0)+1);
        if(pipe.getName().equals("Pipeline_Name")) pipe.setNumber(0);
    }
    public void shiftPipe(double value){
        NetworkTableEntry pipe = table.getEntry("pipeline");
        pipe.setNumber(value);
    }

    /**
     * @return Horizontal angle of the target found by the limelight.
     */
    public double getTx(){
        this.tx = table.getEntry("tx").getDouble(0.0);
        return tx;
    }

    /**
     * @return Vertical angle of the target found by the limelight.
     */
    public double getTy(){
        this.ty = table.getEntry("ty").getDouble(0.0);
        return ty;
    }

    /**
     * @return Area of the target in relation to the image(0-100%).
     */
    public double getTa(){
        this.ta = table.getEntry("ta").getDouble(0.0);
        return ta;
    }

    /**
     * @return Whether there is a valid target(0 or 1).
     */
    public double getTv(){
        this.tv = table.getEntry("tv").getDouble(0.0);
        return tv;
    }
}