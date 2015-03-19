package net.rptools.tokentool.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.colorchooser.*;

import net.rptools.tokentool.AppState;
import net.rptools.tokentool.TokenTool;

/* ColorChooserDemo.java requires no other files. */
public class ColorChooserDialog extends JDialog implements ChangeListener
{
	protected JColorChooser colorChooser;
	protected JLabel banner;
	protected JFrame owner;
	protected JDialog colorChooserDialog;
	private Color lastColor;
	
	public ColorChooserDialog(JFrame owner)
	{
		this.owner = owner;
		init();
		super.setLocationRelativeTo(getParent());
	}

	private void init()
	{
		setSize(550, 350);
		setLayout(new BorderLayout());

		lastColor = AppState.compositionProperties.getBackgroundColor();
		colorChooser = new JColorChooser(lastColor);
		colorChooser.getSelectionModel().addChangeListener(this);

		add(colorChooser, BorderLayout.CENTER);

		colorChooserDialog = JColorChooser.createDialog(owner, "Choose Token Background Color", false, colorChooser , new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent event) { 
				// OK action performed...
				lastColor = colorChooser.getColor();
				AppState.compositionProperties.setBackgroundColor(colorChooser.getColor());
				TokenTool.getFrame().getTokenCompositionPanel().fireCompositionChanged();
			}
		} , new ActionListener() { 
			@Override 
			public void actionPerformed(ActionEvent event) {
				// Cancel action performed...
				AppState.compositionProperties.setBackgroundColor(lastColor);
				TokenTool.getFrame().getTokenCompositionPanel().fireCompositionChanged();
			}
		}); 
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ESCAPE"), "cancel");
		getRootPane().getActionMap().put("cancel", new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				dispose();
			}
		});
		
		colorChooserDialog.setVisible(true);
	}

	public void showGUI()
	{
		colorChooserDialog.setVisible(true);
	}
	
	public void stateChanged(ChangeEvent e)
	{
		AppState.compositionProperties.setBackgroundColor(colorChooser.getColor());
		TokenTool.getFrame().getTokenCompositionPanel().fireCompositionChanged();
	}

}