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

# Utility rule file for generate_cpp_sdai_IFC4.

# Include the progress variables for this target.
include schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/progress.make

schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: bin/exp2cxx
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: ../data/ifc4/IFC4.exp
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4_unity_types.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiAll.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/compstructs.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/schema.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4.cc
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4.init.cc


schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc:
	@$(CMAKE_COMMAND) -E cmake_echo_color --switch=$(COLOR) --blue --bold --progress-dir=/Users/AJ/stepcode/test/CMakeFiles --progress-num=$(CMAKE_PROGRESS_1) "[exp2cxx] Generating 2072 C++ files for sdai_IFC4."
	cd /Users/AJ/stepcode/test/schemas/sdai_IFC4 && /usr/local/Cellar/cmake/3.6.2/bin/cmake -DEXE="/Users/AJ/stepcode/test/bin/exp2cxx" -DEXP="/Users/AJ/stepcode/data/ifc4/IFC4.exp" -DONESHOT="FALSE" -DSDIR="/Users/AJ/stepcode/test/schemas/sdai_IFC4" -P /Users/AJ/stepcode/cmake/SC_Run_exp2cxx.cmake

schemas/sdai_IFC4/SdaiIFC4_unity_types.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/SdaiIFC4_unity_types.cc

schemas/sdai_IFC4/SdaiAll.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/SdaiAll.cc

schemas/sdai_IFC4/compstructs.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/compstructs.cc

schemas/sdai_IFC4/schema.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/schema.cc

schemas/sdai_IFC4/SdaiIFC4.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/SdaiIFC4.cc

schemas/sdai_IFC4/SdaiIFC4.init.cc: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
	@$(CMAKE_COMMAND) -E touch_nocreate schemas/sdai_IFC4/SdaiIFC4.init.cc

generate_cpp_sdai_IFC4: schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4_unity_entities.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4_unity_types.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiAll.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/compstructs.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/schema.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/SdaiIFC4.init.cc
generate_cpp_sdai_IFC4: schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/build.make

.PHONY : generate_cpp_sdai_IFC4

# Rule to build all files generated by this target.
schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/build: generate_cpp_sdai_IFC4

.PHONY : schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/build

schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/clean:
	cd /Users/AJ/stepcode/test/schemas/sdai_IFC4 && $(CMAKE_COMMAND) -P CMakeFiles/generate_cpp_sdai_IFC4.dir/cmake_clean.cmake
.PHONY : schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/clean

schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/depend:
	cd /Users/AJ/stepcode/test && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /Users/AJ/stepcode /Users/AJ/stepcode/test/schemas/sdai_IFC4 /Users/AJ/stepcode/test /Users/AJ/stepcode/test/schemas/sdai_IFC4 /Users/AJ/stepcode/test/schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : schemas/sdai_IFC4/CMakeFiles/generate_cpp_sdai_IFC4.dir/depend

