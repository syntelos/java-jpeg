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

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;

import static java.lang.System.err;

/**
 * 
 */
public final class EXIF
    extends java.util.ArrayList<Segment>
{

    public EXIF(){
	super();
    }


    public void read(OffsetInputStream in)
	throws IOException
    {
	try {
	    while (true){

		this.add(new Segment(in));
	    }
	}
	catch (EOFException normal){
	}
    }
    public void println(PrintStream out){

	for (Segment s : this){

	    s.println(out);
	}
    }
    public String toString(){
	StringBuilder strbuf = new StringBuilder();
	{
	    strbuf.append('[');
	    {
		for (Segment s : this){

		    strbuf.append(s);
		}
	    }
	    strbuf.append(']');
	}
	return strbuf.toString();
    }
}
