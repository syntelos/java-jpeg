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
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * The scan attaches to input following the SOS segment to collect
 * compressed image data until it finds the <code>0xFF, 0xD9</code>
 * EOI sequence.
 */
public final class Scan
    extends Component
{

    /**
     * Size of data payload
     */
    public final int length;
    /**
     * 
     */
    public final byte[] data;


    /**
     * 
     */
    Scan(OffsetInputStream in)
	throws IOException
    {
	super(in);

	ByteArrayOutputStream buffer = new ByteArrayOutputStream(in.available());

	int state = 0;
	int ch;
	while (true){

	    ch = in.read();

	    if (0 > ch){
		throw new EOFException();
	    }
	    else if (0xFF == ch || 0xD9 == ch){

		state += 1;

		if (2 == state){

		    in.unread(Marker.EOI.toByteArray());

		    break;
		}
	    }
	    else {
		state = 0;

		buffer.write(ch);
	    }
	}

	if (0 < buffer.size()){

	    this.data = buffer.toByteArray();
	    this.length = this.data.length;
	}
	else {
	    this.data = new byte[0];
	    this.length = 0;
	}
    }


    public int length(){
	return this.length;
    }
    public byte get(int x){
	return this.data[x];
    }
    public boolean is_app(){
	return false;
    }
    public String tag(){
	return null;
    }
    public long write(OutputStream out)
	throws IOException
    {
	out.write(this.data,0,this.length);

	return this.length;
    }
    public void println(PrintStream p){

	p.println(this.toString());
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
	return "<SCAN> ["+this.length+"]";
    }
}

