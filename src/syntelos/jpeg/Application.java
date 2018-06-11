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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * An application segment is constructed by copying a segment.  The
 * application segment is constructed immediately after the segment
 * has been read, and replaces the segment in the {@link JPEG} list of
 * {@link Component Components}.
 * 
 * A subclass knows how to handle the content of the application
 * segment.  Its constructor parses the segment for structure as
 * permits inspection and editing.
 */
public class Application
    extends Segment
{
    private final static String PP = "syntelos.jpeg.app.";

    private final static Map<String,String> MAP = new HashMap();
    static {
	MAP.put("http://ns.adobe.com/xap/1.0/", "XMP");
	MAP.put("Photoshop 3.0","IPTC");
	MAP.put("Adobe_Photoshop2.5:","IPTC");
	MAP.put("ICC_PROFILE","ICC");
	MAP.put("Adobe","ADCT");
    }


    /**
     * @param source A segment with an APP marker and APP tag.
     */
    public final static <T extends Application> T instantiate(Segment source){

	if (null != source && source.is_app()){

	    String tag = source.tag(), cn = null;

	    if (null != tag){
		{
		    String map = MAP.get(tag);
		    if (null != map){

			cn = (PP+map);
		    }
		    else {
			cn = (PP+tag);
		    }
		}


		try {
		    Class<T> cla = (Class<T>)Class.forName(cn);

		    Constructor<T> ctor = cla.getConstructor(Segment.class);

		    return (T)ctor.newInstance(source);
		}
		catch (ClassNotFoundException x){

		    return null;
		}
		catch (ClassCastException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (NoSuchMethodException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (SecurityException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (InstantiationException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (IllegalAccessException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (IllegalArgumentException x){
		    throw new IllegalStateException(cn,x);
		}
		catch (InvocationTargetException x){
		    throw new IllegalStateException(cn,x);
		}
	    }
	}
	return null;
    }


    /**
     * Constructor of subclasses is public and takes one {@link
     * Segment} argument.
     */
    protected Application(Segment copy){
	super(copy);
	if (!this.marker.is_app()){
	    throw new IllegalStateException();
	}
    }
}
