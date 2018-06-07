/*
 * EXIF Block I/O
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
package syntelos.exif;

import java.io.DataInputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;

/**
 * Process a little "ed" style language over EXIF data for inspection.
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
	c      (Type.Literal),
	l      (Type.Literal),
	p      (Type.Literal),
	n      (Type.Literal),
	q      (Type.Literal),
	help   (Type.Figurative),
	cursor (Type.Figurative),
	end    (Type.Figurative);


	public final Type type;

	Operator(Type type){
	    this.type = type;
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

    private EXIF ex;

    public final String strin;

    public final boolean valid;

    public final Operator operator;

    public final int start;

    public final int end;


    public Processor(String ln, State st, OffsetInputStream in, EXIF ex){
	super();
	/*
	 * 
	 */
	this.strin = ln;
	this.in = in;
	this.ex = ex;
	/*
	 * lex
	 */
	Object[] lex = Processor.Operator.lexer(ln);
	int lexn = lex.length;
	/*
	 * parse
	 */
	boolean invalid = false;
	int start = -1, end = -1;
	Operator k = null;
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

			Segment s = ex.get(st.cursor);

			if (end > s.length){

			    invalid = true;
			}
		    }
		    else if (end > ex.size()){

			invalid = true;
		    }
		}
		else if (Operator.end == a){

		    if (st.lock){

			Segment s = ex.get(st.cursor);

			end = s.length;
			start = (end-1);

			invalid = (0 > start);
		    }
		    else {
			end = ex.size();
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
				Segment s = ex.get(st.cursor);

				end = s.length;
			    }
			    else {
				end = ex.size();
			    }
			}
			else {
			    end = ((Integer)b).intValue();

			    invalid = (end > ex.size());
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
		out.printf("%6d/%6d%6s%n",st.cursor,this.ex.size(),st.lock);
		return true;
	    case l:
		st.lock = (!st.lock);
		out.printf("%6d/%6d%6s%n",st.cursor,this.ex.size(),st.lock);
		return true;
	    case p:
		if (st.lock){

		    Segment s = this.ex.get(st.cursor);
		    out.println(s);

		    if (-1 < this.start && this.start < this.end){

			for (int cc = this.start; cc < this.end; cc++){

			    byte b = s.data[cc];

			    if (0x20 < b && 0x7f > b){

				out.printf(" %2c",b);
			    }
			    else {

				out.printf(" %02X",b);
			    }
			}
			out.println();
		    }
		    else {

			for (int cc = 0; cc < s.length; cc++){

			    byte b = s.data[cc];

			    if (0x20 < b && 0x7f > b){

				out.printf(" %2c",b);
			    }
			    else {

				out.printf(" %02X",b);
			    }
			}
			out.println();
		    }
		}
		else if (-1 < this.start && this.start < this.end){

		    for (int cc = this.start; cc < this.end; cc++){

			Segment s = this.ex.get(cc);

			out.printf("%20s%n",s);
		    }
		}
		else {

		    Segment s = this.ex.get(st.cursor);

		    out.printf("%20s%n",s);
		}
		return true;
	    case n:
		if (st.lock){
		    Segment s = this.ex.get(st.cursor);
		    out.println(s);

		    if (-1 < this.start && this.start < this.end){

			for (int cc = this.start; cc < this.end; cc++){

			    byte b = s.data[cc];

			    out.printf(" %02X",b);
			}
			out.println();
		    }
		    else {

			for (int cc = 0; cc < s.length; cc++){

			    byte b = s.data[cc];

			    out.printf(" %02X",b);
			}
			out.println();
		    }
		}
		else if (-1 < this.start && this.start < this.end){

		    for (int cc = this.start; cc < this.end; cc++){

			Segment s = this.ex.get(cc);

			out.printf("%6d %20s%n",cc,s);
		    }
		}
		else {

		    Segment s = this.ex.get(st.cursor);

		    out.printf("%6d %20s%n",st.cursor,s);
		}
		return true;
	    case q:
		return false;
	    case help:
		out.println("Help");
		out.println();
		out.printf(HELP,"?","This message.");
		out.printf(HELP,"#","Set cursor by index number '#' (from zero).");
		out.printf(HELP,"c","Print (cursor/count) in EXIF.");
		out.printf(HELP,"p","Print segment at cursor.");
		out.printf(HELP,"n","Print segment at cursor with index.");
		out.printf(HELP,"#,#p","Print segment range.");
		out.printf(HELP,"#,#n","Print segment range with indeces.");
		out.println();
		return true;
	    case cursor:
		st.cursor = this.start;
		out.printf("%6d/%6d%6s%n",st.cursor,this.ex.size(),st.lock);
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
