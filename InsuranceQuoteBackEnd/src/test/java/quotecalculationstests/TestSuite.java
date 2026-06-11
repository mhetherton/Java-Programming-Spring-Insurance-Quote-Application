package quotecalculationstests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

/*
The @Suite annotation is used to indicate that this is a test suite.
The TestSuite class will run all the named test classes.
The test classes are selected using the @SelectClasses annotation.
The classes are listed in the order they will be run. In our case the
order will be:
    TestProductTypeFactor
    TestProductValueFactor
    TestCalculateQuote
*/
@Suite
@SelectClasses({
        TestProductTypeFactor.class,
        TestProductValueFactor.class,
        TestCalculateQuote.class
})

    /*
    This class is used to run all the tests in the test suite.
    It does not contain any methods or fields.
    The test classes are selected using the @SelectClasses annotation.
    */
public class TestSuite
{

} // End of TestSuite class