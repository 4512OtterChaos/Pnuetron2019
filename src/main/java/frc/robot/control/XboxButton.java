package frc.robot.control;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.command.Command;

public class XboxButton extends Button {
    private final GenericHID mJoystick;
    private final int mButtonNum;
    private boolean mIsAxis;

    /**
     * Create a joystick button for triggering commands.
     *
     * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
     *                     etc)
     * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
     */
    public XboxButton(GenericHID joystick, int buttonNum) {
        this(joystick, buttonNum, false);
    }
    /**
     * Create a joystick button for triggering commands.
     *
     * @param joystick     The GenericHID object that has the button (e.g. Joystick, KinectStick,
     *                     etc)
     * @param buttonNumber The button number (see {@link GenericHID#getRawButton(int) }
     * 
     * @param isAxis Whether the button number relates to an axis(analog stick)
     */
    public XboxButton(GenericHID joystick, int buttonNum, boolean isAxis) {
      mJoystick = joystick;
      mButtonNum = buttonNum;
      mIsAxis = isAxis;
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
    /**
     * Gets if this button is blocked by other buttons.
     */
    public boolean getClear(XboxButton[] buttons, boolean blocks){
      //blocks: no other specified buttons may be pressed
      //does not block: all other buttons must be pressed
      return (blocks)? !getAnyButtons(buttons):getAllButtons(buttons);
    }
    /**
     * Gets if this button should be activated(clear and pressed)
     * @param buttons
     * @param blocks
     * @return
     */
    public boolean getActivate(XboxButton[] buttons, boolean blocks){
      return get() && getClear(buttons, blocks);
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

    /**
     * Return true if all buttons are pressed
     */
    private boolean getAllButtons(XboxButton[] buttons){
      for(XboxButton button:buttons){
        if(!button.get()) return false;
      }
      return true;
    }
    /**
     * Return true if any of the buttons are pressed
     * @param buttons
     * @return
     */
    private boolean getAnyButtons(XboxButton[] buttons){
      for(XboxButton button:buttons){
        if(button.get()) return true;
      }
      return false;
    }

    //Button -> command evaluaters
    public void whileActive(final Command command, final XboxButton[] others, final boolean blocks){
      new ButtonScheduler() {
        private boolean lastActive = getActivate(others, blocks);
  
        @Override
        public void execute() {
          boolean active = getActivate(others, blocks);
  
          if (active) {
            command.start();
          } else if (lastActive && !active) {
            command.cancel();
          }
  
          lastActive = active;
        }
      }.start();
    }
    public void whenActive(final Command command, final XboxButton[] others, final boolean blocks){
      new ButtonScheduler() {
          private boolean lastActive = getActivate(others, blocks);
    
          @Override
          public void execute() {
            boolean active = getActivate(others, blocks);
    
            if (!lastActive && active) {
              command.start();
            }
    
            lastActive = active;
          }
        }.start();
    }
    public void whenInactive(final Command command, final XboxButton[] others, final boolean blocks) {
      new ButtonScheduler() {
        private boolean lastActive = getActivate(others, blocks);

        @Override
        public void execute() {
          boolean active = getActivate(others, blocks);

          if (lastActive && !active) {
            command.start();
          }

          lastActive = active;
        }
      }.start();
    }
    public void toggleWhenActive(final Command command, final XboxButton[] others, final boolean blocks) {
      new ButtonScheduler() {
        private boolean lastActive = getActivate(others, blocks);

        @Override
        public void execute() {
          boolean active = getActivate(others, blocks);

          if (!lastActive && active) {
            if (command.isRunning()) {
              command.cancel();
            } else {
              command.start();
            }
          }

          lastActive = active;
        }
      }.start();
    }
    public void cancelWhenActive(final Command command, final XboxButton[] others, final boolean blocks) {
      new ButtonScheduler() {
        private boolean lastActive = getActivate(others, blocks);

        @Override
        public void execute() {
          boolean active = getActivate(others, blocks);

          if (!lastActive && active) {
            command.cancel();
          }

          lastActive = active;
        }
      }.start();
    }
}