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
include schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/depend.make

# Include the progress variables for this target.
include schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/progress.make

# Include the compile flags for this target's objects.
include schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/flags.make

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/flags.make
schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o: ../src/test/p21read/p21read.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203e2 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o -c /Users/AJ/stepcode/src/test/p21read/p21read.cc

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.i"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203e2 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/AJ/stepcode/src/test/p21read/p21read.cc > CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.i

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.s"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203e2 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/AJ/stepcode/src/test/p21read/p21read.cc -o CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.s

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.requires:

.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.requires

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.provides: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.requires
	$(MAKE) -f schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/build.make schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.provides.build
.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.provides

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.provides.build: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o


# Object files for target p21read_sdai_ap203e2
p21read_sdai_ap203e2_OBJECTS = \
"CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o"

# External object files for target p21read_sdai_ap203e2
p21read_sdai_ap203e2_EXTERNAL_OBJECTS =

bin/p21read_sdai_ap203e2: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o
bin/p21read_sdai_ap203e2: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/build.make
bin/p21read_sdai_ap203e2: lib/libsdai_ap203e2.2.0.0.dylib
bin/p21read_sdai_ap203e2: lib/libstepeditor.2.0.0.dylib
bin/p21read_sdai_ap203e2: lib/libstepcore.2.0.0.dylib
bin/p21read_sdai_ap203e2: lib/libstepdai.2.0.0.dylib
bin/p21read_sdai_ap203e2: lib/libsteputils.2.0.0.dylib
bin/p21read_sdai_ap203e2: lib/libbase.2.0.0.dylib
bin/p21read_sdai_ap203e2: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable ../../bin/p21read_sdai_ap203e2"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203e2 && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/p21read_sdai_ap203e2.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/build: bin/p21read_sdai_ap203e2

.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/build

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/requires: schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/__/__/__/src/test/p21read/p21read.cc.o.requires

.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/requires

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/clean:
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203e2 && $(CMAKE_COMMAND) -P CMakeFiles/p21read_sdai_ap203e2.dir/cmake_clean.cmake
.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/clean

schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/depend:
	cd /Users/AJ/stepcode/test && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/AJ/stepcode /Users/AJ/stepcode/test/schemas/sdai_ap203e2 /Users/AJ/stepcode/test /Users/AJ/stepcode/test/schemas/sdai_ap203e2 /Users/AJ/stepcode/test/schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : schemas/sdai_ap203e2/CMakeFiles/p21read_sdai_ap203e2.dir/depend

