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

# Utility rule file for version_string.

# Include the progress variables for this target.
include CMakeFiles/version_string.dir/progress.make

CMakeFiles/version_string: ver_string


ver_string:
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "Generating ver_string"
	/usr/local/Cellar/cmake/3.6.2/bin/cmake -DSOURCE_DIR=/Users/AJ/stepcode -DBINARY_DIR=/Users/AJ/stepcode/test -P /Users/AJ/stepcode/test/sc_version_string.cmake

version_string: CMakeFiles/version_string
version_string: ver_string
version_string: CMakeFiles/version_string.dir/build.make

.PHONY : version_string

# Rule to build all files generated by this target.
CMakeFiles/version_string.dir/build: version_string

.PHONY : CMakeFiles/version_string.dir/build

CMakeFiles/version_string.dir/clean:
	$(CMAKE_COMMAND) -P CMakeFiles/version_string.dir/cmake_clean.cmake
.PHONY : CMakeFiles/version_string.dir/clean

CMakeFiles/version_string.dir/depend:
	cd /Users/AJ/stepcode/test && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/AJ/stepcode /Users/AJ/stepcode /Users/AJ/stepcode/test /Users/AJ/stepcode/test /Users/AJ/stepcode/test/CMakeFiles/version_string.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : CMakeFiles/version_string.dir/depend

