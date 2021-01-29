/*
 * generated by Xtext 2.24.0
 */
package org.xtext.example.mydsl.ui.outline;

import javax.inject.Inject;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.PluginImageHelper;
import org.eclipse.xtext.ui.editor.outline.impl.DefaultOutlineTreeProvider;
import org.eclipse.xtext.ui.editor.outline.impl.DocumentRootNode;
import org.eclipse.xtext.ui.editor.utils.TextStyle;
import org.eclipse.xtext.ui.label.StylerFactory;
import org.xtext.example.mydsl.uml.AbstractClass;
import org.xtext.example.mydsl.uml.AbstractFunction;
import org.xtext.example.mydsl.uml.Association;
import org.xtext.example.mydsl.uml.Class;
import org.xtext.example.mydsl.uml.DefinedParameter;
import org.xtext.example.mydsl.uml.Enum;
import org.xtext.example.mydsl.uml.Function;
import org.xtext.example.mydsl.uml.FunctionParameter;
import org.xtext.example.mydsl.uml.Heritage;
import org.xtext.example.mydsl.uml.Interface;
import org.xtext.example.mydsl.uml.InterfaceFunction;
import org.xtext.example.mydsl.uml.Link;
import org.xtext.example.mydsl.uml.Program;
import org.xtext.example.mydsl.uml.Statement;
import org.xtext.example.mydsl.uml.StaticParameter;
import org.xtext.example.mydsl.uml.UmlObject;



/**
 * Customization of the default outline structure.
 *
 * See https://www.eclipse.org/Xtext/documentation/310_eclipse_support.html#outline
 */
public class UmlOutlineTreeProvider extends DefaultOutlineTreeProvider {

	private static final RGB VISIBILITY_COLOR_PUBLIC = new RGB(0, 255, 127);		// #00ff7f 
	private static final RGB VISIBILITY_COLOR_PRIVATE = new RGB(236, 70, 70);		// #ec4646
	private static final RGB VISIBILITY_COLOR_PROTECTED = new RGB(72, 126, 149);  	// #487e95
	

	@Inject 
	private StylerFactory stylerFactory;

	
	
	public Object _text(Link l) {
		return "Link";
	}

	public Object _text(Class c) {
		return c.getName();
	}

	public Object _text(Enum c) {
		return c.getName();
	}

	public Object _text(AbstractClass c) {
		TextStyle abstractStyle = abstractClassStyle();
		return new StyledString(c.getName(), stylerFactory.createXtextStyleAdapterStyler(abstractStyle));
	}

	public Object _text(Interface c) {
		TextStyle interfaceStyle = interfaceStyle();
		return new StyledString(c.getName(), stylerFactory.createXtextStyleAdapterStyler(interfaceStyle));
	}


	/**
	 * Defined Parameter style.
	 * Example: name : String
	 * 
	 * @param d DefinedParameter : StaticParameter | ClassicParameter
	 * 
	 * @return
	 */
	public Object _text(DefinedParameter d) {

		// Get the style for from the visibility
		TextStyle textStyle = this.setColorFromVisibility(d.getVisibility());

		// Set the name that we want to display
		String displayName = d.getName() + " : " + d.getType();
		
		// Check if the variable is static
		if (d instanceof StaticParameter) {
			// Add static style
			textStyle.setStyle(SWT.ITALIC);
		}
		
		return new StyledString(displayName, stylerFactory.createXtextStyleAdapterStyler(textStyle));
	}
	
	
	
	
	public Object _text(Function function) {
		return this.getStyleForFunction(function.getName(), function.getVisibility(), function.getParams(), function.getReturnType());
	}

	public Object _text(AbstractFunction function) {
		return this.getStyleForFunction(function.getName(), function.getVisibility(), function.getParams(), function.getReturnType());
	}
	
	public Object _text(InterfaceFunction function) {
		return this.getStyleForFunction(function.getName(), function.getVisibility(), function.getParams(), function.getReturnType());
	}

	
	/**
	 * Get the style for the function
	 * @param name
	 * @param visibility
	 * @param params
	 * @param returnType
	 * @return
	 */
	private Object getStyleForFunction(String name, Character visibility, EList<FunctionParameter> params, String returnType) {
		TextStyle textStyle = this.setColorFromVisibility(visibility);
		String nameDisplay = this.getDisplayNameFromFunction(name, params, returnType);
		return new StyledString(nameDisplay, stylerFactory.createXtextStyleAdapterStyler(textStyle));
	}
	
	
	/**
	 * Get the name that we will display in the outline
	 * @param name Name of the function
	 * @param params The list of parameters
	 * @param returnType The name of the return type
	 * @return String 
	 */
	private String getDisplayNameFromFunction(String name, EList<FunctionParameter> params, String returnType) {
		String paramsType = "";
		String sep = "";
		for (FunctionParameter e : params) {
			paramsType += sep + e.getType();
			sep = ", ";
		}
		
		return name + "(" + paramsType + ") : " + returnType;
	}
	

	/**
	 * Don't show the list of parameters for the function.
	 * @param Function
	 * @return
	 */
    protected boolean _isLeaf(Function modelElement) {
        return true;
    }

    /**
     * Don't show the list of parameters for the function. 
     * @param modelElement
     * @return
     */
    protected boolean _isLeaf(AbstractFunction modelElement) {
        return true;
    }
    
    /**
     * Don't show the list of parameters for the function.
     * @param modelElement
     * @return
     */
    protected boolean _isLeaf(InterfaceFunction modelElement) {
        return true;
    }

	/**
	 * Set color from visibility char set.
	 * @param c
	 * @return TextStyle with the color
	 */
	private TextStyle setColorFromVisibility(Character c) {

		TextStyle textStyle = new TextStyle();

		// Set color based on the access
		switch (c) {
		case '+':
			textStyle.setColor(VISIBILITY_COLOR_PUBLIC);
			break;
		case '-':
			textStyle.setColor(VISIBILITY_COLOR_PRIVATE);
			break;
		case '#':
			textStyle.setColor(VISIBILITY_COLOR_PROTECTED);
			break;
		case '~':
			// No color
			break;
		default:
			break;
		}

		return textStyle;
	}





	// Links

	public Object _text(Heritage c) {
		return "Heritage";
	}

	public Object _text(Association c) {
		return "Association";
	}






	/**
	 * Abstract class UML :
	 * - Italics
	 * @return
	 */
	private TextStyle abstractClassStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	/**
	 * Interface 
	 * @return
	 */
	private TextStyle interfaceStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setStyle(SWT.BORDER);
		return textStyle;
	}

	
	
	

	@Inject
	private PluginImageHelper imageHelper;

	/**
	 * TODO :: Problem reading image, maybe the source file is not available for eclipse :'(
	 * 
	 * @param c
	 * @return
	 */
	public ImageDescriptor _image(Class c) {
		return imageHelper.getImageDescriptor("icons/metadata.png");
	}



}
