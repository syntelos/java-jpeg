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

/**
 * Type envelope for tag enums.
 */
public interface Tag {

    public int code();

    /**
     * 
     */
    public static enum Table {
	EXIF,
	GPS,
	INTR;

	public Tag valueOf(int code){
	    switch(this){
	    case EXIF:
		return Tag.EXIF.valueOf(code);
	    case GPS:
		return Tag.GPS.valueOf(code);
	    case INTR:
		return Tag.INTR.valueOf(code);
	    default:
		throw new IllegalStateException(name());
	    }
	}
    }

    /**
     * EXIF Tag
     */
    public static enum EXIF implements Tag {
	ImageWidth(0x100),
	    ImageLength(0x101),
	    BitsPerSample(0x102),
	    Compression(0x103),
	    PhotometricInterpretation(0x106),
	    FillOrder(0x10A),
	    DocumentName(0x10D),
	    ImageDescription(0x10E),
	    Make(0x10F),
	    Model(0x110),
	    StripOffsets(0x111),
	    Orientation(0x112),
	    SamplesPerPixel(0x115),
	    RowsPerStrip(0x116),
	    StripByteCounts(0x117),
	    XResolution(0x11A),
	    YResolution(0x11B),
	    PlanarConfiguration(0x11C),
	    ResolutionUnit(0x128),
	    TransferFunction(0x12D),
	    Software(0x131),
	    DateTime(0x132),
	    Artist(0x13B),
	    WhitePoint(0x13E),
	    PrimaryChromaticities(0x13F),
	    TransferRange(0x156),
	    JPEGProc(0x200),
	    JPEGInterchangeFormat(0x201),
	    JPEGInterchangeFormatLength(0x202),
	    YCbCrCoefficients(0x211),
	    YCbCrSubSampling(0x212),
	    YCbCrPositioning(0x213),
	    ReferenceBlackWhite(0x214),
	    BatteryLevel(0x828F),
	    Copyright(0x8298),
	    ExposureTime(0x829A),
	    FNumber(0x829D),
	    IPTC_NAA(0x83BB),
	    ExifIFDPointer(0x8769),
	    InterColorProfile(0x8773),
	    ExposureProgram(0x8822),
	    SpectralSensitivity(0x8824),
	    GPSInfoIFDPointer(0x8825),
	    ISOSpeedRatings(0x8827),
	    OECF(0x8828),
	    ExifVersion(0x9000),
	    DateTimeOriginal(0x9003),
	    DateTimeDigitized(0x9004),
	    ComponentsConfiguration(0x9101),
	    CompressedBitsPerPixel(0x9102),
	    ShutterSpeedValue(0x9201),
	    ApertureValue(0x9202),
	    BrightnessValue(0x9203),
	    ExposureBiasValue(0x9204),
	    MaxApertureValue(0x9205),
	    SubjectDistance(0x9206),
	    MeteringMode(0x9207),
	    LightSource(0x9208),
	    Flash(0x9209),
	    FocalLength(0x920A),
	    SubjectArea(0x9214),
	    MakerNote(0x927C),
	    UserComment(0x9286),
	    SubSecTime(0x9290),
	    SubSecTimeOriginal(0x9291),
	    SubSecTimeDigitized(0x9292),
	    FlashPixVersion(0xA000),
	    ColorSpace(0xA001),
	    PixelXDimension(0xA002),
	    PixelYDimension(0xA003),
	    RelatedSoundFile(0xA004),
	    InteroperabilityIFDPointer(0xA005),
	    FlashEnergy(0xA20B),
	    SpatialFrequencyResponse(0xA20C),
	    FocalPlaneXResolution(0xA20E),
	    FocalPlaneYResolution(0xA20F),
	    FocalPlaneResolutionUnit(0xA210),
	    SubjectLocation(0xA214),
	    ExposureIndex(0xA215),
	    SensingMethod(0xA217),
	    FileSource(0xA300),
	    SceneType(0xA301),
	    CFAPattern(0xA302),
	    CustomRendered(0xA401),
	    ExposureMode(0xA402),
	    WhiteBalance(0xA403),
	    DigitalZoomRatio(0xA404),
	    FocalLengthIn35mmFilm(0xA405),
	    SceneCaptureType(0xA406),
	    GainControl(0xA407),
	    Contrast(0xA408),
	    Saturation(0xA409),
	    Sharpness(0xA40A),
	    DeviceSettingDescription(0xA40B),
	    SubjectDistanceRange(0xA40C),
	    ImageUniqueID(0xA420);

	public final int code;

	EXIF(int code){
	    this.code = code;
	}


	public int code(){
	    return this.code;
	}

