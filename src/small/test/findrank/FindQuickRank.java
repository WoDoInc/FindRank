package small.test.findrank;

import java.math.BigInteger;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * FindQuickRank finds the ranking for an arbitrary string of potentially duplicated characters out of a possible
 * permutation. Instead of attempting to generate all permutations in lexicographic order, which can be
 * expensive:
 * <li> The rank = rank + (size - currentIndex + 1)!.
 * <li> Use an initially sorted array to swap characters in order to calculate rank.
 *
 * Given the current scope of requirements there are no instance (non-static) methods in this class.
 *
 * @author akaszczuk
 *
 */
public class FindQuickRank
{
	/**
	 * FindQuickRank Constructor.
	 */
	public FindQuickRank()
	{
	}

	////////////////////////////////////////////////////////////////////
	// Private Method(s).
	////////////////////////////////////////////////////////////////////

	/**
	 * An iterative function to find the factorial of n for the given size.
	 * Returns one if n is less than or equal to 1.
	 *
	 * @param n The size of the set.
	 * @return Returns the calculated factorial.
	 */
	private static BigInteger findFactorial(int n)
	{
		BigInteger fact = BigInteger.ONE;
		for (int i = 2 ; i <= n ; ++i)
		{
		   fact = fact.multiply(BigInteger.valueOf(i));
		}

		return fact;
	}

	/**
	 * A method to count characters provided in a string. Unique characters will have a count of one. Duplicate
	 * characters will have a count of n based on the n number of times present in the string.
	 * 
	 * It should also be known that the maximum rank for a given string can be calculated via the following:
	 * size!/(dupC1!*dupC2!*...*dupCn!)
	 * 
	 * Example:
	 * abbacdef
	 * Size of string: 8
	 * Duplications: a -> 2, b -> 2
	 * 
	 * 8!/(2!*2!) = 10080
	 * 
	 * @param initialSet
	 * @return
	 */
	private static HashMap<Character, Integer> countCharacters(char[] initialSet)
	{
		HashMap<Character, Integer> charCounter = new HashMap<>(); 
		for (char c : initialSet)
		{
			Integer count = charCounter.get(c);
		    count = (count == null) ? 0 : count;
		    charCounter.put(c, count + 1);
		}

		return charCounter; 
	}

	/**
	 * A utility method to handle creating convenient mutable character ArrayList objects.
	 *
	 * @param array The character array to convert to ArrayList.
	 * @return Return the new ArrayList.
	 */
	private static ArrayList<Character> createArrayList(char[] array)
	{
		ArrayList<Character> cArrList = new ArrayList<Character>();
		for(char character : array)
		{
			cArrList.add(character);
		}

        return cArrList;
	}

	/**
	 * This method creates a new string by ignoring duplicates in the old string as Java has no convenience method
	 * for removing characters from a string. The method uses StringBuilder to avoid unnecessary string clutter
	 * in memory during the loop before finally returning a string.
	 *
	 * @param string The string argument that contains duplicate characters.
	 * @return Returns the parsed string.
	 * @throws Exception Thrown when an invalid string is passed in as a parameter.
	 */
	private static String removeDuplicates(final String string)
	{
		StringBuilder noDupes = new StringBuilder();
		for (int i = 0; i < string.length(); i++)
		{
			String subString = string.substring(i, i + 1);
			if (noDupes.indexOf(subString) == -1)
			{
				noDupes.append(subString);
			}
		}

		return noDupes.toString();
	}

	////////////////////////////////////////////////////////////////////
	// Public Method(s).
	////////////////////////////////////////////////////////////////////

	/**
	 * Determines the rank of the supplied string by creating an array of the initial characters and sorted
	 * characters to help calculate the rank of each character in each position of the string.
	 *
	 * The first rank in a list would be a string that is pre-sorted.
	 * Thus, for ‘adcb’ the first rank would be ‘abcd’. Compare the string to be found character by character.
	 * If the character is not found swap it with the next and then increment the rank with the factorial based
	 * on the number of remaining characters.
	 *
	 * TODO: Currently this method is missing proper handling/logic to rank when duplicates are present.
	 *
	 * @return Returns the rank as a BigInteger.
	 */
	public static BigInteger findRank(final String string)
	{
		if (string == null || string.isEmpty())
		{
			throw new InvalidParameterException("String cannot be null or empty.");
		}

		final char[] initialSet = string.toCharArray();
		final char[] sorted = string.toCharArray();
		final int size = initialSet.length;
		// final ArrayList<Character> cSortedList = createArrayList(removeDuplicates(string).toCharArray());
		// final int sortedSize = cSortedList.size();
		int index = 0;
		int nextIndex = 1;

		// Map the unique and duplicate characters with their count.
		// HashMap<Character, Integer> countedChars = countCharacters(initialSet);

		// Sort the sorted array. This gives us a subset 'alphabet'.
		Arrays.sort(sorted);

		// Set to one, rank one is the initial starting point.
		BigInteger rank = BigInteger.ONE;

		// Iterate over character arrays.
		while (index != size)
		{
			if (sorted[index] != initialSet[index])
			{
				// Swap the current character in the sorted array with the next character.
				char temp = sorted[index];
				sorted[index] = sorted[nextIndex];
				sorted[nextIndex] = temp;
				nextIndex++;

				// Calculate the current rank.
				rank = rank.add(findFactorial(size - (index + 1)));

				/* BUG: Create the algorithm to correctly calculate rank when duplicates are present using the
				 * non-duplicated list.
				 * The following is not correct, but handles some repeat cases:
				 * 
				 * final int duplicates = countedChars.get(initialSet[index]);
				 * rank = rank.add(findFactorial(sortedSize - (index + 1)).divide(BigInteger.valueOf(duplicates)));
				 */
			}
			else
			{
				index++;
				nextIndex = index + 1;
			}
		}

		return rank;
	}

	/**
	 * Main method for execution/testing. Handle parameter validation in this method, primary execution will
	 * be handled by the {@link FindQuickRank#rank} method.
	 * 
	 * @param args The test argument(s) to accept. In this case, we will only be accepting one argument that allows
	 * a string of (alphanumeric) characters. Numeric characters are generally ranked before alpha characters.
	 * 
	 * For example, "a1234" would be ranked similar to "eabcd".
	 */
	public static void main(final String[] args)
	{
		if (args.length != 1 || args[0] == null || args[0].length() > 25)
		{
			throw new InvalidParameterException("Invalid parameter. Please specify a string up to 25 characters as an argument.");
		}

		final String rankString = args[0];
		System.out.println("The rank for \"" + rankString + "\" is " + FindQuickRank.findRank(rankString));
		System.exit(0);
	}
}
