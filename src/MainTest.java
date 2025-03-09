import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void testMain_emptyInput() {
        // Prepare
        String input = "";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }


    @Test
    void testMain_singleWord() {
        // Prepare
        String input = "hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator() +
                "hello: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_multipleWords() {
        // Prepare
        String input = "hello world hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator() +
                "hello: 2" + System.lineSeparator() +
                "world: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_multipleWordsWithDifferentCase() {
        // Prepare
        String input = "Hello world hello WORLD";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator() +
                "hello: 2" + System.lineSeparator() +
                "world: 2" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_multipleSpaces() {
        // Prepare
        String input = "hello   world  hello";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator() +
                "hello: 2" + System.lineSeparator() +
                "world: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_complexText() {
        // Prepare
        String input = "This is a complex text with some repeated words. This text is a test.";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator() +
                "a: 2" + System.lineSeparator() +
                "complex: 1" + System.lineSeparator() +
                "is: 2" + System.lineSeparator() +
                "repeated: 1" + System.lineSeparator() +
                "some: 1" + System.lineSeparator() +
                "test: 1" + System.lineSeparator() +
                "text: 2" + System.lineSeparator() +
                "this: 2" + System.lineSeparator() +
                "with: 1" + System.lineSeparator() +
                "words: 1" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_onlySpaces() {
        // Prepare
        String input = "     ";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutput = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator();
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    void testMain_veryLongInput() {
        // Prepare
        StringBuilder longInputBuilder = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            longInputBuilder.append("word").append(i).append(" ");
        }
        String longInput = longInputBuilder.toString();
        InputStream in = new ByteArrayInputStream(longInput.getBytes());
        System.setIn(in);

        // Act
        Main.main(new String[]{});

        // Assert
        String expectedOutputStart = "Welcome to my word counter program!" + System.lineSeparator() +
                "Please enter a block of text: " + System.lineSeparator() +
                "Word Frequencies:" + System.lineSeparator();
        String actualOutput = outContent.toString();
        assertTrue(actualOutput.startsWith(expectedOutputStart));
        for (int i = 0; i < 100000; i++){
            assertTrue(actualOutput.contains("word" + i + ": 1"));
        }
    }
}
