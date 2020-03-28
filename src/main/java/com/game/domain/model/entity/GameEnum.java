package com.game.domain.model.entity;

import java.io.Serializable;

public interface GameEnum<T extends Serializable> {

	T getValue();

}
