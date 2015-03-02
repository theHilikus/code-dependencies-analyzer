package com.github.thehilikus.dependency.analysis.readers;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * A generic file finder based on a pattern in the file name. To use it use
 * {@link Files#walkFileTree(Path, java.nio.file.FileVisitor)}
 *
 * @author hilikus
 */
public class FileFinder extends SimpleFileVisitor<Path> {
    private PathMatcher matcher;

    private List<Path> matchingFiles;

    /**
     * @param patterns the glob patterns that give a possitive match
     */
    public FileFinder(String patterns) {
	matcher = FileSystems.getDefault().getPathMatcher("glob:" + patterns);
	matchingFiles = new ArrayList<>();
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
	if (matcher.matches(file.getFileName())) {
	    matchingFiles.add(file);
	}

	return FileVisitResult.CONTINUE;
    }

    /**
     * @return the list of files that match the given patterns
     */
    public List<Path> getMatchingFiles() {
	return matchingFiles;
    }

    /**
     * @return true if there is at least one match
     */
    public boolean hasMatches() {
	return !matchingFiles.isEmpty();
    }
}