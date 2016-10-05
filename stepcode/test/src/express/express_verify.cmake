# Inherit the parent CMake setting
set(DEBUGGING_GENERATED_SOURCES )
set(CURRENT_SOURCE_DIR "/Users/AJ/stepcode/src/express")

# Include the file the provides the baseline against which
# current files will be compared

  include("/Users/AJ/stepcode/src/express/generated/verification_info.cmake")

  # Define a variety of convenience routines
  include("/Users/AJ/stepcode/cmake/Generated_Source_Utils.cmake")

  # Individually verify all of the files in question.
  set(filelist "/Users/AJ/stepcode/src/express/expscan.l;/Users/AJ/stepcode/src/express/expparse.y;/Users/AJ/stepcode/src/express/generated/expscan.c;/Users/AJ/stepcode/src/express/generated/expscan.h;/Users/AJ/stepcode/src/express/generated/expparse.c;/Users/AJ/stepcode/src/express/generated/expparse.h")
  VERIFY_FILES("${filelist}" 1 srcs_pass)
  if(NOT srcs_pass)
    if(NOT DEBUGGING_GENERATED_SOURCES)
      message(FATAL_ERROR "Sources have been modified and md5 sums have not been updated.  This generally indicates either\n   a) an input file has been modified but generated files have not been updated, or\n   b) genenerated files have been edited directly.\nTo clear the error:\n  a) Copy the new generated sources from the build directory to the generated/ sources directory, use the <target>_md5gen build target to create a new verifictation_info.cmake file, and copy verfication_info.cmake to generated/ as well.\n  b) install Perplex/Re2C/LEMON and make the changes to the input file rather than the generated file.\nNote:\n  If this is a debugging situation where multiple sequential tests must be conducted, temporarily set the variable DEBUGGING_GENERATED_SOURCES to ON during the CMake configure to disable this check.\nThis measure is necessary to ensure that compilations using either Perplex/Re2C/LEMON generation or the cached outputs of those tools produce consistent results.")
    else(NOT DEBUGGING_GENERATED_SOURCES)
      message(WARNING "Note: Sources have been modified and md5 sums have not been updated - build failure condition temporarily overridden by DEBUGGING_GENERATED_SOURCES setting.")
    endif(NOT DEBUGGING_GENERATED_SOURCES)
  endif(NOT srcs_pass)


# Local Variables:
# tab-width: 8
# mode: cmake
# indent-tabs-mode: t
# End:
# ex: shiftwidth=2 tabstop=8
