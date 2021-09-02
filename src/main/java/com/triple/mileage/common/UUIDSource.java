package com.triple.mileage.common;

import java.util.UUID;
import java.util.function.Supplier;

public class UUIDSource {
	private final Supplier<UUID> uuidSupplier = UUID::randomUUID;

	public UUID uuid() {
		return uuidSupplier.get();
	}
}
