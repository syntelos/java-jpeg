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

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * 
 */
public abstract class Component {

    /**
     * Input location of component before reading.
     */
    public final long offset;


    /**
     * This constructor shall not read from the input stream,
     * therefore it declares no exception.
     */
    Component(OffsetInputStream in){
	super();

	this.offset = in.offset;
    }


    public abstract int length();

    public abstract byte get(int x);

    public abstract long write(OutputStream o) throws IOException;

    public abstract void println(PrintStream p);

    public abstract String toString();
}
