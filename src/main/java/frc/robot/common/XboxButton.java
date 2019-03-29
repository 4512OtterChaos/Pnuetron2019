package frc.robot.common;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XboxButton extends Button {
    private final GenericHID mJoystick;
    private final int mButtonNum;
    private boolean mIsAxis=false;
  
    /**
     * Create a joystick button for triggering commands.
     *
     * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
     *                     etc)
     * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
     */
    public XboxButton(GenericHID joystick, int buttonNum) {
        mJoystick = joystick;
        mButtonNum = buttonNum;
    }
  
    /**
     * Gets the value of the joystick button(s).
     *
     * @return The value of the joystick button(s)
     */
    @Override
    public boolean get() {
        if(!mIsAxis) return mJoystick.getRawButton(mButtonNum);
        else return mJoystick.getRawAxis(mButtonNum)!=0;
    }
    public boolean getPressed(){
      return mJoystick.getRawButtonPressed(mButtonNum);
    }
    public boolean getReleased(){
      return mJoystick.getRawButtonReleased(mButtonNum);
    }

    public int getNum(){
        return mButtonNum;
    }

    public void setAxis(boolean isAxis){
      mIsAxis=isAxis;
    }

    public void whenActive(final Command command, final XboxButton other, final boolean blocks){
        new ButtonScheduler() {
            private boolean m_pressedLast = get() && (other.get()==!blocks);
      
            @Override
            public void execute() {
              boolean pressed = get() && (other.get()==!blocks);
      
              if (!m_pressedLast && pressed) {
                command.start();
              }
      
              m_pressedLast = pressed;
            }
          }.start();
    }
    public void whileActive(final Command command, final XboxButton other, final boolean blocks) {
        new ButtonScheduler() {
          private boolean m_pressedLast = get() && (other.get()==!blocks);
    
          @Override
          public void execute() {
            boolean pressed = get() && (other.get()==!blocks);
    
            if (pressed) {
              command.start();
            } else if (m_pressedLast && !pressed) {
              command.cancel();
            }
    
            m_pressedLast = pressed;
          }
        }.start();
    }
    public void whenInactive(final Command command, final XboxButton other, final boolean blocks) {
        new ButtonScheduler() {
          private boolean m_pressedLast = get() && (other.get()==!blocks);
    
          @Override
          public void execute() {
            boolean pressed = get() && (other.get()==!blocks);
    
            if (m_pressedLast && !pressed) {
              command.start();
            }
    
            m_pressedLast = pressed;
          }
        }.start();
    }
    public void toggleWhenActive(final Command command, final XboxButton other, final boolean blocks) {
        new ButtonScheduler() {
          private boolean m_pressedLast = get() && (other.get()==!blocks);
    
          @Override
          public void execute() {
            boolean pressed = get() && (other.get()==!blocks);
    
            if (!m_pressedLast && pressed) {
              if (command.isRunning()) {
                command.cancel();
              } else {
                command.start();
              }
            }
    
            m_pressedLast = pressed;
          }
        }.start();
    }
    public void cancelWhenActive(final Command command, final XboxButton other, final boolean blocks) {
        new ButtonScheduler() {
          private boolean m_pressedLast = get() && (other.get()==!blocks);
    
          @Override
          public void execute() {
            boolean pressed = get() && (other.get()==!blocks);
    
            if (!m_pressedLast && pressed) {
              command.cancel();
            }
    
            m_pressedLast = pressed;
          }
        }.start();
      }
}