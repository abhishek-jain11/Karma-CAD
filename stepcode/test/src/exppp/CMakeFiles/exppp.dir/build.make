# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.6

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/local/Cellar/cmake/3.6.2/bin/cmake

# The command to remove a file.
RM = /usr/local/Cellar/cmake/3.6.2/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /Users/AJ/stepcode

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /Users/AJ/stepcode/test

# Include any dependencies generated for this target.
include src/exppp/CMakeFiles/exppp.dir/depend.make

# Include the progress variables for this target.
include src/exppp/CMakeFiles/exppp.dir/progress.make

# Include the compile flags for this target's objects.
include src/exppp/CMakeFiles/exppp.dir/flags.make

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o: src/exppp/CMakeFiles/exppp.dir/flags.make
src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o: ../src/express/fedex.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building C object src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/exppp.dir/__/express/fedex.c.o   -c /Users/AJ/stepcode/src/express/fedex.c

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/exppp.dir/__/express/fedex.c.i"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/AJ/stepcode/src/express/fedex.c > CMakeFiles/exppp.dir/__/express/fedex.c.i

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/exppp.dir/__/express/fedex.c.s"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/AJ/stepcode/src/express/fedex.c -o CMakeFiles/exppp.dir/__/express/fedex.c.s

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.requires:

.PHONY : src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.requires

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.provides: src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.requires
	$(MAKE) -f src/exppp/CMakeFiles/exppp.dir/build.make src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.provides.build
.PHONY : src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.provides

src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.provides.build: src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o


src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o: src/exppp/CMakeFiles/exppp.dir/flags.make
src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o: ../src/exppp/exppp-main.c
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Building C object src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -o CMakeFiles/exppp.dir/exppp-main.c.o   -c /Users/AJ/stepcode/src/exppp/exppp-main.c

src/exppp/CMakeFiles/exppp.dir/exppp-main.c.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing C source to CMakeFiles/exppp.dir/exppp-main.c.i"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -E /Users/AJ/stepcode/src/exppp/exppp-main.c > CMakeFiles/exppp.dir/exppp-main.c.i

src/exppp/CMakeFiles/exppp.dir/exppp-main.c.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling C source to assembly CMakeFiles/exppp.dir/exppp-main.c.s"
	cd /Users/AJ/stepcode/test/src/exppp && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/cc  $(C_DEFINES) $(C_INCLUDES) $(C_FLAGS) -S /Users/AJ/stepcode/src/exppp/exppp-main.c -o CMakeFiles/exppp.dir/exppp-main.c.s

src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.requires:

.PHONY : src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.requires

src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.provides: src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.requires
	$(MAKE) -f src/exppp/CMakeFiles/exppp.dir/build.make src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.provides.build
.PHONY : src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.provides

src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.provides.build: src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o


# Object files for target exppp
exppp_OBJECTS = \
"CMakeFiles/exppp.dir/__/express/fedex.c.o" \
"CMakeFiles/exppp.dir/exppp-main.c.o"

# External object files for target exppp
exppp_EXTERNAL_OBJECTS =

bin/exppp: src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o
bin/exppp: src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o
bin/exppp: src/exppp/CMakeFiles/exppp.dir/build.make
bin/exppp: lib/libexppp.2.0.0.dylib
bin/exppp: lib/libexpress.2.0.0.dylib
bin/exppp: lib/libbase.2.0.0.dylib
bin/exppp: src/exppp/CMakeFiles/exppp.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_3) "Linking C executable ../../bin/exppp"
	cd /Users/AJ/stepcode/test/src/exppp && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/exppp.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
src/exppp/CMakeFiles/exppp.dir/build: bin/exppp

.PHONY : src/exppp/CMakeFiles/exppp.dir/build

src/exppp/CMakeFiles/exppp.dir/requires: src/exppp/CMakeFiles/exppp.dir/__/express/fedex.c.o.requires
src/exppp/CMakeFiles/exppp.dir/requires: src/exppp/CMakeFiles/exppp.dir/exppp-main.c.o.requires

.PHONY : src/exppp/CMakeFiles/exppp.dir/requires

src/exppp/CMakeFiles/exppp.dir/clean:
	cd /Users/AJ/stepcode/test/src/exppp && $(CMAKE_COMMAND) -P CMakeFiles/exppp.dir/cmake_clean.cmake
.PHONY : src/exppp/CMakeFiles/exppp.dir/clean

src/exppp/CMakeFiles/exppp.dir/depend:
	cd /Users/AJ/stepcode/test && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/AJ/stepcode /Users/AJ/stepcode/src/exppp /Users/AJ/stepcode/test /Users/AJ/stepcode/test/src/exppp /Users/AJ/stepcode/test/src/exppp/CMakeFiles/exppp.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : src/exppp/CMakeFiles/exppp.dir/depend

