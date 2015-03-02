package com.github.thehilikus.dependency.analysis.readers.java;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.github.thehilikus.dependency.analysis.api.Dependency;
import com.github.thehilikus.dependency.analysis.core.BasicDependency;

/**
 * Tests JavaSourceCodeReader
 *
 * @author hilikus
 */
public class JavaSourceCodeReaderTest {

    private JavaSourceCodeReader testingUnit;

    private static final Path TESTING_DIR = Paths.get("target", "test-classes", "files-test").toAbsolutePath();

    /**
     * Prepares the tests
     * 
     * @throws IOException
     */
    @BeforeMethod
    public void setup() throws IOException {
	testingUnit = new JavaSourceCodeReader(TESTING_DIR);
    }

    /**
     * Tests the basic functionality
     */
    @Test
    public void testGetDependencies() {
	Set<Dependency> result = testingUnit.getDependencies();

	assertEquals(result.size(), 5);

	Set<Dependency> expected = createExpectedSet();

	assertTrue(result.containsAll(expected));
    }

    private static Set<Dependency> createExpectedSet() {
	Set<Dependency> expected = new HashSet<>();
	expected.add(new BasicDependency("1.1"));
	expected.add(new BasicDependency("1.2"));
	expected.add(new BasicDependency("1.3"));

	expected.add(new BasicDependency("3.1"));
	expected.add(new BasicDependency("3.2"));
	return expected;
    }
}