	/**
	 * Exif Tag lookup 
	 */
	public static EXIF valueOf(int code){
	    switch(code){
	    case 0x100:
		return ImageWidth;
	    case 0x101:
		return ImageLength;
	    case 0x102:
		return BitsPerSample;
	    case 0x103:
		return Compression;
	    case 0x106:
		return PhotometricInterpretation;
	    case 0x10A:
		return FillOrder;
	    case 0x10D:
		return DocumentName;
	    case 0x10E:
		return ImageDescription;
	    case 0x10F:
		return Make;
	    case 0x110:
		return Model;
	    case 0x111:
		return StripOffsets;
	    case 0x112:
		return Orientation;
	    case 0x115:
		return SamplesPerPixel;
	    case 0x116:
		return RowsPerStrip;
	    case 0x117:
		return StripByteCounts;
	    case 0x11A:
		return XResolution;
	    case 0x11B:
		return YResolution;
	    case 0x11C:
		return PlanarConfiguration;
	    case 0x128:
		return ResolutionUnit;
	    case 0x12D:
		return TransferFunction;
	    case 0x131:
		return Software;
	    case 0x132:
		return DateTime;
	    case 0x13B:
		return Artist;
	    case 0x13E:
		return WhitePoint;
	    case 0x13F:
		return PrimaryChromaticities;
	    case 0x156:
		return TransferRange;
	    case 0x200:
		return JPEGProc;
	    case 0x201:
		return JPEGInterchangeFormat;
	    case 0x202:
		return JPEGInterchangeFormatLength;
	    case 0x211:
		return YCbCrCoefficients;
	    case 0x212:
		return YCbCrSubSampling;
	    case 0x213:
		return YCbCrPositioning;
	    case 0x214:
		return ReferenceBlackWhite;
	    case 0x828F:
		return BatteryLevel;
	    case 0x8298:
		return Copyright;
	    case 0x829A:
		return ExposureTime;
	    case 0x829D:
		return FNumber;
	    case 0x83BB:
		return IPTC_NAA;
	    case 0x8769:
		return ExifIFDPointer;
	    case 0x8773:
		return InterColorProfile;
	    case 0x8822:
		return ExposureProgram;
	    case 0x8824:
		return SpectralSensitivity;
	    case 0x8825:
		return GPSInfoIFDPointer;
	    case 0x8827:
		return ISOSpeedRatings;
	    case 0x8828:
		return OECF;
	    case 0x9000:
		return ExifVersion;
	    case 0x9003:
		return DateTimeOriginal;
	    case 0x9004:
		return DateTimeDigitized;
	    case 0x9101:
		return ComponentsConfiguration;
	    case 0x9102:
		return CompressedBitsPerPixel;
	    case 0x9201:
		return ShutterSpeedValue;
	    case 0x9202:
		return ApertureValue;
	    case 0x9203:
		return BrightnessValue;
	    case 0x9204:
		return ExposureBiasValue;
	    case 0x9205:
		return MaxApertureValue;
	    case 0x9206:
		return SubjectDistance;
	    case 0x9207:
		return MeteringMode;
	    case 0x9208:
		return LightSource;
	    case 0x9209:
		return Flash;
	    case 0x920A:
		return FocalLength;
	    case 0x9214:
		return SubjectArea;
	    case 0x927C:
		return MakerNote;
	    case 0x9286:
		return UserComment;
	    case 0x9290:
		return SubSecTime;
	    case 0x9291:
		return SubSecTimeOriginal;
	    case 0x9292:
		return SubSecTimeDigitized;
	    case 0xA000:
		return FlashPixVersion;
	    case 0xA001:
		return ColorSpace;
	    case 0xA002:
		return PixelXDimension;
	    case 0xA003:
		return PixelYDimension;
	    case 0xA004:
		return RelatedSoundFile;
	    case 0xA005:
		return InteroperabilityIFDPointer;
	    case 0xA20B:
		return FlashEnergy;
	    case 0xA20C:
		return SpatialFrequencyResponse;
	    case 0xA20E:
		return FocalPlaneXResolution;
	    case 0xA20F:
		return FocalPlaneYResolution;
	    case 0xA210:
		return FocalPlaneResolutionUnit;
	    case 0xA214:
		return SubjectLocation;
	    case 0xA215:
		return ExposureIndex;
	    case 0xA217:
		return SensingMethod;
	    case 0xA300:
		return FileSource;
	    case 0xA301:
		return SceneType;
	    case 0xA302:
		return CFAPattern;
	    case 0xA401:
		return CustomRendered;
	    case 0xA402:
		return ExposureMode;
	    case 0xA403:
		return WhiteBalance;
	    case 0xA404:
		return DigitalZoomRatio;
	    case 0xA405:
		return FocalLengthIn35mmFilm;
	    case 0xA406:
		return SceneCaptureType;
	    case 0xA407:
		return GainControl;
	    case 0xA408:
		return Contrast;
	    case 0xA409:
		return Saturation;
	    case 0xA40A:
		return Sharpness;
	    case 0xA40B:
		return DeviceSettingDescription;
	    case 0xA40C:
		return SubjectDistanceRange;
	    case 0xA420:
		return ImageUniqueID;
	    default:
		throw new IllegalArgumentException(String.valueOf(code));
	    }
	}
    }
    /**
     * GPS Tag
     */
    public static enum GPS implements Tag {
	GPSVersionID(0x0),
	    GPSLatitudeRef(0x1),
	    GPSLatitude(0x2),
	    GPSLongitudeRef(0x3),
	    GPSLongitude(0x4),
	    GPSAltitudeRef(0x5),
	    GPSAltitude(0x6),
	    GPSTimeStamp(0x7),
	    GPSSatellites(0x8),
	    GPSStatus(0x9),
	    GPSMeasureMode(0xA),
	    GPSDOP(0xB),
	    GPSSpeedRef(0xC),
	    GPSSpeed(0xD),
	    GPSTrackRef(0xE),
	    GPSTrack(0xF),
	    GPSImgDirectionRef(0x10),
	    GPSImgDirection(0x11),
	    GPSMapDatum(0x12),
	    GPSDestLatitudeRef(0x13),
	    GPSDestLatitude(0x14),
	    GPSDestLongitudeRef(0x15),
	    GPSDestLongitude(0x16),
	    GPSDestBearingRef(0x17),
	    GPSDestBearing(0x18),
	    GPSDestDistanceRef(0x19),
	    GPSDestDistance(0x1A),
	    GPSProcessingMethod(0x1B),
	    GPSAreaInformation(0x1C),
	    GPSDateStamp(0x1D),
	    GPSDifferential(0x1E);

