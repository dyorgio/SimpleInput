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
package de.ralleytn.simple.input;

import java.awt.MouseInfo;
import java.awt.Point;

/**
 * Represents an event that can be fired by mice.
 * @author Ralph Niemitz/RalleYTN(ralph.niemitz@gmx.de)
 * @version 1.0.0
 * @since 1.0.0
 */
public class MouseEvent extends DeviceEvent {

	/**
	 * Left mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_LEFT = 1;
	
	/**
	 * Right mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_RIGHT = 3;
	
	/**
	 * Middle mouse button
	 * @since 1.0.0
	 */
	public static final int BUTTON_MIDDLE = 2;
	
	/**
	 * No button
	 * @since 1.0.0
	 */
	public static final int BUTTON_NONE = -1;
	
	private final float deltaX;
	private final float deltaY;
	private final float unitsToScroll;
	private final int button;
	private final int x;
	private final int y;
	
	public MouseEvent(Device device, float deltaX, float deltaY, float unitsToScroll, int button) {
		
		super(device);
		
		Point location = MouseInfo.getPointerInfo().getLocation();
		
		this.deltaX = deltaX;
		this.deltaY = deltaY;
		this.unitsToScroll = unitsToScroll;
		this.button = button;
		this.x = location.x;
		this.y = location.y;
	}
	
	/**
	 * @return the cursor position on the X axis on the screen
	 * @since 1.0.0
	 */
	public final int getX() {
		
		return this.x;
	}
	
	/**
	 * @return the cursor position on the Y axis on the screen
	 * @since 1.0.0
	 */
	public final int getY() {
		
		return this.y;
	}
	
	/**
	 * @return the amount of pixel the cursor moved on the X axis since the last update
	 * @since 1.0.0
	 */
	public final int getDeltaX() {
		
		return (int)this.deltaX;
	}
	
	/**
	 * @return the amount of pixel the cursor moved on the Y axis since the last update
	 * @since 1.0.0
	 */
	public final int getDeltaY() {
		
		return (int)this.deltaY;
	}
	
	/**
	 * @return a negative value if the mouse wheel was scrolled down and a positive value if it was scrolled up
	 * @since 1.0.0
	 */
	public final float getUnitsToScroll() {
		
		return this.unitsToScroll;
	}
	
	/**
	 * @return the mouse button associated with this event
	 * @since 1.0.0
	 */
	public final int getButton() {

		return this.button;
	}
}
