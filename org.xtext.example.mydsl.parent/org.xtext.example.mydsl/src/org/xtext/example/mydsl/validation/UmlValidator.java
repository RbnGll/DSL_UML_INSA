/*
 * generated by Xtext 2.24.0
 */
package org.xtext.example.mydsl.validation;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.xtext.validation.Check;
import org.xtext.example.mydsl.uml.ClassContent;
import org.xtext.example.mydsl.uml.Interface;
import org.xtext.example.mydsl.uml.AbstractClass;
import org.xtext.example.mydsl.uml.Class;
import org.xtext.example.mydsl.uml.Relation;
import org.xtext.example.mydsl.uml.UmlPackage;

/**
 * This class contains custom validation rules. 
 *
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#validation
 */
public class UmlValidator extends AbstractUmlValidator {
	public static final String INVALID_NAME = "invalidName";
	public static final String INVALID_QUANTITY = "invalidQuantity";
	public static final String UNDECLARED_CLASS = "undeclaredClass";
	public static final String NO_CLASS_CONTENT = "noClassContent";
	
	
	@Check
	public void checkClassNameStartsWithCapital(ClassContent c) {
	    if (!Character.isUpperCase(c.getName().charAt(0))) {
	        warning("Name should start with a capital",
	            UmlPackage.Literals.CLASS_CONTENT__NAME, // TODO :: Change value
	            INVALID_NAME);
	    }
	}
	
	@Check
	public void checkAbstractClassNameStartsWithCapital(AbstractClass c) {
	    if (!Character.isUpperCase(c.getName().charAt(0))) {
	        warning("Name should start with a capital",
	            UmlPackage.Literals.ABSTRACT_CLASS__NAME, // TODO :: Change value
	            INVALID_NAME);
	    }
	}
	
	@Check
	public void checkInterfaceNameStartsWithCapital(Interface i) {
	    if (!Character.isUpperCase(i.getName().charAt(0))) {
	        warning("Name should start with a capital",
	            UmlPackage.Literals.INTERFACE__NAME, // TODO :: Change value
	            INVALID_NAME);
	    }
	}
	
	@Check
	public void checkClass1ExistInRelation(Relation r) {
		List<String> list = new ArrayList<String>(); //List of declared class names
		if(list.contains(r.getNameClass1())) {
			warning("Class '"+ r.getNameClass1() + "' have not been declared", UmlPackage.Literals.RELATION__NAME_CLASS1, UNDECLARED_CLASS);
		}
	}
	
	@Check
	public void checkClass2ExistInRelation(Relation r) {
		List<String> list = new ArrayList<String>(); //List of declared class names
		if(list.contains(r.getNameClass2())) {
			warning("Class '"+ r.getNameClass2() + "' have not been declared", UmlPackage.Literals.RELATION__NAME_CLASS2, UNDECLARED_CLASS);

		}
	}
	
	@Check
	public void checkClassHasContent(Class c) {
		if(c == null) { // TODO :: Voir comment on peut indiquer si parameter et function sont pas déclarés (voir sinon si on laisse class vide ?)
			error("Classs should had content", UmlPackage.Literals.CLASS__CONTENT, NO_CLASS_CONTENT);
		}
	}

}
