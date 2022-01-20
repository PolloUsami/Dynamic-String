
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class offers an more Specific and Exception safe
 * form of modificating an string, implementing more friendly
 * methods for Modificating Specific parts of the string.
 * 
 * 
 * @author PolloUsami
 * 
 * @see CharSequence
 * @see String
 * @see StringBuffer
 * @see StringBuilder
 * 
 * @implNote
 * This class is Slooooooow for making 
 * anything but is in part a form for
 * making it safe for a thread, sinse
 * the worst part of a mutable String 
 * is working with multiple threads that
 * may access it
 * 
 */
public class StringStack implements CharSequence, Iterable<Character>, Cloneable {
	/**The name explains itself */
	private static final char[] Empty = {};
	
	/**This is used as a return */
	private static final Character Null_Character = 0;

	/**
	 * this method is just a bridge of extracting the
	 * characters in a multi - char to an array 
	 * becouse {'h'} only can be used as constructors
	 * 
	 * @param Varargs Characters
	 * @return The same Characters
	 */
	public static char[] extract(char...characters) {
		return characters;
	}
	/**
	 * this method iterates over a CharSequence creating
	 * an array of all the characters inside, this method
	 * PRAY that the Sequence does not change during the
	 * read or else it can throw an exception or be 
	 * incomplete or cutted
	 * 
	 * @param Sequence an source for reading the characters
	 * @return An array of all the characters in that sequence
	 */
	public static char[] extract(CharSequence Sequence) {
		if(Sequence==null) {return Empty;}
		char[] Chars = new char[Sequence.length()];
		for(int x=0;x<Sequence.length();x++)
		{Chars[x]=Sequence.charAt(x);}
		return Chars;
	}
	/**
	 * Creates a Iterator for the given char
	 * array, one the iterator has been created
	 * any modification to the original array does 
	 * nothing in the contents of this iterator
	 * 
	 * @param characters for the Iterator
	 * @return an Character Iterator
	 */
	public static Iterator<Character> extractIterator(char[] characters) {
		return new Iterator<>() {
			int Cursor;
			final char[] Contents = Arrays.copyOf(characters,characters.length);
			@Override
			public boolean hasNext() {
				return (Cursor<Contents.length);
			}
			@Override
			public Character next() {
				if(!hasNext())
				{throw new NoSuchElementException();}
				return Contents[Cursor++];
			}
		};
	}
	/**
	 * this method first calls {@link #extractIterator(CharSequence)}
	 * an then returns {@link #extractIterator(char[])} with it
	 * 
	 * @param Sequence for obtaining the characters
	 * @return an Character Iterator of the given Sequence
	 */
	public static Iterator<Character> extractIterator(CharSequence Sequence) {
		return extractIterator(extract(Sequence));
	}
	
	/**
	 * Creates a new Empty
	 * String
	 */
	public StringStack() {
		this(null,-1,-2);
	}
	/**
	 * Creates a new String
	 * containing an copy of
	 * the given char array
	 * 
	 * @param Characters to contain
	 */
	public StringStack(char[] Characters) {
		this(Characters,0,Characters.length);
	}
	/**
	 * Creates a new String containing all
	 * the Characters of the given String
	 * 
	 * @param String the reference String
	 */
	public StringStack(String String) {
		this(String.toCharArray(),0,String.length());
	}
	/**
	 * Creates a new String with a copy of all the
	 * characters in the given sequence
	 * 
	 * @param CharSequence the Sequence for copy
	 */
	public StringStack(CharSequence CharSequence) {
		this(extract(CharSequence));
	}
	/**
	 * Its the main Constructor that takes an char
	 * array and based of the bounds given it will
	 * output a String with the length desired
	 * 
	 * @param Characters the contents for copy
	 * @param Start the start of the characters
	 * @param End the end of the characters
	 */
	protected StringStack(char[] Characters, int Start, int End) {
		Controller("constructor",Characters,Start,End);
	}
	
	
	private volatile char[] Contents = Empty;
	
