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
     * Data payload binary, not including the {@link #stride "count"}
     * size of the data payload binary.
     */
    protected byte[] data;
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
	this.data = copy.data;
    }
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
    public boolean is_app(){

	return this.marker.is_app();
    }
    public String tag(){

	if (null != this._tag){

	    return this._tag;
	}
	else if (this.marker.is_app()){

	    ByteArrayOutputStream buf = new ByteArrayOutputStream();
	    for (int cc = 0; cc < this.length; cc++){

		int ch = (this.data[cc] & 0xFF);
		if (0 == ch){
		    break;
		}
		else {

		    buf.write(ch);
		}
	    }

	    byte[] b = buf.toByteArray();
	    if (null != b){

		this._tag = new String(b,0,0,b.length);

		return this._tag;
	    }
	}
	return null;
    }
    public long write(OutputStream out)
	throws IOException
    {
	out.write(0xFF);
	out.write(this.marker.code);
	if (!this.marker.solitary){
	    int a = ((this.stride & 0xFF00)>>8);
	    int b = (this.stride & 0xFF);
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

	out.println(this);
    }
    public void print_p(PrintStream out, int start, int end){

	if (-1 < start && start < end){

	    for (int cc = start; cc < end; cc++){

		byte b = this.data[cc];

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

	    for (int cc = 0; cc < this.length; cc++){

		byte b = this.data[cc];

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
    public void print_n(PrintStream out, int start, int end){

	if (-1 < start && start < end){

	    for (int cc = start; cc < end; cc++){

		byte b = this.data[cc];

		out.printf(" %02X",b);
	    }
	    out.println();
	}
	else {

	    for (int cc = 0; cc < this.length; cc++){

		byte b = this.data[cc];

		out.printf(" %02X",b);
	    }
	    out.println();
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
