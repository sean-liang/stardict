package com.orangereading.stardict.domain;

public enum TypeIdentifier {
	// 'm' Word's pure text meaning. The data should be a utf-8 string ending
	// with '\0'.
	TEXT('m'),
	// 'l' Word's pure text meaning. The data is NOT a utf-8 string, but is
	// instead a string in locale encoding, ending with '\0'. Sometimes using
	// this type will save disk space, but its use is discouraged.
	TEXT_LOCALE('l'),
	// 'g' A utf-8 string which is marked up with the Pango text markup
	// language. For more information about this markup language, See the "Pango
	// Reference Manual." You might have it installed locally at:
	// file:///usr/share/gtk-doc/html/pango/PangoMarkupFormat.html
	PANGO('g'),
	// 't' English phonetic string. The data should be a utf-8 string ending
	// with '\0'.
	// Here are some utf-8 phonetic characters: θʃŋʧðʒæıʌʊɒɛəɑɜɔˌˈːˑṃṇḷ
	// æɑɒʌәєŋvθðʃʒɚːɡˏˊˋ
	ENGLISH_PHONETIC('t'),
	// 'x' A utf-8 string which is marked up with the xdxf language. See
	// http://xdxf.sourceforge.net StarDict have these extention: can have
	// "type" attribute, it can be "image", "sound", "video" and "attach". can
	// have "k" attribute.
	XDXF('x'),
	// 'y' Chinese YinBiao or Japanese KANA. The data should be a utf-8 string
	// ending with '\0'.
	CHINESE_YINBIAO_OR_JAPANESE_KANA('y'),
	// 'k' KingSoft PowerWord's data. The data is a utf-8 string ending with
	// '\0'. It is in XML format.
	KINGSOFT_POWERWORD('k'),
	// 'w' MediaWiki markup language. See
	// http://meta.wikimedia.org/wiki/Help:Editing#The_wiki_markup
	MEDIA_WIKI('w'),
	// 'h' Html codes.
	HTML_CODES('h'),
	// 'r' Resource file list. The content can be: img:pic/example.jpg // Image
	// file snd:apple.wav // Sound file vdo:film.avi // Video file att:file.bin
	// // Attachment file More than one line is supported as a list of available
	// files. StarDict will find the files in the Resource Storage. The image
	// will be shown, the sound file will have a play button. You can "save as"
	// the attachment file and so on.
	RESOURCE_LIST('r'),
	// 'W' wav file. The data begins with a network byte-ordered guint32 to
	// identify the wav file's size, immediately followed by the file's content.
	WAVE('W'),
	// 'P' Picture file. The data begins with a network byte-ordered guint32 to
	// identify the picture file's size, immediately followed by the file's
	// content.
	PICTURE('P'),
	// 'X' this type identifier is reserved for experimental extensions.
	RESERVED('X');

	private final char identifier;

	private TypeIdentifier(final char identifier) {
		this.identifier = identifier;
	}

	public static TypeIdentifier valueOf(final char c) {
		for (TypeIdentifier type : TypeIdentifier.values())
			if (type.identifier == c)
				return type;
		return null;
	}

	public char getIdentifierChar() {
		return this.identifier;
	}

	public boolean isUpperCase() {
		return this.identifier >= 'A' && this.identifier <= 'Z';
	}
}