	/**
	 * Since any desincronization will basicaly result
	 * on throwing an exception, all the methods that 
	 * need to be executed in this String are managed here
	 * for more specific information about any method see
	 * they callers
	 * 
	 * @param <Output> An Multi Output Paramether
	 * @param Event The Operation that will be executed
	 * @param Args The Values that will be worked with
	 * @return Any Value Needed for The String
	 * @throws IllegalStateException if the Event mask is not defined in here
	 */
	@SafeVarargs
	@SuppressWarnings("unchecked")
	protected synchronized final <Output> Output Controller(String Event, Object...Args) {
		char[] Characters;

		//Methods that does'nt modify the "Contents" Size
		switch(Event) {
		case "peek":
			{return (Output) VolatileStack.peek(Contents);}
		case "contains_0":
			{return (Output) VolatileStack.contains(Contents,(char)Args[0]);}
		case "contains_1":
			{return (Output) ((VolatileStack.contains(Contents,(CharSequence)Args[0])==-1)?Boolean.FALSE:Boolean.TRUE);}
		case "replace_0":
			{VolatileStack.replace(Contents,(char)Args[0],(char)Args[1]);return null;}
		case "lower_case":
			{VolatileStack.toLowerCase(Contents);return null;}
		case "upper_case":
			{VolatileStack.toUpperCase(Contents);return null;}
		case "rule_case":
			{VolatileStack.toSpecialCase(Contents);return null;}
		case "set_0":
			{return (Output) VolatileStack.set(Contents,(int)Args[0],(char)Args[1]);}
		case "set_1":
			{VolatileStack.set(Contents,(int)Args[0],(boolean)Args[1]);return null;}
		case "swap":
			{VolatileStack.swap(Contents,(int)Args[0],(int)Args[1]);return null;}
		case "length":
			{return (Output) VolatileStack.length(Contents);}
		case "index_0":
			{return (Output) VolatileStack.indexOf(Contents,(char)Args[0]);}
		case "index_1":
			{return (Output) VolatileStack.lastIndexOf(Contents,(char)Args[0]);}
		case "occurrences":
			{return (Output) VolatileStack.occurrences(Contents,(char)Args[0]);}
		case "char_at":
			{return (Output) VolatileStack.charAt(Contents,(int)Args[0]);}
		case "empty":
			{return (Output) VolatileStack.isEmpty(Contents);}
		case "blank":
			{return (Output) VolatileStack.isBlank(Contents);}
		case "clone":
			{return (Output) new StringStack(Contents);}
		case "to_string":
			{return (Output) new String(Contents);}
		case "to_char_array":
			{return (Output) Arrays.copyOf(Contents,Contents.length);}
		case "iterator":
			{return (Output) extractIterator(Contents);}
		case "sub_sequence":
			{return (Output) new StringStack(Contents,(int)Args[0],(int)Args[1]);}
		
		}
		
		//Methods That Modify the "Contents" Size and Elements
		switch(Event) {
		case "constructor": {
			Characters 	= (char[])	Args[0];
			int Start 	= (Integer)	Args[1];
			int End 	= (Integer)	Args[2];
			if(End<Start)				{break;}
			if(Start<0)					{Start=0;}
			if(End>Characters.length) 	{End=Characters.length;}
			int Length = End - Start;
			
			if(Characters.length<=Length) 
			{Contents = Arrays.copyOf(Characters,Characters.length);break;}
			else {
				if(Start==End)
				{Contents = extract(Characters[Start]);break;}
				
				Contents = new char[Length];
				for(int x=0;x<Length;x++)
				{Contents[x]=Characters[x+Start];}
				break;
			}
		}

		case "push": 
		{
			Characters = new char[Contents.length+1];
			System.arraycopy(Contents,0,Characters,0,Contents.length);
			Characters[Contents.length] = (char) Args[0];
			Contents = Characters;
			break;
		}
		case "pop": {
			if(Contents.length==0) {return (Output) Null_Character;}
			Characters = new char[Contents.length-1];
			Character Out = Character.valueOf(Contents[Contents.length-1]);
			for(int x=0;x<Characters.length;x++)
			{Characters[x]=Contents[x];}
			Contents = Characters;
			return (Output) Out;
		}
		case "concatenate": {
			String S = String.valueOf(Args[0]);
			Characters = new char[Contents.length+S.length()];
			System.arraycopy(Contents,0,Characters,0,Contents.length);
			System.arraycopy(S.toCharArray(),0,Characters,Contents.length,S.length());
			Contents = Characters;
			break;
		}
		case "replace_1": {
			CharSequence Old = (CharSequence) Args[0];
			CharSequence New = (CharSequence) Args[1];
			if(Old==null||New==null) {break;}
			int Index = VolatileStack.contains(Contents,Old);
			if(Index==-1) {break;}
			VolatileStack vol = new VolatileStack();
			boolean Boolean = true;
			for(int x=0;x<Contents.length;x++) {
				if(Index==x) {
					vol.add(New);Boolean=false;
				} else {
					if(x>=Index&&x<(Index+Old.length())) {continue;}
					if(Boolean)
					{vol.push(Contents[x]);}
					else {vol.push(Contents[x-Old.length()]);}
				}
			}
			Contents = Arrays.copyOf(vol.Contents,vol.length());
			break;
		}
		case "remove_0": {
			if(Contents.length==0) {return (Output) Null_Character;}
			int Index = (int) Args[0];
			if(Index<0) {Index=0;}
			if(Index>=Contents.length) {Index=Contents.length-1;}
			Characters = new char[Contents.length-1];
			Character Char = Contents[Index];
			boolean Boolean = true;
			for(int x=0;x<Characters.length;x++) {
				if(x==Index)
				{Boolean=false;}
				if(Boolean)
				{Characters[x]=Contents[x];}
				else
				{Characters[x]=Contents[x+1];}
			}
			Contents = Characters;
			return (Output) Char;
		}
		case "remove_1": {
			char Character = (char) Args[0];
			int Ammount = VolatileStack.occurrences(Contents,Character);
			if(Ammount==0) {break;}
			Characters = new char[Contents.length-Ammount];
			int Delay = 0;
			for(int x=0;x<Contents.length;x++) {
				if(Contents[x]==Character) {Delay++;}
				else {Characters[x-Delay] = Contents[x];}
			}
			Contents = Characters;
			break;
		}
		case "remove_2": {
			CharSequence Sequence = (CharSequence) Args[0];
			if(Sequence==null) {return (Output) Integer.valueOf(-1);}
			Integer Index = VolatileStack.contains(Contents,Sequence);
			if(Index==-1) {return (Output) Index;}
			VolatileStack vol = new VolatileStack();
			for(int x=0;x<Contents.length;x++) {
				if(x>=Index&&x<(Index+Sequence.length())) {continue;}
				vol.push(Contents[x]);
			}
			Contents = Arrays.copyOf(vol.Contents,vol.length());
			return (Output) Index;
		}
		case "cut_0": {
			int Start = (int) Args[0];
			int End = (int) Args[1];
			if(Start<0) {Start=0;}
			if(Start>=Contents.length) {Start=Contents.length;}
			if(End<0) {End=0;}
			if(End>=Contents.length) {End=Contents.length;}
			if(Contents.length==0||End<=Start) {return (Output) new StringStack("");}
			VolatileStack Keep = new VolatileStack();
			VolatileStack Throw = new VolatileStack();
			for(int x=0;x<Contents.length;x++) {
				if(x>=Start&&x<End)
				{Throw.push(Contents[x]);}
				else Keep.push(Contents[x]);
			}
			Contents = Arrays.copyOf(Keep.Contents,Keep.length());
			return (Output) new StringStack(Throw.Contents);
		}
		case "cut_1": {
			int Size = (int) Args[0];
			if(Size<=0) {break;}
			VolatileStack Keep = new VolatileStack();
			VolatileStack Throw = new VolatileStack();
			for(int x=0;x<Contents.length;x++) {
				if(Size-->0)
				{Throw.push(Contents[x]);}
				else Keep.push(Contents[x]);
			}
			Contents = Arrays.copyOf(Keep.Contents,Keep.length());
			return (Output) new StringStack(Throw.Contents);
		}
		case "cut_2": {
			int Size = (int) Args[0];
			if(Size<=0) {break;}
			VolatileStack Keep = new VolatileStack();
			VolatileStack Throw = new VolatileStack();
			for(int x=(Contents.length);x>=0;x--) {
				if(Size-->0)
				{Throw.push(Contents[x]);}
				else Keep.push(Contents[x]);
			}
			Contents = Arrays.copyOf(Keep.Contents,Keep.length());
			return (Output) new StringStack(Throw.Contents);
		}
		case "clear": {
			StringStack Old = new StringStack(Contents);
			Contents = Empty;
			return (Output) Old;
		}
		case "override":{
			StringStack Old = new StringStack(Contents);
			Contents = extract((CharSequence)Args[0]);
			return (Output) Old;
		}
		case "insert": {
			CharSequence Sequence = (CharSequence) Args[0];
			if(Sequence==null) {break;}
			int Index = (int) Args[1];
			if(Index<0) {Index=0;}
			if(Index>=Contents.length) {Index=Contents.length;}
			VolatileStack vol = new VolatileStack();
			for(int x=0;x<Contents.length;x++) {
				if(x==Index)
				{vol.add(Sequence);}
				vol.push(Contents[x]);
			}
			if(Index==Contents.length)
			{vol.add(Sequence);}
			Contents = Arrays.copyOf(vol.Contents,vol.length());
			break;
		}

		default:
			throw new IllegalStateException("Unrecognized Event Mask: "+Event);
		}
		
		return null;
	}
	
