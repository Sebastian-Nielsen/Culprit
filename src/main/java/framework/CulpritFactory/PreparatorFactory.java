package framework.CulpritFactory;

import common.preparatorFacade.FileOptionPreparator;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import framework.PreparatorFacade;

public interface PreparatorFactory {

	FileOptionPreparator createFileOptionPreparator(PreparatorFacade peparatorFacade);

	boolean addIdToContentFilesWithoutOne();

	boolean addDefaultIndexes();


	ContentFileHierarchy getContentHierarchy();

	DeployFileHierarchy getDeployHierarchy();

}
