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
    public final int length;


    /**
     * 
     */
    Scan(OffsetInputStream in)
	throws IOException
    {
	super(in);

	super.optimism(in.available());

	boolean mark = false;
	int ch;

	while (true){

	    ch = in.read();

	    if (0 > ch){
		throw new EOFException();
	    }
	    else if (0xFF == ch && (!mark)){

		mark = true;
	    }
	    else if (0xD9 == ch && mark){

		in.unread(Marker.EOI.toByteArray());

		break;
	    }
	    else {
		if (mark){

		    mark = false;
		}

		super.write(ch);
	    }
	}
	super.reset();
	this.length = super.available();
    }


    public boolean is_app(){
	return false;
    }
    public String tag(){
	return null;
    }
    public String toString(){
	return "<SCAN> ["+this.length+"]";
    }
}

