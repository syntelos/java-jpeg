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
package syntelos.jpeg.app;

import syntelos.jpeg.Segment;
import syntelos.jpeg.tiff.TIFF;
import syntelos.rabu.Endian;
import syntelos.rabu.Printer;
import syntelos.rabu.RandomAccessBuffer;
import syntelos.rabu.Window;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Exif
    extends syntelos.jpeg.Application
{

    /**
     * JPEG APP segment data offsets.
     */
    public static enum JAPP {
	TAG     (0),
	NULL    (4),
	TIFF    (6),
	ENDIAN  (6);


	public final int offset;

	JAPP(int ofs){
	    this.offset = ofs;
	}

	public int offset(int delta){
	    return (this.offset + delta);
	}
    }



    protected final Endian endian;

    protected TIFF tiff;


    public Exif(Segment copy){
	super(copy);
	/*
	 * /END/ <JPEG APP MARKER HEADER> (OFS 5)
	 */
	if (0x0 == get(JAPP.NULL.offset(0)) && 0x0 == get(JAPP.NULL.offset(1))){
	    /*
	     * /BEGIN/ <EMBEDDED TIFF FILE HEADER> (JPEG/APP/PL OFS 6) (TIFF OFS 0)
	     */
	    if (0x49 == get(JAPP.ENDIAN.offset(0)) && 0x49 == get(JAPP.ENDIAN.offset(1))){

		this.endian = Endian.LE;
	    }
	    else if (0x4D == get(JAPP.ENDIAN.offset(0)) && 0x4D == get(JAPP.ENDIAN.offset(1))){

		this.endian = Endian.BE;
	    }
	    else {
		throw new IllegalStateException("EXIF format violation at JAPP.ENDIAN.");
	    }

	    int tw_o = JAPP.ENDIAN.offset;
	    int tw_l = (length()-JAPP.ENDIAN.offset);


	    this.tiff = new TIFF(this.endian, this, new Window(tw_o,tw_l));

	}
	else {
	    throw new IllegalStateException("EXIF format violation at JAPP.NUL.");
	}
    }

    public String toString(){

	return "Exif "+super.toString();
    }
    public void println(PrintStream out){

	out.printf("%6d %20s%n",this.offset,this);
    }
    public void println(int d, PrintStream out){

	for (int cc = 0; cc < d; cc++){

	    out.write('\t');
	}

	out.printf("%6d %20s%n",this.offset,this);

	this.tiff.println((d+1),out);
    }
    public boolean hasApplicationBuffer(){
	return true;
    }
    public List<TIFF> getApplicationBuffer(){

	ArrayList<TIFF> list = new ArrayList();

	list.add(this.tiff);

	return list;
    }
    public void printApplicationBuffer(PrintStream out){

	this.tiff.print(out);
    }
    public void printApplicationBuffer(Printer p, PrintStream out){

	p.print(this.tiff,out);
    }
}
