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
package de.ralleytn.simple.input.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.function.Supplier;

import javax.swing.JFrame;

import de.ralleytn.simple.input.Gamepad;
import de.ralleytn.simple.input.GamepadEvent;
import de.ralleytn.simple.input.Mouse;
import de.ralleytn.simple.input.MouseControl;

final class TestUtil {

	public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
	
	private TestUtil() {}
	
	private static final void testMouseControl(MouseControl mouseControl) {
		
		assertTrue(mouseControl.isEnabled());
		assertEquals(GamepadEvent.ANALOG_STICK_LEFT, mouseControl.getAnalogStick());
		assertEquals(5.0F, mouseControl.getSensity());
		assertEquals(GamepadEvent.BUTTON_X, mouseControl.getLeftMouseButtonMapping());
		assertEquals(GamepadEvent.BUTTON_NONE, mouseControl.getRightMouseButtonMapping());
		assertEquals(GamepadEvent.BUTTON_NONE, mouseControl.getMiddleMouseButtonMapping());
	}
	
	public static final MouseControl setupMouseControl(Gamepad gamepad) {
		
		MouseControl mouseControl = gamepad.getMouseControl();
		mouseControl.setEnabled(true);
		mouseControl.setAnalogStick(GamepadEvent.ANALOG_STICK_LEFT);
		mouseControl.setSensity(5.0F);
		mouseControl.mapLeftMouseButtonTo(GamepadEvent.BUTTON_X);
		mouseControl.mapRightMouseButtonTo(GamepadEvent.BUTTON_NONE);
		
		TestUtil.testMouseControl(mouseControl);
		
		return mouseControl;
	}
	
	public static final void sleep(Supplier<Boolean> callback) {
		
		while(callback.get()) {
			
			try {
				
				Thread.sleep(10);
				
			} catch(InterruptedException exception) {}
		}
	}
	
	public static final void waitUntilAllAreDown(Gamepad gamepad, int[] buttons) {
		
		TestUtil.sleep(() -> {
			
			for(int button : buttons) {
				
				if(!gamepad.isButtonDown(button)) {
					
					return true;
				}
			}
			
			return false;
		});
	}
	
	public static final void centerCursor() {
		
		int screenWidth = TestUtil.SCREEN_SIZE.width;
		int screenHeight = TestUtil.SCREEN_SIZE.height;
		Mouse.setCursorPosition(screenWidth / 2, screenHeight / 2);
	}
	
	public static final void doCursorControlTest(JFrame frame) {
		
		int screenWidth = TestUtil.SCREEN_SIZE.width;
		int screenHeight = TestUtil.SCREEN_SIZE.height;
		TestUtil.centerCursor();
		frame.setLocation((int)(Math.random() * (screenWidth - frame.getWidth())), (int)(Math.random() * (screenHeight - frame.getHeight())));
		TestUtil.sleep(() -> !frame.getBounds().contains(Mouse.getX(), Mouse.getY()));
	}
}
