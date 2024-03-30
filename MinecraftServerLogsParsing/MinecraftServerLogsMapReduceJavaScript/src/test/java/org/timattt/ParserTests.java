package org.timattt;

import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.Assert;
import org.junit.Test;
import org.timattt.jobs.threadDay.ThreadDayMapper;

/**
 * Unit test for simple App.
 */
public class ParserTests {

    @Test
    public void testParser() {
        String in = "[2017-11-29.17:22:26] [Server thread/INFO]: [0;37;22m[[0;36;1mAdministration[0;36;22";
        String out = ThreadDayMapper.parse(in);
        Assert.assertEquals(out, "Server thread:2017-11-29");
    }

}
