package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Debouncer {

    XboxController xbox;
    int buttonnum;
    double latest;
    double debounce_period;
    float pov;

    public Debouncer(XboxController xbox, int buttonnum){
        this.xbox = xbox;
        this.buttonnum = buttonnum;
        this.latest = 0;
        this.debounce_period = .5;
        this.pov = -1.0f;
    }
    public Debouncer(XboxController xbox, int buttonnum, double d){
        this.xbox = xbox;
        this.buttonnum = buttonnum;
        this.latest = 0;
        this.debounce_period = d;
        this.pov = -1.0f;
    }
    public Debouncer(XboxController xbox, float pov, double d){
        this.xbox = xbox;
        this.latest = 0;
        this.debounce_period = d;
        this.pov = pov;
    }

    public void setDebouncePeriod(float period){
        this.debounce_period = period;
    }

    public boolean get(){
        double now = Timer.getFPGATimestamp();
        if(((int)pov) != -1.0f && xbox.getPOV() == ((int)pov)) {
        	if((now-latest) > debounce_period){
                latest = now;
                return true;
            }
        }
        else if(((int)pov) == -1.0f && xbox.getRawButton(buttonnum)){
            if((now-latest) > debounce_period){
                latest = now;
                return true;
            }
        }
        return false;
    }
}