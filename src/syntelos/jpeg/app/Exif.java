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

/**
 * 
 */
public class Exif
    extends syntelos.jpeg.Application
{
    /**
     * 
     */
    public static enum Endian {
	/**
	 * Little endian integers
	 */
	II,
	/**
	 * Big endian integers
	 */
	MM;


	public int int16(byte[] da, int of){

	    int a = (da[of++] & 0xFF);
	    int b = (da[of++] & 0xFF);

	    switch(this){
	    case II:
		return ((b<<8)|(a));
	    case MM:
		return ((a<<8)|(b));
	    default:
		throw new IllegalStateException(name());
	    }
	}
	public int int32(byte[] da, int of){

	    int a = (da[of++] & 0xFF);
	    int b = (da[of++] & 0xFF);
	    int c = (da[of++] & 0xFF);
	    int d = (da[of++] & 0xFF);

	    switch(this){
	    case II:
		return ((d<<24)|(c<<16)|(b<<8)|(a));
	    case MM:
		return ((a<<24)|(b<<16)|(c<<8)|(d));
	    default:
		throw new IllegalStateException(name());
	    }
	}
    }
    /**
     * Segment data offsets.
     */
    public static enum APPT {
	TAG (0),
	NUL (4);

	public final int offset;

	APPT(int ofs){
	    this.offset = ofs;
	}

	public int offset(int delta){
	    return (this.offset + delta);
	}
    }
    /**
     * Segment data offsets.
     */
    public static enum TIFF {
	ENDIAN     (6),
	VALIDATION (8),
	OFS_IFD_0 (10);


	public final int offset;

	TIFF(int ofs){
	    this.offset = ofs;
	}

	public int offset(int delta){
	    return (this.offset + delta);
	}
    }



    protected Endian endian;

    protected int ifd_0;


    public Exif(Segment copy){
	super(copy);
	/*
	 * /END/ <JPEG APP MARKER HEADER> (OFS 5)
	 */
	if (0x0 == data[APPT.NUL.offset(0)] && 0x0 == data[APPT.NUL.offset(1)]){
	    /*
	     * /BEGIN/ <EMBEDDED TIFF FILE HEADER> (JPEG/APP/PL OFS 6) (TIFF OFS 0)
	     */
	    if (0x49 == data[TIFF.ENDIAN.offset(0)] && 0x49 == data[TIFF.ENDIAN.offset(1)]){

		this.endian = Endian.II;
	    }
	    else if (0x4D == data[TIFF.ENDIAN.offset(0)] && 0x4D == data[TIFF.ENDIAN.offset(1)]){

		this.endian = Endian.MM;
	    }
	    else {
		throw new IllegalStateException("EXIF format violation at TIFF.ENDIAN.");
	    }
	    /*
	     */
	    if (42 == this.endian.int16(data,TIFF.VALIDATION.offset)){
		/*
		 */
		this.ifd_0 = this.endian.int32(data,TIFF.OFS_IFD_0.offset);

	    }
	    else {
		throw new IllegalStateException("EXIF format violation at TIFF.VALIDATION.");
	    }
	}
	else {
	    throw new IllegalStateException("EXIF format violation at APPT.NUL.");
	}
    }


    public String toString(){

	return "Exif "+super.toString();
    }
}
