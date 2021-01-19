package common.culpritFactory;

import common.fileOption.FileOptionInserter;
import common.preparatorFacade.FileOptionPreparator;
import framework.ContentFileHierarchy;
import framework.CulpritFactory.Factory;
import framework.CulpritFactory.PreparatorFactory;
import framework.DeployFileHierarchy;
import framework.PreparatorFacade;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DefaultPreparatorFactory extends Factory implements PreparatorFactory {

	private final @NotNull FileOptionInserter fileOptionInserter;
	private final @NotNull File contentRootFile;

	public DefaultPreparatorFactory(@NotNull ContentFileHierarchy contentHierarchy,
	                                @NotNull  DeployFileHierarchy  deployHierarchy,
	                                @NotNull FileOptionInserter fileOptionInserter) {
		super(contentHierarchy, deployHierarchy);
		this.fileOptionInserter = fileOptionInserter;
		this.contentRootFile = contentHierarchy.getContentRootDir();
	}

	public DefaultPreparatorFactory(@NotNull ContentFileHierarchy contentHierarchy,
	                                @NotNull  DeployFileHierarchy deployHierarchy) {
		this(contentHierarchy, deployHierarchy, new FileOptionInserter());
	}

	@Override
	public FileOptionPreparator createFileOptionPreparator(PreparatorFacade peparatorFacade) {
		return new FileOptionPreparator(contentHierarchy, peparatorFacade, fileOptionInserter);
	}

	@Override
	public boolean addIdToContentFilesWithoutOne() {
		return true;
	}

	@Override
	public boolean addDefaultIndexes() {
		return true;
	}

	@Override
	public ContentFileHierarchy getContentHierarchy() {
		return contentHierarchy;
	}

	@Override
	public DeployFileHierarchy getDeployHierarchy() {
		return deployHierarchy;
	}


}
