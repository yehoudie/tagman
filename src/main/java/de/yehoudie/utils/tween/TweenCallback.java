package de.yehoudie.utils.tween;

import javafx.scene.Node;

public interface TweenCallback
{
	public void call(Node target, boolean dir);
}
