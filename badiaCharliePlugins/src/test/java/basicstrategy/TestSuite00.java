package basicstrategy;

import basicstrategy.invalid.HandHasNoCards;
import basicstrategy.invalid.HandHasOneCard;
import basicstrategy.invalid.HandIsNull;
import basicstrategy.invalid.UpCardIsInvalid;
import basicstrategy.invalid.UpCardIsNull;
import basicstrategy.section1.Test00_12_2;
import basicstrategy.section1.Test01_21_6;
import basicstrategy.section1.Test02_17_7;
import basicstrategy.section1.Test03_13_A;
import basicstrategy.section2.Test04_5_2;
import basicstrategy.section2.Test05_11_6;
import basicstrategy.section2.Test06_5_7;
import basicstrategy.section2.Test07_11_A;
import basicstrategy.section3.Test08_A2_2;
import basicstrategy.section3.Test09_A10_6;
import basicstrategy.section3.Test10_A2_7;
import basicstrategy.section3.Test11_A10_A;
import basicstrategy.section4.Test12_22_2;
import basicstrategy.section4.Test13_22_6;
import basicstrategy.section4.Test14_AA_7;
import basicstrategy.section4.Test15_AA_A;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * [Phase 1]
 * 
 * Create a test suite and add the rest of the test
 * case classes, separated by commas and import them.
 * 
 * @author Alex Badia
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    Test00_12_2.class,
    Test01_21_6.class,
    Test02_17_7.class,
    Test03_13_A.class,
    Test04_5_2.class,
    Test05_11_6.class,
    Test06_5_7.class,
    Test07_11_A.class,
    Test08_A2_2.class,
    Test09_A10_6.class,
    Test10_A2_7.class,
    Test11_A10_A.class,
    Test12_22_2.class,
    Test13_22_6.class,
    Test14_AA_7.class,
    Test15_AA_A.class,
    HandHasNoCards.class,
    HandHasOneCard.class,
    HandIsNull.class,
    UpCardIsInvalid.class,
    UpCardIsNull.class
})
public class TestSuite00 {

    @BeforeClass
    public static void setUpClass() throws Exception {
    } // setUpClass

    @AfterClass
    public static void tearDownClass() throws Exception {
    } // tearDownClass

    @Before
    public void setUp() throws Exception {
    } // setUp

    @After
    public void tearDown() throws Exception {
    }  // tearDown
} // TestSuite00
