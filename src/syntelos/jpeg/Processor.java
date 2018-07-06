/*
 * JPEG Block I/O
 * Copyright (C) 2018, John Pritchard, Syntelos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package syntelos.jpeg;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 * Process a little "ed" style language over JPEG data for inspection.
 */
public final class Processor {
    /**
     * Processor registers.
     */
    public static class State {
	/**
	 * Segment index
	 */
	public int cursor;
	/**
	 * Work on one segment
	 */
	public boolean lock;
	/**
	 * JPEG (input) is valid
	 */
	public final boolean valid;


	/**
	 * @param valid Input is valid
	 */
	public State(boolean valid){
	    super();
	    this.valid = valid;
	}
    }
    /**
     * Operator classes.
     */
    public static enum Type {
	/**
	 * Input figure
	 */
	Literal,
	/**
	 * Representation figure
	 */
	Figurative;
    }
    /**
     * A little "ed"-style interaction language.
     */
    public static enum Operator {
	help   (Type.Figurative,"?","This message."),
	c      (Type.Literal,"c","Print (cursor/count) in JPEG."),
	i      (Type.Literal,"i","Input status."),
	l      (Type.Literal,"l","Cursor lock/unlock."),
	p      (Type.Literal,"p","Print segment at cursor or range."),
	q      (Type.Literal,"q","Quit."),
	w      (Type.Literal,"w","Overwrite input file."),
	x      (Type.Literal,"x","Print buffer at cursor."),
	end    (Type.Figurative,"$","Range terminal, as in '0,$p'."),
	cursor (Type.Figurative);


	public final Type type;

	public final String[] description;


	Operator(Type type, String... desc){
	    this.type = type;
	    this.description = desc;
	}


	private static String delimeters;
	static {
	    StringBuilder delimeters = new StringBuilder(",?:;.!@#$%^&*()_-+={}[]|<>/");

	    for (Operator k : values()){

		switch(k.type){
		case Literal:
		    delimeters.append(k.name());
		    break;
		default:
		    break;
		}
	    }
	    Operator.delimeters = delimeters.toString();
	}


	public static Object[] lexer(String in){

	    StringTokenizer tokenizer = new StringTokenizer(in,Operator.delimeters,true);
	    int count = tokenizer.countTokens();
	    Object[] list = new Object[count];
	    for (int cc = 0; cc < count; cc++){
		String token = tokenizer.nextToken();
		try {
		    list[cc] = new Integer(token);
		}
		catch (NumberFormatException exc){

		    if (1 == token.length()){
			try {
			    list[cc] = Operator.valueOf(token);
			}
			catch (RuntimeException rtx){

			    char ch = token.charAt(0);
			    switch(ch){
			    case '?':
				list[cc] = Operator.help;
				break;
			    case '$':
				list[cc] = Operator.end;
				break;
			    default:
				list[cc] = Character.valueOf(ch);
				break;
			    }
			}
		    }
		    else {
			/*
			 * (Error case: token output)
			 */
			list[cc] = token;
		    }
		}
	    }

	    if (1 == count){
		Object c = list[0];

		if (c instanceof Integer || Operator.end == c){

		    Object[] rewrite = new Object[]{
			list[0], Operator.cursor
		    };
		    return rewrite;
		}
	    }
	    return list;
	}
    }


    private OffsetInputStream in;

    private JPEG jpeg;

    public final String strin;

    public final boolean valid;

    public final Operator operator;

    public final int start;

    public final int end;


