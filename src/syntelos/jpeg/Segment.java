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

import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Lexical analysis list node.
 */
public final class Segment
    extends Component
{

    /**
     * Representation of the two byte segment class identity.
     */
    public final Marker marker;
    /**
     * Size of segment, including count and excluding marker.
     */
    private final int stride;
    /**
     * Size of data payload, excluding count and marker.
     */
    private final int length;
    /**
     * 
     */
    private final byte[] data;


    /**
     * Used from {@link JPEG} to read the marker type, segment length,
     * and segment body.  The segment marker prefix
     * <code>(0xff)</code> is consumed by {@link JPEG}.
     */
    Segment(OffsetInputStream in)
	throws IOException
    {
	super(in);

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

		    byte[] data = new byte[length];
		    {
			int ofs = 0;
			int rem = length;
			int red = 0;
			while (0 < rem && -1 < (red = in.read(data,ofs,rem))){

			    ofs += red;
			    rem -= red;
			}

			if (0 < rem){
			    throw new IllegalStateException(String.format("Failed to complete read '%d/%d' from '(%d - %d)/%d'",rem,length,in.offset,start,(in.length-1)));
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
	     * Normal termination of JPEG construction.
	     */
	    throw new EOFException();
	}
	else {
	    throw new IllegalStateException(String.format("Expected '0xFF' not '0x%02X' at '%s'.",ch,in.toString().trim()));
	}
    }


    public Marker marker(){
	return this.marker;
    }
    public int length(){
	return this.length;
    }
    public byte get(int x){
	return this.data[x];
    }
    public long write(OutputStream out)
	throws IOException
    {
	out.write(0xFF);
	out.write(this.marker.code);
	if (!this.marker.solitary){
	    int a = ((this.length & 0xFF00)>>8);
	    int b = (this.length & 0xFF);
	    out.write(a);
	    out.write(b);
	    out.write(this.data,0,this.length);

	    return (2+this.stride);
	}
	else {
	    return (2);
	}
    }
    public void println(PrintStream out){

	out.printf("%s\t%d%n",this.marker,this.length);
    }
    public String toString(){
	StringBuilder strbuf = new StringBuilder();
	{
	    strbuf.append(this.marker);

	    if (!this.marker.solitary){
		strbuf.append('[');
		strbuf.append(this.offset);
		strbuf.append(':');
		strbuf.append(this.length);
		strbuf.append(']');
	    }
	}
	return strbuf.toString();
    }


    private static int COUNT(InputStream in)
	throws IOException
    {
	return (((int)(in.read() & 0xff) << 8)|((int)(in.read() & 0xff)));
    }
}
