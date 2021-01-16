package framework.CulpritFactory;

import common.preparatorFacade.FileOptionPreparator;
import framework.PreparatorFacade;

import java.io.File;

public interface PreparatorFactory {

	FileOptionPreparator createFileOptionPreparator(PreparatorFacade peparatorFacade);

	boolean addIdToContentFilesWithoutOne();

	boolean addDefaultIndexes();


	File getContentRootFolder();

	File getDeployRootFolder();

}
