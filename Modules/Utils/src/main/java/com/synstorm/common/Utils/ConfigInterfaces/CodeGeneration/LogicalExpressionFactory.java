package com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration;

import com.synstorm.common.Utils.Annotations.Classes.Model_v1;
import org.codehaus.commons.compiler.ICompiler;
import org.codehaus.commons.compiler.ICompilerFactory;
import org.codehaus.commons.compiler.util.ResourceFinderClassLoader;
import org.codehaus.commons.compiler.util.resource.MapResourceCreator;
import org.codehaus.commons.compiler.util.resource.MapResourceFinder;
import org.codehaus.commons.compiler.util.resource.Resource;
import org.codehaus.commons.compiler.util.resource.StringResource;
import org.codehaus.janino.CompilerFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Model_v1
public enum LogicalExpressionFactory {
    INSTANCE;
    final static String BASE_CLASS_NAME = "com.synstorm.common.Utils.ConfigInterfaces.CodeGeneration.ILogicalExpression";
    final static Map<String, byte[]> classes = new ConcurrentHashMap<>();

    private final Map<String, String> logicalOperatorMap;
    private final Map<String, String> mathOperatorMap;

    LogicalExpressionFactory() {
        logicalOperatorMap = createLogicalOperatorMap();
        mathOperatorMap = createMathOperatorMap();
    }

    @Nullable
    public ILogicalExpression generateFunction(String funcName, String function, String arguments) throws Exception {
        String prepared = prepareExpression(function, arguments);

        String className = funcName + "Expression";

        String source = "import static java.lang.Math.*;\n" +
                "public final class " + className + " implements " + BASE_CLASS_NAME + " {\n"
                + "public boolean compute(double[] args) {\n"
                + "\treturn " + prepared + ";}\n}\n";


        ICompilerFactory compilerFactory = new CompilerFactory();
        ICompiler compiler = compilerFactory.newCompiler();
        compiler.setClassFileCreator(new MapResourceCreator(classes));

        compiler.compile(new Resource[] {
                new StringResource(className, source)
        });
        ClassLoader cl = new ResourceFinderClassLoader(
                new MapResourceFinder(classes),
                ClassLoader.getSystemClassLoader()
        );
        return (ILogicalExpression) cl.loadClass(className).getDeclaredConstructor().newInstance();
    }

    private String prepareExpression(@NotNull String expr, @NotNull String arguments) {
        String prepared = expr;
        final String[] argumentsList = arguments.split(",");

        //replace args
        for(int i = 0; i < argumentsList.length; ++i) {
            final String pattern = "\\b" + argumentsList[i] + "\\b"; //search entire word
            prepared = prepared.replaceAll(pattern, String.format("args[%d]", i));
        }

        //replace logical operators
        for (String lOperator : logicalOperatorMap.keySet())
            prepared = prepared.replace(lOperator, logicalOperatorMap.get(lOperator));

        //replace math operators
        for (String mOperator : mathOperatorMap.keySet())
            prepared = prepared.replace(mOperator, mathOperatorMap.get(mOperator));

        return prepared;
    }

    private Map<String, String> createLogicalOperatorMap() {
        Map<String, String> res = new HashMap<>();
        res.put("AND", "&&");
        res.put("OR", "||");

        return res;
    }

    private Map<String, String> createMathOperatorMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("_gt", ">");
        map.put("_~gt", ">=");
        map.put("_lt", "<");
        map.put("_~lt", "<=");
        map.put("_eq", "==");
        map.put("_~eq", "!=");

        return map;
    }
}
