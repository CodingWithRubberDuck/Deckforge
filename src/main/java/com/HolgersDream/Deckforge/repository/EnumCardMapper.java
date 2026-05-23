package com.HolgersDream.Deckforge.repository;

import com.HolgersDream.Deckforge.domain.Condition;
import com.HolgersDream.Deckforge.domain.SuperType;
import com.HolgersDream.Deckforge.domain.Type;
import org.springframework.stereotype.Component;

@Component
public class EnumCardMapper {

    public SuperType checkSuperType(String superType) {
        if (superType == null) {
            return null;
        }
        return SuperType.valueOf(superType);
    }

    public Type checkMultiType(String multiType) {
        if (multiType == null) {
            return null;
        }
        return Type.valueOf(multiType);
    }

    public Condition checkCondition(String condition) {
        if (condition == null) {
            return null;
        }
        return Condition.valueOf(condition);
    }
}
