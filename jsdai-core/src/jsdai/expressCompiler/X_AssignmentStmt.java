/*
 * $Id$
 *
 * JSDAI(TM), a way to implement STEP, ISO 10303
 * Copyright (C) 1997-2008, LKSoftWare GmbH, Germany
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License
 * version 3 as published by the Free Software Foundation (AGPL v3).
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * JSDAI is a registered trademark of LKSoftWare GmbH, Germany
 * This software is also available under commercial licenses.
 * See also http://www.jsdai.net/
 */

// %modified: 1016210368457 %

/* Generated By:JJTree: Do not edit this line. X_AssignmentStmt.java */
package jsdai.expressCompiler;

import java.util.*;


// public class X_AssignmentStmt extends StatementNode {
public class X_AssignmentStmt
  extends SimpleNode {

  // GeneralRef() (Qualifier())* ":=" Expression() ";"
  Object general_reference = null;
  Vector qualifiers = null;
  String left_str;
  String right_str;
  boolean last_left = false;
	int last_ind=0;

  public X_AssignmentStmt(int id) {
    super(id);
    qualifiers = new Vector();
  }

  public X_AssignmentStmt(Compiler2 p, int id) {
    super(p, id);
    qualifiers = new Vector();
  }

  /** Accept the visitor. **/
  public Object jjtAccept(Compiler2Visitor visitor, Object data)
                   throws jsdai.lang.SdaiException {

    return visitor.visit(this, data);
  }

  /** Accept the visitor. **/
  public Object jjtAccept_old(Compiler2Visitor visitor, Object data)
                       throws jsdai.lang.SdaiException {
    JavaClass jc = ( JavaClass )data;

    if (children != null) {
      statements = new Vector();
      variable_names = new Vector();
      variable_declarations = new Vector();
      initializing_code = new Vector();
      left_str = "";
      right_str = "";

      for (int i = 0; i < children.length; ++i) {
        children[i].jjtAccept(visitor, data);

        if (jc != null) {
          if (jc.active) {
            if ((( SimpleNode )children[i]).java_contains_statements) {
              java_contains_statements = true;

              // variable_declaration += "\n" + ((SimpleNode)children[i]).variable_declaration;
              for (int j = 0; j < (( SimpleNode )children[i]).variable_names.size(); j++) {
                variable_names.add((( SimpleNode )children[i]).variable_names.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).variable_declarations.size(); j++) {
                variable_declarations.add((( SimpleNode )children[i]).variable_declarations.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).statements.size(); j++) {
                statements.add((( SimpleNode )children[i]).statements.elementAt(j));
              }

              // initializing_code += "\n" + ((SimpleNode)children[i]).initializing_code;
              for (int j = 0; j < (( SimpleNode )children[i]).initializing_code.size(); j++) {
                initializing_code.add((( SimpleNode )children[i]).initializing_code.elementAt(j));
              }
            } // if contains statements

          }
        }
      }
    }

    return visitor.visit(this, data);
  }

  public Object childrenAccept(Compiler2Visitor visitor, Object data)
                        throws jsdai.lang.SdaiException {
    JavaClass jc = ( JavaClass )data;

    if (children != null) {
      statements = new Vector();
      variable_names = new Vector();
      variable_declarations = new Vector();
      initializing_code = new Vector();

      // jc.java_str4 = "";
			left_str = jc.generated_java;
      right_str = "";
			boolean to_break = false;
			//find last attribute qualifier which is on left side and should generate setAttribute

// 					for (int k = children.length; k>=0; --k) {
// 							if(k < qualifiers.size()){
// 									if (children[k] instanceof X_AttributeQualifier) {
// 											last_ind = k;
// 											k = -1; // break
// 											System.out.println("last_ind: " +last_ind);
// 									}	else
// 											jc.last_left_qualifier = false;
// 							}else
// 									jc.last_left_qualifier = false;
// 					}


			printDDebug("children.length: " +children.length +"  qualifiers.size(): " +qualifiers.size(), jc);
      for (int i = 0; i < children.length; ++i) {


// 				if ( i == last_ind ){
// 						jc.last_left_qualifier = true;
// 						last_left = true;
// 				}else{
// 						jc.last_left_qualifier = false;
// 				}

				if ( i == qualifiers.size()-1 ){
						if(children[i] instanceof X_AttributeQualifier){
								jc.last_left_qualifier = true;
								last_left = true;
						}
				}else{
						jc.last_left_qualifier = false;
				}



				// new addition to handle replaceRange()
        if (children[i] instanceof X_IndexQualifier) {
	        if (i <= qualifiers.size()) {
  		        printDDebug("------ LEFT SIDE!!! ------", jc);
  	          ((X_IndexQualifier)children[i]).left_side = true;
          } else {
	            ((X_IndexQualifier)children[i]).left_side = false;
          }
        }


        children[i].jjtAccept(visitor, data);

// System.out.println("X_AssignmentStmt, jc.generated_java: " + jc.generated_java);

        if (jc != null) {
          if (jc.active) {
            if ((( SimpleNode )children[i]).java_contains_statements) {
              java_contains_statements = true;

              // variable_declaration += "\n" + ((SimpleNode)children[i]).variable_declaration;
              for (int j = 0; j < (( SimpleNode )children[i]).variable_names.size(); j++) {
                variable_names.add((( SimpleNode )children[i]).variable_names.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).variable_declarations.size(); j++) {
                variable_declarations.add((( SimpleNode )children[i]).variable_declarations.elementAt(j));
              }

              for (int j = 0; j < (( SimpleNode )children[i]).statements.size(); j++) {
                statements.add((( SimpleNode )children[i]).statements.elementAt(j));
              }

              // initializing_code += "\n" + ((SimpleNode)children[i]).initializing_code;
              for (int j = 0; j < (( SimpleNode )children[i]).initializing_code.size(); j++) {
                initializing_code.add((( SimpleNode )children[i]).initializing_code.elementAt(j));
              }
            } // if contains statements

			
            printDDebug("### AAA0 child: " + i + ", generated: " + jc.generated_java + ", child: " + children[i], jc);

            if (children[i] instanceof X_AttributeQualifier) {

              printDDebug("XP: assignment: qualifiers: " + qualifiers.size() + ", child node: " + i, jc);
              if (i <= qualifiers.size()) {
                printDDebug("------ LEFT SIDE!!! ------", jc );
                (( X_AttributeQualifier )children[i]).left_side = true;
								left_str += jc.generated_java;
								
								//								left_str += jc.generated_java;
								printDDebug("XP: generated java " +left_str, jc);                
              } else {
                printDDebug("### AAA01-attribute child: " + i + ", right_str: " + right_str + ", adding: " + jc.generated_java, jc);
                right_str += jc.generated_java;
              }
	      jc.generated_java = "";
            } else 
            if (children[i] instanceof X_IndexQualifier) {

              // System.out.println("XP: assignment: qualifiers: " + qualifiers.size() + ", child node: " + i);
              if (i <= qualifiers.size()) {
                printDDebug("------ LEFT SIDE!!! ------", jc);
                (( X_IndexQualifier )children[i]).left_side = true;
                left_str += jc.generated_java;
              } else {
                printDDebug("### AAA-index child: " + i + ", right_str: " + right_str + ", adding: " + jc.generated_java, jc);
                right_str += jc.generated_java;
              }
							jc.generated_java = "";
            } else 
            if (children[i] instanceof X_GroupQualifier) {
              if (i <= qualifiers.size()) {
                printDDebug("------ LEFT SIDE!!! ------", jc);
                (( X_GroupQualifier )children[i]).left_side = true;
								left_str += jc.generated_java;
								printActive("XP: generated java " +left_str, jc);                
              } else {
								printDDebug("### AAA-parameter child: " + i + " right_str: " + right_str + ", adding: " + jc.generated_java, jc);						 
								right_str += jc.generated_java;
							  jc.generated_java = "";
							}

            } else 
            if (children[i] instanceof X_ParameterRef) {
            
              printDDebug("### AAA-parameter child: " + i + " right_str: " + right_str + ", adding: " + jc.generated_java, jc);
              right_str += jc.generated_java;
              jc.generated_java = "";
            } else {
              printDDebug("### AAA-other child: " + i + " right_str: " + right_str + ", adding: " + jc.generated_java, jc);
              right_str += jc.generated_java;
              jc.generated_java = "";
            }
          }
        }
      }
    }

    return data;
  }

  public Object childrenAccept_old(Compiler2Visitor visitor, Object data)
                            throws jsdai.lang.SdaiException {
    JavaClass jc = ( JavaClass )data;

    if (children != null) {

      // jc.java_str4 = "";
      for (int i = 0; i < children.length; ++i) {
        children[i].jjtAccept(visitor, data);

        if (jc != null) {
          if (jc.active) {
            if (children[i] instanceof X_AttributeRef) {
              printActive("XP: assignment: qualifiers: " + qualifiers.size() + ", child node: " + i, jc);

              if (i <= qualifiers.size()) {
                printDDebug("------ LEFT SIDE!!! ------", jc);
                (( X_AttributeRef )children[i]).left_side = true;
              }
            }
          }
        }
      }
    }

    return data;
  }
}