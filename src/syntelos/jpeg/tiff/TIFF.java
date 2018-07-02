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

import syntelos.rabu.Endian;
import syntelos.rabu.RandomAccessData;
import syntelos.rabu.Window;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class TIFF
    extends RandomAccessData
{

    public final List<IFD> ifdl = new ArrayList();


    public TIFF(Endian e, RandomAccessData d, Window w){
	super(e,d,w);
	if (0x49 == get(0) && 0x49 == get(1)){

	    if (Endian.LE != this.endian){
		throw new IllegalStateException(this.endian.name());
	    }
	}
	else if (0x4D == get(0) && 0x4D == get(1)){

	    if (Endian.BE != this.endian){
		throw new IllegalStateException(this.endian.name());
	    }
	}
	else {
	    throw new IllegalStateException("ENDIAN");
	}

	if (42 == this.uint16(2)){
	    /*
	     * Construct L<IFD>
	     */
	    int ifd_ofs = this.sint32(4);
	    while (0 < ifd_ofs){
		IFD ifd = new IFD(this, Tag.Table.EXIF, ifd_ofs);

		ifdl.add(ifd);

		ifd_ofs = ifd.next;
	    }

	    /*
	     * Scan L<IFD> for {EXIFIFDPointer, GPSInfoIFDPointer}
	     */
	    IFD ifd_x = null;
	    IFD ifd_g = null;

	    int ix = 0;
	    for (int tfof: this.valueOf(Tag.EXIF.ExifIFDPointer,Tag.EXIF.GPSInfoIFDPointer)){

		if (w.bounds(tfof)){

		    switch(ix){
		    case 0:
			System.out.printf("READ IFDP (%s) 0x%04X%n","EXIFIFDPointer",tfof);

			ifd_x = new IFD(this,Tag.Table.EXIF,tfof);
			ifdl.add(ifd_x);
			break;
		    case 1:
			System.out.printf("READ IFDP (%s) 0x%04X%n","GPSInfoIFDPointer",tfof);

			ifd_g = new IFD(this,Tag.Table.GPS,tfof);
			ifdl.add(ifd_g);
			break;
		    default:
			System.out.printf("READ IFDP (%s) 0x%04X%n","?",tfof);
			break;
		    }
		}
		else if (-1 < tfof){
		    switch(ix){
		    case 0:
			System.out.printf("ERROR IFDP (%s) 0x%04X%n","EXIFIFDPointer",tfof);
			break;
		    case 1:
			System.out.printf("ERROR IFDP (%s) 0x%04X%n","GPSInfoIFDPointer",tfof);
			break;
		    default:
			System.out.printf("ERROR IFDP (%s) 0x%04X%n","?",tfof);
			break;
		    }
		}
		ix += 1;
	    }
	    /*
	     * Scan ifd_x for {InteroperabilityIFDPointer}
	     */
	    if (null != ifd_x){

		IFD ifd_i = null;

		ix = 0;
		for (int tfof: ifd_x.valueOf(Tag.EXIF.InteroperabilityIFDPointer)){

		    if (w.bounds(tfof)){

			switch(ix){
			case 0:
			    System.out.printf("READ IFDP (%s) 0x%04X%n","InteroperabilityIFDPointer",tfof);

			    ifd_i = new IFD(this,Tag.Table.INTR,tfof);
			    ifdl.add(ifd_i);
			    break;
			default:
			    System.out.printf("READ IFDP (%s) 0x%04X%n","?",tfof);
			    break;
			}
		    }
		    else if (-1 < tfof){
			switch(ix){
			case 0:
			    System.out.printf("ERROR IFDP (%s) 0x%04X%n","InteroperabilityIFDPointer",tfof);
			    break;
			default:
			    System.out.printf("ERROR IFDP (%s) 0x%04X%n","?",tfof);
			    break;
			}
		    }
		    ix += 1;
		}
	    }
	}
	else {
	    throw new IllegalStateException("IFDL");
	}
    }


    public int[] valueOf(Tag... tagset){
	int[] re = null;


	for (IFD ifd: this.ifdl){

	    re = add(re, ifd.valueOf(tagset));
	}
	return re;
    }


    public String toString(){

	return String.format("TIFF %s 0x%08X",this.endian,this.length());
    }
    /**
     * The default {@link syntelos.rabu.BufferPrinter buffer print} is
     * an octet dump.  This method may be overridden to print the
     * string returned by {@link #toString()}, or to print a list of
     * children.
     */
    public void println(PrintStream out){

	out.println(this);

	for (IFD ifd: this.ifdl){

	    ifd.println(out);
	}
    }


    public final static int[] add(int[] a, int[] b){
	if (null == a)
	    return b;
	else if (null == b)
	    return a;
	else {
	    int al = a.length;
	    int bl = b.length;
	    int ll = (al+bl);
	    int[] r = new int[ll];
	    {
		System.arraycopy(a,0,r,0,al);
		System.arraycopy(b,0,r,al,bl);
	    }
	    return r;
	}
    }
}
