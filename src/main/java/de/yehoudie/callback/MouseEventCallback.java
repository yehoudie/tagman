package de.yehoudie.callback;

import javafx.scene.input.MouseEvent;

@FunctionalInterface
public interface MouseEventCallback 
{
	void call(MouseEvent e);
}
