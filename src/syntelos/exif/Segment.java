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

import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Lexical analysis list node.
 */
public final class Segment {

    /**
     * Representation of the two byte segment class identity.
     */
    public final Marker marker;
    /**
     * Size of segment, including count and excluding marker.
     */
    public final int stride;
    /**
     * Size of data payload, excluding count and marker.
     */
    public final int length;
    /**
     * 
     */
    public final byte[] data;


    /**
     * Used from {@link JPEG} to read the marker type, segment length,
     * and segment body.  The segment marker prefix
     * <code>(0xff)</code> is consumed by {@link JPEG}.
     */
    Segment(OffsetInputStream in)
	throws IOException
    {
	int ch = in.read();

	if (0xff == ch){

	    this.marker = Marker.valueOf(in.read());

	    if (this.marker.solitary){
		this.stride = 0;
		this.length = 0;
		this.data = new byte[0];
	    }
	    else {
		long start = in.offset;

		int count = COUNT(in);

		if (0 == count){
		    this.stride = 0;
		    this.length = 0;
		    this.data = new byte[0];
		}
		else if (0 < count && 0xFFFF >= count){
		    this.stride = count;
		    this.length = (count-2);

		    byte[] data = new byte[count];
		    {
			int ofs = 0;
			int rem = count;
			int red = 0;
			while (0 < rem && -1 < (red = in.read(data,ofs,rem))){

			    ofs += red;
			    rem -= red;
			}

			if (0 < rem){
			    throw new IllegalStateException(String.format("Failed to complete read '%d/%d' from '(%d - %d)/%d'",rem,count,in.offset,start,(in.length-1)));
			}
		    }
		    this.data = data;
		}
		else {
		    throw new IllegalStateException(String.format("Invalid count '%d'",count));
		}
	    }
	}
	else if (0 > ch){
	    /*
	     * Normal termination of EXIF construction.
	     */
	    throw new EOFException();
	}
	else {
	    throw new IllegalStateException(String.format("Unrecognized input '%d' at '%s'.",ch,in));
	}
    }


    public void println(PrintStream out){

	out.printf("%s\t%d%n",this.marker,this.length);
    }
    public String toString(){
	StringBuilder strbuf = new StringBuilder();
	{
	    strbuf.append('[');
	    {
		strbuf.append(this.marker);
		strbuf.append(':');
		strbuf.append(this.length);
	    }
	    strbuf.append(']');
	}
	return strbuf.toString();
    }


    private static int COUNT(InputStream in)
	throws IOException
    {
	return (((int)(in.read() & 0xff) << 8)|((int)(in.read() & 0xff)));
    }
}
