package com.github.thehilikus.dependency.analysis.readers.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.api.Dependency;
import com.github.thehilikus.dependency.analysis.api.DependencySource;
import com.github.thehilikus.dependency.analysis.core.BasicDependency;
import com.github.thehilikus.dependency.analysis.readers.FileFinder;

/**
 * A dependency provider that knows how to extract dependencies out of a folder with java source
 * files
 *
 * @author hilikus
 */
public class JavaSourceCodeReader implements DependencySource {

    private static final Logger log = LoggerFactory.getLogger(JavaSourceCodeReader.class);

    static final String PATTERN = "*.java";

    private static final String JAVA_IMPORT_REGEXP = "^\\s*import\\s+.+;.*";

    private List<Path> javaSourceFiles;

    /**
     * Constructs a dependency source by recursively navigating the given folder
     * 
     * @param rootDirectory the top folder containing the java sources
     * @throws IOException if there was a problem reading the files
     */
    public JavaSourceCodeReader(Path rootDirectory) throws IOException {
	if (!Files.exists(rootDirectory) || !Files.isDirectory(rootDirectory)) {
	    throw new IllegalArgumentException("Root directory has to be a readable folder");
	}

	filterJavaSources(rootDirectory);
    }

    private void filterJavaSources(Path rootDirectory) throws IOException {
	FileFinder fileWalker = new FileFinder(PATTERN);
	Files.walkFileTree(rootDirectory, fileWalker);
	javaSourceFiles = fileWalker.getMatchingFiles();
    }

    @Override
    public Set<Dependency> getDependencies() {
	if (javaSourceFiles == null) {
	    throw new IllegalStateException("Cannot get list of dependencies. There was an error finding the files");
	}

	Set<Dependency> result = new HashSet<>();
	for (Path sourceFile : javaSourceFiles) {
	    try {
		try (Stream<String> linesMatches = Files.lines(sourceFile).filter(
			line -> line.matches(JAVA_IMPORT_REGEXP))) {
		    Stream<String[]> validImports = linesMatches.map(line -> line.trim().split("( )+|(;)")).filter(
			    tokens -> tokens.length >= 2);

		    result.addAll(validImports.map(tokens -> tokens[1]).map(BasicDependency::new)
			    .collect(Collectors.toSet()));
		}

	    } catch (IOException exc) {
		log.error("[getDependencies] There was en error opening the source code file " + sourceFile, exc);
	    }
	}

	return result;
    }

    @Override
    public String getName() {
	return "Java SourceCode dependency reader";
    }

}
