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

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import static java.lang.System.out;
import static java.lang.System.err;

/**
 * {@link JPEG} driver.
 */
public final class Main {

    /**
     * 
     */
    private static void usage(){
	err.println("Synopsis");
	err.println();
	err.println("    syntelos.jpeg.Main [-] <file.jpg>");
	err.println();
	err.println("Description");
	err.println();
	err.println("    Print component list, optionally interact with");
	err.println("    component list.");
	err.println();
	System.exit(1);
    }
    /**
     * 
     */
    public static void main(String[] argv){
	final int argl = argv.length;

	File fin = null;
	boolean interactive = false;

	JPEG jpeg = new JPEG();

	boolean nominal = true;


	if (0 < argl){

	    if (1 < argl){

		if ("-".equals(argv[0])){

		    interactive = true;
		    fin = new File(argv[1]);
		}
		else if ("-".equals(argv[1])){

		    fin = new File(argv[0]);
		    interactive = true;
		}
		else {
		    usage();
		}
	    }
	    else {
		fin = new File(argv[0]);
	    }

	    if (fin.isFile()){
		/*
		 * Input
		 */
		OffsetInputStream in = null;

		if (nominal){
		    try {
			in = new OffsetInputStream(fin);

			jpeg.read(in);
		    }
		    catch (Exception exc){
			exc.printStackTrace();
			nominal = false;
		    }
		    finally {
			if (null != in){
			    try {
				in.close();
			    }
			    catch (Throwable t){
			    }
			}
		    }
		}
		/*
		 * Output
		 */
		if (interactive){

		    DataInputStream lin = new DataInputStream(System.in);
		    String ln;
		    try {
			out.print("? ");
			Processor.State ps = new Processor.State(nominal);

			while (null != (ln = lin.readLine())){

			    Processor p = new Processor(ln,ps,in,jpeg);

			    err.println(p);

			    if (!p.process(ps,lin,out,err)){

				break;
			    }
			    else {
				out.print("? ");
			    }
			}
			System.exit(0);
		    }
		    catch (IOException iox){
			iox.printStackTrace();

			System.exit(1);
		    }
		}
		else if (nominal){

		    jpeg.println(0,out);

		    System.exit(0);
		}
		else {

		    System.exit(1);
		}
	    }
	    else {
		usage();
	    }
	}
	else {
	    usage();
	}
    }
}
