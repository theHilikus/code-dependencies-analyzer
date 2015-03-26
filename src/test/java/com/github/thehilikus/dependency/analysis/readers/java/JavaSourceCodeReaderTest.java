package com.github.thehilikus.dependency.analysis.readers.java;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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
     * @throws InterruptedException 
     */
    @BeforeMethod
    public void setup() throws IOException, InterruptedException {
	testingUnit = new JavaSourceCodeReader(TESTING_DIR);
    }

    /**
     * Tests the basic functionality
     * @throws InterruptedException 
     */
    @Test
    public void testGetDependencies() throws InterruptedException {
	Map<String, Set<String>> result = testingUnit.getDependencies();

	assertEquals(result.size(), 3);

	Map<String, Set<String>> expected = createExpectedMap();

	assertEquals(result.get("com.github.thehilikus.blah"), expected.get("com.github.thehilikus.blah"));
	assertEquals(result.get("a.b.MyPrivateClass"), expected.get("a.b.MyPrivateClass"));
	assertEquals(result.get("other.hello.MyInterface"), expected.get("other.hello.MyInterface"));

    }

    private static Map<String, Set<String>> createExpectedMap() {
	Map<String, Set<String>> result = new HashMap<>();
	Set<String> expectedOne = new HashSet<>();
	expectedOne.add("1.1");
	expectedOne.add("1.2");
	expectedOne.add("1.3");
	result.put("com.github.thehilikus.blah", expectedOne);

	Set<String> expectedThree = new HashSet<>();
	expectedThree.add("3.1");
	expectedThree.add("3.2.hello.world");
	result.put("a.b.MyPrivateClass", expectedThree);
	
	Set<String> expectedFour = new HashSet<>();
	expectedFour.add("4.1");
	result.put("other.hello.MyInterface", expectedFour);

	return result;
    }
}
