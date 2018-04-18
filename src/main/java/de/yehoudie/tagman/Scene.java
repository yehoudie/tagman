/**
 * 
 */
package de.yehoudie.tagman;

import java.util.function.BiConsumer;

import de.yehoudie.types.Language;
import de.yehoudie.types.OS;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;

/**
 * @author yehoudie
 *
 */
public class Scene extends javafx.scene.Scene
{
	private BiConsumer<Integer, Integer> resizeCallback;
	public void setResizeCallback(BiConsumer<Integer, Integer> resizeCallback) { this.resizeCallback = resizeCallback; };
	
	/**
	 * @param root
	 * @param width
	 * @param height
	 */
	public Scene(final Parent root, double width, double height)
	{
		super(root, width, height);
	}
	
	/**
	 * Initialize the scene:<br>
	 *  - add styles depending on Language and OS
	 *  
	 * @param	os OS the running operating system
	 * @param	lang Language the installed language
	 */
	public void init(final OS os, final Language lang)
	{
		addStyles(os, lang);
	}

	/**
	 * @param os
	 * @param lang
	 */
	protected void addStyles(final OS os, final Language lang)
	{
		this.getStylesheets().add(this.getClass().getResource("/css/main.css").toExternalForm());
//		if ( os == OS.WINDOWS ) this.getStylesheets().add(this.getClass().getResource("/css/main.win.css").toExternalForm());
//		else if ( os == OS.MAC ) this.getStylesheets().add(this.getClass().getResource("/css/main.mac.css").toExternalForm());
//		this.getStylesheets().add(this.getClass().getResource("/css/main." + lang.toString().toLowerCase() + ".css").toExternalForm());
	}
	
	/**
	 * Activate listener.
	 */
	public void activate()
	{
		addResizeListener();
	}

	/**
	 * Create and add stage resize listeners for the root scene.
	 */
	private void addResizeListener()
	{
		addWidthChangeListener();
		addHeightChangeListener();
	}

	/**
	 * 
	 */
	protected void addHeightChangeListener()
	{
		this.heightProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable_value, Number old_height, Number new_height)
			{
				resizeCallback.accept(widthProperty().intValue(), new_height.intValue());
			}
		});
	}

	/**
	 * 
	 */
	protected void addWidthChangeListener()
	{
		this.widthProperty().addListener(new ChangeListener<Number>()
		{
			@Override
			public void changed(ObservableValue<? extends Number> observable_value, Number old_width, Number new_width)
			{
				resizeCallback.accept(new_width.intValue(), heightProperty().intValue());
			}
		});
	}
}
