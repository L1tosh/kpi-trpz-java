package com.example.processor;

import com.example.annotation.Serialized;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

/**
 * A custom annotation processor for handling the {@link Serialized} annotation.
 * <p>
 * This processor generates a new class for each class annotated with {@link Serialized}.
 * The generated class inherits from the original class and includes an additional
 * serialization method.
 * </p>
 *
 * <p>
 * Example:
 * If a class `User` is annotated with {@link Serialized}, this processor generates
 * a new class `UserSerialized` that extends `User` and implements a serialization method.
 * </p>
 *
 * @see Serialized
 */
@SupportedAnnotationTypes("com.example.annotation.Serialized")
@SupportedSourceVersion(SourceVersion.RELEASE_21)
@AutoService(Processor.class)
public class SerializedProcessor extends AbstractProcessor {
    /**
     * Processes elements annotated with {@link Serialized}.
     *
     * @param annotations the set of annotations processed by this processor.
     * @param roundEnv    the environment for information about the current and prior round.
     * @return {@code true} if the annotations are claimed by this processor, otherwise {@code false}.
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Serialized.class)) {
            if (element.getKind().isClass()) {
                try {
                    generateCopyClass((TypeElement) element);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * Generates a subclass of the given annotated class that includes serialization logic.
     *
     * @param classElement the class element annotated with {@link Serialized}.
     * @throws IOException if an error occurs while writing the generated file.
     */
    private void generateCopyClass(TypeElement classElement) throws IOException {
        String packageName = processingEnv.getElementUtils()
                .getPackageOf(classElement)
                .getQualifiedName()
                .toString();

        String className = classElement.getSimpleName().toString();

        JavaFileObject file = processingEnv.getFiler()
                .createSourceFile(packageName + "." + className + "Serialized");

        try (Writer writer = file.openWriter()) {
            writer.write("package " + packageName + ";\n\n");
            writePackages(classElement, writer);

            writer.write("public class " + className + "Serialized extends " + className + " {\n\n");

            writeConstructors(classElement, writer, className);

            Class<?> serializerClass;
            try {
                Serialized serializedAnnotation = classElement.getAnnotation(Serialized.class);
                serializerClass = serializedAnnotation.value();
                writer.write("    public String serialize() {\n");
                writer.write("        return new " + serializerClass.getSimpleName() + "().serialize(this);\n");
                writer.write("    }\n");
            } catch (MirroredTypeException e) {
                String serializerClassName = e.getTypeMirror().toString();
                writeMethodSerialize(writer, serializerClassName);
            }

            writer.write("}");
        }
    }

    /**
     * Writes the constructors of a class to the specified writer, including their parameters and calls to the superclass constructor.
     *
     * @param classElement the class element containing the constructors.
     * @param writer       the writer to which the constructors will be written.
     * @param className    the name of the class for which the constructors are being generated.
     * @throws IOException if an I/O error occurs while writing.
     */
    private static void writeConstructors(TypeElement classElement, Writer writer, String className) throws IOException {
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructor = (ExecutableElement) enclosed;

                writer.write("    public " + className + "Serialized (");
                List<? extends VariableElement> parameters = constructor.getParameters();

                writeParamsToConstructor(parameters, writer);
                writer.write(") { ");
                writer.write("\n");
                writer.write("        super(");
                writeParamsToSuperConstructor(parameters, writer);
                writer.write(");\n    }\n\n");
            }
        }
    }

    /**
     * Writes a `serialize` method that dynamically loads a serializer class, instantiates it, and invokes its `serialize` method.
     *
     * @param writer              the writer to which the method will be written.
     * @param serializerClassName the fully qualified name of the serializer class.
     * @throws IOException          if an I/O error occurs while writing.
     */
    private static void writeMethodSerialize(Writer writer, String serializerClassName) throws IOException {
        writer.write("    public String serialize() {\n");
        writer.write("        try {\n");
        writer.write("            Class<?> serializerClass = Class.forName(\"" + serializerClassName + "\");\n");
        writer.write("            Object serializer = serializerClass.getDeclaredConstructor().newInstance();\n");
        writer.write("            return (String) serializerClass.getMethod(\"serialize\", Object.class).invoke(serializer, this);\n");
        writer.write("        } catch (Exception ex) {\n");
        writer.write("            ex.printStackTrace();\n");
        writer.write("            return \"Serialization error\";\n");
        writer.write("        }\n");
        writer.write("    }\n");
    }

    /**
     * Writes the parameters to a call to the superclass constructor.
     *
     * @param parameters the list of parameters from the constructor.
     * @param writer     the writer to which the parameters will be written.
     * @throws IOException if an I/O error occurs while writing.
     */
    private static void writeParamsToSuperConstructor(List<? extends VariableElement> parameters, Writer writer) throws IOException {
        for (int i = 0; i < parameters.size(); i++) {
            writer.write(parameters.get(i).getSimpleName().toString());
            if (i < parameters.size() - 1) {
                writer.write(", ");
            }
        }
    }

    /**
     * Writes the import statements for all exceptions thrown by methods in the specified class.
     *
     * @param classElement the class element containing methods with thrown exceptions.
     * @param writer       the writer to which the import statements will be written.
     * @throws IOException if an I/O error occurs while writing.
     */
    private static void writePackages(TypeElement classElement, Writer writer) throws IOException {
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.METHOD) {
                ExecutableElement method = (ExecutableElement) enclosed;
                for (TypeMirror thrownType : method.getThrownTypes()) {
                    writer.write("import " + thrownType.toString() + ";\n");
                }
            }
        }
    }

    /**
     * Writes the parameters for a constructor, including their types and names.
     *
     * @param parameters the list of parameters to include in the constructor.
     * @param writer     the writer to which the parameters will be written.
     * @throws IOException if an I/O error occurs while writing.
     */
    private static void writeParamsToConstructor(List<? extends VariableElement> parameters, Writer writer) throws IOException {
        for (int i = 0; i < parameters.size(); i++) {
            VariableElement param = parameters.get(i);
            writer.write(param.asType().toString() + " " + param.getSimpleName());
            if (i < parameters.size() - 1) {
                writer.write(", ");
            }
        }
    }

}

