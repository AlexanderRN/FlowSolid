/**
 * @author lam
 * 10-10-2012 hau: 
 * Direct file manipulation removed (the test should not require more than is 
 * expressed in the interface  - ex: a specific file format)
 * Use of special danish characters omitted
 * Added negative tests
 * 9-10-2013 hau: 
 * automatic filename generation deleted (filename is not part of interface)
 * 3-3-2014 pelo:
 * Added extensive javadoc explanation. 
 * Increased testTries from 30 to 300 in testGetRandomQuestion()
 * 12-4-2014 Stefan (student at Cphbusiness.dk) 
 * Added code to make this test run on a unix like filesystem
 */

import Interfaces.WordPairControlInterface;
import Control.Controller;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * Unit test for the WordPairDemo interface
 */
public class JUnitTest
{

    private WordPairControlInterface Controller;    
    private static String fileName = getFilenameGuid();


    /**
     * Utility method to generate a random filename which can't exist before on
     * users computer. It generates a random number and adds the .txt
     * extension to it. The method asks for the current users home directory and
     * saves the file there. A new file is created each time the test is run.
     * You can find them in your own home directory: "C:\User\Peter\"
     */
    private static String getFilenameGuid()
    {
        // Value to use a Windows or Unix like filesystem.
        // Set to false if you use a Mac or linux variant.
        boolean WindowsOS = false;
        
        String path = System.getProperty("user.home");
        if(WindowsOS){
            return path + "\\" + UUID.randomUUID().toString() + ".txt";
        }else{
            return path + "/" + UUID.randomUUID().toString() + ".txt";
        }
        
    }


    /**
     * This method is executed EVERY TIME just before each of the test methods
     * are executed. Thus 3 new words are entered into the collection so that
     * the every testmethod has some content to test on.
     */
    @Before
    public void setUp()
    {
        //REPLACE WordPairDemoV1    WITH YOUR OWN CLASS THAT IMPLEMENTS THE INTERFACE
        Controller = new Controller(); // !!!!! REPLACE HERE !!!!!
        assertTrue(Controller.size() == 0);
        Controller.add("hest", "horse");      //We trust the add() method here!
        Controller.add("hus", "house");
        Controller.add("bord", "table");
    }

    @After
    public void tearDown()
    {
    }

//////////////////////////////////////////////// THE ACTUAL TEST METHODS  ////////////////////////////////////


    @Test
    /**
     * This test requires that both save and load are implemented, since the
     * test strategy is: Save the file, clear the content of the list, and read
     * the file back in and check the result.
     */
    public void testLoad()
    {
        assertTrue(Controller.save("wordpair.txt"));
        Controller.clear();
        assertTrue(Controller.load("wordpair.txt"));
        assertTrue(Controller.size() == 3);
        assertEquals(Controller.lookup("hest"), "horse");
        assertEquals(Controller.lookup("hus"), "house");
        assertEquals(Controller.lookup("bord"), "table");
    }

    @Test
    /**
     * This test is redundant (covered by testLoad).
     */
    public void testSave()
    {
    }

    /**
     * This test first tries to find an existing word pair.
     */
    @Test
    public void testLookup_1()
    {
        assertEquals("Looked up hest. Expected: horse. Got: " + Controller.lookup("hest"),
                Controller.lookup("hest"), "horse");
    }

    /**
     * However, this is not if the first word exist. If the first word is found
     * (hest) then the returning word must also match. In other words :-) an
     * implementation that simply returns true on the first word and ignores the
     * second will fail the second part this test case.
     */
    @Test
    public void testLookup_SecondWord()
    {
        assertNotSame("Expected: something different from Cow. Got: " + Controller.lookup("hest"),
                Controller.lookup("hest"), "Cow");
    }

    @Test
    public void testClear()
    {
        Controller.clear();
        int collectionSize = Controller.size();
        assertTrue("Cleared the collection of word pairs. Expected size: 0. Got size: " + collectionSize, collectionSize == 0);
    }

    /**
     * Since the setUp() method already added 3 word pairs, there should be 4
     * after this method adds the ko-cow pair.
     */
    @Test
    public void testAdd_4()
    {
        Controller.add("ko", "cow");
        int resultSize = Controller.size();
        assertTrue("Added a 4th wordpair. Expected size: 4. Got size: " + resultSize, resultSize == 4);

        assertEquals(Controller.lookup("ko"), "cow");
    }

    /**
     * Checking to see if a wordpair that we add is actually there.
     */
    @Test
    public void testAdd_GotItRight()
    {
        Controller.add("ko", "cow");
        String lookUpResult = Controller.lookup("ko");
        assertEquals("Added wordpair ko-cow and called loookup(). Expected cow. Got: " + lookUpResult, lookUpResult, "cow");
    }

    @Test
    /**
     * This method simply calls the getEandomQuestion() 300 times. Becuase the
     * setUp() method (see above) has added 3 word pairs we should be able to
     * get them all with 300 tries. That is what is asserted at the bottom of
     * this method.
     *
     * This does not test the distribution, but if we belive in the random
     * generators it tests the "border values" in that all possible values must
     * be generated.
     *
     */
    public void testGetRandomQuestion()
    {
        int testTries = 300;
        boolean horseFound = false;
        boolean houseFound = false;
        boolean tabelFound = false;

        for (int i = 0; i < testTries; i++)
        {
            String question = Controller.getRandomQuestion();
            if (question.equals("hest"))
            {
                horseFound = true;
            }
            if (question.equals("hus"))
            {
                houseFound = true;
            }
            if (question.equals("bord"))
            {
                tabelFound = true;
            }
        }
        String errorMessage = "Expected horseFound: true, houseFound: true, tableFound: true. Got: hoserFound: " + horseFound
                + ", houseFound: " + houseFound + ", tableFound: " + tabelFound;

        assertTrue(errorMessage, horseFound && houseFound && tabelFound);
    }

    @Test
    public void testCheckGuessTrue()
    {
        assertTrue("Expected true. Got: " + Controller.checkGuess("hest", "horse") + " for the wordpair hest, horse",
                Controller.checkGuess("hest", "horse"));
    }

    /**
     * It's not enough that the word "hest" exists. The answer "cow" is wrong
     * and the method checkGuess should return false for the question-answer
     * pair "hest"-"cow".
     */
    @Test
    public void testCheckGuessBoth()
    {
        assertFalse("Expected false. Got: " + Controller.checkGuess("hest", "cow") + " for the wordpair hest,cow",
                Controller.checkGuess("hest", "cow"));
    }

    @Test
    public void testGetSize()
    {
        assertTrue("Expected size 3. Got: " + Controller.size(), Controller.size() == 3);
    }
}
 