	public final int code;

	GPS(int code){
	    this.code = code;
	}


	public int code(){
	    return this.code;
	}

	/**
	 * GPS Tag lookup 
	 */
	public static GPS valueOf(int code){
	    switch(code){
	    case 0x0:
		return GPSVersionID;
	    case 0x1:
		return GPSLatitudeRef;
	    case 0x2:
		return GPSLatitude;
	    case 0x3:
		return GPSLongitudeRef;
	    case 0x4:
		return GPSLongitude;
	    case 0x5:
		return GPSAltitudeRef;
	    case 0x6:
		return GPSAltitude;
	    case 0x7:
		return GPSTimeStamp;
	    case 0x8:
		return GPSSatellites;
	    case 0x9:
		return GPSStatus;
	    case 0xA:
		return GPSMeasureMode;
	    case 0xB:
		return GPSDOP;
	    case 0xC:
		return GPSSpeedRef;
	    case 0xD:
		return GPSSpeed;
	    case 0xE:
		return GPSTrackRef;
	    case 0xF:
		return GPSTrack;
	    case 0x10:
		return GPSImgDirectionRef;
	    case 0x11:
		return GPSImgDirection;
	    case 0x12:
		return GPSMapDatum;
	    case 0x13:
		return GPSDestLatitudeRef;
	    case 0x14:
		return GPSDestLatitude;
	    case 0x15:
		return GPSDestLongitudeRef;
	    case 0x16:
		return GPSDestLongitude;
	    case 0x17:
		return GPSDestBearingRef;
	    case 0x18:
		return GPSDestBearing;
	    case 0x19:
		return GPSDestDistanceRef;
	    case 0x1A:
		return GPSDestDistance;
	    case 0x1B:
		return GPSProcessingMethod;
	    case 0x1C:
		return GPSAreaInformation;
	    case 0x1D:
		return GPSDateStamp;
	    case 0x1E:
		return GPSDifferential;
	    default:
		throw new IllegalArgumentException(String.valueOf(code));
	    }
	}
    }
    /**
     * Interoperability Tag
     */
    public static enum INTR implements Tag {
	InteroperabilityIndex(0x1),
	    InteroperabilityVersion(0x2),
	    RelatedImageFileFormat(0x1000),
	    RelatedImageWidth(0x1001),
	    RelatedImageLength(0x1002);

	public final int code;

	INTR(int code){
	    this.code = code;
	}


	public int code(){
	    return this.code;
	}

	/**
	 * Interoperability Tag lookup 
	 */
	public static INTR valueOf(int code){
	    switch(code){
	    case 0x1:
		return InteroperabilityIndex;
	    case 0x2:
		return InteroperabilityVersion;
	    case 0x1000:
		return RelatedImageFileFormat;
	    case 0x1001:
		return RelatedImageWidth;
	    case 0x1002:
		return RelatedImageLength;
	    default:
		throw new IllegalArgumentException(String.valueOf(code));
	    }
	}
    }
}
