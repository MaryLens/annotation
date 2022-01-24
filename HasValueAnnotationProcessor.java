package custom;



import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedAnnotationTypes("custom.HasValue")
public class HasValueAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment re) {

		// loop through the annotations
		for (TypeElement annotation : annotations) {
			Set<? extends Element> elements = re.getElementsAnnotatedWith(annotation);
			for (Element element : elements) {

				if (element.getKind() == ElementKind.CLASS) {

					boolean found = false;

					for (Element elementIn : element.getEnclosedElements()) {
						if (elementIn.getKind() == ElementKind.FIELD) {
							if (elementIn.getSimpleName().toString().equals("value")) {
								found = true;
								break;

							}
						}
					}

					if (!found) {
						processingEnv.getMessager().printMessage(Kind.ERROR,
								"Class " + element.asType() + " does not contain \"value\" field!", element);
					}
//				try {
//				element.asType().getClass().getField("value");
//			} catch (NoSuchFieldException e) {
//				System.err.println("The class " + className + " does not respect HasValue principle!");
//			} catch (SecurityException e) {
//					e.printStackTrace();
//			}
				}
			}
		}

		return false;
	}

//	public void process(String className) throws ClassNotFoundException {
//		
//		//load class
//		Class clazz = getClass().getClassLoader().loadClass(className);
//		//scan class for annotation
//		
//		if(clazz.isAnnotationPresent(HasValue.class)) {
//			
//			//check if class has value field if is annotated
//			try {
//				clazz.getField("value");
//			} catch (NoSuchFieldException e) {
//				System.err.println("The class " + className + " does not respect HasValue principle!");
//			} catch (SecurityException e) {
//			}
//		}
//		
//	}

}
