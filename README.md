# BytecodeParser
This was created as a way to parse java class files.
Some example uses include inspecting the constant pool, fields, or methods.
Future plans include updating BytecodeParser to write class files, creating a nice library for easily changing class files.

## Usage
`ByteCodeLoader.main(new String[]{"-f","/path/to/class/file"}` Initializes the parser on a class file. Note, this also works with jar files.

Currently BytecodeParser outputs to `bytecodeLog.log` and `stdout`.

## Future Work
Allow writing of class files with changes to constant pool, methods, or fields. 

Instead of merely logging the information, provide an API for clients to use to inspect the data.
