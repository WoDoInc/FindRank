package small.test.findrank;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import org.junit.Test;

public class Test_FindQuickRank
{
	@Test
	public void smallOrderedTest()
	{
		String[] testString = {"abc", "acb", "bac", "bca", "cab", "cba"};
		BigInteger i = BigInteger.ONE;

		for (final String string : testString)
		{
			assertEquals(i, FindQuickRank.findRank(string));
			i = i.add(BigInteger.ONE);
		}
	}

	@Test
	public void ababTest()
	{
			assertEquals(BigInteger.valueOf(2), FindQuickRank.findRank("abab"));
	}

	@Test
	public void aaabTest()
	{
			assertEquals(BigInteger.valueOf(1), FindQuickRank.findRank("aaab"));
	}

	@Test
	public void baaaTest()
	{
			assertEquals(BigInteger.valueOf(4), FindQuickRank.findRank("baaa"));
	}

	@Test
	public void questionTest()
	{
			assertEquals(BigInteger.valueOf(24572), FindQuickRank.findRank("question"));
	}

	@Test
	public void bookkeeperTest()
	{
			assertEquals(BigInteger.valueOf(10743), FindQuickRank.findRank("bookkeeper"));
	}

	@Test
	public void duplicatesOrderedTest()
	{
		String[] testString = {"aabc", "aacb", "abac", "abca", "acab", "acba", "baac", "baca", "bcaa", "caab", "caba", "cbaa"};
		BigInteger i = BigInteger.ONE;

		for (final String string : testString)
		{
			assertEquals(i, FindQuickRank.findRank(string));
			i = i.add(BigInteger.ONE);
		}
	}
}
