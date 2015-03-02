package com.github.thehilikus.dependency.analysis.readers;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Tests
 *
 * @author hilikus
 */
public class FileFinderTest {
    private Path testFolder;
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
	Path file1 = createFile("sample.java");

	createFile("blah.ja");
	createFile("no.c");
	Path file2 = createFile("subdir/blah.java");
	createFile("subdir/blah.ja");

	Files.walkFileTree(testFolder, testingUnit);

	List<Path> result = testingUnit.getMatchingFiles();
	assertEquals(result.size(), 2);
	assertTrue(result.contains(file1));
	assertTrue(result.contains(file2));
    }

    private Path createFile(String filename) throws IOException {
	Path file = testFolder.resolve(filename);
	if (filename.contains("/")) {
	    // create parent dirs first
	    Files.createDirectories(file.getParent());
	}
	return Files.createFile(file);
    }

    /**
     * Creates the hierarchy of files
     * 
     * @throws IOException
     */
    @BeforeClass
    public void beforeTests() throws IOException {
	testFolder = Files.createTempDirectory("fileFinder");
    }

}
