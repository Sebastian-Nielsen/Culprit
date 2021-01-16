package common.other;

import framework.other.UUIDGenerator;

public class UUIDGeneratorImpl implements UUIDGenerator {
	private static final UUIDGenerator instance = new UUIDGeneratorImpl();

	private UUIDGeneratorImpl() {}

	public static UUIDGenerator getInstance() {
		return instance;
	}

	@Override
	public String generate() {
		return java.util.UUID.randomUUID().toString();
	}
}
