package com.github.thehilikus.dependency.analysis.readers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests
 *
 * @author hilikus
 */
public class FileFinderTest {
    private FileFinder testingUnit;

    /**
     * Configures the unit tests
     */
    @BeforeTest
    public void setup() {
	testingUnit = new FileFinder("*.java");
    }

    /**
     * Tests the normal behaviour of finding files given a pattern
     * 
     * @throws IOException
     */
    @Test
    public void testFindingFiles() throws IOException {
	Path workingDir = Paths.get("target", "test-classes", "files-test").toAbsolutePath();
	Files.walkFileTree(workingDir, testingUnit);

	List<Path> result = testingUnit.getMatchingFiles();
	assertEquals(result.size(), 3);
	Path file1 = Paths.get("file1.java");
	Path file3 = Paths.get("subdir", "file3.java");
	assertTrue(result.contains(workingDir.resolve(file1)));
	assertTrue(result.contains(workingDir.resolve(file3)));
	
    }

}