		/**
		 * This concatenates the given Character at the
		 * end of this String or the left side of it
		 * 
		 * @param Character the character to Push
		 */
		public void push(char Character) {
			Controller("push",Character);
		}
		/**
		 * This method returns the character
		 * that is at the tail of this string
		 * returning "character 0" if the String
		 * is empty, <p>
		 * this method is a more secure for of
		 * obtaining the character instead of
		 * using charAt( length() - 1 )
		 * 
		 * @return the last character
		 */
		public char peek() {
			return Controller("peek");
		}
		/**
		 * This method returns and removes
		 * the last character of this string
		 * if the string is empty it will return
		 * the "character 0"
		 * 
		 * @return the last character 
		 */
		public char pop() {
			return Controller("pop");
		}
		/**
		 * This method is a multi use, it accepts
		 * any object as input and uses its toString()
		 * method as a input, an then it gets added at the
		 * end of the String, for inserting a Value into
		 * a specific point of this string use {@link #insert(Object,int)}
		 * 
		 * @param <Text> Any Value 
		 * @param Text The Paramether to Add
		 * @throws NullPointerException if Text is Null
		 */
		public <Text> void concat(Text Text) {
			if(Text==null)
			{throw new NullPointerException("cant concatenate the value \"null\"");}
			Controller("concatenate",Text);
		}
		/**
		 * checks if this String contains the specific
		 * character, it does care if the Character is
		 * upper or lower case
		 * 
		 * @param Character to Comparate
		 * @return True if the String contains the character
		 */
		public boolean contains(char Character) {
			return Controller("contains_0",Character);
		}
		/**
		 * Checks if any point of this String
		 * contains the desired Sequence returning true
		 * if it exist
		 * 
		 * @param Sequence for Comparing 
		 * @return True if this String contains The Specific Sequence
		 */
		public boolean contains(CharSequence Sequence) {
			if(Sequence==null)
			{return false;}
			return Controller("contains_1",Sequence);
		}
		/**
		 * Replaces all the Characters in 
		 * this String that are equal to 
		 * the old character and replaces
		 * them with the new one
		 * 
		 * @param Old char to Replace
		 * @param New char to Put
		 */
		public void replace(char Old, char New) {
			Controller("replace_0",Old,New);
		}
		/**
		 * Replaces the First Ocurrence of the 
		 * given Sequence with a new One
		 * 
		 * @param Old sequence to remove
		 * @param New sequence to put instead
		 * @throws NullPointerException if the first sequence is null
		 */
		public void replace(CharSequence Old, CharSequence New) {
			if(Old==null)
			{throw new NullPointerException("Cant replace the Sequence becouse the Old one is Null");}
			//Replacing a Sequence is null is basically removing it with a extra step
			if(New==null)
			{New="";}
			Controller("replace_1",Old,New);
		}
		/**
		 * Sets all the Characters in
		 * here to Lower Case
		 */
		public void toLowerCase() {
			Controller("lower_case");
		}
		/**
		 * Sets all the Characters in
		 * here to Upper Case
		 */
		public void toUpperCase() {
			Controller("upper_case");
		}
		/**
		 * More or less aply basic
		 * semantic rules for this
		 * string, it will Aply uppercase
		 * to the first letter and to any
		 * letter after a '.' or a ';'
		 */
		public void ruleAppliedCase() {
			Controller("rule_case");
		}
		/**
		 * Sets the Character in that specific index
		 * the given one returning the one that was there
		 * <p>
		 * if the index is lower than 0 it will insert
		 * the character at the start
		 * <p> 
		 * if the index is higher that the length of this
		 * string it will add it at the end
		 * <p>
		 * both of those cases will return a null character
		 * becouse it created a new space not removed one
		 * 
		 * @param Index to Insert The Character
		 * @param Character to Override that location
		 * @return The Character that was there
		 */
		public char set(int Index, char Character) {
			if(Index<0)
			{insert(Character,0);return 0;}
			if(Index>=Contents.length)
			{push(Character);return 0;}
			return Controller("set_0",Index,Character);
		}
		/**
		 * This method turn the character in the specified
		 * index Uppercase if the UpperCase parameter
		 * is True and LowerCase if it is False
		 * <p>
		 * if the index is out of bounds it returns
		 * withouth modificating anything
		 * 
		 * @param Index the Character to change Case
		 * @param UpperCase if the character will be uppercase
		 */
		public void set(int Index, boolean UpperCase) {
			if(Index<0||Index>=Contents.length)
			{return;}
			Controller("set_1",Index,UpperCase);
		}
		/**
		 * Swap of place the two characters from the given indexes
		 * <p>
		 * if any index is out of bounds it returns withouth
		 * modificating anything
		 * 
		 * @param IndexA The index of the First Character
		 * @param IndexB The index of the Second Character
		 */
		public void swap(int IndexA, int IndexB) {
			if(IndexA<0||IndexB<0)
			{return;}
			if(IndexA>=Contents.length||IndexB>=Contents.length)
			{return;}
			Controller("swap",IndexA,IndexB);
		}
		/**
		 * Removes the Character in that index, shortening 
		 * the String by the Way
		 * <p>
		 * if The Index is Lower that 0 it will remove the first
		 * character in this String
		 * <p>
		 * if the Index is Greater that the length of this 
		 * String it will pop the last character in this String
		 * 
		 * @param Index to remove
		 * @return the character in the specified Index
		 */
		public char remove(int Index) {
			if(Index<0)
			{Index=0;}
			if(Index>=Contents.length)
			{return pop();}
			return Controller("remove_0",Index);
		}
		/**
		 * Removes all the characters that 
		 * are equal to the Given character
		 * 
		 * @param Character character to Purge
		 */
		public void remove(char Character) {
			Controller("remove_1",Character);
		}
		/**
		 * Removes the First ocurrance of the
		 * Sequence, returning '-1' if the
		 * Sequence does not exist
		 * 
		 * @param Sequence Sequence to remove
		 * @return The index of the Sequence
		 */
		public int remove(CharSequence Sequence) {
			if(Sequence==null)
			{return -1;}
			return Controller("remove_2",Sequence);
		}
		/**
		 * Removes a Chunk of this String
		 * returning it in the process
		 * 
		 * @param Start to Start Cutting
		 * @param End to End Cutting
		 * @return the Cutted Piece
		 */
		public CharSequence cut(int Start, int End) {
			if(End<Start) {return new StringStack("");}
			if(Start<0){Start=0;}
			if(Start>=Contents.length)
			{Start=Contents.length;}
			if(End<0)	//Why?
			{return new StringStack("");}
			if(End>=Contents.length)
			{End=Contents.length;}
			return Controller("cut_0",Start,End);
		}
		/**
		 * Cuts the Specified Ammount of Characters
		 * from Front the String and returns them
		 * <p>
		 * if the Ammount is greater than the length 
		 * of this string it cuts all of it
		 * <p>
		 * if the Ammount is less than 0
		 * it does not cut anything
		 * 
		 * @param Ammount of Characters to Cut
		 * @return the Cutted Characters
		 */
		public CharSequence cutFront(int Ammount) {
			if(Ammount<=0)
			{return new StringStack("");}
			if(Ammount>=Contents.length)
			{return clear();}
			return Controller("cut_1",Ammount);
		}
		/**
		 * Cuts the Specified Ammount of Characters
		 * from Back the String and returns them
		 * <p>
		 * if the Ammount is greater than the length 
		 * of this string it cuts all of it
		 * <p>
		 * if the Ammount is less than 0
		 * it does not cut anything
		 * 
		 * @param Ammount of Characters to Cut
		 * @return the Cutted Characters
		 */
		public CharSequence cutBack(int Ammount) {
			if(Ammount<=0)
			{return new StringStack("");}
			if(Ammount>=Contents.length)
			{return clear();}
			return Controller("cut_2",Ammount);
		}
		@Override
		public int length() {
			return Controller("length");
		}
		/**
		 * Checks if this String Contains
		 * the Specified Character and return
		 * his index, if the character is not
		 * in this string it returns '-1'
		 * 
		 * @param Character to Search the index
		 * @return the index of the character
		 */
		public int indexOf(char Character) {
			return Controller("index_0",Character);
		}
		/**
		 * Checks if this String Contains
		 * the Specified Character and return
		 * his last index, if the character is 
		 * not in this string it returns '-1'
		 * 
		 * @param Character to Search his last index
		 * @return the last index of the character
		 */
		public int lastIndexOf(char Character) {
			return Controller("index_1",Character);
		}
		/**
		 * Count all the times that the 
		 * Character appear in this String
		 * and returns them
		 * 
		 * @param Character to search
		 * @return all the times that the character appear
		 */
		public int occurrencesOf(char Character) {
			return Controller("occurrences",Character);
		}
		@Override
		public char charAt(int Index) {
			if(Index<0||Index>=Contents.length)
			{return 0;}
			return Controller("char_at",Index);
		}						
		@Override
		public boolean isEmpty() {
			return Contents.length==0;
		}
		/**
		 * Returns true if the String is
		 * completelly filled with null
		 * character or space
		 * 
		 * @return true if everything is ' ' or null
		 */
		public boolean isBlank() {
			return Controller("blank");
		}
		/**
		 * Return this String as separated
		 * and Empties this String
		 * 
		 * @return this String
		 */
		public StringStack clear() {
			return Controller("clear");
		}
		/**
		 * Sets this String equal to the Sequence given
		 * Returning the Old contents
		 * <p>
		 * this method calls {@link #extract(CharSequence)}
		 * and replaces the contents with that
		 * 
		 * @param Contents to replace this String
		 * @return this String
		 */
		public StringStack override(CharSequence Contents) {
			return Controller("override",Contents);
		}
		@Override
		public StringStack clone() {
			return Controller("clone");
		}
		@Override
		public boolean equals(Object other) {
			if(this==other)
			{return true;}
			if(other==null)
			{return false;}
			if(other instanceof CharSequence Sequence)
			{
				String S = Sequence.toString();
				return toString().equals(s);
			}
			return false;
		}
		@Override
		public String toString() {
			return new String(Contents);
		}
		/**
		 * Copies All the characters
		 * in this String and returns them
		 * any modification to the returned
		 * array will not modify this String
		 * 
		 * @return all the characters in this String
		 */
		public char[] toCharArray() {
			return Arrays.copyOf(Contents,Contents.length);
		}
		/**
		 * Insert the "Text" in the string at the specified
		 * index, shifting any characters to the left if
		 * needed for making enought space
		 * 
		 * @param <Text> Any Paramether for Convenience
		 * @param Text The Value to Insert
		 * @param In The Index to Add this Text
		 * @throws NullPointerException if Text is null
		 */
		public <Text> void insert(Text Text, int In) {
			if(Text==null)
			{throw new NullPointerException("cant insert a \"null\" value");}
			Controller("insert",Text,In);
		}
		/**
		 * Return a new Iterator for this String
		 * 
		 * this Method is equals to extractIterator(Contents)
		 * 
		 * @return an Iterator for this String
		 */
		public Iterator<Character> iterator() {
			return extractIterator(Contents);
		}
		@Override
		public StringStack subSequence(int Start, int End) {
			if(End<Start||End<0)
			{return new StringStack("");}
			if(Start<0)
			{Start=0;}
			if(Start>=Contents.length)
			{Start=Contents.length;}
			if(End>=Contents.length)
			{End=Contents.length;}
			return new StringStack(Contents,Start,End);
		}

