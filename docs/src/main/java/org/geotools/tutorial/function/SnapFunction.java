package org.geotools.tutorial.function;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.linearref.LinearLocation;
import org.locationtech.jts.linearref.LocationIndexedLine;
import java.util.List;
import org.geotools.filter.capability.FunctionNameImpl;
import org.geotools.util.Converters;
import org.opengis.filter.capability.FunctionName;
import org.opengis.filter.expression.Expression;
import org.opengis.filter.expression.ExpressionVisitor;
import org.opengis.filter.expression.Function;
import org.opengis.filter.expression.Literal;

/**
 * Quick function that illustrates snapping to a line.
 *
 * @author jody
 */
public class SnapFunction implements Function {

    static FunctionName NAME =
            new FunctionNameImpl(
                    "snap",
                    Point.class,
                    FunctionNameImpl.parameter("point", Point.class),
                    FunctionNameImpl.parameter("line", Geometry.class));

    private final List<Expression> parameters;

    private final Literal fallback;

    public SnapFunction(List<Expression> parameters, Literal fallback) {
        if (parameters == null) {
            throw new NullPointerException("parameters required");
        }
        if (parameters.size() != 2) {
            throw new IllegalArgumentException("snap( point, line) requires two parameters only");
        }
        this.parameters = parameters;
        this.fallback = fallback;
    }

    public Object evaluate(Object object) {
        return evaluate(object, Point.class);
    }

    public <T> T evaluate(Object object, Class<T> context) {
        Expression pointExpression = parameters.get(0);
        Point point = pointExpression.evaluate(object, Point.class);

        Expression lineExpression = parameters.get(1);
        Geometry line = lineExpression.evaluate(object, Geometry.class);

        LocationIndexedLine index = new LocationIndexedLine(line);

        LinearLocation location = index.project(point.getCoordinate());

        Coordinate snap = index.extractPoint(location);

        Point pt = point.getFactory().createPoint(snap);

        return Converters.convert(pt, context); // convert to requested format
    }

    public Object accept(ExpressionVisitor visitor, Object extraData) {
        return visitor.visit(this, extraData);
    }

    public String getName() {
        return NAME.getName();
    }

    public FunctionName getFunctionName() {
        return NAME;
    }

    public List<Expression> getParameters() {
        return parameters;
    }

    public Literal getFallbackValue() {
        return fallback;
    }
}
