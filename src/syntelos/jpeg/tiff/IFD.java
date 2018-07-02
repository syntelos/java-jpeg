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
package syntelos.jpeg.tiff;

import java.io.PrintStream;

/**
 * 
 */
public class IFD
    extends java.util.ArrayList<Field>
    implements syntelos.rabu.Component
{

    public final Tag.Table table;

    public final int offset;

    public final int next;


    public IFD(TIFF r, Tag.Table t, int ofs){
	super();

	this.table = t;
	this.offset = ofs;

	int count = r.uint16(ofs);
	{
	    ofs += 2;
	}

	for (int cc = 0; cc < count; cc++){

	    Field field = new Field(r,t,ofs);

	    this.add(field);

	    ofs += 12;
	}

	this.next = r.sint32(ofs);

	System.out.printf("IFD (%s) this: 0x%04X, count: %d, next: 0x%04X%n",this.table,this.offset,count,this.next);
    }


    public void println(PrintStream out){

	out.printf("IFD (%s) this: 0x%04X, count: %d, next: 0x%04X%n",this.table,this.offset,this.size(),this.next);

	for (Field fld: this){

	    fld.println(out);
	}
    }
    public int[] valueOf(Tag... tagset){
	/*
	 * INIT
	 */
	int tn = tagset.length;
	int[] valueset = new int[tn];
	{
	    for (int cc = 0, cz = tn; cc < cz; cc++){

		valueset[cc] = -1;
	    }
	}
	/*
	 * SCAN IFD_EXIF for IFD_POINTERS
	 */
	int vx = 0;

	for (Field fld: this){

	    int fx = fld.indexOf(tagset);
	    if (-1 < fx){

		valueset[vx++] = fld.int32();
		if (vx >= tn){
		    break;
		}
	    }
	}
	return valueset;
    }
}
