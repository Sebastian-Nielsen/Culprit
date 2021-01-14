package framework.CulpritFactory;

import common.preparatorClasses.FileOptionPreparator;
import framework.PreparatorFacade;

import java.io.File;

public interface PreparatorFactory {

	FileOptionPreparator createFileOptionPreparator(PreparatorFacade peparatorFacade);

	boolean addIdToContentFilesWithoutOne();

	boolean addDefaultIndexes();


	File getContentRootFolder();

	File getDeployRootFolder();

}
