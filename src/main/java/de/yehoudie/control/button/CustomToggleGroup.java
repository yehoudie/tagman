package de.yehoudie.control.button;

import de.yehoudie.types.CssPseudoClasses;

public class CustomToggleGroup 
{
	private CustomButton active_button = null;
	private String active_class = "active";
	
	/**
	 * custom toggle group for CustomButton
	 */
	public CustomToggleGroup()
	{}
	
	/**
	 * activate a button and toggle
	 * 
	 * @param button T the clicked button
	 */
	public void select(CustomButton button)
	{
//		_active_button = button;
//		System.out.println("CustomToggleGroup.activate("+button+")");
		if ( active_button == button ) return;
		
		setDeselected(active_button);
		setSelected(button);
	}

	private void setSelected(CustomButton button)
	{
		if ( button == null ) return;

		active_button = button;
		active_button.deactivate();
		active_button.pseudoClassStateChanged(CssPseudoClasses.ACTIVE_PSEUDO_CLASS, true);
		active_button.setSelected(true);
	}

	private void setDeselected(CustomButton button)
	{
		if ( button == null ) return;

		button.activate();
		button.pseudoClassStateChanged(CssPseudoClasses.ACTIVE_PSEUDO_CLASS, false);
		button.setSelected(false);
		
		if ( active_button == button )
		{
			active_button = null;
		}
	}
	
	/**
	 * Set the css class for :active
	 * 
	 * @param active_class String the name of the class
	 */
	public void setActiveClass(String active_class)
	{
		this.active_class = active_class;
	}
	
	/**
	 * get the css class for :active
	 * 
	 * @return String the name of the class
	 */
	public String getActiveClass()
	{
		return active_class;
	}
	
	/**
	 * get the active button
	 * 
	 * @return	T the active button
	 */
	public CustomButton getSelectedButton()
	{
		return active_button;
	}

	public void reset()
	{
		setDeselected(active_button);
	}
}
