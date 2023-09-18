package com.rabobank.cspapp.service.reader.xml;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class XMLStatementReaderTest {
    private static final String VALID_RECORD_AS_LIST = """
                <records>
                  <record reference="138932">
                    <accountNumber>NL90ABNA0585647886</accountNumber>
                    <description>Flowers for Richard Bakker</description>
                    <startBalance>94.9</startBalance>
                    <mutation>+14.63</mutation>
                    <endBalance>109.53</endBalance>
                  </record>
                 </records>
                """;

    private final static String INVALID_RECORDS_AS_LIST =  """
                <XXXrecords>
                  <record reference="138932">
                    <accountNumber>NL90ABNA0585647886</accountNumber>
                    <description>Flowers for Richard Bakker</description>
                    <startBalance>94.9</startBalance>
                    <mutation>+14.63</mutation>
                    <endBalance>109.53</endBalance>
                  </record>
                 </records>
                """;


    private final static String INVALID_ELEMENT_IN_RECORD_AS_LIST =  """
                <records>
                  <record reference="138932">
                    <XXaccountNumber>NL90ABNA0585647886</accountNumber>
                    <description>Flowers for Richard Bakker</description>
                    <startBalance>94.9</startBalance>
                    <mutation>+14.63</mutation>
                    <endBalance>109.53</endBalance>
                  </record>
                 </records>
                """;
    private XMLStatementReader statementReader;


    @BeforeEach
    void setUp() {
        statementReader = new XMLStatementReader();
    }

    @Test
    public void testConvertStringToDocument_ValidXML()  {
        var result = statementReader.read(VALID_RECORD_AS_LIST).get(0);
        assertAll(
                ()->assertEquals(result.reference(),"138932" ),
                ()->assertEquals(result.description(),"Flowers for Richard Bakker" ),
                ()->assertEquals(result.startBalance().doubleValue(),94.9 ),
                ()->assertEquals(result.mutation().doubleValue(),14.63 ),
                ()->assertEquals(result.endBalance().doubleValue(),109.53 )
        );
    }

    @Test
    public void testConvertStringToDocument_InvalidXML()  {
        var result = statementReader.read(VALID_RECORD_AS_LIST).get(0);
        assertAll(
                ()->assertEquals(result.reference(),"138932" ),
                ()->assertEquals(result.description(),"Flowers for Richard Bakker" ),
                ()->assertEquals(result.startBalance().doubleValue(),94.9 ),
                ()->assertEquals(result.mutation().doubleValue(),14.63 ),
                ()->assertEquals(result.endBalance().doubleValue(),109.53 )
        );
    }

    @Test
    public void testConvertStringToDocument_InvalidRootXML()  {
        var throwable = assertThrows(IllegalArgumentException.class,()->
                statementReader.read(INVALID_RECORDS_AS_LIST));
        assertEquals(throwable.getClass(), IllegalArgumentException.class);
    }


    @Test
    public void testConvertStringToDocument_InvalidRecordElementXML()  {
        var throwable = assertThrows(RuntimeException.class,()->
                statementReader.read(INVALID_ELEMENT_IN_RECORD_AS_LIST));
        assertEquals(throwable.getClass(), IllegalArgumentException.class);
    }

}