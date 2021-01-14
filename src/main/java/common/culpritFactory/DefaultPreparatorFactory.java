package common.culpritFactory;

import common.fileOption.FileOptionInserter;
import common.preparatorClasses.FileOptionPreparator;
import framework.CulpritFactory.Factory;
import framework.CulpritFactory.PreparatorFactory;
import framework.PreparatorFacade;

import java.io.File;

public class DefaultPreparatorFactory extends Factory implements PreparatorFactory {

	private final FileOptionInserter fileOptionInserter;

	public DefaultPreparatorFactory(File contentRootFolder, File deployRootFolder, FileOptionInserter fileOptionInserter) {
		super(contentRootFolder, deployRootFolder);
		this.fileOptionInserter = fileOptionInserter;
	}

	public DefaultPreparatorFactory(File contentRootFolder, File deployRootFolder) {
		this(contentRootFolder, deployRootFolder, new FileOptionInserter());
	}

	@Override
	public FileOptionPreparator createFileOptionPreparator(PreparatorFacade peparatorFacade) {
		return new FileOptionPreparator(contentRootFolder, peparatorFacade, fileOptionInserter);
	}

	@Override
	public boolean addIdToContentFilesWithoutOne() {
		return true;
	}

	@Override
	public boolean addDefaultIndexes() {
		return true;
	}


}
