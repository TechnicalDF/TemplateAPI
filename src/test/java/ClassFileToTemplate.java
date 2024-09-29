import dev.akarah.codeblocks.CodeBlock;
import dev.akarah.codeblocks.actions.SetVariableAction;
import dev.akarah.codeblocks.arguments.Args;
import dev.akarah.codeblocks.arguments.SlotItem;
import dev.akarah.codeblocks.arguments.varitems.DFNumber;
import dev.akarah.codeblocks.arguments.varitems.DFVariable;
import dev.akarah.codeblocks.flow.CallFunction;
import dev.akarah.codeblocks.flow.Function;
import dev.akarah.template.Template;

import java.lang.classfile.*;
import java.lang.classfile.instruction.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@SuppressWarnings("preview")
public class ClassFileToTemplate {
    public static List<Template> transform(ClassModel classModel) {
        return classModel.methods()
            .stream()
            .flatMap(methodElements -> methodToTemplate(classModel, methodElements))
            .toList();
    }

    public static Stream<Template> methodToTemplate(ClassModel classModel, MethodModel methodModel) {
        var templates = new ArrayList<Template>();

        methodModel.code().ifPresent(codeElements -> {
            var codeblocks = new ArrayList<CodeBlock>();
            AtomicInteger stackPointer = new AtomicInteger();

            codeblocks.add(new Function(
                classModel.thisClass().asInternalName()
                + "/"
                + methodModel.methodName().toString()
                + methodModel.methodType().toString(),
                new Args(List.of())
            ));
            codeElements.elementList().forEach(codeElement -> {
                switch (codeElement) {
                    case Label label -> {
                        codeblocks.add(new CallFunction(
                            classModel.thisClass().asInternalName()
                                + "/"
                                + methodModel.methodName().toString()
                                + methodModel.methodType().toString()
                                + "$" + label.hashCode(),
                            new Args(List.of())
                        ));
                        codeblocks.add(new Function(
                            classModel.thisClass().asInternalName()
                                + "/"
                                + methodModel.methodName().toString()
                                + methodModel.methodType().toString()
                                + "$" + label.hashCode(),
                            new Args(List.of())
                        ));
                    }
                    case LoadInstruction loadInstruction -> {
                        codeblocks.add(new SetVariableAction(
                            "=",
                            new Args(List.of(
                                new SlotItem(new DFVariable("stack[" + stackPointer.incrementAndGet() + "]", DFVariable.Scope.LOCAL), 0),
                                new SlotItem(new DFVariable("locals[" + loadInstruction.slot() + "]", DFVariable.Scope.LOCAL), 1)
                            ))
                        ));
                    }
                    case StoreInstruction storeInstruction -> {
                        codeblocks.add(new SetVariableAction(
                            "=",
                            new Args(List.of(
                                new SlotItem(new DFVariable("locals[" + storeInstruction.slot() + "]", DFVariable.Scope.LOCAL), 0),
                                new SlotItem(new DFVariable("stack[" + stackPointer.get() + "]", DFVariable.Scope.LOCAL), 1)
                            ))
                        ));
                        stackPointer.getAndDecrement();
                    }
                    case ConstantInstruction.ArgumentConstantInstruction constantInstruction -> {
                        var number = new DFNumber(String.valueOf(constantInstruction.constantValue()));
                        codeblocks.add(new SetVariableAction(
                            "=",
                            new Args(List.of(
                                new SlotItem(new DFVariable("stack[" + stackPointer.incrementAndGet() + "]", DFVariable.Scope.LOCAL), 0),
                                new SlotItem(number, 1)
                            ))
                        ));
                    }
                    case OperatorInstruction operatorInstruction -> {
                        switch (operatorInstruction.opcode()) {
                            case IADD, DADD, FADD -> {
                                var left = new DFVariable("stack[" + stackPointer.getAndDecrement() + "]", DFVariable.Scope.LOCAL);
                                var right = new DFVariable("stack[" + stackPointer.getAndDecrement() + "]", DFVariable.Scope.LOCAL);
                                var target = new DFVariable("stack[" + stackPointer.incrementAndGet() + "]", DFVariable.Scope.LOCAL);
                                codeblocks.add(new SetVariableAction(
                                    "+",
                                    new Args(List.of(
                                        new SlotItem(target, 0),
                                        new SlotItem(left, 1),
                                        new SlotItem(right, 2)
                                    ))
                                ));
                            }
                        }
                    }
                    default -> System.out.println("Unknown element " + codeElement);
                }
            });
            templates.add(new Template("", "", 1, codeblocks));
        });

        return templates.stream();
    }
}
