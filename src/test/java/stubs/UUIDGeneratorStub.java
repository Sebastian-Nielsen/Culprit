package stubs;

import framework.UUIDGenerator;

import java.util.Iterator;
import java.util.List;

public class UUIDGeneratorStub implements UUIDGenerator {

	private final Iterator<String> iterator = List.of(
			"11stub11-1111-1111-1111-1111stub1111",
			"22stub22-2222-2222-2222-2222stub2222",
			"33stub33-3333-3333-3333-3333stub3333",
			"44stub44-4444-4444-4444-4444stub4444",
			"55stub55-5555-5555-5555-5555stub5555",
			"66stub66-6666-6666-6666-6666stub6666"
	).iterator();

	@Override
	public String generate() {
		return iterator.next();
	}
}
