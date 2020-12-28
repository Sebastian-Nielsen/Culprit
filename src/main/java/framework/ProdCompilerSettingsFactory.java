package framework;

import common.PrecompilerImpl;
import common.DeployerImpl;

public class ProdCompilerSettingsFactory implements CompilerSettingsFactory {
	@Override
	public Deployer createDeployer() {
		return new DeployerImpl();
	}

	@Override
	public Precompiler createPrecompiler() {
		return null;
	}

}
