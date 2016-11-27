package com.satybald;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Unit test for simple Client.
 */
public class SchedulerTest
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SchedulerTest(String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SchedulerTest.class );
    }


    public void testTaskScheduling()
    {
        Scheduler<Integer> scheduler = new SchedulerImpl<Integer>();
        LocalDateTime first = LocalDateTime.now();
        LocalDateTime second = first.plusSeconds(2);
        LocalDateTime third = second.plusSeconds(3);
        final AtomicInteger order = new AtomicInteger();

        Task taskThree = scheduler.schedule(third, () -> {
            return order.incrementAndGet();
        });
        Task taskSecond = scheduler.schedule(second, () -> {
            return order.incrementAndGet();
        });
        Task taskOne = scheduler.schedule(first, () -> {
            return order.incrementAndGet();
        });

        assertEquals(1, taskOne.getCallableResult());
        assertEquals(2, taskSecond.getCallableResult());
        assertEquals(3, taskThree.getCallableResult());
    }
}
