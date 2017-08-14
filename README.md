# Stardict Toolkit

[StarDict](http://www.stardict.org/) is a cross-platform and international dictionary Software. This project is a java library to parse [StarDict format files](http://www.stardict.org/StarDictFileFormat) and a tool to convert them to other formats. It is not a dictionary software which you can lookup words.

## Features

* Support StarDict Compressed Dictionary Index Format .idx.gz
* Support StarDict Compressed Dictionary Data Format .dict.dz
* Support StarDict "sametypesequence" Dictionary Data Format

## Command-Line Usage

You can download the latest release from the [releases page](https://github.com/sean-liang/stardict/releases). And please  also make sure you have java 1.8 or later installed.

### Validation

It is always a good practice to validate your dictionary files in advance. And you can run below command to apply the validation:

```shell
java -jar stardict-0.1.0.jar validate <folder that contains dictonary files>
```

It will recursively walk through the directory tree and find all the info files that end with .ifo. Then it will try to match the index file and data file in the same folder with the same name and apply the validation on them. So if you have many dictionaries to validate, just point the path to their parent folder.

### Export

The main purpose of this tool is to convert the StarDict format files to other common data format like xml or json, so they can be used for easy future processing. Here is the way to do a conversion to xml:

```shell
java -jar stardict-0.1.0.jar export -o <output folder> -f xml <folder that contains dictonary files>
```

Please note that the elements in the xml are encoded in base64, you need to decode it to see the actual content. It will do the conversion recursively with the exact same strategy of validation.

Since the xml is the only format that supported right now, you can omit the "-f xml" part.

## Build

You can use Maven to build a binary and a library package yourself. Just run `mvn package` under the root folder of the project, you will get two jar files under target folder: `stardict-0.1.0.jar` and `stardict-0.1.0-jar-with-dependencies.jar`. The later one is the executable binary with all the dependencies, you can use it as a standalone tool from command-line. And the first one is for library usage which doesn't have the dependencies since they are optional for API usage.


## API Quick Reference

### Load Dictionary

`DictionaryReader` is used to read the whole dictionary including info, index and data files and provide a way to iterate all the items.

```java
// folder contains dictionary files
final String path = ...;
// dictionary file name without extension
final String name = ...;
// Load dictionary, will detected the index and data format automatically by their extension name
final DictionaryReader reader = new CommonFileDictionaryReader(path, name);
// Print out all items
reader.eachWord(item -> {
	System.out.println(item.getIndex().getWord());
    item.getEntries().forEach(entry -> System.out.println(entry.getType().name() + "> " + entry.getDataAsUTF8String());
});
```

### Read Info File

Use `StreamDictionaryInfoReader` to read info file.

```java
try (final Stream<String> lines = Files.lines(Paths.get(this.infoFilePath))) {
    final DictionaryInfoReader reader = new StreamDictionaryInfoReader(lines);
    final ImmutableDictionaryInfo info = reader.read();
}
```

### Read Index File

You can use `InputStreamDictionaryIndexReader` to read index file.

```java
// path to the index file
final String path = ...;
final InputStream in = new FileInputStream(path);
final DictionaryIndexReader reader = InputStreamDictionaryIndexReader(in);
// print out all words
reader.eachItem(item -> System.out.println(item.getWord()));
```

You just need to wrap the input stream with `GZIPInputStream` to read gzip compressed index file.

```java
...
final InputStream in = new FileInputStream(path);
final InputStream gzipIn = GZIPInputStream(in);
final DictionaryIndexReader reader = InputStreamDictionaryIndexReader(gzipIn);
...
```

_I can't find any dictionary that use compressed index file, so I implement it based on the description from the document, and never got a change to test it on a real dictionary._

### Read Data File

You need to read the info and index before read the data file. And `RandomAccessFileDictionaryDataReader` is used to read a uncompressed dict file from file.

```java
// the dict file
final File file = ...;
// read info first
final ImmutableDictionaryInfo info = ...;
// use different parsers based on the info content
final DictionaryParser parser = null == this.info.getSameTypeSequence() ? new PlainDictionaryParser() : new SameTypeSequenceDictionaryParser(info.getSameTypeSequence());
// read index then
final DictionaryIndexReader indexReader = ...;
// read data
final DictionaryIndexReader reader = new RandomAccessFileDictionaryDataReader(parser, file);
indexReader.eachItem(info, item -> {
    try {
        final DictionaryItem data = reader.read(item);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
});
```

_All the dictionary data files I can find are compressed, so the uncompressed data reader is never tested on real data._

StarDict use dictzip for its data file compression. Dictzip file is just a normal gzip file with some extra headers that can be used to support random access. I can't find a working java library to work with the dictzip format. So I just read and decompress the data file and cache it fully in memory. __SO IT'S VERY IMPORTANT TO TAKE MEMORY USAGE INTO CONSIDERATION FOR LARGE DICTIONARIES__.

Here is the sample code to read a compressed data file:

```java
...
final DictionaryIndexReader reader = new MemoryMappedInputStreamDictionaryDataReader(parser, new GZIPInputStream(new FileInputStream(path)))
...
```

## Contribution

* I've done test on 233 chinese-english dictionaries that I can find from internet, and it works like a charm. If you find it's not compatible with your dictionary. Please create a new issue and attach the dictionary files or url to the dictionary.
* This project just implements a subset of the StarDict features. For other features like `Resource Storage`, `Tree Dictionary` are not supported. That's because I can't find any dictionary that supports those features. So it will be great if you can provide such kind of dictionaries.
* You are also welcomed to create pull requests to help fix issues, add new features and new converters.