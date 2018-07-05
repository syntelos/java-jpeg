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

import syntelos.rabu.Endian;
import syntelos.rabu.Printer;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Lexical analysis list node.
 */
public class Segment
    extends Component
{

    /**
     * Representation of the two byte segment class identity.
     */
    public final Marker marker;
    /**
     * Size of segment, including count and excluding marker.
     */
    protected int stride;
    /**
     * Size of data payload, excluding count and marker.
     */
    protected int length;
    /**
     * 
     */
    private transient String _tag;


    /**
     * Copy constructor
     */
    protected Segment(Segment copy){
	super(copy);
	this.marker = copy.marker;
	this.stride = copy.stride;
	this.length = copy.length;
    }
    /**
     * Used from {@link JPEG} to read the marker type, segment length,
     * and segment body.  The segment marker prefix
     * <code>(0xff)</code> is consumed by {@link JPEG}.
     */
    Segment(Printer.Configuration c, Endian e, OffsetInputStream in)
	throws IOException
    {
	super(c,e,in);

	int ch = in.read();

	if (0xff == ch){

	    this.marker = Marker.valueOf(in.read());

	    if (this.marker.solitary){
		this.stride = 0;
		this.length = 0;
	    }
	    else {
		long start = in.offset;

		int count = COUNT(in);

		if (0 == count){
		    this.stride = 0;
		    this.length = 0;
		}
		else if (0 < count && 0xFFFF >= count){
		    this.stride = count;
		    this.length = (count-2);

		    if (!super.copy(in,length)){

			throw new IllegalStateException("Failed to complete read.");
		    }
		    else {
			super.reset();
		    }
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
    public boolean is_app(){

	return this.marker.is_app();
    }
    public String tag(){

	if (null != this._tag){

	    return this._tag;
	}
	else if (this.marker.is_app()){

	    int z = this.indexOf(0);
	    if (0 < z){

		this._tag = this.substring(0,z);

		return this._tag;
	    }
	}
	return null;
    }
    public int write(OutputStream out)
	throws IOException
    {
	out.write(0xFF);
	out.write(this.marker.code);
	if (!this.marker.solitary){
	    /*
	     * (big-endian uint16 segment size)
	     */
	    super.uint16(this.stride,out);
	    /*
	     * (segment payload data)
	     */
	    super.copy(out);

	    return (2+this.stride);
	}
	else {
	    return (2);
	}
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
