# Stardict Toolkit

[StarDict](http://www.stardict.org/) is a cross-platform and international dictionary Software. This project is a java library to parse [StarDict format files](http://www.stardict.org/StarDictFileFormat) and a tool to convert them to other formats. It is not a dictionary software which you can lookup words.

## Features

* Support StarDict Compressed Dictionary Index Format .idx.gz
* Support StarDict Compressed Dictionary Data Format .dict.dz
* Support StarDict "sametypesequence" Dictionary Data Format
* Plugable Exporter

## Command-Line Usage

You can download the latest release from the [releases page](https://github.com/sean-liang/stardict/releases). And please  also make sure you have java 1.8 or later installed. 

It is also possible to pull the source code and use maven to build the executable jar. Run command `mvn clean compile assembly:single` under the root folder of the project and you will get `target/stardict-0.2.2-jar-with-dependencies.jar`.

### Validation

It is always a good practice to validate your dictionary files in advance. And you can run below command to apply the validation:

```shell
java -jar stardict-0.2.2.jar validate <folder that contains dictonary files>
```

It will recursively walk through the directory tree and find all the info files that end with .ifo. Then it will try to match the index file and data file in the same folder with the same name and apply the validation on them. So if you have many dictionaries to validate, just point the path to their parent folder.

### Export

The main purpose of this tool is to convert the StarDict format files to other common data format like xml or json, so they can be used for easy future processing. Here is the way to do a conversion to xml:

```shell
java -jar stardict-0.2.2.jar export -o <output folder> -f xml <folder that contains dictonary files>
```

Please note that the elements in the xml are encoded in base64, you need to decode it to see the actual content. It will do the conversion recursively with the exact same strategy of validation.

Since the xml is the only format that supported right now, you can omit the "-f xml" part.

## API Quick Reference

For maven projects just add this dependency:

```xml
<dependency>
    <groupId>com.orangereading</groupId>
    <artifactId>stardict</artifactId>
    <version>0.2.2</version>
    <exclusions>
        <exclusion>
            <groupId>com.beust</groupId>
            <artifactId>jcommander</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.reflections</groupId>
            <artifactId>reflections</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

`jcommander` and `reflections ` are excluded as they are for command line usage only, not required in library usage scenario.

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

_I can't find any dictionary that use compressed index file, so I implement it based on the description from the document, and never got a chance to test it on a real dictionary._

### Read Data File

You need to read the info and index before reading the data file. And `RandomAccessFileDictionaryDataReader` is used to read a uncompressed dict file.

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

### Add Exporter

All exporters must implement `com.orangereading.stardict.exporter.DictionaryExporter` interface. And register itself by using annotation `com.orangereading.stardict.annotation.Exporter`.

Here is a sample exporter which just print some info to console:

```java
package com.orangereading.stardict.exporter;

import java.io.IOException;

import com.orangereading.stardict.annotation.Exporter;
import com.orangereading.stardict.cli.CommandExport;
import com.orangereading.stardict.domain.DictionaryItem;
import com.orangereading.stardict.domain.ImmutableDictionaryInfo;

@Exporter("cli")
public class ConsoleExporter implements DictionaryExporter {

	@Override
	public void init(final ImmutableDictionaryInfo info, final String name, final CommandExport command) {
		System.out.println(String.format("Init dictionary: %s, name: %s, extra-args: %s", info.getBookname(), name,
				command.getExtraArgs()));
	}

	@Override
	public void append(final DictionaryItem item) {
		System.out.println("Append: " + item.getIndex().getWord());
	}

	@Override
	public void done() throws IOException {
		System.out.println("Done!");
	}

}

```

Then run this exporter with below command:

```shell
java -jar stardict-0.2.2.jar export -f cli -x "put,extra,args,here" <folder that contains dictonary files>
```

## Contribution

* I've done test on 233 chinese-english dictionaries that I can find from internet, and it works like a charm. If you find it's not compatible with your dictionary. Please create a new issue and attach the dictionary files or the url to the dictionary.
* This project just implements a subset of the StarDict features. For other features like `Resource Storage`, `Tree Dictionary` are not supported. That's because I can't find any dictionary that supports those features. So it will be great if you can provide such kind of dictionaries.
* You are also welcomed to create pull requests to help fix issues, add new features and new exporters.