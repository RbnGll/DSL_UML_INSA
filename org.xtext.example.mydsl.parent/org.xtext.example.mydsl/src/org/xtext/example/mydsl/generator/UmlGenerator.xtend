/*
 * generated by Xtext 2.24.0
 */
package org.xtext.example.mydsl.generator

import com.google.inject.Inject
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.AbstractGenerator
import org.eclipse.xtext.generator.IFileSystemAccess2
import org.eclipse.xtext.generator.IGeneratorContext
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.xtext.example.mydsl.uml.Class
import org.xtext.example.mydsl.uml.ClassContent
import org.xtext.example.mydsl.uml.ClassicParameter
import org.xtext.example.mydsl.uml.Function
import org.xtext.example.mydsl.uml.StaticParameter
import org.xtext.example.mydsl.uml.UmlObject
import org.xtext.example.mydsl.uml.AbstractClass
import org.xtext.example.mydsl.uml.Interface
import org.xtext.example.mydsl.uml.Enum
import org.xtext.example.mydsl.uml.Link
import org.xtext.example.mydsl.uml.Heritage
import org.xtext.example.mydsl.uml.DefinedParameter

/**
 * Generates code from your model files on save.
 * 
 * See https://www.eclipse.org/Xtext/documentation/303_runtime_concepts.html#code-generation
 */
 
class UmlGenerator extends AbstractGenerator {
	var links = newArrayList()
	
	@Inject extension IQualifiedNameProvider
	
	override void doGenerate(Resource resource, IFileSystemAccess2 fsa, IGeneratorContext context) {
//		fsa.generateFile('greetings.txt', 'People to greet: ' + 
//			resource.allContents
//				.filter(Greeting)
//				.map[name]
//				.join(', '))

		links.addAll(resource.allContents.toIterable.filter(Link).toList)
		
		for (umlObject: resource.allContents.toIterable.filter(UmlObject)){
			fsa.generateFile(umlObject.class.toString + ".java", umlObject.compile())
			// fsa.generateFile(umlObject.fullyQualifiedName.toString("/") + ".java", umlObject.compile())
		}
		
		
	}
	private dispatch def processUmlObject(UmlObject object)'''«object»'''
	
	
	
		
	/**
	 * Call the right compilation method
	 */
	private dispatch def compile(UmlObject umlObject)''' Error while compiling «umlObject»
	'''
		
	private dispatch def compile(Class c) '''
		class «processUmlObject(c)» {
			«IF c.content !== null »
				«c.content.compile»
			«ENDIF»
		}
	'''
	
	// TODO
	private dispatch def compile (AbstractClass aClass)'''
		abstract «IF aClass.class_ !== null »
					«aClass.class_.compile»
		         «ENDIF»
	'''
	
	// TODO
	private dispatch def compile (Interface umlInterface)'''
		interface «umlInterface»
	'''
	// TODO
	private dispatch def compile (Enum umlEnum)'''«umlEnum»
	'''
	//TODO
	private def compile(ClassContent cc) '''
		«IF cc.params !== null && !cc.params.empty»
			«cc.params.compile»
		«ENDIF»
		
		«IF cc.functions !== null && !cc.functions.empty»
			«cc.functions.compile»
		«ENDIF»
	'''
	
	/**
	 * All ELists<T> should be compiled here, because of Java erasure
	 * Basically, the Java compiler deletes the generic type contained in the list for overridden methods.
	 * As CharSequence.compile() is overridden several times, the generic type is erased, resulting in multiple methods with the same signature
	 * 
	 * Retrieving the generic type contained in a list is a very annoying task to perform, 
	 * we therefore used a workaround by assuming that each EList should only contain a single type
	 * we can then test the class type of the first element of that list 
	 */
	 
	private def compile(EList<?> list) '''
	««« H
		«IF list !== null && !list.empty && list.get(0) instanceof DefinedParameter»
			«FOR param : list as EList<DefinedParameter>»
				«IF param.visibility == '#'»protected«ELSEIF param.visibility == '-'»private«ELSE»public«ENDIF» «IF param instanceof StaticParameter»static «ENDIF»«IF param.modifier != null»«param.modifier» «ENDIF»«param.type» «param.name»;
			«ENDFOR»
		«ENDIF»
	
		«IF list !== null && !list.empty && list.get(0) instanceof Function»
			«FOR function : list as EList<Function>»
				«IF function instanceof Function»«function.compile»«ENDIF»
			«ENDFOR»
		«ENDIF»
	'''
	
	/*
	private dispatch def compile (EList<DefinedParameter> parameters)'''
		«IF !parameters.empty»
			«FOR param : parameters»
				«param.visibility.compile» «IF param instanceof StaticParameter» static «ENDIF»«IF param.modifier != null» «param.modifier» «ENDIF»«param.type» «param.name»
			«ENDFOR»
		«ENDIF»
	'''
	* 
	*/
	
	
	private dispatch def compile (Function function) '''
		«IF function.visibility == '#'»protected«ELSEIF function.visibility == '-'»private«ELSE»public«ENDIF» «function.returnType» «function.name»(«IF function.params != null»«FOR param : function.params»«param.visibility» «param.modifier» «param.type» «param.name»,«ENDFOR»«ENDIF») { 
			// TODO - Auto generated method
		}
	'''
}
