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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import static java.lang.System.err;

/**
 * 
 */
public final class JPEG
    extends java.util.ArrayList<Component>
{

    public final Printer.Configuration pc;

    public final Endian en;


    public JPEG(){
	super();
	pc = new Printer.Configuration(Printer.Offset.HEX,Printer.Content.ASC);
	en = Endian.BE;
    }


    public void read(OffsetInputStream in)
	throws IOException
    {
	while (true){
	    /*
	     * SEGMENTS
	     */
	    Segment s = new Segment(pc,en,in);

	    if (s.is_app()){

		System.err.printf("APP ? %s%n",s.tag());

		Application a = Application.instantiate(s);
		if (null != a){

		    System.err.printf("APP + %s%n",a);

		    this.add(a);
		}
		else {
		    System.err.printf("APP - %s%n",s.tag());

		    this.add(s);
		}
	    }
	    else {
		this.add(s);
	    }
	    /*
	     * SCAN
	     */
	    if (Marker.SOS == s.marker){

		Scan scan = new Scan(pc,en,in);

		this.add(scan);

		Segment eoi = new Segment(pc,en,in);

		this.add(eoi);

		break;
	    }
	}
    }
    public long write(File file)
	throws IOException
    {
	FileOutputStream out = new FileOutputStream(file);
	try {
	    return this.write(out);
	}
	finally {
	    out.close();
	}
    }
    public long write(OutputStream out)
	throws IOException
    {
	long count = 0;

	for (Component c : this){

	    count += c.write(out);
	}
	return count;
    }
    public void println(PrintStream out){
	int cc = 0;
	for (Component c : this){

	    out.printf("%6d %20s%n",cc,c);
	    cc += 1;
	}
    }
    public String toString(){
	StringBuilder strbuf = new StringBuilder();
	{
	    strbuf.append('[');
	    {
		for (Component c : this){

		    strbuf.append(c);
		}
	    }
	    strbuf.append(']');
	}
	return strbuf.toString();
    }
}
