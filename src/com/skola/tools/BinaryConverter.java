package com.skola.tools;

public class BinaryConverter
{

	static public String converTextToBinary(String inputText)
	{

		final byte[] bytes = inputText.getBytes();
		final StringBuilder binary = new StringBuilder();
		for (final byte b : bytes)
		{
			int val = b;
			for (int i = 0; i < 8; i++)
			{
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
			binary.append(' ');
		}
		return binary.toString();
	}

}
