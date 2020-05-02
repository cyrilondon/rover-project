package com.game.domain.model.entity.enums;

import java.io.Serializable;

public interface GameEnum<T extends Serializable> {

	T getValue();

}
