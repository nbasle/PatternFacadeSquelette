
import com.yaps.petstore.domain.*;
import com.yaps.petstore.service.CatalogServiceTest;
import com.yaps.petstore.service.CustomerServiceTest;
import com.yaps.petstore.service.OrderServiceTest;
import com.yaps.petstore.uidgen.UniqueIdGeneratorTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class launches all the tests of the application
 */
public final class AllTests extends TestCase {

    public AllTests() {
        super();
    }

    public AllTests(final String s) {
        super(s);
    }

    //==================================
    //=            Test suite          =
    //==================================
    public static TestSuite suite() {

        final TestSuite suite = new TestSuite();

        // Domain
        suite.addTest(CategoryTest.suite());
        suite.addTest(CustomerTest.suite());
        suite.addTest(ItemTest.suite());
        suite.addTest(OrderLineTest.suite());
        suite.addTest(OrderTest.suite());
        suite.addTest(ProductTest.suite());

        // Service
        suite.addTest(CatalogServiceTest.suite());
        suite.addTest(CustomerServiceTest.suite());
        suite.addTest(OrderServiceTest.suite());

        // Util
        suite.addTest(UniqueIdGeneratorTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