    public Processor(String ln, State st, OffsetInputStream in, JPEG jpeg){
	super();
	/*
	 * 
	 */
	this.strin = ln;
	this.in = in;
	this.jpeg = jpeg;
	/*
	 * lex
	 */
	Object[] lex = Processor.Operator.lexer(ln);
	int lexn = lex.length;
	boolean invalid = false;
	int start = -1, end = -1;
	Operator k = null;

	if (0 < lexn){
	    /*
	     * parse
	     */
	    int kx = (lexn-1);
	    Object le = lex[kx];

	    if (le instanceof Operator){

		k = (Operator)le;

		/*
		 * expr = ( K | aK | a,bK )
		 * lexn = { 1,   2,     4 }
		 */
		Object a,z,b;

		switch(lexn){
		case 1:
		    break;
		case 2:
		    a = lex[0];
		    if (a instanceof Integer){

			start = ((Integer)a).intValue();
			end = (start+1);

			if (0 > start || start >= end){

			    invalid = true;
			}
			else if (st.lock){

			    Component c = jpeg.get(st.cursor);

			    if (end > c.length()){

				invalid = true;
			    }
			}
			else if (end > jpeg.size()){

			    invalid = true;
			}
		    }
		    else if (Operator.end == a){

			if (st.lock){

			    Component c = jpeg.get(st.cursor);

			    end = c.length();
			    start = (end-1);

			    invalid = (0 > start);
			}
			else {
			    end = jpeg.size();
			    start = (end-1);

			    invalid = (0 > start);
			}
		    }
		    else {

			invalid = true;
		    }
		    break;
		case 4:
		    a = lex[0];
		    z = lex[1];
		    b = lex[2];
		    if (a instanceof Integer &&
			z instanceof Character &&
			(b instanceof Integer || Operator.end == b))
			{
			    if (',' == ((Character)z).charValue()){

				start = ((Integer)a).intValue();

				if (Operator.end == b){

				    if (st.lock){
					Component c = jpeg.get(st.cursor);

					end = c.length();
				    }
				    else {
					end = jpeg.size();
				    }
				}
				else {
				    end = ((Integer)b).intValue();

				    invalid = (end > jpeg.size());
				}

				if (0 > start || start >= end){

				    invalid = true;
				}
			    }
			    else {

				invalid = true;
			    }
			}
		    else {

			invalid = true;
		    }
		    break;
		default:

		    invalid = true;
		    break;
		}
	    }
	    else {

		invalid = true;
	    }
	}
	else {
	    invalid = true;
	}

	this.valid = (!invalid);
	this.operator = k;
	this.start = start;
	this.end = end;
    }

    private final static String HELP = "%10s %-60s%n";


    public boolean process(State st, DataInputStream in, PrintStream out, PrintStream err){

	if (this.valid){

	    switch(this.operator){
	    case c:
		out.printf("%6d/%6d %6s%n",st.cursor,this.jpeg.size(),st.lock);
		return true;
	    case i:
		out.printf("%6d/%6d %30s%n",this.in.offset,this.in.length,this.in.file.getPath());
		return true;
	    case l:
		st.lock = (!st.lock);
		out.printf("%6d/%6d%6s%n",st.cursor,this.jpeg.size(),st.lock);
		return true;
	    case p:

		if (-1 < this.start && this.start < this.end){

		    for (int cc = this.start; cc < this.end; cc++){

			Component c = this.jpeg.get(cc);

			c.println(0,out);
		    }
		}
		else {

		    Component c = this.jpeg.get(st.cursor);

		    c.println(0,out);
		}
		return true;
	    case q:
		return false;
	    case w:
		if (st.valid){
		    File file = this.in.file;
		    try {
			long count = jpeg.write(file);

			out.printf("%9d %20s%n",count,file.getPath());
		    }
		    catch (IOException iox){
			String msg = String.format("Error writing to file '%s'.",file);

			err.println(msg);
			iox.printStackTrace();
			err.println(msg);
		    }
		}
		else {
		    err.println("Unable to output from invalid input state.");
		}
		return true;
	    case x:
		{
		    Component c = this.jpeg.get(st.cursor);
		    if (c instanceof Application){

			Application a = (Application)c;

			if (a.hasApplicationBuffer()){

			    a.printApplicationBuffer(out);
			}
			else {
			    c.println(0,out);
			}
		    }
		    else {
			c.println(0,out);
		    }
		}
		return true;
	    case help:
		out.println("Help");
		out.println();
		for (Operator op : Operator.values()){

		    String[] text = op.description;
		    if (null != text && 2 == text.length){

			out.printf(HELP,text[0],text[1]);
		    }
		}
		out.println();
		return true;
	    case cursor:
		st.cursor = this.start;
		out.printf("%6d/%6d %6s%n",st.cursor,this.jpeg.size(),st.lock);
		return true;
	    default:
		err.printf("Unimplemented operator '%s'.%n",this.operator);

		return true;
	    }
	}
	else {
	    err.printf("Unrecognized input '%s'.  Use '?' for help.%n",this.strin);

	    return true;
	}
    }

    public String toString(){
	StringBuilder string = new StringBuilder();
	{
	    string.append("P[");
	    {
		string.append("valid: ");string.append(this.valid);
		string.append(", operator: ");string.append(this.operator);
		string.append(", start: ");string.append(this.start);
		string.append(", end: ");string.append(this.end);
	    }
	    string.append(']');
	}
	return string.toString();
    }
}