		/**
		 * This is a convenience class for
		 * working with a string, all the 
		 * methods that does not care about
		 * synchronization are here for easy
		 * access
		 */
		private static final class VolatileStack {
		
		volatile char[] Contents = {};
		
		public void push(char Character) {
			char[] Characters = new char[Contents.length+1];
			System.arraycopy(Contents,0,Characters,0,Contents.length);
			Characters[Contents.length] = Character;
			Contents = Characters;
		}
		public <Element> void add(Element Element) {
			String S = String.valueOf(Element);
			char[] Characters = new char[Contents.length+S.length()];
			System.arraycopy(Contents,0,Characters,0,Contents.length);
			System.arraycopy(S.toCharArray(),0,Characters,Contents.length,S.length());
			Contents = Characters;
		}
		public int length() {
			return Contents.length;
		}
		
		
		
		public static Character peek(char[] Contents) {
			if(Contents.length==0) {return 0;}
			return Contents[Contents.length-1];	
		}
		public static Boolean contains(char[] Contents, char Character) {
			for(char Char: Contents)
			{if(Char==Character) {return true;}}
			return false;
		}
		public static Integer contains(char[] Contents, CharSequence Sequence) {
			int Index = -1;
			int Segments = 0;
			for(int x=0;x<Contents.length;x++) {
				if(Segments==Sequence.length()) {return Index;}
				if(Contents[x]==Sequence.charAt(0)&&Segments==0)
				{Index=x;}
				if(Contents[x]==Sequence.charAt(Segments)) {Segments++;}
				else {Segments=0;}
			}
			if(Segments==Sequence.length()) {return Index;}
			return -1;
		}
		public static void replace(char[] Contents, char Old, char New) {
			for(int x=0;x<Contents.length;x++)
			{if(Contents[x]==Old) {Contents[x]=New;}}
		}
		public static void toLowerCase(char[] Contents) {
			changeCase(Contents,false);
		}
		public static void toUpperCase(char[] Contents) {
			changeCase(Contents,true);
		}
		public static void toSpecialCase(char[] Contents) {
			boolean Case = true;
			char Char;
			for(int x=0;x<Contents.length;x++) {
				Char = Contents[x];
				if(Character.isWhitespace(Char))
				{continue;}
				if(Char=='.'||Char==';')
				{Case=true;continue;}
				if(Character.isUpperCase(Char))
				{Char = Character.toLowerCase(Char);}
				if(Case)
				{Char = Character.toUpperCase(Char);Case=false;}
				Contents[x] = Char;
			}
		}
		private static void changeCase(char[] Contents, boolean UpperCase) {
			for(int x=0;x<Contents.length;x++)
			{Contents[x]=UpperCase?Character.toUpperCase(Contents[x]):Character.toLowerCase(Contents[x]);}
		}
		public static Character set(char[] Contents, int Index, char Character) {
			if(Index<0||Index>=Contents.length) {return 0;}
			char Char = Contents[Index];
			Contents[Index] = Character;
			return Char;
		}
		public static void set(char[] Contents, int Index, boolean UpperCase) {
			if(Index<0||Index>=Contents.length) {return;}
			Contents[Index] = UpperCase?Character.toUpperCase(Contents[Index]):Character.toLowerCase(Contents[Index]);
		}
		public static void swap(char[] Contents, int IndexA, int IndexB) {
			if(IndexA<0||IndexA>=Contents.length) {return;}
			if(IndexB<0||IndexB>=Contents.length) {return;}
			char Carry = Contents[IndexA];
			Contents[IndexA] = Contents[IndexB];
			Contents[IndexB] = Carry;
		}
		public static Integer length(char[] Contents) {
			return Contents.length;
		}
		public static Integer indexOf(char[] Contents, char Character) {
			for(int x=0;x<Contents.length;x++)
			{if(Contents[x]==Character) {return x;}}
			return -1;
		}
		public static Integer lastIndexOf(char[] Contents, char Character) {
			for(int x=(Contents.length-1);x>=0;x--)
			{if(Contents[x]==Character) {return x;}}
			return -1;
		}
		public static Integer occurrences(char[] Contents, char Character) {
			int Occurrences = 0;
			for(char Char : Contents)
			{if(Char==Character) {Occurrences++;}}
			return Occurrences;
		}
		public static Character charAt(char[] Contents, int Index) {
			if(Index<0||Index>=Contents.length) {return 0;}
			return Contents[Index];
		}
		public static Boolean isEmpty(char[] Contents) {
			return Contents.length==0;
		}
		public static Boolean isBlank(char[] Contents) {
			for(char Char : Contents)
			{if(!(Char==' '||Char==0)) {return false;}}
			return true;
		}
	
	}

}
