/*
 * MIT License
 * 
 * Copyright (c) 2018 Ralph Niemitz
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package de.ralleytn.simple.input.internal;

import java.awt.AWTException;
import java.awt.Robot;

import de.ralleytn.simple.input.Direction;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.KeyboardEvent;
import net.java.games.input.Controller;
import net.java.games.input.Controller.Type;
import net.java.games.input.Component;
import net.java.games.input.Component.Identifier;
import net.java.games.input.Component.Identifier.Axis;
import net.java.games.input.Component.Identifier.Key;

/**
 * Contains utility methods for SimpleInput.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public final class Util {

	private static final Robot ROBOT = Util.createRobot();
	
	private Util() {}
	
	/**
	 * 
	 * @param controller
	 * @return
	 * @since 1.0.0
	 */
	public static final boolean isXInput(Controller controller) {
		
		Type type = controller.getType();
		
		if(Type.GAMEPAD.equals(type)) {
			
			Component[] components = controller.getComponents();
			boolean hasXAxis = false;
			boolean hasYAxis = false;
			boolean hasZAxis = false;
			boolean hasRXAxis = false;
			boolean hasRYAxis = false;
			boolean hasPOV = false;
			
			for(Component component : components) {
				
				Identifier id = component.getIdentifier();
				
					   if(Axis.X.equals(id))   {hasXAxis = true;
				} else if(Axis.Y.equals(id))   {hasYAxis = true;
				} else if(Axis.RX.equals(id))  {hasRXAxis = true;
				} else if(Axis.RY.equals(id))  {hasRYAxis = true;
				} else if(Axis.Z.equals(id))   {hasZAxis = true;
				} else if(Axis.POV.equals(id)) {hasPOV = true;
				}
			}
			
			return hasPOV && hasRXAxis && hasRYAxis && hasXAxis && hasYAxis && hasZAxis;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 * @since 1.0.0
	 */
	public static final float getIntensity(float x, float y) {
		
		double velocity = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	    return (float)(velocity > 1.0F ? 1.0F : velocity);
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 * @since 1.0.0
	 */
	public static final boolean isDead(float value) {
		
		return value < 0.1F && value > -0.1F;
	}
	
	/**
	 * 
	 * @param value
	 * @return
	 * @since 1.0.0
	 */
	public static final Direction getPOVDirection(float value) {
		
		Direction direction = null;
		
		       if(value == 0.125F) {direction = Direction.NORTH_WEST;
		} else if(value == 0.25F)  {direction = Direction.NORTH;
		} else if(value == 0.375F) {direction = Direction.NORTH_EAST;
		} else if(value == 0.5F)   {direction = Direction.EAST;
		} else if(value == 0.625F) {direction = Direction.SOUTH_EAST;
		} else if(value == 0.75F)  {direction = Direction.SOUTH;
		} else if(value == 0.875F) {direction = Direction.SOUTH_WEST;
		} else if(value == 1.0F)   {direction = Direction.WEST;
		}
		
		return direction;
	}
	
	/**
	 * 
	 * @param value
	 * @param x
	 * @param y
	 * @return
	 * @since 1.0.0
	 */
	public static final Direction getAnalogStickDirection(float value, float x, float y) {
		
		Direction direction = null;
		
		if(value != 0.0F) {
			
			double angle = Math.toDegrees(Math.atan2(y, x));
			
			       if(angle == -90.0F) {direction = Direction.NORTH;
			} else if(angle == 0.0F)   {direction = Direction.EAST;
			} else if(angle == 180.0F) {direction = Direction.WEST;
			} else if(angle == 90.0F)  {direction = Direction.SOUTH;
			} else if(angle < -90.0F)  {direction = Direction.NORTH_WEST;
			} else if(angle < 0.0F)    {direction = Direction.NORTH_EAST;
			} else if(angle > 90.0F)   {direction = Direction.SOUTH_WEST;
			} else if(angle > 0.0F)    {direction = Direction.SOUTH_EAST;
			}
		}
		
		return direction;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @since 1.0.0
	 */
	public static final int getGamepadButtonByIdentifier(Identifier id) {
		
		return id == Identifier.Button._0  || id == Identifier.Button.Y            ? GamepadEvent.BUTTON_Y :
			   id == Identifier.Button._1  || id == Identifier.Button.B            ? GamepadEvent.BUTTON_B :
			   id == Identifier.Button._2  || id == Identifier.Button.A            ? GamepadEvent.BUTTON_A :
			   id == Identifier.Button._3  || id == Identifier.Button.X            ? GamepadEvent.BUTTON_X :
			   id == Identifier.Button._4  || id == Identifier.Button.LEFT_THUMB   ? GamepadEvent.BUTTON_L1 :
			   id == Identifier.Button._5  || id == Identifier.Button.RIGHT_THUMB  ? GamepadEvent.BUTTON_R1 :
			   id == Identifier.Button._6  || id == Identifier.Button.LEFT_THUMB2  ? GamepadEvent.BUTTON_L2 :
			   id == Identifier.Button._7  || id == Identifier.Button.RIGHT_THUMB2 ? GamepadEvent.BUTTON_R2 :
			   id == Identifier.Button._8  || id == Identifier.Button.SELECT       ? GamepadEvent.BUTTON_SELECT :
			   id == Identifier.Button._9  || id == Identifier.Button.START        ? GamepadEvent.BUTTON_START :
			   id == Identifier.Button._10 || id == Identifier.Button.LEFT_THUMB3  ? GamepadEvent.BUTTON_L3 :
			   id == Identifier.Button._11 || id == Identifier.Button.RIGHT_THUMB3 ? GamepadEvent.BUTTON_R3 :
			   id == Identifier.Button._12 || id == Identifier.Button.MODE         ? GamepadEvent.BUTTON_MODE :
			   -1;
	}
	
	/**
	 * @param id the jInput identifier
	 * @return the SimpleInput key code
	 * @since 1.0.0
	 */
	public static final int getKeyCode(Identifier id) {

			   if(Key.W.equals(id))      {return KeyboardEvent.KEY_W;
		} else if(Key.A.equals(id))      {return KeyboardEvent.KEY_A;
		} else if(Key.S.equals(id))      {return KeyboardEvent.KEY_S;
		} else if(Key.D.equals(id))      {return KeyboardEvent.KEY_D;
		} else if(Key.LSHIFT.equals(id)) {return KeyboardEvent.KEY_SHIFT;
		} else if(Key.SPACE.equals(id))  {return KeyboardEvent.KEY_SPACE;
		} else if(Key.UP.equals(id))     {return KeyboardEvent.KEY_ARROW_UP;
		} else if(Key.DOWN.equals(id))   {return KeyboardEvent.KEY_ARROW_DOWN;
		} else if(Key.LEFT.equals(id))   {return KeyboardEvent.KEY_ARROW_LEFT;
		} else if(Key.RIGHT.equals(id))  {return KeyboardEvent.KEY_ARROW_RIGHT;
		} else if(Key.RETURN.equals(id)) {return KeyboardEvent.KEY_ENTER;
		} else if(Key.BACK.equals(id))   {return KeyboardEvent.KEY_BACKSPACE;
		} else if(Key.DELETE.equals(id)) {return KeyboardEvent.KEY_DELETE;
		} else if(Key._0.equals(id))     {return KeyboardEvent.KEY_0;
		} else if(Key._1.equals(id))     {return KeyboardEvent.KEY_1;
		} else if(Key._2.equals(id))     {return KeyboardEvent.KEY_2;
		} else if(Key._3.equals(id))     {return KeyboardEvent.KEY_3;
		} else if(Key._4.equals(id))     {return KeyboardEvent.KEY_4;
		} else if(Key._5.equals(id))     {return KeyboardEvent.KEY_5;
		} else if(Key._6.equals(id))     {return KeyboardEvent.KEY_6;
		} else if(Key._7.equals(id))     {return KeyboardEvent.KEY_7;
		} else if(Key._8.equals(id))     {return KeyboardEvent.KEY_8;
		} else if(Key._9.equals(id))     {return KeyboardEvent.KEY_9;
		}
	
		return 0;
	}
	
	/**
	 * Sets the cursor position.
	 * @param x X coordinate in pixel
	 * @param y Y coordinate in pixel
	 * @since 1.0.0
	 */
	public static final void setCursorPosition(int x, int y) {
		
		Util.ROBOT.mouseMove(x, y);
	}
	
	/**
	 * 
	 * @return
	 * @since 1.0.0
	 */
	public static final Robot createRobot() {
		
		try {
			
			return new Robot();
			
		} catch(AWTException exception) {
			
			return null;
		}
	}
}
