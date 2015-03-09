package com.github.thehilikus.dependency.analysis.readers.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.thehilikus.dependency.analysis.api.DependencySource;
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

    private static final Pattern IMPORT_PATTERN = Pattern.compile("^\\s*import\\s+([a-zA-Z0-9\\.]+)\\s*;.*");
    private static final Pattern PACKAGE_PATTERN = Pattern.compile("^\\s*package\\s+([a-zA-Z0-9\\.]+)\\s*;.*");
    private static final Pattern CLASS_PATTERN = Pattern.compile("^\\s*.*\\s*(class|interface)\\s+(\\w+)\\s*.*");

    private List<Path> javaSourceFiles;

    private Path rootDirectory;

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

	this.rootDirectory = rootDirectory;
	filterJavaSources();
    }

    private void filterJavaSources() throws IOException {
	FileFinder fileWalker = new FileFinder(PATTERN);
	Files.walkFileTree(rootDirectory, fileWalker);
	javaSourceFiles = fileWalker.getMatchingFiles();
	log.info("[filterJavaSources] Found files: {}", javaSourceFiles);
    }

    @Override
    public Map<String, Set<String>> getDependencies() {
	if (javaSourceFiles == null) {
	    throw new IllegalStateException("Cannot get list of dependencies. There was an error finding the files");
	}

	Map<String, Set<String>> result = new HashMap<>(javaSourceFiles.size());
	for (Path sourceFile : javaSourceFiles) {
	    log.debug("[getDependencies] Processing file " + sourceFile);
	    Set<String> deps = new HashSet<>();
	    try (BufferedReader reader = Files.newBufferedReader(sourceFile)) {
		String packageName = null;
		String className = null;
		String line = reader.readLine();
		while (line != null) {
		    if (!line.isEmpty()) {
			String importDep = tryToMatch(line, IMPORT_PATTERN);
			if (importDep == null) {
			    if (packageName == null) {
				packageName = tryToMatch(line, PACKAGE_PATTERN);
				if (packageName == null) {
				    className = tryToMatch(line, CLASS_PATTERN);
				    if (className != null) {
					// found class declaration, there's nothing else we're
					// interested in
					break;
				    }
				}
			    } else {
				className = tryToMatch(line, CLASS_PATTERN);
				if (className != null) {
				    // found class declaration, there's nothing else we're
				    // interested in
				    break;
				}
			    }

			} else {
			    // found import
			    log.trace("[getDependencies] Found import: " + importDep);
			    deps.add(importDep);
			}
		    }

		    line = reader.readLine();
		}

		if (packageName != null && className != null) {
		    String fullyQualifiedName = packageName + '.' + className;
		    log.debug("[getDependencies] Done scanning dependencies for " + fullyQualifiedName + ": "
			    + deps.size() + " found");
		    result.put(fullyQualifiedName, deps);
		} else {
		    log.warn("[getDependencies] Malformed java file. Either package or class name not found. Package name = "
			    + packageName + ", class name = " + className);
		}
		reader.close();
	    } catch (IOException exc) {
		log.error("[getDependencies] There was en error opening the source code file " + sourceFile, exc);
	    }
	}

	return result;
    }

    private static String tryToMatch(String line, Pattern patternToUse) {
	Matcher matcher = patternToUse.matcher(line);
	if (matcher.find()) {
	    if (patternToUse != CLASS_PATTERN) {
		return matcher.group(1);
	    } else {
		return matcher.group(2);
	    }
	}
	return null;
    }

    @Override
    public String getName() {
	return "Java SourceCode";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((rootDirectory == null) ? 0 : rootDirectory.hashCode());
	return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	JavaSourceCodeReader other = (JavaSourceCodeReader) obj;
	if (rootDirectory == null) {
	    if (other.rootDirectory != null) {
		return false;
	    }
	} else if (!rootDirectory.equals(other.rootDirectory)) {
	    return false;
	}
	return true;
    }

    @Override
    public String getDescription() {
	return "Java SourceCode dependency reader";
    }

}
