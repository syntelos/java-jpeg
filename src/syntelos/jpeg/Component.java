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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Big endian data buffer.
 */
public abstract class Component
    extends syntelos.rabu.RandomAccessData
    implements syntelos.rabu.Component
{

    /**
     * Input location of component before reading.
     */
    public final long offset;


    /**
     * Copy constructor
     */
    protected Component(Component copy){
	super(Endian.BE,copy);

	this.offset = copy.offset;
    }
    /**
     * This constructor shall not read from the input stream,
     * therefore it declares no exception.
     */
    Component(OffsetInputStream in){
	super(Endian.BE);

	this.offset = in.offset;
    }


    /**
     * Has an <code>APP</code> marker.
     */
    public abstract boolean is_app();
    /**
     * Names a class in package <code>"syntelos.jpeg.app"</code>.
     */
    public abstract String tag();

    public int write(OutputStream out)
	throws IOException
    {
	super.reset();

	return super.copy(out);
    }

    public abstract String toString();

    /**
     * The default {@link syntelos.rabu.BufferPrinter buffer print} is
     * an octet dump.  This method may be overridden to print the
     * string returned by {@link #toString()}, or to print a list of
     * children.
     */
    public void println(PrintStream out){

	out.printf("%6d %20s%n",this.offset,this);
    }
}
