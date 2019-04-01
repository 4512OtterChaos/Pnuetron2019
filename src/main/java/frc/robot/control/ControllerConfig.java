package frc.robot.control;

public abstract class ControllerConfig{
    protected XboxButton[] conditions;
    public abstract void assign(XboxJoystick controller);
    /**
     * 
     * @param blocks If the conditional buttons block or require
     * @return If the conditional buttons meet conditions for blocking/requiring
     */
    public boolean getPassable(boolean blocks){
        if(conditions==null) return true;
        boolean pass = true;
        for(XboxButton b:conditions){
            if(blocks && b.get()) pass = false;
            if(!blocks && !b.get()) pass = false;
        }
        return pass;
    }
    /**
     * 
     * @param conditions Buttons which evaluate two states of control
     */
    public void setConditions(XboxButton[] conditions){
        this.conditions=conditions;
    }
}