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
include schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/depend.make

# Include the progress variables for this target.
include schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/progress.make

# Include the compile flags for this target's objects.
include schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/flags.make

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/flags.make
schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o: ../src/cllazyfile/lazy_test.cc
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Building CXX object schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++   $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -o CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o -c /Users/AJ/stepcode/src/cllazyfile/lazy_test.cc

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.i: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Preprocessing CXX source to CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.i"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -E /Users/AJ/stepcode/src/cllazyfile/lazy_test.cc > CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.i

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.s: cmake_force
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green "Compiling CXX source to assembly CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.s"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203 && /Applications/Xcode.app/Contents/Developer/Toolchains/XcodeDefault.xctoolchain/usr/bin/c++  $(CXX_DEFINES) $(CXX_INCLUDES) $(CXX_FLAGS) -S /Users/AJ/stepcode/src/cllazyfile/lazy_test.cc -o CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.s

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.requires:

.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.requires

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.provides: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.requires
	$(MAKE) -f schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/build.make schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.provides.build
.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.provides

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.provides.build: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o


# Object files for target lazy_sdai_ap203
lazy_sdai_ap203_OBJECTS = \
"CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o"

# External object files for target lazy_sdai_ap203
lazy_sdai_ap203_EXTERNAL_OBJECTS =

bin/lazy_sdai_ap203: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o
bin/lazy_sdai_ap203: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/build.make
bin/lazy_sdai_ap203: lib/libsdai_ap203.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libsteplazyfile.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libstepeditor.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libstepcore.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libstepdai.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libsteputils.2.0.0.dylib
bin/lazy_sdai_ap203: lib/libbase.2.0.0.dylib
bin/lazy_sdai_ap203: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/link.txt
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --green --bold --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_2) "Linking CXX executable ../../bin/lazy_sdai_ap203"
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203 && $(CMAKE_COMMAND) -E cmake_link_script CMakeFiles/lazy_sdai_ap203.dir/link.txt --verbose=$(VERBOSE)

# Rule to build all files generated by this target.
schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/build: bin/lazy_sdai_ap203

.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/build

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/requires: schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/__/__/__/src/cllazyfile/lazy_test.cc.o.requires

.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/requires

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/clean:
	cd /Users/AJ/stepcode/test/schemas/sdai_ap203 && $(CMAKE_COMMAND) -P CMakeFiles/lazy_sdai_ap203.dir/cmake_clean.cmake
.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/clean

schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/depend:
	cd /Users/AJ/stepcode/test && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/AJ/stepcode /Users/AJ/stepcode/test/schemas/sdai_ap203 /Users/AJ/stepcode/test /Users/AJ/stepcode/test/schemas/sdai_ap203 /Users/AJ/stepcode/test/schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : schemas/sdai_ap203/CMakeFiles/lazy_sdai_ap203.dir/depend

