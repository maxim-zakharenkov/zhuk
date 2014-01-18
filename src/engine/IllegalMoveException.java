package engine;

import java.util.Collections;
import java.util.Set;

/**
 * @author Max
 */
@SuppressWarnings("serial")
public class IllegalMoveException extends Exception {

    public final Set<Unit> illegalMoveUnitSet;

    public IllegalMoveException(Set<Unit> illegalMoveUnitSet) {
        this.illegalMoveUnitSet = Collections.unmodifiableSet(illegalMoveUnitSet);
    }
}
