import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CompactorVisitorTest {
    @Test
    void testGetCompactedItemsComputed(){
        //Arrange
        int result;
        CompactorVisitor compactorVisitor = new CompactorVisitor();
        Compact compact = new Compact();
        //Accepting the visitor twice!
        compact.accept(compactorVisitor);
        compact.accept(compactorVisitor);

        //Act
        result = compactorVisitor.aCompacted;

        //Assert
        assertEquals(2,result);
    }
}
